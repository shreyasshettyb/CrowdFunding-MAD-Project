package com.example.crowdfunding;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.crowdfunding.DBHelpers.UserDBHelper;
import com.example.crowdfunding.Models.User;

import java.util.Random;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {
    EditText name;
    EditText email;
    EditText password;
    EditText conPassword;
    Spinner type;
    EditText fundingCode;
    EditText fundingName;
    UserDBHelper helper;
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
//        fundingName = findViewById(R.id.fundingName);
        helper = new UserDBHelper(this, "innodb", null, 1);
    }

    public void signUp(View v){

        String uname = name.getText().toString(), e = email.getText().toString(), pass = password.getText().toString(), conPass = conPassword.getText().toString();
        String fundingC = fundingCode.getText().toString();
        //String fundingName = fundingName.getText().toString();
        String fundingName = "TESTING";
        String tp = type.getSelectedItem().toString();

        if(uname.equals("") || e.equals("") || pass.equals("") || conPass.equals("") || (tp.equals("Volunteer") && fundingC.equals("")) || fundingName.equals(""))
            return;

        if(!isEmailValid(e)){
            Toast.makeText(this, "Invalid email", Toast.LENGTH_LONG).show();
            return;
        }

        if (helper.isUserPresent(e)) {
            Toast.makeText(this, "Entered Credentials already exists", Toast.LENGTH_LONG).show();
        } else if (isPasswordValid(pass, conPass)) {
            if (tp.equals("Admin")) {
                fundingC = generateRandomWord();
                while (helper.isFundingCodePresent(fundingC)) {
                    fundingC = generateRandomWord();
                }
            }
            else if (helper.isFundingCodePresent(fundingC) || !isFundingCodeValid(fundingC)) {
                Toast.makeText(this, "Invalid FundingCode", Toast.LENGTH_LONG).show();
                return;
            }
            if (helper.addUser(new User(uname, e, pass, tp, fundingC, fundingName))) {
                Bundle bundle = new Bundle();
                bundle.putString("Username", uname);
                String text = fundingName + "+" + fundingC;
                if(tp.equals("Admin"))
                    text += "+Want to add volunteers? Share this code\n                        " + fundingC;
                else
                    text += "+Thanks for being a volunteer";
                bundle.putString("Text", text);
                Toast.makeText(this, "Successfully Registered", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(SignupActivity.this, Home.class);
                intent.putExtra("data", bundle);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Error occurred during registration, Please try again", Toast.LENGTH_LONG).show();
            }
        }
    }

    private boolean isPasswordValid(String pass, String repass){

        String msg;
        Pattern lowerCase = Pattern.compile("^.*[a-z].*$");
        Pattern upperCase = Pattern.compile("^.*[A-Z].*$");
        Pattern number = Pattern.compile("^.*[0-9].*$");
        //Pattern specialCharacter = Pattern.compile("^.*[^a-zA-Z0-9].*$");
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

    private String generateRandomWord() {
        String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";
        int LENGTH = 6;
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < LENGTH; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            sb.append(randomChar);
        }

        return sb.toString();
    }

    private boolean isFundingCodeValid(String fc){
        Pattern regex = Pattern.compile("^[A-Z1-9]{6}$");
        return regex.matcher(fc).matches();
    }

    private boolean isEmailValid(String email){
        Pattern regex = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
        return regex.matcher(email).matches();
    }
}