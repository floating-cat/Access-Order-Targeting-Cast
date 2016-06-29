package cl.monsoon.access_order_targeting_cast;

import android.app.Activity;
import android.app.Application;

import java.util.Map;

import javax.inject.Inject;

import cl.monsoon.access_order_targeting_cast.di.ActivityComponentBuilder;
import cl.monsoon.access_order_targeting_cast.di.HasActivitySubcomponentBuilders;
import cl.monsoon.access_order_targeting_cast.util.NotFinalForTesting;

@NotFinalForTesting
public class App extends Application implements HasActivitySubcomponentBuilders {
    @Inject
    Map<Class<? extends Activity>, ActivityComponentBuilder<?, ?, ?>> activityComponentBuilderMap;

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = getInitAppComponent();
        appComponent.inject(this);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <A extends Activity, Module, Component> ActivityComponentBuilder<A, Module, Component>
    getActivityComponentBuilder(Class<? extends Activity> activityClass) {
        return (ActivityComponentBuilder<A, Module, Component>)
                activityComponentBuilderMap.get(activityClass);
    }

    AppComponent getInitAppComponent() {
        return DaggerAppComponent.builder()
                .appModuleInternal(new AppModuleInternal(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
