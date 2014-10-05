package fr.ygo.jobboard;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

/**
 * Created by Yoann on 03/10/2014.
 */
public class JobListActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // load the view
        setContentView(R.layout.activity_job_list);
    }
}