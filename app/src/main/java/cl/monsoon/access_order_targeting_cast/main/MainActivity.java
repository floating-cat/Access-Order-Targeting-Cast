package cl.monsoon.access_order_targeting_cast.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.Objects;

import javax.inject.Inject;

import cl.monsoon.access_order_targeting_cast.R;
import cl.monsoon.access_order_targeting_cast.di.ActivityComponentBuilder;
import cl.monsoon.access_order_targeting_cast.di.HasActivitySubcomponentBuilders;

public final class MainActivity extends AppCompatActivity {
    /**
     * Inject this presenter to its corresponding view {@link MainFragment}.
     */
    @Inject
    MainPresenter mainPresenter;

    private MainFragment mainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpToolbar();
        addMainFragmentIfNeeded();
        inject();
    }

    private void setUpToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Objects.requireNonNull(toolbar);
        setSupportActionBar(toolbar);
    }

    private void addMainFragmentIfNeeded() {
        mainFragment = (MainFragment) getFragmentManager().findFragmentById(
                R.id.frame_layout);
        if (mainFragment == null) {
            mainFragment = new MainFragment();
            getFragmentManager().beginTransaction().add(R.id.frame_layout, mainFragment,
                    MainFragment.TAG).commit();
        }
    }

    private void inject() {
        ActivityComponentBuilder<MainActivity, MainPresenterModule, MainComponent> builder =
                ((HasActivitySubcomponentBuilders) getApplication())
                        .getActivityComponentBuilder(MainActivity.class);
        builder.activityModule(new MainPresenterModule(mainFragment))
                .build()
                .inject(this);
    }
}
