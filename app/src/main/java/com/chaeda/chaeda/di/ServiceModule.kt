package com.chaeda.chaeda.di

import com.chaeda.data.service.ImageService
import com.chaeda.data.service.SampleService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Singleton
    @Provides
    fun provideSampleService(@Named("ChaedaRetrofit") chaedaRetrofit: Retrofit) =
        chaedaRetrofit.create(SampleService::class.java)

    @Singleton
    @Provides
    fun provideImageService(@Named("ImageRetrofit") imageRetrofit: Retrofit) =
        imageRetrofit.create(ImageService::class.java)
}