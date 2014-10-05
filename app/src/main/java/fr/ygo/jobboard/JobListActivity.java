package fr.ygo.jobboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;

/**
 * Created by Yoann on 03/10/2014.
 */
public class JobListActivity extends ActionBarActivity {

    private boolean hasTwoPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // load the view
        setContentView(R.layout.activity_job_list);

        // check if view has the detail fragment layout
        if(findViewById(R.id.fragment_job_detail_layout) != null){
            hasTwoPane = true;
            // set the choice mode to "single"
            ((JobListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.fragment_job_list))
                    .setListSingleSelectionMode(true);
        }
    }

    public void onJobSelected (String id){

        if(hasTwoPane){

            Bundle bundle=new Bundle();
            bundle.putString(JobDetailFragment.ARG_JOB_ID, id);

            // Create fragment and add bundle
            Fragment fragment = new JobDetailFragment();
            fragment.setArguments(bundle);

            // Load fragment in the Activity
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_job_detail_layout, fragment)
                    .commit();
        }
        else {
            // create an intent with job id extra to launch the detail activity
            Intent intent=new Intent(this, JobDetailActivity.class);
            intent.putExtra(JobDetailFragment.ARG_JOB_ID, id);

            // send intent to start activity
            startActivity(intent);
        }
    }
}