package cl.monsoon.access_order_targeting_cast.util;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.provider.DocumentFile;

import javax.inject.Inject;

import cl.monsoon.access_order_targeting_cast.target.TargetUriPermissionGrantHelper;

public final class UriHelperImpl implements UriHelper {
    private final Context context;
    private final ContentResolver contentResolver;
    private final TargetUriPermissionGrantHelper targetUriPermissionGrantHelper;

    @Inject
    public UriHelperImpl(Context context, ContentResolver contentResolver,
                         TargetUriPermissionGrantHelper targetUriPermissionGrantHelper) {
        this.context = context;
        this.contentResolver = contentResolver;
        this.targetUriPermissionGrantHelper = targetUriPermissionGrantHelper;
    }

    @Override
    @Nullable
    public String getUriDisplayName(Uri uri) {
        String name = DocumentFile.fromSingleUri(context, uri).getName();
        if (name != null) {
            return name;
        } else {
            return DocumentFile.fromTreeUri(context, uri).getName();
        }
    }

    @Override
    public void takePersistableUriPermission(Uri uri) {
        int takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                | Intent.FLAG_GRANT_WRITE_URI_PERMISSION;
        contentResolver.takePersistableUriPermission(uri, takeFlags);
        targetUriPermissionGrantHelper.grantUriPermission(uri);
    }

    @Override
    public void releasePreviousPersistableUriPermission(Uri uri) {
        int takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                | Intent.FLAG_GRANT_WRITE_URI_PERMISSION;
        targetUriPermissionGrantHelper.revokeUriPermission(uri);

        try {
            contentResolver.releasePersistableUriPermission(uri, takeFlags);
        } catch (SecurityException ignored) {
            // we can not release permission when app restarts
        }
    }

    @Override
    public void releaseOldPersistableUriPermissionIfNeeded(@Nullable Uri oldUri, Uri newUri) {
        if (oldUri != null && !oldUri.equals(newUri)) {
            releasePreviousPersistableUriPermission(oldUri);
        }
    }
}
