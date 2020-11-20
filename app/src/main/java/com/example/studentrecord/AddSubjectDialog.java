package com.example.studentrecord;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddSubjectDialog extends AppCompatDialogFragment {

    private EditText subjectEditText,TimeEditText;
    private addDialogListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (addDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Include the addDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.layout_adddialog,null);
        alertDialog.setView(view)
                .setTitle("Add Subject")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String subject = subjectEditText.getText().toString();
                        String time = subjectEditText.getText().toString();
                        listener.valesFromEditText(subject,time);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AddSubjectDialog.this.getDialog().cancel();
                    }
                });

        subjectEditText = view.findViewById(R.id.subjectEditText);
        TimeEditText = view.findViewById(R.id.timeEditText);

        return alertDialog.create();
    }

    public interface addDialogListener{
        void valesFromEditText(String subject, String time);
    }
}
