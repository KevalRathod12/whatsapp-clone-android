package com.example.whatsappclone;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * RegisterActivity — Nav user create kare che
 *
 * Steps:
 *   1. Name, Email, Password enter karo
 *   2. Firebase Authentication ma user create thay
 *   3. User nu data /users/{uid}/ ma save thay che Realtime Database ma
 *   4. Auto-login → MainActivity
 */
public class RegisterActivity extends AppCompatActivity {

    private com.google.android.material.textfield.TextInputEditText etName, etEmail, etPassword;
    private Button btnRegister;
    private TextView tvLogin;

    private FirebaseAuth mAuth;
    private DatabaseReference dbRef; // Firebase Realtime Database reference

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize Firebase
        mAuth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference("users");

        // Connect UI
        etName     = findViewById(R.id.etName);
        etEmail    = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);
        tvLogin    = findViewById(R.id.tvLogin);

        btnRegister.setOnClickListener(v -> registerUser());

        // Back to Login link
        tvLogin.setOnClickListener(v -> finish());
    }

    /**
     * registerUser() — Firebase Auth ma new account banave che ane DB ma save kare che
     */
    private void registerUser() {
        String name     = etName.getText().toString().trim();
        String email    = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Validation
        if (TextUtils.isEmpty(name)) {
            etName.setError("Name is required");
            return;
        }
        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Email is required");
            return;
        }
        if (TextUtils.isEmpty(password) || password.length() < 6) {
            etPassword.setError("Password must be at least 6 characters");
            return;
        }

        btnRegister.setEnabled(false);

        // Step 1: Firebase Auth ma account banavo
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    String uid = authResult.getUser().getUid();

                    // Step 2: /users/{uid}/ ma UserModel save karo
                    UserModel user = new UserModel(uid, name, email);
                    dbRef.child(uid).setValue(user)
                            .addOnSuccessListener(aVoid -> {
                                Toast.makeText(this, "Registered successfully!", Toast.LENGTH_SHORT).show();
                                // Step 3: Go to main screen
                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            })
                            .addOnFailureListener(e -> {
                                btnRegister.setEnabled(true);
                                Toast.makeText(this, "DB Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                            });
                })
                .addOnFailureListener(e -> {
                    btnRegister.setEnabled(true);
                    Toast.makeText(this, "Registration failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }
}
