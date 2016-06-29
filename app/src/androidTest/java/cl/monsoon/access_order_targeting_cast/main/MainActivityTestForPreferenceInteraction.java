package cl.monsoon.access_order_targeting_cast.main;

import android.net.Uri;
import android.support.test.espresso.DataInteraction;
import android.support.test.filters.MediumTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.f2prateek.rx.preferences.Preference;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import javax.inject.Inject;

import cl.monsoon.access_order_targeting_cast.R;
import cl.monsoon.access_order_targeting_cast.di.OutputDirectoryUriPreference;
import cl.monsoon.access_order_targeting_cast.di.SourceFileUriPreference;
import cl.monsoon.access_order_targeting_cast.util.ActivityTestRuleUtil;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.PreferenceMatchers.withTitle;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static cl.monsoon.access_order_targeting_cast.util.ActivityTestRuleUtil.restartActivity;
import static cl.monsoon.access_order_targeting_cast.util.EspressoUtil.setSwitchPreferenceChecked;
import static org.hamcrest.Matchers.not;

@MediumTest
@RunWith(AndroidJUnit4.class)
public final class MainActivityTestForPreferenceInteraction {
    @Rule
    public final ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Inject
    @SourceFileUriPreference
    Preference<Uri> sourceFileUriPreference;
    @Inject
    @OutputDirectoryUriPreference
    Preference<Uri> outputDirectoryUriPreference;

    private final Uri anyUri = Uri.parse("http://example.com/");

    private DataInteraction targetingPreferenceDataInteraction;
    private DataInteraction sourceFilePreferenceDataInteraction;
    private DataInteraction outputDirectoryPreferenceDataInteraction;

    @Before
    public void setup() throws IOException {
        ActivityTestRuleUtil.getAndroidTestAppComponent(activityTestRule)
                .inject(this);

        targetingPreferenceDataInteraction = onData(withTitle(R.string.targeting));
        sourceFilePreferenceDataInteraction = onData(withTitle(R.string.source_file));
        outputDirectoryPreferenceDataInteraction = onData(withTitle(R.string.output_directory));
    }

    @After
    public void teardown() {
        sourceFileUriPreference.set(null);
        outputDirectoryUriPreference.set(null);
    }

    @Test
    public void targetingSwitchPreferenceIsNotEnabledIfSourceFileIsEmpty() {
        sourceFileUriPreference.set(null);

        restartActivity(activityTestRule);

        targetingPreferenceDataInteraction.check(matches(not(isEnabled())));
    }

    @Test
    public void targetingSwitchPreferenceIsNotEnabledIfOutputDirectoryIsEmpty() {
        outputDirectoryUriPreference.set(null);

        restartActivity(activityTestRule);

        targetingPreferenceDataInteraction.check(matches(not(isEnabled())));
    }

    @Test
    public void targetingSwitchPreferenceIsEnabledIfBothSourceFileAndOutputDirectoryUriPreferenceAreNotEmpty() {
        sourceFileUriPreference.set(anyUri);
        outputDirectoryUriPreference.set(anyUri);

        restartActivity(activityTestRule);

        targetingPreferenceDataInteraction.check(matches(isEnabled()));
    }

    @Test
    public void disableSourceFileAndOutputDirectoryPreferenceIfTargetingSwitchPreferenceIsChecked() {
        sourceFileUriPreference.set(anyUri);
        outputDirectoryUriPreference.set(anyUri);

        setSwitchPreferenceChecked(targetingPreferenceDataInteraction, true);

        sourceFilePreferenceDataInteraction.check(matches(not(isEnabled())));
        outputDirectoryPreferenceDataInteraction.check(matches(not(isEnabled())));
    }

    @Test
    public void enableSourceFileAndOutputDirectoryPreferenceIfTargetingSwitchPreferenceIsNotChecked() {
        sourceFileUriPreference.set(anyUri);
        outputDirectoryUriPreference.set(anyUri);

        setSwitchPreferenceChecked(targetingPreferenceDataInteraction, true);
        setSwitchPreferenceChecked(targetingPreferenceDataInteraction, false);

        sourceFilePreferenceDataInteraction.check(matches(isEnabled()));
        outputDirectoryPreferenceDataInteraction.check(matches(isEnabled()));
    }
}
