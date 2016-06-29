package cl.monsoon.access_order_targeting_cast.target;

import android.support.annotation.Nullable;

public final class TargetImpl implements Target {
    @Nullable
    @Override
    public String getXposedHookedAppPackage() {
        return null;
    }

    @Override
    public String getSourceFileMimeType() {
        // see http://stackoverflow.com/a/1176031/2331527
        return "application/octet-stream";
    }
}
