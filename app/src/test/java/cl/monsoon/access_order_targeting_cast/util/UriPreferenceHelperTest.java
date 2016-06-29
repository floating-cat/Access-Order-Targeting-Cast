package cl.monsoon.access_order_targeting_cast.util;

import android.net.Uri;

import com.f2prateek.rx.preferences.Preference;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import rx.functions.Action1;

import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public final class UriPreferenceHelperTest {
    private static final String ANY_DISPLAY_NAME = "any_display_name";
    private static final int ANY_PROMPT_MESSAGE_RESOURCE_STRING = 1;

    @Mock
    UriHelper uriHelper;

    @Mock
    Preference<Uri> anyUriPreference;

    @Mock
    Uri anyUriPrevious;

    @Mock
    Uri anyUriNew;

    @Mock
    Action1<String/** preference display name **/> onSuccess;

    @Mock
    Action1<Integer/** resource string for prompt message **/> onFailure;

    private UriPreferenceHelper uriPreferenceHelper;

    @Before
    public void setup() {
        uriPreferenceHelper = new UriPreferenceHelper(uriHelper, anyUriPreference,
                ANY_PROMPT_MESSAGE_RESOURCE_STRING);
    }

    @Test
    public void succeedToGetUriDisplayName() {
        when(uriHelper.getUriDisplayName(anyUriPrevious)).thenReturn(ANY_DISPLAY_NAME);
        when(anyUriPreference.get()).thenReturn(anyUriPrevious);

        uriPreferenceHelper.getUriDisplay(onSuccess, onFailure);

        verify(onSuccess, only()).call(ANY_DISPLAY_NAME);
        verifyZeroInteractions(onFailure);
    }

    @Test
    public void failToGetUriDisplayNameIfUnableToGetOldUriDisplayName() {
        when(uriHelper.getUriDisplayName(anyUriPrevious)).thenReturn(null);
        when(anyUriPreference.get()).thenReturn(anyUriPrevious);

        uriPreferenceHelper.getUriDisplay(onSuccess, onFailure);

        verify(onFailure, only()).call(ANY_PROMPT_MESSAGE_RESOURCE_STRING);
        verifyZeroInteractions(onSuccess);
        verify(uriHelper).releasePreviousPersistableUriPermission(anyUriPrevious);
        verify(anyUriPreference).set(null);
    }

    @Test
    public void succeedToHandleActivityResultForSelectedSourceFileUri() {
        when(uriHelper.getUriDisplayName(anyUriNew)).thenReturn(ANY_DISPLAY_NAME);
        when(anyUriPreference.get()).thenReturn(anyUriPrevious);

        uriPreferenceHelper.saveUriPreferenceAndGetDisplayName(anyUriNew, onSuccess, onFailure);

        verify(onSuccess, only()).call(ANY_DISPLAY_NAME);
        verifyZeroInteractions(onFailure);
        verify(uriHelper).releaseOldPersistableUriPermissionIfNeeded(anyUriPrevious,
                anyUriNew);
        verify(uriHelper).takePersistableUriPermission(anyUriNew);
        verify(anyUriPreference).set(anyUriNew);
    }

    @Test
    public void failToHandleActivityResultForSelectedSourceFileUriIfUnableToGetNewUriDisplayName() {
        when(uriHelper.getUriDisplayName(anyUriNew)).thenReturn(null);

        uriPreferenceHelper.saveUriPreferenceAndGetDisplayName(anyUriNew, onSuccess, onFailure);

        verify(onFailure).call(ANY_PROMPT_MESSAGE_RESOURCE_STRING);
        verifyZeroInteractions(onSuccess);
        verifyZeroInteractions(anyUriPreference);
    }
}
