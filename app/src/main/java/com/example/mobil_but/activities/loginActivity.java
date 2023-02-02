package com.example.mobil_but.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobil_but.MainActivity;
import com.example.mobil_but.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class loginActivity extends AppCompatActivity {

    Button bt_login_login;
    EditText et_login_eposta, et_login_parola;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bt_login_login = findViewById(R.id.bt_login_girisyap);
        et_login_eposta = findViewById(R.id.et_login_eposta);
        et_login_parola = findViewById(R.id.et_login_parola);
        firebaseAuth = FirebaseAuth.getInstance();

        bt_login_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { loginUser(); }
            });
    }

    private void loginUser() {
        String keposta = et_login_eposta.getText().toString();
        String kparola = et_login_parola.getText().toString();
        if (keposta.isEmpty()){
            Toast.makeText(loginActivity.this,"E-posta boş bırakılamaz.",Toast.LENGTH_SHORT).show();
        }
        if (kparola.isEmpty() || kparola.length() < 6){
            Toast.makeText(loginActivity.this,"Parola en az 6 karakterli olmalıdır",Toast.LENGTH_SHORT).show();
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(keposta).matches()){
            Toast.makeText(loginActivity.this,"Lütfen geçerli bir eposta adresi girin",Toast.LENGTH_SHORT).show();
        }
        firebaseAuth.signInWithEmailAndPassword(keposta,kparola).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isComplete() && Patterns.EMAIL_ADDRESS.matcher(keposta).matches()){
                    Toast.makeText(loginActivity.this,"Giriş yapılıyor, lütfen bekleyiniz.",Toast.LENGTH_SHORT).show();
                }
                if (task.isSuccessful()){
                    Toast.makeText(loginActivity.this,"Başarıyla giriş yapıldı. Hoşgeldiniz.",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(loginActivity.this, MainActivity.class));
                }else{
                    Toast.makeText(loginActivity.this,"Giriş başarısız, tekrar deneyin.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void signupClick(View view){
        startActivity(new Intent(loginActivity.this,signupActivity.class));
    }
}
