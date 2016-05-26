<%-- 
    Document   : upload_rated_activity
    Created on : 22/12/2010, 12:13:01
    Author     : Ahmed Adel
--%>

<%@ page import ="javax.servlet.*"
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.commission.*"
         import="com.mobinil.sds.core.system.commission.dao.*"
         import="com.mobinil.sds.core.system.commission.factor.model.*"%>

<html>
    <head>
        <META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=windows-1252"/>
        <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
        <title>JSP Page</title>
    </head>
    <%   HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
        String appName = request.getContextPath();
        //String attach_seq=(String)dataHashMap.get(InterfaceKey.ATTACH_ID);
        String attach_seq="temp";
         String page_header="Upload Zip File";

        String ErrorMessage="";
      	ErrorMessage =(String) dataHashMap.get(InterfaceKey.HASHMAP_KEY_MESSAGE);
      	String user_id =(String) dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
        String fileId =(String)dataHashMap.get(CommissionInterfaceKey.RATED_FILE_ID);
        if(fileId==null)
            fileId="";


    %>


        <body>
        <center>
        <br>
        <h2>Upload Zip File Include Rated Activity</h2>
        <br>
        <br>
        <br>
        <form name="uploadzip" action="" method="post" enctype="multipart/form-data">

         <table style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="50%" border="1">
            
             <tr class=TableTextNote >
                 <td align="left">From</td>
                 <td align="left">Day 
                     <Select  name="<%=CommissionInterfaceKey.RATED_ACTIVITY_DATE_FROM%>" >
                         <option value="">--</option>
                         <% for (int i=1;i<=31;i++){ %>
                         <option value="<%=i%>"><%=i%></option>
                             <%}%>
                     </Select>
                 </td>
            
             
                 <td  align="left">Month
                 
                     <Select  name="<%=CommissionInterfaceKey.RATED_ACTIVITY_MONTH_FROM%>">
                         <option value="">--</option>
                         <option value="1">January</option>
                         <option value="2">February</option>
                         <option value="3">March</option>
                         <option value="4">April</option>
                         <option value="5">May</option>
                         <option value="6">June</option>
                         <option value="7">July</option>
                         <option value="8">August</option>
                         <option value="9">September</option>
                         <option value="10">October</option>
                         <option value="11">November</option>
                         <option value="12">December</option>
                     </Select>
                 </td>
             
                 <td align="left">Year
                
                     <Select  name="<%=CommissionInterfaceKey.RATED_ACTIVITY_YEAR_FROM%>">
                         <option value="">--</option>
                         <option value="2009">2009</option>
                         <option value="2010">2010</option>
                         <option value="2011">2011</option>
                         <option value="2012">2012</option>
                         <option value="2013">2013</option>
                         <option value="2014">2014</option>
                         <option value="2015">2015</option>
                         <option value="2016">2016</option>
                         <option value="2017">2017</option>
                         <option value="2018">2018</option>
                         <option value="2019">2019</option>
                         <option value="2020">2020</option>
                     </Select>
                 </td>
               </tr>
              
         </table>
                <br>
            <br>
                <table style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="50%" border="1">
              <tr class=TableTextNote>
                 <td  align="center">To</td>
           
                 <td align="left">Day
                 <td align="center">
                     <Select  name="<%=CommissionInterfaceKey.RATED_ACTIVITY_DATE_TO%>" >
                         <option value="">--</option>
                         <% for (int i=1;i<=31;i++){ %>
                         <option value="<%=i%>"><%=i%></option>
                             <%}%>
                     </Select>
                 </td>
             
           
                 <td align="left">Month
                     <Select  name="<%=CommissionInterfaceKey.RATED_ACTIVITY_MONTH_TO%>" >
                         <option value="">--</option>
                         <option value="1">January</option>
                         <option value="2">February</option>
                         <option value="3">March</option>
                         <option value="4">April</option>
                         <option value="5">May</option>
                         <option value="6">June</option>
                         <option value="7">July</option>
                         <option value="8">August</option>
                         <option value="9">September</option>
                         <option value="10">October</option>
                         <option value="11">November</option>
                         <option value="12">December</option>
                     </Select>
                 </td>
             
             
                 <td align="left">Year
                
                     <Select  name="<%=CommissionInterfaceKey.RATED_ACTIVITY_YEAR_TO%>" >
                <option value="">--</option>
                         <option value="2009">2009</option>
                         <option value="2010">2010</option>
                         <option value="2011">2011</option>
                         <option value="2012">2012</option>
                         <option value="2013">2013</option>
                         <option value="2014">2014</option>
                         <option value="2015">2015</option>
                         <option value="2016">2016</option>
                         <option value="2017">2017</option>
                         <option value="2018">2018</option>
                         <option value="2019">2019</option>
                         <option value="2020">2020</option>
                     </Select>
                 </td>
             </tr>
         </table>
                    <br>
                    <br>
          <table style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="50%" border="1">
              <tr class=TableTextNote>
                 

                   <td>Zip File</td>
                   <td>
                   <input type="file" name="myfile">
                   </td>
               
                 
                 <td>
                     <%if(CommissionDAO.isuploadingfile()==0||CommissionDAO.isuploadingfile()==-1){%>
                     <input type="button" value="upload" onclick="submitForm();">
                     <%}else{%>
                     <input type="button" disabled value="There's file currently uploading">
                     <%}%>
                 </td>
             </tr>
           </table>
        </form>
        </center>

    </body>
<script>
function submitForm()
{
   if(document.uploadzip.myfile.value.lastIndexOf(".zip")==-1)
    {
   alert("Please upload only Zip file");
   return false;
    }
    if(document.uploadzip.myfile.value==""){
               alert('Please specify file to attach.');
                    return false;
    }
    if(document.uploadzip.<%=CommissionInterfaceKey.RATED_ACTIVITY_DATE_FROM%>.value==""){
          alert('Please Enter from Day.');
                    return false;
    }
    if(document.uploadzip.<%=CommissionInterfaceKey.RATED_ACTIVITY_DATE_TO%>.value==""){
          alert('Please Enter to Day.');
                    return false;
    }
    if(document.uploadzip.<%=CommissionInterfaceKey.RATED_ACTIVITY_MONTH_FROM%>.value==""){
          alert('Please Enter from Month.');
                    return false;
    }
    if(document.uploadzip.<%=CommissionInterfaceKey.RATED_ACTIVITY_MONTH_TO%>.value==""){
          alert('Please Enter to Month.');
                    return false;
    }
    if(document.uploadzip.<%=CommissionInterfaceKey.RATED_ACTIVITY_YEAR_FROM%>.value==""){

     alert('Please specify file from Year.');
                    return false;
    }
    if(document.uploadzip.<%=CommissionInterfaceKey.RATED_ACTIVITY_YEAR_TO%>.value==""){
          alert('Please specify file Year to .');
                    return false;
    }


  document.uploadzip.action = "<%=appName%>/commission/uploadzipfile.jsp?attach_id=<%=attach_seq%>"+'&'+'<%=CommissionInterfaceKey.RATED_ACTIVITY_DATE_FROM%>='+document.uploadzip.<%=CommissionInterfaceKey.RATED_ACTIVITY_DATE_FROM%>.value
  +'&'+"<%=CommissionInterfaceKey.RATED_ACTIVITY_DATE_TO%>="+document.uploadzip.<%=CommissionInterfaceKey.RATED_ACTIVITY_DATE_TO%>.value+"&"+"<%=CommissionInterfaceKey.RATED_ACTIVITY_MONTH_FROM%>="+document.uploadzip.<%=CommissionInterfaceKey.RATED_ACTIVITY_MONTH_FROM%>.value
  +"&"+"<%=CommissionInterfaceKey.RATED_ACTIVITY_MONTH_TO%>="+document.uploadzip.<%=CommissionInterfaceKey.RATED_ACTIVITY_MONTH_TO%>.value+"&"+"<%=CommissionInterfaceKey.RATED_ACTIVITY_YEAR_FROM%>="+document.uploadzip.<%=CommissionInterfaceKey.RATED_ACTIVITY_YEAR_FROM%>.value+"&"+"<%=CommissionInterfaceKey.RATED_ACTIVITY_YEAR_TO%>="+document.uploadzip.<%=CommissionInterfaceKey.RATED_ACTIVITY_YEAR_TO%>.value+'&'+'user_id='+<%=user_id%>
  document.uploadzip.submit();
  
}
 </script>
</html>

