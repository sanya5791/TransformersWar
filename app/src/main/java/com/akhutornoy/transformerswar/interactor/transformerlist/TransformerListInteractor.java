package com.akhutornoy.transformerswar.interactor.transformerlist;

import android.support.annotation.NonNull;

import com.akhutornoy.transformerswar.base.BaseInteractor;
import com.akhutornoy.transformerswar.interactor.allspark.AllSparkProvider;
import com.akhutornoy.transformerswar.interactor.base.CachedNetworkResource;
import com.akhutornoy.transformerswar.repository.cache.TransformerDao;
import com.akhutornoy.transformerswar.repository.cache.TransformerEntity;
import com.akhutornoy.transformerswar.repository.cache.ValidationDao;
import com.akhutornoy.transformerswar.repository.cache.ValidationEntity;
import com.akhutornoy.transformerswar.repository.rest.NetworkApi;
import com.akhutornoy.transformerswar.repository.rest.dto.Transformer;
import com.akhutornoy.transformerswar.repository.rest.dto.Transformers;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public class TransformerListInteractor extends BaseInteractor {
    private final NetworkApi api;
    private final TransformerDao transformerDao;
    private final ValidationDao validationDao;

    public TransformerListInteractor(AllSparkProvider allSparkProvider, NetworkApi api, TransformerDao transformerDao, ValidationDao validationDao) {
        super(allSparkProvider);
        this.api = api;
        this.transformerDao = transformerDao;
        this.validationDao = validationDao;
    }

    public Flowable<List<TransformerEntity>> getTransformers() {
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
                return mapToTransformerEntities(transformers);
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

    private List<TransformerEntity> mapToTransformerEntities(List<Transformer> transformers) {
        List<TransformerEntity> result = new ArrayList<>(transformers.size());
        for (Transformer transformer : transformers) {
            result.add(mapToTransformerEntity(transformer));
        }
        return result;
    }

    private TransformerEntity mapToTransformerEntity(Transformer source) {
        return new TransformerEntity(
                source.getId(),
                source.getName(),
                source.getTeam(),
                source.getStrength(),
                source.getIntelligence(),
                source.getSpeed(),
                source.getEndurance(),
                source.getRank(),
                source.getCourage(),
                source.getFirepower(),
                source.getSkill(),
                source.getTeam_icon()
        );
    }

    public Completable deleteTransformer(String transformerId) {
        return getAllStark()
                .flatMapCompletable(allSpark -> api.deleteTransformer(allSpark, transformerId));
    }

    public Single<TransformerEntity> getTransformerById(@NonNull String id) {

        return refreshTransformers()
                .andThen(transformerDao.getById(id));
    }

    private Completable refreshTransformers() {
        return getTransformers()
                .take(1)
                .ignoreElements();
    }
}
