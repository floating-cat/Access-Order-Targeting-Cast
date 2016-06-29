package cl.monsoon.access_order_targeting_cast.di;

import android.app.Activity;

public interface HasActivitySubcomponentBuilders {
    <A extends Activity, Module, Component> ActivityComponentBuilder<A, Module, Component>
    getActivityComponentBuilder(Class<? extends Activity> activityClass);
}
