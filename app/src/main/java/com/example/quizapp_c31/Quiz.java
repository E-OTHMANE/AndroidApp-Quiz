package com.example.quizapp_c31;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

//import com.bumptech.glide.Glide;
import com.example.quizapp_c31.entities.Questions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Quiz extends AppCompatActivity {

    public static int numQuestion=0;
    public static int nbrReponse=0;
    String reponse="";
    ImageView imgQuiz;
    TextView question,username;
    RadioGroup allAnswers;
    RadioButton answer;
    Button next;
    FirebaseDatabase db;
    DatabaseReference dbRef;
    List<Questions> questions=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        username=(TextView) findViewById(R.id.tvUsername);
        imgQuiz=(ImageView) findViewById(R.id.imageQuiz);
        question=(TextView) findViewById(R.id.tvQuestion);
        allAnswers=(RadioGroup) findViewById(R.id.rgAnwsers);
        next=(Button) findViewById(R.id.btNext);
        //finish=(Button) findViewById(R.id.btFinish);
        db=FirebaseDatabase.getInstance();
        dbRef=db.getReference().child("Questions");

        username.setText("user :"+Login.playerName);

        if(numQuestion==0){nbrReponse=0;}


            dbRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        HashMap<String, Object> map = (HashMap<String, Object>) data.getValue();
                        Questions ques = new Questions();
                        ques.setReponse(map.get("Reponse").toString());
                        ques.setLibeller(map.get("Libeller").toString());
                        ques.setImageURL(map.get("URL").toString());
                        questions.add(ques);
                    }
                    if(numQuestion<9 && numQuestion>=0) {
                        Questions ques = questions.get(numQuestion);
                        question.setText(ques.getLibeller());
                        reponse = ques.getReponse();
                        Picasso.get()
                                .load(ques.getImageURL())
                                .into(imgQuiz);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });



        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index =allAnswers.getCheckedRadioButtonId();
                if(index==-1)
                {
                    Toast.makeText(getApplicationContext(), "Choisisez une reponse", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    answer=(RadioButton) findViewById(index);
                    if(answer.getText().toString().equals(reponse))
                    {
                        answer.setBackgroundColor(Color.parseColor("#058A10"));
                        nbrReponse++;
                    }
                    else
                    {
                            answer.setBackgroundColor(Color.parseColor("#8A0505"));
                    }
                    numQuestion++;
                    if(numQuestion<9)
                    {
                        startActivity(new Intent(Quiz.this,Quiz.class));
                        finish();
                        if(numQuestion==7) next.setText("Finish");
                    }
                    else
                    {
                        startActivity(new Intent(Quiz.this,Score.class));
                        finish();
                    }
                    answer.setChecked(false);
                }
            }
        });
    }
}