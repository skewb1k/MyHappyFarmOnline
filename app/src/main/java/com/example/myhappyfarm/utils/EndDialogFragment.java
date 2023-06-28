package com.example.myhappyfarm.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import com.example.myhappyfarm.R;

public class EndDialogFragment extends DialogFragment {
    private IEndGame iEndGame;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        iEndGame = (IEndGame) context;
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return builder
                .setTitle(getResources().getString(R.string.endgame_title))
                .setMessage(getArguments().getString("message"))
                .setPositiveButton(getResources().getString(R.string.ok_title), (dialog, which) -> iEndGame.gameEnd())
                .create();
    }
}