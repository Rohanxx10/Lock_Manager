package com.example.lock;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.io.InputStream;



public class login extends AppCompatActivity {

    Button buttons;
    ImageView imageView;
    EditText user,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        buttons=findViewById(R.id.button);
        imageView=findViewById(R.id.gifImageView);


        user=findViewById(R.id.editTextText);
        password=findViewById(R.id.editTextText2);





            Glide.with(this)
                    .asGif()
                    .load("file:///android_asset/decision-making.gif") // Ensure the file name matches
                    .into(imageView);


        Intent intent=new Intent(this,MainActivity.class);
        buttons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String u=user.getText().toString();
                String p=password.getText().toString();
                if(u.equals("test@admin.com") && p.equals("12345678")) {
                    startActivity(intent);
                    finish();
                }
                else {
                    user.setError("Enter Valid id");
                    password.setError("Enter Valid password");
                }
            }
        });



    }
}