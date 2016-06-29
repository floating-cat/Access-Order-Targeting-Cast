package cl.monsoon.access_order_targeting_cast.util;

import android.support.annotation.IdRes;
import android.support.test.espresso.DataInteraction;
import android.support.test.espresso.action.ViewActions;

import static android.support.test.espresso.action.ViewActions.actionWithAssertions;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;

public final class EspressoUtil {
    @IdRes
    private static final int ID_SWITCH_WIDGET = android.R.id.switch_widget;

    public static void assertSwitchPreferenceChecked(DataInteraction dataInteraction) {
        dataInteraction.onChildView(withId(ID_SWITCH_WIDGET))
                .check(matches(not(isChecked())));
    }

    /**
     * Forked from {@link ViewActions#replaceText(String)}.
     */
    public static void setSwitchPreferenceChecked(DataInteraction dataInteraction, boolean checked) {
        dataInteraction.onChildView(withId(ID_SWITCH_WIDGET))
                .perform(actionWithAssertions(new SetCheckedAction(checked)));
    }
}
