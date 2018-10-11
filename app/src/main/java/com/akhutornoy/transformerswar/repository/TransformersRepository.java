package com.akhutornoy.transformerswar.repository;

import com.akhutornoy.transformerswar.repository.cache.TransformerEntity;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public interface TransformersRepository {
    Flowable<List<TransformerEntity>> getAllTransformers();

    Single<TransformerEntity>  addTransformer(TransformerEntity transformer);

    Single<TransformerEntity> updateTransformer(TransformerEntity transformer);

    Single<TransformerEntity> getTransformerById(String transformerId);

    Completable deleteTransformer(String transformerId);
}
