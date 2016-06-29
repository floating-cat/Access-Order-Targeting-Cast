package cl.monsoon.access_order_targeting_cast;

import cl.monsoon.access_order_targeting_cast.di.AppScope;
import cl.monsoon.access_order_targeting_cast.main.MainActivitySubcomponentsModule;
import cl.monsoon.access_order_targeting_cast.main.MainActivityTestForIntents;
import cl.monsoon.access_order_targeting_cast.main.MainActivityTestForPreferenceInteraction;
import cl.monsoon.access_order_targeting_cast.main.SetTargetingFalseTest;
import dagger.Component;

@AppScope
@Component(modules = {
        AppModuleInternal.class,
        AndroidTestAppModulePublishedAlternativeForUriHelper.class,
        MainActivitySubcomponentsModule.class
})
public interface AndroidTestAppComponent extends AppComponent {
    void inject(MainActivityTestForIntents mainActivityTestForIntents);

    void inject(SetTargetingFalseTest setTargetingFalseTest);

    void inject(MainActivityTestForPreferenceInteraction mainActivityTestForPreferenceInteraction);
}
