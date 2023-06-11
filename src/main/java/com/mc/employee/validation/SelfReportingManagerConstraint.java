package com.mc.employee.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SelfReportingManagerValidator.class)
@Documented
public @interface SelfReportingManagerConstraint {
	String message() default "An employee cannot have self as reporting manager.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
