package com.akhutornoy.transformerswar.di.app.interactor;

import com.akhutornoy.transformerswar.interactor.battle.BattleInteractor;
import com.akhutornoy.transformerswar.interactor.battle.mars.BattleInitializer;
import com.akhutornoy.transformerswar.interactor.battle.mars.Mars;
import com.akhutornoy.transformerswar.interactor.battle.mars.RatingCalculator;
import com.akhutornoy.transformerswar.interactor.battle.mars.TransformersArena;
import com.akhutornoy.transformerswar.interactor.allspark.AllSparkProvider;
import com.akhutornoy.transformerswar.repository.cache.ValidationDao;
import com.akhutornoy.transformerswar.repository.rest.NetworkApi;

import dagger.Module;
import dagger.Provides;

@Module
public class BattleInteractorModule {
    @Provides
    public BattleInteractor provideBattleInteractor(
            AllSparkProvider allSparkProvider, NetworkApi networkApi, Mars mars, ValidationDao validationDao) {
        return new BattleInteractor(allSparkProvider, networkApi, mars, validationDao);
    }

    @Provides
    public Mars provideMars() {
        BattleInitializer battleInitializer = new BattleInitializer();
        RatingCalculator calculator = new RatingCalculator();
        TransformersArena arena = new TransformersArena(calculator);
        return new Mars(battleInitializer, arena);
    }
}
