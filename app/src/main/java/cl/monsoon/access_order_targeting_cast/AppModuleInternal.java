package cl.monsoon.access_order_targeting_cast;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.f2prateek.rx.preferences.RxSharedPreferences;

import cl.monsoon.access_order_targeting_cast.di.AppScope;
import cl.monsoon.access_order_targeting_cast.target.Target;
import cl.monsoon.access_order_targeting_cast.target.TargetUriPermissionGrantHelper;
import cl.monsoon.access_order_targeting_cast.util.UriPreferenceAdapter;
import dagger.Module;
import dagger.Provides;

@Module(includes = {AppModulePublishedWithoutAlternative.class})
final class AppModuleInternal {
    private final Context context;

    AppModuleInternal(Context context) {
        this.context = context;
    }

    @Provides
    @AppScope
    Context provideContext() {
        return context;
    }

    @Provides
    @AppScope
    static ContentResolver provideContentResolver(Context context) {
        return context.getContentResolver();
    }

    @Provides
    @AppScope
    static RxSharedPreferences provideRxSharedPreferences(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return RxSharedPreferences.create(sharedPreferences);
    }

    @Provides
    @AppScope
    static UriPreferenceAdapter provideUriPreferenceAdapter() {
        return new UriPreferenceAdapter();
    }

    @Provides
    @AppScope
    static TargetUriPermissionGrantHelper provideTargetUriPermissionGrantHelper(Context context,
                                                                                Target target) {
        return new TargetUriPermissionGrantHelper(context, target);
    }
}
