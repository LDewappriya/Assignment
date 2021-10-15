package com.assignment.biomatricauthentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {

    //ui views
    private TextView authStatusTv;
    private Button authBtn;

    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init UI views
        authStatusTv = findViewById(R.id.authStatusTv);
        authBtn = findViewById(R.id.authBtn);

        //init biomatric

        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(MainActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);

                //Error authentication, stop tasks that requires auth
                authStatusTv.setText("Authentication Error: "+ errString);
                Toast.makeText(MainActivity.this, "Authentication Error: "+ errString, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                //authentication succeed, continue tasks that requires auth
                authStatusTv.setText("Authentication succeed...!");
                Toast.makeText(MainActivity.this, "Authentication succeed...!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                //fail authentication, stop tasks that requires auth
                authStatusTv.setText("Authentication failed...!");
                Toast.makeText(MainActivity.this, "Authentication failed...!", Toast.LENGTH_SHORT).show();
            }
        });

        //setup title,description on auth dialog
        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biomatric Authentication ")
                .setSubtitle("Login using fingerprint Authentication")
                .setNegativeButtonText("User password app")
                .build();



        //Handle authBtn click, start authentication
        authBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //show auth dialog
                biometricPrompt.authenticate(promptInfo);

            }
        });


    }
}