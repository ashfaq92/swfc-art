
// for dll:gcc -o dpt_gammq.dll -shared dpt_gammq.c
#include <stdbool.h>
#include <stdlib.h>
#include <stdio.h>
#include <math.h>

float gammln(float xx)
{
	double x,tmp,ser;
	static double cof[6]={76.18009173,-86.50532033,24.01409822,
		-1.231739516,0.120858003e-2,-0.536382e-5};
	int j;

	x=xx-1.0;
	tmp=x+5.5;
	tmp -= (x+0.5)*log(tmp);
	ser=1.0;
	for (j=0;j<=5;j++) {
		x += 1.0;
		ser += cof[j]/x;
	}
	return -tmp+log(2.50662827465*ser);
}

#define ITMAX 100
#define EPS 3.0e-7

void gcf(float *gammcf, float a, float x, float *gln)
{
	int n;
	float gold=0.0,g,fac=1.0,b1=1.0;
	float b0=0.0,anf,ana,an,a1,a0=1.0;

	*gln=gammln(a);
	a1=x;
	for (n=1;n<=ITMAX;n++) {
		an=(float) n;
		ana=an-a;
		a0=(a1+a0*ana)*fac;
		b0=(b1+b0*ana)*fac;
		anf=an*fac;
		a1=x*a0+anf*a1;
		b1=x*b0+anf*b1;
		if (a1) {
			fac=1.0/a1;
			g=b1*fac;
			if (fabs((g-gold)/g) < EPS) {
				*gammcf=exp(-x+a*log(x)-(*gln))*g;
				return;
			}
			gold=g;
		}
	}
	perror("a too large, ITMAX too small in routine GCF");
}

#undef ITMAX
#undef EPS

#define ITMAX 100
#define EPS 3.0e-7

void gser(float *gamser, float a, float x, float *gln)
{
	int n;
	float sum,del,ap;

	*gln=gammln(a);
	if (x <= 0.0) {
		if (x < 0.0) perror("x less than 0 in routine GSER");
		*gamser=0.0;
		return;
	} else {
		ap=a;
		del=sum=1.0/a;
		for (n=1;n<=ITMAX;n++) {
			ap += 1.0;
			del *= x/ap;
			sum += del;
			if (fabs(del) < fabs(sum)*EPS) {
				*gamser=sum*exp(-x+a*log(x)-(*gln));
				return;
			}
		}
		perror("a too large, ITMAX too small in routine GSER");
		return;
	}
}

void gserm(float *gamser, float a, float x, float *gln)
{
	int n;
	float sum,del,ap;

	*gln=gammln(a);
	if (x <= 0.0) {
		if (x < 0.0) perror("x less than 0 in routine GSER");
		*gamser=0.0;
		return;
	} else {
		ap=a;
		del=sum=1.0/a;
		for (n=1;n<=ITMAX;n++) {
			ap += 1.0;
			del *= x/ap;
			sum += del;
			/* ERROR */
			/* if (fabs(del) < fabs(sum)*EPS) { */
			if (fabs(del) <= fabs(sum)*EPS) {
				*gamser=sum*exp(-x+a*log(x)-(*gln));
				return;
			}
		}
		perror("a too large, ITMAX too small in routine GSER");
		return;
	}
}

#undef ITMAX
#undef EPS

float gammq(float a, float x)
{
	float gamser,gammcf,gln;

	if (x < 0.0 || a <= 0.0) perror("Invalid arguments in routine GAMMQ");
	if (x < (a+1.0)) {
		gser(&gamser,a,x,&gln);
		return 1.0-gamser;
	} else {
		gcf(&gammcf,a,x,&gln);
		return gammcf;
	}
}

float gammqm(float a, float x)
{
	float gamser,gammcf,gln;

	/* ERROR */
	/* if (x < 0.0 || a <= 0.0) nrerror("Invalid arguments in routine GAMMQ"); */
	if (x <= 0.0 || a <= 0.0) perror("Invalid arguments in routine GAMMQ");
	/* ERROR */
	/* if (x < (a+1.0)) { */
	if (x <= (a+2.5)) {
	/* if (x <= (a+10.0)) { */
		gserm(&gamser,a,x,&gln);
		return 1.0-gamser;
	} else {
		gcf(&gammcf,a,x,&gln);
		return gammcf;
	}
}

double original_fn(double x, double y)
{
	return gammq((float) x, (float) y);
}

double modified_fn(double x, double y)
{
	return gammqm((float) x, (float) y);
}

// Need to override this here to get around the nerror problem
bool Produces_Error(double x, double y)
{
	bool error_exists = false;

	if (y == 0.0) // nerror problem in modified version
		error_exists = true;
	else if (x == 0.0) // nerror problem, but not an error!
		error_exists = false;
	else // shouldn't be any nerror problems
		error_exists = (original_fn(x, y) != modified_fn(x, y));

	return error_exists;
}
