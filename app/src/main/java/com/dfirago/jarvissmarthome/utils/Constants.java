package com.dfirago.jarvissmarthome.utils;

import com.dfirago.jarvissmarthome.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dmfi on 01/05/2017.
 */

public interface Constants {

    String HUB_NAME_PREFIX = "JSH_HUB";

    String API_URL = "http://172.24.1.1";

    int SIGNAL_LEVELS = 5;

    List<Integer> SIGNAL_LEVEL_DRAWABLES = new ArrayList<Integer>(SIGNAL_LEVELS) {{
        add(R.drawable.ic_signal_cellular_0_bar_black_36dp);
        add(R.drawable.ic_signal_cellular_1_bar_black_36dp);
        add(R.drawable.ic_signal_cellular_2_bar_black_36dp);
        add(R.drawable.ic_signal_cellular_3_bar_black_36dp);
        add(R.drawable.ic_signal_cellular_4_bar_black_36dp);
    }};

}
