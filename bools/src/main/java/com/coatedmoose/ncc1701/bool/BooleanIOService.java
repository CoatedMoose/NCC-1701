package com.coatedmoose.ncc1701.bool;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * @author Andrew Crichton (andrew@coatedmoose.com).
 */
interface BooleanIOService {
    @POST("/")
    Call<BooleanIOBoolean> createBool();

    @FormUrlEncoded
    @POST("/")
    Call<BooleanIOBoolean> createBool(@Field("val") boolean value);

    @PUT("{id}")
    Call<BooleanIOBoolean> toggleBool(@Path("id") String id);

    @FormUrlEncoded
    @PUT("{id}")
    Call<BooleanIOBoolean> updateBool(@Path("id") String id, @Field("val") boolean value);

    @GET("{id}")
    Call<BooleanIOBoolean> getBool(@Path("id") String id);

    @DELETE("{id}")
    Call<Void> deleteBool(@Path("id") String id);
}
