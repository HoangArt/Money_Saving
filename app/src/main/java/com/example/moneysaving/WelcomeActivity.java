package com.example.moneysaving;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.moneysaving.databinding.ActivityWelcomeBinding;
import com.example.moneysaving.viewmodel.WelcomeViewModel;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        ActivityWelcomeBinding binding = ActivityWelcomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Nếu người dùng đã đăng nhập, chuyển hướng đến MainActivity
        WelcomeViewModel viewModel = new ViewModelProvider(this).get(WelcomeViewModel.class);
        viewModel.isUserLoggedIn.observe(this, isLoggedIn -> {
            if (Boolean.TRUE.equals(isLoggedIn)) {
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }
        });

        // Xử lý các nút đăng ký, đăng nhập
        binding.btnRegister.setOnClickListener(
                view -> startActivity(new Intent(this, RegisterActivity.class)));
        binding.btnLogin.setOnClickListener(
                view -> startActivity(new Intent(this, LoginActivity.class)));
    }
}