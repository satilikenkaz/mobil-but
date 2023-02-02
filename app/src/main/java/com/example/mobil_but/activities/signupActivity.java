package com.example.mobil_but.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobil_but.R;
import com.example.mobil_but.models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signupActivity extends AppCompatActivity {

    Button bt_signup_signup;
    EditText et_signup_isim, et_signup_eposta, et_signup_parola;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        bt_signup_signup = findViewById(R.id.bt_signup_kayitol);
        et_signup_isim = findViewById(R.id.et_signup_isim);
        et_signup_eposta = findViewById(R.id.et_signup_eposta);
        et_signup_parola = findViewById(R.id.et_signup_parola);

        firebaseAuth = FirebaseAuth.getInstance();

        bt_signup_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signupClick();
            }
        });
    }

    private void signupClick() {
        String kisim = et_signup_isim.getText().toString();
        String keposta = et_signup_eposta.getText().toString();
        String kparola = et_signup_parola.getText().toString();

        if (kisim.isEmpty()){
            Toast.makeText(signupActivity.this,"İsim boş bırakılamaz.",Toast.LENGTH_SHORT).show();
        }
        if (keposta.isEmpty()){
            Toast.makeText(signupActivity.this,"E-posta boş bırakılamaz.",Toast.LENGTH_SHORT).show();
        }
        if (kparola.isEmpty() || kparola.length() < 6){
            Toast.makeText(signupActivity.this,"Parola en az 6 karakterli olmalıdır.",Toast.LENGTH_SHORT).show();
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(keposta).matches()){
            Toast.makeText(signupActivity.this,"Lütfen geçerli bir eposta adresi girin",Toast.LENGTH_SHORT).show();
        }

        firebaseAuth.createUserWithEmailAndPassword(keposta,kparola).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isComplete()&&Patterns.EMAIL_ADDRESS.matcher(keposta).matches()){
                    Toast.makeText(signupActivity.this,"Kayıt yapılıyor, lütfen bekleyiniz.",Toast.LENGTH_SHORT).show();
                }
                if (task.isSuccessful()){
                    UserModel user = new UserModel(kisim,keposta,kparola);
                    String uid = task.getResult().getUser().getUid();
                    Toast.makeText(signupActivity.this,"Kayıt başarılı.",Toast.LENGTH_SHORT).show();
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("kullanıcılar").child(uid);
                    myRef.setValue(user);
                    startActivity(new Intent(signupActivity.this,loginActivity.class));
                }else{
                    Toast.makeText(signupActivity.this,"Tekrar deneyin.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void loginClick(View view){
        startActivity(new Intent(signupActivity.this,loginActivity.class));
    }
}
