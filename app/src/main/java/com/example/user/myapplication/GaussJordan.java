package com.example.user.myapplication;

import java.text.DecimalFormat;

/**
 * Created by FAHIM on 1/31/2018.
 */

public class GaussJordan {

    DecimalFormat df=new DecimalFormat("#.#####");

    int n;
    double coeff[][]=new double[20][20];
    double solution[]=new double[20];

    GaussJordan(int degree,double cf[][]){
        n=degree;
        coeff=cf;
    }
    public void Gauss()
    {
        int i,j,k,m;
        int row,column;
        double temp;
        for(row=0; row<n; row++)
        {
            //normalising each column
            for(column=0,temp=coeff[row][row]; column<=n; column++)
            {
                coeff[row][column]=coeff[row][column]/(temp);
            }


            for(i=0; i<n; i++)
            {
                if(i!=row)
                {
                    for(column=0,temp=coeff[i][row]; column<=n; column++)
                    {
                        coeff[i][column]=coeff[i][column]-(temp*coeff[row][column]);
                    }
                }
            }
        }

        for(i=0;i<n;i++){
            solution[i]=Double.parseDouble(df.format(coeff[i][n]));
        }
    }

    double[] result(){
        return solution;
    }
}
