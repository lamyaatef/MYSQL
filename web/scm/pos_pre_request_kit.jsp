<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.scm.SCMInterfaceKey"
         import="com.mobinil.sds.core.system.request.model.SupervisorModel"
%>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
<title>POS Pre-Request</title>
  <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
</head>
<body>

<br>
 <Center>
      <H2>POS Pre-Request</H2>
 </Center>
 <br>
 
<%

HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
int stkAvailable = (Integer) objDataHashMap.get(SCMInterfaceKey.REP_KIT_QUANTITY_AVAILABLE);
Vector supervisorList = (Vector) objDataHashMap.get(SCMInterfaceKey.REP_KIT_SUPERVISOR_LIST);
String todayDate = (String) objDataHashMap.get(SCMInterfaceKey.REP_KIT_DATE);
String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

%>

 <form id="formRepKit" name="formRepKit" action="" method=post>
 
 <input type="hidden" name=<%=InterfaceKey.HASHMAP_KEY_ACTION%> value="<%= SCMInterfaceKey.ACTION_STORE_REQUEST %>">
 <input type=hidden name=<%=InterfaceKey.HASHMAP_KEY_USER_ID%> value=<%=strUserID %> >
 
 <table align="center">
 <tr>
 <td>Supervisor  </td>
 <td>
 <select name=<%= SCMInterfaceKey.REP_KIT_SUPERVISOR_ID %> id=<%= SCMInterfaceKey.REP_KIT_SUPERVISOR_ID %>>
 <%
    for(int i = 0 ; i < supervisorList.size() ; i ++)
    {	
    	SupervisorModel supervisor = (SupervisorModel)supervisorList.get(i);
 %>
        <option value="<%= supervisor.getSupervisorId() %>"><%= supervisor.getSupervisorName() %></option>
 <%
    }
 %>
 
 </select>
 </td>
 </tr>
 
 <tr>
 <td>Date  </td>
 <td><input type=text disabled="disabled" value=<%=todayDate%> /></td>
 </tr>
 
 <tr>
 <td>POS Code Quantity  </td>
 <td><input type=text  name=<%=SCMInterfaceKey.REP_KIT_POS_QUANTITY %> id=<%= SCMInterfaceKey.REP_KIT_POS_QUANTITY %> /></td>
 </tr>
 
 <tr>
 <td>STK Quantity  </td>
 <td><input type=text  name=<%=SCMInterfaceKey.REP_KIT_STK_QUANTITY_REQUIRED%> id=<%=SCMInterfaceKey.REP_KIT_STK_QUANTITY_REQUIRED%> onclick="removeDiv()"  /></td>
 </tr>
 
 <tr>
 <td colspan="2"><center><input class=button  name="stkButton" id="stkButton" value="Submit STK Quantity" type=button onclick="createBox()"  /></center></td>
 </tr>
 
 <tr>
 <td colspan="2">
 <div align=center name="stkDivParent" id="stkDivParent">
 <div align=center name="stkDiv" id="stkDiv"></div>
 </div>
 </td>
 </tr>
 </table>
 <br>
 <center><input class=button  type="button"  value="Submit Request" onclick="saveRequest()"></center>
 </form>
</body>
</html>

<SCRIPT language=JavaScript>


function removeDiv()
{
	document.formRepKit.<%=SCMInterfaceKey.REP_KIT_STK_QUANTITY_REQUIRED%>.value ='';

        var child = document.getElementById("stkDiv");
	document.getElementById("stkDivParent").removeChild(child);

	var element = document.createElement("div");
	element.setAttribute("name", "stkDiv");
	element.setAttribute("id", "stkDiv");
	document.getElementById("stkDivParent").appendChild(element);

}


function createBox()
{
    var stkRequired  = document.formRepKit.<%=SCMInterfaceKey.REP_KIT_STK_QUANTITY_REQUIRED%>.value;

    if(eval("document.formRepKit.<%=SCMInterfaceKey.REP_KIT_STK_QUANTITY_REQUIRED%>.value") != "" && eval("document.formRepKit.<%=SCMInterfaceKey.REP_KIT_STK_QUANTITY_REQUIRED%>.value")!= 0)
    {
        if (isNaN(stkRequired))
        {
            alert("STK Quantity Accepts Numbers Only ...");
        }
        else
        {
            if(eval("document.formRepKit.<%=SCMInterfaceKey.REP_KIT_STK_QUANTITY_REQUIRED%>.value") > <%= stkAvailable%>)
            {
                alert("STK Quantity Greater Than the Available .. Quantity Available is " + '<%= stkAvailable%>' );
            }
            else
            {

                var child = document.getElementById("stkDiv");
                document.getElementById("stkDivParent").removeChild(child);

                var element = document.createElement("div");
                element.setAttribute("name", "stkDiv");
                element.setAttribute("id", "stkDiv");
                document.getElementById("stkDivParent").appendChild(element);
                for(i = 0 ; i < stkRequired ; i++ )
                {
		    var name = 'stkNo' + i;
		    var element = document.createElement("input");
		    element.setAttribute("type", "text");
		    element.setAttribute("name", name);
		    element.setAttribute("id", name);
		    element.setAttribute("align", "center");
		    
                    element.onkeydown = function() {checkEnter(event);};

		    document.getElementById("stkDiv").appendChild(element);
		    var br = document.createElement('br');
		    document.getElementById("stkDiv").appendChild(br);
                }

            }
        }
    }
    else
    {
        alert("Please Enter STK Quantity ... ");
    }

}



function saveRequest()
{
    var flag = 0;
  if(eval("document.formRepKit.<%=SCMInterfaceKey.REP_KIT_POS_QUANTITY%>.value") != "")
    {
      if (isNaN(document.formRepKit.<%=SCMInterfaceKey.REP_KIT_POS_QUANTITY%>.value))
      {
          flag = 1;
        alert("POS Quantity Accepts Numbers Only ...");
        return;
      }
      else if(eval("document.formRepKit.<%=SCMInterfaceKey.REP_KIT_POS_QUANTITY%>.value") < 0)
      {
          flag = 1;
        alert("Invalid POS Quantity ...");
        return;
      }

    }
    else if(eval("document.formRepKit.<%=SCMInterfaceKey.REP_KIT_POS_QUANTITY%>.value") == "" || eval("document.formRepKit.<%=SCMInterfaceKey.REP_KIT_POS_QUANTITY%>.value") == "0")
    {
      if(eval("document.formRepKit.<%=SCMInterfaceKey.REP_KIT_STK_QUANTITY_REQUIRED%>.value") == "" || eval("document.formRepKit.<%=SCMInterfaceKey.REP_KIT_STK_QUANTITY_REQUIRED%>.value") == "0")
      {
          flag = 1;
        alert("POS Quantity or STK Quantity Must be Specified ...");
        return;
      }

    }
  if(flag != 1)
  {
   formRepKit.submit();
  }

}




function checkEnter()
{
	//alert (event.keyCode);
    if (event.keyCode==13)
    {

	    event.keyCode=9;
	   // alert (event.keyCode);
	    return event.keyCode ;

	 }
}

</script>