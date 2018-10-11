package com.akhutornoy.transformerswar.interactor.addedit;

import com.akhutornoy.transformerswar.repository.TransformersRepository;
import com.akhutornoy.transformerswar.repository.cache.TransformerEntity;

import io.reactivex.Single;

public class AddEditTransformerInteractor {
    private final TransformersRepository repository;

    public AddEditTransformerInteractor(TransformersRepository repository) {
        this.repository = repository;
    }

    public Single<TransformerEntity> addTransformer(TransformerEntity transformer) {
        return repository.addTransformer(transformer);
    }

    public Single<TransformerEntity> editTransformer(TransformerEntity transformer) {
        return repository.updateTransformer(transformer);
    }
}
