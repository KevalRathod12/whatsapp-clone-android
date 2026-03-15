package com.example.whatsappclone;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * LoginActivity — App nu pehlu screen
 *
 * Je user pehela thi logged-in che teno check kare che.
 * Jya Firebase Auth use kari ne email/password thi login thay che.
 *
 * Flow:
 *   1. App open thay → LoginActivity launch thay
 *   2. Je already logged in hoy → directly MainActivity ma jav
 *   3. Email + Password enter karo → Login button → Firebase verify kare
 *   4. Success → MainActivity
 *   5. Fail → Error toast show
 */
public class LoginActivity extends AppCompatActivity {

    // UI Views
    private com.google.android.material.textfield.TextInputEditText etEmail, etPassword;
    private Button btnLogin;
    private TextView tvRegister;

    // Firebase Authentication instance
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Get Firebase Auth instance
        mAuth = FirebaseAuth.getInstance();

        // --- Auto-login check ---
        // Je user pehela thi logged in hoy to seedhuj MainAcitivity ma jav
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            goToMain();
            return;
        }

        // Connect UI elements
        etEmail    = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin   = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);

        // Login button click
        btnLogin.setOnClickListener(v -> loginUser());

        // "Register" link click → open RegisterActivity
        tvRegister.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });
    }

    /**
     * loginUser() — Firebase thi email/password verify kare che
     */
    private void loginUser() {
        String email    = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Basic validation
        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Email is required");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Password is required");
            return;
        }

        // Firebase sign in
        btnLogin.setEnabled(false); // Prevent double click
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    // Login successful!
                    Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();
                    goToMain();
                })
                .addOnFailureListener(e -> {
                    // Login failed
                    btnLogin.setEnabled(true);
                    Toast.makeText(this, "Login failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }

    /**
     * goToMain() — MainActivity par jaav ane LoginActivity band karo
     */
    private void goToMain() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
