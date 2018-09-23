package com.akhutornoy.transformerswar.utils.validation.models;

import com.akhutornoy.transformerswar.utils.validation.validator.Validator;

public class ValidationModel {
    private final int id;
    private final Validator validator;
    private final boolean isMandatory;

    public ValidationModel(int id, Validator validator, boolean isMandatory) {
        this.id = id;
        this.validator = validator;
        this.isMandatory = isMandatory;
    }

    public int getId() {
        return id;
    }

    public Validator getValidator() {
        return validator;
    }

    public boolean isMandatory() {
        return isMandatory;
    }
}
