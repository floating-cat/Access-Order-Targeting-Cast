package cl.monsoon.access_order_targeting_cast;

import com.f2prateek.rx.preferences.Preference;

import cl.monsoon.access_order_targeting_cast.di.AppScope;
import cl.monsoon.access_order_targeting_cast.di.TargetingPreference;
import cl.monsoon.access_order_targeting_cast.main.MainActivitySubcomponentsModule;
import dagger.Component;

@AppScope
@Component(modules = {
        AppModuleInternal.class,
        AppModulePublishedAlternativeForUriHelper.class,
        MainActivitySubcomponentsModule.class
})
public interface AppComponent {
    void inject(App app);

    @TargetingPreference
    Preference<Boolean> getTargetingPreference();
}
