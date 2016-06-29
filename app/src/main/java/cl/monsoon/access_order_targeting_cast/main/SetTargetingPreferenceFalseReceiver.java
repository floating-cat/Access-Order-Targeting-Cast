package cl.monsoon.access_order_targeting_cast.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.f2prateek.rx.preferences.Preference;

import cl.monsoon.access_order_targeting_cast.App;

/**
 * See https://github.com/rovo89/XposedBridge/issues/63
 */
public final class SetTargetingPreferenceFalseReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        getTargetingPreference(context).set(false);
    }

    private Preference<Boolean> getTargetingPreference(Context context) {
        return ((App) context.getApplicationContext()).getAppComponent().getTargetingPreference();
    }
}
