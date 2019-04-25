package com.example.user.myapplication;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Vector;

public class quadratic extends AppCompatActivity {
    private LineGraphSeries<DataPoint> series1, series2;
    private EditText coefficientText;
    private Button coefficientOkButton, solveButton, nextRootButton, previousRootButton,repeat;
    private TextView finalText, congratsMessage,MB;
    private final int degree = 2;
    public int controlTill = 0;
    public int track=0;
    public int k=0;
    int rootIndex = 0;
    double coeff[]=new double[100];
    public TextView TrackCoeff ;

    Vector<Double> coefficients = new Vector<>();
    Vector<String> result=new Vector<>();
    private double x1;
    private Animation a1, a2, a3, a4, a5, a6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quadratic);

        coefficientText = (EditText) findViewById(R.id.QuadCoeffInputId);
        coefficientOkButton = (Button) findViewById(R.id.CoeffOkId);
        solveButton = (Button) findViewById(R.id.solveButtonId);
        nextRootButton = (Button) findViewById(R.id.nextRootButtonId);
        previousRootButton = (Button) findViewById(R.id.previusRootButtonId);
        finalText = (TextView) findViewById(R.id.finalmessageId);
        congratsMessage = (TextView) findViewById(R.id.rootNumberMessageId);
        TrackCoeff = (TextView) findViewById(R.id.NumOfCoeffEntered);
        repeat = (Button) findViewById(R.id.REPEAT);

        MB=(TextView)findViewById(R.id.B);


        previousRootButton.setEnabled(false);
        nextRootButton.setEnabled(false);
        coefficientOkButton.setEnabled(false);
        solveButton.setEnabled(false);
        coefficientText.setEnabled(true);
        //repeat.setVisibility(0);
        repeat.setEnabled(false);

        LinearLayout l1 = (LinearLayout) findViewById(R.id.Ql4);
        a1 = AnimationUtils.loadAnimation(this, R.anim.qdowntoup);
        l1.setAnimation(a1);

        LinearLayout l2 = (LinearLayout) findViewById(R.id.Ql1);
        a2 = AnimationUtils.loadAnimation(this, R.anim.quptodown);
        l2.setAnimation(a2);

        LinearLayout l3 = (LinearLayout) findViewById(R.id.Ql2);
        a3 = AnimationUtils.loadAnimation(this, R.anim.qlefttoright);
        l3.setAnimation(a3);

        LinearLayout l4 = (LinearLayout) findViewById(R.id.Ql3);
        a4 = AnimationUtils.loadAnimation(this, R.anim.qrighttoleft);
        l4.setAnimation(a4);

        previousRootButton.setVisibility(View.INVISIBLE);
        nextRootButton.setVisibility(View.INVISIBLE);

        MB.setText(Html.fromHtml("ax<sup>2</sup>+bx+c=0"));
    }


    @Override
    protected void onRestart() {
        super.onRestart();
    }
    public void CoeffOk(View v){
        try{
            String temp = coefficientText.getText().toString();
            double ce = Double.parseDouble(temp);
            coefficients.add(ce);
            coeff[track]=ce;
            track++;
            TrackCoeff.setText(track+"/ "+ (degree+1)+ " coefficients entered");
            if(track==1){
                coefficientText.setHint("Enter  coefficient b:");
            }
            if(track==2){
                coefficientText.setHint("Enter  coefficient c:");
            }
            if(track>2){
                coefficientText.setHint("");
            }

            //     double ce = Double.parseDouble();
            controlTill++;
            if(controlTill > degree){
                //  controlTill++;
                solveButton.setEnabled(true);
                coefficientOkButton.setEnabled(false);
                coefficientText.setEnabled(false);
                coefficientText.setText(null);
                //   finalText.setText(coefficientText.getText().toString());
                coefficientText.clearComposingText();
            }
            else{
                //  controlTill++;
                coefficientText.setText(null);
            }
        }catch(Exception e){
            Toast.makeText(quadratic.this, "Please enter co-efficients ", Toast.LENGTH_LONG).show();
        }
    }
    public void CoeffEntered(View v){
        coefficientOkButton.setEnabled(true);
    }

    public void SolvePressed(View view){
        solveButton.setEnabled(false);
        solution(view);
        solveButton.setVisibility(View.INVISIBLE);
        repeat.setEnabled(true);
        repeat.setVisibility(View.VISIBLE);
        previousRootButton.setVisibility(View.VISIBLE);
        nextRootButton.setVisibility(View.VISIBLE);
        repeat.setVisibility(View.VISIBLE);
        repeat.setEnabled(true);

        LinearLayout l5 = (LinearLayout) findViewById(R.id.Ql5);
        a5 = AnimationUtils.loadAnimation(this, R.anim.qlefttoright);
        l5.setAnimation(a5);
        LinearLayout l6 = (LinearLayout) findViewById(R.id.Ql6);
        a6 = AnimationUtils.loadAnimation(this, R.anim.qrighttoleft);
        l6.setAnimation(a6);

        graph();
    }
    public void graph(){
        double X = 200, Y, temp = 5.0;
        GraphView g = (GraphView) findViewById(R.id.QGraph);

        series1 = new LineGraphSeries<>();

        for(int i = -100; i <= 100; i++){
            X = i;
            Y = Horner(X);
            series1.appendData(new DataPoint(X, Y), true, 200);
        }
        g.addSeries(series1);

        g.getViewport().setYAxisBoundsManual(true);
        g.getViewport().setMaxY(200);
        //

        g.getViewport().setXAxisBoundsManual(true);
        g.getViewport().setMinX(2);
        g.getViewport().setMaxX(40);

        // enable scaling and scrolling
        g.getViewport().setScalable(true);
        g.getViewport().setScalableY(true);
    }

    public void solution(View v){
        //final MediaPlayer mp = MediaPlayer.create(this, R.raw.mkta);
      //  mp.start();
        BAIRSTOW b=new BAIRSTOW(coeff);
        result=b.findroot();
        int t;
        t=result.size();
        nextRootButton.setEnabled(true);
        String s;
        s = result.elementAt(0);
        congratsMessage.setText("Root " + (rootIndex+1) + "/"+degree+ " is:" );
        finalText.setText(" " + s);
    }

    public void NextPressed(View view){

        rootIndex++;
        previousRootButton.setEnabled(true);
        if(rootIndex < degree){
            String s;
            s = result.elementAt(rootIndex);
            congratsMessage.setText("Root " + (rootIndex+1) + "/" + degree + " is:" );
            finalText.setText(" " + s);
            if(rootIndex == degree-1){
                nextRootButton.setEnabled(false);
            }
        }
    }

    public void PrevPressed(View view){
        rootIndex--;
        nextRootButton.setEnabled(true);
        if(rootIndex >= 0){
            String s;
            s = result.elementAt(rootIndex);
            congratsMessage.setText("Root " + (rootIndex+1) + "/" + degree + " is:" );
            finalText.setText(" " + s);
            if(rootIndex <= 0){
                previousRootButton.setEnabled(false);
            }
        }
    }
    public void ReInitialize(View v){
        finish();
        Intent intent = new Intent(this, quadratic.class);
        startActivity(intent);
        //onRestart();
    }
    public double Horner(double x){
        double res = coeff[0];
        for(int i = 1; i <= degree; i++){
            res = res*x+coeff[i];
        }
        return res;
    }
}
