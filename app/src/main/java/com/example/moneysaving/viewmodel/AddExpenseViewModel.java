package com.example.moneysaving.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.moneysaving.object.Expense;
import com.example.moneysaving.repository.ExpenseRepository;
import com.google.firebase.Timestamp;


public class AddExpenseViewModel extends ViewModel {

    private final ExpenseRepository repository;
    private final MutableLiveData<Boolean> isSuccess = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public AddExpenseViewModel() {
        repository = new ExpenseRepository();
    }

    public LiveData<Boolean> getIsSuccess() {
        return isSuccess;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void addExpense(double amount,
                           String category,
                           String note,
                           String type,
                           Timestamp date) {

        if (amount <= 0 || category.isEmpty()) {
            errorMessage.setValue("Vui lòng nhập đầy đủ thông tin");
            return;
        }

        Expense expense = new Expense(amount, category, note, type, date);

        repository.addExpense(expense,
                unused -> isSuccess.setValue(true),
                e -> errorMessage.setValue(e.getMessage()));
    }
}
