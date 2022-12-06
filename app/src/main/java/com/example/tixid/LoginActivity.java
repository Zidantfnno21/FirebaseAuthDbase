package com.example.tixid;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText etUsername, etPassword;
    private Button btMasuk;
    private TextView tvBelumDaftar;
    private ProgressBar pbLoading;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etUsername = findViewById(R.id.etEmail);
        etPassword= findViewById(R.id.etPassword);
        btMasuk= findViewById(R.id.btMasuk);
        tvBelumDaftar = findViewById(R.id.tvBelumRegist);
        pbLoading = findViewById(R.id.pbLoading);
        btMasuk = findViewById(R.id.btMasuk);
        mAuth = FirebaseAuth.getInstance();

        tvBelumDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this , RegistActivity.class);
                startActivity(i);

            }
        });

        btMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pbLoading.setVisibility(View.VISIBLE);
                String username=etUsername.getText().toString();
                String password=etPassword.getText().toString();
                if(TextUtils.isEmpty(username) && TextUtils.isEmpty(password)){
                    Toast.makeText(LoginActivity.this, "Data ada yang kosong.", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    mAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                pbLoading.setVisibility(View.GONE);
                                Toast.makeText(LoginActivity.this, "Proses masuk berhasil.", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(i);
                                finish();
                            }else{
                                pbLoading.setVisibility(View.GONE);
                                Toast.makeText(LoginActivity.this, "Proses masuk gagal", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user!=null){
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
            this.finish();
        }
    }
}