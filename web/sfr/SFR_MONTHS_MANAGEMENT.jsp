
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.sfr.*"
         import ="com.mobinil.sds.core.system.sfr.sheets.model.*"  
%>
<html>
        <%
        HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
        String appName = request.getContextPath();
        Vector sheetDetails = (Vector)dataHashMap.get(SFRInterfaceKey.SFR_HASHMAP_KEY_COLLECTION); 
        String msg = (String)dataHashMap.get(SFRInterfaceKey.SFR_HASHMAP_KEY_ADDITIONAL_COLLECTION); 
        
        %>
        <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Months Management</title>
        
          <LINK REL=STYLESHEET TYPE="text/css" HREF="<%out.print(appName);%>/resources/css/Template1.css">

    </head>
    
    
<SCRIPT language="javascript">

 function addYear()
 {
	document.SFRform.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = '<%=SFRInterfaceKey.ACTION_ADD_YEAR_SFR%>';
	SFRform.submit();
 }


 function closeAndOpenMonth(year , month ,  status , statusId)
 {
    document.getElementById("<%=SFRInterfaceKey.INPUT_HIDDEN_YEAR%>").value = year;
    document.getElementById("<%=SFRInterfaceKey.INPUT_HIDDEN_MONTH%>").value = month;
    document.getElementById("<%=SFRInterfaceKey.INPUT_HIDDEN_STATUS%>").value = status;
    document.getElementById("<%=SFRInterfaceKey.INPUT_HIDDEN_STATUS_ID%>").value = statusId;
    
	document.SFRform.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = '<%=SFRInterfaceKey.ACTION_CLOSE_MONTH_SFR%>';
	SFRform.submit();
 }
 
</SCRIPT>

  
    <body>
  
                  
       <form name='SFRform' id='SFRform' action='' method='post'>
  
  
  <%
    String strUserID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
    out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+" value=\""+"\">");
  %>
   <input type=hidden name=<%=InterfaceKey.HASHMAP_KEY_USER_ID%> id=<%=InterfaceKey.HASHMAP_KEY_USER_ID%>  value=<%=strUserID%>>
   <input type=hidden name=<%= SFRInterfaceKey.INPUT_HIDDEN_YEAR%> id=<%= SFRInterfaceKey.INPUT_HIDDEN_YEAR%>>
   <input type=hidden name=<%=SFRInterfaceKey.INPUT_HIDDEN_MONTH%> id=<%=SFRInterfaceKey.INPUT_HIDDEN_MONTH%> >
   <input type=hidden name=<%=SFRInterfaceKey.INPUT_HIDDEN_STATUS%> id=<%=SFRInterfaceKey.INPUT_HIDDEN_STATUS%>>
   <input type=hidden name=<%=SFRInterfaceKey.INPUT_HIDDEN_STATUS_ID%> id=<%=SFRInterfaceKey.INPUT_HIDDEN_STATUS_ID%>>
        
            <table style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=0>
                <tr class=TableHeader>
                 <td colspan=2>
                 <center><font size="4" ><b>Months Management</b></font></center> <br></td><td></td></tr>
                 <br>

                <tr class="TableTextNode">

                    <td colspan=2>
                    <center>
                        <div id="divText"><b>Year : </b>
                        &nbsp;&nbsp;
                        <select name="<%= SFRInterfaceKey.INPUT_YEAR %>" id="<%= SFRInterfaceKey.INPUT_YEAR %>">
                          <%
                              for(int i = 2005 ; i < 2026 ; i++)
                              {
                            	%>
                            	<option><%= i %></option>
                            	<%   
                              }
                          %>
                        </select>
                        </div>
                        </center>
                        
                    </td>
                    

                </tr>
            </table>
            <br>
             <center> <input type=button class=button  value=Save onclick="addYear();">  
          
           </center>
            <br>
               <center>    <%=  msg %>  </center> 
            </br>
            
             <%
 
                for(int i = 0 ; i< sheetDetails.size() ; i++)
                {
                	MonthsModel monthsMainObj = (MonthsModel)sheetDetails.get(i);
                	
                  %>
                  <center>
                   <table style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="60%" border=1>
                      <tr>
                       <td colspan=3>
                       <%= monthsMainObj.getYear() %>
                      </td>
                     </tr>
                     <tr>
                       <td ></td>
                       <td >Sales From Registration</td>
                       
                     </tr>
	               <%
                     for(int y = 0;  y < 12  ;  y++ )
                     {
                    	MonthsModel monthsObj = (MonthsModel)sheetDetails.get(i);	
                     %>
	                    <tr>
		                   <td>
		                    <%
		                      int month = monthsObj.getMonth();
		                      switch (month)
		                      {
		                      case 1:
		                    	 out.println(" Jan.");
		                    	  break;
		                      case 2:
		                    	  out.println(" Feb.");
		                    	  break;  
		                      case 3:
		                    	  out.println(" Mar.");
		                    	  break;
		                      case 4:
		                    	  out.println(" Apr.");
		                    	  break;
		                      case 5:
		                    	  out.println(" May.");
		                    	  break;
		                      case 6:
		                    	  out.println(" Jun.");
		                    	  break;
		                      case 7:
		                    	  out.println(" Jul.");
		                    	  break;
		                      case 8:
		                    	  out.println(" Aug.");
		                    	  break;
		                      case 9:
		                    	  out.println(" Sep.");
		                    	  break;
		                      case 10:
		                    	  out.println(" Oct.");
		                    	  break;
		                      case 11:
		                    	  out.println(" Nov.");
		                    	  break;
		                      case 12:
		                    	  out.println(" Dec.");
		                    	  break;
		                      }
		                    	  
		                    %>
		                   </td>
		                   <% 
		                   if(monthsObj.getStatusOne() == 1&& monthsObj.getStatusTwo() == 1) 
		                   {
		                   %>
		                   <td><input type="button" class="button"  value="Re-open" disabled="disabled"/></td>
		                   
		                   <%
		                   }
		                   else if(monthsObj.getStatusOne() == 1 && monthsObj.getStatusTwo() == 0) 
		                   {
		                   %>
		                   <td><input type="button" class="button"  value="Re-open" onclick="closeAndOpenMonth(<%= monthsObj.getYear() %> , <%= monthsObj.getMonth() %> , 0  , 1);" /></td>
		              
		                   <%
		                   }
		                   else if(monthsObj.getStatusOne() == 0 && monthsObj.getStatusTwo() == 0) 
		                   {
		                   %>
		                   <td><input type="button" class="button"  value="Close" onclick="closeAndOpenMonth(<%= monthsObj.getYear() %> , <%= monthsObj.getMonth() %> , 1 , 1);"/></td>
		                  
		                   <%
		                   }
		                   else if(monthsObj.getStatusOne() == 0 && monthsObj.getStatusTwo() == 1) 
		                   {
		                   %>
		                   <td><input type="button" class="button"  value="Close" onclick="closeAndOpenMonth(<%= monthsObj.getYear() %> , <%= monthsObj.getMonth() %> , 1 , 1);"/></td>
		                   
		                   <%
		                   }
		                   %>
	                     
	                     </tr>
	                     
	                 <%	 
	                 if( (y+1) != 12 )
	                       i++ ;
                     	
                     }
	               
	               %>
	               </table>
	               <br>
	            
	               <% 
	               
                }
             %>


          
           
        </form>
    </body>




</html>
