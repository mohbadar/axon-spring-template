package com.unite.axon_spring.iam.audit.annotation;

import com.unite.axon_spring.iam.model.User;
import com.unite.axon_spring.iam.service.HostService;
import com.unite.axon_spring.iam.service.JsonParserUtil;
import com.unite.axon_spring.iam.service.UserService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
@Profile(value = "prod")
public class AuditableAspect {

    Logger log = LoggerFactory.getLogger(AuditableAspect.class.getName());

    @Autowired
    private HostService hostService;
    @Autowired
    private UserService userService;

    @Before("@annotation(com.unite.axon_spring.iam.audit.annotation.Auditable)")
    public void annotatedBeforeLoggingAdvice(JoinPoint joinPoint) throws Throwable {
        String desc = "[" + joinPoint.getSignature().getDeclaringTypeName() + "]" +
                "[" + ((MethodSignature) joinPoint.getSignature()).getMethod().getName() + "] " +
                "Input Params : " + joinPoint.getArgs().toString();
//        log.info(desc);

        String methodName = ((MethodSignature) joinPoint.getSignature()).getMethod().getName();
        String eventId= joinPoint.getSignature().getDeclaringTypeName() + " | "+  methodName ;
        String eventName= joinPoint.getSignature().getDeclaringTypeName();

        User user  = userService.getLoggedInUser();


        AuditRequestDto dto = new AuditRequestDto();
        dto.setHostIp(hostService.getDefaultIP());
        dto.setSessionUserName(user.getName());
        dto.setApplicationId("IAM");
        dto.setApplicationName("Identity and Access Management for Spring and AxonIQ");
        dto.setDescription(desc);
        dto.setEventId(eventId);
        dto.setEventName(eventName);
        dto.setEventType(joinPoint.getSignature().getDeclaringType().toGenericString());
        dto.setHostName(hostService.getDefaultHostName());
        dto.setId(methodName);
        dto.setIdType(methodName);
        dto.setSessionUserId(user.getId());
        dto.setModuleName(joinPoint.toLongString());
        dto.setModuleId(joinPoint.toLongString());
        dto.setCreatedBy(user.getUsername());
        dto.setActionTimeStamp(LocalDateTime.now());
        dto.setCreatedBy(user.getUsername());
        dto.setSessionUserId(user.getId());
        dto.setSessionUserName(user.getUsername());

        log.info(dto.toString());
        //Store Or Route to a Messaging System
    }

}