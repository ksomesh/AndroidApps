package com.example.budget;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.budget.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    AssetLiabilityFragment assetLiabilityFragment = new AssetLiabilityFragment();
    IncomeFragment incomeFragment = new IncomeFragment();
    ExpenseFragment expenseFragment = new ExpenseFragment();
    TransferFragment transferFragment = new TransferFragment();
    SummaryFragment summaryFragment = new SummaryFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        replaceFragment(new ExpenseFragment());

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    if(item.getItemId() == R.id.navigation_asset_liability) {
                        replaceFragment(new AssetLiabilityFragment());
                        return true;
                    }
                    else if(item.getItemId() == R.id.income) {
                        replaceFragment(new IncomeFragment());
                        return true;
                    }
                    else if (item.getItemId() == R.id.expense) {
                        replaceFragment(new ExpenseFragment());
                        return true;
                    }
                    else if(item.getItemId() == R.id.transfer) {
                        replaceFragment(new TransferFragment());
                        return true;
                    }
                    else if(item.getItemId() == R.id.summary) {
                        replaceFragment(new SummaryFragment());
                        return true;
                    }
                    else {
                        return false;
                    }
            }
        });
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

}