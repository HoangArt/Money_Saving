package com.example.moneysaving;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.moneysaving.databinding.ActivityRegisterBinding;
import com.example.moneysaving.viewmodel.RegisterViewModel;
import com.google.android.material.snackbar.Snackbar;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;
    private RegisterViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        setSupportActionBar(binding.topAppBar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(null);
        }

        // Không cho nhập dấu cách trong các ô
        InputFilter noWhiteSpaceFilter = (source, start, end, dest, dstart, dend) -> {
            for (int i = start; i < end; i++) {
                if (Character.isWhitespace(source.charAt(i))) return "";
            }
            return null;
        };

        binding.email.getEditText().setFilters(new InputFilter[]{noWhiteSpaceFilter});
        binding.password.getEditText().setFilters(new InputFilter[]{noWhiteSpaceFilter});

        // Đăng ký
        binding.btnRegister.setOnClickListener(view -> { handleRegister();});

        viewModel.isLoading.observe(this, isLoading ->
                binding.loading.setVisibility(isLoading ? View.VISIBLE : View.GONE));

        // Tạo tài khoản thành công
        viewModel.registerSuccess.observe(this, success -> {
            if (success) {
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }
        });

        viewModel.registerErrorMessage.observe(this, msg -> {
            if (msg == null) return;

            if (msg.equals(getString(R.string.error_email_taken))) {
                binding.email.setError(msg);
            } else {
                Snackbar.make(binding.getRoot(), msg, Snackbar.LENGTH_LONG).show();
            }
        });

        binding.btnHaveAnAccount.setOnClickListener(view -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }

    private void handleRegister() {
        String fullName = binding.fullName.getEditText().getText().toString().trim();
        String email = binding.email.getEditText().getText().toString().trim();
        String password = binding.password.getEditText().getText().toString().trim();

        if (fullName.isEmpty()) {
            binding.fullName.setError(getString(R.string.error_full_name_field_empty));
            return;
        }

        if (email.isEmpty()) {
            binding.email.setError(getString(R.string.error_email_field_empty));
            return;
        }

        if (password.isEmpty()) {
            binding.password.setError(getString(R.string.error_password_field_empty));
            return;
        }

        viewModel.register(fullName, email, password);
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