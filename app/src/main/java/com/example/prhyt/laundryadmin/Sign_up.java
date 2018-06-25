package com.example.prhyt.laundryadmin;

//Sign-Up Page for New Users

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Sign_up extends AppCompatActivity {

    private EditText username,userEmail,userPass;
    private Button signup;
    private String name,email,password;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setID();
        firebaseAuth = FirebaseAuth.getInstance();
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = username.getText().toString();
                email = userEmail.getText().toString();
                password = userPass.getText().toString();
                if(validate(name,email,password)) {
                    //Toast.makeText(getApplicationContext(),"Works!!!!!!!!",Toast.LENGTH_SHORT).show();
                    String useremail, passw;
                    useremail = userEmail.getText().toString().trim();
                    passw = userPass.getText().toString().trim();
                    firebaseAuth.createUserWithEmailAndPassword(useremail, passw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                sendUserData();
                                Toast.makeText(getApplicationContext(), "Sign-In successful!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Registration Failed!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                }
            }
        });
    }

    private void sendUserData(){
        String s,day,month,year,hours,mins,secs;
        Calendar calendar;
        calendar = new GregorianCalendar();
        int[] arr = {1,2,3,4,5,6,7,8,9,10,11,12};
        //arr[12] = ;
        //int num_month;
       // num_month = calendar.get(Calendar.MONTH);
        //num_month++;

        hours = Integer.toString(calendar.get(Calendar.HOUR_OF_DAY));
        mins = Integer.toString(calendar.get(Calendar.MINUTE));
        secs = Integer.toString(calendar.get(Calendar.SECOND));
        day = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
        month = Integer.toString(arr[calendar.get(Calendar.MONTH)]);
        year = Integer.toString(calendar.get(Calendar.YEAR));
        

        s = hours + ":" + mins + ":" + secs + " " + day + "/" + month + "/" + year;

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference(firebaseAuth.getUid());
        UserInformation userinfo = new UserInformation(name,email,password,0,0,0,s);
        myRef.setValue(userinfo);
    }

    private void setID(){
        username = findViewById(R.id.user_name_admin);
        userEmail = findViewById(R.id.user_email_admin);
        userPass = findViewById(R.id.user_password_admin);
        signup = findViewById(R.id.Sign_up_btn);
    }

    private boolean validate(String naam, String email , String pass){

        if(naam.isEmpty() || email.isEmpty() || pass.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Enter the valid info", Toast.LENGTH_SHORT).show();
            return false;
        }
        else
        {
            return true;
        }
    }
}
