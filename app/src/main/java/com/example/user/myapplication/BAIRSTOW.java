package com.example.user.myapplication;

import java.text.DecimalFormat;
import java.util.Vector;

/**
 * Created by FAHIM on 1/31/2018.
 */

public class BAIRSTOW {
    double[] coeff=new double[100];
    public int degree=2;
    public int track=0;
    public int k=0;
    DecimalFormat df=new DecimalFormat("#.#####");
    Vector<Double> coefficients = new Vector<>();
    Vector<String> result=new Vector<>();

    BAIRSTOW(double[] coef){
        this.coeff=coef;
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

    public Vector<String>  findroot(){

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
            k++;
        }

        // printf("\n\nEnter two initial approximation value : \n\n");
        // scanf("%lf %lf",&r,&s);
        r=0.1;
        s=0.1;
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

        return result;

    }

}
