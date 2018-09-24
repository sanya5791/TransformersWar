package com.akhutornoy.transformerswar.ui.utils.validation.validator;

public class TransformerNameValidator implements Validator {
    private final String string;
    private final ValidationState[] validationStates;

    public TransformerNameValidator(String string, ValidationState... validationStates) {
        this.string = string;
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
            default:
                throw new IllegalArgumentException("'" + state + "' is unsupported '" + ValidationState.class.getSimpleName() + "'");
        }
    }

    private boolean isEmpty() {
        return string == null || string.isEmpty();
    }
}
