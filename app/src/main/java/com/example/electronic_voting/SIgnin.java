package com.example.electronic_voting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SIgnin extends AppCompatActivity {
    EditText musername,mpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        musername = findViewById(R.id.username);
        mpassword = findViewById(R.id.password);
    }

    public void login(View view) {

        if (!TextUtils.isEmpty(musername.getText())&&!TextUtils.isEmpty(mpassword.getText())){
            if (musername.getText().toString().equals("admin")&&mpassword.getText().toString().equals("admin")){
                musername.setText("");
                mpassword.setText("");
                Intent intent = new Intent(this,admin.class);
                startActivity(intent);
            }else if(musername.getText().toString().equals("station")&&mpassword.getText().toString().equals("station")){
                musername.setText("");
                mpassword.setText("");
                Intent intent = new Intent(this,Station.class);
                startActivity(intent);
            }
            else{
                Toast.makeText(SIgnin.this,"Username or Password incorect",Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(SIgnin.this,"Data not inserted",Toast.LENGTH_SHORT).show();
        }
    }
}
