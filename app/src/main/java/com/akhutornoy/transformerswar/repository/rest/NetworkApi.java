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
import retrofit2.http.Path;

public interface NetworkApi {

    @GET("/allspark")
    Single<String> getAllSpark();

    @GET("/transformers")
    Single<Transformers> getTransformers(@Header("Authorization") String allSpark);

    @POST("/transformers")
    Single<Transformer> postTransformer(@Header("Authorization") String allSpark, @Body Transformer transformer);

    @PUT("/transformers")
    Single<Transformer> updateTransformer(@Header("Authorization") String allSpark, @Body Transformer transformer);

    @GET("/transformers/{id}")
    Single<Transformer> getTransormer(@Header("Authorization") String allSpark, @Path("id") String transformerId);

    @DELETE("/transformers/{id}")
    Completable deleteTransformer(@Header("Authorization") String allSpark, @Path("id") String transformerId);
}
