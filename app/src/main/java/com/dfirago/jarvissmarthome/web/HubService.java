package com.dfirago.jarvissmarthome.web;

import com.dfirago.jarvissmarthome.web.model.ModulesResponse;
import com.dfirago.jarvissmarthome.web.model.RegisterModuleRequest;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by dmfi on 29/05/2017.
 */

public interface HubService {

    @GET("api/mobile/modules")
    Single<ModulesResponse> getModules();

    @GET("api/mobile/modules/registered")
    Single<ModulesResponse> getRegisteredModules();

    @POST("api/mobile/modules")
    Single<Void> registerModule(@Body RegisterModuleRequest request);
}
