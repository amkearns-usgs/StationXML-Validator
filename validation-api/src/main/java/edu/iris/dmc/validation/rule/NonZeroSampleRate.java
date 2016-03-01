package edu.iris.dmc.validation.rule;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import edu.iris.dmc.validation.validator.EpochOverlapValidator;
import edu.iris.dmc.validation.validator.EpochRangeValidator;
import edu.iris.dmc.validation.validator.NonZeroSampleRateValidator;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = { NonZeroSampleRateValidator.class })
public @interface NonZeroSampleRate {
	String message() default "{edu.iris.dmc.validator.rule.NonZero}";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
	boolean required() default false;
	String expression() default "[unassigned]";
}