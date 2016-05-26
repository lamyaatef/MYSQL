<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="com.mobinil.sds.core.utilities.Utility"
         import ="javax.servlet.*" 
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
         import="com.mobinil.sds.core.system.sop.franchise.model.*"
%>
<html>
    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
      <SCRIPT language=JavaScript src="../resources/js/calendar.js" type=text/javascript></SCRIPT>   
     <script type="text/javascript" src="../resources/js/jquery-latest.js"></script>
      <script type="text/javascript" src="../resources/js/jquery.tablesorter.pack.js"></script>
      <link rel="stylesheet" href="../resources/css/themes/blue/style.css" type="text/css"/>
    </head>
  <body>
<script>
        function drawCalender(argOrder,argValue)
        {
            document.write("<INPUT value="+argValue+" readonly class=input name=\""+argOrder+"\">&nbsp;<A onclick=\"showCalendar(SOPform."+argOrder+",'dd/mm/yyyy','Choose date')\">");
            document.write("<IMG height=16 src=\"../resources/img/icon_calendar.gif\" width=20 border=0></A>");
        }

        function setSearchValues(franchiseCode,itemCode,pgwStartDate,pgwEndDate,lcsStartDate,lcsEndDate)
        {
          document.getElementById("<%=SOPInterfaceKey.INPUT_TEXT_FRANCHISE_CODE%>").value = franchiseCode;
          document.getElementById("<%=SOPInterfaceKey.INPUT_TEXT_ITEM_CODE%>").value = itemCode;
          document.getElementById("<%=SOPInterfaceKey.INPUT_TEXT_PGW_START_DATE%>").value = pgwStartDate;
          document.getElementById("<%=SOPInterfaceKey.INPUT_TEXT_PGW_END_DATE%>").value = pgwEndDate;
          document.getElementById("<%=SOPInterfaceKey.INPUT_TEXT_LCS_START_DATE%>").value = lcsStartDate;
          document.getElementById("<%=SOPInterfaceKey.INPUT_TEXT_LCS_END_DATE%>").value = lcsEndDate;
        }
        function Toggle(item) 
        {
          obj=document.getElementById(item);
          if (obj!=null) 
          {
            visible=(obj.style.display!="none")
            key=document.getElementById("x"+item);
            if (visible) 
            {
              obj.style.display="none";
              key.innerHTML="<img src='../resources/img/plus.gif' border='0'>";
            } 
            else 
            {
              obj.style.display="block";
              key.innerHTML="<img src='../resources/img/minus.gif' border='0'>";
            }
          }
        }
</script>  
<script type="text/javascript">
 $(document).ready(function(){$("#FranchiseState").tablesorter({headers: {6:{sorter:false}},widgets: ['zebra']});});
</script>

    <CENTER>
      <H2> Franchise Details </H2>
    </CENTER>
<form name='SOPform' id='SOPform' action='' method='post'>

<%
HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
 if(objDataHashMap == null)
    objDataHashMap = new HashMap();
String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
int rowNum = Integer.parseInt((String)objDataHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_ROW_NUM));
int count = Integer.parseInt((String)objDataHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_COUNT));
Utility.logger.debug("The Row Num issssssssssssssssssss " + rowNum);
Utility.logger.debug("The count isssssssssssssssssssssss " + count );

  String searchFranchiseCode = "";
  String searchItemcode = "";
  String searchPGWStartDate = "*";
  String searchPGWEndDate = "*";
  String searchLCSStartDate = "*";
  String searchLCSEndDate = "*";

if(objDataHashMap.containsKey(SOPInterfaceKey.INPUT_TEXT_FRANCHISE_CODE)){searchFranchiseCode = (String)objDataHashMap.get(SOPInterfaceKey.INPUT_TEXT_FRANCHISE_CODE);}
if(objDataHashMap.containsKey(SOPInterfaceKey.INPUT_TEXT_ITEM_CODE)){searchItemcode = (String)objDataHashMap.get(SOPInterfaceKey.INPUT_TEXT_ITEM_CODE);}
if(objDataHashMap.containsKey(SOPInterfaceKey.INPUT_TEXT_PGW_START_DATE)){searchPGWStartDate = (String)objDataHashMap.get(SOPInterfaceKey.INPUT_TEXT_PGW_START_DATE);}
if(objDataHashMap.containsKey(SOPInterfaceKey.INPUT_TEXT_PGW_END_DATE)){searchPGWEndDate = (String)objDataHashMap.get(SOPInterfaceKey.INPUT_TEXT_PGW_END_DATE);}
if(objDataHashMap.containsKey(SOPInterfaceKey.INPUT_TEXT_LCS_START_DATE)){searchLCSStartDate = (String)objDataHashMap.get(SOPInterfaceKey.INPUT_TEXT_LCS_START_DATE);}
if(objDataHashMap.containsKey(SOPInterfaceKey.INPUT_TEXT_LCS_END_DATE)){searchLCSEndDate = (String)objDataHashMap.get(SOPInterfaceKey.INPUT_TEXT_LCS_END_DATE);}

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                    " value=\""+"\">");

    out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">"); 

    out.println("<input type=\"hidden\" name=\""+SOPInterfaceKey.INPUT_HIDDEN_ROW_NUM+"\""+
                  " value=\""+rowNum+"\">");

    out.println("<input type=\"hidden\" name=\""+SOPInterfaceKey.INPUT_HIDDEN_COUNT+"\""+
                  " value=\""+count+"\">");

  Vector franchiseData = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);

%>
  
      <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
      <tr class=TableHeader>
        <td align='left' colspan=6>Search</td>
      </tr>
      <TR class=TableTextNote>
        <td>Franchise Code</td>
        <td align=middle><input type='text' name='<%=SOPInterfaceKey.INPUT_TEXT_FRANCHISE_CODE%>' value="" ></td>
        <td>Item Code</td>
        <td align=middle><input type='text' name='<%=SOPInterfaceKey.INPUT_TEXT_ITEM_CODE%>' value=""></td>
        
      </TR>

      <TR class=TableTextNote>
        <td>PGW Date From</td>
       <td align=middle><Script>drawCalender('<%=SOPInterfaceKey.INPUT_TEXT_PGW_START_DATE%>','<%=searchPGWStartDate%>',"*");</script></td>
        <td>To</td>
        <td align=middle><Script>drawCalender('<%=SOPInterfaceKey.INPUT_TEXT_PGW_END_DATE%>','<%=searchPGWEndDate%>',"*");</script></td>
        
      </TR>
      
      <TR class=TableTextNote>
        <td>LCS Date From</td>
       <td align=middle><Script>drawCalender('<%=SOPInterfaceKey.INPUT_TEXT_LCS_START_DATE%>','<%=searchLCSStartDate%>',"*");</script></td>
        <td>To</td>
        <td align=middle><Script>drawCalender('<%=SOPInterfaceKey.INPUT_TEXT_LCS_END_DATE%>','<%=searchLCSEndDate%>',"*");</script></td>
        
      </TR>
      <tr>
        <td align='center' colspan=6>
        <%
        out.println("<INPUT class=button type=submit value=\" Search \" name=\"searchrequest\" id=\"searchrequest\" onclick=\"document.SOPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SOPInterfaceKey.ACTION_SEARCH_FRANCHISE_DETAILS+"';"+
                  "\">");

        out.println("<INPUT class=button type=button value=\" Clear Values \" name=\"clear\" id=\"clear\" onclick=\"setSearchValues('','','*','*','*','*');\">");          
        %>
        </td>
      </tr>
    </table>
    <br><br>
    <%
    Utility.logger.debug("The size isssssssssssssss "+franchiseData.size());
      if(franchiseData.size()!=0)
      {
      %>
       <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
        <TR class=TableHeader>
        <td>Franchise Code</td>
          <td>Item Code</td>
          <td>LCS Item Code</td>
          <td>LCS Quantity</td>
          <td>PGW Quantity</td>
          </tr>

          <%
        for(int i=0;i<franchiseData.size();i++)
        {
        FranchiseDetailsModel franchiseDetailsModel = (FranchiseDetailsModel)franchiseData.get(i);
        String franchCode = franchiseDetailsModel.getFranchiseCode();
        String itCode = franchiseDetailsModel.getItemCode();
        String lcsItemCode = franchiseDetailsModel.getLcsItemCode();
        if(lcsItemCode == null)lcsItemCode = "";
        //String lcsItemDesc = franchiseDetailsModel.getLcsItemDesc();
        String pgwQuantity = franchiseDetailsModel.getPgwQuantity();
        String lcsQuantity = franchiseDetailsModel.getLcsQuantity();
%>

<tr class=<%=InterfaceKey.STYLE[i%2]%>>
<td><%=franchCode%></td>
<td><%=itCode%></td>
<td><%=lcsItemCode%></td>
<td><%=lcsQuantity%></td>
<td><%=pgwQuantity%></td>
</tr>
<%
}
%>
</table>
<br><br>
 <table width=95% border=0 cellspacing=0 cellpadding=0>
 <tr>
 <td align = 'right'>
<%
int totalRows = (rowNum+1)*49;
Utility.logger.debug("The total rows issssssssssss " + totalRows);
  

  if(rowNum>=1)
  {

     out.println("<td align = 'right'><INPUT id=button4 class=button type=button value=\"<<\" name=button4 onclick=\"document.SOPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SOPInterfaceKey.ACTION_PREVIOUS_FRANCHISE_DETAILS+"';"+"document.SOPform.submit();\">");
  }
  else
  {
    out.println("<INPUT id=button4 class=button type=button value=\"<<\" name=button4 disabled>");
  }

  if(franchiseData.size()>=49&&totalRows!=count)
  {

    out.println("<INPUT id=button3 class=button type=button value=\">>\" name=button3 onclick=\"document.SOPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SOPInterfaceKey.ACTION_NEXT_FRANCHISE_DETAILS+"';"+"document.SOPform.submit();\"></td>");
  }
  else
  {
    out.println("<INPUT id=button3 class=button type=button value=\">>\" name=button3 disabled></td>");
  }
  
    %> 

    </td>
    <tr>
   </table>
<%
}
 
    %>

 <%
if(objDataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_ACTION))
  {
    String strAction = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ACTION);
    if(strAction.compareTo(SOPInterfaceKey.ACTION_SEARCH_FRANCHISE_DETAILS)==0)
    {
      out.println("<script>setSearchValues('"+searchFranchiseCode+"','"+searchItemcode+"','"+searchPGWStartDate+"','"+searchPGWEndDate+"','"+searchLCSStartDate+"','"+searchLCSEndDate+"');</script>");
    }
  }
%>
</body>
</html>