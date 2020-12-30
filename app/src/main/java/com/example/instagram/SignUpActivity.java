package com.example.instagram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    EditText emailEditText, sifreEditText;
    String emailAdresi, sifre;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initialize();
        kullaniciVarMı();
    }

    public void girisYap(View view) {
        emailAdresi = emailEditText.getText().toString();
        sifre = sifreEditText.getText().toString();

        if (!emailAdresi.equals("") && !sifre.equals("")) {
            firebaseAuth.signInWithEmailAndPassword(emailAdresi, sifre).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Toast.makeText(getApplicationContext(), emailEditText.getText().toString() + " hoşgeldin.", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SignUpActivity.this, FeedActivity.class);
                    startActivity(intent);
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            });

        } else {
            Toast.makeText(getApplicationContext(), "Eksik bilgi girdiniz.", Toast.LENGTH_LONG).show();
        }
    }

    public void kayitOl(View view) {
        emailAdresi = emailEditText.getText().toString();
        sifre = sifreEditText.getText().toString();

        if (!emailAdresi.matches("") && !sifre.matches("")) {
            firebaseAuth.createUserWithEmailAndPassword(emailAdresi, sifre).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Toast.makeText(SignUpActivity.this, "Hesap Oluşturuldu.", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SignUpActivity.this, FeedActivity.class);
                    startActivity(intent);
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(SignUpActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            });

        } else {
            Toast.makeText(getApplicationContext(), "Eksik bilgi girdiniz.", Toast.LENGTH_LONG).show();
        }
    }

    public void initialize() {
        firebaseAuth = FirebaseAuth.getInstance();
        emailEditText = findViewById(R.id.emailEditText);
        sifreEditText = findViewById(R.id.sifreEditText);
        firebaseUser = firebaseAuth.getCurrentUser();
    }
    public void kullaniciVarMı(){
        if(firebaseUser != null){
            Intent intent = new Intent(SignUpActivity.this, FeedActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
