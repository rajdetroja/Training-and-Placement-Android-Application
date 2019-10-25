package com.example.tnpcell;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;

@SuppressLint("ValidFragment")
public class Dashboard extends Fragment {
    Context t;
    View V;
    @SuppressLint("ValidFragment")
    public Dashboard(Context c) {
        t=c;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        V=inflater.inflate(R.layout.fragment_dashboard, container, false);
        array=new ArrayList<>();
        set=new HashSet<>();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(t);
        String branch = prefs.getString("branch", "computer");

        final GridView gridView =  V.findViewById(R.id.userdashboard);
        DatabaseReference ddatabase= FirebaseDatabase.getInstance().getReference().child(branch);

        ValueEventListener VV=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                array=new ArrayList<>();
                for(DataSnapshot u: dataSnapshot.child("company").getChildren()) {
                    String name=u.getKey();
                    String ctc= u.child("ctc").getValue().toString();
                    String profile= u.child("profile").getValue().toString();
                    String cgpa= u.child("cgpa").getValue().toString();
                    String lastdate= u.child("lastdate").getValue().toString();
                    String onboardate= u.child("onboarddate").getValue().toString();
                    if(!set.contains(new tnpDashboard.companydetail(name,ctc,profile,cgpa,lastdate,onboardate))) {
                        array.add(new tnpDashboard.companydetail(name, ctc, profile, cgpa, lastdate, onboardate));
                    }
                }
                gridView.setAdapter(new DashboardAdapter());
                new DashboardAdapter().getItem(0);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        ddatabase.addValueEventListener(VV);
        return V;
    }

    ArrayList<tnpDashboard.companydetail> array;
    HashSet<tnpDashboard.companydetail> set;
    public class DashboardAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return array.size();
        }

        @Override
        public Object getItem(int position) {
            notifyDataSetChanged();
            return null;
        }

        @Override
        public long getItemId(int position) {
            notifyDataSetChanged();
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater li = (LayoutInflater) LayoutInflater.from(t);
            View v = li.inflate(R.layout.dashboardgridview,null,false);
            final TextView company=v.findViewById(R.id.ctc);
            company.setText(array.get(position).ctc);
            TextView profile=v.findViewById(R.id.profile);
            profile.setText(array.get(position).profile);
            TextView cgpa=v.findViewById(R.id.cgpa);
            cgpa.setText(array.get(position).cgpa);
            final TextView compan=v.findViewById(R.id.company);
            compan.setText(array.get(position).com_name);

            TextView onboard=v.findViewById(R.id.onboarddate);
            onboard.setText(array.get(position).onboarddate);

            TextView lastdate=v.findViewById(R.id.lastdate);
            lastdate.setText(array.get(position).lastdate);
            final Button b = v.findViewById(R.id.apply);
            b.setVisibility(View.VISIBLE);
            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(t);
            String branchs = pref.getString("branch", "computer");
            final String usernamee = pref.getString("username", "0");
            final FirebaseDatabase databasse = FirebaseDatabase.getInstance();

            final DatabaseReference reff = databasse.getReference(branchs).child("company");

            DatabaseReference rr=reff.child(array.get(position).com_name).child("applied");
            ValueEventListener V=new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot u: dataSnapshot.getChildren()) {
                        String com = (String) u.getValue();
                        Log.d(com,usernamee);
                        if (com.equals(usernamee)) {
                            b.setText("Applied");
                        }

                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };
            rr.addValueEventListener(V);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(t);
                    String branch = prefs.getString("branch", "computer");
                    final String username = prefs.getString("username", "0");
                    final FirebaseDatabase database = FirebaseDatabase.getInstance();
                    final DatabaseReference ref = database.getReference(branch).child("company");
                    DatabaseReference r=ref.child(compan.getText().toString()).child("applied");
                    ValueEventListener VV=new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            boolean check=false;
                            for(DataSnapshot u: dataSnapshot.getChildren()) {
                                String com = (String) u.getValue();
                                if (com.equals(username)) {
                                    b.setText("Applied");
                                    check = true;
                                }

                            }
                            if(!check) {
                                ref.child(compan.getText().toString()).child("applied").push().setValue(username);
                                Toast.makeText(t, "Applied Successfully for "+compan.getText().toString(), Toast.LENGTH_SHORT).show();
                            }

                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    };
                    r.addValueEventListener(VV);

                }
            });
            return v;
        }
    }

}