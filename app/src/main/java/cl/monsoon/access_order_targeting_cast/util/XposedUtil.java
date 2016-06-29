package cl.monsoon.access_order_targeting_cast.util;

import android.content.Context;

import java.io.File;

public final class XposedUtil {
    // https://github.com/rovo89/XposedBridge/issues/56
    // http://forum.xda-developers.com/xposed/development/modeworldreadable-android-n-t3407123
    public static void setPreferenceFileWorldReadable(Context context, String preferenceName) {
        new File(context.getFilesDir().getParentFile(), "shared_prefs/" + preferenceName + ".xml")
                .setReadable(true, false);
    }
}
