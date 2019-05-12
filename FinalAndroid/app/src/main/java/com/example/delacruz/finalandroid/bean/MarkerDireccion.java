package com.example.delacruz.finalandroid.bean;

import com.google.android.gms.maps.model.LatLng;

public class MarkerDireccion {
    String title;
    String snippet;
    LatLng latLng;

    public MarkerDireccion(String title, String snippet, LatLng latLng) {
        this.title = title;
        this.snippet = snippet;
        this.latLng = latLng;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }
}
