/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.sds.core.system.commission.postarget.utils;

import com.mobinil.sds.core.system.commission.postarget.model.WeekDays;
import java.util.*;

/**
 *
 * @author mabdelaal
 */
public class CalendarUtility {
    public HashMap<Integer, String> getQuaraters() {
        HashMap<Integer, String> quartarsHM = new HashMap<Integer, String>(5);
//        quartarsHM.put(0, "Please select quarter");
        quartarsHM.put(1, "Q1 From 01/01 To 31/03");
        quartarsHM.put(2, "Q2 From 01/04 To 30/06");
        quartarsHM.put(3, "Q3 From 01/07 To 30/09");
        quartarsHM.put(4, "Q4 From 01/10 To 31/12");
        return quartarsHM;
    }

    public HashMap<Integer, String> getMonths() {
        HashMap<Integer, String> monthsHM = new HashMap<Integer, String>(12);        
        monthsHM.put(1, "JAN");
        monthsHM.put(2, "FEB");
        monthsHM.put(3, "MAR");
        monthsHM.put(4, "APR");
        monthsHM.put(5, "MAY");
        monthsHM.put(6, "JUN");
        monthsHM.put(7, "JUL");
        monthsHM.put(8, "AUG");
        monthsHM.put(9, "SEP");
        monthsHM.put(10, "OCT");
        monthsHM.put(11, "NOV");
        monthsHM.put(12, "DEC");
        return monthsHM;
    }
    
    public static void main(String args [])
    {
        CalendarUtility ss = new         CalendarUtility ();
        for (Integer string : ss.getQuaraters().keySet()) {
            System.out.println(string);
        }
    
    }

    public SortedMap<Integer, Integer> getYears() {
        /*Get years form  database.*/
        SortedMap<Integer, Integer> yearsHM = new TreeMap<Integer, Integer>();
        yearsHM.put(1, 2008);
        yearsHM.put(1, 2009);
        yearsHM.put(2, 2010);
        yearsHM.put(3, 2011);
        yearsHM.put(4, 2012);        
        yearsHM.put(5, 2013);        
        yearsHM.put(6, 2014);        
        yearsHM.put(7, 2015);        
        yearsHM.put(8, 2016);        
        return yearsHM;
    }
    public SortedMap<Integer, Integer> getYears(String yearId) {
        SortedMap<Integer, Integer> oneYearHM = new TreeMap<Integer, Integer>();
        oneYearHM.put(1,getYears().get(Integer.parseInt(yearId)));
        
        return oneYearHM;
        
    }
    public int getYearById (String yearId){    
        return getYears().get(Integer.valueOf(yearId));
    }
    
    public SortedMap<Integer, Vector<WeekDays>> getYearMonthsRanges(SortedMap<Integer, Integer> yearsHM){
    SortedMap<Integer, Vector<WeekDays>> yearMonths = new TreeMap<Integer, Vector<WeekDays>>();        
    GregorianCalendar cal = new GregorianCalendar();
    int numberOfWeeksInYear = 0;
        for (int yearKey : yearsHM.keySet()) {
            cal.set(yearsHM.get(yearKey), 0, 1);
           numberOfWeeksInYear =   cal.getActualMaximum(Calendar.WEEK_OF_YEAR);            
            fillYearMonths(yearMonths, numberOfWeeksInYear, yearsHM.get(yearKey), cal);            
        }        
    return yearMonths;
    
    }
    
   private void fillYearMonths (SortedMap<Integer, Vector<WeekDays>> yearMonths,int numberOfWeeksInYear,int year,Calendar cal){
       Vector<WeekDays> yearMonthsVec = new Vector<WeekDays>();
   HashMap<Integer, String> monthsHM = getMonths();   
   WeekDays weekdays = null;
       for (int monthKey : monthsHM.keySet()) {           
           cal.set(year, monthKey, 0);
           int daysInMonth = cal.get(Calendar.DAY_OF_MONTH);
           for (int weeknumber = 1; weeknumber <= numberOfWeeksInYear; weeknumber++) {
               weekdays = getStartAndEndDaysPerWeek(daysInMonth, weeknumber, cal);
               if (weekdays!=null)yearMonthsVec.add(weekdays);    
           }
       }
       yearMonths.put(year, yearMonthsVec);
   }
   
   private WeekDays getStartAndEndDaysPerWeek(int daysInMonth,int weeknumber,Calendar cal){
        Vector<Integer> days = new Vector<Integer>();        
     for (int iDays = 1; iDays <= daysInMonth; iDays++) {
            cal.set(Calendar.DATE, iDays);
            if (cal.get(Calendar.WEEK_OF_MONTH) == weeknumber) {
                days.add(iDays);
            }
        }     
     return days.isEmpty() ? null :
             new WeekDays(weeknumber,cal.get(Calendar.MONTH)+1,days.get(0).intValue(),days.lastElement().intValue());    
    }
}
