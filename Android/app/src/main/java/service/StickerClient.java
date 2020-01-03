package service;

import java.util.List;

import model.StickerModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface StickerClient {
    @GET("wa_stickers/StickersController.php")
    Call<List<StickerModel>> stickersForPack(@Query("id") String id);

}
