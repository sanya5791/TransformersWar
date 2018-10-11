package com.akhutornoy.transformerswar.di.app.interactor;

import com.akhutornoy.transformerswar.interactor.addedit.AddEditTransformerInteractor;
import com.akhutornoy.transformerswar.interactor.battle.BattleInteractor;
import com.akhutornoy.transformerswar.interactor.battle.mars.BattleInitializer;
import com.akhutornoy.transformerswar.interactor.battle.mars.Mars;
import com.akhutornoy.transformerswar.interactor.battle.mars.RatingCalculator;
import com.akhutornoy.transformerswar.interactor.battle.mars.TransformersArena;
import com.akhutornoy.transformerswar.interactor.transformerlist.TransformerListInteractor;
import com.akhutornoy.transformerswar.repository.TransformersRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class InteractorsModule {
    @Provides
    public TransformerListInteractor provideTransformerListInteractor(TransformersRepository repository) {
        return new TransformerListInteractor(repository);
    }

    @Provides
    public AddEditTransformerInteractor provideAddEditTransformerInteractor(TransformersRepository repository) {
        return new AddEditTransformerInteractor(repository);
    }

    @Provides
    public BattleInteractor provideBattleInteractor(TransformersRepository repository, Mars mars) {
        return new BattleInteractor(repository, mars);
    }

    @Provides
    public Mars provideMars() {
        BattleInitializer battleInitializer = new BattleInitializer();
        RatingCalculator calculator = new RatingCalculator();
        TransformersArena arena = new TransformersArena(calculator);
        return new Mars(battleInitializer, arena);
    }

}
