package com.example.vote_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String[] geo = {"Kiambu","Kisumu","Nairobi"};
    Button register;
    String select;
    EditText firstname,Surname,ID,password,repass;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://kath-log-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firstname=findViewById(R.id.txtfirstname);
        Surname=findViewById(R.id.txtsurname);
        ID=findViewById(R.id.txtid);
        password=findViewById(R.id.txtpassword);
        repass=findViewById(R.id.txtrpass);

        register=findViewById(R.id.reg);

        TextView back=findViewById(R.id.backen);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //Spinner
        Spinner spin = (Spinner)findViewById(R.id.spcounty);
        spin.setOnItemSelectedListener(this);
        ArrayAdapter av=new ArrayAdapter<>(this,R.layout.spinner_list,geo);
        av.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(av);



    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        select = geo[i];

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get data
                final String firstnametxt=firstname.getText().toString().trim();
                final String surnametxt=Surname.getText().toString().trim();
                final String idtxt=ID.getText().toString().trim();
                final String passwordtxt=password.getText().toString().trim();
                final String repasstxt=repass.getText().toString().trim();

                //check if user fill all fields
                if(firstnametxt.isEmpty()||surnametxt.isEmpty()||idtxt.isEmpty()||passwordtxt.isEmpty()){
                    Toast.makeText(Register.this,"Please fill in all fields",Toast.LENGTH_SHORT).show();
                }else if (!passwordtxt.equals(repasstxt)){
                    Toast.makeText(Register.this,"Passwords are not matching",Toast.LENGTH_SHORT).show();
                }else{
                    databaseReference.child("citizens").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //check if citizen is not registered
                            if(snapshot.hasChild(idtxt)){
                                Toast.makeText(Register.this,"Citizen is already registered",Toast.LENGTH_SHORT).show();
                            } else {
                                //sending data
                                // we are using ID as unique identity of every user
                                // so all other details fall under ID
                                databaseReference.child("citizens").child(idtxt).child("firstname").setValue(firstnametxt);
                                databaseReference.child("citizens").child(idtxt).child("surname").setValue(surnametxt);
                                databaseReference.child("citizens").child(idtxt).child("county").setValue(select);
                                databaseReference.child("citizens").child(idtxt).child("password").setValue(passwordtxt);
                                databaseReference.child("citizens").child(idtxt).child("voted").setValue("no");

                                Toast.makeText(Register.this,"User Registered Successfully",Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
