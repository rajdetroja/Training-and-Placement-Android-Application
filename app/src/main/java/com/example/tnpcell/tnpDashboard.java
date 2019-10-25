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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@SuppressLint("ValidFragment")
public class tnpDashboard extends Fragment {
    Context t;
    @SuppressLint("ValidFragment")
    public tnpDashboard(Context c) {
        t=c;
    }
    View V;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        V=inflater.inflate(R.layout.fragment_tnpdashboard, container, false);
        FloatingActionButton fab = V.findViewById(R.id.addcompany);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(t,AddCompany.class);
                startActivity(intent);
            }
        });
        final GridView gridView =  V.findViewById(R.id.tnpdashboard);

        array=new ArrayList<>();
        Log.d("sfdd","haaa");
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(t);
        String branch = prefs.getString("branch", "computer");
        DatabaseReference ddatabase= FirebaseDatabase.getInstance().getReference().child(branch);
        set=new HashSet<>();
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
                    if(!set.contains(new companydetail(name,ctc,profile,cgpa,lastdate,onboardate))) {
                        array.add(new companydetail(name, ctc, profile, cgpa, lastdate, onboardate));
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

        Log.d("debug","c");
        Log.d("debug","d");
        Log.d("debug","e");
        return V;
    }



    public static class companydetail{
        String com_name;
        String ctc;
        String profile;
        String cgpa;
        String lastdate;
        String onboarddate;

        companydetail(String com,String ct,String p,String c,String l,String o){
            com_name=com;ctc=ct;profile=p;cgpa=c;lastdate=l;onboarddate=o;
        }
    }
    ArrayList<companydetail> array;
    HashSet<companydetail> set;
    public class DashboardAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            Log.d("ans ", String.valueOf(array.size()));

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
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater li = (LayoutInflater) LayoutInflater.from(t);
            View v = li.inflate(R.layout.dashboardgridview,null,false);
            TextView company=v.findViewById(R.id.ctc);
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
            Button b = v.findViewById(R.id.removecompany);
            b.setVisibility(View.VISIBLE);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(t);
                    String branch = prefs.getString("branch", "computer");
                    final FirebaseDatabase database = FirebaseDatabase.getInstance();
                    final DatabaseReference ref = database.getReference(branch).child("company").child(compan.getText().toString());
                    ref.removeValue();
                }
            });


            return v;
        }
    }

}