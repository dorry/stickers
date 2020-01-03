package model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class StickerModel {
    private String id;
    private String image_file;
    private String sticker_image;
    private String pack_id;

    public String getId() {
        return id;
    }

    public String getImage_file() {
        return image_file;
    }

    public Bitmap getSticker_image() {
        byte[] decodedString = Base64.decode(sticker_image, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;

    }

    public String getPack_id() {
        return pack_id;
    }
}
