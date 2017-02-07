<%-- 
    Document   : search_gen_user
    Created on : Nov 10, 2010, 11:16:41 AM
    Author     : AHMED SAFWAT
--%>


<%@page contentType="text/html" pageEncoding="windows-1252"
        import="com.mobinil.sds.web.interfaces.*"
        import="com.mobinil.sds.web.interfaces.scm.*"
        import="com.mobinil.sds.core.system.scm.model.*"
        import="com.mobinil.sds.core.system.dcm.region.model.*"
        import="com.mobinil.sds.core.system.dcm.user.model.*"
        import="com.mobinil.sds.core.system.sa.persons.model.PersonModel"
        import="java.util.*"
        %>
<%
            HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
            Vector<PersonModel> searchResults=new Vector();
            String appName = request.getContextPath();
            String formName = "genUserSearchForm";
            String userId = (String) dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            searchResults=(Vector)dataHashMap.get(SCMInterfaceKey.VECTOR_GEN_USER_SEARCH_RESULTS);
            String confMessage=(String)dataHashMap.get(SCMInterfaceKey.CONFIRMATION_MESSAGE);
            String personName=(String)dataHashMap.get(SCMInterfaceKey.PERSON_NAME);
            personName=personName==null?"":personName;
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
        <LINK REL=STYLESHEET TYPE="text/css" HREF="<%=appName%>/resources/css/Template1.css">
        <SCRIPT language=JavaScript src="<%=appName%>/resources/js/validation.js" type="text/javascript"></SCRIPT>
        <SCRIPT language=JavaScript src="<%=appName%>/resources/js/sorttable.js" type="text/javascript"></SCRIPT>

        <title>Rep Management</title>

        <script language="JavaScript">

            function selectPerson(personId,personName,personEmail,personAddress){
            document.<%=formName%>.<%=SCMInterfaceKey.PERSON_ID%>.value=personId;
            document.<%=formName%>.<%=SCMInterfaceKey.PERSON_NAME%>.value=personName;
            document.<%=formName%>.<%=SCMInterfaceKey.PERSON_EMAIL%>.value=personEmail;
            document.<%=formName%>.<%=SCMInterfaceKey.PERSON_ADDRESS%>.value=personAddress;
            document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_NEW_REP_SUP%>";
            document.<%=formName%>.submit();

            }


            function submitSearchForm(){

            document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_SEARCH_GEN_USER%>";
            document.<%=formName%>.submit();

            }

            function doBack(){
                document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_VIEW_REP_MANAGEMENT%>";
                document.<%=formName%>.submit();
            }


       </script>
    </head>
    <body>
                <div align="center">
            <br>
            <br>
            <h2>Search User</h2>
            <br>
            <br>

            <form action="<%=appName%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="<%=formName%>" method="post">

            <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_ACTION%>" value="0">
            <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_USER_ID%>" value="<%=userId%>">
            <input type="hidden" name="<%=SCMInterfaceKey.PERSON_ID%>" value="-1">
            <input type="hidden" name="<%=SCMInterfaceKey.PERSON_EMAIL%>" value="-1">
            <input type="hidden" name="<%=SCMInterfaceKey.PERSON_ADDRESS%>" value="-1">
                <table style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="80%" border="1">
                    <tr class=TableTextNote>
                        <td nowrap align=center >Name</td>
                        <td align="center"><input type="text" name="<%=SCMInterfaceKey.PERSON_NAME%>" value="<%=personName%>"></td>
                    </tr>
                    <tr>
                        <td colspan="4" align="center">
                            <input type="button" class="button" value="Search" onclick="submitSearchForm();">
                            &nbsp;
                            <input type="button" class="button" value="Back" style="font-size: 11px;font-family: tahoma;line-height: 15px" onclick="doBack();">
                        </td>
                    </tr>
                </table>
            </form>

        <%
       if(searchResults!=null){

           if(searchResults.size()==0){

              out.print("<font style='font-size: 11px;font-family: tahoma;line-height: 15px'>No Results for this name.</font>");

               }else{


        %>

        <br>

                <table class="sortable" style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="80%" border="1">
                    <tr>
                        <td class=TableHeader nowrap align=center >Name</td>
                        <td class=TableHeader nowrap align=center >Level Type</td>
                        
                        <td class=TableHeader nowrap align=center>Email</td>
                        <td class=TableHeader nowrap align=center>Select</td>
                    </tr>


                    <%

                                for (int i = 0; i < searchResults.size(); i++) {
                                    PersonModel person = (PersonModel ) searchResults.get(i);
                    %>


                    <tr>
                        <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=person.getPersonFullName()%></td>
                        <td align="center"  style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=person.getM_strUserLevelTypeName()%></td>
                        
                        <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=person.getPersonEMail()%></td>
                        <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><input type="button" class="button" value="Select" onclick="selectPerson(<%=person.getPersonID()%>,'<%=person.getPersonFullName()%>','<%=person.getPersonEMail()%>','<%=person.getPersonAddress()%>');"></td>

                    </tr>

                    <%
                                }

                    %>
                </table>

                <%
                        }
           }
                %>

                </div>

                <div id="confMessage" align="center">
                    <%

                                if (confMessage != null) {
                                    if (confMessage.contains("Invalid")) {
                    %>
                    <div id="confMessage"><font style="font-size: 14px;font-family: tahoma;line-height: 15px;color:red"><%=confMessage%></font></div>
                    <%} else {
                    %>      <div id="confMessage"><font style="font-size: 14px;font-family: tahoma;line-height: 15px;color:green"><%=confMessage%></font></div>
                    <%
                                    }
                                }%>
                </div>
    </body>
</html>
