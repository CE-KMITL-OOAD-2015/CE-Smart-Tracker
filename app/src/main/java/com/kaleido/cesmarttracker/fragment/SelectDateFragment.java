package com.kaleido.cesmarttracker.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.kaleido.cesmarttracker.R;

import java.util.Calendar;

public class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    int yy,mm,dd;
    String abcd[] = {"","",""};


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        yy = calendar.get(Calendar.YEAR);
        mm = calendar.get(Calendar.MONTH);
        dd = calendar.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, yy, mm, dd);
    }
    public void onDateSet(DatePicker view, int yy, int mm, int dd) {
        populateSetDate(yy, mm + 1, dd);
    }
    public void populateSetDate(int year, int month, int day) {

        TextView text2 = (TextView)getActivity().findViewById(R.id.subItem);
        text2.setText("due date : " + day + " / " + month + " / " + year);
        TextView text = (TextView)getActivity().findViewById(R.id.hiddenText);
        text.setText("due date : " + day + " / " + month + " / " + year);
        Toast toast = Toast.makeText(getActivity(),"due date : " + day+" / "+month+" / "+year,Toast.LENGTH_LONG);
        toast.show();
    }

}