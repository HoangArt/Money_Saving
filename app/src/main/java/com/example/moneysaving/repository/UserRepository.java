package com.example.moneysaving.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.moneysaving.object.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UserRepository {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private final MutableLiveData<Boolean> _registerSuccess = new MutableLiveData<>();
    public final LiveData<Boolean> registerSuccess = _registerSuccess;
    private final MutableLiveData<Boolean> _isLoading = new MutableLiveData<>(false);
    public final LiveData<Boolean> isLoading = _isLoading;
    private final MutableLiveData<Boolean> _loginSuccess = new MutableLiveData<>();
    public final LiveData<Boolean> loginSuccess = _loginSuccess;
    private final MutableLiveData<String> _registerError = new MutableLiveData<>();
    public final LiveData<String> registerError = _registerError;
    private final MutableLiveData<String> _loginError = new MutableLiveData<>();
    public final LiveData<String> loginError = _loginError;
    public void register(String fullName, String email, String password) {
        _isLoading.setValue(true);
        _registerSuccess.setValue(false);
        _registerError.setValue(null);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        _isLoading.setValue(false);
                        handleRegisterError(task.getException());
                        return;
                    }

                    String uid = mAuth.getCurrentUser().getUid();

                    Map<String, Object> user = new HashMap<>();
                    user.put("uid", uid);
                    user.put("fullName", fullName);
                    user.put("email", email);

                    db.collection("Users").document(uid)
                            .set(user)
                            .addOnSuccessListener(unused -> {
                                _isLoading.setValue(false);
                                _registerSuccess.setValue(true);
                            })
                            .addOnFailureListener(e -> {
                                _isLoading.setValue(false);
                                _registerError.setValue("ERROR_NETWORK");
                            });
                });
    }

    public void login(String emailOrUsername, String password) {
        _isLoading.setValue(true);
        loginWithEmail(emailOrUsername, password);
    }

    private void loginWithEmail(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        _isLoading.setValue(false);
                        handleLoginError(task.getException());
                        return;
                    }

                    String uid = mAuth.getCurrentUser().getUid();

                    db.collection("Users").document(uid)
                            .get()
                            .addOnSuccessListener(doc -> {
                                _isLoading.setValue(false);
                                _loginSuccess.setValue(true);
                            })
                            .addOnFailureListener(e -> {
                                _isLoading.setValue(false);
                                _loginError.setValue("ERROR_READ_DATA");
                            });
                });
    }

    private void handleRegisterError(Exception e) {
        if (e instanceof FirebaseAuthUserCollisionException) {
            String code = ((FirebaseAuthUserCollisionException) e).getErrorCode();
            _registerError.setValue(code);
        } else if (e instanceof FirebaseAuthWeakPasswordException) {
            String code = ((FirebaseAuthWeakPasswordException) e).getErrorCode();
            _registerError.setValue(code);
        } else if (e instanceof FirebaseAuthInvalidCredentialsException) {
            String code = ((FirebaseAuthInvalidCredentialsException) e).getErrorCode();
            _registerError.setValue(code);
        } else {
            _registerError.setValue("ERROR_UNKNOWN");
        }
    }

    private void handleLoginError(Exception e) {
        if (e == null) {
            _loginError.setValue("ERROR_UNKNOWN");
            return;
        }

        if (e instanceof FirebaseAuthInvalidUserException) {
            String code = ((FirebaseAuthInvalidUserException) e).getErrorCode();
            _loginError.setValue(code);
        } else if (e instanceof FirebaseAuthInvalidCredentialsException) {
            String code = ((FirebaseAuthInvalidCredentialsException) e).getErrorCode();
            _loginError.setValue(code);
        } else {
            _loginError.setValue("ERROR_UNKNOWN");
        }
    }
}
