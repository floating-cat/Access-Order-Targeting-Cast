package cl.monsoon.access_order_targeting_cast.main;

import android.net.Uri;

import cl.monsoon.access_order_targeting_cast.di.BaseView;
import rx.functions.Action1;

interface MainContract {
    interface View extends BaseView<Presenter> {
    }

    interface Presenter {
        String getSourceFileSelectingIntentType();

        void initSourceFilePreferenceDisplay(Action1<String/** preference display name **/> onCompleted,
                                             Action1<Integer/** resource string for prompt message **/> onError);

        void initOutputDirectoryPreferenceDisplay(Action1<String> onCompleted, Action1<Integer> onError);

        void handleActivityResultForSelectedSourceFileUri(Uri sourceFileUri,
                                                          Action1<String> onCompleted,
                                                          Action1<Integer> onError);

        void handleActivityResultForSelectedOutputDirectoryUri(Uri outputDirectoryUri,
                                                               Action1<String> onCompleted,
                                                               Action1<Integer> onError);
    }
}
