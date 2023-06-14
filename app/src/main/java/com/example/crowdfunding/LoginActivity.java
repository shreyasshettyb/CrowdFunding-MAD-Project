package com.example.crowdfunding;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crowdfunding.db.DBHelper;

public class LoginActivity extends AppCompatActivity {
    EditText username;
    EditText password;
    TextView signup;
    DBHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.loginEmail);
        password = findViewById(R.id.loginPassword);
        signup = findViewById(R.id.linkToSignUp);
        helper = new DBHelper(this, "infodb", null, 1);
        signup.setOnClickListener(v -> {
                Intent i = new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(i);
            }
        );
    }

    public void onLogin(View v){
        if( helper.checkUser(username.getText().toString(), password.getText().toString()) ){
            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, AdminHome.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "Entered Credentials do not match", Toast.LENGTH_SHORT).show();
        }
    }
}