package fr.ygo.jobboard;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;

/**
 * Created by Yoann on 05/10/2014.
 */
public class JobDetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // load the view
        setContentView(R.layout.activity_job_detail);

        // if save instance state bundle is not null (orientation change, ...) then the fragment is automatically reloaded
        if(savedInstanceState == null) {

            // Create fragment and add bundle
            Fragment fragment = new JobDetailFragment();

            // Load fragment in the Activity
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_job_detail_layout, fragment)
                    .commit();
        }
    }
}
