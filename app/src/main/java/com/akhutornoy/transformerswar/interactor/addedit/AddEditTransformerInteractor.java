package com.akhutornoy.transformerswar.interactor.addedit;

import com.akhutornoy.transformerswar.base.BaseInteractor;
import com.akhutornoy.transformerswar.interactor.allspark.AllSparkProvider;
import com.akhutornoy.transformerswar.repository.cache.TransformerEntity;
import com.akhutornoy.transformerswar.repository.cache.ValidationDao;
import com.akhutornoy.transformerswar.repository.cache.ValidationEntity;
import com.akhutornoy.transformerswar.repository.rest.NetworkApi;
import com.akhutornoy.transformerswar.repository.rest.dto.Transformer;

import io.reactivex.Single;

public class AddEditTransformerInteractor extends BaseInteractor {
    private final NetworkApi api;
    private final ValidationDao validationDao;

    public AddEditTransformerInteractor(AllSparkProvider allSparkProvider, NetworkApi api, ValidationDao validationDao) {
        super(allSparkProvider);
        this.api = api;
        this.validationDao = validationDao;
    }

    public Single<TransformerEntity> addTransformer(TransformerEntity transformer) {
        Transformer transformerApi = mapToTransformerApi(transformer);
        return getAllStark()
                .flatMap(allSpark -> api.postTransformer(allSpark, transformerApi))
                .doOnSuccess(ignored -> invalidateCacheTransformers())
                .map(this::mapToTransformerEntity);
    }

    public Single<TransformerEntity> editTransformer(TransformerEntity transformer) {
        Transformer transformerApi = mapToTransformerApi(transformer);
        return getAllStark()
                .flatMap(allSpark -> api.updateTransformer(allSpark, transformerApi))
                .doOnSuccess(ignored -> invalidateCacheTransformers())
                .map(this::mapToTransformerEntity);
    }

    private void invalidateCacheTransformers() {
        validationDao.put(new ValidationEntity(ValidationDao.Id.TRANSFORMERS.name(), false));
    }

    private Transformer mapToTransformerApi(TransformerEntity transformer) {
        return new Transformer.Builder()
                .setId(transformer.getId())
                .setName(transformer.getName())
                .setTeam(transformer.getTeam())
                .setStrength(transformer.getStrength())
                .setIntelligence(transformer.getIntelligence())
                .setSpeed(transformer.getSpeed())
                .setEndurance(transformer.getEndurance())
                .setRank(transformer.getRank())
                .setCourage(transformer.getCourage())
                .setFirepower(transformer.getFirepower())
                .setSkill(transformer.getSkill())
                .build();
    }

    private TransformerEntity mapToTransformerEntity(Transformer transformerApi) {
        return new TransformerEntity(
                transformerApi.getId(),
                transformerApi.getName(),
                transformerApi.getTeam(),
                transformerApi.getStrength(),
                transformerApi.getIntelligence(),
                transformerApi.getSpeed(),
                transformerApi.getEndurance(),
                transformerApi.getRank(),
                transformerApi.getCourage(),
                transformerApi.getFirepower(),
                transformerApi.getSkill(),
                transformerApi.getTeam_icon()
        );
    }
}
