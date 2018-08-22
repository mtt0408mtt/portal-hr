package com.pm.portal.validator;
import  javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import com.pm.portal.util.ValidatorUtil;


public class IsCodeValidator implements ConstraintValidator<IsCode, String> {

	private boolean required = false;
	
	public void initialize(IsCode constraintAnnotation) {
		required = constraintAnnotation.required();
	}

	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(required) {
			return ValidatorUtil.isCode(value);
		}else {
			if(StringUtils.isEmpty(value)) {
				return true;
			}else {
				return ValidatorUtil.isCode(value);
			}
		}
	}

}
