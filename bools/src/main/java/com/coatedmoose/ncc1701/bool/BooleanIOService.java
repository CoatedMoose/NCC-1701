package com.coatedmoose.ncc1701.bool;

import retrofit2.Call;
import retrofit2.http.*;

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

    @FormUrlEncoded
    @DELETE("{id}")
    Call<Void> deleteBool(@Path("id") String id);
}
