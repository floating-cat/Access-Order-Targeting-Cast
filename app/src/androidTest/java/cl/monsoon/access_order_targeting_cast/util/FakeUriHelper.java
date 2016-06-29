package cl.monsoon.access_order_targeting_cast.util;

import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.Nullable;

import javax.inject.Inject;

/**
 * Use a fake {@link UriHelper} because we can't get any
 * real {@link Uri} for {@link ContentResolver} in testing.
 */
public final class FakeUriHelper implements UriHelper {
    @Inject
    public FakeUriHelper() {
    }

    @Nullable
    @Override
    public String getUriDisplayName(Uri uri) {
        String uriString = uri.toString();
        return uriString.substring(uriString.lastIndexOf('\\') + 1);
    }

    @Override
    public void takePersistableUriPermission(Uri uri) {
        // do nothing
    }

    @Override
    public void releasePreviousPersistableUriPermission(Uri uri) {
        // do nothing
    }

    @Override
    public void releaseOldPersistableUriPermissionIfNeeded(@Nullable Uri oldUri, Uri newUri) {
        // do nothing
    }
}
