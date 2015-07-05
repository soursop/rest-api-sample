package com.demo.validator;

import java.util.Set;


import javax.validation.ConstraintViolation;
import javax.validation.metadata.ConstraintDescriptor;

import org.springframework.beans.NotReadablePropertyException;
import org.springframework.core.env.Environment;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * ProfileValidatorFactoryBean
 * @author Gim Gyoung Jin
 * change print strategy by -Dspring.profiles.active
 */
public class ProfileValidatorFactoryBean extends LocalValidatorFactoryBean {

	private final boolean isNonLive;

	public ProfileValidatorFactoryBean(Environment environment) {
		super();
		// if live profile is not accepted => e.g. dev, local ... else
		isNonLive = !environment.acceptsProfiles("live");
	}
	
	@Override
	protected void processConstraintViolations(
			Set<ConstraintViolation<Object>> violations, Errors errors) {
		
		boolean isNotBIndException = !(errors instanceof BindingResult);
		if (isNonLive || isNotBIndException) {
			super.processConstraintViolations(violations, errors);
			return;
		}
		for (ConstraintViolation<Object> violation : violations) {
			String field = determineField(violation);
			FieldError fieldError = errors.getFieldError(field);
			if (fieldError == null || !fieldError.isBindingFailure()) {
				try {
					ConstraintDescriptor<?> cd = violation.getConstraintDescriptor();
					String errorCode = determineErrorCode(cd);
					Object[] errorArgs = getArgumentsForConstraint(errors.getObjectName(), field, cd);
					// Can do custom FieldError registration with invalid value from ConstraintViolation,
					// as necessary for Hibernate Validator compatibility (non-indexed set path in field)
					BindingResult bindingResult = (BindingResult) errors;
					String nestedField = bindingResult.getNestedPath() + field;
					if ("".equals(nestedField)) {
						String[] errorCodes = bindingResult.resolveMessageCodes(errorCode);
						bindingResult.addError(new ObjectError(
								errors.getObjectName(), errorCodes, errorArgs, violation.getMessage()));
					}
					else {
						bindingResult.addError(new FieldError(
								errors.getObjectName(), nestedField, violation.getMessage()));
					}
				}
				catch (NotReadablePropertyException ex) {
					throw new IllegalStateException("JSR-303 validated property '" + field +
							"' does not have a corresponding accessor for Spring data binding - " +
							"check your DataBinder's configuration (bean property versus direct field access)", ex);
				}
			}
		}		
	}

	private String determineErrorCode(ConstraintDescriptor<?> cd) {
		return cd.getAnnotation().annotationType().getSimpleName();
	}

	private String determineField(ConstraintViolation<Object> violation) {
		return violation.getPropertyPath().toString();
	}
	
}
