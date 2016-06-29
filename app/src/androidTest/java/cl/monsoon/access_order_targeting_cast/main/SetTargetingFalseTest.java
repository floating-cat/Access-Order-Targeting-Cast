package cl.monsoon.access_order_targeting_cast.main;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.MediumTest;
import android.support.test.runner.AndroidJUnit4;

import com.f2prateek.rx.preferences.Preference;
import com.google.common.truth.Truth;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import cl.monsoon.access_order_targeting_cast.di.TargetingPreference;
import cl.monsoon.access_order_targeting_cast.util.ActivityTestRuleUtil;
import cl.monsoon.access_order_targeting_cast.util.ElapsedTimeIdlingResource;

import static cl.monsoon.access_order_targeting_cast.BuildConfig.SET_TARGETING_PREFERENCE_FALSE_RECEIVER_ACTION;

@MediumTest
@RunWith(AndroidJUnit4.class)
public final class SetTargetingFalseTest {
    @Rule
    public final IntentsTestRule<MainActivity> activityTestRule =
            new IntentsTestRule<>(MainActivity.class);

    @Inject
    @TargetingPreference
    Preference<Boolean> targetingPreference;

    @Before
    public void setup() {
        ActivityTestRuleUtil.getAndroidTestAppComponent(activityTestRule)
                .inject(this);
    }

    @After
    public void teardown() {
        targetingPreference.set(false);
    }

    @Test
    public void setTargetingPreferenceFalseWhenReceivingSetTargetingPreferenceFalseReceiver() {
        targetingPreference.set(true);

        activityTestRule.getActivity().sendBroadcast(new Intent(
                SET_TARGETING_PREFERENCE_FALSE_RECEIVER_ACTION));
        IdlingResource idlingResource = new ElapsedTimeIdlingResource(100);
        Espresso.registerIdlingResources(idlingResource);

        Truth.assertThat(targetingPreference.get()).isFalse();

        Espresso.unregisterIdlingResources(idlingResource);
    }
}
