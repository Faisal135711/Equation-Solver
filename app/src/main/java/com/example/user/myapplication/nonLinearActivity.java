package com.example.user.myapplication;


import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
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

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.Vector;

public class nonLinearActivity extends AppCompatActivity implements OnClickListener {
    private LinearLayout l1, l2, l3, l4, l5, l6;
    private Animation a1, a2, a3, a4, a5, a6;
    private LineGraphSeries<DataPoint> series, series2;
    private EditText degreeText, coefficientText;
    private Button coefficientOkButton, solveButton, degreeOkButton, nextRootButton, previousRootButton,repeat;
    private TextView finalText, congratsMessage,Msubscript,MA;
    public int degree, controlTill = 0;
    public int track=0;
    public int k=0;
    int rootIndex = 0;
    double coeff[]=new double[100];
    double h[]=new double[100];
    DecimalFormat df=new DecimalFormat("#.#####");
    public TextView TrackCoeff ;
    Vector<Double> coefficients = new Vector<>();
    Vector<String> result=new Vector<>();
    private double x1;

    //horner
    double funcValue(double x){
        double res = coeff[0];
        for(int i = 1; i <= degree; i++){
            res = res*x+coeff[i];
        }
        return res;
    }
    void linear(double a0,double a1)
    {
        double e=-a0/a1;
        e= Double.parseDouble(df.format(e));
        String s;
        s=""+e;
        result.add(s);
    }

    void quadratic(double t,double r,double s)
    {
        double det,x1,x2,x3,x4;
        det=(r*r)-(4*s*t);
        if(det>=0)
        {
            x1= (-r+Math.sqrt(det))/2*t;
            x2= (-r-Math.sqrt(det))/2*t;
            x1= Double.parseDouble(df.format(x1));
            x2= Double.parseDouble(df.format(x2));

            String s1;
            s1=""+x1;
            result.add(s1);
            s1=""+x2;
            result.add(s1);

        }
        else
        {
            x3=-r/2;
            x4=Math.sqrt(Math.abs(det))/2;
            String s2;
            x3= Double.parseDouble(df.format(x3));
            x4= Double.parseDouble(df.format(x4));
            s2=""+ x3+"+"+x4+"i";
            result.add(s2);
            s2=""+x3+"-"+x4+"i";
            result.add(s2);
        }
    }


    void findroot(){
        int w,j,i,n;
        double t,det,p,q,x1=0.00,x2=0.00,x1r,x1i,r,s,ds,dr;
        double a[]=new double[100];
        double b[]=new double[100];
        double c[]=new double[100];
        double d[]=new double[100];
        double e[]=new double[100];

        b[4]=0;
        b[3]=0;

        n=degree;

        for(i=n; i>=0; i--)
        {

            a[i] = coeff[k];
            //h[2]=546;
            k++;
        }

        r=0.0001;
        s=0.0001;
        w=n;
        if(w==1)
        {
            linear(a[0],a[1]);
            w--;
        }
        if(w==2)
        {
            quadratic(a[2],a[1],a[0]);
            w=w-2;
        }
        while(w>=3)
        {
            for(j=1; j<=50; j++)
            {
                b[n]=a[n];
                b[n-1]=a[n-1]-r*b[n];
                for(i=n-2; i>=1; i--)
                {
                    b[i]=a[i]-r*b[i+1]-s*b[i+2];
                }
                b[0]=a[0]-s*b[2];
                c[n]=b[n];
                c[n-1]=b[n-1]-r*c[n];
                for(i=n-2; i>=2; i--)
                {
                    c[i]=b[i]-r*c[i+1]-s*c[i+2];
                }
                c[1]=-s*c[3];
                d[n]=b[n];
                d[n-1]=b[n-1]-r*d[n];
                for(i=n-2; i>=3; i--)
                {
                    d[i]=b[i]-r*d[i+1]-s*d[i+2];
                }
                d[2]=b[2]-s*d[4];
                dr=(b[0]*d[3]-b[1]*d[2])/((d[2]-r*d[3])*d[2]+s*d[3]*d[3]);
                ds=(-b[1]*s*d[3]-b[0]*(d[2]-r*d[3]))/((d[2]-r*d[3])*d[2]+s*d[3]*d[3]);
                p=r-dr;
                q=s-ds;
                r=p;
                s=q;
            }
            t=1;
            quadratic(t,r,s);
            w=w-2;
            for(i=n; i>=0; i--)
            {
                a[n-i]=b[n-i];
            }
            for(i=n; i>=0; i--)
            {
                a[n-i]=a[n-i+2];
            }
        }
        if(w==2)
        {
            quadratic(b[4],b[3],b[2]);
            w=w-2;
        }
        if(w==1)
        {
            linear(b[2],b[3]);
            w--;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_linear);

        degreeText = (EditText) findViewById(R.id.nonLinearDegreeInputId);
        coefficientText = (EditText) findViewById(R.id.nonLinearCoefficientInputId);
        coefficientOkButton = (Button) findViewById(R.id.okButtonForCoefficientId);
        solveButton = (Button) findViewById(R.id.solveButtonId);
        degreeOkButton = (Button) findViewById(R.id.degreeOkId);
        nextRootButton = (Button) findViewById(R.id.nextRootButtonId);
        previousRootButton = (Button) findViewById(R.id.previusRootButtonId);
        finalText = (TextView) findViewById(R.id.finalmessageId);
        congratsMessage = (TextView) findViewById(R.id.rootNumberMessageId);
        TrackCoeff = (TextView) findViewById(R.id.NumOfCoeffEntered);
        repeat = (Button) findViewById(R.id.REPEAT);

        Msubscript=(TextView) findViewById(R.id.Subscript);
        MA=(TextView) findViewById(R.id.A);

        degreeText.setOnClickListener(this);
        coefficientText.setOnClickListener(this);
        coefficientOkButton.setOnClickListener(this);
        degreeOkButton.setOnClickListener(this);
        solveButton.setOnClickListener(this);
        nextRootButton.setOnClickListener(this);
        previousRootButton.setOnClickListener(this);

        previousRootButton.setEnabled(false);
        nextRootButton.setEnabled(false);
        degreeOkButton.setEnabled(false);
        coefficientOkButton.setEnabled(false);
        solveButton.setEnabled(false);
        coefficientText.setEnabled(false);
        //repeat.setVisibility(0);
        repeat.setEnabled(false);

        previousRootButton.setVisibility(View.INVISIBLE);
        nextRootButton.setVisibility(View.INVISIBLE);
      //  repeat.setVisibility(View.VISIBLE);

        Msubscript.setText(Html.fromHtml("A<sub>n</sub>X<sup>n</sup>"+"+A<sub>n-1</sub>X<sup>n-1</sup>"+"+..."+"+A<sub>1</sub>X"+"+A<sub>O</sub>"+"=0"));
        MA.setText(Html.fromHtml("A<sub>n</sub>,A<sub>n-1</sub>,...A<sub>0</sub>=Coefficients of X<sub>n</sub>,X<sub>n-1</sub>,...X"));

        //animation
        l4 = (LinearLayout) findViewById(R.id.nl6);
        a4 = AnimationUtils.loadAnimation(this, R.anim.qdowntoup);
        l4.setAnimation(a4);

        l1 = (LinearLayout) findViewById(R.id.nl1);
        a1 = AnimationUtils.loadAnimation(this, R.anim.quptodown);
        l1.setAnimation(a1);

        l2 = (LinearLayout) findViewById(R.id.nl2);
        a2 = AnimationUtils.loadAnimation(this, R.anim.qlefttoright);
        l2.setAnimation(a2);

        l3 = (LinearLayout) findViewById(R.id.nl3);
        a3 = AnimationUtils.loadAnimation(this, R.anim.qrighttoleft);
        l3.setAnimation(a3);

        l4 = (LinearLayout) findViewById(R.id.nl4);
        a4 = AnimationUtils.loadAnimation(this, R.anim.qlefttoright);
        l4.setAnimation(a4);

        l5 = (LinearLayout) findViewById(R.id.nl5);
        a5 = AnimationUtils.loadAnimation(this, R.anim.qrighttoleft);
        l5.setAnimation(a5);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.okButtonForCoefficientId){
            try{
                String temp = coefficientText.getText().toString();

                double ce = Double.parseDouble(temp);
                coefficients.add(ce);
                coeff[track]=ce;
                track++;
                TrackCoeff.setText(track+"/ "+ (degree+1)+ " coefficients entered");
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
                Toast.makeText(nonLinearActivity.this, "Please enter co-efficients ", Toast.LENGTH_LONG).show();
            }
        }
        else if(view.getId() == R.id.degreeOkId){
            try{
                degree = Integer.parseInt(degreeText.getText().toString());
                degreeOkButton.setEnabled(false);
                coefficientText.setEnabled(true);
                degreeText.setEnabled(false);
            }catch (Exception e){
                Toast.makeText(nonLinearActivity.this, "Please enter the degree first", Toast.LENGTH_LONG).show();
            }
        }
        else if(view.getId() == R.id.nonLinearDegreeInputId){
            degreeOkButton.setEnabled(true);
        }
        else if(view.getId() == R.id.nonLinearCoefficientInputId){
            coefficientOkButton.setEnabled(true);
        }
        else if(view.getId() == R.id.solveButtonId){
            //   congratsMessage.setText();
            //final MediaPlayer mp = MediaPlayer.create(this, R.raw.mkta);
            //mp.start();
            solveButton.setEnabled(false);
            solveButton.setVisibility(View.INVISIBLE);
            solution(view);

            repeat.setEnabled(true);
            repeat.setVisibility(View.VISIBLE);
            if(degree > 1) {
                previousRootButton.setVisibility(View.VISIBLE);
                nextRootButton.setVisibility(View.VISIBLE);

                l5 = (LinearLayout) findViewById(R.id.nl7);
                a5 = AnimationUtils.loadAnimation(this, R.anim.qlefttoright);
                l5.setAnimation(a5);

                l6 = (LinearLayout) findViewById(R.id.nl8);
                a6 = AnimationUtils.loadAnimation(this, R.anim.qrighttoleft);
                l6.setAnimation(a6);
            }
        }
        else if(view.getId() == R.id.nextRootButtonId){
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
        else if(view.getId() == R.id.previusRootButtonId){
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
    }
    public void solution(View v){
        int t;
        t=result.size();
        findroot();

        nextRootButton.setEnabled(true);

        String s;
        s = result.elementAt(0);
        congratsMessage.setText("Root " + (rootIndex+1) + "/"+degree+ " is:" );
        finalText.setText(" " + s);
        //
        graph();
    }
    public void graph(){
        double x, y;
        GraphView g = (GraphView) findViewById(R.id.graph);
        series = new LineGraphSeries<>();
        series2 = new LineGraphSeries<>();

        for(int i = 0; i < 100; i++){
            x = i;
            y = funcValue(x);
            series.appendData(new DataPoint(x, y), true, 200);
        }
        g.addSeries(series);
        for(int i = -100; i <= 0; i++){
            x = i;
            y = funcValue(x);
            series2.appendData(new DataPoint(x, y), true, 200);
        }
        g.addSeries(series2);
        g.getViewport().setYAxisBoundsManual(true);
        g.getViewport().setMaxY(200);
     //   g.getViewport().setMinY(0);

        g.getViewport().setXAxisBoundsManual(true);
        g.getViewport().setMinX(4);
        g.getViewport().setMaxX(40);

        // enable scaling and scrolling
        g.getViewport().setScalable(true);
        g.getViewport().setScalableY(true);
    //    g.getViewport().setScalable(true);
    }

    public void ReInitialize(View v){
  //      Toast.makeText(nonLinearActivity.this, "Please enter the degree first", Toast.LENGTH_LONG).show();
        finish();
        Intent intent = new Intent(this, nonLinearActivity.class);
        startActivity(intent);
        //onRestart();
    }
}