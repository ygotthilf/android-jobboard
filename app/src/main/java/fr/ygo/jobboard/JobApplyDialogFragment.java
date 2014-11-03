package fr.ygo.jobboard;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import fr.ygo.jobboard.data.JobApi;

/**
 * Created by Yoann on 20/10/2014.
 */
public class JobApplyDialogFragment extends DialogFragment {

    public static String ARG_JOB_ID = "job_id";


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle args = getArguments();

        final String jobId = args.getString(ARG_JOB_ID);

        // Inflate dialog view layout
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_fragment_job_apply, null);

        final EditText emailText = (EditText)dialogView.findViewById(R.id.email);
        final EditText messageText = (EditText)dialogView.findViewById(R.id.message);

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.job_apply_title)
                .setView(dialogView)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        JobApplyTask task=new JobApplyTask(getActivity());
                        task.execute(jobId, emailText.getText().toString(), messageText.getText().toString());
                      }
                })
                .setNegativeButton(android.R.string.cancel,null);
        // Create the AlertDialog object and return it

        return builder.create();
    }

    private static class JobApplyTask extends AsyncTask<String, Void, Boolean> {

        private Context mContext;

        public JobApplyTask (Context context){
            this.mContext=context;
        }

        @Override
        protected Boolean doInBackground(String... params) {

            try {
                return JobApi.postApply(mContext, params[0], params[1], params[2]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if(success){
                Toast.makeText(mContext, "Apply submitted", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(mContext, "Apply failed", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
