package com.dfirago.jarvissmarthome.utils;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by dmfi on 20/05/2017.
 */

public class DataBindingUtils {

    @BindingAdapter("signalLevel")
    public static void loadSignalLevelIcon(ImageView imageView, int signalLevel) {
        if (imageView != null) {
            if (signalLevel >= 0 && signalLevel <= 4) {
                Picasso
                        .with(imageView.getContext())
                        .load(Constants.SIGNAL_LEVEL_DRAWABLES
                                .get(signalLevel))
                        .into(imageView);
            }
        }
    }
}
