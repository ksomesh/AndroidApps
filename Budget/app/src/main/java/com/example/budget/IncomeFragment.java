package com.example.budget;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Calendar;
import java.util.Locale;


public class IncomeFragment extends Fragment {

    private EditText dateEditText;
    private EditText etDescription;
    private EditText etAmount;

    private Spinner spDest;
    private Spinner spTag;


    private View rootView;
    DatabaseHelper db;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_income, container, false);
        db = new DatabaseHelper(getContext());
        dateEditText = rootView.findViewById(R.id.income_Date);

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
                        String formattedDate = String.format(Locale.US, "%02d/%02d/%04d", month + 1, dayOfMonth, year);
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

        Button addBtn = rootView.findViewById(R.id.income_add_button);
        etDescription = rootView.findViewById(R.id.income_desc);
        etAmount= rootView.findViewById(R.id.income_amount);
        spDest = rootView.findViewById(R.id.spinner_income_dest);
        spTag = rootView.findViewById(R.id.spinner_income_tag);

        addBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {

                String strDesc = etDescription.getText().toString();
                Float fAmount = Float.parseFloat(etAmount.getText().toString());
                String strTag = spTag.getSelectedItem().toString();
                String strDest = spDest.getSelectedItem().toString();
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
                
                etDescription.setText(null);
                etAmount.setText(null);
                dateEditText.setText("Select a date");

                db.insertDataToIncomeExpenseTable(date, strDesc, fAmount, strTag, strDest, "income");
                db.queryAndUpdateFinalTable(0, 0, fAmount, 0);
            };
        });

        return rootView;
    }
}