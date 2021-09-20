package com.example.electronic_voting;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

public class User extends AppCompatActivity {
    private RecyclerView recyclerView;
    DatabaseReference mdatabase;
    String val;
    String f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        mdatabase = FirebaseDatabase.getInstance().getReference().child("Party");
        recyclerView = findViewById(R.id.partylist);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Party,partyviewholder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Party, partyviewholder>(
                Party.class,R.layout.listcontent,partyviewholder.class,mdatabase
        ) {
            @Override
            protected void populateViewHolder(partyviewholder viewHolder, Party model, int position) {

                final String key = getRef(position).getKey();

                viewHolder.setName(model.getName());
                viewHolder.setImage(getApplicationContext(),model.getImage());
                System.out.println(model.getImage());
                System.out.println(model.getName());

                viewHolder.view.setOnClickListener(new View.OnClickListener() {


                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(User.this);
                        dialog.setMessage("Are You sure You want to choose this party?");
                        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DatabaseReference count = mdatabase.child(key).child("Vote");
                                count.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        String vote = dataSnapshot.getValue(String.class);

                                        System.out.println(vote);
                                        int x = Integer.parseInt(vote);
                                        x+=1;
                                        String y = String.valueOf(x);
                                        System.out.println(y);
                                        mdatabase.child(key).child("Vote").setValue(y);
                                        //For the intent-----------------------
                                        Intent intent = new Intent(User.this,elect.class);
                                        startActivity(intent);
//------------------------------------------------------------------------------------------------------------------
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                            }
                        });
                        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();

                            }
                        });
                        dialog.setCancelable(true);
                        //System.out.println(getdata(key));
                        AlertDialog alert = dialog.create();
                        alert.setTitle("Warning");
                        alert.show();


                    }
                });
            }
        };

        recyclerView.setAdapter(firebaseRecyclerAdapter);
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
