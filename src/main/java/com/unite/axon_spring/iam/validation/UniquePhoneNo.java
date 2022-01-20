package com.unite.axon_spring.iam.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniquePhoneNoValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniquePhoneNo {
    String message() default "PhoneNo is already registered";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
