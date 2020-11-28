package com.example.novelty.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.novelty.R;

public class FilterDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        final String[] status = getActivity().getResources().getStringArray(R.array.status);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setItems(status, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                Intent intent;
                switch (i) {
                    case 0:
                        break;
                    case 1:
                        intent = new Intent(getActivity(), MyAcceptActivity.class);

                        getActivity().startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(getActivity(), MyRequestActivity.class);
                        getActivity().startActivity(intent);
                        break;
                    case 3:
                        break;
                }

            }
        });

        return builder.create();

    }
}
