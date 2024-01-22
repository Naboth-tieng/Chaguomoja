package com.example.vote_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class login extends AppCompatActivity {
    /*final EditText ID=findViewById(R.id.txtid);
    final EditText password = findViewById(R.id.txtpassword);
    final Button btnlogin=findViewById(R.id.btnlogin);
    final TextView reg=findViewById(R.id.reg);*/
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://kath-log-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final EditText ID=findViewById(R.id.txtid);
        final EditText password = findViewById(R.id.txtpassword);
        final Button btnlogin=findViewById(R.id.btnlogin);
        final TextView reg=findViewById(R.id.reg);
        reg.setEnabled(false);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String IDtxt = ID.getText().toString().trim();
                final String passwordtxt = password.getText().toString().trim();
                if(IDtxt.isEmpty() || passwordtxt.isEmpty()) {
                    Toast.makeText(login.this, "please enter your ID or password", Toast.LENGTH_SHORT).show();
                }else{
                    databaseReference.child("citizens").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //check if ID is in the Firebase
                            if(snapshot.hasChild(IDtxt)){
                                //ID is present in the Database
                                //now reterive the password
                                final String getpassword = snapshot.child(IDtxt).child("password").getValue(String.class);
                                final String getcounty = snapshot.child(IDtxt).child("county").getValue(String.class);
                                final String getvote = snapshot.child(IDtxt).child("voted").getValue(String.class);
                                if(getvote.equals("yes")){
                                    Toast.makeText(getApplicationContext(),"Citizen already voted",Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(login.this,MainActivity2.class);
                                    i.putExtra("county",getcounty);
                                    startActivity(i);
                                    finish();
                                }else{
                                if(getpassword.equals(passwordtxt)){
                                    Toast.makeText(login.this, "Successfully Logged in", Toast.LENGTH_SHORT).show();
                                    //Toast.makeText(login.this,getcounty,Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(login.this,Presidency.class);
                                    i.putExtra("county",getcounty);
                                    i.putExtra("id",IDtxt);
                                    startActivity(i);
                                    finish();
                                }else{
                                    Toast.makeText(login.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                                }}
                            }else{
                                Toast.makeText(login.this, "Citizen not Registered", Toast.LENGTH_SHORT).show();
                                reg.setEnabled(true);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }

                }
        });
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(login.this,"am working",Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(login.this,Register.class));
                startActivity(new Intent(login.this,Register.class));
            }

        });
    }

}