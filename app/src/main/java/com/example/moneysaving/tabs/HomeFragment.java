package com.example.moneysaving.tabs;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.moneysaving.R;
import com.example.moneysaving.adapter.RecentExpenseAdapter;
import com.example.moneysaving.viewmodel.HomeViewModel;

public class HomeFragment extends Fragment {

    private HomeViewModel viewModel;
    private TextView tvBalance, tvIncome, tvExpense;
    private RecyclerView rvRecent;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        setupViewModel();
    }

    private void initViews(View view) {
        tvBalance = view.findViewById(R.id.tvBalance);
        tvIncome = view.findViewById(R.id.tvIncome);
        tvExpense = view.findViewById(R.id.tvExpense);
        rvRecent = view.findViewById(R.id.rvRecent);

        rvRecent.setLayoutManager(new LinearLayoutManager(requireContext()));
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this)
                .get(HomeViewModel.class);

        viewModel.getTotalIncome().observe(getViewLifecycleOwner(), income ->
                tvIncome.setText("Thu: " + formatMoney(income)));

        viewModel.getTotalExpense().observe(getViewLifecycleOwner(), expense ->
                tvExpense.setText("Chi: " + formatMoney(expense)));

        viewModel.getTotalIncome().observe(getViewLifecycleOwner(), income ->
                viewModel.getTotalExpense().observe(getViewLifecycleOwner(), expense ->
                        tvBalance.setText(formatMoney(income - expense))
                ));

        viewModel.getRecentExpenses().observe(getViewLifecycleOwner(), expenses ->
                rvRecent.setAdapter(new RecentExpenseAdapter(expenses))
        );
    }

    private String formatMoney(double value) {
        return String.format("%,.0f ₫", value);
    }
}
