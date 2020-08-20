package org.communitycookerfoundation.communitycookerfoundation.ui.admin;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.communitycookerfoundation.communitycookerfoundation.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddCookerFragment  extends DialogFragment {
    AddCookerListener mListener;


    public AddCookerFragment(AddCookerListener addCookerListener) {
        mListener = addCookerListener;

        // Required empty public constructor
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        // Get the layout inflater
        LayoutInflater inflater = requireParentFragment().getLayoutInflater();
        View addUserDialogView = inflater.inflate(R.layout.dialog_add_cooker, null);
        // Inflate and set the layout for the dialog
        final EditText userName = addUserDialogView.findViewById(R.id.add_cooker_title);
        final EditText userEmailAddress = addUserDialogView.findViewById(R.id.add_user_email);
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(addUserDialogView)
                // Add action buttons
                .setPositiveButton(R.string.add_cooker_dial, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if (TextUtils.isEmpty(userName.getText().toString())) {
                            Toast myToast = Toast.makeText(getContext(), "Please fill in the user name ", Toast.LENGTH_SHORT);
                            myToast.show();
                        } else if(TextUtils.isEmpty(userEmailAddress.getText().toString())) {
                            Toast myToast = Toast.makeText(getContext(), "Please fill in the email ", Toast.LENGTH_SHORT);
                            myToast.show();
                        }
                          else {
                            mListener.onDialogPositiveClick(userName.getText().toString(), userEmailAddress.getText().toString());// sign in the user ...
                        }




                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AddCookerFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        /*try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (AddCookerListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement NoticeDialogListener");
        }*/
    }

    public interface AddCookerListener{
        void onDialogPositiveClick(String userTitle, String userEmail);
    }

}
