<%@ page import ="com.mobinil.sds.core.utilities.Utility"
         import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.sa.*"
         import="com.mobinil.sds.web.interfaces.dcm.*"
         import="com.mobinil.sds.web.interfaces.commission.*"         
         import="com.mobinil.sds.web.interfaces.gn.ua.*"
         import="com.mobinil.sds.core.system.sa.users.model.*"
         import="com.mobinil.sds.core.system.sa.users.dto.*"
         import="com.mobinil.sds.core.system.dcm.region.model.*"
         import="com.mobinil.sds.core.system.dcm.user.model.*"
         import="com.mobinil.sds.core.system.dcm.genericModel.*"
         import="com.mobinil.sds.web.interfaces.gn.querybuilder.*"
         import="com.mobinil.sds.core.system.gn.querybuilder.dto.*"
         import="com.mobinil.sds.core.system.gn.querybuilder.model.*"
         import="com.mobinil.sds.core.system.gn.dataview.dto.*"         
         import="com.mobinil.sds.core.system.commission.model.*"
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<link href="../resources/css/Template1.css" rel="stylesheet" type="text/css" />
<SCRIPT language=JavaScript src="../resources/js/calendar.js" type=text/javascript></SCRIPT>
</head>
<script lnguage="javascript">    
    function showDp(cat_value)
    { 
        
	if(document.getElementById("commission_category").value!="" || document.getElementById("commission_category").value!=undefined)
	{
            xmlHttp=GetXmlHttpObject()
            if (xmlHttp==null)
            {
                alert ("Browser does not support HTTP Request")
                return
            }
            
            var url="http://<%=request.getLocalAddr()%>:<%=request.getLocalPort()%><%=request.getContextPath() %>/commission/DPAjax.jsp"
            url=url+"?cat_id="+cat_value            
            xmlHttp.onreadystatechange=stateChanged 
            xmlHttp.open("GET",url,true)
            xmlHttp.send(null)
            
	}
	else
	{
            alert("Please Select Category Id");
	}
    }
    
    function stateChanged() 
    { 		
        if (xmlHttp.readyState==4 || xmlHttp.readyState=="complete")
        {     
            document.getElementById("txtResult").innerHTML =  "<select  name=<%=CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_DP%> id=<%=CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_DP%>> <option value=-1>Select</option>"+xmlHttp.responseText+"</select>"; 
            
        }        
    }
    
    function GetXmlHttpObject()
    {
        var xmlHttp=null;
        try
        {
            // Firefox, Opera 8.0+, Safari
            xmlHttp=new XMLHttpRequest();
        }
        catch (e)
        {
            //Internet Explorer
            try
            {
                xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");
            }
            catch (e)
            {
                xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
            }
        }
        return xmlHttp;
    }

    
function checkForSubmit()
{ 
  var commissionStartDate = document.COMform.<%=CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_START_DATE%>.value
  var commissionEndDate = document.COMform.<%=CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_END_DATE%>.value
  var commissionStartDatefirstIndex = commissionStartDate.indexOf ("/");
  var commissionStartDatelastIndex = commissionStartDate.lastIndexOf ("/");
  commissionStartDateDay = parseFloat(commissionStartDate.substring (0, commissionStartDatefirstIndex));
  commissionDateMonth = parseFloat(commissionStartDate.substring (commissionStartDatefirstIndex+1, commissionStartDatelastIndex));
  commissionStartDateYear = parseFloat(commissionStartDate.substring (commissionStartDatelastIndex+1, commissionStartDate.length));

  var commissionEndDatefirstIndex = commissionEndDate.indexOf ("/");
  var commissionEndDatelastIndex = commissionEndDate.lastIndexOf ("/");
  commissionEndDateDay = parseFloat(commissionEndDate.substring (0, commissionEndDatefirstIndex));
  commissionEndDateMonth = parseFloat(commissionEndDate.substring (commissionEndDatefirstIndex+1, commissionEndDatelastIndex));
  commissionEndDateYear = parseFloat(commissionEndDate.substring (commissionEndDatelastIndex+1, commissionEndDate.length));
  if(eval("document.COMform.<%=CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_BASE%>.value") == 0)
  {
      
      if(eval("document.COMform.<%=CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_CHANNEL%>.value") == "")
      {
        alert("Commission Channel must not be empty.");
        return;
      }
      
      if(eval("document.COMform.<%=CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_CATEGORY%>.value") == "")
      {
        alert("Commission Category must not be empty.");
        return;
      }
//      if(eval("document.COMform.<%=CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_DP %>.value") == "-1")
//      {
//        alert("Commission Driving Plan must not be empty.");
//        return;
//      }

      if(commissionStartDate == "*")
      {
        alert("Commission Start Date must not be empty.");
        return;
      }

     if(commissionEndDate == "*")
      {
        alert("Commission End Date must not be empty.");
        return;
      }

      if(commissionStartDateYear > commissionEndDateYear)
      {
        alert("Commission End Date must be greater than Commission Start date");
                    return;
      }
      else if(commissionStartDateYear == commissionEndDateYear && commissionDateMonth > commissionEndDateMonth)
      {
        alert("Commission End Date must be greater than Commission Start date");
                    return;
      }
      else if(commissionStartDateYear == commissionEndDateYear && commissionDateMonth == commissionEndDateMonth && commissionStartDateDay > commissionEndDateDay)
      {
        alert("Commission End Date must be greater than Commission Start date");
                    return;
      }
      
      if(eval("document.COMform.<%=CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_DATA_VIEW%>.value") == "0")
      {
        alert("Commission Data View must not be empty.");
        return;
      }
      if(document.COMform.<%=CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_DATA_VIEW_TYPE%>[0].checked ==false &&
              document.COMform.<%=CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_DATA_VIEW_TYPE%>[1].checked ==false)
      {
        alert("Commission Data View Type must not be empty.");
        return;
      }
  }
  COMform.submit()
}
function drawCalender(argOrder,argValue)
    {
        document.write("<INPUT value="+argValue+" class=input readOnly name=\""+argOrder+"\">&nbsp;<A onclick=\"showCalendar(COMform."+argOrder+",'dd/mm/yyyy','Choose date')\">");
        document.write("<IMG height=16 src=\"../resources/img/icon_calendar.gif\" width=20 border=0></A>");
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
    function disable(value)
    {
      if(value != 0)
      {
        document.COMform.<%=CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_CATEGORY%>.disabled =false;
        document.COMform.<%=CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_TYPE%>.disabled =false;
        document.COMform.<%=CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_DATA_VIEW%>.disabled =false;
        document.COMform.<%=CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_DATA_VIEW_TYPE%>[0].disabled=false;        
        document.COMform.<%=CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_DATA_VIEW_TYPE%>[1].disabled=false;               

      }
      else
      {
        document.COMform.<%=CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_CATEGORY%>.disabled =true;
        document.COMform.<%=CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_TYPE%>.disabled =true;
        document.COMform.<%=CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_DATA_VIEW%>.disabled =true;
        document.COMform.<%=CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_DATA_VIEW_TYPE%>[0].disabled=true;        
        document.COMform.<%=CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_DATA_VIEW_TYPE%>[1].disabled=true;               
      }
    }

</script>
<body>
<center><H2>Commission Details</H2></center>
<%
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
  Vector commissionTypes = (Vector)objDataHashMap.get(CommissionInterfaceKey.VECTOR_COMMISSION_TYPE);
  Vector commissionTypeCategories = (Vector)objDataHashMap.get(CommissionInterfaceKey.VECTOR_COMMISSION_TYPE_CATEGORY);
  Vector dataViews = (Vector) objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);  
  Vector commissionVector = (Vector)objDataHashMap.get(CommissionInterfaceKey.VECTOR_COMMISSION_SEARCH_RESULT);
  Vector comChannelVec = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
  
%>

<form name="COMform" action="" method="post">
<%
  
  
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");             
                                    
%>

<script>
    function changeTypeCategory(selectName) 
    {
      var typeID = eval("document.COMform."+selectName+".value");
      removeAllOptions('<%=CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_CATEGORY%>');
      addOption('','','<%=CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_CATEGORY%>'); 
      <%
      for(int i = 0 ; i< commissionTypeCategories.size() ; i++)
      {
        GenericModel commissionTypeCategoryModel = (GenericModel)commissionTypeCategories.get(i);
        String categoryID = commissionTypeCategoryModel.get_primary_key_value().trim();
        String categoryName = commissionTypeCategoryModel.get_field_2_value().trim();
        String categoryType = commissionTypeCategoryModel.get_field_3_value().trim();
        %>
        
        if(typeID == '<%=categoryType%>')
        {
          addOption('<%=categoryID%>','<%=categoryName%>','<%=CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_CATEGORY%>');          
        }
        
      <%}%>
    }

</script>

<table style="BORDER-COLLAPSE: collapse" width="756" border="1" align="center" cellpadding="0" cellspacing="1" >
<tr>
  <td height="14" colspan="6" class="TableHeader">Commission Details</td>
</tr>
<tr>
    <td width="165" height="31" class="TableTextNote">Commission Base </td>
    <td width="591" class="TableTextNote"><select name="<%=CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_BASE%>" id="<%=CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_BASE%>"/>
      <option value="0"></option>
    <%
    for(int i = 0 ; i < commissionVector.size() ; i++)
    {
    CommissionModel commissionModel = (CommissionModel)commissionVector.get(i);
      out.println("<option value=\""+commissionModel.getCommissionID()+"\" > "+commissionModel.getCommissionName()+"</option>");
    }
    
    %>
    </select></td>  </tr>
  <!--  <tr>
  
    <td width="165" height="31" class="TableTextNote">Commission Name </td>
    <td width="591" class="TableTextNote"><input type="text" name="<%=CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_NAME%>" id="<%=CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_NAME%>" /></td>
  </tr>
  -->
  <tr>
    <td height="27" class="TableTextNote">Commission Type </td>
    <td class="TableTextNote"><select name="<%=CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_TYPE%>" id="<%=CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_TYPE%>" onchange="changeTypeCategory(this.name);" >
    <%
      for(int i = 0 ; i < commissionTypes.size() ; i++)
      {
        GenericModel commissionTypeModel = (GenericModel)commissionTypes.get(i);
        out.println("<option value='"+commissionTypeModel.get_primary_key_value()+"'> "+commissionTypeModel.get_field_2_value());
        out.println(" </option> ");
      }
    %>
    </select>    </td>
  </tr>
  <tr>
  <td height="27" class="TableTextNote">Commission Channel </td>
  <td class=TableTextNote>
          <select name='<%=CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_CHANNEL%>' id='<%=CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_CHANNEL%>'>
          <option value=''></option>
              <%
              for(int j=0;j<comChannelVec.size();j++)
              {
                CommissionChannelModel commissionChannelModel = (CommissionChannelModel)comChannelVec.get(j);
                String commissionChannelIdX = commissionChannelModel.getCommissionChannelId();
                String commissionChannelNameX = commissionChannelModel.getCommissionChannelName();
                
                %>
                <option value='<%=commissionChannelIdX%>'><%=commissionChannelNameX%></option>
                <%
              }
              %>
            </select>
        </td>
  </tr>
  <tr>
    <td height="28" class="TableTextNote">Commission Category </td>
    <td class="TableTextNote"><select onChange="showDp(this.value);" name="<%=CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_CATEGORY%>" id="<%=CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_CATEGORY%>">
    <option></option>
    <%
    
      for(int i = 0 ; i < commissionTypeCategories.size() ; i++)
      {
        GenericModel commissionTypeCategoryModel = (GenericModel)commissionTypeCategories.get(i);
        
        out.println("<option value='"+commissionTypeCategoryModel.get_primary_key_value()+"'>"+commissionTypeCategoryModel.get_field_2_value());
                out.println("</option>");
      }
    %>    
    </select></td>
  </tr>
  <tr>
    <td height="28" class="TableTextNote">Commission Driving Plan </td>
    <td class="TableTextNote">
        <div id="txtResult">
        <select  name="<%=CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_DP%>" id="<%=CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_DP%>">
            <option value="-1" >Select</option>        
    </select>
        </div>
        </td>
  </tr>
  <tr>
    <td height="27" class="TableTextNote">Commission Start Date </td>
    <td class="TableTextNote"><script>drawCalender('<%=CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_START_DATE%>' , "*");</script></td>
  </tr>
  <tr>
    <td height="29" class="TableTextNote">Commission End Date </td>
    <td class="TableTextNote"><script>drawCalender('<%=CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_END_DATE%>' , "*");</script></td>
  </tr>
  <tr>
    <td height="26" class="TableTextNote">Commission Data View</td>
    <td class="TableTextNote"><select name="<%=CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_DATA_VIEW%>" id="<%=CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_DATA_VIEW%>">
    <option value="0"></option>
    <%
         BriefDataViewDTO dtoBriefDataView                        = null;
    if(dataViews != null)
    {
        
        for (int i = 0; i < dataViews.size(); i++) 
        {
            dtoBriefDataView = (BriefDataViewDTO)dataViews.elementAt(i);
            if (dtoBriefDataView.getOverRideSQL() !=null)
            {
            Utility.logger.debug(" not going to display "+ dtoBriefDataView.getDataViewName());
            Utility.logger.debug(" the over ride sql is  "+ dtoBriefDataView.getOverRideSQL());
            }
            else
            {            
              out.println(" <option desc='"+dtoBriefDataView.getDataViewDescription()+"' value="+dtoBriefDataView.getDataViewID()+">"+dtoBriefDataView.getDataViewName()+"</option> ");
            }
        
        }
    } 
    %>
    </select></td>
  </tr>
  <tr>
  <td class=TableTextNote> Data View Type</td>
  <td class=TAbleTextNote>Cross Tab<input type="radio" value="<%=CommissionInterfaceKey.DATA_VIEW_TYPE_A%>" name="<%=CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_DATA_VIEW_TYPE%>">   Normal<input type="radio" value="<%=CommissionInterfaceKey.DATA_VIEW_TYPE_B%>" name="<%=CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_DATA_VIEW_TYPE%>"></td>
  </tr>
  <tr>
    <td class="TableTextNote">Commission Description </td>
    <td class="TableTextNote"><textarea name="<%=CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_DESCRIPTION%>" id="<%=CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_DESCRIPTION%>" style="width:60%;height:80px"></textarea></td>
  </tr>
   <tr>
    <td class="TableTextNote">Subtract Commission </td>
    <td class="TableTextNote"><input type="text" name="<%=CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_SUBTRACT_ID%>" id="<%=CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_SUBTRACT_ID%>"></></td>
  </tr>
  <tr>
    </form>
<%
out.println("<td colspan=\"6\" align=\"center\"><input name=\"Submit\" type=\"button\" class=\"button\" value=\"Save\""+
            " onclick=\"document.COMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+
            CommissionInterfaceKey.ACTION_COMMISSION_SAVE_NEW_COMMISSION+"';checkForSubmit();\" /></td>");
%>

  </tr>
</table>
</body>
</html>
