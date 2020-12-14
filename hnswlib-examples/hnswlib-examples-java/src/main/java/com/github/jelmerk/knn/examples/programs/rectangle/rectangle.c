//taken from Source Code of Benchmark Programs 
//https://shodhganga.inflibnet.ac.in/bitstream/10603/202479/18/18_appendix.pdf
//gcc -o rectangle.dll -shared rectangle.c

#include <math.h>
#define PRECISION 1e-3
int LineRectangle(double xr1, double xr2, double yr1, double yr2, double xl1, double xl2, double yl1, double yl2)
{
    int exit = -1;
    float x_s, y_s;
    // preconditions
    if (xr1 < xr2 && yr1 < yr2 && xl1 <= xl2)
    {
        // completely covering
        if(xl1 >= xr1 && xl1 <= xr2 && xl2 >= xr1 && xl2 <= xr2 && yl1 >= yr1 && yl1 <= yr2 && yl2 >= yr1 && yl2<=yr2)
            exit = 1;
        else
        {
            if (fabs(yl1 - yl2)<PRECISION)
            {
                if(yl1<yr1)
                    exit=2;
                else
                {
                    if(yl1<yr1)
                        exit=2;
                    else
                    {
                        if(xl1 > xr2 || xl2 < xr1)
                            exit=2;
                        else
                            exit=3;
                    }
                }
            } else {
                if (fabs(xl1 - xl2)<PRECISION) //line vertical
                {
                    if(xl1<xr1)
                        exit=2;
                    else
                    {
                        if(xl1>xr2)
                            exit=2;
                        else
                        {
                            if(yl1>yr2 || yl2<yr1)
                                exit=2;
                            else
                                exit=3;
                        }
                    }
                }
                else
                {
                    if(yl1 < yr1 && yl2<yr1) //superior departs from the rectangle
                        exit=2;
                    else
                    {
                        if(yl1 > yr2 && yl2 > yr2) // infirior departs from the rectangle
                            exit=2;
                        else
                        {
                            if(xl1<xr1 && xl2< xr1)  // left hand departs from the rectangle
                                exit = 2;
                            else
                            {
                                if (xl1 > xr2 && xl2 > xr2) // right departs from the rectangle
                                    exit=2;
                                else
                                {
                                    x_s = (xl2 - xl1) / (yl2 - yl1) * (yr1 - yl1) + xl1;
                                    if(x_s>=xr1 && x_s <= xr2)
                                        exit=3;
                                    else
                                    {
                                        x_s = (xl2 - xl1) / (yl2 - yl1) * (yr2 - yl1) + xl1;
                                        if (x_s >= xr1 && x_s <= xr2)
                                            exit=3;
                                        else
                                        {
                                            y_s = (yl2 - yl1) / (xl2 - xl1) * (xr1 - xl1) + yl1;
                                            if(y_s >= yr1 && y_s <= yr2)
                                                exit = 3;
                                            else
                                            {
                                                y_s = (yl2 - yl1) / (xl2 - xl1) * (xr2 - xr1) + yl1;
                                                if (y_s >= yr1 && y_s <= yr2)
                                                    exit=3;
                                                else
                                                    exit = 2;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    return(exit);
}

int LineRectangle_m(double xr1, double xr2, double yr1, double yr2, double xl1, double xl2, double yl1, double yl2)
{
    int exit = -1;
    float x_s, y_s;
    // preconditions
    if (xr1 < xr2 && yr1 < yr2 && xl1 <= xl2)
    {
        // completely covering
        if(xl1 >= xr1 && xl1 <= xr2 && xl2 >= xr1 && xl2 <= xr2 && yl1 >= yr1 && yl1 <= yr2 && yl2 >= yr1 && yl2<=yr2)
            exit = 1;
        else
        {
            if (fabs(yl1 - yl2)<PRECISION)
            {
                if(yl1<yr1)
                    //SEEDED ERROR
                    //exit=2;
                    exit=1;
                else
                {
                    if(yl1<yr1)
                        exit=2;
                    else
                    {
                        //SEEDED ERROR
                        //if(xl1 > xr2 || xl2 < xr1)
                        if(xl1 > xr2 && xl2 < xr1)
                            //SEEDED ERROR
                            //exit=2;
                            exit=1;
                        else
                            exit=3;
                    }
                }
            } else {
                if (fabs(xl1 - xl2)<PRECISION) //line vertical
                {
                    if(xl1<xr1)
                        exit=2;
                    else
                    {
                        if(xl1>xr2)
                            exit=2;
                        else
                        {
                            //SEEDED ERROR
                            // if(yl1>yr2 || yl2<yr1)
                            if(yl1<yr2 || yl2<yr1)
                                exit=2;
                            else
                                exit=3;
                        }
                    }
                }
                else
                {
                    if(yl1 < yr1 && yl2<yr1) //superior departs from the rectangle
                        exit=2;
                    else
                    {
                        if(yl1 > yr2 && yl2 > yr2) // infirior departs from the rectangle
                            exit=2;
                        else
                        {
                            if(xl1<xr1 && xl2< xr1)  // left hand departs from the rectangle
                                exit = 2;
                            else
                            {
                                if (xl1 > xr2 && xl2 > xr2) // right departs from the rectangle
                                    exit=2;
                                else
                                {
                                    x_s = (xl2 - xl1) / (yl2 - yl1) * (yr1 - yl1) + xl1;
                                    if(x_s>=xr1 && x_s <= xr2)
                                        exit=3;
                                    else
                                    {
                                        x_s = (xl2 - xl1) / (yl2 - yl1) * (yr2 - yl1) + xl1;
                                        //SEEDED ERROR
                                        //if (x_s >= xr1 && x_s <= xr2)
                                        if (x_s >= xr1-2 && x_s <= xr2)
                                            exit=3;
                                        else
                                        {
                                            y_s = (yl2 - yl1) / (xl2 - xl1) * (xr1 - xl1) + yl1;
                                            if(y_s >= yr1 && y_s <= yr2)
                                                exit = 3;
                                            else
                                            {
                                                y_s = (yl2 - yl1) / (xl2 - xl1) * (xr2 - xr1) + yl1;
                                                if (y_s >= yr1 && y_s <= yr2)
                                                    exit=3;
                                                else
                                                    exit = 2;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    return(exit);
}

//bool Produces_Error(double d1, double d2, double d3, double d4, double d5, double d6, double d7, double d8) {
//    return LineRectangle(d1,d2,d3,d4,d5,d6,d7,d8) !=  LineRectangle_m(d1,d2,d3,d4,d5,d6,d7,d8);
//}