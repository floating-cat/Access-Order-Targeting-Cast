package cl.monsoon.access_order_targeting_cast.target;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public final class TargetUriPermissionGrantHelper {
    private final Context context;

    private final Target target;

    public TargetUriPermissionGrantHelper(Context context, Target target) {
        this.context = context;
        this.target = target;
    }

    public void grantUriPermission(Uri uri) {
        if (target.getXposedHookedAppPackage() != null) {
            int takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                    | Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                    | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION
                    | Intent.FLAG_GRANT_PREFIX_URI_PERMISSION;
            context.grantUriPermission(target.getXposedHookedAppPackage(), uri, takeFlags);
        }
    }

    public void revokeUriPermission(Uri uri) {
        if (target.getXposedHookedAppPackage() != null) {
            int takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                    | Intent.FLAG_GRANT_WRITE_URI_PERMISSION;
            context.revokeUriPermission(uri, takeFlags);
        }
    }
}
