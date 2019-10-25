package com.example.tnpcell;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.widget.Toast.LENGTH_LONG;

public class AddBlog extends AppCompatActivity {

    public EditText com_name;
    public EditText author;
    public EditText role;
    public EditText year;
    public EditText discription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_blog);

        com_name=findViewById(R.id.company);
        author=findViewById(R.id.author);
        role=findViewById(R.id.role);
        year=findViewById(R.id.year);
        discription=findViewById(R.id.description);
        final Button cancel = findViewById(R.id.discard);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button ok=findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String com=com_name.getText().toString();
                String aut=author.getText().toString();
                String rol=role.getText().toString();
                String yea=year.getText().toString();
                String dis=discription.getText().toString();
                if(com.length()<=1 || aut.length()==0 || rol.length()==0 || yea.length()==0 || dis.length()==0){
                    Toast.makeText(AddBlog.this, "Please Enter Valid Details", Toast.LENGTH_SHORT).show();
                    return;
                }






                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(AddBlog.this);
                    String branch = prefs.getString("branch", "computer");
                    final FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference ref = database.getReference(branch).child("blog");
                    ref.child(com).setValue(new User(aut,dis,rol,yea), new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                            if (databaseError != null) {
                                Toast.makeText(AddBlog.this, "Data couldn't be uploaded", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(AddBlog.this, "Data uploaded Successfully", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                    });


            }
        });
    }

    public static class User {
        public String company;
        public String author;
        public String content;
        public String role;
        public String year;
        public User(String a,String c,String r,String y) {
            author=a;
            content=c;
            role=r;
            year=y;
        }
        public User(String com,String a,String dis,String r,String y) {
            company=com;
            author=a;
            content=dis;
            role=r;
            year=y;
        }


    }
}
