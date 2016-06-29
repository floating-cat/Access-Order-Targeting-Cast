package cl.monsoon.access_order_targeting_cast.main;

import android.support.test.filters.MediumTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import cl.monsoon.access_order_targeting_cast.util.ActivityTestRuleUtil;

@MediumTest
@RunWith(AndroidJUnit4.class)
public final class MainActivityTest {
    @Rule
    public final ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void doNotCrashAppWhenAppRestarts() {
        ActivityTestRuleUtil.restartActivity(activityTestRule);
    }
}
