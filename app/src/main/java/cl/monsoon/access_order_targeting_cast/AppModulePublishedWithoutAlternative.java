package cl.monsoon.access_order_targeting_cast;

import android.net.Uri;

import com.f2prateek.rx.preferences.Preference;
import com.f2prateek.rx.preferences.RxSharedPreferences;

import cl.monsoon.access_order_targeting_cast.di.AppScope;
import cl.monsoon.access_order_targeting_cast.di.OutputDirectoryUriPreference;
import cl.monsoon.access_order_targeting_cast.di.OutputDirectoryUriPreferenceHelper;
import cl.monsoon.access_order_targeting_cast.di.SourceFileUriPreference;
import cl.monsoon.access_order_targeting_cast.di.SourceFileUriPreferenceHelper;
import cl.monsoon.access_order_targeting_cast.di.TargetingPreference;
import cl.monsoon.access_order_targeting_cast.target.Target;
import cl.monsoon.access_order_targeting_cast.target.TargetImpl;
import cl.monsoon.access_order_targeting_cast.util.UriHelper;
import cl.monsoon.access_order_targeting_cast.util.UriPreferenceAdapter;
import cl.monsoon.access_order_targeting_cast.util.UriPreferenceHelper;
import dagger.Module;
import dagger.Provides;

import static cl.monsoon.access_order_targeting_cast.Constants.PREF_KEY_OUTPUT_DIRECTORY;
import static cl.monsoon.access_order_targeting_cast.Constants.PREF_KEY_SOURCE_FILE;
import static cl.monsoon.access_order_targeting_cast.Constants.PREF_KEY_TARGETING;

@Module(includes = {AppModulePublishedWithoutAlternative.PreferencesRelated.class})
final class AppModulePublishedWithoutAlternative {
    @Provides
    @AppScope
    static Target provideTarget() {
        return new TargetImpl();
    }

    @Module
    static final class PreferencesRelated {
        @Provides
        @AppScope
        @TargetingPreference
        static Preference<Boolean> provideTargetingPreference(RxSharedPreferences rxSharedPreferences) {
            return rxSharedPreferences.getBoolean(PREF_KEY_TARGETING);
        }

        @Provides
        @AppScope
        @SourceFileUriPreference
        static Preference<Uri> provideSourceFileUriPreference(RxSharedPreferences rxSharedPreferences,
                                                              UriPreferenceAdapter uriPreferenceAdapter) {
            return rxSharedPreferences.getObject(PREF_KEY_SOURCE_FILE, uriPreferenceAdapter);
        }

        @Provides
        @AppScope
        @OutputDirectoryUriPreference
        static Preference<Uri> provideOutputDirectoryUriPreference(RxSharedPreferences rxSharedPreferences,
                                                                   UriPreferenceAdapter uriPreferenceAdapter) {
            return rxSharedPreferences.getObject(PREF_KEY_OUTPUT_DIRECTORY, uriPreferenceAdapter);
        }

        @Provides
        @AppScope
        @SourceFileUriPreferenceHelper
        static UriPreferenceHelper provideSourceFileUriPreferenceHelper(UriHelper uriHelper,
                                                                        @SourceFileUriPreference Preference<Uri> uriPreference) {
            return new UriPreferenceHelper(uriHelper, uriPreference,
                    R.string.prompt_unable_to_find_source_file);
        }

        @Provides
        @AppScope
        @OutputDirectoryUriPreferenceHelper
        static UriPreferenceHelper provideOutputDirectoryUriPreferenceHelper(UriHelper uriHelper,
                                                                             @OutputDirectoryUriPreference Preference<Uri> uriPreference) {
            return new UriPreferenceHelper(uriHelper, uriPreference,
                    R.string.prompt_unable_to_find_output_directory);
        }
    }
}
