package com.example.tnpcell;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddCompany extends AppCompatActivity {

    public EditText com_name;
    public EditText ctc;
    public EditText profile;
    public EditText cgpa;
    public EditText lastdate;
    public EditText onboarddate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_company);


        com_name=findViewById(R.id.company);
        ctc=findViewById(R.id.ctc);
        cgpa=findViewById(R.id.cgpa);
        onboarddate=findViewById(R.id.onboarddate);
        lastdate=findViewById(R.id.lastdate);
        profile=findViewById(R.id.profile);


        final Button cancel = findViewById(R.id.discard);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Log.d("added","compnayadd");
        Button ok=findViewById(R.id.ok);


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String com=com_name.getText().toString();
                String CTC=ctc.getText().toString();
                String cgp=cgpa.getText().toString();
                String onb=onboarddate.getText().toString();
                String las=lastdate.getText().toString();
                String pro=profile.getText().toString();
                if(pro.length()==0 || com.length()==0 || CTC.length()==0 || cgp.length()==0 || onb.length()==0 || las.length()==0){
                    Toast.makeText(AddCompany.this, "Please Enter Valid Details", Toast.LENGTH_SHORT).show();
                    return;
                }else{

                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(AddCompany.this);
                    String branch = prefs.getString("branch", "computer");
                    final FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference ref = database.getReference(branch).child("company");
                    ref.child(com).setValue(new companydetail(CTC,pro,cgp,las,onb), new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                            if (databaseError != null) {
                                Toast.makeText(AddCompany.this, "Company couldn't be uploaded", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(AddCompany.this, "Company uploaded Successfully", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                    });
                }
            }
        });
    }

    public static class companydetail{
        public String com_name;
        public String ctc;
        public String profile;
        public String cgpa;
        public String lastdate;
        public String onboarddate;
        public companydetail(String ct,String p,String c,String l,String o){
            ctc=ct;profile=p;cgpa=c;lastdate=l;onboarddate=o;
        }
    }





}

