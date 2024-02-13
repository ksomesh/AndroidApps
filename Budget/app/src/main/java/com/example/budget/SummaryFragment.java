package com.example.budget;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class SummaryFragment extends Fragment {

    private View rootView;
    DatabaseHelper db;
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

        textViewAssetVal.setText(String.valueOf(summary.fTotalAsset));
        textViewLiabVal.setText(String.valueOf(summary.fTotalLiability));
        textViewIncomeVal.setText(String.valueOf(summary.fTotalIncome));
        textViewExpenseVal.setText(String.valueOf(summary.fTotalExpense));
        return rootView;
    }
}