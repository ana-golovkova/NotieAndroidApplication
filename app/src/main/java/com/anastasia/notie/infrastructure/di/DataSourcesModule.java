package com.anastasia.notie.infrastructure.di;

import android.app.Application;

import com.anastasia.notie.NotieApplication;
import com.anastasia.notie.infrastructure.sources.AnalyticsService;
import com.anastasia.notie.infrastructure.sources.NotieService;
import com.chuckerteam.chucker.api.ChuckerInterceptor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class DataSourcesModule {

    @Provides
    @Singleton
    public NotieService provideNotieService(Application context) {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(new ChuckerInterceptor.Builder(context).build()).build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("https://notie-fifnnkcglq-ts.a.run.app/")
                .build();

        return retrofit.create(NotieService.class);
    }

    @Provides
    @Singleton
    public AnalyticsService provideAnalyticsService() {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(new ChuckerInterceptor.Builder(NotieApplication.getContext()).build()).build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("https://notie-fifnnkcglq-ts.a.run.app/")
                .build();

        return retrofit.create(AnalyticsService.class);
    }
}
