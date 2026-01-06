package com.example.moneysaving.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.moneysaving.R;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordViewModel extends AndroidViewModel {
    private final MutableLiveData<Boolean> _isLoading = new MutableLiveData<>();
    public final LiveData<Boolean> isLoading = _isLoading;
    private final MutableLiveData<String> _toastMessage = new MutableLiveData<>();
    public final LiveData<String> toastMessage = _toastMessage;
    private final MutableLiveData<Boolean> _resetPasswordSuccess = new MutableLiveData<>();
    public final LiveData<Boolean> resetPasswordSuccess = _resetPasswordSuccess;
    private final FirebaseAuth mAuth;

    public ResetPasswordViewModel(@NonNull Application application) {
        super(application);
        mAuth = FirebaseAuth.getInstance();
        mAuth.useAppLanguage();
    }

    public void forgotPassword(String email) {
        if (email.isEmpty()) {
            _toastMessage.setValue(String.valueOf(R.string.error_email_field_empty));
            return;
        }

        _isLoading.setValue(true);

        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    _isLoading.setValue(false);
                    if (task.isSuccessful()) {
                        _resetPasswordSuccess.setValue(true);
                    } else {
                        Exception e = task.getException();
                        if (e != null) {
                            _toastMessage.setValue("Error: " + e.getMessage());
                        }
                    }
                });
    }
}
