package com.example.moneysaving.repository;

import com.example.moneysaving.object.Expense;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class ExpenseRepository {
    private final FirebaseFirestore db;
    private final FirebaseAuth auth;

    public ExpenseRepository() {
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    public void getAllExpenses(OnSuccessListener<List<Expense>> callback) {
        String uid = auth.getCurrentUser().getUid();

        db.collection("Users")
                .document(uid)
                .collection("expenses")
                .orderBy("date", Query.Direction.DESCENDING)
                .limit(10)
                .get()
                .addOnSuccessListener(query -> {
                    List<Expense> list = new ArrayList<>();
                    for (DocumentSnapshot doc : query) {
                        Expense e = doc.toObject(Expense.class);
                        list.add(e);
                    }
                    callback.onSuccess(list);
                });
    }


    public void addExpense(Expense expense,
                           OnSuccessListener<Void> success,
                           OnFailureListener failure) {

        String uid = auth.getCurrentUser().getUid();

        db.collection("Users")
                .document(uid)
                .collection("expenses")
                .document()
                .set(expense)
                .addOnSuccessListener(success)
                .addOnFailureListener(failure);
    }
}
