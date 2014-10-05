package fr.ygo.jobboard;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;


public class JobDetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // load the view
        setContentView(R.layout.activity_job_detail);

        // if save instance state bundle is not null (orientation change, ...) then the fragment is automatically reloaded
        if(savedInstanceState == null) {

            // Get job id in intent extra
            String id = getIntent().getStringExtra(JobDetailFragment.ARG_JOB_ID);

            // Add job id in fragment bundle
            Bundle bundle=new Bundle();
            bundle.putString(JobDetailFragment.ARG_JOB_ID, id);

            // Create fragment and add bundle
            Fragment fragment = new JobDetailFragment();
            fragment.setArguments(bundle);

            // Load fragment in the Activity
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_job_detail_layout, fragment)
                    .commit();
        }

        // display action bar back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            // if back button is pressed then go to previous activity
            case android.R.id.home :
                NavUtils.navigateUpTo(this, new Intent(this, JobListActivity.class));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
