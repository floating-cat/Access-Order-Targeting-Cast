package cl.monsoon.access_order_targeting_cast.di;

import android.app.Activity;

public interface ActivityComponentBuilder<A extends Activity, Module, Component> {
    ActivityComponentBuilder<A, Module, Component> activityModule(Module module);

    Component build();
}
