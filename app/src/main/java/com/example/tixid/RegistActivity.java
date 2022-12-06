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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegistActivity extends AppCompatActivity {

    private TextInputEditText etUsername, etIdName, etPassword, etKonfirmPassword;
    private Button btRegist;
    private ProgressBar pbLoading;
    private TextView tvSudahRegist;
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        etUsername = findViewById(R.id.etEmail);
        etIdName = findViewById(R.id.etIdName);
        etPassword = findViewById(R.id.etPassword);
        etKonfirmPassword = findViewById(R.id.etKonfirmPassword);
        btRegist = findViewById(R.id.btDaftar);
        pbLoading = findViewById(R.id.pbLoading);
        tvSudahRegist = findViewById(R.id.tvSudahRegist);
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("User");



        tvSudahRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegistActivity.this, com.example.tixid.LoginActivity.class);
                startActivity(i);
            }
        });

        btRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pbLoading.setVisibility(View.VISIBLE);
                String username= etUsername.getText().toString();
                String idName= etIdName.getText().toString();
                String password= etPassword.getText().toString();
                String konfirmPassword= etKonfirmPassword.getText().toString();


                if(!password.equals(konfirmPassword)){
                    Toast.makeText(RegistActivity.this, "kata sandi tidak cocok.", Toast.LENGTH_SHORT).show();
                    pbLoading.setVisibility(View.GONE);
                }else if(TextUtils.isEmpty(username) && TextUtils.isEmpty(password) && TextUtils.isEmpty(konfirmPassword)){
                    Toast.makeText(RegistActivity.this, "Data ada yang kosong.", Toast.LENGTH_SHORT).show();
                    pbLoading.setVisibility(View.GONE);
                }else {
                    mAuth.createUserWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                pbLoading.setVisibility(View.GONE);
                                Toast.makeText(RegistActivity.this, "Registrasi Berhasil.", Toast.LENGTH_SHORT).show();

                                databaseReference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        firebaseUser = mAuth.getInstance().getCurrentUser();
                                        String idUsername = firebaseUser.getUid();
                                        UserRV userRV = new UserRV(idName, username, password, idUsername);
                                        databaseReference.child(idUsername).setValue(userRV);

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Toast.makeText(RegistActivity.this, "Data Registrasi Gagal Disimpan.", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                Intent i = new Intent(RegistActivity.this, com.example.tixid.LoginActivity.class);
                                startActivity(i);
                                finish();
                            } else {
                                pbLoading.setVisibility(View.GONE);
                                Toast.makeText(RegistActivity.this, "Registrasi gagal..", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }


                }

        });

    }
}