//taken from
//https://shodhganga.inflibnet.ac.in/bitstream/10603/202479/18/18_appendix.pdf
// calculate number of days for a given date range
//gcc -o NumberDays.dll -shared NumberDays.c
#include <stdbool.h>
#include <stdio.h>
int NumberDays(int day0, int month0, int year0, int day1, int month1, int year1)
{
    int exit = -1;
    int aux,month,day,total = -1;
    //securing that dates are right
    if (year0 >= 2000 && year0<2100 && year1>=2000 && year1<2100 && month0>0 && month0<=12 && month1>0 && month1<=12 && day0>0&& day1>0)
    {
        if (month0 <= 7)
        {
            if(month0%2==0) //month pair
            {
                if (month0==2) //february
                {
                    //calcualting leap
                    if(year0%4==0)
                        day = 29;
                    else
                        day = 28;
                } //if (month0==2)
                 else
                    day = 30;
            }// if(monthO% 2 == 0)
            else
                day = 31;
        } // if (month0 <= 7)
        else
        {
            if(month0%2==0)
                day =31;
            else
                day = 30;
        }
        if(day0>day)
            total = -1;
        else
        {
            if (month1<=7)
            {
                if(month1 %2 == 0) // month pair
                {
                    if(month1 == 2) // February
                    {
                        //calcucating leap
                        if (year1 % 4 == 0)
                            day = 29;
                        else
                            day = 28;
                    }// if(month1 == 2)
                    else
                        day = 30;
                } //if(month1 %2 == 0)
                else
                    day = 31;
            } //  if (month1<=7)
            else
            {
                if(month1%2==0)
                    day = 31;
                else
                    day = 30;
            }
            if (day1>day)
            {
                total = -1;
                return(total);
            }
            else {
                // Securing that he puts a date on it 0 is younger that he puts a date on it 1
                if (year0 > year1) {
                    aux = day0;
                    day0 = day1;
                    day1 = aux;
                    aux = month0;
                    month0 = month1;
                    month1 = aux;
                    aux = year0;
                    year0 = year1;
                    year1 = aux;
                } else {
                    if (year0 == year1) {
                        if (month0 > month1) {
                            aux = day0;
                            day0 = day1;
                            day1 = aux;
                            aux = month0;
                            month0 = month1;
                            month1 = aux;
                        } else {
                            if (month0 == month1 && day0 > day1) {
                                aux = day0;
                                day1 = day0;
                                // day0 = aux;
                                day1 = aux;
                            }
                        }
                    }
                }
            }
            total = 0;
            if(year0 != year1)
            {
                // considering the number if days to the 1/1/year0+1
                month = month0;
                // days to the month following
                if (month <= 7)
                {
                    if (month %2 == 0) // month pair
                    {
                        if (month==2)//february
                        {
                            //calculating leap
                            if (year0%4==0)
                                total+=(29-day0);
                            else
                                total+=(28-day0);
                        } //if (month==2)
                        else
                            total+=(30-day0);
                    } //if(month%2==0)
                    else
                        total+=(31-day0);
                } //if (month<=7)
                else
                {
                    if (month % 2 == 2)
                        total += (31 - day0);
                    else
                        total += (30 - day0);
                }
                //even final days of year
                month++;
                while(month<=12)
                {
                    if (month<=7)
                    {
                        if (month%2 == 0) //month pair
                        {
                            if (month == 2) //february
                            {
                                //calculating leap
                                if(year0%4 == 0)
                                    total += 29;
                                else
                                    total +=28;
                            } // if (month ==2)
                            else
                                total += 30;
                        } // if (month%2==0)
                        else
                            total += 31;
                    } //if (month <=7)
                    else
                    {
                        if(month %2 == 0)
                            total += 31;
                        else
                            total += 30;
                    }
                    month++;
                } //while (month<=12)
                year0++; //we passed another year
                // considering the number of years to year1
                while (year0!=year1)
                {
                    if(year0>year1)
                        return(total);
                    if (year0%4==0)
                        total += 366;
                    else
                        total += 365;
                    year0++;
                }//while (year0!=year1)
                total++;
                /* because bill was even the 31 of the 12 and the new date the 1 comes from the 1 the new
                 * initial date 0 is day 1, month 01, year0==year1*/
                day0=1;
                month0 = 1;
            } //if(year0 != year1)
            //Counting the days from monthO to month1, knowing that the year is the same one
            if (month0!=month1)
            {
                //days to the month following
                if(month0<=7)
                {
                    if(month0%2 == 0) //month pair
                    {
                        if (month0 == 2) //february
                        {
                            //calculcating leap
                            if (year0%4==0)
                                total+=(29-day0);
                            else
                                total+=(28-day0);
                        } // if (month0 == 2)
                        else
                            total += (30-day0);
                    } // if(month0%2 == 0)
                    else
                        total+=(31-day0);
                } // if(monthO <= 7)
                else
                {
                    if(month0%2==0)
                        total+=(31-day0);
                    else
                        total += (30-day0);
                }
                month0++;
                //counting month0 to month1
                while(month0<month1)
                {
                    if (month0<=7)
                    {
                        if(month0 % 2 == 0) //month pair
                        {
                            if (month0 == 2) // Februrary
                            {
                                //calcucating leap
                                if (year0 % 4 == 0)
                                    total += 29;
                                else
                                    total += 28;
                            } // if (month0 == 2)
                            else
                                total += 30;
                        } // if(month0 % 2 == 0)
                        else
                            total += 31;
                    } //if (month0<=7)
                    else
                    {
                        if (month0 % 2 ==0)
                            total += 31;
                        else
                            total += 30;
                    }
                    month0++;
                }  //while(month0<month1)
                // days of month1
                total+=day1;
            }else
                total += (day1 - day0);
        }
    }
    return total;
}

int NumberDays_m(int day0, int month0, int year0, int day1, int month1, int year1)
{
    int exit = -1;
    int aux,month,day,total = -1;
    //securing that dates are right
    if (year0 >= 2000 && year0<2100 && year1>=2000 && year1<2100 && month0>0 && month0<=12 && month1>0 && month1<=12 && day0>0&& day1>0)
    {
        if (month0 <= 7)
        {
            if(month0 % 2 == 0) //month pair
            {
                if (month0==2) //february
                {
                    //calcualting leap
                    if(year0%4==0)
                        //  SEEDED ERROR
                        // CORRECT STATEMENT
                        // day = 29
                        day = 31;
                    else
                        day = 28;
                } //if (month0==2)
                 else
                    day = 30;
            }// if(monthO% 2 == 0)
            else
                day = 31;
        } // if (month0 <= 7)
        else
        {
            if(month0%2==0)
                day =31;
            else
                day = 30;
        }
        if(day0>day)
            total = -1;
        else
        {
            if (month1<=7)
            {
                if(month1 %2 == 0) // month pair
                {
                    if(month1 == 2) // February
                    {
                        //calcucating leap
                        if (year1 % 4 == 0)
                            day = 29;
                        else
                            day = 28;
                    }// if(month1 == 2)
                    else
                        day = 30;
                } //if(month1 %2 == 0)
                else
                    day = 31;
            } //  if (month1<=7)
            else
            {
                if(month1%2==0)
                    day = 31;
                else
                    day = 30;
            }
            if (day1>day)
            {
                total = -1;
                return(total);
            }
            else {
                // Securing that he puts a date on it 0 is younger that he puts a date on it 1
                if (year0 > year1) {
                    aux = day0;
                    day0 = day1;
                    day1 = aux;
                    aux = month0;
                    month0 = month1;
                    month1 = aux;
                    aux = year0;
                    year0 = year1;
                    year1 = aux;
                } else {
                    if (year0 == year1) {
                        if (month0 > month1) {
                            aux = day0;
                            day0 = day1;
                            day1 = aux;
                            aux = month0;
                            month0 = month1;
                            month1 = aux;
                        } else {
                            if (month0 == month1 && day0 > day1) {
                                aux = day0;
                                day1 = day0;
                                // day0 = aux;
                                day1 = aux;
                            }
                        }
                    }
                }
            }
            total = 0;
            if(year0 != year1)
            {
                // considering the number if days to the 1/1/year0+1
                month = month0;
                // days to the month following
                if (month <= 7)
                {
                    if (month %2 == 0) // month pair
                    {
                        if (month==2)//february
                        {
                            //calculating leap
                            if (year0%4==0)
                                total+=(29-day0);
                            else
                                total+=(28-day0);
                        } //if (month==2)
                        else
                            total+=(30-day0);
                    } //if(month%2==0)
                    else
                        total+=(31-day0);
                } //if (month<=7)
                else
                {
                    if (month % 2 == 2)
                        total += (31 - day0);
                    else
                        total += (30 - day0);
                }
                //even final days of year
                month++;
                while(month<=12)
                {
                    if (month<=7)
                    {
                        if (month%2 == 0) //month pair
                        {
                            if (month == 2) //february
                            {
                                //calculating leap
                                if(year0%4 == 0)
                                    total += 29;
                                else
                                    total +=28;
                            } // if (month ==2)
                            else
                                total += 30;
                        } // if (month%2==0)
                        else
                            total += 31;
                    } //if (month <=7)
                    else
                    {
                        if(month %2 == 0)
                            total += 31;
                        else
                            total += 30;
                    }
                    month++;
                } //while (month<=12)
                year0++; //we passed another year
                // considering the number of years to year1
                while (year0!=year1)
                {
                    if(year0>year1)
                        return(total);
                    if (year0%4==0)
                        total += 366;
                    else
                        total += 365;
                    year0++;
                }//while (year0!=year1)
                total++;
                /* because bill was even the 31 of the 12 and the new date the 1 comes from the 1 the new
                 * initial date 0 is day 1, month 01, year0==year1*/
                day0=1;
                month0 = 1;
            } //if(year0 != year1)
            //Counting the days from monthO to month1, knowing that the year is the same one
            if (month0!=month1)
            {
                //days to the month following
                if(month0<=7)
                {
                    if(month0%2 == 0) //month pair
                    {
                        if (month0 == 2) //february
                        {
                            //calculcating leap
                            if (year0%4==0)
                                total+=(29-day0);
                            else
                                total+=(28-day0);
                        } // if (month0 == 2)
                        else
                            total += (30-day0);
                    } // if(month0%2 == 0)
                    else
                        total+=(31-day0);
                } // if(monthO <= 7)
                else
                {
                    if(month0%2==0)
                        total+=(31-day0);
                    else
                        total += (30-day0);
                }
                month0++;
                //counting month0 to month1
                while(month0<month1)
                {
                    if (month0<=7)
                    {
                        if(month0 % 2 == 0) //month pair
                        {
                            if (month0 == 2) // Februrary
                            {
                                //calcucating leap
                                if (year0 % 4 == 0)
                                    total += 29;
                                else
                                    total += 28;
                            } // if (month0 == 2)
                            else
                                total += 30;
                        } // if(month0 % 2 == 0)
                        else
                            total += 31;
                    } //if (month0<=7)
                    else
                    {
                        if (month0 % 2 ==0)
                            total += 31;
                        else
                            total += 30;
                    }
                    month0++;
                }  //while(month0<month1)
                // days of month1
                total+=day1;
            }else
                total += (day1 - day0);
        }
    }
    return total;
}

bool Produces_Error(int a, int b, int c, int d, int e, int f) {
//    printf("%d\n", NumberDays(a,b,c,d,e,f));
//    printf("%d", NumberDays_m(a,b,c,d,e,f));
    int val1 = NumberDays(a,b,c,d,e,f);
    int val2 = NumberDays_m(a,b,c,d,e,f);
    return val1 != val2;
}
//#include <stdio.h>
//
//int main() {
//    printf("Hello, World!\n");
//    int x = NumberDays(24,4,2000,26,12,2019);
//    printf("%d", x);
//    return 0;
//}