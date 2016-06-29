package cl.monsoon.access_order_targeting_cast.util;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import org.hamcrest.Matcher;

import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static org.hamcrest.Matchers.allOf;

/**
 * Forked from {@link android.support.test.espresso.action.ReplaceTextAction}.
 */
final class SetCheckedAction implements ViewAction {
    private final boolean checked;

    SetCheckedAction(boolean checked) {
        this.checked = checked;
    }

    @Override
    public Matcher<View> getConstraints() {
        return allOf(isDisplayed(), isAssignableFrom(TextView.class));
    }

    @Override
    public String getDescription() {
        return "set checked";
    }

    @Override
    public void perform(UiController uiController, View view) {
        ((Switch) view).setChecked(checked);
    }
}
