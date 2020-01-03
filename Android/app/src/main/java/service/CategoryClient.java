package service;

import java.util.List;

import model.Category;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CategoryClient {
    @GET("wa_stickers/CategoriesController.php?view=all")
    Call<List<Category>> allCategories();
}
