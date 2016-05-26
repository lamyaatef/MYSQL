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

         import="com.mobinil.sds.core.system.sop.schemas.model.*"
         import="com.mobinil.sds.core.system.sop.requests.model.*"

         import="com.mobinil.sds.core.system.gn.dcm.dto.*"
         import="com.mobinil.sds.core.system.gn.dcm.model.*"
%>
<html>
    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
    </head>
  <body>
  <script>
  function checkBeforeSubmit()
  {
    var channelValue = document.SOPform.<%=SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID%>.value;
    if(channelValue == "")
    {
      alert("You must choose SCM.");
    }
    else
    {
      SOPform.submit();
    }
  }

  function removeAllOptions(selectname)
	{
	var elSel = document.getElementById(selectname);
	if (elSel.length == 0) 
		{
	  	return;
	  	}
	elSel.remove(0);
	removeAllOptions(selectname)  	
	}
	      
	function addOption(optvalue,opttext,selectname)
	{
		var elSel = document.getElementById(selectname);
		var elOptNew = new Option();
	
		elOptNew.text =  opttext;
		elOptNew.value =  optvalue;
		
		elSel.options[elSel.length] = elOptNew;		
	}
  </script>
  <%!
 String convertFromUTF8(String s) {
  String out = null;
  //Utility.logger.debug("S before = " + s);

  try {
    out = new String(s.getBytes("Cp1256"), "UTF-8");
    //Utility.logger.debug("out = " + out);
  } catch (java.io.UnsupportedEncodingException e) {
    return null;
  }
  return out;
}

%>
<%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
Utility.logger.debug(objDataHashMap);
  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
  //DCMDto dcmDto = (DCMDto)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION); 
  Vector channelVec = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
  Vector dcmChannelVec = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2);
  Vector warehouseChannelVec = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_3);
  //Utility.logger.debug(dcmDto);
  String strAction = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ACTION);
  String pageHeader = (String)objDataHashMap.get(SOPInterfaceKey.PAGE_HEADER);

  if(objDataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_MESSAGE))
  {
    String sysMsg = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_MESSAGE);
    out.println("<script>alert('"+sysMsg+"');</script>");
  }
  String channelSuperTypeIdValue = "";

  //String strChannelId = (String)objDataHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
  //Utility.logger.debug("Channel id issssssssssssssssss" + strChannelId);
%>  
    <CENTER>
      <H2> <%=pageHeader%> </H2>
    </CENTER>
<form name='SOPform' id='SOPform' action='' method='post'>
<%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");   

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");                  

  out.println("<input type=\"hidden\" name=\""+SOPInterfaceKey.INPUT_HIDDEN_DCM_NAME+"\""+
                  " value=\""+"\">");  

  //out.println("<input type=\"hidden\" name=\""+SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID+"\""+
    //              " value=\""+strChannelId+"\">");
       
%>
        <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="50%" border=1 align="center">
        
        
        
        <tr>
        
          <TD class=TableHeader>Channel</TD>
          <TD >
          <script>
            function changeSCMSelect(superTypeValue)
            {
              var caseTypeListName = eval("document.SOPform.<%=SOPInterfaceKey.INPUT_HIDDEN_DCM_ID%>.name");
              removeAllOptions(caseTypeListName);
              <%
              for(int l=0;l<dcmChannelVec.size();l++)
              {
                ChannelDCMModel channelDCMModel = (ChannelDCMModel)dcmChannelVec.get(l);
                String strDCMIdX = channelDCMModel.getDcmId();
                String strDCMNameX = channelDCMModel.getDcmName();
                String strChannelIdX = channelDCMModel.getChannelId();
                %>
                if(<%=strChannelIdX%> == superTypeValue)
                {

                  addOption('<%=strDCMIdX%>',"<%=convertFromUTF8(strDCMNameX)%>",caseTypeListName);
                }
                <%
              }
              %>

              var warehouseName = eval("document.SOPform.<%=SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID%>.name");
              removeAllOptions(warehouseName);
              <%
              for(int l=0;l<warehouseChannelVec.size();l++)
              {
                WarehouseChannelModel warehouseChannelModel = (WarehouseChannelModel)warehouseChannelVec.get(l);
                String strWarehouseIdX = warehouseChannelModel.getWarehouseId();
                String strWarehouseNameX = warehouseChannelModel.getWarehouseName();
                String strChannelIdX = warehouseChannelModel.getChannelId();
                %>
                if(<%=strChannelIdX%> == superTypeValue)
                {

                  addOption('<%=strWarehouseIdX%>',"<%=convertFromUTF8(strWarehouseNameX)%>",warehouseName);
                }
                <%
              }
              %>
              
            }
          </script>
            <select  name="<%=SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID%>" id="<%=SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID%>" onchange="changeSCMSelect(this.value);">
              <option value=""></option>
              <%
              for(int j=0;j<channelVec.size();j++)
              {
                chanelModel channelModel = (chanelModel)channelVec.get(j);
                String channelId = channelModel.getCHANNEL_ID();
                String channelName = channelModel.getCHANNEL_NAME();
                
                out.println("<option value='"+channelId+"'>"+channelName+"</option>");  
              }
              %>
            </select>
            
          </td>
        </tr>
        
        
        <TR >
          <TD class=TableHeader>SCM Name</TD>
          <TD >
            <select name="<%=SOPInterfaceKey.INPUT_HIDDEN_DCM_ID%>" id="<%=SOPInterfaceKey.INPUT_HIDDEN_DCM_ID%>">
            <option value=""></option>
              <%
              for(int k=0;k<dcmChannelVec.size();k++)
              {
                ChannelDCMModel channelDcmModel = (ChannelDCMModel)dcmChannelVec.get(k);
                String strDcmId = channelDcmModel.getDcmId();
                String strDcmName = channelDcmModel.getDcmName();
                if(channelSuperTypeIdValue.compareTo(strDcmId) == 0)
                {
                
                  out.println("<option value='"+strDcmId+"'>"+strDcmName+"</option>");  
                }
                }
              
              %>
            </select>
            
          </td>
        </tr>
        
        <TR >
          <TD class=TableHeader>Warehouse Name</TD>
          <TD >
            <select name="<%=SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID%>" id="<%=SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID%>">
            <option value=""></option>
              <%
              for(int m=0;m<warehouseChannelVec.size();m++)
              {
            	  WarehouseChannelModel warehouseChannelModel = (WarehouseChannelModel)warehouseChannelVec.get(m);
                  String strWarehouseIdX = warehouseChannelModel.getWarehouseId();
                  String strWarehouseNameX = warehouseChannelModel.getWarehouseName();
                  if(channelSuperTypeIdValue.compareTo(strWarehouseIdX) == 0)
                  {
                
                  out.println("<option value='"+strWarehouseIdX+"'>"+strWarehouseNameX+"</option>");  
                  }
                }
              
              %>
            </select>
            
          </td>
        </tr>
        
     </TABLE>   
       
<br>
<center>
<%
        
              out.print("<INPUT class=button type=button value=\" Next \" name=\"select\" id=\"select\" onclick=\"document.SOPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+strAction+"';"+
                  "checkBeforeSubmit();\">");
%>
</center>
        
</form>    
  </body>
</html>
