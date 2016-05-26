<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="com.mobinil.sds.core.utilities.Utility"
         import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="java.sql.*"
         import="javax.servlet.jsp.*"
        import="com.mobinil.sds.web.interfaces.*"
        import = " com.mobinil.sds.core.system.*"
         
        
       
       
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 
<%@page import="com.mobinil.sds.web.interfaces.ar.AuthResInterfaceKey"%>
<%@page import="com.mobinil.sds.core.system.authenticationResult.model.AuthResStatisticsModel"%><html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Upload Statistics</title>
</head>
 <body>

    <CENTER>
      <H2>Upload Statistics</H2>
    </CENTER>
  <form  name='Authform' id='Authform' action='' method='post'>  

  <%

     HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
	 Vector vecStatistics = (Vector)objDataHashMap.get(AuthResInterfaceKey.VECTOR_STATISTICS);

	 String strUserID     =   (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

	
     out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                " value=\""+"\">");   

     out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                " value=\""+strUserID+"\">"); 


            
%>  
     

     <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1> 
     
  <%
  
  if(vecStatistics.size()!=0)

    %>
    <tr class=TableHeader>
      <td>Number Of Read Lines</td>
      <td>Number Of Inserted Lines</td>
     <td>Start Time</td>
     <td>End Time</td>
     <td>Total Time of Processing</td>   
     </tr> 
     
     <tr> 
         <%    
    
     for (int i=0;i<vecStatistics.size();i++){
    	AuthResStatisticsModel model = (AuthResStatisticsModel) vecStatistics.get(i);
 	     String readLines = model.getNO_OF_READ_LINES();
 	    String insertedLInes = model.getNO_OF_INSERTED_LINES();
 	   Timestamp startTime = model.getStartTimesStamp();
 	  // System.out.println("start date issssssss "+ startTime);
 	  Timestamp endTime = model.getEndTimesStamp();
 	 //System.out.println("end date issssssss "+ endTime);
 	 long diff = Math.abs(startTime.getTime()- endTime.getTime());
 	 float mins = (float)diff/1000/60;
 	 mins = mins*10;
 	 float round = Math.round(mins);
 	 round = round/10;
 	//System.out.println("long diff  issssssss "+ Math.round(mins));
 	 String file_id = model.getFILE_ID();
        //System.out.println("file id isssssssssss"+file_id); %>
      <td > <%=readLines %></td>  
      <td > <%=insertedLInes %></td>  
   <td > <%=startTime %></td>
   <td > <%=endTime %></td>
   <td > <%=round %> m</td>
   <td > </td>
  
    <%
   
     }
   %>
       </tr>

     </TABLE>
     

</form>
 </body>
 </html>
