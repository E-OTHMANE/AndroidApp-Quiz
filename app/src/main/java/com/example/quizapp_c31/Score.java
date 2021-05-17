package com.example.quizapp_c31;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.List;

public class Score extends AppCompatActivity {

    int reponse;
    float rightAnwsers ;
    float falseAnwsers;
    Button tryAgain,signOut;
    FirebaseAuth auth;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef ;
    TextView mssg;
    ImageView imgScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        mssg=(TextView) findViewById(R.id.tvMssg);
        tryAgain=(Button)findViewById(R.id.btTryAgain);
        signOut=(Button)findViewById(R.id.btSignOut);
        imgScore=(ImageView) findViewById(R.id.imgScore);
        auth = FirebaseAuth.getInstance();
        //https://firebasestorage.googleapis.com/v0/b/quiz-app10.appspot.com/o/score.png?alt=media&token=0ddc5b55-a7b6-4869-b718-9c94a2979c42
        //Picasso.get().load().into(imgScore);
        //String s = storageRef.getDownloadUrl().getResult().toString();
        //Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();

        mssg.setText("Pie presente les reponses de "+Login.playerName);
        storageRef = storage.getReference("score.png");
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(imgScore);
            }
        });

        Pie pie = AnyChart.pie();

        reponse=Quiz.nbrReponse;

        rightAnwsers=reponse;
        falseAnwsers=9-reponse;

        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("Right Anwser",rightAnwsers));
        data.add(new ValueDataEntry("False Anwser",falseAnwsers));

        pie.data(data);
        if(rightAnwsers==falseAnwsers)
            pie.title("Pas mal mais vous pouvez faire mieux ");
        else{
            if (rightAnwsers<falseAnwsers)
                pie.title("Il faut bien recomencer le quiz");
            else
                pie.title("Felicitation vous avez la moitier vrai");
        }
        AnyChartView chartPie = (AnyChartView) findViewById(R.id.cPie);
        chartPie.setChart(pie);

        tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Score.this,Quiz.class));
                Quiz.numQuestion=0;
                finish();
            }
        });

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                startActivity(new Intent(Score.this,Login.class));
                finish();
            }
        });

    }
}