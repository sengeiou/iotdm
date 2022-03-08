package com.aibaixun.iotdm.transport.limits;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 设备限制 连接服务
 * @author wangxiao@aibaixun.com
 * @date 2022/3/8
 */
public class DefaultTransportLimitService implements TransportLimitService {

    private final Logger log  = LoggerFactory.getLogger(DefaultTransportLimitService.class);

    @Value("${transport.limit.ip_limit_enabled:false}")
    private boolean ipRateLimitsEnabled;
    @Value("${transport.limit.max_wrong_credentials_per_ip:10}")
    private int maxWrongCredentialsPerIp;
    @Value("${transport.limit.ip_block_timeout:60000}")
    private long ipBlockTimeout;


    private final Map<InetAddress, RemoteAddressLimitStats> remoteAddressMap= new ConcurrentHashMap<>();

    @Override
    public boolean checkAddress(InetSocketAddress address) {
        if (!ipRateLimitsEnabled){
            return true;
        }
        var stats = remoteAddressMap.computeIfAbsent(address.getAddress(), a -> new RemoteAddressLimitStats());
        return !stats.isBlocked() || (stats.getLastActivityTs() + ipBlockTimeout < System.currentTimeMillis());
    }

    @Override
    public void onDeviceAuthSuccess(InetSocketAddress address) {
        if (!ipRateLimitsEnabled) {
            return;
        }

        var stats = remoteAddressMap.computeIfAbsent(address.getAddress(), a -> new RemoteAddressLimitStats());
        stats.getLock().lock();
        try {
            stats.setLastActivityTs(System.currentTimeMillis());
            stats.setFailureCount(0);
            if (stats.isBlocked()) {
                stats.setBlocked(false);
                log.info("[{}] IP address un-blocked due to correct credentials.", address.getAddress());
            }
        } finally {
            stats.getLock().unlock();
        }
    }

    @Override
    public void onDeviceAuthFailure(InetSocketAddress address) {
        if (!ipRateLimitsEnabled) {
            return;
        }

        var stats = remoteAddressMap.computeIfAbsent(address.getAddress(), a -> new RemoteAddressLimitStats());
        stats.getLock().lock();
        try {
            stats.setLastActivityTs(System.currentTimeMillis());
            int failureCount = stats.getFailureCount() + 1;
            stats.setFailureCount(failureCount);
            if (failureCount >= maxWrongCredentialsPerIp) {
                log.info("[{}] IP address blocked due to constantly wrong credentials.", address.getAddress());
                stats.setBlocked(true);
            }
        } finally {
            stats.getLock().unlock();
        }
    }
}
