package io.kaeawc.listviewcrashapp;

import android.content.res.Resources;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Colors {

    public static List<String> get(Resources resources) {

        String[] colors = resources.getStringArray(R.array.colors);
        List<String> strings = new ArrayList<>();
        Collections.addAll(strings, colors);

        return strings;
    }
}
