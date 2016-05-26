<%@ page contentType="text/html;charset=windows-1256"%>
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.te.TaskInterfaceKey"
         import="com.mobinil.sds.core.system.te.dto.*"         
 %>

 <%
 String appName = request.getContextPath();
     String userId=(String)request.getSession().getAttribute(InterfaceKey.HASHMAP_KEY_USER_ID);

 %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
    <LINK REL="STYLESHEET" TYPE="text/css" HREF="../resources/css/Template1.css">    
        <SCRIPT language="JavaScript" src="<%out.print(appName);%>/resources/js/validation.js" type="text/javascript"></SCRIPT>
        <SCRIPT language="JavaScript" src="<%out.print(appName);%>/resources/js/combobox.js"></SCRIPT>
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
    <SCRIPT language="JavaScript">

    
    var ids=new Array('x_2','x_1');

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
            
        document.getElementById(id.value).style.display = 'block';    
    }


    function showid(id) {
    	//safe function to show an element with a specified id
            var hh=id;
            
            for(i=0;i<hh.length;i++)
            {
            
              if(hh.options[i].selected==true)
              {
              if (hh.options[i].value=='x_2'){
              document.getElementById(hh.options[i].value).style.display = 'block';
              }

              if (hh.options[i].value=='x_1'){
                  document.getElementById(hh.options[i].value).style.display = 'block';
            }
              
              
              }  

    }
  }
    function defaultValues() 
    {
    document.getElementById('postpaidSelect').selectedIndex =0;
    document.getElementById('prepaidSelect').selectedIndex =0;
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
    	    
function loadValues(control,value)
  {
    document.formArpuTaskDetails.control.value=value;
  }    
function checkbeforSubmit()
{		
  if(NonBlank(document.formArpuTaskDetails.<%out.print(TaskInterfaceKey.CONTROL_TEXT_TASK_NAME);%>, true, 'Task Name'))
  {    
		document.formArpuTaskDetails.<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>.value='<%out.print(TaskInterfaceKey.ACTION_SAVE_ARBU_TASK);%>';
		formArpuTaskDetails.submit();
	 
  } 
return false;
}
  
</SCRIPT>
  </head>
  <body>
        <CENTER>
<H2>  New&nbsp;ARPU&nbsp;Task </H2>
</CENTER>
 <form action="../servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="formArpuTaskDetails" id="formArpuTaskDetails" method="post" >

<%

HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);


  
 String taskName="";
 String taskDesc="";





Vector postpaidPartitions = (Vector)objDataHashMap.get(TaskInterfaceKey.POST_PAID_PARTITIONS_VEC) ;

Vector prepaidPartitions = (Vector)objDataHashMap.get(TaskInterfaceKey.PRE_PAID_PARTITIONS_VEC) ;


out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");
out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+userId+"\">");                  

out.println("<TABLE style=\"WIDTH: 746px; BORDER-COLLAPSE: collapse; HEIGHT: 91px\" cellSpacing=2 cellPadding=1 width=\"746\" border=1>");
out.println("<TR class=TableTextNote>");
out.println("<TD class=TableTextNote width=\"20%\">Task&nbsp;Name *</TD>");


out.println("<TD class=TableTextNote colSpan=4><INPUT style=\"WIDTH: 452px; HEIGHT: 22px\" size=66"
              + " value=\"" +"\""  
              + " name=\""+TaskInterfaceKey.CONTROL_TEXT_TASK_NAME+"\">");


out.println("</TD></TR>");
out.println("<TR>");
out.println("<TD class=TableTextNote width=\"20%\" noWrap>Task&nbsp;Description </TD>");
out.println("<TD class=TableTextNote colSpan=4><TEXTAREA id=textarea1 style=\"WIDTH: 451px; HEIGHT: 84px\" name=\""+TaskInterfaceKey.CONTROL_TEXT_TASK_DESCRIPTION+"\" rows=4 cols=47>"+"</TEXTAREA></TD></TR>");

out.println("<TR>");
out.println("<TD class=TableTextNote width=\"20%\" noWrap>ARBU&nbsp;Payment&nbsp;Type </TD>");
out.println("<TD align=left class=TableTextNote colSpan=4><select onChange=\"switcid(this);defaultValues();\" style=\"width:100pt\" name=\"selectTskType\"><option selected  value=\"x_1\">Pre-Paid</option><option  value=\"x_2\">PostPaid</option></select>");
out.println("<hr style=\"width:100pt\">");
out.println("<div id=\"x_2\" style=\"display:none;\">");
out.println("<select style=\"width:100pt\" name=\"postpaidSelect\">");
for(int i=0;i<postpaidPartitions.size();i++)
{
	String postString = (String)postpaidPartitions.get(i);
	String label = postString.substring(0,3)+"-20" +postString.substring(3,5);


		out.println("<option value="+1+postString+">"+label+"</option>");
}
out.println("</select></div>");
out.println("<div id=\"x_1\" style=\"display:block;\">");
out.println("<select style=\"width:100pt\" name=\"prepaidSelect\">");
for(int i=0;i<prepaidPartitions.size();i++)
{
	String perString = (String)prepaidPartitions.get(i);
	
	String label = perString.substring(0,3)+"-20" +perString.substring(3,5);
	
		out.println("<option value="+1+perString+">"+label+"</option>");
}
out.println("</select></div>");
out.println("</TD></TR>");

		out.println("<tr class=TableTextNote>");
		out.println("<td class=TableTextNote >Update Criteria</td>");
		out.println("<td class=TableTextNote align=left colspan=\"4\">");
    
        out.println("<table>");
        out.println("<TR ><td  nowrap rowspan=5 align=left>");
        out.println("<select  size=\"10\" multiple name=\"groups_field\">");
        out.println("<option value=\"\">-- AVAILABLE FIELDS --</option>");            
        out.println("<option value=\"4\">Transaction Type</option>");
        out.println("<option value=\"1\">LCS Dcm</option>");
        out.println("<option value=\"2\">Activation Date</option>");
        out.println("<option value=\"3\">Product</option>");


        
        out.println("</select>");
        out.println("</td>");
        out.println("</TR>");

        out.println("<TR>");
        out.println("<td align=left width=\"5%\"><input value=\"     >     \" class=\"button\" type=\"button\" name=\"addList\" "+
                    " onclick=\"xaddToList(groups_field"+", "+TaskInterfaceKey.PARAM_UPDATE_CUSTOMIZED_UPDATE_SELECT_LIST+");\""
                    +"  style=\"WIDTH: 60px; HEIGHT: 20px\"></td>");

        out.println("<td nowrap align=left rowspan=4>");
        out.println("<select  size=\"10\" multiple name=\""+TaskInterfaceKey.PARAM_UPDATE_CUSTOMIZED_UPDATE_SELECT_LIST +"\">");
        out.println("<option value=\"\">-- SELECTED FIELDS --</option>");
        out.println("</select>");
        out.println("</td>");
        out.println("</TR>");

        out.println("<TR >");
        out.println("<td align=left width=\"5%\"><input class=\"button\" type=\"button\" name=\"addAll1\" value=\"    >>    \" "+
                    "onclick=\"xaddAll(groups_field"+", "+TaskInterfaceKey.PARAM_UPDATE_CUSTOMIZED_UPDATE_SELECT_LIST+");\""
                    + " style=\"WIDTH: 60px; HEIGHT: 20px\" size=19></td>");


        out.println("</TR>");

        out.println("<TR >");
        out.println("<td align=left width=\"5%\"><input class=\"button\" type=\"button\" name=\"removeFromList1\" value=\"     <      \" "+
        " onclick=\"xremoveFromList(groups_field"+", "+TaskInterfaceKey.PARAM_UPDATE_CUSTOMIZED_UPDATE_SELECT_LIST+");\""
        +" style=\"WIDTH: 61px; HEIGHT: 20px\" size=19></td>");
        out.println("</TR>");
        out.println("<TR >");
        out.println("<td align=left width=\"5%\"><input class=\"button\" type=\"button\" name=\"removeAll1\" value=\"    <<    \" "+
        " onclick=\"xremoveAll(groups_field"+", "+TaskInterfaceKey.PARAM_UPDATE_CUSTOMIZED_UPDATE_SELECT_LIST+");\"></TD>");

        out.println("</TR>");
        out.println("</table></td></tr>"); 
        
    
    




out.println("<TR class=TableTextNote><TD colspan=5 class=TableTextNote><font size=1>* indicates mandatory fields.&nbsp;</font></TD></TR></TABLE></TABLE>");

out.println("<input type=\"hidden\" name=\""+TaskInterfaceKey.CONTROL_HIDDEN_TASK_ID+"\""+
                  " value=\""+"\">");       
        



%>

<table  cellspacing="2" cellpadding="1" border="0" width="747" style="WIDTH: 747px; HEIGHT: 26px">
<tr>
<td align=middle>
      <P align=center>

 &nbsp;<INPUT class=button onClick="selectItems('<%out.print(TaskInterfaceKey.PARAM_UPDATE_CUSTOMIZED_UPDATE_SELECT_LIST); %>');checkbeforSubmit();" type=button value=" Save " name=save></P></td>
</tr>
</table>
</form>

  </body>
</html>




