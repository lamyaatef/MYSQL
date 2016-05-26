<%@ page contentType="text/html;charset=windows-1252"
              import ="com.mobinil.sds.core.utilities.Utility"
              import="org.apache.commons.fileupload.*"
              import="java.util.*"                   
              import="com.mobinil.sds.web.interfaces.*"
              import="com.mobinil.sds.web.interfaces.sa.*"
              import="com.mobinil.sds.web.interfaces.ac.*"
              import="com.mobinil.sds.web.interfaces.sop.*"
              import="com.mobinil.sds.core.system.sa.importdata.model.*"
              import="com.mobinil.sds.core.system.sop.requests.model.*"
              import ="java.io.*"              
              %>

<HTML>
   <HEAD>
      <META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=windows-1252"/>
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
      <TITLE>Warehouse Data Import</TITLE>
      <center>
      <h2>Warehouse Data Import</h2>
      </center>
   </HEAD>



   <body onkeypress = "if(event.keyCode==13){myform.submit();}">


<script>
function submitForm()
{
  var tableId = document.myform.<%out.print(AdministrationInterfaceKey.CONTROL_DATA_IMPORT_TABLES);%>.value;
  if (tableId=='')
  {
  alert('Please Select a Table');
  return;
  }

  var operation = document.myform.<%out.print(AdministrationInterfaceKey.CONTROL_DATA_IMPORT_OPERATION);%>.value;
  if (operation =='')
  {
  alert('Please Select an Operation');
  return;
  }

    var fileName = document.myform.myfile.value;
    if(fileName == "")
    {
      alert("File field can not be empty.");
      return;
    }
    fileName = fileName.substring(fileName.length - 4, fileName.length);
    fileName = fileName.toLowerCase();
    if(fileName != ".xls")
    {
      alert("Invalid input. Please specify a right excel file.");
      return;
    }

    var documentAction =  document.myform.action;
    documentAction += '&'+'<%out.print(AdministrationInterfaceKey.QUERY_STRING_TABLES+"");%>='+document.myform.<%out.print(AdministrationInterfaceKey.CONTROL_DATA_IMPORT_TABLES);%>.value;
    documentAction += '&'+'<%out.print(AdministrationInterfaceKey.QUERY_STRING_OPERATION+"=");%>'+document.myform.<%out.print(AdministrationInterfaceKey.CONTROL_DATA_IMPORT_OPERATION);%>.value;
    documentAction += '&'+'<%out.print(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID+"=");%>'+document.myform.<%out.print(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);%>.value;
    documentAction += '&'+'<%out.print(SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID+"=");%>'+document.myform.<%out.print(SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID);%>.value;
        
  //document.myform.action = document.myform.action+'&'+'<%out.print(AdministrationInterfaceKey.QUERY_STRING_TABLES+"");%>='+document.myform.<%out.print(AdministrationInterfaceKey.CONTROL_DATA_IMPORT_TABLES);%>.value+'&'+'<%out.print(AdministrationInterfaceKey.QUERY_STRING_OPERATION+"=");%>'+document.myform.<%out.print(AdministrationInterfaceKey.CONTROL_DATA_IMPORT_OPERATION);%>.value
  			//alert (documentAction)	;	 
  			document.myform.action = documentAction; 
  document.myform.Submit.disabled = true;
  document.myform.submit();
  
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
String appName = request.getContextPath();




    HashMap dataHashMap = null;
    dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
	
    String strUserID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
    Vector channelVec = (Vector)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
    Vector dcmChannelVec = (Vector)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2);
    Vector warehouseChannelVec = (Vector)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_3);
    //String strChannelId = (String)dataHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
    //Utility.logger.debug("Channel id issssssssssssssssss" + strChannelId);
    String channelSuperTypeIdValue = "";
    String formAction = appName +"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"
                    +InterfaceKey.HASHMAP_KEY_ACTION+"="
                    +SOPInterfaceKey.ACTION_DATA_WAREHOUSE_IMPORT_PROCESS+
                    "&"+InterfaceKey.HASHMAP_KEY_USER_ID+"="
                    +strUserID;
                  // "&"+SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID+"="
                    //+channelId;
%>    
<form name="myform" action="<%out.print(formAction);%>"method="post" enctype="multipart/form-data">
<%         
                  
    Vector tableVec = (Vector) dataHashMap.get(AdministrationInterfaceKey.TABLE_DEF_VECTOR);
    out.println("<center>");

    out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"50%\" border=1>");
    %>
    
    <tr>
        
          <TD class=TableHeader>Channel</TD>
          <TD >
          <script>
            function changeSCMSelect(superTypeValue)
            {
             
              var warehouseName = eval("document.myform.<%=SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID%>.name");
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
    
    
    
    
    
    
    
    <% 
    out.println("<TR style='display:none'>");
    out.println("<td class=TableHeader nowrap align=center>");
    out.println("Table");
    out.println("</TD>");        
    out.println("<td nowrap align=left>");
    out.println("<Select name="+AdministrationInterfaceKey.CONTROL_DATA_IMPORT_TABLES+">");
    for(int k= 0; k<tableVec.size(); k++)
    {
      DataImportTableDefModel tableModel = (DataImportTableDefModel) tableVec.get(k);            
      out.println("<option value=\""+tableModel.getTableId()+"\">"+tableModel.getTableName()+"</option>");        
    }
    out.println("</Select>");
    out.println("</td>");    
    out.println("</tr>");
    //out.println("<TR>");
    //out.println("<td class=TableHeader nowrap align=center>");
   // out.println("Operation");
   // out.println("</TD>");        
   // out.println("<td nowrap align=left>");
    out.println("<Select name="+AdministrationInterfaceKey.CONTROL_DATA_IMPORT_OPERATION+" style=\"Display:none\">");
    out.println("<option></option>");
    out.println("<option value=\""+AdministrationInterfaceKey.DATA_IMPORT_INSERT+"\" selected>"+AdministrationInterfaceKey.DATA_IMPORT_INSERT+"</option>");
    //out.println("<option value=\""+AdministrationInterfaceKey.DATA_IMPORT_UPDATE+"\">"+AdministrationInterfaceKey.DATA_IMPORT_UPDATE+"</option>");        
    out.println("</Select>");
    //out.println("</td>");    
    //out.println("</tr>");


    out.println("<tr>");
    out.println("<td class=TableHeader nowrap align=center>");
    out.println("File");
    out.println("</td>");
    out.println("<td nowrap align=left>");
%>
   <input type="hidden" name="hiddenField">
    <input type="file" name="myfile">
<%
    out.println("</td>");
    out.println("</tr>");
    out.println("</table>");
%>

    
    <input class="button" type="button" name="Submit" value="Submit your file" onclick="submitForm();">
    </center>
    </form>
   </BODY>
</HTML>
              