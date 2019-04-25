package com.example.user.myapplication;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.DecimalFormat;

public class LinearActivity extends AppCompatActivity implements View.OnClickListener{
    private LinearLayout l1, l2, l3, l4, l5, l6;
    private Animation a1, a2, a3, a4, a5, a6;
    private LineGraphSeries<DataPoint> series1, series2;
    private Button aOk, bOk, solveButton, newEquationButton;
    private EditText aInput, bInput;
    private TextView rootText, rootMsg;
    private double a, b, x;
    DecimalFormat df=new DecimalFormat("#.#####");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linear);

        aOk = (Button) findViewById(R.id.OkButtonforA);
        aOk.setEnabled(false);
        aOk.setOnClickListener(this);

        bOk = (Button) findViewById(R.id.OkButtonforB);
        bOk.setEnabled(false);
        bOk.setOnClickListener(this);

        solveButton = (Button) findViewById(R.id.solveId);
        solveButton.setOnClickListener(this);
        solveButton.setEnabled(false);

        newEquationButton = (Button) findViewById(R.id.newEquationId);
        newEquationButton.setOnClickListener(this);
        newEquationButton.setVisibility(View.INVISIBLE);

        aInput = (EditText) findViewById(R.id.aId);
        aInput.setOnClickListener(this);

        bInput = (EditText) findViewById(R.id.bId);
        bInput.setOnClickListener(this);

        rootText = (TextView) findViewById(R.id.rootShowId);
        rootMsg = (TextView) findViewById(R.id.msgId);

        l1 = (LinearLayout) findViewById(R.id.li6);
        a1 = AnimationUtils.loadAnimation(this, R.anim.qdowntoup);
        l1.setAnimation(a1);

        l2 = (LinearLayout) findViewById(R.id.li1);
        a2 = AnimationUtils.loadAnimation(this, R.anim.quptodown);
        l2.setAnimation(a2);

        l3 = (LinearLayout) findViewById(R.id.li2);
        a3 = AnimationUtils.loadAnimation(this, R.anim.qlefttoright);
        l3.setAnimation(a3);

        l4 = (LinearLayout) findViewById(R.id.li3);
        a4 = AnimationUtils.loadAnimation(this, R.anim.qrighttoleft);
        l4.setAnimation(a4);

        l5 = (LinearLayout) findViewById(R.id.li4);
        a5 = AnimationUtils.loadAnimation(this, R.anim.qlefttoright);
        l5.setAnimation(a5);

        l6 = (LinearLayout) findViewById(R.id.li5);
        a6 = AnimationUtils.loadAnimation(this, R.anim.qrighttoleft);
        l6.setAnimation(a6);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.aId){
            aOk.setEnabled(true);
        }
        else if(view.getId() == R.id.bId){
            bOk.setEnabled(true);
        }
        else if(view.getId() == R.id.OkButtonforA){
            try{
                a = Double.parseDouble(aInput.getText().toString());
                aOk.setEnabled(false);
                aInput.setEnabled(false);
            }catch (Exception e){
                Toast.makeText(LinearActivity.this, "Please enter a", Toast.LENGTH_LONG).show();
            }
        }
        else if(view.getId() == R.id.OkButtonforB){
            try{
                b = Double.parseDouble(bInput.getText().toString());
                bOk.setEnabled(false);
                bInput.setEnabled(false);
                solveButton.setEnabled(true);
            }catch (Exception e){
                Toast.makeText(LinearActivity.this, "Please enter b", Toast.LENGTH_LONG).show();
            }
        }
        else if(view.getId() == R.id.solveId){
            //final MediaPlayer mp = MediaPlayer.create(this, R.raw.mkta);
            //mp.start();
            solveButton.setEnabled(false);
            solveButton.setVisibility(View.INVISIBLE);
            newEquationButton.setVisibility(View.VISIBLE);
            x = -b/a;
            x=Double.parseDouble(df.format(x));
            String r = Double.toString(x);

            rootMsg.setText("Root is:");
            rootText.setText(r);

            graph();
        }
        else if(view.getId() == R.id.newEquationId){
            finish();
            Intent intent = new Intent(this, LinearActivity.class);
            startActivity(intent);
        }
    }

    public void graph(){
        double X = 200, Y, temp = 5.0;
        GraphView g = (GraphView) findViewById(R.id.linearGraphId);

        series1 = new LineGraphSeries<>();

        for(int i = -100; i <= 100; i++){
            X = i;
            Y = a*X+b;
            series1.appendData(new DataPoint(X, Y), true, 200);
        }
        g.addSeries(series1);

        g.getViewport().setYAxisBoundsManual(true);
        g.getViewport().setMaxY(200);
        //   g.getViewport().setMinY(0);

        g.getViewport().setXAxisBoundsManual(true);
        g.getViewport().setMinX(2);
        g.getViewport().setMaxX(40);

        // enable scaling and scrolling
        g.getViewport().setScalable(true);
        g.getViewport().setScalableY(true);
    }

}
