package com.example.aditya.health_tracker_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
EditText username;
EditText password;
boolean signupMode=true;//checks whether signup mode or login mode selected
    @Override
    public void onClick(View v) {//login<->Signup(modes changing)
        if(v.getId()==R.id.swiTch) {
            TextView loginswitch = findViewById(R.id.swiTch);
            Button button = findViewById(R.id.signUp);
            if (signupMode) {
                signupMode = false;
                loginswitch.setText("or Signup");
                button.setText("Login");
            }
            else {
                signupMode=true;
                loginswitch.setText("or Login");
                button.setText("Signup");
            }
        }
    }

 public void onButtonClick(View view){//When Button is clicked
    if(username.getText().toString().isEmpty()||password.getText().toString().isEmpty()){
        Toast.makeText(MainActivity.this,"Enter valid credentials",Toast.LENGTH_SHORT).show();//when username or password is not not valid
    }
    else {
        if(signupMode){
            //Make a Signup and add user in Parse Database
            ParseUser newuser=new ParseUser();
            newuser.setUsername(username.getText().toString());
            newuser.setPassword(password.getText().toString());
            newuser.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if(e==null){
                        Toast.makeText(MainActivity.this,"User added succesfully!!!",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else{
            //Make a login
            ParseUser.logInInBackground(username.getText().toString().trim(), password.getText().toString(), new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if(user!=null){
                        Toast.makeText(MainActivity.this,"Hi "+username.getText().toString(),Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
 }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username=findViewById(R.id.userName);
        password=findViewById(R.id.passWord);
        TextView loginswitch=findViewById(R.id.swiTch);
        loginswitch.setOnClickListener(this);
        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }


}
