<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import=" com.mobinil.sds.web.interfaces.cam.*"
         import=" com.mobinil.sds.core.system.cam.accrual.model.*"
         import ="com.mobinil.sds.core.system.cam.accrual.dao.*"
         import ="com.mobinil.sds.web.interfaces.InterfaceKey"
         import ="com.mobinil.sds.core.system.cam.common.model.*"
         %>
<%
        String appName = request.getContextPath();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String form_name = "accrual_mgt_form";
        String page_header="Accrual List";
//get data hashmap specidifed b4 in kpihandler
        
        HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
        Vector all_accrual = (Vector) dataHashMap.get(AccrualInterfaceKey.VECTOR_ALL_ACCRUAL);
        Vector all_accrual_status = (Vector) dataHashMap.get(AccrualInterfaceKey.VECTOR_ALL_ACCRUAL_STATUS);
         Vector all_dcm_channel = (Vector) dataHashMap.get(AccrualInterfaceKey.VECTOR_ALL_DCM_CHANNEL);
        String[] months_names={"January","February","March","April","May","June","July","August","September","October","November","December"};
        
%>
<script language="JavaScript">
 
function submitEdit(rowId)
{
document.<%=form_name%>.<%=AccrualInterfaceKey.CONTROL_HIDDEN_ACCRUAL_ID%>.value=rowId;
document.<%=form_name%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=AccrualInterfaceKey.ACTION_EDIT_ACCRUAL%>";
document.<%=form_name%>.submit();
}
function submitUpdateStatuses()
{
document.<%=form_name%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=AccrualInterfaceKey.ACTION_UPDATE_ACCRUAL_STATUSES%>";
document.<%=form_name%>.submit();
}
function submitNew()
{
document.<%=form_name%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=AccrualInterfaceKey.ACTION_NEW_ACCRUAL%>";

document.<%=form_name%>.submit();
}
function submitSearch()
{
  document.<%=form_name%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=AccrualInterfaceKey.ACTION_SEARCH_ACCRUAL%>";
  document.<%=form_name%>.submit();
}

function toggle(item1)
{
  divs=document.getElementsByTagName("DIV");
  for (i=0;i<divs.length;i++) 
  {
    if(divs[i].id != item1 && divs[i].id != 'listofusers')
    {
      divs[i].style.display="none";
    }
  }
  obj=document.getElementById(item1);
  if (obj!=null)
  {
    visible = obj.style.display!="none";
    if (visible) {
      obj.style.display="none";
    } 
    else {
       obj.style.display="";
    }
  }
}
</script>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
        <LINK REL=STYLESHEET TYPE="text/css" HREF="<%out.print(appName);%>/resources/css/Template1.css">
        <SCRIPT language=JavaScript src="<%out.print(appName);%>/resources/js/validation.js" type="text/javascript"></SCRIPT>
    </head>
    <body>
        <center><h2> <%=page_header%></h2></center>

        <%
         
        %>
        <form action="<%=appName%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="<%=form_name%>" method="post">
            <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_ACTION%>" value="0">
            <input type="hidden" name="<%=AccrualInterfaceKey.CONTROL_HIDDEN_ACCRUAL_ID%>" value="-1">   

             <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
              <TR class=TableHeader>
              <TD>Accrual name</TD>
              <TD><input type="text" name="<%=AccrualInterfaceKey.CONTROL_TEXT_SEARCH_NAME%>"></TD>
              <TD>Status</TD>
              <TD>
                  <%
                   if(all_accrual_status!=null && all_accrual_status.size()>0)
                   {%>
                   <select name="<%=AccrualInterfaceKey.CONTROL_SELECT_ALL_ACCRUAL_STATUS%>" id="<%=AccrualInterfaceKey.CONTROL_SELECT_ALL_ACCRUAL_STATUS%>">
                   <option>
                   <%
                
                   for(int j=0;j<all_accrual_status.size();j++){
                    StatusModel status = (StatusModel)all_accrual_status.get(j);
                    
                   %>
                   <option   value="<%=status.getStatus_id()%>"><%=status.getStatus_name()%></option>
                   <%}%>
                   </select>
                   <%}%>
              </TD>
              <TD>Channel</TD>
              <td>
                   <%
                   if(all_dcm_channel!=null && all_dcm_channel.size()>0)
                   {%>
                   <select name="<%=AccrualInterfaceKey.CONTROL_SELECT_ACCRUAL_CHANNEL%>" id="<%=AccrualInterfaceKey.CONTROL_SELECT_ACCRUAL_CHANNEL%>">
                   <option>
                   <%
                  
                   for(int j=0;j<all_dcm_channel.size();j++){
                    DCMChannelModel channel = (DCMChannelModel)all_dcm_channel.get(j);
                   %>
                   <option  value="<%=channel.getChannel_id()%>"><%=channel.getChannel_name()%></option>
                   <%}%>
                   </select>
                   <%}%>
             </td>
              </TR>
               <tr>
                    <td align=center colspan="7">
                        <input class=button type="button" name="new" value="Search"
                               onclick="submitSearch();">                    
                    </td>                     
                </tr>
             </TABLE>
             
             
             
             
            
            <%
                if(all_accrual != null && all_accrual.size()>0){
            %>
             <div name="listofusers" id="listofusers" style="display:none">
             <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
              <TR class=TableHeader>
              <td>Name</td>
              <td>Channel</td>
              <td>Value</td>
              <td>Description</td>
              <td>Status</td>
              <td>Edit</td>
              </TR>
             
             <%
             for(int i=0;i<all_accrual.size();i++){
              AccrualModel item = (AccrualModel)all_accrual.get(i);
             %>
             <TR class=TableTextNote>
             <td><a href="#divname<%=item.getAccrual_id()%>" onclick="javascript:toggle('<%=item.getAccrual_id()%>');"><%=item.getAccrual_name()%></a></td>
             <td><%=item.getChannel_name()%></td>
             <td><%=item.getAccrual_value()%></td>
             <td><%=item.getAccural_desc()%></td>
             <td>
             <%
             if(all_accrual_status!=null && all_accrual_status.size()>0)
             {%>
             <select name="<%=AccrualInterfaceKey.CONTROL_SELECT_ALL_ACCRUAL_STATUS+item.getAccrual_id()%>" id="<%=AccrualInterfaceKey.CONTROL_SELECT_ALL_ACCRUAL_STATUS+item.getAccrual_id()%>">
             <%
             String status_selected="";
             for(int j=0;j<all_accrual_status.size();j++){
              StatusModel status = (StatusModel)all_accrual_status.get(j);
              if(status.getStatus_name().equalsIgnoreCase(item.getStatus_name()))
               status_selected="selected";
              else 
               status_selected="";
             %>
             <option <%=status_selected%> value="<%=status.getStatus_id()%>"><%=status.getStatus_name()%></option>
             <%}%>
             </select>
             <%}%>
             </td>
             <td><input class="button" type="button" value="Edit" onclick="submitEdit(<%=item.getAccrual_id()%>)"></td>
             </TR>
         <%}%>
    </TABLE>
    </div>
    <%
                if(all_accrual != null && all_accrual.size()>0)
                	  for(int i=0;i<all_accrual.size();i++){
                          AccrualModel item = (AccrualModel)all_accrual.get(i);
                          String accrual_24_in_out_values_str = item.getAccrual_24_in_out_values_str();
                          if(accrual_24_in_out_values_str!=null){
                          String[] in_out_pairs = accrual_24_in_out_values_str.split("#");
                          %>
               
    <a name="divname<%=item.getAccrual_id()%>" id="divname<%=item.getAccrual_id()%>">
   
    <div style="DISPLAY: none" id="<%=item.getAccrual_id()%>" name="<%=item.getAccrual_name()%>">
    <table style="BORDER-COLLAPSE: collapse" cellSpacing="2" cellPadding="1" width="70%" border="1">
     <TR class=TableHeader>
     <td width="5%">Month Number</td>
     <td width="20%">Month</td>
     <td>Accrual Value In</td>
     <td>Accrual Value Out</td>
     </TR>
     <% 
     
     for(int j=0;j<in_out_pairs.length;j++){
    	if(in_out_pairs[j].equalsIgnoreCase("null"))
    	{
    		in_out_pairs[j]="0";
    	}
     }
     for(int j=0;j<in_out_pairs.length;j++){
    		if(j%2==0)
    		{
    		%>
    		<tr><td><%=(j/2)+1%></td><td><%=months_names[j/2]%></td><td><%=in_out_pairs[j] %></td><td><%=in_out_pairs[j+1] %></td>
    		<%}%>
    		
    		<%
    	
    	
     }
                          }
    	 %>
     
    </table>
    </div>
    </a>
       <%}}%>
        <br>
<table  cellspacing="2" cellpadding="1" border="0" width="100%">
                <tr>
                    <td align=center>
                        <input class=button type="button" name="new" value="New Accrual"
                               onclick="submitNew();">
                    &nbsp
                        <input class=button type="button" name="new" value="Update status"
                               onclick="submitUpdateStatuses();">
                    </td>
                     
                </tr>
            </table>

<script>var obj = document.getElementById('listofusers');obj.style.display='';</script>
        </form>
    </body>
</html>
 