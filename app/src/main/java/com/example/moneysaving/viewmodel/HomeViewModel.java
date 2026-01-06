package com.example.moneysaving.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.moneysaving.object.Expense;
import com.example.moneysaving.repository.ExpenseRepository;

import java.util.List;

public class HomeViewModel extends ViewModel {

    private final ExpenseRepository repository;

    private final MutableLiveData<Double> totalIncome = new MutableLiveData<>(0.0);
    private final MutableLiveData<Double> totalExpense = new MutableLiveData<>(0.0);
    private final MutableLiveData<List<Expense>> recentExpenses = new MutableLiveData<>();

    public HomeViewModel() {
        repository = new ExpenseRepository();
        loadHomeData();
    }

    public LiveData<Double> getTotalIncome() {
        return totalIncome;
    }

    public LiveData<Double> getTotalExpense() {
        return totalExpense;
    }

    public LiveData<List<Expense>> getRecentExpenses() {
        return recentExpenses;
    }

    private void loadHomeData() {
        repository.getAllExpenses(expenses -> {
            double income = 0;
            double expense = 0;

            for (Expense e : expenses) {
                if ("INCOME".equals(e.getType())) {
                    income += e.getAmount();
                } else {
                    expense += e.getAmount();
                }
            }

            totalIncome.setValue(income);
            totalExpense.setValue(expense);
            recentExpenses.setValue(expenses);
        });
    }
}
