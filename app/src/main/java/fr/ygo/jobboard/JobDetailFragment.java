package fr.ygo.jobboard;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import fr.ygo.jobboard.data.Job;
import fr.ygo.jobboard.data.JobApi;

/**
 * Created by Yoann on 03/10/2014.
 */
public class JobDetailFragment extends Fragment {

    public static String ARG_JOB_ID = "job_id";

    private View mJobLoader;
    private View mJobContent;

    private TextView mTitleText ;
    private TextView mDescriptionText ;
    private TextView mDurationText ;
    private TextView mStartText ;
    private TextView mPriceText ;
    private TextView mLocationText ;
    private TextView mPublishText ;

    private Button mSubmitButton;

    private JobDetailTask mTask;

    private Job mJob;

    private Context mContext ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // load the view
        return inflater.inflate(R.layout.fragment_job_detail, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setHasOptionsMenu(true);

        // get arguments sent by the activity
        Bundle args = getArguments();

        // get application context
        mContext=getActivity();

        // get the loaded view
        View parent = getView();

        // fetch views
        mJobLoader = parent.findViewById(R.id.job_loader);
        mJobContent = parent.findViewById(R.id.job_content);

        mTitleText = (TextView) parent.findViewById(R.id.job_title);
        mDescriptionText = (TextView) parent.findViewById(R.id.job_description);
        mDurationText = (TextView) parent.findViewById(R.id.job_duration);
        mStartText = (TextView) parent.findViewById(R.id.job_start);
        mPriceText = (TextView) parent.findViewById(R.id.job_price);
        mLocationText = (TextView) parent.findViewById(R.id.job_location);
        mPublishText = (TextView) parent.findViewById(R.id.job_publish);

        mSubmitButton = (Button) parent.findViewById(R.id.job_submit);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showJobApplyDialog();
            }
        });

        Anim.hide(mContext, mJobContent);
        Anim.show(mContext, mJobLoader);

        mTask = new JobDetailTask();
        mTask.execute(args.getString(ARG_JOB_ID));
    }

    private void updateView (Job job){
        if(mJob!=null) {
            mTitleText.setText(mJob.getTitle());
            mDescriptionText.setText(mJob.getDescription());
            mDurationText.setText(mJob.getDuration());
            mStartText.setText(mJob.getStart());
            mPriceText.setText(mJob.getPrice());
            mLocationText.setText(mJob.getLocation());

            long created = mJob.getCreated().getTime();
            mPublishText.setText(getString(
                    R.string.job_publish,
                    mJob.getPublisher(),
                    DateUtils.getRelativeTimeSpanString(created, System.currentTimeMillis(), DateUtils.DAY_IN_MILLIS)
            ));
        }

        Anim.hide(mContext, mJobLoader);
        Anim.show(mContext, mJobContent);
    }

    private void showJobApplyDialog (){
        DialogFragment fragment = new JobApplyDialogFragment();

        Bundle bundle=new Bundle();
        bundle.putString(JobApplyDialogFragment.ARG_JOB_ID, mJob.getId());
        fragment.setArguments(bundle);

        fragment.show(getFragmentManager(), "apply");
    }

    private void showErrorDialog (){
        DialogFragment fragment = new ErrorDialogFragment();
        fragment.setTargetFragment(this, 0);
        fragment.show(getFragmentManager(), "error");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.job_detail, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.share :
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "I found this job for you : "+mJob.getTitle()+". See on http://www.imacjobboard.com/job/"+mJob.getId());
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                return true;
            case R.id.apply:
                showJobApplyDialog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        if(mTask!=null){
            mTask.cancel(true);
        }
        super.onDestroy();
    }

    public class JobDetailTask extends AsyncTask<String, Void, Job> {

        private boolean error;

        @Override
        protected Job doInBackground(String... ids) {
            try {
                return JobApi.getJob(getActivity(), ids[0]);
            } catch (Exception e) {
                e.printStackTrace();
                error = true;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Job job) {

            if(error){
                showErrorDialog();
            }

            // push job data in the view
            mJob = job;
            updateView(job);
        }
    }
}
