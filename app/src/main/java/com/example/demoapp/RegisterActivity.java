package com.example.demoapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class RegisterActivity extends AppCompatActivity{
    private static final int RC_SIGN_IN = 1 ;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthlistener;
    private List<AuthUI.IdpConfig> providers;

    @Override
    protected void onStart() {
        super.onStart();
               mAuth.addAuthStateListener(mAuthlistener); }

    @Override
    protected void onStop() {
       if(mAuthlistener!=null)
           mAuth.removeAuthStateListener(mAuthlistener);
        super.onStop();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
      init();
         }

    private void init() {
        providers= Arrays.asList(
               new AuthUI.IdpConfig.PhoneBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.EmailBuilder().build()
        );
        mAuth = FirebaseAuth.getInstance();
        mAuthlistener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
               FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user!= null) {
                    Toast.makeText(RegisterActivity.this,"Already Signed in",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    startActivityForResult(AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                            .setLogo(R.drawable.mylogo)
                            .setTheme(R.style.LoginTheme)
                    .build(),RC_SIGN_IN);
                }
            }
        };
    }

}