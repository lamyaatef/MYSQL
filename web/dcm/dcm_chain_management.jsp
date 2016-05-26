<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="com.mobinil.sds.core.utilities.Utility"
         import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.dcm.*"

         import="com.mobinil.sds.core.system.dcm.chain.model.*"
         import="com.mobinil.sds.core.system.dcm.chain.model.chainCityModel"
         
%>
<html>
    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
      <SCRIPT language=JavaScript src="../resources/js/calendar.js" type=text/javascript></SCRIPT>
    </head>
  <body>
<script>
function setSearchValues(chainCode,chainLevel,chainPaymentLevel,chainChannel,chainCity,chainDistrict,chainStatus,chainStkNumber)
{
  document.getElementById("<%=DCMInterfaceKey.INPUT_SEARCH_TEXT_DCM_CODE%>").value = chainCode;
  document.getElementById("<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_LEVEL%>").value = chainLevel;
  document.getElementById("<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_PAYMENT_LEVEL%>").value = chainPaymentLevel;
  document.getElementById("<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_CHANNEL%>").value = chainChannel;
  document.getElementById("<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_CITY%>").value = chainCity;
  document.getElementById("<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_DISTRICT%>").value = chainDistrict;
  document.getElementById("<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_STATUS%>").value = chainStatus;
  document.getElementById("<%=DCMInterfaceKey.INPUT_SEARCH_TEXT_STK_NUMBER%>").value = chainStkNumber;
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
  <%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String searchChainCode = "";
  String searchChainLevel = "";
  String searchChainPaymentLevel = "";
  String searchChainChannel = "";
  String searchChainCity = "";
  String searchChainDistrict = "";
  String searchChainStatus = "";
  String searchChainStkNumber = "";
  Vector chainListVec = new Vector();

  if(objDataHashMap.containsKey(DCMInterfaceKey.INPUT_SEARCH_TEXT_DCM_CODE)){searchChainCode = (String)objDataHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_DCM_CODE);}
  if(objDataHashMap.containsKey(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_LEVEL)){searchChainLevel = (String)objDataHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_LEVEL);}
  Utility.logger.debug("The level isssssssssssssssssssssssssssssssssssssssssssss " + searchChainLevel);
  if(objDataHashMap.containsKey(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_PAYMENT_LEVEL)){searchChainPaymentLevel = (String)objDataHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_PAYMENT_LEVEL);}
  if(objDataHashMap.containsKey(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_CHANNEL)){searchChainChannel = (String)objDataHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_CHANNEL);}
  if(objDataHashMap.containsKey(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_CITY)){searchChainCity = (String)objDataHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_CITY);}
  if(objDataHashMap.containsKey(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_DISTRICT)){searchChainDistrict = (String)objDataHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_DISTRICT);}
  if(objDataHashMap.containsKey(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_STATUS)){searchChainStatus = (String)objDataHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_STATUS);}
  if(objDataHashMap.containsKey(DCMInterfaceKey.INPUT_SEARCH_TEXT_STK_NUMBER)){searchChainStkNumber = (String)objDataHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_STK_NUMBER);}

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

   Vector vecChainList = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
   Utility.logger.debug("The size issssssssssss " + vecChainList.size());
   //int startIndex = 0;
   //int endIndex = 0;
   //if((String)objDataHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_PAGE_START_INDEX)!=null)
   //{
     // startIndex = Integer.parseInt((String)objDataHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_PAGE_START_INDEX));
      //endIndex = Integer.parseInt((String)objDataHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_PAGE_END_INDEX));
   //}
   //if(endIndex==0)
   //endIndex = vecChainList.size();
   //if(vecChainList.size()<endIndex)
   //endIndex = vecChainList.size();
  

  //Utility.logger.debug("startIndex: "+startIndex)    ;
  //Utility.logger.debug("EndIndex: "+endIndex)    ;
   Vector vecChainLevelList = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
   Vector vecChainPaymentLevelList = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2);
   Vector vecChainChannelList = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_3);
   Vector vecChainCityList = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_4);
   Vector vecChainDistrictList = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_5);
   Vector vecChainStatusList = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_XXX);
    int count = Integer.parseInt((String)objDataHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_COUNT));
    Utility.logger.debug("The count isssssssssssssssss " + count);
   int rowNum = Integer.parseInt((String)objDataHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_ROW_NUM));
   Utility.logger.debug("The Row Num issssssssssss " + rowNum);
  %>

  <CENTER>
      <H2> Chain Management </H2>
    </CENTER>
<form name='DCMform' id='DCMform' action='' method='post'>
<%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

   out.println("<input type=\"hidden\" name=\""+DCMInterfaceKey.INPUT_HIDDEN_DCM_ID+"\""+
                  " value=\""+"\">"); 

   out.println("<input type=\"hidden\" name=\""+DCMInterfaceKey.INPUT_HIDDEN_ROW_NUM+"\""+
                  " value=\""+rowNum+"\">"); 

  out.println("<input type=\"hidden\" name=\""+DCMInterfaceKey.INPUT_HIDDEN_COUNT+"\""+
                  " value=\""+count+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">"); 
                  
   //out.println("<input type=\"hidden\" name=\""+DCMInterfaceKey.INPUT_HIDDEN_PAGE_START_INDEX+"\""+
                  //" value=\""+startIndex+"\">"); 

  //out.println("<input type=\"hidden\" name=\""+DCMInterfaceKey.INPUT_HIDDEN_PAGE_END_INDEX+"\""+
                 //" value=\""+endIndex+"\">"); 
                  
                  
%>

<TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
      <tr class=TableHeader>
        <td align='left' colspan=6>Search</td>
      </tr>
      <TR class=TableTextNote>
        <td align=middle>Chain Code</td>
        <td align=middle><input type='text' name='<%=DCMInterfaceKey.INPUT_SEARCH_TEXT_DCM_CODE%>' id='<%=DCMInterfaceKey.INPUT_SEARCH_TEXT_DCM_CODE%>' value ="<%=searchChainCode%>"></td>

        <td align=middle>STK Number</td>
        <td align=middle><input type='text' name='<%=DCMInterfaceKey.INPUT_SEARCH_TEXT_STK_NUMBER%>' id='<%=DCMInterfaceKey.INPUT_SEARCH_TEXT_STK_NUMBER%>' value ="<%=searchChainStkNumber%>"></td>
        </tr>
         <TR class=TableTextNote>
        <TD align=middle>Chain Level</TD>
        <td align=middle>
          <select name='<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_LEVEL%>' id='<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_LEVEL%>' value ="<%=searchChainLevel%>">
          <option value=''></option>
          <%
          for (int j=0; j<vecChainLevelList.size(); j++)
          {
              chainLevelModel chainLevelModel = (chainLevelModel)vecChainLevelList.get(j); 
              String chainLevelId = chainLevelModel.getDcmLevelId();
              String chainLevelName = chainLevelModel.getDcmLevelName();
          %>
              <option value='<%=chainLevelId%>'><%=chainLevelName%></option>    
          <%
          }
          %>
          </select>
        </td>
        
        <TD align=middle>Chain Channel</TD>
        <td align=middle>
          <select name='<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_CHANNEL%>' id='<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_CHANNEL%>' value ="<%=searchChainChannel%>">
          <option value=''></option>
          <%
          for (int i=0; i<vecChainChannelList.size(); i++)
          {
              chainChannelModel chainChannelModel = (chainChannelModel)vecChainChannelList.get(i); 
              String chainChannelId = chainChannelModel.getChannelId();
              String chainChannelName = chainChannelModel.getChannelName();
          %>
              <option value='<%=chainChannelId%>'><%=chainChannelName%></option>    
          <%
          }
          %>
          </select>
        </td>
        </tr>
        
        <TR class=TableTextNote>
        <TD align=middle>Chain Payment Level</TD>
        <td align=middle>
          <select name='<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_PAYMENT_LEVEL%>' id='<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_PAYMENT_LEVEL%>' value ="<%=searchChainPaymentLevel%>">
          <option value=''></option>
          <%
          for (int k=0; k<vecChainPaymentLevelList.size(); k++)
          {
              chainPaymentLevelModel chainPaymentLevelModel = (chainPaymentLevelModel)vecChainPaymentLevelList.get(k); 
              String chainPaymentLevelId = chainPaymentLevelModel.getDcmPaymentLevelId();
              String chainPaymentLevelName = chainPaymentLevelModel.getDcmPaymentLevelName();
          %>
              <option value='<%=chainPaymentLevelId%>'><%=chainPaymentLevelName%></option>    
          <%
          }
          %>
          </select>
        </td>
        
        <TD align=middle>Chain City</TD>
        <td align=middle>
          <select name='<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_CITY%>' id='<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_CITY%>' value ="<%=searchChainCity%>">
          <option value=''></option>
          <%
          for (int l=0; l<vecChainCityList.size(); l++)
          {
              chainCityModel chainCityModel = (chainCityModel)vecChainCityList.get(l); 
              String chainCityId = chainCityModel.getCityId();
              String chainCityName = chainCityModel.getCityName();
          %>
              <option value='<%=chainCityId%>'><%=chainCityName%></option>    
          <%
          }
          %>
          </select>
        </td>
        </tr>
        <TR class=TableTextNote>
        <TD align=middle>Chain District</TD>
        <td align=middle>
          <select name='<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_DISTRICT%>' id='<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_DISTRICT%>' value ="<%=searchChainDistrict%>">
          <option value=''></option>
          <%
          for (int m=0; m<vecChainDistrictList.size(); m++)
          {
              chainDistrictModel chainDistrictModel = (chainDistrictModel)vecChainDistrictList.get(m); 
              String chainDistrictId = chainDistrictModel.getDistrictId();
              String chainDistrictName = chainDistrictModel.getDistrictName();
          %>
              <option value='<%=chainDistrictId%>'><%=chainDistrictName%></option>    
          <%
          }
          %>
          </select>
        </td>
        
        <TD align=middle>Chain Status</TD>
        <td align=middle>
          <select name='<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_STATUS%>' id='<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_STATUS%>' value ="<%=searchChainStatus%>">
          <option value=''></option>
          <%
          for (int n=0; n<vecChainStatusList.size(); n++)
          {
              chainStatusModel chainStatusModel = (chainStatusModel)vecChainStatusList.get(n); 
              String chainStatusId = chainStatusModel.getDcmStatusId();
              String chainCityName = chainStatusModel.getDcmStatusName();
          %>
              <option value='<%=chainStatusId%>'><%=chainCityName%></option>    
          <%
          }
          %>
          </select>
        </td>
        </tr>
        </table>
        <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
      <tr>
        <td align='center' colspan=5>
        <%
        out.print("<INPUT class=button type=button value=\" Search \" name=\"search\" id=\"search\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+DCMInterfaceKey.ACTION_SEARCH_GEN_DCM+"';"+
                  "DCMform.submit();\">");

        out.print("<INPUT class=button type=button value=\" Export \" name=\"export\" id=\"export\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+DCMInterfaceKey.ACTION_EXPORT_GEN_DCM_TO_EXCEL+"';"+
                  "DCMform.submit();\">");

        out.print("<INPUT class=button type=button value=\" Clear Values \" name=\"clear\" id=\"clear\" onclick=\"setSearchValues('','','','','','','','');\">");          
        %>
        </td>
      </tr>
      </table>
      <br>
      <br>
      
      
       <%
      if(vecChainList.size()!=0)
      {
          //List chainlistLST = vecChainList.subList(startIndex,endIndex);
          //startIndex = endIndex+1;
          //endIndex = endIndex+50;
          //chainListVec.addAll( 0,chainlistLST) ;
          //Utility.logger.debug("sizeeeee"+chainListVec.size());
      
      %>
      <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1> 
   <TR class=TableHeader>
        <td width="4%" align=middle>Chain Code</td>
         <td width="5%" align=middle>Chain Name</td>
         <td width="4%" align=middle>Chain Status</td>
         <td width="4%" align=middle>Chain Channel</td>
         <td width="4%" align=middle>Chain Level</td>
         <td width="4%" align=middle>Chain Payment Level</td>
         <td width="3%" align=middle>Chain City</td>
         <td width="5%" align=middle>Chain District</td>
         <td width="5%" align=middle>Chain Phone</td>
         <td width="5%" align=middle>STK Number</td>
         <td width="3%" align=middle>Edit</td>
         
         </tr>  
  <%
  
  ChainModel chainModel = null;
  String chainCode = "";
  String chainName = "";
  String chainStatus = "";
  String chainChannel = "";
  String chainLevel = "";
  String chainPaymentLevel = "";
  String chainCity = "";
  String chainId = "";
  String chainPhone = "";
  String chainEmail = "";
  String chainDistrict = "";
  String stkNumber = "";

   for (int z=0; z<vecChainList.size(); z++)
        {
        chainModel = (ChainModel)vecChainList.get(z);
        chainCode = chainModel.getDcmCode();
        if(chainCode == null)chainCode = "";
        chainName = chainModel.getDcmName();
        if(chainName == null)chainName = "";
        chainStatus = chainModel.getDcmStatusName();
        if(chainStatus == null)chainStatus = "";
        chainChannel = chainModel.getChannelName();
        if(chainChannel == null)chainChannel = "";
        chainLevel = chainModel.getDcmLevelName();
        if(chainLevel == null)chainLevel="";
        chainPaymentLevel = chainModel.getDcmPaymentLevelName();
        if(chainPaymentLevel == null)chainPaymentLevel = "";
        chainCity = chainModel.getDcmCityName();
        if(chainCity == null)chainCity = "";
        chainId = chainModel.getDcmId();
        chainDistrict = chainModel.getDcmDistrictName();
        if(chainDistrict == null)chainDistrict = "";
        chainPhone = chainModel.getDcmPhone();
        if(chainPhone == null)chainPhone = "";
        stkNumber = chainModel.getStkNumber();
        if(stkNumber == null) stkNumber ="";

  %>
<tr class="<%=InterfaceKey.STYLE[z%2]%>">
<td><%=chainCode%></td>
  <td><%=chainName%></td>
   <td><%=chainStatus%></td>
    <td><%=chainChannel%></td>
    <td><%=chainLevel%></td>
    <td><%=chainPaymentLevel%></td>
    <td><%=chainCity%></td>
    <td><%=chainDistrict%></td>
    <td><%=chainPhone%></td>
    <td><%=stkNumber%></td>
    
    <td align=middle>
            <%
              out.print("<INPUT class=button type='button' value=\" Edit \" name=\"edit\" id=\"edit\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+DCMInterfaceKey.ACTION_EDIT_GEN_DCM+"';"+
                  "document.DCMform."+DCMInterfaceKey.INPUT_HIDDEN_DCM_ID+".value = '"+chainId+"';"+
                  "DCMform.submit();\">");
          %>
           </td>
     </tr>
<%
chainModel = null;
 }
 %>
 </table>
 <br>
 <table width=95% border=0 cellspacing=0 cellpadding=0>
 <tr>
 <td align = 'right'>
<%
int totalRows = (rowNum+1)*49;
Utility.logger.debug("The total rows issssssssssss " + totalRows);
  
  if(rowNum>=1)
  {
    //out.println("<a href=\"#\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+DCMInterfaceKey.ACTION_PREVIOUS_GEN_DCM+"';"+"document.DCMform.submit();\">Previous</a></td>");

    out.println("<td align = 'right'><INPUT id=button4 class=button type=button value=\"<<\" name=button3 onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+DCMInterfaceKey.ACTION_PREVIOUS_GEN_DCM+"';"+"document.DCMform.submit();\">");
  }
  else
  {
    out.println("<INPUT id=button4 class=button type=button value=\"<<\" name=button3 disabled>");
  }

  if(vecChainList.size()>=49&& totalRows!=count)
  {
   //out.println("<td align = 'right'><a href=\"#\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+DCMInterfaceKey.ACTION_NEXT_GEN_DCM+"';"+"document.DCMform.submit();\">next</a>");

   out.println("<INPUT id=button3 class=button type=button value=\">>\" name=button3 onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+DCMInterfaceKey.ACTION_NEXT_GEN_DCM+"';"+"document.DCMform.submit();\"></td>");
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

                  <br>
    <center>
                  <%
        out.print("<INPUT class=button type=button value=\" Create New Chain \" name=\"new\" id=\"new\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+DCMInterfaceKey.ACTION_CREATE_NEW_GEN_DCM+"';"+
                  "DCMform.submit();\">");
                  %>
   </center>
   
 <%
if(objDataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_ACTION))
  {
    String strAction = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ACTION);
    if(strAction.compareTo(DCMInterfaceKey.ACTION_SEARCH_GEN_DCM)==0)
    {
      out.println("<script>setSearchValues('"+searchChainCode+"','"+searchChainLevel+"','"+searchChainPaymentLevel+"','"+searchChainChannel+"','"+searchChainCity+"','"+searchChainDistrict+"','"+searchChainStatus+"','"+searchChainStkNumber+"');</script>");
    }
  }
%>
</form>
</body>
</html>