package cl.monsoon.access_order_targeting_cast.main;

import cl.monsoon.access_order_targeting_cast.di.ActivityComponentBuilder;
import cl.monsoon.access_order_targeting_cast.di.ActivityScope;
import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = MainPresenterModule.class)
public interface MainComponent {
    void inject(MainActivity activity);

    @Subcomponent.Builder
    interface Builder extends ActivityComponentBuilder<MainActivity, MainPresenterModule, MainComponent> {

    }
}
