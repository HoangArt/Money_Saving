package com.example.moneysaving;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.moneysaving.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.nav_host_fragment_activity_main);
        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(binding.navView, navController);

        binding.addExpenseFloatingButton.setOnClickListener(view -> {
            if (navController.getCurrentDestination().getId() == R.id.navigation_home) {
                startActivity(new Intent(this, AddExpenseActivity.class));
            }
        });

        // Nút đăng xuất tạm thời
//        binding.btnLogout.setOnClickListener(v -> {
//            FirebaseAuth.getInstance().signOut();
//
//            // Chuyển về Welcome và xóa history
//            Intent intent = new Intent(this, WelcomeActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);
//        });
    }
}