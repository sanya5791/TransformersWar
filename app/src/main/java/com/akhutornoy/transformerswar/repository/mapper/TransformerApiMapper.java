package com.akhutornoy.transformerswar.repository.mapper;

import com.akhutornoy.transformerswar.base.Mapper;
import com.akhutornoy.transformerswar.repository.cache.TransformerEntity;
import com.akhutornoy.transformerswar.repository.rest.dto.Transformer;

public class TransformerApiMapper extends Mapper<Transformer, TransformerEntity> {

    @Override
    public TransformerEntity map(Transformer transformerApi) {
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
                transformerApi.getTeam_icon());
    }

    @Override
    public Transformer mapInverse(TransformerEntity transformer) {
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
}
