package cl.monsoon.access_order_targeting_cast.util;

import android.support.test.espresso.IdlingResource;

/**
 * Forked from https://github.com/chiuki/espresso-samples/blob/master/idling-resource-elapsed-time/app/src/androidTest/java/com/sqisland/espresso/idling_resource/elapsed_time/ElapsedTimeIdlingResource.java
 * commit a70a343862a4199804f6fdffd9173cd453bb8d11
 */
public final class ElapsedTimeIdlingResource implements IdlingResource {
    private final long startTimeMillis;
    private final long waitingTimeMillis;

    private ResourceCallback resourceCallback;

    public ElapsedTimeIdlingResource(long waitingTimeMillis) {
        this.startTimeMillis = System.currentTimeMillis();
        this.waitingTimeMillis = waitingTimeMillis;
    }

    @Override
    public String getName() {
        return ElapsedTimeIdlingResource.class.getName() + ":" + waitingTimeMillis;
    }

    @Override
    public boolean isIdleNow() {
        long elapsedTimeMillis = System.currentTimeMillis() - startTimeMillis;
        boolean idle = (elapsedTimeMillis >= waitingTimeMillis);
        if (idle) {
            resourceCallback.onTransitionToIdle();
        }
        return idle;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.resourceCallback = callback;
    }
}
