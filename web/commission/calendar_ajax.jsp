<%-- 
    Document   : calendar_ajax
    Created on : Dec 4, 2011, 3:42:20 PM
    Author     : mabdelaal
--%>

<%@page import="com.mobinil.sds.core.system.commission.postarget.model.WeekDays"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*" %>
<%@page import="com.mobinil.sds.core.system.commission.postarget.utils.CalendarUtility" %>
<%@page import="com.mobinil.sds.web.interfaces.commission.POSTargetInterface" %>
<!DOCTYPE html>
<%
String typeId= request.getParameter(POSTargetInterface.CONTROL_SELECT_PERIOD_TYPE_NAME ) , 
        yearId=request.getParameter(POSTargetInterface.CONTROL_SELECT_YEAR_NAME)  ;
HashMap<Integer, String> itemz = null;
CalendarUtility calUtil = new CalendarUtility();
SortedMap<Integer, Vector<WeekDays>> weekItemzPerYear = null;
StringBuilder str = new StringBuilder();
str.append("<select name=");
str.append(POSTargetInterface.CONTROL_SELECT_DATE_TYPE_NAME);
str.append(" id=");
str.append(POSTargetInterface.CONTROL_SELECT_DATE_TYPE_NAME );
str.append("><option selected=\"selected\" value=\"0\" label=\"---Select---\"/>");
if(typeId.compareTo("0")!=0&& yearId.compareTo("0")!=0){
if (typeId.compareTo(POSTargetInterface.CONSTANT_QUARTER_TYPE)==0)
       {
        itemz = calUtil.getQuaraters();
        str.append("<optgroup label=\"Quarters\">");
        for (int quarterIndx : itemz.keySet())
         {
        str.append("<option value=");
        str.append(quarterIndx);
        str.append(">");
        str.append(itemz.get(quarterIndx));
        str.append("</option>");
         }
        str.append("</optgroup>");
        }
if (typeId.compareTo(POSTargetInterface.CONSTANT_MONTH_TYPE)==0)
       {
        itemz = calUtil.getMonths();
        str.append("<optgroup label=\"Months\">");
        for (int monthIndx : itemz.keySet())
         {
        str.append("<option value=");
        str.append(monthIndx );
        str.append(">");
        str.append(itemz.get(monthIndx));
        str.append("</option>");
         }
        str.append("</optgroup>");
        }
if (typeId.compareTo(POSTargetInterface.CONSTANT_WEEK_TYPE)==0)
       {        
       Vector<WeekDays> days = null;   
        SortedMap<Integer, Integer> oneYearHM = calUtil.getYears(yearId);          
       weekItemzPerYear = calUtil.getYearMonthsRanges(calUtil.getYears(yearId));
       itemz = calUtil.getMonths();
       int currentMonIndx = 0;
       boolean startNewMonth = false;
       
       str.append("<optgroup label=\""+oneYearHM.get(1) +" Weeks\">");
        for (int weekIndx : weekItemzPerYear.keySet())
         {
        days = weekItemzPerYear.get(weekIndx)    ;
        
        for (WeekDays day :days)
        {
            if (currentMonIndx != day.getMonth()){startNewMonth = true;}
            if(startNewMonth){
            str.append("<optgroup label=\""+itemz.get(day.getMonth()) +"\">");           
            }
            
            str.append("<option value=");
            str.append(day.getWeek() );
            str.append("_");
            str.append(day.getMonth());
            str.append(">Week ");
            str.append(day.getWeek());
            str.append(" From ");
            str.append(day.getStart());
            str.append("/");
            str.append(day.getMonth());
            str.append(" To ");
            str.append(day.getEnd());
            str.append("/");
            str.append(day.getMonth());
            str.append("</option>");
        
            
            
            if (currentMonIndx != day.getMonth()){startNewMonth = false;}
            if(startNewMonth){
            str.append("</optgroup>");
                       }
            currentMonIndx = day.getMonth();
            
            
        }
        
         }
        str.append("</optgroup>");
       
        }
}
str.append("</select>");
System.out.println("data is "+str);
out.println(str.toString());



%>

