package com.jaysonstaff.staff.adapter;

import android.net.Uri;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class UriTypeAdapter extends TypeAdapter<Uri> {
    @Override
    public void write(JsonWriter out, Uri value) throws IOException {
        if (value != null) {
            out.value(value.toString());
        } else {
            out.nullValue();
        }
    }

    @Override
    public Uri read(JsonReader in) throws IOException {
        String uriString = in.nextString();
        if (uriString != null) {
            return Uri.parse(uriString);
        } else {
            return null;
        }
    }
}

