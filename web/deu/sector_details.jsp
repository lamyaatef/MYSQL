<%
/**
 *This page is responsible for configuring sectors attached to a certain file; the user can add, delete and change the order of the sectors within the file. And then on submission of the page, all sectors attached to a certain file is deleted and the current sectors on the page are attached to the file.
 * 
 * @author Michael Gameel
 */ 
 %>
 
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.deu.*"
         import="com.mobinil.sds.core.system.deu.source.model.*"
         import="com.mobinil.sds.core.system.deu.sector.model.*"
         import="com.mobinil.sds.web.interfaces.*"
%>
<html>
<head>
<title>::::DEU ADMIN::::</title>
<link href="../resources/css/Template2.css" type="text/css" rel="stylesheet">
<script language="JavaScript" src="../resources/js/utilities.js" type="text/javascript"></script>
<script language="JavaScript" src="../resources/js/gen_validatorv2.js" type="text/javascript"></script>
</head>
<body leftMargin="0" topMargin="0">
    <Center>
      <H2>Sector Details</H2>
    </Center>
<%
      HashMap dataHashMap = new HashMap(100);
      dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
      if(dataHashMap !=null)
      {
        HashMap additional;
       additional = (HashMap)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
       String fileID = (String)additional.get(DEUKeyInterface.HIDDEN_FILE_ID);
       Vector sources = (Vector)additional.get(DEUKeyInterface.VECTOR_SOURCES);
       String separator =""; 
       String sourceID= "1";
%>
 <script language="JavaScript" type="text/javascript">
function addRowToTable(paramIndex,paramSep,paramSQL)
{
var styles= new Array(2) 
styles[0]="TableTextColumnEven"; 
styles[1]="TableTextColumnOdd";
  var tbl = document.getElementById('tblSectors');
  var lastRow = tbl.rows.length;
  var upStatus = "";
  if(lastRow==1)
  {
    var upStatus = "disabled";
  }
  if(lastRow>1)
  {
  for(var j = 1 ; j < lastRow;j++)
  {
  var el=document.getElementById('Down'+j);
  el.disabled=false;
	}}
	var valSQL=""; 
  var valSep="";
  var iteration=lastRow;
  
  if(paramIndex !=-1)
  {
    iteration = paramIndex;
    valSQL=paramSQL;
    valSep=paramSep;
    lastRow=tbl.rows.length;
  }
  var style=styles[lastRow%2];  
  var row = tbl.insertRow(lastRow);
  
  // left cell
  var sHTML4="<table WIDTH='100%' height='100%' style='border-collapse: collapse' bordercolor='#111111' cellspacing='0' cellpadding='5'><tr><td class='"+style+"' vAlign='top' noWrap align='center' width='10%' height='21' title='Sector Order in File'><B>"+iteration+"</B></td></tr></table>";
  var cellLeft = row.insertCell(0); 
  var order = document.createElement('div');
  order.innerHTML=sHTML4;
  cellLeft.appendChild(order);
  
  // right cell
  var cellRight = row.insertCell(1);
  var sHTML5="<table WIDTH='100%' cellpadding='5' style='border-collapse: collapse' bordercolor='#111111' cellspacing='0'><tr><td class="+style+" vAlign=center noWrap align=center width='40%' ><input class=input2 type='text' name='<%=DEUKeyInterface.CONTROL_TEXT_SECTOR_SEPARATOR_XXX%>"+iteration+"' id='<%=DEUKeyInterface.CONTROL_TEXT_SECTOR_SEPARATOR_XXX%>"+iteration+"' value='"+valSep+"' size='71' title='Values Separator'/></td></tr></table>";
  var el = document.createElement('div');
  el.innerHTML=sHTML5;
  cellRight.appendChild(el);
  
  // select cell
  var cellRightSel = row.insertCell(2);
  var sHTML6="<table WIDTH='100%' height='100%' style='border-collapse: collapse' bordercolor='#111111' cellspacing='0' cellpadding='5'><tr><td class='"+style+"' vAlign='top' noWrap align='center' width='40%' height='21'><select id='<%=DEUKeyInterface.CONTROL_TEXT_SECTOR_SOURCE_XXX%>"+iteration+"' name='<%=DEUKeyInterface.CONTROL_TEXT_SECTOR_SOURCE_XXX%>"+iteration+"' title='SQL Source Name'> <%
  if(sources!=null)
  {
    for ( int i=0 ; i <sources.size();i++)
    {
      SourceModel source =(SourceModel)(sources.get(i));
      out.print("<OPTION value='"+source.getSourceID()+"'>");
      out.print(source.getSourceName());
      out.print("</option>");
    }
  }
%></select> </td></tr></table>";
  var sel = document.createElement('div');  
  sel.innerHTML=sHTML6;
  cellRightSel.appendChild(sel);
  var source = document.getElementById("<%=DEUKeyInterface.CONTROL_TEXT_SECTOR_SOURCE_XXX%>"+iteration);
  for( var selIndex=0;selIndex < source.options.length;selIndex++)
  {
    if(source.options[selIndex].value==valSQL)
      source.selectedIndex = selIndex;  
  }
  
  
  //up cell
  var sHTML3="<table WIDTH='100%' height='100%' style='border-collapse: collapse' bordercolor='#111111' cellspacing='0' cellpadding='5'> <tr> <td class='"+style+"' vAlign='top' noWrap align='center' width='40%' height='21'><input class='input2' name='Up"+iteration+"' id='Up"+iteration+"' type='button' "+upStatus+" value='Up' onclick='moveUp("+iteration+");' title='Click to shift This Row UP'/></td></tr></table>";
  var cellUp = row.insertCell(3);
  var up = document.createElement('div');
    up.innerHTML= sHTML3;
    up.setAttribute('title','Click to shift UP');
  cellUp.appendChild(up);
  
  //down cell
  var sHTML2="<table WIDTH='100%' height='100%' style='border-collapse: collapse' bordercolor='#111111' cellspacing='0' cellpadding='5'> <tr> <td class='"+style+"' vAlign='top' noWrap align='center' width='40%' height='21'><input class='input2' name='Down"+iteration+"' id='Down"+iteration+"' type='button' value='Down' onclick='moveDown("+iteration+");' disabled title='Click to shift This Row Down'/></td></tr></table>";
  var cellDown = row.insertCell(4);
  var down = document.createElement('div');
  down.innerHTML= sHTML2;
  down.setAttribute('title','Click to shift DOWN');
  cellDown.appendChild(down);
  
  //delete cell

  var sHTML="<table WIDTH='100%' height='100%' style='border-collapse: collapse' bordercolor='#111111' cellspacing='0' cellpadding='5'> <tr> <td class='"+style+"' vAlign='top' noWrap align='center' width='40%' height='21'><input class='input2' name='Delete"+iteration+"' id='Delete"+iteration+"' type='button' value='Delete' onclick='removeRowFromTable("+iteration+");' title='Click to Delete This Row'/></td></tr></table>";
  var cellDel = row.insertCell(5);
  var del = document.createElement('div');
    del.innerHTML = sHTML;
   del.setAttribute('title','Click to DELETE');

  cellDel.appendChild(del);
  
  
}
function removeRowFromTable(index)
{
  var tbl = document.getElementById('tblSectors');
	var lastRow = tbl.rows.length;
	if(lastRow>1)
	{
  for(var i = index; i < lastRow-1;i++)
  {
	 var upperSep= document.getElementById("<%=DEUKeyInterface.CONTROL_TEXT_SECTOR_SEPARATOR_XXX%>"+i); 
	 var lowerSep= document.getElementById("<%=DEUKeyInterface.CONTROL_TEXT_SECTOR_SEPARATOR_XXX%>"+(i+1));
	 var upperSql= document.getElementById("<%=DEUKeyInterface.CONTROL_TEXT_SECTOR_SOURCE_XXX%>"+i);
	 var lowerSql= document.getElementById("<%=DEUKeyInterface.CONTROL_TEXT_SECTOR_SOURCE_XXX%>"+(i+1));
	 upperSep.value=lowerSep.value;
	 upperSql.value=lowerSql.value;
	  }
  tbl.deleteRow(lastRow-1);
  if(lastRow>2)
  {
   document.getElementById("Down"+(tbl.rows.length-1)).disabled=true;
  }
}
}
function moveDown(i)
{
  var tbl = document.getElementById('tblSectors');
	 var upperSep= document.getElementById("<%=DEUKeyInterface.CONTROL_TEXT_SECTOR_SEPARATOR_XXX%>"+i);
	 var lowerSep= document.getElementById("<%=DEUKeyInterface.CONTROL_TEXT_SECTOR_SEPARATOR_XXX%>"+(i+1));
	 var upperSql= document.getElementById("<%=DEUKeyInterface.CONTROL_TEXT_SECTOR_SOURCE_XXX%>"+i);
	 var lowerSql= document.getElementById("<%=DEUKeyInterface.CONTROL_TEXT_SECTOR_SOURCE_XXX%>"+(i+1));
	 var temp1=lowerSep.value;
	 var temp2=lowerSql.value;
	 lowerSep.value=upperSep.value;
	 lowerSql.value=upperSql.value;
	 upperSep.value=temp1;
	 upperSql.value=temp2;
}
function moveUp(i)
{
  var tbl = document.getElementById('tblSectors');
	 var upperSep= document.getElementById("<%=DEUKeyInterface.CONTROL_TEXT_SECTOR_SEPARATOR_XXX%>"+i);
	 var lowerSep= document.getElementById("<%=DEUKeyInterface.CONTROL_TEXT_SECTOR_SEPARATOR_XXX%>"+(i-1));
	 var upperSql= document.getElementById("<%=DEUKeyInterface.CONTROL_TEXT_SECTOR_SOURCE_XXX%>"+i);
	 var lowerSql= document.getElementById("<%=DEUKeyInterface.CONTROL_TEXT_SECTOR_SOURCE_XXX%>"+(i-1));
	 var temp1=lowerSep.value;
	 var temp2=lowerSql.value;
	 lowerSep.value=upperSep.value;
	 lowerSql.value=upperSql.value;
	 upperSep.value=temp1;
	 upperSql.value=temp2;
}
function validateRow(frm)
{
    var tbl = document.getElementById('tblSectors');
    var lastRow = tbl.rows.length-1;
    for (var i=1; i<=lastRow; i++) {
      var aRow = document.getElementById('<%=DEUKeyInterface.CONTROL_TEXT_SECTOR_SEPARATOR_XXX%>' + i);
      if (aRow.value.length==0) {
      }
  }
  frm.submit();
}
</script>
<form id="frmAddSectors" name="frmAddSectors" action='<%out.print(request.getContextPath());%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet' method=post>
<input type="hidden" name="<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>" value="<%out.print(DEUKeyInterface.ACTION_SAVE_SECTORS);%>">
<%if((sources!=null)&&(sources.size()!=0)){
%>
<input type="hidden" name="<%out.print(DEUKeyInterface.HIDDEN_FILE_ID);%>" value="<%=fileID%>">
  <table class="main" cellSpacing="0" cellPadding="2" width="100%" align="center" border="0">
    <tr>
      <td>
      <table class="main" cellSpacing="0" cellPadding="2" width="100%" align="center" border="0">
        <tr>
          <td class="TableTextColumneven" vAlign="top" noWrap align="right" width="1%" height="16">
          <table cellSpacing="0" cellPadding="1" width="100%" bgColor="#ffffff" border="0">
            <tr class="TableHeader">
              <td align="left" height="16">Configure Sectors:</td>
            </tr>
            <tr>
              <td class="TableTextColumneven" vAlign="top" noWrap align="center" width="1%">
              <table cellSpacing="0" cellPadding="0" bgColor="#ffffff" border="0" width="100%" name="tblSectors" id="tblSectors" height="100%">
                            <tr>
              <TD class=TableTextColumnEven vAlign=top
                              align=middle ><B>Sector Order</B></td>
<TD class=TableTextColumnEven vAlign=top
                              align=middle ><B>Values Separator</B></td>
<TD class=TableTextColumnEven vAlign=top
                              align=middle ><B>SQL Source</B></td>
<TD class=TableTextColumnEven vAlign=top
                              align=middle ><B>Up</B></td>
<TD class=TableTextColumnEven vAlign=top
                              align=middle ><B>Down</B></td>
<TD class=TableTextColumnEven vAlign=top
                              align=middle ><B>Delete</B></td>
              </tr>
              </table>
              
<%
  Vector sectorsVector = (Vector)dataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
  if((sectorsVector!=null) && (!sectorsVector.isEmpty()))
  {
    for(int i=0;i<sectorsVector.size();i++)
    {
       SectorModel newSectorModel=(SectorModel)sectorsVector.get(i);
       separator =newSectorModel.getSeparator();
       if(separator==null)
        separator="";
       sourceID=newSectorModel.getSourceID();
       %>
        <SCRIPT>addRowToTable("<%=i+1%>","<%=separator%>","<%=sourceID%>");</SCRIPT>          
       <%
    }
  }
%>
      <table cellSpacing="0" cellPadding="0" bgColor="#ffffff" border="0" width="100%" height="100%">
                 <tr>
                  <td class="TableTextColumneven" noWrap height="21" align="center" colspan="6">
                  <input class="button" TYPE="button" value="Add New Sector" onclick="addRowToTable(-1,'','');" size="20" /></td>
                </tr>
              </table>
              </center>
              </td>
            </tr>
          </table>
          <table cellSpacing="0" cellPadding="1" width="100%" bgColor="#ffffff" border="0">
            <tr class="TableHeader">
              <td class="TableTextColumneven" vAlign="top" noWrap align="middle" colSpan="2">
              <input class="button" type="button" value="Save Sectors" title="Save Sectors" name="SaveSectors" onclick="validateRow(this.form);"></td>
            </tr>
          </table>
          </td>
        </tr>
      </table>
      </td>
    </tr>
  </table>
</form>
       <%}
else
{
  String action=DEUKeyInterface.ACTION_NEW_SOURCE;
  %><SCRIPT>
document.frmAddSectors.<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>.value='<%=action%>';
alert('A Source must be added first!');
document.frmAddSectors.submit();
</SCRIPT>
<%}%>      
           <%}%>
</body>

</html></body>

</html>
