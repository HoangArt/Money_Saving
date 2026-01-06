package com.example.moneysaving.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.example.moneysaving.R;
import com.example.moneysaving.repository.UserRepository;

public class RegisterViewModel extends AndroidViewModel {
    public final LiveData<Boolean> registerSuccess;
    public final LiveData<String> registerErrorMessage;
    public final LiveData<Boolean> isLoading;
    private final UserRepository repository;

    public RegisterViewModel(@NonNull Application application) {
        super(application);
        repository = new UserRepository();

        isLoading = repository.isLoading;
        registerSuccess = repository.registerSuccess;

        registerErrorMessage = Transformations.map(repository.registerError, errorCode -> {
            if (errorCode == null) return null;
            Application app = getApplication();

            switch (errorCode) {
                case "ERROR_EMAIL_ALREADY_IN_USE":
                    return app.getString(R.string.error_email_taken);
                case "ERROR_WEAK_PASSWORD":
                    return app.getString(R.string.error_weak_password);
                case "ERROR_INVALID_EMAIL":
                    return app.getString(R.string.error_invalid_email);
                case "ERROR_NETWORK":
                    return app.getString(R.string.error_network);
                case "ERROR_READ_DATA":
                    return app.getString(R.string.error_read_data);
                case "ERROR_UNKNOWN":
                    return "I don't know";
                default:
                    return app.getString(R.string.try_again_later);
            }
        });
    }

    public void register(String fullName, String email, String password) {
        repository.register(fullName, email, password);
    }
}

