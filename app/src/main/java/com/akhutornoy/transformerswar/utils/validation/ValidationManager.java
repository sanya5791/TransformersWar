package com.akhutornoy.transformerswar.utils.validation;

import com.akhutornoy.transformerswar.utils.validation.models.ValidationModel;
import com.akhutornoy.transformerswar.utils.validation.models.ValidationResult;
import com.akhutornoy.transformerswar.utils.validation.models.ValidationResult.Result;
import com.akhutornoy.transformerswar.utils.validation.validator.ValidationState;

import java.util.ArrayList;
import java.util.List;

import static com.akhutornoy.transformerswar.utils.validation.validator.ValidationState.NO_ERRORS;

public class ValidationManager {

    public ValidationResult validate(List<ValidationModel> validations) {
        List<Result> results = new ArrayList<>();
        for (ValidationModel validation : validations) {
            if (validation.isMandatory()) {
                ValidationState state = validation.getValidator().validate();
                results.add(new Result(validation.getId(), state));
            }
        }
        return new ValidationResult(isValid(results), results);
    }

    public boolean isValid(List<Result> results) {
        for (Result result : results) {
            if (result.getState() != NO_ERRORS) {
                return false;
            }
        }
        return true;
    }
}
