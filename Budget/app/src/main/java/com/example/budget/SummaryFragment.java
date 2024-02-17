package com.example.budget;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;


public class SummaryFragment extends Fragment {

    private View rootView;
    DatabaseHelper db;
    Button getSummaryBtn;
    Button btnReset;
    Summary summary;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_summary, container, false);
        db = new DatabaseHelper(getContext());
        summary = db.getSummary();
        TextView textViewAssetVal = rootView.findViewById(R.id.tv_tot_asset_val);
        TextView textViewLiabVal = rootView.findViewById(R.id.tv_tot_liab_val);
        TextView textViewIncomeVal = rootView.findViewById(R.id.tv_tot_inc_val);
        TextView textViewExpenseVal = rootView.findViewById(R.id.tv_tot_exp_val);
        getSummaryBtn = rootView.findViewById(R.id.btn_downloadSummary);
        btnReset = rootView.findViewById(R.id.btn_reset);
        Spinner spSummaryAcc = rootView.findViewById(R.id.sp_summary_acc);
        spSummaryAcc.setAdapter(SpinnerItemFetcher.fetchSpinnerItem(getContext(), SpinnerItemFetcher.ACCOUNTS));
        TextView tvAccSummary = rootView.findViewById(R.id.tv_summary_acc_val);


        textViewAssetVal.setText(String.valueOf(summary.fTotalAsset));
        textViewLiabVal.setText(String.valueOf(summary.fTotalLiability));
        textViewIncomeVal.setText(String.valueOf(summary.fTotalIncome));
        textViewExpenseVal.setText(String.valueOf(summary.fTotalExpense));


        spSummaryAcc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String strAccName = spSummaryAcc.getSelectedItem().toString();
                tvAccSummary.setText(Double.toString(db.getBalanceForAcc(strAccName)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        getSummaryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.exportAllTablesToCSV();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.exportAllTablesToCSV();
                db.resetFinDB();
                SharedPrefHandler.clear(getContext());
                textViewAssetVal.setText(String.valueOf(0.0));
                textViewLiabVal.setText(String.valueOf(0.0));
                textViewIncomeVal.setText(String.valueOf(0.0));
                textViewExpenseVal.setText(String.valueOf(0.0));
                tvAccSummary.setText(String.valueOf(0.0));
            }
        });
        return rootView;
    }
}