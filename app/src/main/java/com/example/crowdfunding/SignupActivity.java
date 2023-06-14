package com.example.crowdfunding;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.crowdfunding.db.DBHelper;
import com.example.crowdfunding.db.User;

import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {
    EditText name;
    EditText email;
    EditText password;
    EditText conPassword;
    Spinner type;
    EditText fundingCode;
    DBHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.signupPassword);
        conPassword = findViewById(R.id.conPassword);
        fundingCode = findViewById(R.id.fundingCode);
        type = findViewById(R.id.type);
        helper = new DBHelper(this, "infodb", null, 1);
    }

    public void signUp(View v){
        if( isPasswordValid()) {
            if(helper.isUserPresent(email.getText().toString())){
                Toast.makeText(this, "Entered Credentials already exists", Toast.LENGTH_LONG).show();
            }
            else {
                if (type.getSelectedItem().toString().equals("Admin")) {
                    fundingCode.setText("");
                }
                if (helper.addUser(new User(name.getText().toString(), email.getText().toString(), password.getText().toString(), type.getSelectedItem().toString(), fundingCode.getText().toString()))){
                    Toast.makeText(this, "Successfully Registered", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SignupActivity.this, AdminHome.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(this, "Error occurred during registration, Please try again", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private boolean isPasswordValid(){
        String msg;
        Pattern lowerCase = Pattern.compile("^.*[a-z].*$");
        Pattern upperCase = Pattern.compile("^.*[A-Z].*$");
        Pattern number = Pattern.compile("^.*[0-9].*$");
        //Pattern specialCharacter = Pattern.compile("^.*[^a-zA-Z0-9].*$");
        String pass = password.getText().toString(), repass = conPassword.getText().toString();
        if(pass.equals("") || repass.equals(""))
            msg = "Enter password and Confirm it";
        else if(pass.length() < 8)
            msg = "Password must at least be 8 characters long";
        else if(! lowerCase.matcher(pass).matches())
            msg = "Password must at least have one lowercase letter";
        else if(! upperCase.matcher(pass).matches())
            msg = "Password must at least have one uppercase letter";
        else if(! number.matcher(pass).matches())
            msg = "Password must at least have one digit";
        else if(! pass.equals(repass))
            msg = "Both passwords must be same";
        else
            return true;

        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        return false;
    }
}