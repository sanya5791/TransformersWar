package com.akhutornoy.transformerswar.utils.validation.models;

import com.akhutornoy.transformerswar.utils.validation.validator.ValidationState;

import java.util.List;

public class ValidationResult {
    private final boolean isValid;
    private final List<Result> results;

    public ValidationResult(boolean isValid, List<Result> results) {
        this.isValid = isValid;
        this.results = results;
    }

    public boolean isValid() {
        return isValid;
    }

    public List<Result> getResults() {
        return results;
    }

    public static final class Result {
        private final int id;
        private final ValidationState state;

        public Result(int id, ValidationState state) {
            this.id = id;
            this.state = state;
        }

        public int getId() {
            return id;
        }

        public ValidationState getState() {
            return state;
        }
    }
}
