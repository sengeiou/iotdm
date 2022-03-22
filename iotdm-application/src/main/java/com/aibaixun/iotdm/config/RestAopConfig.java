package com.aibaixun.iotdm.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * rest ful aop config
 * @author wangxiao@aibaixun.com
 * @date 2022/3/22
 */
@Aspect
@Component
public class RestAopConfig {

    /**
     * 控制是否开启日志
     */
    public static Boolean onOff = false;

    private final Logger logger = LoggerFactory.getLogger(RestAopConfig.class);


    @Pointcut("execution(public * com.aibaixun.iotdm.controller..*.*(..))")
    public void pointCutRestDef(){
    }


    @Around("pointCutRestDef()")
    public Object processRst(ProceedingJoinPoint point) throws Throwable {
        Object returnValue;
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (sra == null) {
            return point.proceed();
        }
        HttpServletRequest request = sra.getRequest();
        long startTime = System.currentTimeMillis();
        logger.info("iotDm.dOLog >>>>>restUrl:{} rest method:——>{}.{}  time:{}", request.getRequestURI(),point.getSignature().getDeclaringTypeName() ,  point.getSignature().getName(),startTime);
        returnValue = point.proceed(point.getArgs());
        long endTime = System.currentTimeMillis();
        logger.info("iotDm.dOLog >>>>>restUrl:{} rest method:——>{}.{}  time:{},useTime:{}", request.getRequestURI(),point.getSignature().getDeclaringTypeName() ,  point.getSignature().getName(),endTime,endTime-startTime);
        return returnValue;
    }

}
