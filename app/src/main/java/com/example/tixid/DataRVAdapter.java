package com.example.tixid;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DataRVAdapter extends RecyclerView.Adapter<DataRVAdapter.ViewHolder> {
    private ArrayList<com.example.tixid.DataRV> dataRVArrayList;
    private Context context;
    int lastPos=-1;
    private DataClickInterface dataClickInterface;



    public DataRVAdapter(ArrayList<com.example.tixid.DataRV> dataRVArrayList, Context context, DataClickInterface dataClickInterface) {
        this.dataRVArrayList = dataRVArrayList;
        this.context = context;
        this.dataClickInterface = dataClickInterface;
    }

    private void isLike(String idData, ImageButton ibDislike){
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Likes")
                .child(idData);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(firebaseUser.getUid()).exists()){
                    ibDislike.setImageResource(R.drawable.ic_suka_aktif);
                    ibDislike.setTag("disukai");
                }else{
                    ibDislike.setImageResource(R.drawable.ic_suka);
                    ibDislike.setTag("berhenti disukai");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void nrLikes(final TextView tvSuka, String idData){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Likes")
                .child(idData);
        reference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //tvSuka.setText(snapshot.getChildrenCount() + " likes");
                tvSuka.setText(snapshot.getChildrenCount()+ " likes");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.data_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        String currentUserId=user.getUid();


        com.example.tixid.DataRV dataRV = dataRVArrayList.get(position);
        holder.tvNamaData.setText(dataRV.getNamaData());
        holder.tvHarga.setText("Rp. "+dataRV.getHargaData());
        Picasso.get().load(dataRV.getLinkGambarData()).into(holder.ivData);
        setAnimation(holder.itemView,position);
        holder.ivData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataClickInterface.onDataClick(position);
            }
        });
        isLike(dataRV.getDataID(), holder.ibDislike);
        nrLikes(holder.tvDislike, dataRV.getDataID());

        holder.ibDislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.ibDislike.getTag().equals("berhenti disukai")){
                    FirebaseDatabase.getInstance().getReference().child("Likes").child(dataRV.getDataID())
                            .child(user.getUid()).setValue("true");
                }else{
                    FirebaseDatabase.getInstance().getReference().child("Likes").child(dataRV.getDataID())
                            .child(user.getUid()).removeValue();
                }
            }
        });
    }

    private void setAnimation(View itemView, int position){
        if(position>lastPos){
            Animation animation= AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            itemView.setAnimation(animation);
            lastPos = position;
        }
    }
    @Override
    public int getItemCount() {
        return dataRVArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvNamaData,tvHarga, tvDislike;
        private ImageView ivData;
        private ImageButton ibDislike;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNamaData=itemView.findViewById(R.id.tvNamaData);
            tvHarga=itemView.findViewById(R.id.tvHarga);
            ivData=itemView.findViewById(R.id.ivData);
            ibDislike=itemView.findViewById(R.id.ibDislike);
            tvDislike=itemView.findViewById(R.id.tvDislike);

        }
    }

    public interface DataClickInterface{
        void onDataClick(int position);

        }

    }
