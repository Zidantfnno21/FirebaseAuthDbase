package com.example.tixid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddTicketActivity extends AppCompatActivity {

    private TextInputEditText etNamaData, etHargaData, etLinkGambarData, etTargetData, etLinkData, etDeskData;
    private Button btTambahData;
    private ProgressBar pbLoading;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String dataID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ticket);
        etNamaData=findViewById(R.id.etNamaData);
        etHargaData=findViewById(R.id.etHargaData);
        etTargetData=findViewById(R.id.etTargetData);
        etLinkGambarData=findViewById(R.id.etLLinkGambarData);
        etLinkData=findViewById(R.id.etLinkData);
        etDeskData=findViewById(R.id.etDeskipsiData);
        btTambahData=findViewById(R.id.btTambahData);
        pbLoading=findViewById(R.id.pbLoading);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Data");

        btTambahData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //pbLoading.setVisibility(View.VISIBLE);
                String namaData= etNamaData.getText().toString();
                String hargaData= etHargaData.getText().toString();
                String targetData= etTargetData.getText().toString();
                String linkGambarData= etLinkGambarData.getText().toString();
                String linkData= etLinkData.getText().toString();
                String deskripsiData= etDeskData.getText().toString();
                dataID=namaData;
                DataRV dataRV = new DataRV(namaData, hargaData, targetData, linkGambarData, linkData, deskripsiData, dataID);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //pbLoading.setVisibility(View.GONE);
                        databaseReference.child(dataID).setValue(dataRV);
                        Toast.makeText(AddTicketActivity.this, "Data berhasil ditambahkan.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddTicketActivity.this, MainActivity.class));

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(AddTicketActivity.this, "Terjadi error: "+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}