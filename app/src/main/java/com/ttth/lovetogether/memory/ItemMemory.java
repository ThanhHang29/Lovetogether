package com.ttth.lovetogether.memory;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by HangPC on 5/18/2017.
 */

public class ItemMemory implements Parcelable {
    private int id;
    private String name, date, dateCome;
    private String uriImage;

    public ItemMemory(int id, String name, String date, String dateCome, String uriImage) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.dateCome = dateCome;
        this.uriImage = uriImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.date);
        dest.writeString(this.dateCome);
        dest.writeString(this.uriImage);
    }

    protected ItemMemory(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.date = in.readString();
        this.dateCome = in.readString();
        this.uriImage = in.readString();
    }

    public static final Parcelable.Creator<ItemMemory> CREATOR = new Parcelable.Creator<ItemMemory>() {
        @Override
        public ItemMemory createFromParcel(Parcel source) {
            return new ItemMemory(source);
        }

        @Override
        public ItemMemory[] newArray(int size) {
            return new ItemMemory[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getDateCome() {
        return dateCome;
    }

    public String getUriImage() {
        return uriImage;
    }

    @Override
    public String toString() {
        return "ItemMemory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", dateCome='" + dateCome + '\'' +
                ", uriImage='" + uriImage + '\'' +
                '}';
    }
}
