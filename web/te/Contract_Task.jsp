<%@ page import="javax.servlet.*"
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"         
         import="com.mobinil.sds.web.interfaces.te.TaskInterfaceKey"
         
%>
<%
 String appName = request.getContextPath();
 String userId=(String)request.getSession().getAttribute(InterfaceKey.HASHMAP_KEY_USER_ID);

 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
<SCRIPT language=JavaScript src="<%out.print(appName);%>/resources/js/validation.js" type="text/javascript"></SCRIPT>
<SCRIPT language=JavaScript src="<%out.print(appName);%>/resources/js/calendar.js" type=text/javascript></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="<%out.print(appName);%>/resources/js/combobox.js"></SCRIPT>
<title>Insert title here</title>
<script>
function xremoveFromListOrderList(source, destination,orderTypeDestination) 
{



  for(i=0;i<destination.options.length;i++) 
  {
    if(destination.options[i].selected == true && destination.options[i].text!='-- SELECTED FIELDS --') 
    {
      var no   = new Option();
      no.value = destination.options[i].value;
      var text = destination.options[i].text;
      no.text  =   text.substring(0,text.lastIndexOf("("));       
      source.options[source.options.length] = no;		
    }
  }//end for
	for(i=destination.options.length-1;i>=0;i--) 
  {
		if(destination.options[i].selected == true && destination.options[i].text!='-- SELECTED FIELDS --') 
    {
			destination.options[i]=null;	
      orderTypeDestination.options[i]=null;
		}
	}//end for
}
</script>

<script>
function xremoveAllOrderList(source, destination,orderTypeDestination) 
{
	for(i=1;i<destination.options.length;i++) 
  {
		destination.options[i].selected = true
	}//end for
	xremoveFromListOrderList(source, destination ,orderTypeDestination );
}
</script>
 
 <script>
function xaddAllOrderList(source, destination,orderTypeDestination,orderType) 
{
	for(i=1;i<source.options.length;i++) 
  {
		source.options[i].selected = true
	}//end for
	xaddToOrderList(source, destination, orderTypeDestination,orderType );
}
</script>

<script>
function xaddToOrderList(source, destination, orderTypeDestination, orderType) 
{
	 for(i=0;i<source.options.length;i++) 
  {
    if(source.options[i].selected == true && source.options[i].text!='-- AVAILABLE FIELDS --') 
    {
      var no   = new Option();
      no.value = source.options[i].value;
      no.text  = source.options[i].text + ' ('+orderType+')';
      destination.options[destination.options.length] = no;
      var orderTypeNode = new Option();
      orderTypeNode.value= orderType;
      orderTypeNode.text= orderType;
      orderTypeDestination.options[orderTypeDestination.options.length]=orderTypeNode;
    }
  }
	for(i=source.options.length-1;i>=0;i--) 
  {
		if(source.options[i].selected == true && source.options[i].text!='-- AVAILABLE FIELDS --') 
    {
			source.options[i]=null;		
		}
  }
	
}
</script>
<script>
    function addServPakgAndRtPln(searchList, updateList)
    {
      addOption(searchList,'5','Rate Plane');
      addOption(searchList,'6','Service Package');
      addOption(updateList,'5','Rate Plane');
      addOption(updateList,'6','Service Package');

    }

    function addOption(selectList,value,text){
        var no   = new Option();
      no.value = value;
      no.text  = text;
      selectList.options[selectList.options.length] = no;

    }
    function removeServPakgAndRtPln(searchList, updateList,selectedSearchList, selectedUpdateList)
    {
      removeOption(searchList,'Rate Plane');
      removeOption(searchList,'Service Package');
      removeOption(updateList,'Rate Plane');
      removeOption(updateList,'Service Package');

      removeOption(selectedSearchList,'Rate Plane');
      removeOption(selectedSearchList,'Service Package');
      removeOption(selectedUpdateList,'Rate Plane');
      removeOption(selectedUpdateList,'Service Package');

    }

    function removeOption(selectList,text){

        for(i=selectList.options.length-1;i>=0;i--)
        {
            if(selectList.options[i].text==text)
            {
                selectList.options[i]=null;	
            }
	}//end for       
    }
</script>
<script type="text/javascript">
var ids=new Array('x_1','x_2');

function switcid(id){	

	hideallids();        
		showid(id);//for filter	
		
	showdiv(id);// for left menu 
        
}

function hideallids(){
	//loop through the array and hide each element by id
	for (var i=0;i<ids.length;i++){
		hidediv(ids[i]);
	}		  
}

function hidediv(id) {
	//safe function to hide an element with a specified id
	if (document.getElementById) { // DOM3 = IE5, NS6

		document.getElementById(id).style.display = 'none';
		
	}
	else {
		if (document.layers) { // Netscape 4
			document.id.display = 'none';
		}
		else { // IE 4
			document.all.id.style.display = 'none';
		}
	}
}

function showdiv(id) {
        
        
	if (document.getElementById) { // DOM3 = IE5, NS6
		document.getElementById(id).style.display = 'block';
	}
	else {
		if (document.layers) { // Netscape 4
			document.id.display = 'block';
		}
		else { // IE 4
			document.all.id.style.display = 'block';
		}
	}
}


function showid(id) {
	//safe function to show an element with a specified id
        var hh=id;
        
        for(i=0;i<hh.length;i++)
        {
        
          if(hh.options[i].selected==true)
          {
          
          
            if (hh.options[i].value=='x_1'){
          document.getElementById('x_1').style.display = 'block';
          document.formTaskContract.selectType.value='1';
          }
          
          if (hh.options[i].value=='x_2'){
          document.getElementById(hh.options[i].value).style.display = 'block';
          document.formTaskContract.selectType.value='2';
          }
        }
          
          
          }  

}
function defaultValues() 
{
document.getElementById('selectSFR').selectedIndex =0;
document.getElementById('selectCIF').selectedIndex =0;
}

function selectItems(selectList){
	
var tt = document.getElementById(selectList);
	if (tt.options.length<=1) 
	{
	alert('There Must be at least one field in the selected fields');
	return false;
	}

	
	for(i=1;i<tt.options.length;i++)
		{
		
		tt[i].selected=true;
		}	
	
}



function getConrolValue(controlName)
{
	
	var d = document.getElementById(controlName).value;
	return d;
}

function getListArry(controlName)
{
	
	var ob = document.getElementById(controlName);
	var argselected = new Array(); 
	for (var i = 0; i < ob.options.length; i++) 
		{
		if (ob.options[ i ].selected){
			argselected.push(ob.options[ i ].value);
			}
		}	
	
	return argselected;
}


function checkbeforSubmit()
{
if (NonBlank(document.getElementById('formTaskContract').<%out.print(TaskInterfaceKey.CONTROL_INPUT_NAME_CONTRACT_NAME);%>, true, 'Task Name'))
{
	
	
	
	
		if (document.getElementById('formTaskContract').<%out.print(TaskInterfaceKey.CONTROL_INPUT_FILE);%>.value.length!=0)
		{
			if 	(document.formTaskContract.selectType.value==2||document.formTaskContract.selectType.value==1)
			{	
			
				
			document.formTaskContract.taskAction.value='<%out.print(TaskInterfaceKey.ACTION_PROCESS_BATCH);%>';				
			}
			else
			{
				document.formTaskContract.taskAction.value='<%out.print(TaskInterfaceKey.ACTION_ADD_NEW_TASK_CONTRACT);%>';
									
			}
		}
		else
		{	
			document.formTaskContract.taskAction.value='<%out.print(TaskInterfaceKey.ACTION_ADD_NEW_TASK_CONTRACT);%>';
			
		}
		//debug
		


		//debug
		var temppp ='<%out.print(appName);%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet?'; 
		 	
		 	temppp+='action=';		 	
		 	temppp+=document.getElementById('taskAction').value+'&';
				
		 	temppp+='contract_name='+document.getElementById('<%out.print(TaskInterfaceKey.CONTROL_INPUT_NAME_CONTRACT_NAME);%>').value+'&';
		
		 	temppp+='fromDate='+document.getElementById('<%out.print(TaskInterfaceKey.CONTROL_INPUT_NAME_FROM_DATE);%>').value+'&';
		
		 	temppp+='toDate='+document.getElementById('<%out.print(TaskInterfaceKey.CONTROL_INPUT_NAME_TO_DATE);%>').value+'&';
				
		 	temppp+='contractFlag='+document.getElementById('contractFlag').value+'&';
		
		 	temppp+='selectType='+document.getElementById('selectType').value+'&';
		
		 	temppp+='batch_type='+document.getElementById('batch_type').value+'&';
		
			temppp+='search_select_list='+getListArry('<%out.print(TaskInterfaceKey.PARAM_UPDATE_CUSTOMIZED_SEARCH_SELECT_LIST);%>')+'&';
		
		temppp+='update_select_list='+getListArry('<%out.print(TaskInterfaceKey.PARAM_UPDATE_CUSTOMIZED_UPDATE_SELECT_LIST);%>')+'';
		
		alert(temppp);
		document.getElementById('formTaskContract').action=temppp;
		
				
		document.getElementById('formTaskContract').submit();
		
		
		
		
				
		
	

}
return false;
}
</script>
</head>
<body onLoad="showdiv('divSFR');showdiv('x_1');document.formTaskContract.contractFlag.value='2';document.formTaskContract.selectType.value='1';">
<CENTER>
<H2>  New&nbsp;Contract&nbsp;Task </H2>
</CENTER>
<form action="" enctype="multipart/form-data"  name="formTaskContract" id="formTaskContract" method="post" >


<input type="hidden" name="taskAction" id="taskAction" value="">

<input type="hidden" name="contractFlag" id="contractFlag" value="">
<input type="hidden" name="selectType" id="selectType" value="">

<input type="hidden" name="<%out.print(InterfaceKey.HASHMAP_KEY_USER_ID); %>" id="<%out.print(InterfaceKey.HASHMAP_KEY_USER_ID); %>" value="<%out.print(userId);%>">


<%
HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);


HashMap vecBatchTypes = (HashMap) objDataHashMap.get (TaskInterfaceKey.HASHMAP_BATCH_TYPES) ;

 %>
<table width="100%" style="WIDTH: 746px; BORDER-COLLAPSE: collapse; HEIGHT: 91px" cellSpacing=2 cellPadding=1 border=1>
<tr class=TableTextNote>
    <td width="226" class=TableTextNote>Task Name</td>
    <td width="504" class=TableTextNote>
    <input name="<%out.print(TaskInterfaceKey.CONTROL_INPUT_NAME_CONTRACT_NAME);%>" id="<%out.print(TaskInterfaceKey.CONTROL_INPUT_NAME_CONTRACT_NAME);%>" type="Text" value=""/>
	</td>
  </tr>
  <tr class=TableTextNote>
    <td width="226" class=TableTextNote>Contract</td>
    <td width="504" class=TableTextNote>
      <label>
        <input  name="<%out.print(TaskInterfaceKey.CONTROL_OPTION_CIF);%>" id="<%out.print(TaskInterfaceKey.CONTROL_OPTION_CIF);%>" type="radio" value="CIF"  onclick="defaultValues();hidediv('divSFR');hideallids();showdiv('divCIF');showdiv('x_1');document.formTaskContract.contractFlag.value='1';addServPakgAndRtPln(groups_fields,groups_field);" />
        CIF</label>
&nbsp; &nbsp;

      <label>
        <input name="<%out.print(TaskInterfaceKey.CONTROL_OPTION_CIF);%>" id="<%out.print(TaskInterfaceKey.CONTROL_OPTION_CIF);%>" type="radio" value="SFR" checked="checked" onClick="defaultValues();hidediv('divCIF');showdiv('divSFR');hideallids();showdiv('x_1');document.formTaskContract.contractFlag.value='2';removeServPakgAndRtPln(groups_fields,groups_field,<%=TaskInterfaceKey.PARAM_UPDATE_CUSTOMIZED_SEARCH_SELECT_LIST%>,<%=TaskInterfaceKey.PARAM_UPDATE_CUSTOMIZED_UPDATE_SELECT_LIST%>);" />
        SFR</label>
</td>
  </tr>
  <tr class=TableTextNote>
    <td class=TableTextNote height="48">Type</td>
    <td class=TableTextNote colspan="2">
	
     	<div id="divCIF" style="display:none;">
	  <label>
	  
      <select name="selectCIF" id="selectCIF" onChange="javascript:switcid(this);">
        <option value="x_1">Batch File</option>
        <option  value="x_2">Batch Type</option>

        </select>
      </label>
      </div>
	  
	<div id="divSFR" style="display:none;">
	  <label>	  
      <select name="selectSFR" id="selectSFR" onChange="javascript:switcid(this);">
        <option value="x_1">Batch File</option>
        </select>
      </label>
      </div>
    </td>
  </tr>
  <tr class=TableTextNote>
    <td class=TableTextNote height="48">&nbsp;</td>
    <td class=TableTextNote colspan="2">

		<table>
		<tr>
		<td width="17%" nowrap class="<%out.print(InterfaceKey.STYLE[1%2]);%>" align=left>From</td>
		          <td width="33%" nowrap class="<%out.print(InterfaceKey.STYLE[1%2]);%>" align=left><input name="<%out.print(TaskInterfaceKey.CONTROL_INPUT_NAME_FROM_DATE);%>" id="<%out.print(TaskInterfaceKey.CONTROL_INPUT_NAME_FROM_DATE);%>" type="text">
		            <label>
		            <input type="button" onClick="showCalendar(formTaskContract.<%out.print(TaskInterfaceKey.CONTROL_INPUT_NAME_FROM_DATE);%>,'dd/mm/yyyy','Choose date')" name="Button" id="Button" value="...">
		          </label></td>
		          <td align=left class="<%out.print(InterfaceKey.STYLE[1%2]);%>" nowrap>To</td>
		          <td align=left class="<%out.print(InterfaceKey.STYLE[1%2]);%>" nowrap><input name="<%out.print(TaskInterfaceKey.CONTROL_INPUT_NAME_TO_DATE);%>" id="<%out.print(TaskInterfaceKey.CONTROL_INPUT_NAME_TO_DATE);%>" type="text">
		          <input type="button" onClick="showCalendar(formTaskContract.<%out.print(TaskInterfaceKey.CONTROL_INPUT_NAME_TO_DATE);%>,'dd/mm/yyyy','Choose date')" name="Submit2" id="Submit2" value="..."></td>
		  </tr>
		</table>

	<div id="x_1" style="display:none;">
		  <label>
	    <input type="file" name="<%out.print(TaskInterfaceKey.CONTROL_INPUT_FILE);%>" id="<%out.print(TaskInterfaceKey.CONTROL_INPUT_FILE);%>" />
	    </label>
	</div>
	
	<div id="x_2" style="display:none;">
		  <select  name="batch_type" id="batch_type">
		  <%
		  
		  Set set = vecBatchTypes.entrySet();
          Iterator i = set.iterator();
          int counter = 0;
         while(i.hasNext())
         {        	 
        	 counter++;        	 
         Map.Entry me = (Map.Entry)i.next();         
         String id=(String)me.getKey();
         String val = (String)me.getValue();
         if (counter==1)out.println("<option value="+id+" selected=\"selected\">"+val+"</option>");
         else out.println("<option value="+id+">"+val+"</option>");         
         }
		  %>        
        </select>
	</div>
    
	


	</td>
  </tr>
  <tr class=TableTextNote>
    <td class=TableTextNote >LCS Search Criteria </td>
    <td class=TableTextNote colspan="2">
            <%
            out.println("<table>");
            out.println("<TR> <td  nowrap rowspan=5 align=left>");
            out.println("<select  size=\"10\" multiple name=\"groups_fields\" id=\"groups_fields\">");
            out.println("<option value=\"\">-- AVAILABLE FIELDS --</option>");            
            out.println("<option value=\"0\">None</option>");
            out.println("<option value=\"1\">LCS Dcm</option>");
            out.println("<option value=\"2\">Activation Date</option>");
            out.println("<option value=\"7\">Issue Date</option>");
            out.println("<option value=\"3\">Product</option>");
            out.println("</select>");
            out.println("</td>");
            out.println("</TR>");

            out.println("<TR>");
            out.println("<td align=left width=\"5%\"><input value=\"     >     \" class=\"button\" type=\"button\" id=\"addToList\" name=\"addToList\" "+
                        " onclick=\"xaddToList(groups_fields"+", "+TaskInterfaceKey.PARAM_UPDATE_CUSTOMIZED_SEARCH_SELECT_LIST+");\""
                        +"  style=\"WIDTH: 60px; HEIGHT: 20px\"></td>");

            out.println("<td nowrap rowspan=4>");
            out.println("<select  size=\"10\" multiple name=\""+TaskInterfaceKey.PARAM_UPDATE_CUSTOMIZED_SEARCH_SELECT_LIST +"\" id=\""+TaskInterfaceKey.PARAM_UPDATE_CUSTOMIZED_SEARCH_SELECT_LIST +"\">");
            out.println("<option value=\"\">-- SELECTED FIELDS --</option>");
            out.println("</select>");
            out.println("</td>");
            out.println("</TR>");

            out.println("<TR>");
            out.println("<td align=left width=\"5%\"><input class=\"button\" type=\"button\" name=\"addAll\" id=\"addAll\" value=\"    >>    \" "+
                        "onclick=\"xaddAll(groups_fields"+", "+TaskInterfaceKey.PARAM_UPDATE_CUSTOMIZED_SEARCH_SELECT_LIST+");\""
                        + " style=\"WIDTH: 60px; HEIGHT: 20px\" size=19></td>");


            out.println("</TR>");

            out.println("<TR >");
            out.println("<td align=left width=\"5%\"><input class=\"button\" type=\"button\" name=\"removeFromList\" id=\"removeFromList\" value=\"     <      \" "+
            " onclick=\"xremoveFromList(groups_fields"+", "+TaskInterfaceKey.PARAM_UPDATE_CUSTOMIZED_SEARCH_SELECT_LIST+");\""
            +" style=\"WIDTH: 61px; HEIGHT: 20px\" size=19></td>");
            out.println("</TR>");
            out.println("<TR >");
            out.println("<td width=\"5%\"><input class=\"button\" type=\"button\" name=\"removeAll\" id=\"removeAll\" value=\"    <<    \" "+
            " onclick=\"xremoveAll(groups_fields"+", "+TaskInterfaceKey.PARAM_UPDATE_CUSTOMIZED_SEARCH_SELECT_LIST+");\"></TD>");

            out.println("</TR>");
            out.println("</table>"); %>
            
            
            </td>
            
            
            
            
  </tr>
  <tr class=TableTextNote>
    <td class=TableTextNote >LCS Update Criteria</td>
    <td class=TableTextNote colspan="2">
        <%
            out.println("<table>");
            out.println("<TR ><td  nowrap rowspan=5 align=left>");
            out.println("<select  size=\"10\" multiple name=\"groups_field\" id=\"groups_field\">");
            out.println("<option value=\"\">-- AVAILABLE FIELDS --</option>");            
            out.println("<option value=\"4\">Transaction Type</option>");
            out.println("<option value=\"1\">LCS Dcm</option>");
            out.println("<option value=\"2\">Activation Date</option>");
            out.println("<option value=\"7\">Issue Date</option>");
            out.println("<option value=\"3\">Product</option>");


            
            out.println("</select>");
            out.println("</td>");
            out.println("</TR>");

            out.println("<TR>");
            out.println("<td align=left width=\"5%\"><input value=\"     >     \" class=\"button\" type=\"button\" name=\"addList\" id=\"addList\" "+
                        " onclick=\"xaddToList(groups_field"+", "+TaskInterfaceKey.PARAM_UPDATE_CUSTOMIZED_UPDATE_SELECT_LIST+");\""
                        +"  style=\"WIDTH: 60px; HEIGHT: 20px\"></td>");

            out.println("<td nowrap rowspan=4>");
            out.println("<select  size=\"10\" multiple name=\""+TaskInterfaceKey.PARAM_UPDATE_CUSTOMIZED_UPDATE_SELECT_LIST +"\" id=\""+TaskInterfaceKey.PARAM_UPDATE_CUSTOMIZED_UPDATE_SELECT_LIST +"\">");
            out.println("<option value=\"\">-- SELECTED FIELDS --</option>");
            out.println("</select>");
            out.println("</td>");
            out.println("</TR>");

            out.println("<TR >");
            out.println("<td align=left width=\"5%\"><input class=\"button\" type=\"button\" name=\"addAll1\" id=\"addAll1\" value=\"    >>    \" "+
                        "onclick=\"xaddAll(groups_field"+", "+TaskInterfaceKey.PARAM_UPDATE_CUSTOMIZED_UPDATE_SELECT_LIST+");\""
                        + " style=\"WIDTH: 60px; HEIGHT: 20px\" size=19></td>");


            out.println("</TR>");

            out.println("<TR >");
            out.println("<td align=left width=\"5%\"><input class=\"button\" type=\"button\" name=\"removeFromList1\" id=\"removeFromList1\" value=\"     <      \" "+
            " onclick=\"xremoveFromList(groups_field"+", "+TaskInterfaceKey.PARAM_UPDATE_CUSTOMIZED_UPDATE_SELECT_LIST+");\""
            +" style=\"WIDTH: 61px; HEIGHT: 20px\" size=19></td>");
            out.println("</TR>");
            out.println("<TR >");
            out.println("<td width=\"5%\"><input class=\"button\" type=\"button\" name=\"removeAll1\" id=\"removeAll1\" value=\"    <<    \" "+
            " onclick=\"xremoveAll(groups_field"+", "+TaskInterfaceKey.PARAM_UPDATE_CUSTOMIZED_UPDATE_SELECT_LIST+");\"></TD>");

            out.println("</TR>");
            out.println("</table>"); 
            %>
        
        </td>
  </tr>
</table>

<table  cellspacing="2" cellpadding="1" border="0" width="747" style="WIDTH: 747px; HEIGHT: 26px">
<tr>
<td align=middle>
      <P align=center>

 &nbsp;<INPUT class=button onClick="selectItems('<%out.print(TaskInterfaceKey.PARAM_UPDATE_CUSTOMIZED_UPDATE_SELECT_LIST); %>');selectItems('<%out.print(TaskInterfaceKey.PARAM_UPDATE_CUSTOMIZED_SEARCH_SELECT_LIST); %>');checkbeforSubmit();" type=button value=" Add " name=" Add" id=" Add" ></P></td>
</tr>
</table>
</form>
</body>
</html>