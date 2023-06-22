package com.example.crowdfunding;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class QRCodeActivity extends AppCompatActivity {

    private ImageView qrCodeImageView;
    private String link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);

        qrCodeImageView = findViewById(R.id.qr_code_image_view);
        TextView infoQRCode = findViewById(R.id.info_qrcode);
        try {
            Bundle bundle = getIntent().getBundleExtra("data");
            String upiID = bundle.getString("UpiId");
            String name = bundle.getString("Name");
            String amount = bundle.getString("Amount");
            link = "upi://pay?pa=" + upiID + "&pn=" + name + "&am=" + amount + "&cu=INR";
            String info = "\nPaying To: " + name + "\nUPI ID: " + upiID;
            infoQRCode.setText(info);
            Log.d("myTag", link);

            generateAndDisplayQRCode();
        }catch(Exception e){
            Log.d("myTag", "" + e);
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    private void generateAndDisplayQRCode() {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {

            BitMatrix bitMatrix = qrCodeWriter.encode(link, BarcodeFormat.QR_CODE, 512, 512);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap qrCodeBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    qrCodeBitmap.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }

            qrCodeImageView.setImageBitmap(qrCodeBitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

}
