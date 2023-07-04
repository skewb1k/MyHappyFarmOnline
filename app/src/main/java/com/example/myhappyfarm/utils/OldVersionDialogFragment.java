package com.example.myhappyfarm.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import com.example.myhappyfarm.R;

public class OldVersionDialogFragment extends DialogFragment {
    private IOldVersion iOldVersion;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        iOldVersion = (IOldVersion) context;
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        StringBuilder sb = new StringBuilder();
        sb
                .append(getResources().getString(R.string.your_version))
                .append(" ")
                .append(getArguments().getString("currentVersion"))
                .append("\n")
                .append(getResources().getString(R.string.latest_version))
                .append(" ")
                .append(getArguments().getString("dbVersion"));
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return builder
                .setTitle(getResources().getString(R.string.oldversion_title))
                .setMessage(sb.toString())
                .setPositiveButton(getResources().getString(R.string.exit_title), (dialog, which) -> iOldVersion.exitApp())
                .create();
    }
}