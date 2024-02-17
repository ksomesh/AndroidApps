package com.example.budget;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AssetLiabilityFragment extends Fragment  {

    private EditText dateEditText;
    private EditText descriptionEditText;
    private EditText amountEditText;

    private Spinner assetLiabSpinner;
    private View rootView;
    DatabaseHelper db;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_asset_liability, container, false);
        db = new DatabaseHelper(getContext());

        dateEditText = rootView.findViewById(R.id.al_Date);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        dateEditText.setText(sdf.format(new Date()));
        // Set an OnClickListener for the date EditText
        dateEditText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // Create a DatePickerDialog
                Calendar myCalendar = Calendar.getInstance();

                DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Handle the selected date (e.g., update the EditText)
                        String formattedDate = String.format(Locale.US, "%02d/%02d/%04d", dayOfMonth, month + 1, year);
                        dateEditText.setText(formattedDate);
                    }
                };
                new DatePickerDialog(
                        getContext(),
                        dateListener,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)
                ).show();
            }
        });

        descriptionEditText = rootView.findViewById(R.id.al_desc);
        amountEditText = rootView.findViewById(R.id.al_amount);
        amountEditText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        assetLiabSpinner = rootView.findViewById(R.id.spinner_asset_liab);

        Button addBtn = rootView.findViewById(R.id.al_add_button);
        addBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {

                String strDesc = descriptionEditText.getText().toString();
                Float fAmount = Float.parseFloat(amountEditText.getText().toString());
                String strType = assetLiabSpinner.getSelectedItem().toString();
                String date = dateEditText.getText().toString();
                /*
                SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
                Date date = null;
                try {
                    date = format.parse(dateEditText.getText().toString());
                } catch (ParseException e) {
                    Log.e("Asset Liability FrameWork", "Failed to cast date");
                }*/
                //Todo : test the authenticity of data

                descriptionEditText.setText(null);
                amountEditText.setText(null);
                dateEditText.setText("Select a date");

                db.insertDataToAssetLiabilityTable(date, strDesc, fAmount, strType);

                float fAsset = Float.parseFloat("0");
                float fLiability = Float.parseFloat("0");
                if(strType.equals("Asset")) {
                    fAsset = fAmount;
                } else {
                    fLiability = fAmount;
                }

                db.queryAndUpdateFinalTable(fAsset, fLiability, 0, 0);

            }
        });


        return rootView;
    }
}