<%--
    Document   : Attachment
    Created on : 05/01/2009, 03:09:04 م
    Author     : Medhat Osama
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import=" com.mobinil.sds.web.interfaces.cam.*"
 
         import ="com.mobinil.sds.web.interfaces.InterfaceKey"
      
         %>

<%@page import="com.mobinil.sds.web.interfaces.dm.DMInterfaceKey"%><html>
    <head>
        <%
        
        HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
        String appName = request.getContextPath();
        String attach_seq=(String)dataHashMap.get(InterfaceKey.ATTACH_ID);
         String page_header="Upload Zip File";
         
        String ErrorMessage="";
      	ErrorMessage =(String) dataHashMap.get(InterfaceKey.HASHMAP_KEY_MESSAGE);
      	String user_id =(String) dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
      	
      	// if(dataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_MESSAGE))
    	//  {

    	  //  out.println("<script>alert('"+ErrorMessage+"');</script>");
    	//  }
 %>
        


        
        
        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Import File</title>
        <script language="JavaScript">
            var file_name;
            function checkBefore()
            {
                var input = document.getElementById("file");
                var split_input=input.value.split("\\");
                file_name=split_input[split_input.length-1];
                var file_type_arr = file_name.split(".");
                var file_type = file_type_arr[file_type_arr.length-1];
                if(file_type != "zip")
                {
                    alert('Please select ZIP file.');
                    return;
                }
                if(file_path=="")
                    alert('Please specify file to attach.');

               

                
                else{
                    var year = document.getElementById('<%=DMInterfaceKey.CONTROL_SELECT_UPLOAD_ZIP_YEAR%>');
                    var month = document.getElementById('<%=DMInterfaceKey.CONTROL_SELECT_UPLOAD_ZIP_MONTH%>');
                    var user_id=document.getElementById ( '<%=InterfaceKey.HASHMAP_KEY_USER_ID%>').value=<%=user_id%>; 
                    document.UploadFile.attach_id.value='<%=attach_seq%>';
               
                   // document.UploadFile.year_hidden.value=year.value;
                   // document.UploadFile.month_hidden.value=month.value;
                    document.UploadFile.action = "<%=appName%>/dm/upload_file.jsp?attach_id=<%=attach_seq%>&user_id="+user_id+"+&year_hidden="+year.value+"&month_hidden="+month.value;
                    document.UploadFile.submit();
                }
             
            }
            var num=1;
            var file_path="";
            function CreateNewFileInput(inputFile)
            {
                file_path=inputFile.value;
                split_input=file_path.split("\\");
                file_name=split_input[split_input.length-1]
                nameID=inputFile.name;
 
                eval("document.UploadFile."+nameID+".name='"+file_name+"';");
                eval("document.UploadFile."+nameID+".id='"+file_name+"';");
             
            }


            function getYear()
            {
            	return document.getElementById('<%=DMInterfaceKey.CONTROL_SELECT_UPLOAD_ZIP_YEAR%>').value;
            }

            function getMonth()
            {
            	return document.getElementById('<%=DMInterfaceKey.CONTROL_SELECT_UPLOAD_ZIP_MONTH%>').value;
            }
            function setMonth()
            {
                var selectedIndex=document.getElementById('<%=DMInterfaceKey.CONTROL_SELECT_UPLOAD_ZIP_MONTH%>').selectedIndex;
                var value1=document.getElementById('<%=DMInterfaceKey.CONTROL_SELECT_UPLOAD_ZIP_MONTH%>').options[selectedIndex].value;
               
            	document.getElementById('year_hidden').value=value1;
            }
            function setYear()
            {
                var selectedIndex=document.getElementById('<%=DMInterfaceKey.CONTROL_SELECT_UPLOAD_ZIP_YEAR%>').selectedIndex;
                var value2=document.getElementById('<%=DMInterfaceKey.CONTROL_SELECT_UPLOAD_ZIP_YEAR%>').options[selectedIndex].value;
              
            	document.getElementById('year_hidden').value=value2;
            }
        </script>
    </head>

    <LINK REL=STYLESHEET TYPE="text/css" HREF="<%out.print(appName);%>/resources/css/Template1.css">

    <body>
        <form ENCTYPE="multipart/form-data" action="" name="UploadFile" id="UploadFile" method="post">
        <center><h2> <%=page_header%></h2></center>
            <table style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="70%" border=1 align="center">
                <tr class=TableHeader>
                    <td>
                  
                <font size="4" >  <b>Upload</b> </font> <br></td><td></td></tr>
                  
                <br>
                <tr>
                <td width="15%">Year:</td>
                <td>
                <select name="<%=DMInterfaceKey.CONTROL_SELECT_UPLOAD_ZIP_YEAR %>" id="<%=DMInterfaceKey.CONTROL_SELECT_UPLOAD_ZIP_YEAR %>">
                <option></option>
                <%for(int i=2000; i<2020;i++){ %>
                <option value="<%=i %>"><%=i %></option>
                <%} %>
                </select>
                </td>
                </tr>
                
                <tr>
                <td>Month:</td>
                <td>
                <select onchange="setMonth()" name="<%=DMInterfaceKey.CONTROL_SELECT_UPLOAD_ZIP_MONTH %>" id="<%=DMInterfaceKey.CONTROL_SELECT_UPLOAD_ZIP_MONTH %>">
                <option></option>
                <%
                String [] months = {"","Jan", "Feb", "March", "April", "May", "June", "July", "August", "Sept", "Oct", "Nove", "Dec"};
                for(int i=1; i<months.length;i++){
                	
                    
                   
              
                	%>
                	
                <option value="<%=i%>"><%=months[i]%></option>
                <%} %>
                </select>
                </td>
                </tr>
                <tr class="TableTextNode">

                    <td >
                        <div id="divText"> <b>File path:</b></div>
                    </td>
                    <td>

                        <div id="divFile">
                            <input type="file" name="file" id="file" size="45" onchange="CreateNewFileInput(this);"/>
                        </div>

                    </td>

                </tr>
                

                <tr  class="TableTextNode">
                    <td >
                        <div id="divText1"></div>
                    </td>
                    <td>
                        <div id="divFile1"></div>
                    </td>
                </tr>
            </table>
            <br>
            <input type="button" class="button"  value="Upload" onclick="checkBefore();"/>
            <input type="hidden" name="attach_id" id="attach_id"/>
            <input type="hidden" name="year_hidden" id="year_hidden" value=""/>
            <input type="hidden" name="month_hidden" id="month_hidden" value=""/>
            <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_USER_ID%>" id="<%=InterfaceKey.HASHMAP_KEY_USER_ID%>"  value=""/>

        </form>
    </body>
</html>
