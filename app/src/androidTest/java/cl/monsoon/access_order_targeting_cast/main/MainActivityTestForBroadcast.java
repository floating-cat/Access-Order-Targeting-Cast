package cl.monsoon.access_order_targeting_cast.main;

import android.content.Intent;
import android.support.test.espresso.DataInteraction;
import android.support.test.filters.MediumTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import cl.monsoon.access_order_targeting_cast.R;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.matcher.PreferenceMatchers.withTitle;
import static cl.monsoon.access_order_targeting_cast.BuildConfig.SET_TARGETING_PREFERENCE_FALSE_RECEIVER_ACTION;
import static cl.monsoon.access_order_targeting_cast.util.EspressoUtil.assertSwitchPreferenceChecked;
import static cl.monsoon.access_order_targeting_cast.util.EspressoUtil.setSwitchPreferenceChecked;

@MediumTest
@RunWith(AndroidJUnit4.class)
public final class MainActivityTestForBroadcast {
    @Rule
    public final ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    private DataInteraction targetingPreferenceDataInteraction;

    @Before
    public void setup() {
        targetingPreferenceDataInteraction = onData(withTitle(R.string.targeting));
    }

    @Test
    public void uncheckTargetingSwitchPreferenceWhenReceivingSetTargetingPreferenceFalseReceiver() {
        setSwitchPreferenceChecked(targetingPreferenceDataInteraction, true);

        activityTestRule.getActivity().sendBroadcast(new Intent(
                SET_TARGETING_PREFERENCE_FALSE_RECEIVER_ACTION));

        assertSwitchPreferenceChecked(targetingPreferenceDataInteraction);
    }
}
