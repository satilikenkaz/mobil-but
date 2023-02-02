package com.example.mobil_but.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobil_but.MainActivity;
import com.example.mobil_but.R;
import com.google.firebase.auth.FirebaseAuth;

public class splashactivity extends AppCompatActivity {

    Context context;
    ImageView iv_splash_logo;
    FirebaseAuth firebaseAuth;
    Thread wait;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        context = getApplicationContext();
        firebaseAuth = FirebaseAuth.getInstance();
        iv_splash_logo = findViewById(R.id.iv_splash_logo);
        iv_splash_logo.setImageResource(R.drawable.logo);
        SplashThread();

        if (firebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(splashactivity.this,MainActivity.class));
            Toast.makeText(splashactivity.this,"Giriş yapmış durumdasınız.", Toast.LENGTH_SHORT).show();
        }
    }

    public void loginClick(View view){
        startActivity(new Intent(splashactivity.this, loginActivity.class));
    }

    public void signupClick(View view){
        startActivity(new Intent(splashactivity.this, signupActivity.class));
    }

    private void SplashThread() {
        wait = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(500);
                    startActivity(new Intent(splashactivity.this, MainActivity.class));
                    finish();
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        };
    }

}
