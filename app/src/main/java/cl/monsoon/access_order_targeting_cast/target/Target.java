package cl.monsoon.access_order_targeting_cast.target;

import android.support.annotation.Nullable;

public interface Target {
    @Nullable
    String getXposedHookedAppPackage();

    String getSourceFileMimeType();
}
