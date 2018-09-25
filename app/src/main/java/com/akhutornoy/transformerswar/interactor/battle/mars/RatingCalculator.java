package com.akhutornoy.transformerswar.interactor.battle.mars;

import com.akhutornoy.transformerswar.repository.rest.dto.Transformer;

public class RatingCalculator {
    public int calculate(Transformer transformer) {
        return transformer.getStrength()
                + transformer.getIntelligence()
                + transformer.getSpeed()
                + transformer.getEndurance()
                + transformer.getFirepower();
    }
}
