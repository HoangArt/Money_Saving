package com.example.moneysaving;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.moneysaving.databinding.ActivityLoginBinding;
import com.example.moneysaving.viewmodel.LoginViewModel;
import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        setSupportActionBar(findViewById(R.id.topAppBar));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(null);
        }

        viewModel.isLoading.observe(this, isLoading ->
                binding.loading.setVisibility(isLoading ? View.VISIBLE : View.GONE));

        viewModel.loginErrorMessage.observe(this, msg -> {
            if (msg != null && !msg.isEmpty()) {
                Snackbar.make(binding.getRoot(), msg, Snackbar.LENGTH_LONG).show();
            }
        });

        viewModel.loginSuccess.observe(this, success -> {
            if (Boolean.TRUE.equals(success)) {
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }
        });

        binding.btnLogin.setOnClickListener(view -> {
            String email = binding.email.getEditText() != null ?
                    binding.email.getEditText().getText().toString().trim() : "";
            String password = binding.password.getEditText() != null ?
                    binding.password.getEditText().getText().toString().trim() : "";

            if (email.isEmpty()) {
                binding.email.setError(getString(R.string.error_email_field_empty));
                return;
            } else {
                binding.email.setError(null);
                binding.email.setErrorEnabled(false);
            }
            if (password.isEmpty()) {
                binding.password.setError(getString(R.string.error_password_field_empty));
                return;
            } else {
                binding.password.setError(null);
                binding.password.setErrorEnabled(false);
            }

            viewModel.login(email, password);

        });

        binding.btnHaveNoAccount.setOnClickListener(view -> {
            startActivity(new Intent(this, RegisterActivity.class));
            finish();
        });
        binding.btnForgotPassword.setOnClickListener(
                view -> startActivity(new Intent(this, ResetPasswordActivity.class)));

        boolean reset_email_sent = getIntent().getBooleanExtra("reset_email_sent", false);
        if (reset_email_sent) Snackbar.make(binding.getRoot(), R.string.email_sent, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(this, WelcomeActivity.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}