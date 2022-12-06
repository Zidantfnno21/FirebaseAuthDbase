package com.example.tixid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class EditTicketActivity extends AppCompatActivity {

    private TextInputEditText etNamaData, etHargaData, etLinkGambarData, etTargetData, etLinkData, etDeskData;
    private Button btUbahData, btHapusData;
    //private ProgressBar pbLoading;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String dataID;
    private DataRV dataRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_ticket);

        etNamaData=findViewById(R.id.etNamaData);
        etHargaData=findViewById(R.id.etHargaData);
        etTargetData=findViewById(R.id.etTargetData);
        etLinkGambarData=findViewById(R.id.etLLinkGambarData);
        etLinkData=findViewById(R.id.etLinkData);
        etDeskData=findViewById(R.id.etDeskipsiData);
        btUbahData=findViewById(R.id.btUbahData);
        btHapusData=findViewById(R.id.btHapusData);
        //pbLoading=findViewById(R.id.pbLoading);
        firebaseDatabase=FirebaseDatabase.getInstance();

        dataRV=getIntent().getParcelableExtra("data");
        if(dataRV!=null){
            etNamaData.setText(dataRV.getNamaData());
            etHargaData.setText(dataRV.getHargaData());
            etTargetData.setText(dataRV.getTargetData());
            etDeskData.setText(dataRV.getDeskripsiData());
            etLinkGambarData.setText(dataRV.getLinkGambarData());
            etLinkData.setText(dataRV.getLinkData());
            dataID=dataRV.getDataID();
        }

        databaseReference=firebaseDatabase.getReference("Data").child(dataID);
        btUbahData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //pbLoading.setVisibility(View.VISIBLE);
                String namaData = etNamaData.getText().toString();
                String hargaData= etHargaData.getText().toString();
                String targetData= etTargetData.getText().toString();
                String linkGambarData= etLinkGambarData.getText().toString();
                String linkData= etLinkData.getText().toString();
                String deskripsiData= etDeskData.getText().toString();

                Map<String,Object> map = new HashMap<>();
                map.put("namaData",namaData);
                map.put("hargaData",hargaData);
                map.put("targetData",targetData);
                map.put("linkGambarData",linkGambarData);
                map.put("linkData",linkData);
                map.put("deskripsiData",deskripsiData);
                map.put("dataID",dataID);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //pbLoading.setVisibility(View.GONE);
                        databaseReference.updateChildren(map);
                        Toast.makeText(EditTicketActivity.this, "Data berhasil diubah.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditTicketActivity.this, MainActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(EditTicketActivity.this, "Data gagal diubah.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btHapusData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //pbLoading.setVisibility(View.VISIBLE);
                hapusData();
            }
        });
    }

    private void hapusData(){
        databaseReference.removeValue();
        Toast.makeText(this, "Data berhasil dihapus.", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(EditTicketActivity.this, MainActivity.class));

    }
}