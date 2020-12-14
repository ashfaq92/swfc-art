// for dll: gcc -o dpt_cel.dll -shared dpt_cel.c

#include <math.h>
#include <stdbool.h>
#define CA 0.0003
#define PIO2 1.57079632679490

double cel(double qqc, double pp, double aa, double bb)
{
	double a,b,e,f,g,em,p,q,qc;
    //	void nrerror();

	if (qqc == 0.0) perror("Bad qqc in routine CEL");
	qc=fabs(qqc);
	a=aa;
	b=bb;
	p=pp;
	e=qc;
	em=1.0;
	if (p > 0.0) {
		p=sqrt(p);
		b /= p;
	} else {
		f=qc*qc;
		q=1.0-f;
		g=1.0-p;
		f -= p;
		q *= (b-a*p);
		p=sqrt(f/g);
		a=(a-b)/g;
		b = -q/(g*g*p)+a*p;
	}
	for (;;) {
		f=a;
		a += (b/p);
		g=e/p;
		b += (f*g);
		b += b;
		p=g+p;
		g=em;
		em += qc;
		if (fabs(g-qc) <= g*CA) break;
		qc=sqrt(e);
		qc += qc;
		e=qc*em;
	}
	return PIO2*(b+a*em)/(em*(em+p));
}

double celm(double qqc, double pp, double aa, double bb)
{
	double a,b,e,f,g,em,p,q,qc;
    //	void nrerror();

	if (qqc == 0.0) perror("Bad qqc in routine CEL");
	qc=fabs(qqc);
	a=aa;
	b=bb;
	p=pp;
	e=qc;
	em=1.0;
	/* ERROR */
	/* if (p > 0.0) { */
	if (p >= 0.1) {
		p=sqrt(p);
		b /= p;
	} else {
		f=qc*qc;
		q=1.0-f;
		g=1.0-p;
		/* ERROR */
		/* f -= p; */
		f = p;
		q *= (b-a*p);
		p=sqrt(f/g);
		a=(a-b)/g;
		b = -q/(g*g*p)+a*p;
	}
	for (;;) {
		f=a;
		a += (b/p);
		g=e/p;
		b += (f*g);
		b += b;
		p=g+p;
		g=em;
		em += qc;
		if (fabs(g-qc) <= g*CA) break;
		qc=sqrt(e);
		qc += qc;
		e=qc*em;
	}
	return PIO2*(b+a*em)/(em*(em+p));
}

bool Produces_Error(double x, double y, double z, double w) {
    bool error_exists = false;
    if (x == 0.0) {
        error_exists = false;
    } else {
        error_exists = cel(x, y, z, w) != celm(x, y, z, w);
    }
    return error_exists;
}

#undef CA
#undef PIO2
