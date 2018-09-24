package com.akhutornoy.transformerswar.ui.transformerlist.addedit;

import com.akhutornoy.transformerswar.interactor.transformerlist.addedit.AddEditTransformerInteractor;
import com.akhutornoy.transformerswar.repository.rest.dto.Transformer;
import com.akhutornoy.transformerswar.utils.validation.ValidationManager;

import io.reactivex.Single;

public class EditTransformerViewModel extends AddTransformerViewModel {

    public EditTransformerViewModel(AddEditTransformerInteractor addEditTransformerInteractor,
                                    ValidationManager validationManager) {
        super(addEditTransformerInteractor, validationManager);
    }

    @Override
    protected Single<Transformer> getAddTransformerObservable(Transformer transformer) {
        return addEditTransformerInteractor.editTransformer(transformer);
    }
}
