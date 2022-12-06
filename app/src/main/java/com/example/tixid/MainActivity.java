package com.example.tixid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements DataRVAdapter.DataClickInterface {

    private RecyclerView rvData;
    private ProgressBar pbLoading;
    private FloatingActionButton fabTambah;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference, likesReference;
    private ArrayList<DataRV> dataRVArrayList;
    private RelativeLayout rlMainHome;
    private RelativeLayout rlBottomSheet;
    private DataRVAdapter dataRVAdapter;
    private FirebaseAuth mAuth;
    private boolean isLike = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvData=findViewById(R.id.rvData);
        pbLoading=findViewById(R.id.pbLoading);
        fabTambah=findViewById(R.id.fabTambahData);
        //rlMainHome=findViewById(R.id.rlMainHome);
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Data");
        dataRVArrayList = new ArrayList<>();
        rlBottomSheet=findViewById(R.id.rlBottomSheet);
        dataRVAdapter = new DataRVAdapter(dataRVArrayList, this, this);
        //rvData.setLayoutManager(new LinearLayoutManager(this));
        rvData.setAdapter(dataRVAdapter);
        likesReference = firebaseDatabase.getReference("likes");
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2,GridLayoutManager.VERTICAL, false);
        rvData.setLayoutManager(gridLayoutManager);
        rvData.setHasFixedSize(true);
        fabTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddTicketActivity.class));
            }
        });
        getAllData();

    }

    private void getAllData(){
        dataRVArrayList.clear();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                pbLoading.setVisibility(View.GONE);
                dataRVArrayList.add(snapshot.getValue(DataRV.class));
                dataRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                pbLoading.setVisibility(View.GONE);
                dataRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                pbLoading.setVisibility(View.GONE);
                dataRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                pbLoading.setVisibility(View.GONE);
                dataRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onDataClick(int position) {
        tampilanBottomSheet(dataRVArrayList.get(position));
    }

    private void tampilanBottomSheet(DataRV dataRV){
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_dialog, rlBottomSheet);
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();

        TextView tvNama = layout.findViewById(R.id.tvNamaData);
        TextView tvDeskripsi = layout.findViewById(R.id.tvDeskipsi);
        TextView tvHarga = layout.findViewById(R.id.tvHarga);
        TextView tvTarget = layout.findViewById(R.id.tvTarget);
        ImageButton ibDislike = layout.findViewById(R.id.ibDislike);
        Button btUbah = layout.findViewById(R.id.btUbah);
        Button btLihatDetail = layout.findViewById(R.id.btLihatDetail);
        ImageView ivData = layout.findViewById(R.id.ivData);

        tvNama.setText(dataRV.getNamaData());
        tvDeskripsi.setText(dataRV.getDeskripsiData());
        tvHarga.setText("Rp. "+dataRV.getHargaData());
        tvTarget.setText(dataRV.getTargetData());
        Picasso.get().load(dataRV.getLinkGambarData()).into(ivData);



        btUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, EditTicketActivity.class);
                i.putExtra("data",dataRV);
                startActivity(i);
            }
        });

        btLihatDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(dataRV.getLinkData());
                Intent i = new Intent(Intent.ACTION_VIEW,uri);
                //i.setData(Uri.parse(dataRV.getLinkData()));
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        switch (id){
            case R.id.idKeluar:
                Toast.makeText(this,"Anda berhasil keluar.", Toast.LENGTH_SHORT).show();
                mAuth.signOut();
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}