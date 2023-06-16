package com.example.crowdfunding;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    EditText username;
    EditText password;
    TextView signup;
    UserDBHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.loginEmail);
        password = findViewById(R.id.loginPassword);
        signup = findViewById(R.id.linkToSignUp);
        helper = new UserDBHelper(this, "infodb", null, 1);
        signup.setOnClickListener(v -> {
                Intent i = new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(i);
            }
        );
    }

    public void onLogin(View v){
        String user = username.getText().toString();
        String pass = password.getText().toString();
        if(user.equals("") || pass.equals(""))
            return;
        if( helper.checkUser(user, pass) ){
            Bundle bundle = new Bundle();
            bundle.putString("Username", user);
            String text = helper.getText(user);
            bundle.putString("Text", text);
            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
            try {
                Intent intent = new Intent(LoginActivity.this, Home.class);
                intent.putExtra("data", bundle);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }catch(Exception e){
                Log.e("mytag", "" + e);
            }
        }
        else{
            Toast.makeText(this, "Entered Credentials do not match", Toast.LENGTH_SHORT).show();
        }
    }
}