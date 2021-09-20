package com.example.electronic_voting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    //DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //db = FirebaseDatabase.getInstance().getReference();
    }


    public void Click(View view) {
        Intent intent = new Intent(this, User.class);
        startActivity(intent);
    }

    public void posting(View view) {
        Intent intent = new Intent(this, admin.class);
        startActivity(intent);
    }

    public void regvoter(View view) {
        Intent intent = new Intent(this, Station.class);
        startActivity(intent);
    }

    public void vote(View view) {
        Intent intent = new Intent(this,elect.class);
        startActivity(intent);
    }
}
