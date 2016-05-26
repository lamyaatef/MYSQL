<%@ page contentType="text/html;charset=windows-1252"%>
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
<script>
 function drawCalender(argOrder,argValue)
        {
            document.write("<INPUT value="+argValue+" class=input readOnly name=\""+argOrder+"\">&nbsp;<A onclick=\"showCalendar(SOPform."+argOrder+",'mm/dd/yyyy','Choose date')\">");
            document.write("<IMG height=16 src=\"../resources/img/icon_calendar.gif\" width=20 border=0></A>");
        }
 function setSearchValues(creationDateFrom,creationDateTo,paymentDateFrom,paymentDateTo,warehouseId)
        {
          document.getElementById("<%=SOPInterfaceKey.INPUT_SEARCH_TEXT_CREATION_DATE_FROM%>").value = creationDateFrom;
          document.getElementById("<%=SOPInterfaceKey.INPUT_SEARCH_TEXT_CREATION_DATE_TO%>").value = creationDateTo;
          document.getElementById("<%=SOPInterfaceKey.INPUT_SEARCH_TEXT_PAYMENT_DATE_FROM%>").value = paymentDateFrom;
          document.getElementById("<%=SOPInterfaceKey.INPUT_SEARCH_TEXT_PAYMENT_DATE_TO%>").value = paymentDateTo;
          document.getElementById("<%=SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID%>").value = warehouseId;
        }
        
</script>

  <%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

  String strChannelId = (String)objDataHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
  
  String reportId = (String)objDataHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_REPORT_ID);

  HashMap totalReport = (HashMap)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
  
  Vector warehouseVec = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);

  String searchCreationDateFrom = "*";
  String searchCreationDateTo = "*";
  String searchPaymentDateFrom = "*";
  String searchPaymentDateTo = "*";
  String searchWarehouseId = "";
  
  if(objDataHashMap.containsKey(SOPInterfaceKey.INPUT_SEARCH_TEXT_CREATION_DATE_FROM))
  {
    searchCreationDateFrom = (String)objDataHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_CREATION_DATE_FROM);
  }
  if(objDataHashMap.containsKey(SOPInterfaceKey.INPUT_SEARCH_TEXT_CREATION_DATE_TO))
  {
    searchCreationDateTo= (String)objDataHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_CREATION_DATE_TO);
  }
  if(objDataHashMap.containsKey(SOPInterfaceKey.INPUT_SEARCH_TEXT_PAYMENT_DATE_FROM))
  {
    searchPaymentDateFrom = (String)objDataHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_PAYMENT_DATE_FROM);
  }
  if(objDataHashMap.containsKey(SOPInterfaceKey.INPUT_SEARCH_TEXT_PAYMENT_DATE_TO))
  {
    searchPaymentDateTo = (String)objDataHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_PAYMENT_DATE_TO);
  }
  if(objDataHashMap.containsKey(SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID))
  {
	  searchWarehouseId = (String)objDataHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID);
  }
%>

<CENTER>
<H2> Total Report </H2>
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
   out.println("<input type=\"hidden\" name=\""+SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID+"\" id=\""+SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID+"\""+
          " value=\"\">");
%>


<TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
      <tr class=TableHeader>
        <td align='left' colspan=6>Search</td>
      </tr>

      
      <tr class=TableTextNote>  
        <td align=middle>Creation Date</td>
        <td align=middle>From</td>
        <td align=middle><Script>drawCalender('<%=SOPInterfaceKey.INPUT_SEARCH_TEXT_CREATION_DATE_FROM%>','<%=searchCreationDateFrom%>',"*");</script></td>
        <td align=middle>To</td>
        <td align=middle colspan=2><Script>drawCalender('<%=SOPInterfaceKey.INPUT_SEARCH_TEXT_CREATION_DATE_TO%>','<%=searchCreationDateTo%>',"*");</script></td>
      </tr>

      
      <tr class=TableTextNote>  
        <td align=middle>Payment Date</td>
        <td align=middle>From</td>
        <td align=middle><Script>drawCalender('<%=SOPInterfaceKey.INPUT_SEARCH_TEXT_PAYMENT_DATE_FROM%>','<%=searchPaymentDateFrom%>',"*");</script></td>
        <td align=middle>To</td>
        <td align=middle colspan=2><Script>drawCalender('<%=SOPInterfaceKey.INPUT_SEARCH_TEXT_PAYMENT_DATE_TO%>','<%=searchPaymentDateTo%>',"*");</script></td>
      </tr>
<%--      <tr class=TableTextNote>
      <td align=middle></td>
      <td align=middle>Warehouse Name</TD>
          <TD >
            <select name="<%=SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID%>" id="<%=SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID%>">
            <option value=""></option>
              <%
              for(int m=0;m<warehouseVec.size();m++)
              {
            	  WarehouseModel warehouseModel = (WarehouseModel)warehouseVec.get(m);
                  String strWarehouseIdX = warehouseModel.getWarehouseId();
                  String strWarehouseNameX = warehouseModel.getWarehouseName();
                  String warehouseSelected = "";
                  if(strWarehouseIdX.compareTo(searchWarehouseId)==0)
                  {
                	  warehouseSelected = "selected";
                  }
                	  %>
                      <option value='<%=strWarehouseIdX%>' <%=warehouseSelected%>><%=strWarehouseNameX%></option>
                      <%
                  
                }
              
              %>
            </select>
            
          </td>
      <td align=middle></td>
      <td align=middle></td>
      </tr>
      <tr>--%>
        <td align='center' colspan=6>
        <%
        out.print("<INPUT class=button type=button value=\" Search \" name=\"searchScratch\" id=\"searchScratch\" onclick=\"document.SOPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SOPInterfaceKey.ACTION_SEARCH_TOTAL_REPORT+"';"+
                  "SOPform.submit();\">");

        out.print("<INPUT class=button type=button value=\" Clear Values \" name=\"clear\" id=\"clear\" onclick=\"setSearchValues('*','*','*','*');\">");          
        %>
        </td>
      </tr>
      <table>

      <br><br>
      
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
        SOPInterfaceKey.ACTION_EXPORT_TOTAL_REPORT%>'; document.SOPform.submit();">
        </center>
            <%
  }%>
    
    


      <%
if(objDataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_ACTION))
  {
    String strAction = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ACTION);
    if(strAction.compareTo(SOPInterfaceKey.ACTION_SEARCH_TOTAL_REPORT)==0)
    {
      out.println("<script>setSearchValues('"+searchCreationDateFrom+"','"+searchCreationDateTo+"','"+searchPaymentDateFrom+"','"+searchPaymentDateTo+"','"+searchWarehouseId+"');</script>");
    }
  }
%>

</body>
</html>