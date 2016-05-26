<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="com.mobinil.sds.core.utilities.Utility"
         import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.ccm.*"
          import="com.mobinil.sds.web.interfaces.ccm.CCMInterfaceKey"

         import="com.mobinil.sds.core.system.ccm.*"
%>

<%@page import="com.mobinil.sds.core.system.ccm.addAndWin.model.AddAndWinShopAssignmentModel"%>
<%@page import="com.mobinil.sds.core.system.ccm.addAndWin.model.shopModel"%>
<%@page import="com.mobinil.sds.core.facade.handlers.CCMHandler"%>
<% 
HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
  Vector addAndWin = (Vector)objDataHashMap.get(CCMInterfaceKey.VECTOR_ADD_AND_WIN);
  Vector shops = (Vector)objDataHashMap.get(CCMInterfaceKey.VECTOR_SHOPS);
  
     String Name =(String)objDataHashMap.get(CCMInterfaceKey.INPUT_SEARCH_TEXT_ADDANDWIN_NAME);
	String Code =(String)objDataHashMap.get(CCMInterfaceKey.INPUT_SEARCH_TEXT_ADDANDWIN_CODE);
  //int rowNum =0;
  int count = 0;
  String cur_page_num = (String)objDataHashMap.get(CCMInterfaceKey.INPUT_HIDDEN_ROW_NUM);
 
 String Search_Name=(String)objDataHashMap.get(CCMInterfaceKey.INPUT_SEARCH_TEXT_ADDANDWIN_NAME);
 String Search_Code=(String)objDataHashMap.get(CCMInterfaceKey.INPUT_SEARCH_TEXT_ADDANDWIN_CODE);

 if(Search_Name == null || Search_Name.equalsIgnoreCase("null"))
	 Search_Name="";
 
 if(Search_Code == null || Search_Code.equalsIgnoreCase("null"))
	 Search_Code="";
 if(objDataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_ACTION))
   {
     String strAction = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ACTION);
 	out.println("<script> alert('"+Search_Name+"');   </script>");
   System.out.println("search  isssssssss  " +Search_Name);
     
     if(strAction.compareTo(CCMInterfaceKey.ACTION_SEARCH_ADD_AND_WIN)==0)
     {
   	out.println("<script> alert('"+Search_Name+"');   </script>");
       out.println("<script> setSearchValues('"+Search_Name+"','"+Search_Code+"');</script>");
     }
   }
 %>

<html>
<head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
      <SCRIPT language=JavaScript src="../resources/js/calendar.js" type=text/javascript></SCRIPT>     
    </head>
<body>


<script>
var page_counter = <%=cur_page_num%>;
  function setSearchValues(name,code)
  {
    document.getElementById("<%=CCMInterfaceKey.INPUT_SEARCH_TEXT_ADDANDWIN_NAME%>").value = name;
    document.getElementById("<%=CCMInterfaceKey.INPUT_SEARCH_TEXT_ADDANDWIN_CODE%>").value = code;


  }
  function submitPaging(mode)
  {
	 
	 if(mode == 'inc')
	 {
	    
	  	 page_counter++;
	 }
	 else
	 {
		page_counter--;
	 }
	  	  
	  document.CCMform.<%=CCMInterfaceKey.INPUT_HIDDEN_ROW_NUM%>.value = page_counter++;;
	  document.CCMform.submit();
}

  function submitSaving()
  {
  	document.CCMform.<%=CCMInterfaceKey.INPUT_HIDDEN_ROW_NUM%>.value = page_counter++;;
  	  document.CCMform.submit();
  }
  function submitSearch()
  {
	  document.CCMform.<%=CCMInterfaceKey.INPUT_HIDDEN_ROW_NUM%>.value = 0;
	/// if( page_counter!=0){
//
	//	  page_counter ==0;
		///  document.CCMform.<%=CCMInterfaceKey.INPUT_HIDDEN_ROW_NUM%>.value = page_counter;
		  
		  //document.CCMform.submit();
		// }
	  //else {
	  //document.CCMform.<%=CCMInterfaceKey.INPUT_HIDDEN_ROW_NUM%>.value = page_counter++;
 
  	 document.CCMform.submit();
	  }
	//  }
  </script>

  <CENTER>
      <H2> Add And Win _Shop Assignment </H2>
    </CENTER>
    <form name='CCMform' id='CCMform' action='' method='post'>
      <%

out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");   
      


  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">"); 
  
  

  out.println("<input type=\"hidden\" name=\""+CCMInterfaceKey.INPUT_HIDDEN_ROW_NUM+"\""+
                  " value=\"0\">");

  out.println("<input type=\"hidden\" name=\""+CCMInterfaceKey.INPUT_HIDDEN_COUNT+"\""+
                  " value=\""+count+"\">");
  
  
  

%>

 <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" 

border=1>
      <tr class=TableHeader>
        <td align='left' colspan=6>Search</td>
      </tr>
      
      <TR class=TableTextNote>
        <td align=middle>Name </td>
        <td align=middle><input type='text' name='<%=CCMInterfaceKey.INPUT_SEARCH_TEXT_ADDANDWIN_NAME%>' id='<%=CCMInterfaceKey.INPUT_SEARCH_TEXT_ADDANDWIN_NAME%>' value="<%=Search_Name %>"></td>
        
                <td align=middle>Code</td>
        <td align=middle><input type='text' name='<%=CCMInterfaceKey.INPUT_SEARCH_TEXT_ADDANDWIN_CODE%>' id='<%=CCMInterfaceKey.INPUT_SEARCH_TEXT_ADDANDWIN_CODE%>'  value="<%=Search_Code %>"></td>
        </TR>  
   
    <tr>
        <td align='center' colspan=6>
        <%
        out.print("<INPUT class=button type=button value=\" Search \" name=\"searchAddandWin\" id=\"searchAddandWin\" onclick=\"document.CCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+CCMInterfaceKey.ACTION_SEARCH_ADD_AND_WIN+"';"+
        "submitSearch();\">");
        %>
        
        
        
        <%
        out.print("<INPUT class=button type=button value=\" Clear Values \" name=\"clear\" id=\"clear\" onclick=\"setSearchValues('','');\">");          
      
        %>
        </td>
      </tr>
   
   
  
 
   
</table>

<br>
<br>

<TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1> 
      <tr class=TableHeader>
      
      <td>Code</td>
      <td>ADD and Win</td>
        
      
        <td>Shops</td>
      
      </tr>
    
      <%
      if(addAndWin != null){
      	for (int i =0;i<addAndWin.size();i++)
      	{
      		AddAndWinShopAssignmentModel addAndWinModel=(AddAndWinShopAssignmentModel)  addAndWin.get(i);
      	    String  addAndWinCode=addAndWinModel.getDCM_CODE();
      		String  addAndWinId = addAndWinModel.getDCM_ID();
      		String addAndWinIdName = addAndWinModel.getDCM_NAME();
      		String shopId_List=addAndWinModel.getSHOP_ID();
      		if(shopId_List == null){shopId_List = "";}
      		//System.out.println("The shop id from ADD and win model issssss " +shopId_List  );
      	%>
      	<tr class=<%=InterfaceKey.STYLE[i%2]%>>
      	   <td><%=addAndWinCode%></td>
      	    <td><%=addAndWinIdName%></td>
      	 	
     
  	
      	 <td>
      	 <select  name='<%=CCMInterfaceKey.CONTROL_HIDDEN_SHOP_ID+addAndWinId%>' id='<%=CCMInterfaceKey.CONTROL_HIDDEN_SHOP_ID+addAndWinId%><%=addAndWinId%>'>
      	<option value=''></option>
      	 
      	 
          <%
  
        int vecSize = shops.size();
        
        for (int j=0;j<vecSize;j++){
         	shopModel model = (shopModel) shops.get(j);
       	    String shopIdX = model.getDCM_ID();
       	    //System.out.println("The normal shop id issssss " + shopIdX);
            String shopNameX = model.getDCM_NAME();
            String option_selected="";
             if(shopIdX.compareTo(shopId_List)==0)
     		{
            	// System.out.println("Righhhhhhhhhhhhhht");
     			option_selected="selected";
     		}
        
             %>
         <option value="<%=shopIdX%>" <%=option_selected %>> <%=shopNameX%></option>
             <% 
        }
        %>
        </select>
     

 </td>

 </tr>
         <%} %>
   
      </TABLE>
      <br><br>
     

<table width=95% border=0 cellspacing=0 cellpadding=0>
 <tr>
 

 <td align = 'center'>
 
 <INPUT  style="display:none" id="button888" class="button" type="button" value="<<" name="button3" onclick="document.CCMform.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = '<%=CCMInterfaceKey.ACTION_SEARCH_ADD_AND_WIN%>'; submitPaging('dec')">

&nbsp&nbsp 
  
  <script type="text/javascript">
 var pre = document.getElementById("button888");
 if(page_counter == 0)
 {
	 pre.style.display = "none";
 }
 else
 {
	 pre.style.display = "";
 }

 </script>
 
 
 
 
 

 <INPUT  style="" id="button889" class="button" type="button" value=">>" name="button3" onclick="document.CCMform.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = '<%=CCMInterfaceKey.ACTION_SEARCH_ADD_AND_WIN%>'; submitPaging('inc')">
 
<%
int totalRows = (CCMHandler.rowNum+1)*49;
Utility.logger.debug("The total rows issssssssssss " + totalRows);
  if(addAndWin.size()>=49)
  {
%>
<script type="text/javascript">
 	var pre = document.getElementById("button889");
	 pre.style.display = "";
 </script>


<%  
  }
  else{
    %>
    
    <script type="text/javascript"> 
    var pre = document.getElementById("button889");
	 pre.style.display = "none";
 </script> 
<%} %>
    </td>
    <tr>
   </table>
    
<br>
   <center>
<%
           
	      //    System.out.println("The action issssssssssssssss " + nextAction);
	
out.print("<INPUT class=button type=button value=\"Save \" name=\"Save\" id=\"Save\" onclick=\"document.CCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+CCMInterfaceKey.ACTION_ASSIGN_ADD_AND_WIN_TO_SHOP+"';"+
" submitSaving();\">");
%> 
      </center>   
 <%} %>   



 
 

      
</form>
</body>
</html>