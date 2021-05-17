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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class Register extends AppCompatActivity {

    Button signUp,back;
    ImageView imgRegister;
    EditText fullName,email,password,passwordConfirmation;
    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        signUp=(Button) findViewById(R.id.btSignUp);
        back=(Button) findViewById(R.id.btBack);
        fullName=(EditText) findViewById(R.id.etFName);
        email=(EditText) findViewById(R.id.etEmail);
        password=(EditText) findViewById(R.id.etPassword);
        passwordConfirmation=(EditText) findViewById(R.id.etCPassword);
        auth=FirebaseAuth.getInstance();

        imgRegister=(ImageView) findViewById(R.id.imgRegister);

        storageRef = storage.getReference("register.png");
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(imgRegister);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(fullName.getText())) {Toast.makeText(getApplicationContext(),"Please write your full name",Toast.LENGTH_LONG).show();}
                else{
                    if(TextUtils.isEmpty(email.getText())) {Toast.makeText(getApplicationContext(),"Please write your e-amil",Toast.LENGTH_LONG).show();}
                    else{
                        if(TextUtils.isEmpty(password.getText())) { Toast.makeText(getApplicationContext(),"Please write your password",Toast.LENGTH_LONG).show();}
                        else
                        {
                            if(TextUtils.isEmpty(passwordConfirmation.getText())) { Toast.makeText(getApplicationContext(),"Please confirm your password",Toast.LENGTH_LONG).show();}
                            else {
                                if(password.getText().length()<6) { Toast.makeText(getApplicationContext(),"The password should be 6 or more caracters",Toast.LENGTH_LONG).show();}
                                else{
                                    if(!password.getText().toString().equals(passwordConfirmation.getText().toString())) {Toast.makeText(getApplicationContext(),"The password and the confirmation should be alike",Toast.LENGTH_LONG).show();}
                                    else{
                                        String mail=email.getText().toString();
                                        String psswrd=password.getText().toString();
                                        auth.createUserWithEmailAndPassword(mail,psswrd).addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if(task.isSuccessful())
                                                {
                                                    //S'authentifier pour modifier le nom d'utilisateur creer
                                                    auth.signInWithEmailAndPassword(mail,psswrd).addOnCompleteListener(Register.this,new OnCompleteListener<AuthResult>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                                            user=FirebaseAuth.getInstance().getCurrentUser();
                                                            UserProfileChangeRequest profileUpate= new UserProfileChangeRequest.Builder()
                                                                    .setDisplayName(fullName.getText().toString())
                                                                    .build();
                                                            user.updateProfile(profileUpate);
                                                        }
                                                    });
                                                    Toast.makeText(getApplicationContext(), "You've been sign up with success", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(Register.this, Login.class));
                                                    finish();
                                                }
                                                else {
                                                    Toast.makeText(getApplicationContext(), "There might be a problem, verify what you wrote down", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }
                                }
                            }
                        }
                    }

                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this,Login.class));
                finish();
            }
        });
    }
}