package cl.monsoon.access_order_targeting_cast.di;

import android.app.Activity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import dagger.MapKey;

@MapKey
@Target({ElementType.METHOD})
public @interface ActivityKey {
    Class<? extends Activity> value();
}
