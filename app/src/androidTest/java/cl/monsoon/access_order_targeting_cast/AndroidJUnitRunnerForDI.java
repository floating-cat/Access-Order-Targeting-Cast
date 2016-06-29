package cl.monsoon.access_order_targeting_cast;

import android.app.Application;
import android.content.Context;
import android.support.test.runner.AndroidJUnitRunner;

public final class AndroidJUnitRunnerForDI extends AndroidJUnitRunner {
    @Override
    public Application newApplication(ClassLoader cl, String className, Context context)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return super.newApplication(cl, AndroidTestApp.class.getName(), context);
    }
}
