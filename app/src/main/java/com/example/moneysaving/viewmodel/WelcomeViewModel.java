package com.example.moneysaving.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class WelcomeViewModel extends AndroidViewModel {
    private final MutableLiveData<Boolean> _isUserLoggedIn = new MutableLiveData<>();
    public final LiveData<Boolean> isUserLoggedIn = _isUserLoggedIn;

    public WelcomeViewModel(@NonNull Application application) {
        super(application);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        _isUserLoggedIn.setValue(currentUser != null);
    }
}
