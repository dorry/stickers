package service;

import java.util.List;

import model.Category;
import model.Pack;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PackClient {
    @GET("wa_stickers/PacksController.php")
    Call<List<Pack>> PackForCategory(@Query("id") String id);

}
