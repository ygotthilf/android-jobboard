package fr.ygo.jobboard;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
                            Toast.makeText(getActivity(), "applied to the job "+jobId+" with email "+emailText.getText()+" and message "+messageText.getText(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton(android.R.string.cancel,null);
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }
