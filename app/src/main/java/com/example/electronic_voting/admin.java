package com.example.electronic_voting;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URL;

public class admin extends AppCompatActivity {


    ProgressBar mprogresbar;
    DatabaseReference myref;
    Button browse;
    Button registor;
    private static final int GALLERY_INTENT = 2;
    StorageReference sreference;
    ProgressDialog progres;
    ImageView imageView;

    String duri;

    EditText textview;

    Uri uri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);


        textview = findViewById(R.id.editText);
        myref = FirebaseDatabase.getInstance().getReference().child("Party");
        myref.keepSynced(true);
        progres = new ProgressDialog(this);
        sreference = FirebaseStorage.getInstance().getReference();
        browse = findViewById(R.id.browsebtn);
        registor = findViewById(R.id.registorbtn);
        imageView = findViewById(R.id.imageView);


        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_INTENT);
            }
        });
        registor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                post();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        System.out.println("hey");
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {


            uri = data.getData();

            imageView.setImageURI(uri);
            progres.setMessage("uploading image");
            progres.show();
            final StorageReference filepath = sreference.child("Photo").child(uri.getLastPathSegment());
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    filepath.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            duri = task.getResult().toString();
                                    progres.dismiss();
                        }
                    });

                }

            });

        }
    }

    public void post(){
        String nameval = textview.getText().toString();

        if (!TextUtils.isEmpty(nameval)&&uri!=null){
            final StorageReference filepath = sreference.child("Photo").child(uri.getLastPathSegment());

                    filepath.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                               duri = task.getResult().toString();
                            System.out.println("hey + " + duri);

                        }
                    });

                }
            DatabaseReference st = myref.push();
            st.child("Name").setValue(nameval);
            st.child("Image").setValue(duri);
            st.child("Vote").setValue("0");
            System.out.println("hey + " + duri);
        }
    public void vote(){

    }

    public void showdata(View view) {
        Intent intent = new Intent(this,Showadmin.class);
        startActivity(intent);
    }
    public void logout(View view) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("Are You sure You want to log out?");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(admin.this,elect.class);
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


