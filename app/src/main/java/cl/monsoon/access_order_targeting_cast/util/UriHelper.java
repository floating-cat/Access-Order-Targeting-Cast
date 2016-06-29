package cl.monsoon.access_order_targeting_cast.util;

import android.net.Uri;
import android.support.annotation.Nullable;

public interface UriHelper {
    @Nullable
    String getUriDisplayName(Uri uri);

    void takePersistableUriPermission(Uri uri);

    void releasePreviousPersistableUriPermission(Uri uri);

    void releaseOldPersistableUriPermissionIfNeeded(@Nullable Uri oldUri, Uri newUri);
}
