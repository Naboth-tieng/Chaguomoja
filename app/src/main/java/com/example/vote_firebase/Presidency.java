package com.example.vote_firebase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Presidency extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<candidate> list;
    DatabaseReference databaseReference;
    myadapter adapter;
    String Sname;
    int cast;
    String county,id;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presidency);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            county= extras.getString("county");
            id=extras.getString("id");
        }
        Button vote= findViewById(R.id.vote);
        recyclerView = findViewById(R.id.idrvpresidenvy);
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://kath-log-default-rtdb.firebaseio.com/");
        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter= new myadapter(this,list);
        recyclerView.setAdapter(adapter);

        databaseReference.child("candidates").child("president").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    candidate candidate = dataSnapshot.getValue(candidate.class);
                    list.add(candidate);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        vote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get selected candidate
                Sname=adapter.select;
                //pass the vote to database
                databaseReference.child("vote").child("president").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild(Sname)) {
                            String x;
                            x= String.valueOf(snapshot.child(Sname).child("total").getValue());
                            //Toast.makeText(Presidency.this, x, Toast.LENGTH_SHORT).show();
                            cast = Integer.parseInt(x);
                            cast = cast+1;
                            databaseReference.child("vote").child("president").child(Sname).child("total").setValue(cast);

                        } else {
                            databaseReference.child("vote").child("president").child(Sname).child("total").setValue(1);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                // go to senator
                Intent i = new Intent(Presidency.this,senator.class);
                i.putExtra("county",county);
                i.putExtra("id",id);
                startActivity(i);
                finish();
            }
        });
    }

}
