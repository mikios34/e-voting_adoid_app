package com.example.electronic_voting;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class elect extends AppCompatActivity {
    EditText coupon;
    DatabaseReference couponreference;

    ArrayList<Integer> coupons;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elect);
        coupon = findViewById(R.id.editcoupon);

        coupons = new ArrayList<>();

        couponreference = FirebaseDatabase.getInstance().getReference().child("Coupon");



        couponreference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childdataSnapshot:dataSnapshot.getChildren()){
                    String i = childdataSnapshot.child("coupon").getValue().toString();
                    System.out.println("hey"+i);
                    int val = Integer.parseInt(i);
                    coupons.add(val);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void go(View view) {
        if (!TextUtils.isEmpty(coupon.getText())) {
            int y = 0;
            int x = Integer.parseInt(coupon.getText().toString());
            for (int i : coupons){
                if (i==x){
                    coupon.setText("");
                    Intent intent = new Intent(this,User.class);
                    startActivity(intent);
                     y = 1;
                    break;
                }
            }
            if (y==0){
                Toast.makeText(this,"You have not ben Registerd",Toast.LENGTH_SHORT).show();
                coupon.setText("");
            }

        }
    }

    public void signin(View view) {
        Intent intent = new Intent(this,SIgnin.class);
        startActivity(intent);
    }
}
