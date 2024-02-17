package com.example.budget;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SideBarActivity extends AppCompatActivity {

    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_side_bar);

        db = new DatabaseHelper(this);
        ImageView ivProfileBack = findViewById(R.id.iv_toolbar_back);
        EditText etProfileAddSrc = findViewById(R.id.et_profile_src);
        EditText etProfileAddSrcVal = findViewById(R.id.et_profile_src_val);
        Button btnAddSrc = findViewById(R.id.btn_profile_src_add);
        EditText etProfileAddTag = findViewById(R.id.et_profile_tag);
        Button btnProfileAddTag = findViewById(R.id.btn_profile_tag_add);

        btnAddSrc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strSrcAcc = etProfileAddSrc.getText().toString();
                Float fStartBal = Float.parseFloat(etProfileAddSrcVal.getText().toString());
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

                db.insertDataToIncomeExpenseTable(sdf.format(new Date()), "Start balance", fStartBal, "Start balance", strSrcAcc, "income");
                db.queryAndUpdateFinalTable(0, 0, fStartBal, 0);
                SharedPrefHandler.saveData(getApplicationContext(), SharedPrefHandler.ACCOUNTS, strSrcAcc);
                etProfileAddSrcVal.setText(null);
                etProfileAddSrc.setText(null);
            }
        });

        btnProfileAddTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strTag = etProfileAddTag.getText().toString();
                SharedPrefHandler.saveData(getApplicationContext(),SharedPrefHandler.TAGS, strTag);
                etProfileAddTag.setText(null);
            }
        });
        ivProfileBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}