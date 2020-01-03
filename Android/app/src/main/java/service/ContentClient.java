package service;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ContentClient {
    @GET("wa_stickers/ContentsController.php")
    Call<JsonObject> Content(@Query("id") String id);
}
