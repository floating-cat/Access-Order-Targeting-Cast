package cl.monsoon.access_order_targeting_cast;

public final class AndroidTestApp extends App {
    @Override
    AppComponent getInitAppComponent() {
        return DaggerAndroidTestAppComponent.builder()
                .appModuleInternal(new AppModuleInternal(this))
                .build();
    }
}
