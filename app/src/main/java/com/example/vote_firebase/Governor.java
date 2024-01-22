package com.example.vote_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Governor extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView textView;
    ArrayList<candidate> list;
    DatabaseReference databaseReference;
    myadapter adapter;
    String Sname;
    int cast;
    String county,id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_governor);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            county= extras.getString("county");
            id=extras.getString("id");
        }
        textView = findViewById(R.id.gvit);
        textView.setText(county);

        citizen ct = new citizen();
        Button vote= findViewById(R.id.gvote);
        recyclerView = findViewById(R.id.grecyclerView);
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://kath-log-default-rtdb.firebaseio.com/");
        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter= new myadapter(this,list);
        recyclerView.setAdapter(adapter);

        databaseReference.child("candidates").child("governor").child(county).addValueEventListener(new ValueEventListener() {
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
                Toast.makeText(Governor.this,Sname,Toast.LENGTH_SHORT).show();

                //pass the vote to database
                databaseReference.child("vote").child("governor").child(county).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild(Sname)) {
                            String x;
                            x= String.valueOf(snapshot.child(Sname).child("total").getValue());
                            //Toast.makeText(Presidency.this, x, Toast.LENGTH_SHORT).show();
                            cast = Integer.parseInt(x);
                            cast = cast+1;
                            databaseReference.child("vote").child("governor").child(county).child(Sname).child("total").setValue(cast);

                        } else {
                            databaseReference.child("vote").child("governor").child(county).child(Sname).child("total").setValue(1);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                databaseReference.child("citizens").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild(id)){
                            databaseReference.child("citizens").child(id).child("voted").setValue("yes");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                Toast.makeText(getApplicationContext(),id,Toast.LENGTH_SHORT).show();

                // go to lastpage
               Intent i = new Intent(Governor.this,MainActivity2.class);
                i.putExtra("county",county);
                startActivity(i);
                finish();
            }
        });
    }
    }