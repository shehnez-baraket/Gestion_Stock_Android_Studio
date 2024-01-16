package com.example.projetmobile_geststock;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends AppCompatActivity {
    EditText usernameEditText, passwordEditText;
    Button loginButton;
    AuthManager authManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);

        authManager = AuthManager.getInstance();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                try {
                    if (authManager.authenticate(username, password)) {
                        // Authentification réussie, passer à l'activité principale
                        Intent intent = new Intent(LoginActivity.this, ListProduct.class);
                        startActivity(intent);
                        finish(); // Ferme l'activité d'authentification pour qu'elle ne puisse pas être retournée en arrière
                    }
                } catch (AuthException e) {
                    // Afficher un Toast avec le message d'erreur
                    Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}