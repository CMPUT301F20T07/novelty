package com.example.novelty.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.novelty.R;

public class FilterDialog extends DialogFragment {

    @NonNull
    @Override
   public Dialog onCreateDialog (@Nullable Bundle savedInstanceState) {

        final String [] status = getActivity().getResources().getStringArray(R.array.status);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setItems(status, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                Toast.makeText(getActivity(), "you choose: " + status[i], Toast.LENGTH_SHORT).show();
            }
        });

        return builder.create();

   }
}
