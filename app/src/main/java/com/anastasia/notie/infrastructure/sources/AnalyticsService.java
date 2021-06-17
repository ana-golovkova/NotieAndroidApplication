package com.anastasia.notie.infrastructure.sources;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AnalyticsService {
    @POST("events")
    void postEvents(@Body List<String> events);
}
