package com.akhutornoy.transformerswar.ui.transformerlist.addedit;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.akhutornoy.transformerswar.base.BaseViewModel;
import com.akhutornoy.transformerswar.interactor.transformerlist.addedit.AddTransformerInteractor;
import com.akhutornoy.transformerswar.repository.rest.dto.Transformer;
import com.akhutornoy.transformerswar.utils.RxUtils;
import com.akhutornoy.transformerswar.utils.validation.ValidationManager;
import com.akhutornoy.transformerswar.utils.validation.models.ValidationModel;
import com.akhutornoy.transformerswar.utils.validation.models.ValidationResult;

import java.util.List;

public class AddTransformerViewModel extends BaseViewModel {

    private final AddTransformerInteractor addTransformerInteractor;
    private final ValidationManager validationManager;

    private final MutableLiveData<Boolean> onTransformerAdded = new MutableLiveData<>();
    private final MutableLiveData<ValidationResult> onTransformerValidated = new MutableLiveData<>();

    public AddTransformerViewModel(AddTransformerInteractor addTransformerInteractor, ValidationManager validationManager) {
        this.addTransformerInteractor = addTransformerInteractor;
        this.validationManager = validationManager;
    }

    public LiveData<Boolean> getOnTransformerAdded() {
        return onTransformerAdded;
    }

    public void addTransformer(Transformer transformer) {
        autoUnsubscribe(
                addTransformerInteractor.addTransformer(transformer)
                        .compose(RxUtils.applySchedulersSingle())
                        .compose(RxUtils.applyProgressViewSingle(this))
                        .subscribe(
                                addedTransformer ->
                                        onTransformerAdded.setValue(true),
                                this::showError
                        )
        );
    }

    public LiveData<ValidationResult> getOnTransformerValidated() {
        return onTransformerValidated;
    }

    public void validate(List<ValidationModel> validationModels) {
        ValidationResult validationResult = validationManager.validate(validationModels);
        onTransformerValidated.setValue(validationResult);
    }
}
