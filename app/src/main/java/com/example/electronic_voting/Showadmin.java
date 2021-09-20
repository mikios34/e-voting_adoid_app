package com.example.electronic_voting;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class Showadmin extends AppCompatActivity {
    private RecyclerView recycler;
    DatabaseReference mdatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showadmin);

        mdatabase = FirebaseDatabase.getInstance().getReference().child("Party");
        recycler = findViewById(R.id.recyclerview);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(Showadmin.this));
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Party,partyviewholder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Party, partyviewholder>(
                Party.class,R.layout.adminlistcontent,partyviewholder.class,mdatabase
        ) {
            @Override
            protected void populateViewHolder(partyviewholder viewHolder, Party model, int position) {

                final String key = getRef(position).getKey();

                viewHolder.setName(model.getName());
                viewHolder.setImage(getApplicationContext(),model.getImage());
                viewHolder.setVote(model.getVote());
                System.out.println(model.getImage());
                System.out.println(model.getName());


            }
        };

        recycler.setAdapter(firebaseRecyclerAdapter);
    }


    public static class partyviewholder extends RecyclerView.ViewHolder{

        View view;

        public partyviewholder(@NonNull View itemView) {
            super(itemView);

            view = itemView;
        }

        public void setName(String name){
            TextView nametxt = (TextView) view.findViewById(R.id.party_name);
            nametxt.setText(name);
        }

        public void setVote(String vote){
            TextView votetxt = (TextView) view.findViewById(R.id.vote_count);
            votetxt.setText(vote);
        }
        public  void  setImage(final Context ctx, final String image){
            final ImageView ima =(ImageView) view.findViewById(R.id.party_umg);
            //Picasso.with(ctx).load(image).into(ima);

            Picasso.with(ctx).load(image).networkPolicy(NetworkPolicy.OFFLINE).into(ima, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {
                    Picasso.with(ctx).load(image).into(ima);
                }
            });
        }
    }

}
