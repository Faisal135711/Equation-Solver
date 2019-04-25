package com.example.user.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends Activity implements View.OnClickListener{
    private Button nonLinearEquationButton, multipleVariableEquationButton, linearEquationButton;
    private RelativeLayout l1, l2, l3, l4, l5;
    private Animation a1, a2, a3, a4, a5;
    private TextView nameText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameText = (TextView) findViewById(R.id.appName);
        nameText.setSelected(true);

        nonLinearEquationButton = (Button) findViewById(R.id.nonLinearButtonId);
        nonLinearEquationButton.setOnClickListener(this);

        multipleVariableEquationButton = (Button) findViewById(R.id.multipleVariableButtonId);
        multipleVariableEquationButton.setOnClickListener(this);

        linearEquationButton = (Button) findViewById(R.id.linearButtonId);
        linearEquationButton.setOnClickListener(this);

        l1 = (RelativeLayout) findViewById(R.id.l1);
        a1 = AnimationUtils.loadAnimation(this, R.anim.t1);
        l1.setAnimation(a1);

        l2 = (RelativeLayout) findViewById(R.id.l2);
        a2 = AnimationUtils.loadAnimation(this, R.anim.b1);
        l2.setAnimation(a2);

        l3 = (RelativeLayout) findViewById(R.id.l3);
        a3 = AnimationUtils.loadAnimation(this, R.anim.b2);
        l3.setAnimation(a3);

        l4 = (RelativeLayout) findViewById(R.id.l4);
        a4 = AnimationUtils.loadAnimation(this, R.anim.b3);
        l4.setAnimation(a4);

        l5 = (RelativeLayout) findViewById(R.id.l5);
        a5 = AnimationUtils.loadAnimation(this, R.anim.b4);
        l5.setAnimation(a5);
    }
    public void onClick(View view) {
        if(view.getId() == R.id.linearButtonId){
            Intent intent = new Intent(this, LinearActivity.class);
            startActivity(intent);
        }
        else if(view.getId() == R.id.nonLinearButtonId){
         //   System.out.println("Aj Styles");
            Intent intent = new Intent(this, nonLinearActivity.class);
            startActivity(intent);
        }
        else if(view.getId() == R.id.multipleVariableButtonId){
            Intent intent = new Intent(MainActivity.this, multipleVariableGauss.class);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Are you sure you want to exit?");
        builder.setCancelable(true);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        AlertDialog alertDialog = builder.create();

        alertDialog.show();
    }

    public void GoToQuad(View v){
        Intent intent = new Intent(MainActivity.this, quadratic.class);
        startActivity(intent);

    }
}
