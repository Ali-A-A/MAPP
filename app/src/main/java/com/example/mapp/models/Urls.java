package com.example.mapp.models;

import com.google.gson.annotations.SerializedName;

public class Urls {
    @SerializedName("raw")
    public String getRaw() {
        return this.raw;
    }
    public void setRaw(String raw) {
        this.raw = raw;
    }
    String raw;
    @SerializedName("full")
    public String getFull() {
        return this.full;
    }
    public void setFull(String full) {
        this.full = full;
    }
    String full;
    @SerializedName("regular")
    public String getRegular() {
        return this.regular;
    }
    public void setRegular(String regular) {
        this.regular = regular;
    }
    String regular;
    @SerializedName("small")
    public String getSmall() {
        return this.small;
    }
    public void setSmall(String small) {
        this.small = small;
    }
    String small;
    @SerializedName("thumb")
    public String getThumb() {
        return this.thumb;
    }
    public void setThumb(String thumb) {
        this.thumb = thumb;
    }
    String thumb;
}
