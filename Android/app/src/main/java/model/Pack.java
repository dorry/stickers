package model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class Pack {

    private String identifier;
    private String name;
    private String publisher;
    private String tray_image_file;
    private String image_data_version;
    private String pack_logo;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getTray_image_file() {
        return tray_image_file;
    }

    public void setTray_image_file(String tray_image_file) {
        this.tray_image_file = tray_image_file;
    }

    public String getImage_data_version() {
        return image_data_version;
    }

    public void setImage_data_version(String image_data_version) {
        this.image_data_version = image_data_version;
    }

    public Bitmap getPack_logo() {
        byte[] decodedString = Base64.decode(pack_logo, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }

    public void setPack_logo(String pack_logo) {
        this.pack_logo = pack_logo;
    }



}
