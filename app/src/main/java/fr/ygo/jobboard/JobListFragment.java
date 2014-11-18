package fr.ygo.jobboard;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import fr.ygo.jobboard.data.Job;
import fr.ygo.jobboard.data.JobApi;

/**
 * Created by Yoann on 03/10/2014.
 */
public class JobListFragment extends ListFragment {

    private JobListActivity mActivity;

    private JobListTask mTask;

    private View mJobListLoader;
    private View mJobListEmpty;

    private int mAnimationDuration;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // load the view
        return inflater.inflate(R.layout.fragment_job_list, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAnimationDuration = getResources().getInteger(
                android.R.integer.config_longAnimTime);
    }

    public void setListSingleSelectionMode (boolean selection){
        getListView().setChoiceMode(selection ? ListView.CHOICE_MODE_SINGLE : ListView.CHOICE_MODE_SINGLE);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.job_list, menu);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setHasOptionsMenu(true);

        // get the application context
        mActivity = (JobListActivity)getActivity();

        View parent = getView();

        mJobListLoader = parent.findViewById(R.id.job_list_loader);
        mJobListEmpty = parent.findViewById(R.id.job_list_empty);

        launchTask();
    }


    private void launchTask (){

        Anim.show(mActivity, mJobListLoader);
        Anim.hide(mActivity, mJobListEmpty);
        Anim.hide(mActivity, getListView());

        if(mTask!=null){
            mTask.cancel(true);
        }
        mTask = new JobListTask();
        mTask.execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();
        switch (itemId){

            case R.id.refresh:
                launchTask();
                return true;
            case R.id.settings:
                startActivity(new Intent(mActivity, AppSettings.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        // get the selected job
        Job selectedJob = (Job)getListView().getItemAtPosition(position);

        // delegate click to the activity
        mActivity.onJobSelected(selectedJob.getId());
    }

    private void showErrorDialog (){
        DialogFragment fragment = new ErrorDialogFragment();
        fragment.show(getFragmentManager(), "error");
    }

    private void updateView (List<Job> jobs){

        boolean isEmpty = jobs==null || jobs.isEmpty();

        Anim.hide(mActivity, mJobListLoader);

        if(isEmpty){
            setListAdapter(null);

            Anim.show(mActivity, mJobListEmpty);
            Anim.hide(mActivity, getListView());
        }
        else {
            setListAdapter(new ArrayAdapter<Job>(mActivity, android.R.layout.simple_list_item_activated_1, jobs));

            Anim.hide(mActivity, mJobListEmpty);
            Anim.show(mActivity, getListView());

        }

    }

    @Override
    public void onDestroy() {
        if(mTask!=null){
            mTask.cancel(true);
        }
        super.onDestroy();
    }

    public class JobListTask extends AsyncTask<Void, Void, List<Job>> {

        private boolean error;

        @Override
        protected List<Job> doInBackground(Void... voids) {
            try {
                return JobApi.getJobs(getActivity());
            } catch (Exception e){
                e.printStackTrace();
                error=true;
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Job> jobs) {
            if(error){
                showErrorDialog();
            }
            // display the job list
            updateView(jobs);
        }
    }
}
