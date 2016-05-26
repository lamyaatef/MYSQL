<%@ page contentType="Application/vnd.excel;charset=windows-1256"%>

<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.sop.*"

         import="com.mobinil.sds.core.system.sop.requests.model.*"

         import="com.mobinil.sds.core.system.gn.dcm.dto.*"
         import="com.mobinil.sds.core.system.gn.dcm.model.*"
%>
<html>
 <head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
      <SCRIPT language=JavaScript src="../resources/js/calendar.js" type=text/javascript></SCRIPT>     
    </head>
  <body>


  <%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

  String strChannelId = (String)objDataHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
  
  String reportId = (String)objDataHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_REPORT_ID);

  HashMap totalReport = (HashMap)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);

   response.addHeader("Content-Disposition", "attachment; filename=report.xls");
%>

<CENTER>
<H2>Total Report </H2>
</CENTER>
<form name='SOPform' id='SOPform' action='' method='post'>


<%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">"); 

  out.println("<input type=\"hidden\" name=\""+SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID+"\""+
                  " value=\""+strChannelId+"\">"); 
  
  out.println("<input type=\"hidden\" name=\""+SOPInterfaceKey.INPUT_HIDDEN_REPORT_ID+"\""+
          " value=\""+reportId+"\">");
%>
      <%
    if(totalReport.size()!=0)
    {
    %>
<TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
  <tr>
    <th scope="col" class=TableHeader>DIST</th>
<%
 Set set = totalReport.entrySet();
             Iterator i = set.iterator();
            while(i.hasNext())
            {
            Map.Entry me = (Map.Entry)i.next();
            HashMap allProducts =(HashMap)me.getValue();
            Map mapKeys = Collections.synchronizedMap(new TreeMap(allProducts));
            String dcmID=(String)me.getKey();
            Set set2 = mapKeys.entrySet();
             Iterator i2 = set2.iterator();
             while(i2.hasNext())
            {
             Map.Entry me2 = (Map.Entry)i2.next();
              

            System.out.println("Product name after " + me2.getKey());
            out.println("<th scope=\"col\" class=TableHeader>"+me2.getKey()+"</th>");
            
          
              //out.println("<th scope=\"col\" class=TableHeader>"+me2.getKey()+"</th>");
            //System.out.println("Product Code: "+me2.getKey()+" ProductValue: "+me2.getValue());
            }
            break;
            }                
%>
  </tr>
 
  <%

  Map mapdcms = Collections.synchronizedMap(new TreeMap(totalReport));

   Set set3 = mapdcms.entrySet();
  Iterator i3 = set3.iterator();
  while(i3.hasNext())
  {
            Map.Entry me3 = (Map.Entry)i3.next();
            HashMap allProducts2 =(HashMap)me3.getValue();
            Map mapProducts = Collections.synchronizedMap(new TreeMap(allProducts2));
            String dcmID3=(String)me3.getKey();
            
            out.println("<tr>");
            out.println("<th scope=\"row\">"+dcmID3+"</th>");
            Set set4 = mapProducts.entrySet();
             Iterator i4 = set4.iterator();
             while(i4.hasNext())
            {
             Map.Entry me4 = (Map.Entry)i4.next();
            out.println("<td>"+me4.getValue()+"</td>");
            //System.out.println("Product Code: "+me4.getKey()+" ProductValue: "+me4.getValue());
           
            }
            out.println("</tr>");
            
            }  
            
%>
</table>

<br><br>
    <center>
    <input type=button class=button value="Export to Excel" 
        onclick="document.getElementById('<%=InterfaceKey.HASHMAP_KEY_ACTION%>').value='<%=
        SOPInterfaceKey.ACTION_EXPORT_SCRATCH_REPORT%>'; document.SOPform.submit();">
        </center>

<%
  }%>
    
    

   

</body>
</html>
