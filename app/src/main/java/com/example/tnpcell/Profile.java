package com.example.tnpcell;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

@SuppressLint("ValidFragment")
public class Profile extends Fragment {
    View V;
    Context t;

    public Profile(Context m) {
        t=m;
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        V=inflater.inflate(R.layout.fragment_profile, container, false);

        final TextView name = (TextView) V.findViewById(R.id.name);
        final TextView uid = (TextView) V.findViewById(R.id.uid);
        final TextView email = (TextView) V.findViewById(R.id.email);
        final TextView dob = (TextView) V.findViewById(R.id.dob);
        final TextView gender = (TextView) V.findViewById(R.id.gender);
        final TextView category = (TextView) V.findViewById(R.id.category);
        final TextView hometown = (TextView) V.findViewById(R.id.hometown);
        final TextView contact = (TextView) V.findViewById(R.id.contact);
        final TextView per_ten = (TextView) V.findViewById(R.id.per_ten);
        final TextView per_twe = (TextView) V.findViewById(R.id.per_twe);
        final TextView cg_1= (TextView) V.findViewById(R.id.cg_1);
        final TextView cg_2= (TextView) V.findViewById(R.id.cg_2);
        final TextView cg_3 = (TextView) V.findViewById(R.id.cg_3);
        final TextView cg_4 = (TextView) V.findViewById(R.id.cg_4);
        final TextView cg_5 = (TextView) V.findViewById(R.id.cg_5);
        final TextView cg_6 = (TextView) V.findViewById(R.id.cg_6);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(t);
        String branch = prefs.getString("branch", "computer");
        final String username = prefs.getString("username", "computer");
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference(branch);
        ValueEventListener VV=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String ctc= dataSnapshot.child("user").child(username).child("name").getValue().toString();
                name.setText(ctc);
                uid.setText(username);
                email.setText(dataSnapshot.child("user").child(username).child("email").getValue().toString());
                dob.setText(dataSnapshot.child("user").child(username).child("dob").getValue().toString());
                gender.setText(dataSnapshot.child("user").child(username).child("gender").getValue().toString());
                category.setText(dataSnapshot.child("user").child(username).child("category").getValue().toString());
                hometown.setText(dataSnapshot.child("user").child(username).child("hometown").getValue().toString());
                contact.setText(dataSnapshot.child("user").child(username).child("contact").getValue().toString());
                per_ten.setText(dataSnapshot.child("user").child(username).child("per10").getValue().toString());
                per_twe.setText(dataSnapshot.child("user").child(username).child("per12").getValue().toString());
                cg_1.setText(dataSnapshot.child("user").child(username).child("cg1").getValue().toString());
                cg_2.setText(dataSnapshot.child("user").child(username).child("cg2").getValue().toString());
                cg_3.setText(dataSnapshot.child("user").child(username).child("cg3").getValue().toString());
                cg_4.setText(dataSnapshot.child("user").child(username).child("cg4").getValue().toString());
                cg_5.setText(dataSnapshot.child("user").child(username).child("cg5").getValue().toString());
                cg_6.setText(dataSnapshot.child("user").child(username).child("cg6").getValue().toString());

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        ref.addValueEventListener(VV);

        return V;
    }

}

