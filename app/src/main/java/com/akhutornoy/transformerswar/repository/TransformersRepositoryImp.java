package com.akhutornoy.transformerswar.repository;

import com.akhutornoy.transformerswar.repository.base.CachedNetworkResource;
import com.akhutornoy.transformerswar.repository.cache.TransformerDao;
import com.akhutornoy.transformerswar.repository.cache.TransformerEntity;
import com.akhutornoy.transformerswar.repository.cache.ValidationDao;
import com.akhutornoy.transformerswar.repository.cache.ValidationEntity;
import com.akhutornoy.transformerswar.repository.mapper.TransformerApiMapper;
import com.akhutornoy.transformerswar.repository.rest.NetworkApi;
import com.akhutornoy.transformerswar.repository.rest.dto.Transformer;
import com.akhutornoy.transformerswar.repository.rest.dto.Transformers;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public class TransformersRepositoryImp implements TransformersRepository {
    private final AllSparkProvider allSparkProvider;
    private final NetworkApi api;
    private final TransformerDao transformerDao;
    private final ValidationDao validationDao;
    private final TransformerApiMapper apiMapper;

    public TransformersRepositoryImp(AllSparkProvider allSparkProvider, NetworkApi api, TransformerDao transformerDao, ValidationDao validationDao, TransformerApiMapper apiMapper) {
        this.allSparkProvider = allSparkProvider;
        this.api = api;
        this.transformerDao = transformerDao;
        this.validationDao = validationDao;
        this.apiMapper = apiMapper;
    }

    @Override
    public Flowable<List<TransformerEntity>> getAllTransformers() {
        return new CachedNetworkResource<List<TransformerEntity>, List<Transformer>>() {
            private final String VALIDATION_ID = ValidationDao.Id.TRANSFORMERS.name();

            @Override
            protected Flowable<List<TransformerEntity>> getFromCache() {
                return transformerDao.getAll();
            }

            @Override
            protected Single<Boolean> isCacheValid(List<TransformerEntity> transformerEntities) {
                if (transformerEntities == null
                        || transformerEntities.isEmpty()) {
                    return Single.just(false);
                }
                ValidationEntity validationEntity = validationDao.get(VALIDATION_ID);
                if (validationEntity == null
                        || !(validationEntity.isValid())) {
                    return Single.just(false);
                }
                return Single.just(true);
            }

            @Override
            protected Completable setCacheValidationStateOnApiReceived() {
                ValidationEntity validation = new ValidationEntity(VALIDATION_ID, true);
                return Completable.fromAction(() -> validationDao.put(validation));
            }

            @Override
            protected Single<List<Transformer>> getApiData() {
                return getAllStark()
                        .flatMap(api::getTransformers)
                        .map(Transformers::getTransformers);
            }

            @Override
            protected List<TransformerEntity> mapApiDataToCacheData(List<Transformer> transformers) {
                return apiMapper.map(transformers);
            }

            @Override
            protected Completable insertToCache(List<TransformerEntity> transformerEntities) {
                return Completable.fromAction(() -> {
                    transformerDao.deleteAll();
                    transformerDao.insert(transformerEntities);
                });
            }
        }.getData();
    }

    @Override
    public Single<TransformerEntity> addTransformer(TransformerEntity transformer) {
        Transformer transformerApi = apiMapper.mapInverse(transformer);
        return getAllStark()
                .flatMap(allSpark -> api.postTransformer(allSpark, transformerApi))
                .doOnSuccess(ignored -> invalidateTransformersCache())
                .map(apiMapper::map);
    }

    @Override
    public Single<TransformerEntity> updateTransformer(TransformerEntity transformer) {
        Transformer transformerApi = apiMapper.mapInverse(transformer);
        return getAllStark()
                .flatMap(allSpark -> api.updateTransformer(allSpark, transformerApi))
                .doOnSuccess(ignored -> invalidateTransformersCache())
                .map(apiMapper::map);
    }

    @Override
    public Single<TransformerEntity> getTransformerById(String transformerId) {
        return refreshTransformers()
                .andThen(transformerDao.getById(transformerId));
    }

    private Completable refreshTransformers() {
        return getAllTransformers()
                .take(1)
                .ignoreElements();
    }

    @Override
    public Completable deleteTransformer(String transformerId) {
        return getAllStark()
                .flatMapCompletable(allSpark -> api.deleteTransformer(allSpark, transformerId))
                .doOnComplete(this::invalidateTransformersCache);

    }

    private void invalidateTransformersCache() {
        ValidationEntity validation = new ValidationEntity(ValidationDao.Id.TRANSFORMERS.name(), false);
        validationDao.put(validation);
    }

    protected Single<String> getAllStark() {
        return allSparkProvider.getAllSpark();
    }

}
