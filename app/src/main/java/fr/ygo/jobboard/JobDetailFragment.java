package fr.ygo.jobboard;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import fr.ygo.jobboard.data.Job;
import fr.ygo.jobboard.data.JobsContent;

/**
 * Created by Yoann on 03/10/2014.
 */
public class JobDetailFragment extends Fragment {

    public static String ARG_JOB_ID = "job_id";

    private TextView mTitleText ;
    private TextView mDescriptionText ;
    private TextView mDurationText ;
    private TextView mStartText ;
    private TextView mPriceText ;
    private TextView mLocationText ;
    private TextView mPublishText ;

    private Button mSubmitButton;

    // TEST FRAGMENT
    private static Job ITEM_TEST = JobsContent.JOBS.get(0);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // load the view
        return inflater.inflate(R.layout.fragment_job_detail, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // get arguments sent by the activity
        Bundle args = getArguments();

        // get application context
        Context context=getActivity();

        // get the loaded view
        View parent = getView();

        // fetch views
        mTitleText = (TextView) parent.findViewById(R.id.job_title);
        mDescriptionText = (TextView) parent.findViewById(R.id.job_description);
        mDurationText = (TextView) parent.findViewById(R.id.job_duration);
        mStartText = (TextView) parent.findViewById(R.id.job_start);
        mPriceText = (TextView) parent.findViewById(R.id.job_price);
        mLocationText = (TextView) parent.findViewById(R.id.job_location);
        mPublishText = (TextView) parent.findViewById(R.id.job_publish);

        mSubmitButton = (Button) parent.findViewById(R.id.job_submit);

        //Job job = ITEM_TEST;
        Job job = JobsContent.findJob(args.getString(ARG_JOB_ID));

        // push job data in the view
        mTitleText.setText(job.getTitle());
        mDescriptionText.setText(job.getDescription());
        mDurationText.setText(job.getDuration());
        mStartText.setText(job.getStart());
        mPriceText.setText(job.getPrice());
        mLocationText.setText(job.getLocation());

        long created = job.getCreated().getTime();
        mPublishText.setText(getString(
                R.string.job_publish,
                job.getPublisher(),
                DateUtils.getRelativeTimeSpanString(created, System.currentTimeMillis(), DateUtils.DAY_IN_MILLIS)
        ));
    }
}
