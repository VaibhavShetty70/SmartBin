package com.example.priti.smartbin;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import javax.xml.transform.Result;

public class CustomAddBinDialog extends DialogFragment {
    TextView txt_uid,txt_name, txt_info;
    String uid,name, info;
    long value;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        final View view = inflater.inflate(R.layout.custom_dialog, null);
        builder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // to do when user presses Add
                        txt_uid=(TextView) view.findViewById(R.id.binUID);
                        txt_name = (TextView) view.findViewById(R.id.binname);
                        txt_info = (TextView) view.findViewById(R.id.bininfo);
                        uid=txt_uid.getText().toString();
                        name = txt_name.getText().toString();
                        info = txt_info.getText().toString();

                        Data data = new Data();
                        data.setBinId(uid);
                        data.setBinName(name);
                        data.setBinInfo(info);
                        DataLab.get(getActivity()).addData(data);
                        sendResult(Activity.RESULT_OK);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        /*  CustomAddBinDialog.this.getDialog().cancel();*/
                        dialog.dismiss();
                    }
                });
        return builder.create();
    }
    private void sendResult(int resultCode){
        if(getTargetFragment()==null){
            return;
        }
        Intent intent =new Intent();
        getTargetFragment().onActivityResult(getTargetRequestCode(),resultCode,intent);
    }
}
