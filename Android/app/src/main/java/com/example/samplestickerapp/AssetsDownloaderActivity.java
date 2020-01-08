package com.example.samplestickerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.snatik.storage.Storage;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Pack;
import model.StickerModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import service.ContentClient;
import service.PackClient;
import service.RetrofitBuilder;
import service.StickerClient;

public class AssetsDownloaderActivity extends AppCompatActivity {
    private Storage storage;
    public static String path;
    private Retrofit retrofit;
    private boolean isPackDownloadFinished;
    private boolean isContentDownloadFinished;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packs_downloader);

        retrofit = RetrofitBuilder.build();
        storage = new Storage(getApplicationContext());
        path = createAssetsFolder();
        isPackDownloadFinished = false;
        isContentDownloadFinished = false;
        //Getting the pressed category id
        Intent i = getIntent();
        String id = i.getStringExtra("id");

        try {
            isContentDownloadFinished = downloadContents(id);
            isPackDownloadFinished = downloadPacks(id);
        }
        catch (Exception e){
            Log.d("sdf", "exception: "+e.toString());
        }
//        Intent intent = new Intent(AssetsDownloaderActivity.this, EntryActivity.class);
//        if (isPackDownloadFinished &&isContentDownloadFinished )
//            startActivity(intent);

    }


    public String createAssetsFolder(){
        String path = getCacheDir() + "/Assets1/";
        storage.createDirectory(path, true);
        return path;
    }

    public boolean downloadPacks(String id){

        PackClient packClient = retrofit.create(PackClient.class);
        Call<List<Pack>> call = packClient.PackForCategory(id);

        call.enqueue(new Callback<List<Pack>>() {
            @Override
            public void onResponse(Call<List<Pack>> call, Response<List<Pack>> response) {
                List<Pack> packs = response.body();

                for (Pack pack:packs) {

                    String Packpath = path + pack.getIdentifier();
                    storage.createDirectory(Packpath, true);
                    Bitmap tray_icon = pack.getPack_logo();
                    tray_icon = Bitmap.createScaledBitmap(tray_icon, 96, 96, true);
                    FileOutputStream fos = null;
                    try {
                        fos = new FileOutputStream(Packpath + "/" + pack.getTray_image_file());
                        tray_icon.compress(Bitmap.CompressFormat.PNG, 100, fos);
                        fos.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

//                    storage.createFile(Packpath+"/"+pack.getTray_image_file(),pack.getPack_logo());
                    downloadStickers(pack);
                }
//                downloadContents(id);
            }

            @Override
            public void onFailure(Call<List<Pack>> call, Throwable t) {
                Toast.makeText(AssetsDownloaderActivity.this, "Error in packs", Toast.LENGTH_SHORT).show();
                Log.d("packs", "onFailure: "+ t.toString());
            }
        });
        return true;
    }

    public boolean downloadContents(String id){
        ContentClient contentClient = retrofit.create(ContentClient.class);
        Call<JsonObject> call = contentClient.Content(id);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject content = response.body();
                storage.createFile(path+"contents.json",content.toString());
//                Log.d("content", "onResponse:"+content.toString());
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(AssetsDownloaderActivity.this, "Error in content", Toast.LENGTH_SHORT).show();
                Log.d("Content", "onFailure: "+t.toString());
            }
        });
        return true;
    }

    public boolean downloadStickers(Pack pack){
        StickerClient stickerClient = retrofit.create(StickerClient.class);
        Call<List<StickerModel>> call = stickerClient.stickersForPack(pack.getIdentifier());

        call.enqueue(new Callback<List<StickerModel>>() {
            @Override
            public void onResponse(Call<List<StickerModel>> call, Response<List<StickerModel>> response) {
                List<StickerModel> stickers = response.body();
                if (stickers!= null){
                    for (StickerModel sticker:stickers) {
                        String Packpath = path + pack.getIdentifier();
                        Bitmap stickerBitmap = sticker.getSticker_image();
                        stickerBitmap = Bitmap.createScaledBitmap(stickerBitmap, 512, 512, true);

                        try {
                            FileOutputStream fos = new FileOutputStream(Packpath + "/" + sticker.getImage_file());
                            stickerBitmap.compress(Bitmap.CompressFormat.WEBP, 100, fos);
                            Log.d("d", "onResponse: "+stickerBitmap.getHeight());
                            fos.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    StickerContentProvider.instance.loadData();
                    Intent intent = new Intent(AssetsDownloaderActivity.this, EntryActivity.class);
                    startActivity(intent);
            }}

            @Override
            public void onFailure(Call<List<StickerModel>> call, Throwable t) {
                Toast.makeText(AssetsDownloaderActivity.this, "Error in Stickers", Toast.LENGTH_SHORT).show();
                Log.d("Sticker", "onFailure: "+t.toString());
            }
        });
        return true;
    }
}
