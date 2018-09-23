package com.akhutornoy.transformerswar.ui.transformerlist.addedit;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.akhutornoy.transformerswar.base.BaseViewModel;
import com.akhutornoy.transformerswar.interactor.transformerlist.addedit.AddTransformerInteractor;
import com.akhutornoy.transformerswar.repository.rest.dto.Transformer;
import com.akhutornoy.transformerswar.utils.RxUtils;

public class AddTransformerViewModel extends BaseViewModel {

    private final AddTransformerInteractor addTransformerInteractor;
    private final MutableLiveData<Boolean> onTransformerAdded = new MutableLiveData<>();

    public AddTransformerViewModel(AddTransformerInteractor addTransformerInteractor) {
        this.addTransformerInteractor = addTransformerInteractor;
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
}
