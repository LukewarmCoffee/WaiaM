package com.example.waiam;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

public class DeleteAlertFragment extends DialogFragment {
    public interface ResultDialogListener{
        void onDialogPositiveClick(int position);
    }

    ResultDialogListener mListener;

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        try{
            mListener = (ResultDialogListener)context;
        } catch (ClassCastException e){
            throw new ClassCastException(getActivity().toString() + "does not implement resultdialoglistener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        Bundle bundle = getArguments();
        final int position = bundle.getInt("position");
        boolean nightmode = bundle.getBoolean("nightmode");
        int theme;
        if (nightmode)
            theme = R.style.DialogThemeDark;
        else
            theme = R.style.DialogThemeLight;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), theme);
        builder.setMessage(R.string.delete_income_dialog)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //send yes
                        mListener.onDialogPositiveClick(position);
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //send nudes
                    }
                });
        return builder.create();
    }
}
