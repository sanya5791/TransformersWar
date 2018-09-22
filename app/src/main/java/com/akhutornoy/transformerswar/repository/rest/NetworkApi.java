package com.akhutornoy.transformerswar.repository.rest;

import com.akhutornoy.transformerswar.repository.rest.dto.Transformer;
import com.akhutornoy.transformerswar.repository.rest.dto.Transformers;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface NetworkApi {

    @GET("/allspark")
    Single<String> getAllSpark();

    @GET("/transformers")
    Single<Transformers> getTransformers(@Header("Authorization") String allSpark);

    @POST("/transformers")
    Single<Transformer> postTransformer(@Header("Authorization") String allSpark, @Body Transformer transformer);

    @PUT("/transformers")
    Single<Transformer> updateTransformer(@Header("Authorization") String allSpark, @Body Transformer transformer);

    @GET("/transformers")
    Single<Transformer> getTransormer(@Header("Authorization") String allSpark, String transformerId);

    @DELETE("/transformers")
    Completable deleteTransormer(@Header("Authorization") String allSpark, String transformerId);
}
