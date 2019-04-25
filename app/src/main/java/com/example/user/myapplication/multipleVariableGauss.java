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

public class multipleVariableGauss extends AppCompatActivity {
    private LinearLayout l1, l2, l3, l4, l5, l6;
    private Animation a1, a2, a3, a4, a5, a6;
    EditText MEdegree;
    Button MBdegree;
    EditText MEcoeff;
    Button MBcoeff;
    TextView MTcoeff;
    Button MBsolve;
    Button MBprev,MBnext,MBNewEqn;
    TextView MToutput;
    int trackCoeff=0;
    int trackEqn=1;
    int trackResult=0;
    int trackIndex=0;
    int degree;
    int row=0,column=0;
    double coeff[][]=new double[20][20];
    double solution[]=new double[20];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_variable_gauss);

        MEdegree=(EditText)findViewById(R.id.multipleVariableDegreInputId);
        MBdegree=(Button)findViewById(R.id.multipleVariableDegreOkId);
        MEcoeff=(EditText)findViewById(R.id.multipleVariableCoeficientInputId);
        MBcoeff=(Button)findViewById(R.id.Buttoncoefficient);
        MTcoeff=(TextView)findViewById(R.id.Track);
        MBsolve=(Button)findViewById(R.id.SolveEqn);
        MToutput=(TextView)findViewById(R.id.output);
        MBnext=(Button)findViewById(R.id.Next);
        MBprev=(Button)findViewById(R.id.Prev);
        MBNewEqn=(Button)findViewById(R.id.NewEquation);
        //MBdegree.setEnabled(false);
        MEcoeff.setEnabled(false);
        MBsolve.setEnabled(false);
        MBnext.setVisibility(View.INVISIBLE);
        MBprev.setVisibility(View.INVISIBLE);
        MBNewEqn.setVisibility(View.INVISIBLE);
        MBnext.setEnabled(false);
        MBprev.setEnabled(false);
        MBNewEqn.setEnabled(false);
        //MBcoeff.setEnabled(false);

        l1 = (LinearLayout) findViewById(R.id.ml6);
        a1 = AnimationUtils.loadAnimation(this, R.anim.quptodown);
        l1.setAnimation(a1);

        l2 = (LinearLayout) findViewById(R.id.ml1);
        a2 = AnimationUtils.loadAnimation(this, R.anim.quptodown);
        l2.setAnimation(a1);

        l3 = (LinearLayout) findViewById(R.id.ml2);
        a3 = AnimationUtils.loadAnimation(this, R.anim.qlefttoright);
        l3.setAnimation(a3);

        l4 = (LinearLayout) findViewById(R.id.ml3);
        a4 = AnimationUtils.loadAnimation(this, R.anim.qrighttoleft);
        l4.setAnimation(a4);

        l5 = (LinearLayout) findViewById(R.id.ml4);
        a5 = AnimationUtils.loadAnimation(this, R.anim.qlefttoright);
        l5.setAnimation(a5);

        l6 = (LinearLayout) findViewById(R.id.ml5);
        a6 = AnimationUtils.loadAnimation(this, R.anim.qrighttoleft);
    }

    public void CoeffOkPressed(View v){
        try {
            trackCoeff++;
            double input;
            String temp1 = MEcoeff.getText().toString();
            input = Double.parseDouble(temp1);
            coeff[row][column] = input;
            // MToutput.setText("coeff "+row +"  "+column+" = "+coeff[row][column]);
            column++;
            String temp = trackCoeff + "/" + (degree + 1) + " coefficients of equation " + trackEqn + " entered";
            MTcoeff.setText(temp);
            if (trackCoeff >= degree + 1) {
                trackCoeff = 0;
                trackEqn++;
                row++;
                column = 0;
            }
            if (trackEqn >= degree + 1) {
                MEcoeff.setHint("");
                MEcoeff.setText("");
                MBcoeff.setEnabled(false);
                MEcoeff.setEnabled(false);
                MEcoeff.setHint("");
                MEcoeff.setText("");
                MBsolve.setEnabled(true);
            }

            if (trackEqn < degree + 1) {

                MEcoeff.setHint("");
                MEcoeff.setText("");
                MEcoeff.setHint("Enter coefficients of equation " + trackEqn);
            }
        }
        catch (Exception e){
            Toast.makeText(multipleVariableGauss.this, "Please enter the coefficients", Toast.LENGTH_LONG).show();
        }
    }
    public void DegreeOkPressed(View v){
        try {
            String temp = MEdegree.getText().toString();
            degree = Integer.parseInt(temp);
            MEdegree.setEnabled(false);
            MBdegree.setEnabled(false);
            MEcoeff.setHint("Enter coefficients of equation 1");
            MTcoeff.setText(0 + "/" + (degree + 1) + " coefficients of equation " + trackEqn + " entered");
            MEcoeff.setEnabled(true);
        }

        catch (Exception e){
            Toast.makeText(multipleVariableGauss.this, "Please enter the number of variables first", Toast.LENGTH_LONG).show();
        }
    }

    public void DegreeTouched(View v){
        MBdegree.setEnabled(true);
    }
    public void CoeffTouched(View v){
        MBcoeff.setEnabled(true);
    }
    public void SOLVE(View v){
        MBsolve.setEnabled(false);
        MBsolve.setVisibility(View.INVISIBLE);
        GaussJordan g=new GaussJordan(degree,coeff);
        g.Gauss();
        solution=g.result();
        if(degree>1) {
            MBnext.setVisibility(View.VISIBLE);
            MBprev.setVisibility(View.VISIBLE);
            MBnext.setEnabled(true);

            LinearLayout lprev = (LinearLayout) findViewById(R.id.ml7);
            Animation aprev = AnimationUtils.loadAnimation(this, R.anim.qlefttoright);
            lprev.setAnimation(aprev);

            LinearLayout lnext = (LinearLayout) findViewById(R.id.ml8);
            Animation anext = AnimationUtils.loadAnimation(this, R.anim.qrighttoleft);
            lnext.setAnimation(anext);
        }
        MBNewEqn.setVisibility(View.VISIBLE);
        MBNewEqn.setEnabled(true);
        trackResult++;
        MToutput.setText("x"+trackResult+"="+solution[trackIndex]);
        //final MediaPlayer mp = MediaPlayer.create(this, R.raw.mkta);
       // mp.start();
    }
    public void NextClicked(View v){
        trackResult++;
        trackIndex++;
        MToutput.setText("x"+trackResult+"="+solution[trackIndex]);
        MBprev.setEnabled(true);
        if(trackResult>=degree){
            MBnext.setEnabled(false);
        }
    }
    public void PrevClicked(View v){
        trackResult--;
        trackIndex--;
        MToutput.setText("x"+trackResult+"="+solution[trackIndex]);
        MBnext.setEnabled(true);
        if(trackResult<=1){
            MBprev.setEnabled(false);
        }
    }
    public void Reinitialize(View v){
        finish();
        Intent intent = new Intent(this, multipleVariableGauss.class);
        startActivity(intent);
    }
}
