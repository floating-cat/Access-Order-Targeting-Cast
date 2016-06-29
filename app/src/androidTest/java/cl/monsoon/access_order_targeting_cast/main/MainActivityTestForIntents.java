package cl.monsoon.access_order_targeting_cast.main;


import android.app.Activity;
import android.app.Instrumentation.ActivityResult;
import android.content.Intent;
import android.net.Uri;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.DataInteraction;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.MediumTest;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;

import com.f2prateek.rx.preferences.Preference;
import com.google.common.io.Files;
import com.google.common.truth.Truth;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import cl.monsoon.access_order_targeting_cast.R;
import cl.monsoon.access_order_targeting_cast.di.OutputDirectoryUriPreference;
import cl.monsoon.access_order_targeting_cast.di.SourceFileUriPreference;
import cl.monsoon.access_order_targeting_cast.target.Target;
import cl.monsoon.access_order_targeting_cast.util.ActivityTestRuleUtil;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasType;
import static android.support.test.espresso.matcher.PreferenceMatchers.withTitle;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static org.hamcrest.Matchers.allOf;

@MediumTest
@RunWith(AndroidJUnit4.class)
@Ignore // due to https://code.google.com/p/android/issues/detail?id=202217
public final class MainActivityTestForIntents {
    @Rule
    public final IntentsTestRule<MainActivity> activityTestRule =
            new IntentsTestRule<>(MainActivity.class);

    @Inject
    Target target;

    @Inject
    @SourceFileUriPreference
    Preference<Uri> sourceFileUriPreference;
    @Inject
    @OutputDirectoryUriPreference
    Preference<Uri> outputDirectoryUriPreference;

    private File anyTempFile;
    private File anyTempDirectory;

    private DataInteraction targetingPreferenceDataInteraction;
    private DataInteraction sourceFilePreferenceDataInteraction;
    private DataInteraction outputDirectoryPreferenceDataInteraction;

    @Before
    public void setup() throws IOException {
        ActivityTestRuleUtil.getAndroidTestAppComponent(activityTestRule)
                .inject(this);

        anyTempFile = getATempFile();
        anyTempDirectory = getATempDirectory();

        targetingPreferenceDataInteraction = onData(withTitle(R.string.targeting));
        sourceFilePreferenceDataInteraction = onData(withTitle(R.string.source_file));
        outputDirectoryPreferenceDataInteraction = onData(withTitle(R.string.output_directory));
    }

    @After
    public void teardown() {
        sourceFileUriPreference.set(null);
        outputDirectoryUriPreference.set(null);

        anyTempFile.deleteOnExit();
        anyTempDirectory.deleteOnExit();

        // https://github.com/google/android-testing-support-library/issues/13
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).pressBack();
    }

    @Test
    public void validateIntentWhenSelectingSourceFile() {
        sourceFilePreferenceDataInteraction.perform(click());

        intended(allOf(hasAction(Intent.ACTION_OPEN_DOCUMENT),
                hasType(target.getSourceFileMimeType())));
    }

    @Test
    public void handleSourceFileProperly() {
        sourceFileUriPreference.set(null);
        stubSuccessActivityResultForSourceFileSelectedIntent();

        sourceFilePreferenceDataInteraction.perform(click());

        Truth.assertThat(sourceFileUriPreference.get());
    }

    @Test
    public void validateIntentWhenSelectingOutputDirectory() {
        outputDirectoryPreferenceDataInteraction.perform(click());

        intended(hasAction(Intent.ACTION_OPEN_DOCUMENT_TREE));
    }

    @Test
    public void handleOutputDirectoryProperly() {
        outputDirectoryUriPreference.set(null);
        stubSuccessActivityResultForOutputDirectorySelectedIntent();

        outputDirectoryPreferenceDataInteraction.perform(click());

        Truth.assertThat(outputDirectoryUriPreference.get()).isNotNull();
    }

    @Test
    public void enableTargetingSwitchPreferenceIfHandleBothSourceFileAndOutputDirectoryProperly() {
        sourceFileUriPreference.set(null);
        stubSuccessActivityResultForSourceFileSelectedIntent();
        sourceFilePreferenceDataInteraction.perform(click());

        outputDirectoryUriPreference.set(null);
        stubSuccessActivityResultForOutputDirectorySelectedIntent();
        outputDirectoryPreferenceDataInteraction.perform(click());

        targetingPreferenceDataInteraction.check(matches(isEnabled()));
    }

    private File getATempFile() throws IOException {
        File internalStorageDirectory = activityTestRule.getActivity().getFilesDir();
        return File.createTempFile("test", null, internalStorageDirectory);
    }

    private File getATempDirectory() {
        return Files.createTempDir();
    }

    private void stubSuccessActivityResultForSourceFileSelectedIntent() {
        ActivityResult activityResult = getSuccessActivityResultForSourceFile();
        intending(allOf(hasAction(Intent.ACTION_OPEN_DOCUMENT),
                hasType(target.getSourceFileMimeType()))).respondWith(activityResult);
    }

    private void stubSuccessActivityResultForOutputDirectorySelectedIntent() {
        ActivityResult activityResult = getSuccessActivityResultForOutputDirectory();
        intending(hasAction(Intent.ACTION_OPEN_DOCUMENT_TREE)).respondWith(activityResult);
    }

    private ActivityResult getSuccessActivityResultForSourceFile() {
        Intent resultData = new Intent();
        // we use a file Uri SCHEME because we can't get any content Uri SCHEME in testing
        resultData.setData(Uri.fromFile(anyTempFile));

        return new ActivityResult(Activity.RESULT_OK, resultData);
    }

    private ActivityResult getSuccessActivityResultForOutputDirectory() {
        Intent resultData = new Intent();
        // we use a file Uri SCHEME because we can't get any content Uri SCHEME in testing
        resultData.setData(Uri.fromFile(anyTempDirectory));

        return new ActivityResult(Activity.RESULT_OK, resultData);
    }
}
