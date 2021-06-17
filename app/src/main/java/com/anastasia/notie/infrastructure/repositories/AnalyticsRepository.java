package com.anastasia.notie.infrastructure.repositories;

import com.anastasia.notie.NotieApplication;
import com.anastasia.notie.infrastructure.sources.AnalyticsService;
import com.anastasia.notie.infrastructure.sources.NotieService;
import com.chuckerteam.chucker.api.ChuckerInterceptor;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class AnalyticsRepository {
    private LinkedList<String> eventsQueue = new LinkedList<>();
    private final Executor executor = Executors.newSingleThreadExecutor();
    private final AnalyticsService analyticsService;


    public AnalyticsRepository() {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(new ChuckerInterceptor.Builder(NotieApplication.getContext()).build()).build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("https://notie-fifnnkcglq-ts.a.run.app/")
                .build();

        analyticsService = retrofit.create(AnalyticsService.class);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(30000);
                        drainEvents();
                    } catch (InterruptedException exception) {

                    }
                }
            }
        });
    }

    public void recordEvent(String event) {
        eventsQueue.push(event);
    }

    private void drainEvents() {
        List<String> events = new ArrayList<>();
        while (!eventsQueue.isEmpty()) {
            events.add(eventsQueue.pop());
        }
        if (!events.isEmpty()) {
            analyticsService.postEvents(events);
        }
    }
}
