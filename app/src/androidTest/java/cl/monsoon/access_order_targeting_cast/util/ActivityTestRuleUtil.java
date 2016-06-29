package cl.monsoon.access_order_targeting_cast.util;

import android.app.Activity;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import cl.monsoon.access_order_targeting_cast.AndroidTestApp;
import cl.monsoon.access_order_targeting_cast.AndroidTestAppComponent;

public final class ActivityTestRuleUtil {
    public static AndroidTestAppComponent getAndroidTestAppComponent(
            ActivityTestRule<? extends Activity> activityTestRule) {
        AndroidTestApp androidTestApp =
                (AndroidTestApp) activityTestRule.getActivity().getApplication();
        return (AndroidTestAppComponent) androidTestApp.getAppComponent();
    }

    public static void restartActivity(ActivityTestRule<?> activityTestRule) {
        InstrumentationRegistry.getInstrumentation().runOnMainSync(() ->
                activityTestRule.getActivity().recreate());
    }
}
