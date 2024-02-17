package com.example.budget;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.budget.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {

    RelativeLayout mainLayout;
    public boolean bLoggedIn = false;
    BottomNavigationView bottomNavigationView;
    AssetLiabilityFragment assetLiabilityFragment = new AssetLiabilityFragment();
    IncomeFragment incomeFragment = new IncomeFragment();
    ExpenseFragment expenseFragment = new ExpenseFragment();
    TransferFragment transferFragment = new TransferFragment();
    SummaryFragment summaryFragment = new SummaryFragment();

    BiometricPrompt biometricPrompt;
    BiometricPrompt.PromptInfo promptInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainLayout = findViewById(R.id.main_layout);
        ImageView ivToolBarMenu = findViewById(R.id.iv_toolbar_menu);

        ivToolBarMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(getApplicationContext(), SideBarActivity.class);
                startActivity(intent);
            }
        });

        if(!bLoggedIn) {
            BiometricManager biometricManager = BiometricManager.from(this);
            switch (biometricManager.canAuthenticate()) {
                case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                    Toast.makeText(this, "Device does not support biometric", Toast.LENGTH_SHORT).show();
                    break;
                case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                    Toast.makeText(this, "Device biometric is unavailable", Toast.LENGTH_SHORT).show();
                    break;
                case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                    Toast.makeText(this, "No biometric enrolled", Toast.LENGTH_SHORT).show();
                    break;
            }

            Executor executor = ContextCompat.getMainExecutor(this);
            biometricPrompt = new BiometricPrompt(MainActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
                @Override
                public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                    super.onAuthenticationError(errorCode, errString);
                    if (errorCode == BiometricPrompt.ERROR_USER_CANCELED) {
                        finishAndRemoveTask();
                    }
                }

                @Override
                public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                    super.onAuthenticationSucceeded(result);
                    Toast.makeText(MainActivity.this, "Login successfull", Toast.LENGTH_SHORT).show();
                    bLoggedIn = true;
                    mainLayout.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAuthenticationFailed() {
                    super.onAuthenticationFailed();
                }
            });


            promptInfo = new BiometricPrompt.PromptInfo.Builder().setTitle("Budget").setDeviceCredentialAllowed(true).build();
            biometricPrompt.authenticate(promptInfo);
        }
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.expense);
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