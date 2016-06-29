package cl.monsoon.access_order_targeting_cast.main;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.support.annotation.StringRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;

import cl.monsoon.access_order_targeting_cast.R;
import cl.monsoon.access_order_targeting_cast.util.XposedUtil;

import static cl.monsoon.access_order_targeting_cast.BuildConfig.SET_TARGETING_PREFERENCE_FALSE_RECEIVER_ACTION;
import static cl.monsoon.access_order_targeting_cast.Constants.PREF_KEY_OUTPUT_DIRECTORY;
import static cl.monsoon.access_order_targeting_cast.Constants.PREF_KEY_SOURCE_FILE;
import static cl.monsoon.access_order_targeting_cast.Constants.PREF_KEY_TARGETING;

public final class MainFragment extends PreferenceFragment implements MainContract.View {
    private static final int REQUEST_SELECT_SOURCE_FILE = 1;
    private static final int REQUEST_OUTPUT_DIRECTORY_FILE = 2;

    static final String TAG = MainFragment.class.getName();

    private MainContract.Presenter presenter;

    private CoordinatorLayout coordinatorLayout;

    private SwitchPreference targetingSwitchPreference;
    private Preference sourceFilePreference;
    private Preference outputDirectoryPreference;

    private BroadcastReceiver broadcastReceiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference_main);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        coordinatorLayout = (CoordinatorLayout) getActivity().findViewById(R.id.coordinator_layout);
        targetingSwitchPreference = (SwitchPreference) findPreference(PREF_KEY_TARGETING);
        sourceFilePreference = findPreference(PREF_KEY_SOURCE_FILE);
        outputDirectoryPreference = findPreference(PREF_KEY_OUTPUT_DIRECTORY);

        initTargetingSwitchPreferenceChangeListener();
        initPreferenceClickListener();
        initPreferenceDisplay();

        registerReceiverForUncheckingTargetingSwitchPreference();
    }

    @Override
    public void onPause() {
        super.onPause();

        // Set preferences file permissions to be world readable
        XposedUtil.setPreferenceFileWorldReadable(getContext(),
                getPreferenceManager().getSharedPreferencesName());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getContext().unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            switch (requestCode) {
                case REQUEST_SELECT_SOURCE_FILE:
                    handleActivityResultForSelectedSourceFileUri(uri);
                    break;
                case REQUEST_OUTPUT_DIRECTORY_FILE:
                    handleActivityResultForSelectedOutputDirectoryUri(uri);
                    break;
                default:
                    throw new IllegalStateException();
            }
        }
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        this.presenter = presenter;
    }

    private void initTargetingSwitchPreferenceChangeListener() {
        targetingSwitchPreference.setOnPreferenceChangeListener((preference, o) -> {
            boolean isPreviousTargetingSwitchPreferenceChecked =
                    targetingSwitchPreference.isChecked();
            sourceFilePreference.setEnabled(isPreviousTargetingSwitchPreferenceChecked);
            outputDirectoryPreference.setEnabled(isPreviousTargetingSwitchPreferenceChecked);

            return true;
        });
    }

    private void initPreferenceClickListener() {
        sourceFilePreference.setOnPreferenceClickListener(preference -> {
            startActivityForSelectingSourceFile();
            return true;
        });
        outputDirectoryPreference.setOnPreferenceClickListener(preference -> {
            startActivityForSelectingOutputDirectory();
            return true;
        });
    }

    private void startActivityForSelectingSourceFile() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType(presenter.getSourceFileSelectingIntentType());
        startActivityForResult(intent, REQUEST_SELECT_SOURCE_FILE);
    }

    private void startActivityForSelectingOutputDirectory() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
        startActivityForResult(intent, REQUEST_OUTPUT_DIRECTORY_FILE);
    }

    private void initPreferenceDisplay() {
        presenter.initSourceFilePreferenceDisplay(this::setSourceFilePreferenceDisplayName,
                this::showPrompt);
        presenter.initOutputDirectoryPreferenceDisplay(this::setOutputFilePreferenceDisplayName,
                this::showPrompt);
        invalidateTargetingSwitchPreferenceState();
    }

    private void registerReceiverForUncheckingTargetingSwitchPreference() {
        // update `targetingSwitchPreference` checked state because we can change
        // targeting Preference by using `SetTargetingPreferenceFalseReceiver`
        IntentFilter intentFilter = new IntentFilter(SET_TARGETING_PREFERENCE_FALSE_RECEIVER_ACTION);
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                targetingSwitchPreference.setChecked(false);
            }
        };
        getContext().registerReceiver(broadcastReceiver, intentFilter);
    }

    private void handleActivityResultForSelectedSourceFileUri(Uri uri) {
        presenter.handleActivityResultForSelectedSourceFileUri(uri,
                this::setSourceFilePreferenceDisplayName, this::showPrompt);
        invalidateTargetingSwitchPreferenceState();
    }

    private void handleActivityResultForSelectedOutputDirectoryUri(Uri uri) {
        presenter.handleActivityResultForSelectedOutputDirectoryUri(uri,
                this::setOutputFilePreferenceDisplayName, this::showPrompt);
        invalidateTargetingSwitchPreferenceState();
    }

    private void setSourceFilePreferenceDisplayName(String sourceFileDisplayName) {
        sourceFilePreference.setSummary(sourceFileDisplayName);
    }

    private void setOutputFilePreferenceDisplayName(String sourceFileDisplayName) {
        outputDirectoryPreference.setSummary(sourceFileDisplayName);
    }

    private void invalidateTargetingSwitchPreferenceState() {
        targetingSwitchPreference.setEnabled(!TextUtils.isEmpty(sourceFilePreference.getSummary())
                && !TextUtils.isEmpty(outputDirectoryPreference.getSummary()));

        boolean isTargetingSwitchPreferenceChecked = targetingSwitchPreference.isChecked();
        sourceFilePreference.setEnabled(!isTargetingSwitchPreferenceChecked);
        outputDirectoryPreference.setEnabled(!isTargetingSwitchPreferenceChecked);
    }

    private void showPrompt(@StringRes int promptMessage) {
        Snackbar.make(coordinatorLayout, promptMessage, Snackbar.LENGTH_SHORT).show();
    }
}
