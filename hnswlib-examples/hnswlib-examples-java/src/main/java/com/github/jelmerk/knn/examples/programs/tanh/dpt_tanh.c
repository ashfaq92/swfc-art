// for dll: gcc -o dpt_tanh.dll -shared dpt_tanh.c
// January 10, 2003
// Revised from C++ translation by Dave Towey
#include <stdlib.h>
#include <stdio.h>
#include <math.h>
double tanh(double u)
{
	double epu = exp(u);
	double emu = 1.0/epu;

	if (fabs(u) < 0.3)
	{
		double u2 = u*u;
		return ( 2*u*(1+u2/6*(1+u2/20*(1+u2/42*(1+u2/72))))/(epu+emu) );
	} 
	else 
	{
		double difference = epu - emu;
		double sum = epu + emu;
		double fraction = difference/sum;
		return fraction;
	}
}

double tanhm(double u)
{
	double epu = exp(u);
	double emu = 1.0/epu;

	/* ERROR */
	/* if (fabs(u) < 0.3) */
	if (fabs(u) <=0.9)
	{
		double u2 = u*u;
		/* ERROR */
		/* return ( 2*u*(1+u2/6*(1+u2/20*(1+u2/42*(1+u2/72))))/(epu+emu) ); */
		return ( 2*u*(1+u2/6*(1+u/20*(1+u2/42*(1-u2/72))))/(epu+emu) );
	}
	else 
	{
		double difference = epu - emu;
		double sum = epu + emu;
		double fraction = difference/sum;
		return fraction;
	}
}
