package com.example.novelty;

import android.net.Uri;

import java.io.Serializable;

public class Photograph implements Serializable {

    private Uri photoUri;

    public Photograph() {
        this.photoUri = null;
    }

    public Photograph(Uri photoUri) {
        this.photoUri = photoUri;
    }

    public Uri getPhoto() {
        return photoUri;
    }

    public void setPhoto(Uri photoUri) {
        this.photoUri = photoUri;
    }
}

