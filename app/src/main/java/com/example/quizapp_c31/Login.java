package com.example.quizapp_c31;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    public static String playerName="";
    Button signIn;
    ImageView imglogin;
    EditText email,password;
    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef ;
    TextView register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        register=(TextView) findViewById(R.id.tvRegister);
        signIn=(Button) findViewById(R.id.btSignIn);
        email=(EditText) findViewById(R.id.etEmail);
        password=(EditText) findViewById(R.id.etPassword);
        imglogin=(ImageView) findViewById(R.id.imgLogin);
        auth=FirebaseAuth.getInstance();

        storageRef = storage.getReference("register.png");
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(imglogin);
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail=email.getText().toString();
                String psswrd=password.getText().toString();
                if(TextUtils.isEmpty(mail)){
                    Toast.makeText(getApplicationContext(),"Please write your e-amil",Toast.LENGTH_LONG).show();}
                else {
                    if(TextUtils.isEmpty(psswrd)){
                        Toast.makeText(getApplicationContext(),"Please write your password",Toast.LENGTH_LONG).show();}
                    else
                    {
                        auth.signInWithEmailAndPassword(mail,psswrd).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful())
                                {
                                    user=auth.getCurrentUser();
                                    Intent intent = new Intent(Login.this,Quiz.class);
                                    playerName=user.getDisplayName();
                                    Toast.makeText(getApplicationContext(),"Welcome "+user.getDisplayName(),Toast.LENGTH_SHORT).show();
                                    startActivity(intent);
                                    finish();
                                }
                                else {

                                    Toast.makeText(getApplicationContext(),"You might have committed an error in your email or your password",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,Register.class));
                finish();
            }
        });
    }
}