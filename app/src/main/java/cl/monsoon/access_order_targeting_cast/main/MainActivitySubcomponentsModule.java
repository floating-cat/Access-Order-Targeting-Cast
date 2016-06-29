package cl.monsoon.access_order_targeting_cast.main;

import cl.monsoon.access_order_targeting_cast.di.ActivityComponentBuilder;
import cl.monsoon.access_order_targeting_cast.di.ActivityKey;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module(subcomponents = {MainComponent.class})
public abstract class MainActivitySubcomponentsModule {
    @Binds
    @IntoMap
    @ActivityKey(MainActivity.class)
    abstract ActivityComponentBuilder<?, ?, ?> activityComponentBuilder(MainComponent.Builder builder);
}
