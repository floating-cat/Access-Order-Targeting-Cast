package cl.monsoon.access_order_targeting_cast.util;

import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.f2prateek.rx.preferences.Preference;

public final class UriPreferenceAdapter implements Preference.Adapter<Uri> {
    @Nullable
    @Override
    public Uri get(@NonNull String key, @NonNull SharedPreferences preferences) {
        String uriString = preferences.getString(key, null);
        if (uriString != null) {
            return Uri.parse(uriString);
        } else {
            return null;
        }
    }

    @Override
    public void set(@NonNull String key, @NonNull Uri value, @NonNull SharedPreferences.Editor editor) {
        editor.putString(key, value.toString());
    }
}
