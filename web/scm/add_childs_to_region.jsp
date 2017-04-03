<%-- 
    Document   : add_childs_to_region.jsp
    Created on : 22/11/2010, 11:07:08
    Author     : Ahmed Adel
--%>
<%@page import="com.lowagie.text.Utilities"%>
<%@ page contentType="text/html;charset=windows-1252"
              import="org.apache.commons.fileupload.*"
              import="java.util.*"
              import="com.mobinil.sds.web.interfaces.*"
              import="com.mobinil.sds.web.interfaces.dcm.*"
              import="com.mobinil.sds.core.system.sa.importdata.model.*"
              import ="java.io.*"
              import="java.sql.*"
              import ="com.mobinil.sds.core.system.dcm.region.dao.*"
              import ="com.mobinil.sds.core.system.dcm.region.dto.*"
              import ="com.mobinil.sds.core.utilities.*"
              %>

<HTML>
   <HEAD>
      <META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=windows-1252"/>
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
      <TITLE>New Entity</TITLE>



   </HEAD>
<%


String appName = request.getContextPath();
HashMap dataHashMap = null;
dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
String USER_ID =(String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
String region_ID=(String)dataHashMap.get(DCMInterfaceKey.INPUT_TEXT_REGION_ID);
String region_Type=(String)dataHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_DCM_USER_LEVEL_TYPE_ID);
String alert = (String)dataHashMap.get(DCMInterfaceKey.Message);
Connection con= Utility.getConnection();
Vector<Area_Level_Type_OriginalDto>Area_Level_Type_Original=null;//RegionDAO.getAllOriginalsLevels(con);
Vector<CovarageLevelDto>CovarageLevel=null;//RegionDAO.getAllCovarageLevels(con);
String excelformAction = appName +"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"
+InterfaceKey.HASHMAP_KEY_ACTION+"="
+DCMInterfaceKey.ACTION_ADD_CHILDS_TO_REGION_PROCESS+"&"+InterfaceKey.HASHMAP_KEY_USER_ID+"="
+USER_ID;

String EntryformAction = appName +"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"
+InterfaceKey.HASHMAP_KEY_ACTION+"="
+DCMInterfaceKey.ACTION_ADD_CHILDS_TO_REGION+"&"+InterfaceKey.HASHMAP_KEY_USER_ID+"="
+USER_ID;
%>



   <body onkeypress = "if(event.keyCode==13){myform.submit();}">
<div align="center">
    <br>
    <br>
    <h2 align="center">Add New Entity TO <%=RegionDAO.getRegionName(region_ID)%></h2>
   <br>
   <br>


<div name="select" align="center">
 <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="80%" border=1>
     <TR>
     <td class=TableTextNote nowrap align=center>
      Type of Request
      </td>
      <td nowrap align=center>
<select name="inputtype" id="inputtype" onchange="show(this);">
    <option></option>
    <option value="OnebyOne">One by One</option>
    <option value="ByMass">By Excel Sheet</option>
</select>
      </td>
 </TABLE>
</div>




<div name ="OnebyOne" id="OnebyOne" align="center" style="display:none">
<form name="entryForm" id="entryForm" action="<%out.print(EntryformAction);%>" method="post">

    <h3 style="font:font-family : arial;color:Black;font-size:15px" align="center">Import Single Entity</h3>

<table style="BORDER-COLLAPSE: collapse" width="80%" border="1" align="center" cellpadding="3" cellspacing="3">
  <tr>
      <td class="TableTextNote" align="center"> Entity English Name</td>
    <td   align="center">
    <% out.println("<input type=hidden name="+DCMInterfaceKey.INPUT_TEXT_REGION_ID+" value="+region_ID+" >");%>
    <input type="text" name="<%=DCMInterfaceKey.INPUT_TEXT_REGION_NAME%>" id="<%=DCMInterfaceKey.INPUT_TEXT_REGION_NAME%>" /></td>
  </tr>

   <tr>
      <td class="TableTextNote" align="center">Entity Arabic Name</td>
    <td   align="center">
    <input type="text" name="<%=DCMInterfaceKey.INPUT_TEXT_REGION_ANAME%>" id="<%=DCMInterfaceKey.INPUT_TEXT_REGION_ANAME%>" /></td>
  </tr>

  <tr>
      <td class="TableTextNote" align="center">Enitiy Code</td>
    <td   align="center">
    <input type="text" name="<%=DCMInterfaceKey.INPUT_TEXT_REGION_CODE %>" id="<%=DCMInterfaceKey.INPUT_TEXT_REGION_CODE%>" /></td>
  </tr>

  <tr>
      <td class="TableTextNote" align="center">Capmas Code</td>
    <td   align="center">
    <input type="text" name="<%=DCMInterfaceKey.INPUT_TEXT_CAPMAS_CODE%>" id="<%=DCMInterfaceKey.INPUT_TEXT_CAPMAS_CODE%>" /></td>
  </tr>


    <%if(region_Type.equals("4")){%>
    <tr>
      <td class="TableTextNote" align="center">POP Code</td>
    <td   align="center">
    <input type="text" name="<%=DCMInterfaceKey.INPUT_TEXT_POP_CODE%>" id="<%=DCMInterfaceKey.INPUT_TEXT_POP_CODE%>" /></td>
  </tr>
  <tr>
      <td class="TableTextNote" align="center">Covarage Level</td>
      <td align=middle><select name='<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_COVARAGE_LEVEL_NAME%>' id='<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_COVARAGE_LEVEL_NAME%>'>
          <option value=''></option>
          <%
          if(CovarageLevel!=null)
          {
          for (int j=0; j<CovarageLevel.size(); j++)
          {
              CovarageLevelDto CovaragelevelModel = (CovarageLevelDto)CovarageLevel.get(j);
              Integer levelIdint = CovaragelevelModel.getId();
              String levelId=levelIdint.toString();
              String levelName = CovaragelevelModel.getName();

          %>
          <option name='<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_COVARAGE_LEVEL_NAME%>' id='<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_COVARAGE_LEVEL_NAME%>' value='<%=levelId%>'><%=levelName%></option>
          <%
          }
          }
          %>
          </select>
        </td>
  </tr>
  <tr>
        <td class="TableTextNote" align="center">Type Original Level</td>
      <td align=middle><select name='<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_ORIGINAL_LEVEL_NAME%>' id='<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_ORIGINAL_LEVEL_NAME%>'>
          <option value=''></option>
          <%
          if(Area_Level_Type_Original!=null)
          {
          for (int j=0; j<Area_Level_Type_Original.size(); j++)
          {
              Area_Level_Type_OriginalDto originallevelModel = (Area_Level_Type_OriginalDto)Area_Level_Type_Original.get(j);
              Integer levelIdint = originallevelModel.getId();
              String levelId=levelIdint.toString();
              String levelName = originallevelModel.getName();

          %>
          <option name='<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_ORIGINAL_LEVEL_NAME%>' id='<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_ORIGINAL_LEVEL_NAME%>' value='<%=levelId%>'><%=levelName%></option>
          <%
          }
          }
          %>
          </select>
        </td>
  </tr>
   <tr>
      <td class="TableTextNote" align="center">Marakaz English Name</td>
    <td   align="center">
    <input type="text" name="<%=DCMInterfaceKey.INPUT_TEXT_MARKAZ_ENAME%>" id="<%=DCMInterfaceKey.INPUT_TEXT_MARKAZ_ENAME%>" /></td>
  </tr>
  <tr>
      <td class="TableTextNote" align="center">Marakaz Arabic Name</td>
    <td   align="center">
    <input type="text" name="<%=DCMInterfaceKey.INPUT_TEXT_MARKAZ_ANAME%>" id="<%=DCMInterfaceKey.INPUT_TEXT_MARKAZ_ANAME%>" /></td>
  </tr>
  <tr>
      <td class="TableTextNote" align="center">Family</td>
    <td   align="center">
    <input type="text" name="<%=DCMInterfaceKey.INPUT_TEXT_FAMILY%>" id="<%=DCMInterfaceKey.INPUT_TEXT_FAMILY%>" /></td>
  </tr>
 <%}
  %>

</table>

<br>
<br>
<center>
  <input class="button"  type="button" name="Submit" value="Save" onclick="EntryForm();">
</center>
</form>
</div>
<div name="Excel" id="Excel" align="center" style="display: none">
<form name="excelform" action="<%out.print(excelformAction);%> "method="post" enctype="multipart/form-data">
<table style="BORDER-COLLAPSE: collapse" width="80%" border="1" align="center" cellpadding="3" cellspacing="3">
   <input type="hidden" name="hiddenField" align="center">
   <%if(region_Type.equals("4")){%>
   <% out.println("<input type=hidden name="+DCMInterfaceKey.IMPORT_TABLE+" value="+DCMInterfaceKey.AREA_CHILDS_TABLE+" >");%>
   <%}else{%>
 <% out.println("<input type=hidden name="+DCMInterfaceKey.IMPORT_TABLE+" value="+DCMInterfaceKey.REGION_CHILDS_TABLE+" >");%>
   <%}%>

    <% out.println("<input type=hidden name="+DCMInterfaceKey.INPUT_TEXT_REGION_ID+" value="+region_ID+" >");%>

   <br><h3 style="font:font-family : arial;color:black;font-size:15px" align="center">Import Regions by Excel File</h3>
        <tr>
            <td class=TableTextNote nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px">Excel File:<br>(.xls or .xlsx)</font></td>
            <td align="center"><input type="file" name="myfile"></td>
        </tr>
</table>
    <center>
    <br>
    <input class="button" type="button" name="Submit" value="Submit your file" onclick="ExcelForm();">
    </center>
    </form>
     </div>
        <br>
                    <input type=button value="Back" onClick="history.go(-1)">
         <br>
</div>

   </BODY>
</HTML>




<script>
function ExcelForm()
{
    if(document.excelform.myfile.value.lastIndexOf(".xls")==-1&&document.excelform.myfile.value.lastIndexOf(".xlsx")==-1)
    {
   alert("Please upload only Excel file");
   return false;
    }


       document.excelform.action = document.excelform.action+'&'+'<%out.print(DCMInterfaceKey.IMPORT_TABLE+"");%>='+document.excelform.<%out.print(DCMInterfaceKey.IMPORT_TABLE);%>.value+
       '&'+'<%out.print(DCMInterfaceKey.INPUT_TEXT_REGION_ID+"");%>='+document.entryForm.<%out.print(DCMInterfaceKey.INPUT_TEXT_REGION_ID);%>.value
       document.excelform.submit();


}
function EntryForm()

{
         if(document.entryForm.<%out.print(DCMInterfaceKey.INPUT_TEXT_REGION_NAME);%>.value=="")
        {

          alert("Region name cannot be empty");

        }else

        {


	document.entryForm.submit();
        }
}

function show(selectBox)
{
    var selectedIndex=selectBox.selectedIndex;
    var selectedValue=selectBox.options[selectedIndex].value;
   if(selectedValue=="OnebyOne")
        {
            document.getElementById("OnebyOne").style.display="";
            document.getElementById("Excel").style.display="none";
        }else if(selectedValue=="ByMass"){
             document.getElementById("OnebyOne").style.display="none";
            document.getElementById("Excel").style.display="";
        }else if(selectedValue=="")
            {
              document.getElementById("OnebyOne").style.display="none";
            document.getElementById("Excel").style.display="none";
            }
}
</script>
