package com.example.moneysaving;

import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.moneysaving.viewmodel.AddExpenseViewModel;
import com.google.firebase.Timestamp;

public class AddExpenseActivity extends AppCompatActivity {
    private AddExpenseViewModel viewModel;
    private EditText edtAmount, edtCategory, edtNote;
    private RadioButton rbExpense, rbIncome;
    private Button btnSave, btnDate;

    private Timestamp selectedDate = Timestamp.now();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        initViews();
        setupViewModel();
        setupEvents();
    }

    private void initViews() {
        edtAmount = findViewById(R.id.edtAmount);
        edtCategory = findViewById(R.id.edtCategory);
        edtNote = findViewById(R.id.edtNote);
        rbExpense = findViewById(R.id.rbExpense);
        rbIncome = findViewById(R.id.rbIncome);
        btnSave = findViewById(R.id.btnSave);
        btnDate = findViewById(R.id.btnDate);

        rbExpense.setChecked(true);
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this)
                .get(AddExpenseViewModel.class);

        viewModel.getIsSuccess().observe(this, success -> {
            if (success) {
                Toast.makeText(this, "Đã lưu", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        viewModel.getErrorMessage().observe(this, msg -> {
            if (msg != null) {
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupEvents() {
        btnDate.setOnClickListener(v -> showDatePicker());

        btnSave.setOnClickListener(v -> {
            double amount = Double.parseDouble(edtAmount.getText().toString());
            String category = edtCategory.getText().toString();
            String note = edtNote.getText().toString();
            String type = rbExpense.isChecked() ? "EXPENSE" : "INCOME";

            viewModel.addExpense(amount, category, note, type, selectedDate);
        });
    }

    private void showDatePicker() {
        Calendar cal = Calendar.getInstance();

        new DatePickerDialog(this,
                (view, year, month, day) -> {
                    Calendar c = Calendar.getInstance();
                    c.set(year, month, day);
                    selectedDate = new Timestamp(c.getTime());
                    btnDate.setText(day + "/" + (month + 1) + "/" + year);
                },
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
        ).show();
    }
}