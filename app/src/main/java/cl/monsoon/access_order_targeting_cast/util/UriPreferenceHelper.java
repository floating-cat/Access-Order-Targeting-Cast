package cl.monsoon.access_order_targeting_cast.util;

import android.net.Uri;
import android.support.annotation.StringRes;

import com.f2prateek.rx.preferences.Preference;

import rx.functions.Action1;

@NotFinalForTesting
public class UriPreferenceHelper {
    private final UriHelper uriHelper;

    private final Preference<Uri> uriPreference;

    @StringRes
    private final int promptMessage;

    public UriPreferenceHelper(UriHelper uriHelper,
                               Preference<Uri> uriPreference, int promptMessage) {
        this.uriHelper = uriHelper;
        this.uriPreference = uriPreference;
        this.promptMessage = promptMessage;
    }

    public void getUriDisplay(Action1<String> onCompleted, Action1<Integer> onError) {
        Uri uri = uriPreference.get();
        if (uri != null) {
            String uriDisplayName = uriHelper.getUriDisplayName(uri);
            if (uriDisplayName != null) {
                onCompleted.call(uriDisplayName);
            } else {
                uriHelper.releasePreviousPersistableUriPermission(uri);
                uriPreference.set(null);
                onError.call(promptMessage);
            }
        }
    }

    public void saveUriPreferenceAndGetDisplayName(Uri uri,
                                                   Action1<String> onCompleted,
                                                   Action1<Integer> onError) {
        String fileDisplayName = uriHelper.getUriDisplayName(uri);
        if (fileDisplayName != null) {
            uriHelper.releaseOldPersistableUriPermissionIfNeeded(uriPreference.get(), uri);
            uriHelper.takePersistableUriPermission(uri);
            uriPreference.set(uri);
            onCompleted.call(fileDisplayName);
        } else {
            onError.call(promptMessage);
        }
    }
}
