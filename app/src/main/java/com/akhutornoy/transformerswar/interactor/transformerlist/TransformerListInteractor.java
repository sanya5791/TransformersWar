package com.akhutornoy.transformerswar.interactor.transformerlist;

import android.support.annotation.NonNull;

import com.akhutornoy.transformerswar.repository.TransformersRepository;
import com.akhutornoy.transformerswar.repository.cache.TransformerEntity;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public class TransformerListInteractor {
    private final TransformersRepository repository;

    public TransformerListInteractor(TransformersRepository repository) {
        this.repository = repository;
    }

    public Flowable<List<TransformerEntity>> getTransformers() {
        return repository.getAllTransformers();
    }

    public Completable deleteTransformer(String transformerId) {
        return repository.deleteTransformer(transformerId);
    }

    public Single<TransformerEntity> getTransformerById(@NonNull String id) {
        return repository.getTransformerById(id);
    }
}
