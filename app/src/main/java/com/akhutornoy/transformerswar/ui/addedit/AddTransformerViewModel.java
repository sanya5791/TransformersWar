package com.akhutornoy.transformerswar.ui.addedit;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.akhutornoy.transformerswar.base.BaseViewModel;
import com.akhutornoy.transformerswar.interactor.addedit.AddEditTransformerInteractor;
import com.akhutornoy.transformerswar.repository.rest.dto.Transformer;
import com.akhutornoy.transformerswar.utils.RxUtils;
import com.akhutornoy.transformerswar.ui.utils.validation.ValidationManager;
import com.akhutornoy.transformerswar.ui.utils.validation.models.ValidationModel;
import com.akhutornoy.transformerswar.ui.utils.validation.models.ValidationResult;

import java.util.List;

import io.reactivex.Single;

public class AddTransformerViewModel extends BaseViewModel {

    protected final AddEditTransformerInteractor addEditTransformerInteractor;
    private final ValidationManager validationManager;

    private final MutableLiveData<Boolean> onTransformerAdded = new MutableLiveData<>();
    private final MutableLiveData<ValidationResult> onTransformerValidated = new MutableLiveData<>();

    public AddTransformerViewModel(AddEditTransformerInteractor addEditTransformerInteractor, ValidationManager validationManager) {
        this.addEditTransformerInteractor = addEditTransformerInteractor;
        this.validationManager = validationManager;
    }

    public LiveData<Boolean> getOnTransformerAdded() {
        return onTransformerAdded;
    }

    public void addTransformer(Transformer transformer) {
        autoUnsubscribe(
                getAddTransformerObservable(transformer)
                        .compose(RxUtils.applySchedulersSingle())
                        .compose(RxUtils.applyProgressViewSingle(this))
                        .subscribe(
                                addedTransformer ->
                                        onTransformerAdded.setValue(true),
                                this::showError
                        )
        );
    }

    protected Single<Transformer> getAddTransformerObservable(Transformer transformer) {
        return addEditTransformerInteractor.addTransformer(transformer);
    }

    public LiveData<ValidationResult> getOnTransformerValidated() {
        return onTransformerValidated;
    }

    public void validate(List<ValidationModel> validationModels) {
        ValidationResult validationResult = validationManager.validate(validationModels);
        onTransformerValidated.setValue(validationResult);
    }
}
