package com.akhutornoy.transformerswar.di.app.repository;

import com.akhutornoy.transformerswar.repository.AllSparkProvider;
import com.akhutornoy.transformerswar.repository.TransformersRepository;
import com.akhutornoy.transformerswar.repository.TransformersRepositoryImp;
import com.akhutornoy.transformerswar.repository.cache.TransformerDao;
import com.akhutornoy.transformerswar.repository.cache.ValidationDao;
import com.akhutornoy.transformerswar.repository.mapper.TransformerApiMapper;
import com.akhutornoy.transformerswar.repository.rest.NetworkApi;

import dagger.Module;
import dagger.Provides;

@Module(includes = {AllSparkProviderModule.class})
public class TransformerRepositoryModule {
    @Provides
    public TransformersRepository provideTransformersRepository(AllSparkProvider allSparkProvider, NetworkApi networkApi, TransformerDao transformerDao, ValidationDao validationDao, TransformerApiMapper apiMapper) {
        return new TransformersRepositoryImp(allSparkProvider, networkApi, transformerDao, validationDao, apiMapper);
    }

    @Provides
    public TransformerApiMapper providesTransformerApiMapper() {
        return new TransformerApiMapper();
    }
}
