package org.openelisglobal.validation.constraintvalidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.openelisglobal.common.log.LogEvent;
import org.openelisglobal.common.provider.validation.AccessionNumberValidatorFactory.AccessionFormat;
import org.openelisglobal.common.provider.validation.IAccessionNumberValidator.ValidationResults;
import org.openelisglobal.common.util.ConfigurationProperties;
import org.openelisglobal.common.util.ConfigurationProperties.Property;
import org.openelisglobal.sample.util.AccessionNumberUtil;
import org.openelisglobal.validation.annotations.ValidAccessionNumber;

public class AccessionNumberConstraintValidator implements ConstraintValidator<ValidAccessionNumber, String> {

    ValidAccessionNumber validateAccessionNumberConstraint;

    @Override
    public void initialize(ValidAccessionNumber constraint) {
        validateAccessionNumberConstraint = constraint;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if( !Boolean.valueOf(ConfigurationProperties.getInstance().getPropertyValue(Property.ACCESSION_NUMBER_VALIDATE))) {
            return true;
        }
        if (org.apache.commons.validator.GenericValidator.isBlankOrNull(value)) {
            return true;
        }
        if (AccessionFormat.ALPHANUM_DASH.equals(validateAccessionNumberConstraint.format())) {
            return value.matches("^[a-zA-Z0-9-]*$");
        }
        try {
            return ValidationResults.SUCCESS
                    .equals(AccessionNumberUtil.getAccessionNumberValidator(validateAccessionNumberConstraint.format())
                    .validFormat(value, validateAccessionNumberConstraint.dateValidate()));
        } catch (IllegalArgumentException e) {
            LogEvent.logError(e);
            return false;
        }
    }
}