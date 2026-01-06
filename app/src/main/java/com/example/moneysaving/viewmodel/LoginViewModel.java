package com.example.moneysaving.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.example.moneysaving.R;
import com.example.moneysaving.repository.UserRepository;

public class LoginViewModel extends AndroidViewModel {
    public final LiveData<Boolean> isLoading;
    public final LiveData<Boolean> loginSuccess;
    public final LiveData<String> loginErrorMessage;
    private final UserRepository repository;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        repository = new UserRepository();

        isLoading = repository.isLoading;
        loginSuccess = repository.loginSuccess;

        loginErrorMessage = Transformations.map(repository.loginError, errorCode -> {
            if (errorCode == null) return null;
            Log.d("LoginViewModel", errorCode);
            Application app = getApplication();

            switch (errorCode) {
                case "ERROR_USER_NOT_FOUND":
                    return app.getString(R.string.error_user_not_found);
                case "ERROR_INVALID_EMAIL":
                    return app.getString(R.string.error_invalid_email);
                case "ERROR_INVALID_CREDENTIAL":
                    return app.getString(R.string.error_login_info);
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

    public void login(String emailOrUsername, String password) {
        repository.login(emailOrUsername, password);
    }
}
