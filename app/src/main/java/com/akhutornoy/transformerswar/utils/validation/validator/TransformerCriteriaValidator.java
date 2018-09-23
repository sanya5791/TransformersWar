package com.akhutornoy.transformerswar.utils.validation.validator;

public class TransformerCriteriaValidator implements Validator {
    private static final int MIN_CRITERIA_VALUE = 1;
    private static final int MAX_CRITERIA_VALUE = 10;
    private final int criteriaValue;
    private final ValidationState[] validationStates;

    public TransformerCriteriaValidator(int criteriaValue, ValidationState... validationStates) {
        this.criteriaValue = criteriaValue;
        this.validationStates = validationStates;
    }

    @Override
    public ValidationState validate() {
        for (ValidationState error : validationStates) {
            if (hasError(error)) {
                return error;
            }
        }
        return ValidationState.NO_ERRORS;
    }

    private boolean hasError(ValidationState state) {
        switch (state) {
            case IS_EMPTY:
                return isEmpty();
            case TRANSFORMER_CRITERIA_WRONG_FORMAT:
                return isWrongFormat();
            default:
                throw new IllegalArgumentException("'" + state + "' is unsupported '" + ValidationState.class.getSimpleName() + "'");
        }
    }

    private boolean isEmpty() {
        return criteriaValue == 0;
    }

    private boolean isWrongFormat() {
        return criteriaValue < MIN_CRITERIA_VALUE || criteriaValue > MAX_CRITERIA_VALUE;
    }
}
