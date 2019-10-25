package com.example.tnpcell;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

@SuppressLint("ValidFragment")
public class tnpBlog extends Fragment {

    Context t;

    @SuppressLint("ValidFragment")
    public tnpBlog(Context n)
    {
        t=n;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View V=inflater.inflate(R.layout.fragment_tnpblog, container, false);
        FloatingActionButton fab = V.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(t,AddBlog.class);

                startActivity(intent);
            }
        });
        Log.d("debug","a");
        final ProgressBar progressBar = V.findViewById(R.id.login_progress);
        progressBar.setVisibility(View.VISIBLE);
        GridView gridView =  V.findViewById(R.id.tnpblog);
        Log.d("debug","b");

        array=new ArrayList<>();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(t);
        String branch = prefs.getString("branch", "computer");
        DatabaseReference ddatabase= FirebaseDatabase.getInstance().getReference().child(branch);
        ValueEventListener VV=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                array=new ArrayList<>();
                for(DataSnapshot u: dataSnapshot.child("blog").getChildren()) {
                    String com=u.getKey();
                    String aut= (String) u.child("author").getValue();
                    String con= (String) u.child("content").getValue();
                    String rol= (String) u.child("role").getValue();
                    String yea= (String) u.child("year").getValue();
                    array.add(new AddBlog.User(com,aut,con,rol,yea));
                }
                new BlogAdapter().getItem(0);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        ddatabase.addValueEventListener(VV);

        Log.d("debug","c");
        Log.d("debug","d");
        progressBar.setVisibility(View.GONE);
        gridView.setAdapter(new BlogAdapter());
        Log.d("debug","e");
        return V;

    }
    ArrayList<AddBlog.User> array;
    public class BlogAdapter extends BaseAdapter{

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
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater li = LayoutInflater.from(t);
            View v = li.inflate(R.layout.bloggridview,null,false);

            final TextView company=v.findViewById(R.id.company);
            company.setText(array.get(position).company);

            TextView author=v.findViewById(R.id.author);
            author.setText(array.get(position).author);

            TextView role=v.findViewById(R.id.role);
            role.setText(array.get(position).role);

            TextView year=v.findViewById(R.id.year);
            year.setText(array.get(position).year);
//
            TextView discrition=v.findViewById(R.id.discription);
            discrition.setText(array.get(position).content);

            Button b = v.findViewById(R.id.removeblog);
            b.setVisibility(View.VISIBLE);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(t);
                    String branch = prefs.getString("branch", "computer");
                    final FirebaseDatabase database = FirebaseDatabase.getInstance();
                    final DatabaseReference ref = database.getReference(branch).child("blog").child(company.getText().toString());
                    ref.removeValue();
                    notifyDataSetChanged();
                }
            });


            return v;
        }
    }



}