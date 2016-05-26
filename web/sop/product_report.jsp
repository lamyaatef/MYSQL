<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="com.mobinil.sds.core.utilities.Utility"
         import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import ="com.mobinil.sds.core.system.sop.requests.model.*"
         
        
       
       
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 
<%@page import="com.mobinil.sds.web.interfaces.sop.SOPInterfaceKey"%>
<%@page import="com.mobinil.sds.core.system.sop.schemas.model.ProductModel"%>
<%@page import="com.mobinil.sds.core.system.sop.requests.model.genProductModel"%><html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Product Report</title>
</head>
 <body>
 <center>    <H2>Product Report</H2>   </center>
 
  <form  name='Productform' id='Productform' action='' method='post'>  
  
  
  
  
  <script>
function checkBeforeSubmit()
{
  var REPORT = document.Productform.<%=SOPInterfaceKey.CONTROL_HIDDEN_REPORT_ID%>.value;
  var CHANNEL = document.Productform.<%=SOPInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID%>.value;

 
  if( REPORT == "")
  {
    alert("REPORT MUST NOT BE EMPTY");
    return;
  }
  if(CHANNEL=="")
  {
  alert("CHANNEL MUST NOT BE Empty");
 } 


else
{
	Productform.submit();
}

}




function setSearchValues(reports,channels)
{
  document.getElementById("<%=SOPInterfaceKey.CONTROL_HIDDEN_REPORT_ID%>").value = reports;
  document.getElementById("<%=SOPInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID%>").value = channels;
  

}


</script>

  <%

     HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
	
	 Vector provec = (Vector)objDataHashMap.get(SOPInterfaceKey.PRODUCT_VECTOR);
	 
	 Vector productAssigned = (Vector)objDataHashMap.get(SOPInterfaceKey.PRODUCT_ASSIGNED_VECTOR);
	 
	 Vector report = (Vector)objDataHashMap.get(SOPInterfaceKey.PRODUCT_REPORT);
	 
	 Vector channel= (Vector)objDataHashMap.get(SOPInterfaceKey.PRODUCT_CHANNEL);
	 
	 
	 String strUserID     =   (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
	 
	 
	 

	
     out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                " value=\""+"\">");   

     out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                " value=\""+strUserID+"\">"); 

            
%>  


  <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" 

border=1>
      <tr class=TableHeader>
        <td align='left' colspan=6>Search</td>
      </tr>
      
      <TR class=TableTextNote>
        
        
                <td align=middle>Report Type</td>
               <td align=middle>
              
                <%

 out.println("<select name='"+SOPInterfaceKey.CONTROL_HIDDEN_REPORT_ID+"' id='"+SOPInterfaceKey.CONTROL_HIDDEN_REPORT_ID+"'> ");
 out.println("<option value=\"\"></option>");
 
 int vecSize = report.size();
 for (int i=0;i<vecSize;i++){
 	reportModel model = (reportModel) report.get(i);
 	 String reportID = model.getREPORT_ID();
      String reportName = model.getREPORT_NAME();
   out.println("<option value="+reportID+">"+reportName+"</option>");  
 }
 out.println("</select>");
 %>
 </td>
               
        
        <td align=middle>Channel Type</td>
        <td align=middle>
        
        
                        <%

 out.println("<select name='"+SOPInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID+"' id='"+SOPInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID+"'> ");
 out.println("<option value=\"\"></option>");
 
 int vecSize1 = channel.size();
 for (int j=0;j<vecSize1;j++){
 	chanelModel model1 = (chanelModel) channel.get(j);
 	 String channelID = model1.getCHANNEL_ID();
      String chanelName = model1.getCHANNEL_NAME();
   out.println("<option value="+channelID+">"+chanelName+"</option>");  
 }
 out.println("</select>");
 %>
 

        </td>
      </TR>

      <tr>
        <td align='center' colspan=6>
        <%
     out.print("<INPUT class=button type=button value=\" Search \" name=\"Search\" id=\"Search\" onclick=\"document.Productform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SOPInterfaceKey.ACTION_SEARCH_PRODUCTS+"';"+
                "checkBeforeSubmit();\">");

        out.print("<INPUT class=button type=button value=\" Clear Values \" name=\"clear\" id=\"clear\" onclick=\"setSearchValues('','','');\">");          
      
        %>
        </td>
      </tr>
      
</TABLE>






     <% if(provec.size()!=0)
     {
 %>


   <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>     	 
   
       <tr class=TableHeader> 
    
     <td>Product Name</td>
   
      <td>Assigned</td>
     </tr>

     
 <%
   String  checkValue="";
 String CheckedproductNAME="";
 
 
for (int j=0;j<provec.size();j++){
        	genProductModel model2 = (genProductModel) provec.get(j);
        	
        	String productName=model2.getPRODUCT_NAME();
       	   // String Product = model2.getPRODUCT_NAME();
      
       	 for (int k=0;k<productAssigned.size();k++){
 			checkValue="";     	
         	genProductModel model3 = (genProductModel) productAssigned.get(k);
         	
         	CheckedproductNAME=model3.getPRODUCT_NAME();
         	
         	if(CheckedproductNAME.equals(productName))
 	       {
         		
         		  
         		System.out.println("name1 "+CheckedproductNAME + "   name 2 = "+productName);
         		
	     		checkValue="checked";
	     		break;
	      	
          	}
         	else
    			checkValue="";     	
         	

       	 }
       	
       		   
       		   
    
       	  
       	 
       	    
       %>
       <tr>
        
       
        <td> <%=productName %></td> 
        <td><input type="checkbox" name="<%=SOPInterfaceKey.CONTROL_HIDDEN_PRODUCT_CHECKED+ productName %>" <%=checkValue%> ></td>
        
  </tr>
   <%
}
     
   %>
 </TABLE>
 
 <br><br>
 
   <center> 
  <%
   out.print("<INPUT class=button type=button value=\" Submit \" name=\"Submit\" id=\"Submit\" onclick=\"document.Productform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SOPInterfaceKey.ACTION_SUBMIT_PRODUCTS+"';"+
                 "submit();\">");
 %>
 </center> 
 
 <%
     }
    String searchReport= "";
     String searchChannel = "";
  
    
    if(objDataHashMap.containsKey(SOPInterfaceKey.CONTROL_HIDDEN_REPORT_ID))
    { 
    	searchReport = (String)objDataHashMap.get(SOPInterfaceKey.CONTROL_HIDDEN_REPORT_ID);
    	if (searchReport==null){
    		searchReport="";
    		
    	}
    }
    if(objDataHashMap.containsKey(SOPInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID))
    {
    	searchChannel = (String)objDataHashMap.get(SOPInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID);
      	if (searchChannel==null){
      		searchChannel="";
    		
    	}
    }

 
    %>
 
 

 
    <%
if(objDataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_ACTION))
  {
	
    String strAction = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ACTION);
    if((strAction.compareTo(SOPInterfaceKey.ACTION_SEARCH_PRODUCTS)==0) ||  (strAction.compareTo(SOPInterfaceKey.ACTION_SUBMIT_PRODUCTS)==0) )
    {
      out.println("<script>setSearchValues('"+searchReport+"','"+searchChannel+"');</script>");
    }
  }
%>
 
 
  </form>
 </body>
 </html>
