package com.example.tnpcell;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

@SuppressLint("ValidFragment")
public class tnpRequest extends Fragment {

    Context t;
    @SuppressLint("ValidFragment")
    public tnpRequest(Context c) {
        t=c;
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View V=inflater.inflate(R.layout.fragment_tnprequest, container, false);
        array=new ArrayList<>();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(t);
        String branch = prefs.getString("branch", "computer");
        DatabaseReference ddatabase= FirebaseDatabase.getInstance().getReference().child(branch).child("company");
        ValueEventListener VV=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                array=new ArrayList<>();
                for(DataSnapshot u: dataSnapshot.getChildren()) {
                    String com=u.getKey();
                    boolean check=true;
                    for(DataSnapshot id:u.child("applied").getChildren()){
                        com=com+"\n"+id.getValue();
                        check=false;
                    }
                    if(check){
                        com+="\n"+"No one has Applied";
                    }
                    array.add(com);

                }
                GridView gridView =  V.findViewById(R.id.apply);
                gridView.setAdapter(new AppliedAdapter());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        ddatabase.addValueEventListener(VV);


        return V;
    }

    ArrayList<String> array;
    public class AppliedAdapter extends BaseAdapter {

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
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater li = LayoutInflater.from(t);
            View v = li.inflate(R.layout.appliedgridview,null,false);

            final TextView company=v.findViewById(R.id.company);
            company.setText(array.get(position));
            return v;
        }
    }


}