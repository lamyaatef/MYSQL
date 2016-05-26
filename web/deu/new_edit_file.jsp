<%
/**
 *This page is responsible for adding or updating a file.
 * 1.	In case of updating a task, It has a button for configuring sectors attached to this file, after 
 *     updating the file, the user is redirected to file_details.jsp
 * 2.	In case of adding a new file, the user is redirected to sector_details.jsp 
 *    after the file addition.
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
         import="com.mobinil.sds.core.system.deu.file.dao.*"
         import="com.mobinil.sds.core.system.deu.file.model.*"
         import="com.mobinil.sds.core.system.deu.filetype.model.*"
         import="com.mobinil.sds.core.system.deu.encoding.model.*"
         import="com.mobinil.sds.core.system.deu.source.model.*"
         import="com.mobinil.sds.core.system.deu.sector.model.*"

         import="com.mobinil.sds.web.interfaces.*"
%>
<HTML><HEAD>
<TITLE>::::DEU ADMIN::::</TITLE>
<META http-equiv=Content-Type content="text/html; charset=windows-1256">
<LINK REL=STYLESHEET TYPE="text/css" HREF="<%out.print(request.getContextPath());%>/resources/css/Template2.css">
<SCRIPT language=JavaScript src="<%out.print(request.getContextPath());%>/resources/js/utilities.js" type=text/javascript></SCRIPT>
<script language="JavaScript" src="<%out.print(request.getContextPath());%>/resources/js/gen_validatorv2.js" type="text/javascript"></script></HEAD>
 <script language="JavaScript" src="../resources/js/utilities.js" type="text/javascript"></script>
<script language="JavaScript" src="../resources/js/gen_validatorv2.js" type="text/javascript"></script>
<BODY leftMargin=0 topMargin=0>
    <Center><H2>File Details</H2></Center>
<%
      HashMap dataHashMap = new HashMap(100);
      dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
      HashMap additional=null;
      Vector encodingVector=null, typeVector=null;
      additional = (HashMap)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
      encodingVector = (Vector)additional.get(DEUKeyInterface.VECTOR_FILE_ENCODINGS);
      typeVector = (Vector)additional.get(DEUKeyInterface.VECTOR_FILE_TYPES);
      String text="";
       boolean boolSectors=false;
      if(dataHashMap !=null)
      {
      boolSectors=false;
        text="Add";
       String action=DEUKeyInterface.ACTION_ADD_FILE;
       String fileID = (String)dataHashMap.get(DEUKeyInterface.HIDDEN_FILE_ID);
       String name="";
       String fileName= "";
       String filePath= "";
       String fileExtension= "";
       String separator= "";
       String timeStamped= "CHECKED";
       String encoding= "";
       String description= "";
       FileModel newFileModel = (FileModel)dataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
      if(newFileModel != null)
      {
      boolSectors=true;
        text="Update";
        action=DEUKeyInterface.ACTION_UPDATE_FILE;
        fileID= newFileModel.getFileID();
        fileName= newFileModel.getFileName();
        name= newFileModel.getName();
        filePath= newFileModel.getFilePath();
        fileExtension= newFileModel.getFileExtension() ;
        //Utility.logger.debug("fileExtension" +fileExtension);
        separator= newFileModel.getSeparator() ;
        if(separator==null)
          separator="";
        timeStamped= newFileModel.getTimeStamped();
        if(timeStamped.equals("1"))
          timeStamped="CHECKED";
        else timeStamped="";
        encoding= newFileModel.getEncoding() ;
        description= newFileModel.getDescription() ;
        if((description==null)||(description.equals("null")))
          description="";
      }
%>
<Script>
function getFileName(what) 
{
    if (what.indexOf('/') > -1)
        answer = what.substring(what.lastIndexOf('/')+1,what.length);
    else
        answer = what.substring(what.lastIndexOf('\\')+1,what.length);
    return answer;
}

function getFileExtension(what) 
{
    if (what.lastIndexOf('.') > -1)
        answer = what.substring(what.lastIndexOf('.')+1,what.length);
    return answer;
}

function getFilePath(what, fileName) 
{
    if (what.indexOf(fileName) > -1)
        answer = what.substring(0, what.lastIndexOf(fileName));
    return answer;
}

function setFileNamePath(fullPathValue)
{
	flag_valid = true;
	if(fullPathValue != "")
	{
		//get file name
		tmp_File_Name = getFileName(fullPathValue);
		//get file extension
		tmp_File_Ext =  getFileExtension(tmp_File_Name);
		//get file path
		tmp_File_Path = getFilePath(fullPathValue, tmp_File_Name);
		//--------------------------------
		
		// validate file path is network path, and ext is from the combobox
		//----------------- validate the file path --------------------

		//check if the first char in uppercase equals to its lowercase, then it's not an alphabet [i.e. not a drive ;)]
		if ((tmp_File_Path.substring(0,1)).toUpperCase() != (tmp_File_Path.substring(0,1)).toLowerCase()) 
		{
			alert('Invalid File Location. \r\nIt must be a network path in this format \\\\123.123.123.123\\file.ext');
			flag_valid = false;
		}
		//----------------- validate the file ext. --------------------
		tmp_max = frmAddFile.<%out.print(DEUKeyInterface.CONTROL_SELECT_FILE_TYPE);%>.length;
		flag_found = false;
		for(i=0; i<tmp_max; i++)
		{
			if(tmp_File_Ext == frmAddFile.<%out.print(DEUKeyInterface.CONTROL_SELECT_FILE_TYPE);%>[i].text) 
			{
				//set file extension into the control value
				frmAddFile.<%out.print(DEUKeyInterface.CONTROL_SELECT_FILE_TYPE);%>[i].selected = true; 
				flag_found = true;
				frmAddFile.x<%out.print(DEUKeyInterface.CONTROL_SELECT_FILE_TYPE);%>.value=tmp_File_Ext;
			}
		}
		
		flag_valid = flag_valid && flag_found ;
		
		if(flag_found == false) 
		{
			alert('Invalid File Extension');
			flag_valid=  false;
		}
		
		//--------------------------------
		if(flag_valid==true)
		{
			//set file name into the control value
			frmAddFile.<%out.print(DEUKeyInterface.CONTROL_TEXT_NAME);%>.value=tmp_File_Name ;
			frmAddFile.<%out.print(DEUKeyInterface.CONTROL_TEXT_FILE_NAME);%>.value=tmp_File_Name ;
			//set file path into the control value
			frmAddFile.<%out.print(DEUKeyInterface.CONTROL_TEXT_FILE_PATH);%>.value = tmp_File_Path;
		}
		else
		{
			//set file name into the control value
			frmAddFile.<%out.print(DEUKeyInterface.CONTROL_TEXT_NAME);%>.value="";
			frmAddFile.<%out.print(DEUKeyInterface.CONTROL_TEXT_FILE_NAME);%>.value="";
			//set file path into the control value
			frmAddFile.<%out.print(DEUKeyInterface.CONTROL_TEXT_FILE_PATH);%>.value = "";
		}
	}
}
</script>
<FORM id=frmAddFile name=frmAddFile action='<%out.print(request.getContextPath());%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet' method=post>
<input type="hidden" name="<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>" >
<input type="hidden" name="<%out.print(DEUKeyInterface.HIDDEN_FILE_ID);%>" value="<%=fileID%>">
<TABLE class=main cellSpacing=0 cellPadding=2 width="100%" align=center
border=0>
  <TBODY>
  <TR>
    <TD>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" bgColor=#ffffff
        border=0><TBODY>
        <TR>
          <TD>
            <TABLE cellSpacing=0 cellPadding=1 width="100%" bgColor=#ffffff
            border=0 height="151">
              <TBODY>
              <TR class=TableHeader >
                <TD colspan=2 align=left height="16"><%=text%> File:</TD>

              <TR>
                <TD class=TableTextColumnodd vAlign=top noWrap align=left width="1%" height="21">File Path Name: </TD>
                <TD class=TableTextColumnodd vAlign=top noWrap height="21">
                <input class=input2 type="file" maxlength="50" size="55" name="x<%out.print(DEUKeyInterface.CONTROL_TEXT_FILE_NAME);%>" title='Enter File Name' onchange="setFileNamePath(frmAddFile.x<%out.print(DEUKeyInterface.CONTROL_TEXT_FILE_NAME);%>.value);"></TD>
              </TR>    
              <TR>
                <TD class=TableTextColumnodd vAlign=top noWrap align=left
                width="1%" height="21">Output File Name: </TD>
                <TD class=TableTextColumnodd vAlign=top noWrap height="21">
                <input class=input2 maxlength="30" type="text" size="55" name="<%out.print(DEUKeyInterface.CONTROL_TEXT_NAME);%>" value='<%=name%>' title='Enter DF Name' ></TD></TR>
              <TR>
                <TD class=TableTextColumnodd vAlign=top noWrap align=left
                width="1%" height="18">File Path: </TD>
                <TD class=TableTextColumnodd vAlign=top noWrap height="18">
                <input class=input2 readonly maxlength="200" type="text" size="55" name="<%out.print(DEUKeyInterface.CONTROL_TEXT_FILE_PATH);%>" title='Enter File Path' value="<%=filePath%>"></TD></TR>
              <TR>
                <TD class=TableTextColumnodd vAlign=top noWrap align=left
                width="1%" height="21">File Name: </TD>
                <TD class=TableTextColumnodd vAlign=top noWrap height="21">
                <input class=input2 readonly type="text" maxlength="50" size="55" name="<%out.print(DEUKeyInterface.CONTROL_TEXT_FILE_NAME);%>" title='Enter File Name' value="<%=fileName%>"></TD></TR>
              <tr>
                <TD class=TableTextColumnodd vAlign=top noWrap align=left
                width="1%" height="16">File Type: </TD>
                <TD class=TableTextColumnodd vAlign=top noWrap height="16"> 
                <input class=input2 disabled type="text" maxlength="50" size="55" name="x<%out.print(DEUKeyInterface.CONTROL_SELECT_FILE_TYPE);%>" title='Enter File Name' value="<%=fileExtension%>">
                <select style='display:none' name="<%out.print(DEUKeyInterface.CONTROL_SELECT_FILE_TYPE);%>">
                <%
                  if(typeVector!=null)
                  {
                    for ( int i=0 ; i <typeVector.size();i++)
                    {

                    FileTypeModel type =(FileTypeModel)(typeVector.get(i));
                    //Utility.logger.debug(fileExtension);
                      if(type.getFileTypeExt().equals(fileExtension))
                        out.print("<OPTION Selected value='"+type.getFileTypeID()+"'>");
                      else 
                        out.print("<OPTION value='"+type.getFileTypeID()+"'>");
                      out.print(type.getFileTypeExt());
                      out.print("</option>\n");
                    }
                  }
                %>
                </select></TD>
              </tr>
              <tr>
                <TD class=TableTextColumnodd vAlign=top noWrap align=left
                width="1%" height="16">File Encoding: </TD>
                <TD class=TableTextColumnodd vAlign=top noWrap height="16">
                <select name="<%out.print(DEUKeyInterface.CONTROL_SELECT_FILE_ENCODING);%>">
                <%
                  if(encodingVector!=null)
                  {
                    for ( int i=0 ; i <encodingVector.size();i++)
                    {
                    EncodingModel enc =(EncodingModel)(encodingVector.get(i));

                    if (enc.getEncodingType().equals(encoding))
                        out.print("<OPTION Selected value='"+enc.getEncodingID()+"'>");
                      else 
                        out.print("<OPTION value='"+enc.getEncodingID()+"'>");
                        
                      out.print(enc.getEncodingType());
                      out.print("</option>\n");
                    }
                  }
                %>
                </select></TD>
                </tr>
                <tr>
                <TD class=TableTextColumnodd vAlign=top noWrap align=left
                width="1%" height="21">Sectors Separator</TD>
                <TD class=TableTextColumnodd vAlign=top noWrap height="21">
                <input class=input2  maxlength="3" type="text" size="3" " name="<%out.print(DEUKeyInterface.CONTROL_TEXT_FILE_SEPARATOR);%>" title='Enter Sectors Separator' value="<%=separator%>"></TD>
              </tr>
              <tr>
                <TD class=TableTextColumnodd vAlign=top noWrap align=left
                width="1%" height="1">Time Stamped: </TD>
                <TD class=TableTextColumnodd vAlign=top noWrap height="1">
    <input type="checkbox" name="<%out.print(DEUKeyInterface.CONTROL_CHK_FILE_TIMESTAMP);%>" <%=timeStamped%>></TD>
              </tr>
              <tr>
                <TD class=TableTextColumnodd vAlign=top noWrap align=right
                width="1%" height="52">File Description (Optional):</TD>
                <TD class=TableTextColumnodd vAlign=top noWrap height="52">
    <TEXTAREA ID="desc" name="<%out.print(DEUKeyInterface.CONTROL_TEXT_FILE_DESC);%>"
    onpaste="if (document.frmAddFile.<%out.print(DEUKeyInterface.CONTROL_TEXT_FILE_DESC);%>.value.length>=300) event.returnValue = false;" 
    ONINPUT="if (document.frmAddFile.<%out.print(DEUKeyInterface.CONTROL_TEXT_FILE_DESC);%>.value.length>=300) event.returnValue = false;" 
    ONKEYPRESS="if (document.frmAddFile.<%out.print(DEUKeyInterface.CONTROL_TEXT_FILE_DESC);%>.value.length>=300) event.returnValue = false;"
    title="Enter text to describe File" rows="3" cols="61"><%=description%></TEXTAREA></TD>
              </tr>
              </TBODY></TABLE>
            <TABLE cellSpacing=0 cellPadding=1 width="100%" bgColor=#ffffff
            border=0>
              <TBODY>
              <TR class=TableHeader>
                <TD class=TableTextColumnodd vAlign=top noWrap align=middle colSpan=2>
                
                <input class=button type=submit value="<%=text%> File" title='<%=text%> File' name="AddFile" onclick="document.frmAddFile.<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>.value='<%=action%>';">

                <%
              /*  
                if(boolSectors)
                {
                  out.print("<input class=button type=submit value='Configure Sectors' title='Configure Sectors' name='configureSectors' onclick=\"document.frmAddFile."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+DEUKeyInterface.ACTION_SHOW_SECTORS+"';\">");                  
                }
                */    
                if(text.equalsIgnoreCase("Update"))
                {
                  out.print("<input class=button type=submit value='Delete' title='Delete File' name='deleteFile' onclick=\"document.frmAddFile."+InterfaceKey.HASHMAP_KEY_ACTION+".value='deu_delete_file';\">");
                }
                
                %>
<!--
                <input type=button class=button value='Cancel' name='Cancel' onclick='history.go(-1);'>
-->
                </TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></FORM>
                <script language="JavaScript" type="text/javascript">
 var frmvalidator = new Validator("frmAddFile");
 frmvalidator.addValidation("<%out.print(DEUKeyInterface.CONTROL_TEXT_NAME);%>","req","Please enter name");
 frmvalidator.addValidation("<%out.print(DEUKeyInterface.CONTROL_TEXT_FILE_PATH);%>","req","Please enter filepath");
 frmvalidator.addValidation("<%out.print(DEUKeyInterface.CONTROL_TEXT_FILE_NAME);%>","req","Please enter filename");

 </script><%}%></BODY></HTML>

<%
if(text.compareTo("Add")==0)
return;


%>
</head>
<body leftMargin="0" topMargin="0">
    <Center>
      <H2>Sector Details</H2>
    </Center>
<%
//      HashMap dataHashMap = new HashMap(100);
      dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
      if(dataHashMap !=null)
      {
  //      HashMap additional;
 //      additional = (HashMap)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
       String fileID = (String)dataHashMap.get(DEUKeyInterface.HIDDEN_FILE_ID);
       Vector sources = (Vector)dataHashMap.get(DEUKeyInterface.VECTOR_SOURCES);
       String separator =""; 
       String sourceID= "1";
%>
 <script language="JavaScript" type="text/javascript">
function addRowToTable(paramIndex,paramSep,paramSQL)
{
var styles= new Array(2) 
styles[0]="TableTextColumnodd"; 
styles[1]="TableTextColumnOdd";
  var tbl = document.getElementById('tblSectors');
  var lastRow = tbl.rows.length;
  var upStatus = "";
  var down_img = "../resources/img/down_ds.gif";
  var up_img = "../resources/img/up.gif";
  if(lastRow==1)
  {
    var upStatus = "disabled";
    up_img = "../resources/img/up_ds.gif";
  }
	if(lastRow>1)
	{
		for(var j = 1 ; j < lastRow;j++)
		{
			var el=document.getElementById('Down'+j);
			el.disabled=false;
			el.src ="../resources/img/down.gif";
			
		}
	}
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
  var sHTML3="<table WIDTH='100%' height='100%' style='border-collapse: collapse' bordercolor='#111111' cellspacing='0' cellpadding='5'> <tr> <td class='"+style+"' vAlign='top' noWrap align='center' width='40%' height='21'><input class='input2' name='Up"+iteration+"' id='Up"+iteration+"' type='image' src='"+up_img+"' "+upStatus+" value='Up' onclick='moveUp("+iteration+");return false;' title='Click to shift This Row UP'/></td></tr></table>";
  var cellUp = row.insertCell(3);
  var up = document.createElement('div');
    up.innerHTML= sHTML3;
    up.setAttribute('title','Click to shift UP');
  cellUp.appendChild(up);
  
  //down cell
  var sHTML2="<table WIDTH='100%' height='100%' style='border-collapse: collapse' bordercolor='#111111' cellspacing='0' cellpadding='5'> <tr> <td class='"+style+"' vAlign='top' noWrap align='center' width='40%' height='21'><input class='input2' name='Down"+iteration+"' id='Down"+iteration+"' type='image' src='"+down_img+"' value='Down' onclick='moveDown("+iteration+");return false;' disabled title='Click to shift This Row Down'/></td></tr></table>";
  
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
   document.getElementById("Down"+(tbl.rows.length-1)).src ="../resources/img/down_ds.gif";
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
          <td class="TableTextColumnodd" vAlign="top" noWrap align="right" width="1%" height="16">
          <table cellSpacing="0" cellPadding="1" width="100%" bgColor="#ffffff" border="0">
            <tr class="TableHeader">
              <td align="left" height="16">Configure Sectors:</td>
            </tr>
            <tr>
              <td class="TableTextColumnodd" vAlign="top" noWrap align="center" width="1%">
              <table cellSpacing="0" cellPadding="0" bgColor="#ffffff" border="0" width="100%" name="tblSectors" id="tblSectors" height="100%">
                            <tr>
              <TD class=TableTextColumnodd vAlign=top
                              align=middle ><B>Sector Order</B></td>
<TD class=TableTextColumnodd vAlign=top
                              align=middle ><B>Values Separator</B></td>
<TD class=TableTextColumnodd vAlign=top
                              align=middle ><B>SQL Source</B></td>
<TD class=TableTextColumnodd vAlign=top
                              align=middle ><B>Up</B></td>
<TD class=TableTextColumnodd vAlign=top
                              align=middle ><B>Down</B></td>
<TD class=TableTextColumnodd vAlign=top
                              align=middle ><B>Delete</B></td>
              </tr>
              </table>
              
<%
  Vector sectorsVector = (Vector)dataHashMap.get(DEUKeyInterface.VECTOR_SECTOR);
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
                  <td class="TableTextColumnodd" noWrap height="21" align="center" colspan="6">
                  <input class="button" TYPE="button" value="Add New Sector" onclick="addRowToTable(-1,'','');" size="20" /></td>
                </tr>
              </table>
              </center>
              </td>
            </tr>
          </table>
          <table cellSpacing="0" cellPadding="1" width="100%" bgColor="#ffffff" border="0">
            <tr class="TableHeader">
              <td class="TableTextColumnodd" vAlign="top" noWrap align="middle" colSpan="2">
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
