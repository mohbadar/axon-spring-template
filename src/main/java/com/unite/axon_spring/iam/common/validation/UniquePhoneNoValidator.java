package com.unite.axon_spring.iam.common.validation;

import com.unite.axon_spring.iam.eventhadler.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class UniquePhoneNoValidator implements ConstraintValidator<UniqueUsername, String> {

    @Autowired
    UserRepository userRepository;

    @Override
    public boolean isValid(String phoneNo, ConstraintValidatorContext context) {
        return !userRepository.existsByPhoneNo(phoneNo);
    }
}