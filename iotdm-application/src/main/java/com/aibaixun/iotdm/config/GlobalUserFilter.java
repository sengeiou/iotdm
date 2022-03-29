package com.aibaixun.iotdm.config;

import com.aibaixun.basic.context.UserContextHolder;
import com.aibaixun.basic.entity.BaseAuthUser;
import com.aibaixun.common.redis.util.RedisRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 用户拦截过滤器
 * @author wangxiao@aibaixun.com
 * @date 2022/3/14
 */
@Component
@WebFilter(
        urlPatterns = {"/*"},
        filterName = "globalUserFilter"
)
public class GlobalUserFilter implements Filter, Ordered {
    @Autowired
    private RedisRepository redisRepository;

    public GlobalUserFilter() {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String token = request.getHeader("authentication");
        if (StringUtils.isBlank(token)) {
            String userId = request.getHeader("userId");
            token = (String) this.redisRepository.get(this.getUserIdRedisKey(userId));
        }

        BaseAuthUser userInfo = (BaseAuthUser) this.redisRepository.get(this.getTokenRedisKey(token));
        UserContextHolder.setUserInfo(userInfo);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public int getOrder() {
        return 1;
    }

    private String getTokenRedisKey(String token) {
        return "gail-auth:" + token;
    }

    private String getUserIdRedisKey(String userId) {
        return "gail-userId:" + userId;
    }
}