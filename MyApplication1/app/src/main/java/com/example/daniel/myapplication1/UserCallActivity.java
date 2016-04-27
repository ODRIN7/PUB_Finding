package com.example.daniel.myapplication1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.telephony.SmsManager;
import android.view.View;

import butterknife.InjectView;

public class UserCallActivity extends AppCompatActivity {

    private AppCompatEditText contenText;
    private AppCompatEditText phoneText;
    private AppCompatButton actionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_call);

        actionButton = (AppCompatButton) findViewById(R.id.callbutton);
        contenText = (AppCompatEditText) findViewById(R.id.mycontent);
        phoneText = (AppCompatEditText) findViewById(R.id.phonenumber);

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phonenumber = phoneText.getText().toString().trim();
                if (phonenumber.isEmpty()) {
                    if (ActivityCompat.checkSelfPermission(UserCallActivity.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        ActivityCompat.requestPermissions(UserCallActivity.this,new String[]{
                              //  Manifest.permission.CALL_PHONE;
                        Manifest.permission.SEND_SMS
                        },10);
                        return;
                    }
                    startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + Uri.encode(phonenumber))));
                    SmsManager manager = SmsManager.getDefault();
                    manager.sendTextMessage(phonenumber,null,contenText.getText().toString().trim(),null,null);
                }

            }
        });
    }




}
