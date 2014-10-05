package fr.ygo.jobboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import fr.ygo.jobboard.data.Job;
import fr.ygo.jobboard.data.JobsContent;

/**
 * Created by Yoann on 03/10/2014.
 */
public class JobListFragment extends ListFragment {

    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // load the view
        return inflater.inflate(R.layout.fragment_job_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // get the application context
        mContext = getActivity();

        // get the job list
        List<Job> jobs = JobsContent.JOBS;

        // display the job list
        setListAdapter(new ArrayAdapter<Job>(mContext, android.R.layout.simple_list_item_1, jobs));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        // get the selected job
        Job selectedJob = (Job)getListView().getItemAtPosition(position);

        // create an intent with job id extra to launch the detail activity
        Intent intent=new Intent(mContext, JobDetailActivity.class);
        intent.putExtra(JobDetailFragment.ARG_JOB_ID, selectedJob.getId());

        // send intent to start activity
        startActivity(intent);
    }
}
