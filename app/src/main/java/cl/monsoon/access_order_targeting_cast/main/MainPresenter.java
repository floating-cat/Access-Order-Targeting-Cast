package cl.monsoon.access_order_targeting_cast.main;

import android.net.Uri;

import javax.inject.Inject;

import cl.monsoon.access_order_targeting_cast.di.OutputDirectoryUriPreferenceHelper;
import cl.monsoon.access_order_targeting_cast.di.SourceFileUriPreferenceHelper;
import cl.monsoon.access_order_targeting_cast.target.Target;
import cl.monsoon.access_order_targeting_cast.util.UriPreferenceHelper;
import rx.functions.Action1;

final class MainPresenter implements MainContract.Presenter {
    private final Target target;

    private final UriPreferenceHelper sourceFileUriPreferenceHelper;
    private final UriPreferenceHelper outputDirectoryUriPreferenceHelper;

    @Inject
    MainPresenter(MainContract.View view,
                  Target target,
                  @SourceFileUriPreferenceHelper UriPreferenceHelper sourceFileUriPreferenceHelper,
                  @OutputDirectoryUriPreferenceHelper UriPreferenceHelper outputDirectoryUriPreferenceHelper) {
        this.target = target;
        this.sourceFileUriPreferenceHelper = sourceFileUriPreferenceHelper;
        this.outputDirectoryUriPreferenceHelper = outputDirectoryUriPreferenceHelper;

        view.setPresenter(this);
    }

    @Override
    public String getSourceFileSelectingIntentType() {
        return target.getSourceFileMimeType();
    }

    @Override
    public void initSourceFilePreferenceDisplay(Action1<String> onCompleted,
                                                Action1<Integer> onError) {
        sourceFileUriPreferenceHelper.getUriDisplay(onCompleted, onError);
    }

    @Override
    public void initOutputDirectoryPreferenceDisplay(Action1<String> onCompleted,
                                                     Action1<Integer> onError) {
        outputDirectoryUriPreferenceHelper.getUriDisplay(onCompleted, onError);
    }

    @Override
    public void handleActivityResultForSelectedSourceFileUri(Uri sourceFileUri,
                                                             Action1<String> onCompleted,
                                                             Action1<Integer> onError) {
        sourceFileUriPreferenceHelper.saveUriPreferenceAndGetDisplayName(sourceFileUri,
                onCompleted, onError);
    }

    @Override
    public void handleActivityResultForSelectedOutputDirectoryUri(Uri outputDirectoryUri,
                                                                  Action1<String> onCompleted,
                                                                  Action1<Integer> onError) {
        outputDirectoryUriPreferenceHelper.saveUriPreferenceAndGetDisplayName(outputDirectoryUri,
                onCompleted, onError);
    }
}
