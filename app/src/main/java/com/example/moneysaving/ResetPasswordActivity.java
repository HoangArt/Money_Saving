package com.example.moneysaving;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.moneysaving.databinding.ActivityChangePasswordBinding;
import com.example.moneysaving.viewmodel.ResetPasswordViewModel;
import com.google.android.material.snackbar.Snackbar;

public class ResetPasswordActivity extends AppCompatActivity {
    private ActivityChangePasswordBinding binding;
    private ResetPasswordViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityChangePasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(ResetPasswordViewModel.class);
        setSupportActionBar(findViewById(R.id.topAppBar));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(null);
        }

        viewModel.isLoading.observe(this, isLoading ->
                        binding.loading.setVisibility(isLoading ? View.VISIBLE : View.GONE));

        // Thay đổi mật khẩu thành công
        viewModel.resetPasswordSuccess.observe(this, success -> {
            if (success != null && success) {
                Intent intent = new Intent(this, LoginActivity.class);
                intent.putExtra("reset_email_sent", true);
                startActivity(intent);
                finish();
            }
        });

        binding.btnResetPassword.setOnClickListener(view -> {
            String email = binding.email.getEditText().getText().toString().trim();

            if (email.isEmpty()) {
                Snackbar.make(binding.getRoot(), R.string.error_email_field_empty, Snackbar.LENGTH_LONG).show();
                return;
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Snackbar.make(binding.getRoot(), R.string.error_invalid_email, Snackbar.LENGTH_LONG).show();
                return;
            }

            viewModel.forgotPassword(email);
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}