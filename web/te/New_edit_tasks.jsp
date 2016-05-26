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
     HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
     TaskDTO taskInfo = (TaskDTO) objDataHashMap.get(TaskInterfaceKey.DTO_TASK) ;

 %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
    <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">    
        <SCRIPT language=JavaScript src="<%out.print(appName);%>/resources/js/validation.js" type="text/javascript"></SCRIPT>
    <SCRIPT language=JavaScript>

    
    var ids=new Array('x_2');

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
              if (hh.options[i].value=='x_2'){
              document.getElementById(hh.options[i].value).style.display = 'block';
              
              }
            }
              
              
              }  

    }
    function defaultValues() 
    {
    document.getElementById('start_hour').selectedIndex =0;
    document.getElementById('end_hour').selectedIndex =0;
    }
function loadValues(control,value)
  {
    document.formTaskDetails.control.value=value;
  }    
function checkbeforSubmit()
{		
  if(NonBlank(document.formTaskDetails.<%out.print(TaskInterfaceKey.CONTROL_TEXT_TASK_NAME);%>, true, 'Task Name'))
  {
      <%if (taskInfo.getTaskName().compareTo("")==0 || taskInfo.getTaskName() == null){%>
	  var startHour = document.formTaskDetails.start_hour.value;
	  var endHour = document.formTaskDetails.end_hour.value;
	  var selectType = document.formTaskDetails.selectTskType.value;
	  if (parseInt(startHour) >=  parseInt(endHour) && selectType=='x_2' )
	  {
	  	alert('Please check tast start and end hour.');
	  }
	  else
	  {checkBeforePrint();}
              <%}else{%>
                  checkBeforePrint();
              <%}%>
	  
  } 
return false;
}

function checkBeforePrint(){

document.formTaskDetails.<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>.value='<%out.print(TaskInterfaceKey.ACTION_SAVE_TASK);%>'
		formTaskDetails.submit();

}
  
</SCRIPT>
  </head>
  <body>
        <CENTER>
<H2>  New&nbsp;Task </H2>
</CENTER>
 <form action="../servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="formTaskDetails" id="formTaskDetails" method="post" >

<%




  
 String taskName="";
 String taskDesc="";






out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");
out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+"\">");                  

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
if (taskInfo.getTaskName().compareTo("")==0 || taskInfo.getTaskName() == null){
out.println("<TR>");
out.println("<TD class=TableTextNote width=\"20%\" noWrap>Task&nbsp;Type </TD>");
out.println("<TD class=TableTextNote colSpan=4><select onChange=\"switcid(this);defaultValues();\" name=\"selectTskType\"><option selected value=\"x_1\">Immediatly</option><option  value=\"x_2\">Between two hours</option></select>");
out.println("<div id=\"x_2\" style=\"display:none;\">");
out.println("From &nbsp;&nbsp;<select name=\"start_hour\">");
for(int i=0;i<24;i++)
{
	if (i<10)
		out.println("<option value="+i+">0"+i+"</option>");
	else 
		out.println("<option value="+i+">"+i+"</option>");
}
out.println("</select>");

out.println("To &nbsp;&nbsp;<select name=\"end_hour\">");
for(int i=0;i<24;i++)
{
	if (i<10)		
		out.println("<option value="+i+">0"+i+"</option>");
	else 
		out.println("<option value="+i+">"+i+"</option>");
}
out.println("</select></div>");
out.println("</TD></TR>");

}

out.println("<TR class=TableTextNote><TD colspan=5 class=TableTextNote><font size=1>* indicates mandatory fields.&nbsp;</font></TD></TR></TABLE></TABLE>");

out.println("<input type=\"hidden\" name=\""+TaskInterfaceKey.CONTROL_HIDDEN_TASK_ID+"\""+
                  " value=\""+"\">");


        taskName = taskInfo.getTaskName();
        String taskId = taskInfo.getTaskId()+"";
        taskDesc = taskInfo.getTaskDescription();

        if (taskDesc==null){taskDesc="";}
        out.println("<script>");
        out.println("document.formTaskDetails."+TaskInterfaceKey.CONTROL_TEXT_TASK_NAME+".value='"+taskName+"';");
        out.println("document.formTaskDetails."+TaskInterfaceKey.CONTROL_TEXT_TASK_DESCRIPTION+".value='"+taskDesc+"';");
        out.println("document.formTaskDetails."+InterfaceKey.HASHMAP_KEY_USER_ID+".value='"+userId+"';");        
        out.println("document.formTaskDetails."+TaskInterfaceKey.CONTROL_HIDDEN_TASK_ID+".value='"+taskId+"';");        
        out.println("</script>");
        



%>

<table  cellspacing="2" cellpadding="1" border="0" width="747" style="WIDTH: 747px; HEIGHT: 26px">
<tr>
<td align=middle>
      <P align=center>

 &nbsp;<INPUT class=button onclick="checkbeforSubmit();" type=button value=" Save " name=save></P></td>
</tr>
</table>
</form>

  </body>
</html>




