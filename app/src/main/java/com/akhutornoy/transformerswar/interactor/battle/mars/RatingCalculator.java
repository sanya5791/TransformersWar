package com.akhutornoy.transformerswar.interactor.battle.mars;

import com.akhutornoy.transformerswar.repository.cache.TransformerEntity;

public class RatingCalculator {
    public int calculate(TransformerEntity transformer) {
        return transformer.getStrength()
                + transformer.getIntelligence()
                + transformer.getSpeed()
                + transformer.getEndurance()
                + transformer.getFirepower();
    }
}
