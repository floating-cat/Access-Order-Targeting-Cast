package cl.monsoon.access_order_targeting_cast.main;

import cl.monsoon.access_order_targeting_cast.di.ActivityScope;
import dagger.Module;
import dagger.Provides;

@Module
public final class MainPresenterModule {
    private final MainContract.View view;

    MainPresenterModule(MainContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MainContract.View provideMainContractView() {
        return view;
    }
}
