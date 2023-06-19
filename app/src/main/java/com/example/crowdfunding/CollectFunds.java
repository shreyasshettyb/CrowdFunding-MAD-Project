package com.example.crowdfunding;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crowdfunding.Models.Transaction;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

public class CollectFunds extends AppCompatActivity {
    String username, fundingCode, fundingName;
    EditText amount, upiEditText;
    UserDBHelper userDBHelper;
    TransactionDBHelper transactionDBHelper;
    String choice = "";
    boolean radioChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_collect_funds);
            Bundle bundle = getIntent().getBundleExtra("data");
            username = bundle.getString("Username");
            fundingCode = bundle.getString("FundingCode");
            fundingName = bundle.getString("FundingName");

            amount = findViewById(R.id.amount_fundCollection);
            upiEditText = findViewById(R.id.upiID_fundCollection);

            userDBHelper = new UserDBHelper(this, "userDB", null, 1);
            transactionDBHelper = new TransactionDBHelper(this, "transactionDB", null, 1);

            ((TextView) findViewById(R.id.fundingName)).setText(fundingName);
        }catch(Exception e){
            Log.e("myTag", "" + e);
        }
    }

    public void updateAmount(View v){
        try {
            TextView textView = (TextView) v;
            String amt = textView.getText().toString();
            amount.setText(amt);
        }catch (Exception e){
            Log.e("myTag", "" + e);
        }
    }

    public void onSubmit(View v){
        String donorName = ((EditText) findViewById(R.id.donorName_fundCollection)).getText().toString();
        String upiID = upiEditText.getText().toString();
        double amountVal;

        if (! radioChecked){
            Toast.makeText(this, "Select CASH or UPI", Toast.LENGTH_LONG).show();
            return;
        }

        if(choice.equals("upi") && ! isUpiIdValid(upiID))
            return;

        try {
            amountVal = Double.parseDouble(amount.getText().toString());
        }catch(Exception e){
            Log.e("myTag", "" + e);
            Toast.makeText(this, "Amount is required", Toast.LENGTH_LONG).show();
            return;
        }

        if (! ( (CheckBox) findViewById(R.id.checkBox_authorize) ).isChecked() ){
            Toast.makeText(this, "Click the checkbox", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            // Get the current date and time
            Calendar calendar = Calendar.getInstance();
            Date currentDate = calendar.getTime();

            // Display the current date and time
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            String dateTime = dateFormat.format(currentDate);

            String collectorName = userDBHelper.getUserName(username);
            if(collectorName.equals("")){
                Toast.makeText(this, "Not Authorised, Please Log In again", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(CollectFunds.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }

            if(! transactionDBHelper.addTransaction(new Transaction(donorName, upiID, dateTime, amountVal, username, fundingCode, collectorName)) )
                Toast.makeText(this, "Some Error occurred, Please try again", Toast.LENGTH_LONG).show();
            else{
                Bundle bundle = userDBHelper.getInfo(username);
                if(bundle == null){
                    Toast.makeText(this, "Something went wrong, Try Log In", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(CollectFunds.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    return;
                }
                bundle.putString("Username", username);

                Toast.makeText(this, "Successfully registered the transaction", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(CollectFunds.this, Home.class);
                intent.putExtra("data", bundle);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }

        }catch(Exception e){
            Log.e("myTag", "In CollectFunds" + e);
        }
    }

    public void onRadioButtonClicked(View view) {
        radioChecked = ((RadioButton) view).isChecked();
        choice = ((RadioButton) view).getText().toString().toLowerCase();
        LinearLayout upiLayout = findViewById(R.id.upiLayout_fundCollection);
        if(choice.equals("cash"))
            upiLayout.setVisibility(View.GONE);
        else
            upiLayout.setVisibility(View.VISIBLE);

    }

    private boolean isUpiIdValid(String upiId){

        Pattern upiPattern = Pattern.compile("^[a-zA-Z0-9.-]{2,256}@[a-zA-Z][a-zA-Z]{2,64}$");
        if (upiId.equals(""))
            Toast.makeText(this, "UPI ID is required", Toast.LENGTH_LONG).show();
        else if (! upiPattern.matcher(upiId).matches())
            Toast.makeText(this, "UPI ID must be valid", Toast.LENGTH_LONG).show();
        else
            return true;
        return false;
    }
}