package com.kaleido.cesmarttracker.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kaleido.cesmarttracker.R;

import java.util.Calendar;
import java.util.HashMap;

public class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    int yy,mm,dd;



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        yy = calendar.get(Calendar.YEAR);
        mm = calendar.get(Calendar.MONTH);
        dd = calendar.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, yy, mm, dd);
    }

    public void onDateSet(DatePicker view, int yy, int mm, int dd) {
        populateSetDate(yy, mm+1, dd);
    }
    public void populateSetDate(int year, int month, int day) {
        TextView text = (TextView)getActivity().findViewById(R.id.showDetail);
        text.setText("Due Date : " + day+" / "+month+" / "+year);
        Toast toast = Toast.makeText(getActivity(),"Due Date : " + day+" / "+month+" / "+year,Toast.LENGTH_LONG);
        toast.show();
    }
}