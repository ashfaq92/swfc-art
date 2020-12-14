// for dll: gcc -o dpt_probks.dll -shared dpt_probks.c
#include <stdlib.h>
#include <stdio.h>
#include <math.h>

double probks(double alam)
{
	const double EPS1=0.001, EPS2=1.0e-8;
	double a2, fac, sum, term, termbf;

	a2 = -2.0 * alam * alam;  
	fac = 2.0;  
	sum = 0.0;  
	termbf = 0.0;

	for (int j = 1; j <= 100; j++)
	{
		// Original Pascal uses Pascal routine sqr(j) instead of j*j
		term = fac * exp(a2*(j*j));
		sum += term;

		// In the Numerical recipes reference, '<=' is used instead of '<'
		if ( ( fabs(term) < (EPS1*termbf) ) || ( fabs(term) < (EPS2*sum) ) )
			return sum;
		else
		{
			fac = -fac;
			termbf = fabs(term);
		}
	}

	return 1.0;
}

double probksm(double alam)
{
	const double EPS1=0.001, EPS2=1.0e-8;
	double a2, fac, sum, term, termbf;

	/* ERROR */
	/* a2 = -2.0 * alam * alam; */
	a2 = -2.1 * alam * alam;  
	fac = 2.0;  
	sum = 0.0;  
	termbf = 0.0;

	for (int j = 1; j <= 100; j++)
	{
		// Original Pascal uses Pascal routine sqr(j) instead of j*j
	  	/* ERROR */
		/* term = fac * exp(a2*(j*j)); */
		term = fac * exp(a2+(j*j));
		sum += term;

	  	/* ERROR */
		/*// In the Numerical recipes reference, '<=' is used instead of '<' */
		/* if ( ( fabs(term) < (EPS1*termbf) ) || ( fabs(term) < (EPS2*sum) ) ) */
		if ( ( fabs(term) < (EPS1*termbf) ) || ( fabs(term) > (EPS2*sum) ) )
			return sum;
		else
		{
			/* ERROR */
			/* fac = -fac; */
			fac = -term;
			termbf = fabs(term);
		}
	}
	return 1.0;
}

