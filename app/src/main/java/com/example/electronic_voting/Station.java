package com.example.electronic_voting;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

public class Station extends AppCompatActivity {

    EditText vname,vage;
    Button regbtn;
    RadioGroup rg;
    RadioButton rb;
    DatabaseReference couponreference;
    ProgressDialog mprogres;


    int num;
    ArrayList<Integer> coupons;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        final DatabaseReference dref = FirebaseDatabase.getInstance().getReference().child("Voters");

        mprogres = new ProgressDialog(this);
        setContentView(R.layout.activity_station);
        vname = findViewById(R.id.editname);
        vage = findViewById(R.id.editage);
        regbtn = findViewById(R.id.regvotebtn);
        rg = findViewById(R.id.radiogroup);

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



        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mprogres.setMessage("adding");
                mprogres.show();

                if (!TextUtils.isEmpty(vname.getText())&&!TextUtils.isEmpty(vage.getText())){
                    int radioid = rg.getCheckedRadioButtonId();
                    rb = findViewById(radioid);
                    String sexval = rb.getText().toString();



                    String nameval = vname.getText().toString();
                    String ageval = vage.getText().toString();
                    DatabaseReference ddref = dref.push();
                    ddref.child("Name").setValue(nameval);
                    ddref.child("Age").setValue(ageval);
                    ddref.child("Sex").setValue(sexval);

                    //Toast.makeText(Station.this,"Coupon "+num+"-Registered Succesfully",Toast.LENGTH_LONG).show();
                    DatabaseReference ref = couponreference.push().child("coupon");
                    ref.setValue(randomgenerator()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            //Toast.makeText(Station.this,"Coupon "+randomgenerator()+"-Registered Succesfully",Toast.LENGTH_LONG).show();
                            mprogres.dismiss();

                        }
                    });
                    //ref.child("coupon").setValue(randomgenerator());
                    Toast.makeText(Station.this,"Coupon "+num+"-Registered Succesfully",Toast.LENGTH_LONG).show();
                    mprogres.dismiss();
                    vname.setText("");
                    vage.setText("");
                    rg.clearCheck();
                }
            }
        });
    }


    public void fromfiretolist(){



    }


    public Integer randomgenerator(){
        int c = 0;
        Random ran = new Random();
        int cupon = ran.nextInt(2000);
        for (int i : coupons){
            if (i==cupon){
                randomgenerator();
            }else {
                cupon+=1000;
                break;
            }

        }


        System.out.println("hey+"+cupon);
        num = cupon;
        return cupon;
    }

    public void logout(View view) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("Are You sure You want to log out?");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Station.this,elect.class);
                startActivity(intent);
            }
        });
        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = dialog.create();
        alert.setTitle("Warning");
        alert.show();
    }
}
