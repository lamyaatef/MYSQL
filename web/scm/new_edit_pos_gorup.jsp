<%-- 
    Document   : new_edit_pos_gorup
    Created on : Nov 1, 2010, 5:32:47 AM
    Author     : AHMED SAFWAT
--%>

<%@page contentType="text/html" pageEncoding="windows-1252"
        import="com.mobinil.sds.web.interfaces.*"
        import="com.mobinil.sds.web.interfaces.scm.*"
        import="com.mobinil.sds.core.system.scm.model.*"
        import="java.util.*"
        %>
<%
            HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
            POSGroupModel posGroup=(POSGroupModel)dataHashMap.get(SCMInterfaceKey.POS_GROUP_MODEL);
            String appName = request.getContextPath();
            String formName = "posGroupNewEditForm";
            String userId = (String) dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            String confMessage=(String)dataHashMap.get(SCMInterfaceKey.CONFIRMATION_MESSAGE);
            Vector<GroupTypeModel> groupTypes=new Vector();
            groupTypes=(Vector)dataHashMap.get(SCMInterfaceKey.VECTOR_ALL_POS_GROUP_TYPES);
            
            String pageHeader="Add New Group";
            String buttonValue="Add";
            String buttonAction=SCMInterfaceKey.ACTION_ADD_NEW_POS_GROUP;
            int groupId=0;
            String groupName="";
            String description="";
            String groupTypeId="";

            if(posGroup!=null){
                pageHeader="Update Group";
                buttonValue="Update";
                buttonAction=SCMInterfaceKey.ACTION_UPDATE_POS_GROUP;
                groupId=posGroup.getGroupId();
                groupName=posGroup.getGroupName();
                description=posGroup.getDescription();
                groupTypeId=posGroup.getGroupTypeId();

            }

%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
        <LINK REL=STYLESHEET TYPE="text/css" HREF="<%=appName%>/resources/css/Template1.css">
        <SCRIPT language=JavaScript src="<%=appName%>/resources/js/validation.js" type="text/javascript"></SCRIPT>

        <title>New/Edit POS Group </title>
        <script>
        function submitForm()
        {
            groupName=document.<%=formName%>.<%=SCMInterfaceKey.CONTROL_TEXT_GROUP_NAME%>.value;
            description=document.<%=formName%>.<%=SCMInterfaceKey.CONTROL_TEXT_DESCRIPTION%>.value;
            groupType=document.<%=formName%>.<%=SCMInterfaceKey.POS_GROUP_TYPE_ID%>.value;
            if(groupName==""){
                alert("Please Enter Group Name");
                return;
            }
             if(groupType==""){
                alert("Please Choose Group Type");
                return;
            }
            if(description==""){
                alert("Please Enter Description");
                return;
            }
            document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=buttonAction%>";
            document.<%=formName%>.submit();
        }
        function doBack(){
            document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_VIEW_POS_GROUPS%>";
            document.<%=formName%>.submit();
        }

        function ismaxlength(obj){
            var mlength=obj.getAttribute? parseInt(obj.getAttribute("maxlength")) : ""
            if (obj.getAttribute && obj.value.length>mlength)
            obj.value=obj.value.substring(0,mlength)
            if(obj.value.lastIndexOf("<script>")!=-1||obj.value.lastIndexOf("<\/script>")!=-1){
                    document.<%=formName%>.submitButton.disabled=true;
                    alert("JavaScript Injection Stop.");

            }else{
                    document.<%=formName%>.submitButton.disabled=false;
            }
        }
        function checkJavascriptInjection(obj){
            var objValue=obj.value;
            if(objValue.lastIndexOf("<script>")!=-1||obj.value.lastIndexOf("<\/script>")!=-1){
              document.<%=formName%>.submitButton.disabled=true;
                 alert("JavaScript Injection Stop.");

            }else{
                             document.<%=formName%>.submitButton.disabled=false;
            }

 
        }
</script>

    </head>
    <body>
        <div align="center">
            <br>
            <br>
            <h2><%=pageHeader%></h2>
            <br>
            <br>

               <form action="<%=appName%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="<%=formName%>" method="post">
                           <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_ACTION%>" value="0">
                           <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_USER_ID%>" value="<%=userId%>">
                           <input type="hidden" name="<%=SCMInterfaceKey.CONTROL_HIDDEN_GROUP_ID %>" value="<%=groupId%>">

                            <table style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="80%" border=1>
                              <tr class=TableTextNote>
                              <td>Group Name</td>
                              <td><input type="text" onkeyup="return checkJavascriptInjection(this)" name="<%=SCMInterfaceKey.CONTROL_TEXT_GROUP_NAME %>" value="<%=groupName%>"><font style="font-size: 11px;font-family: tahoma;line-height: 15px;color: red">*</font></td>
                              </tr>
                              <tr class=TableTextNote>
                              <td>Group Type</td>
                              <td>
                              <select name="<%=SCMInterfaceKey.POS_GROUP_TYPE_ID%>">
                                <option value="">-----</option>
                            <%
                                        if(groupTypes!=null && groupTypes.size()!=0){
                                        for (int i = 0; i < groupTypes.size(); i++) {
                                            GroupTypeModel groupType = (GroupTypeModel) groupTypes.get(i);
                            %>
                            <option value="<%=groupType.getGroupTypeId()%>"
                                    <%
                                    if(groupTypeId!=null && groupTypeId.trim()!=""){
                                        if(groupTypeId.equalsIgnoreCase(groupType.getGroupTypeId())){
                                            out.print("selected");
                                        }
                                        }
                                    %>
                                    ><%=groupType.getGroupTypeName()%></option>
                            <%
                                        }
                                        }
                            %>
                            </select>

                              <font style="font-size: 11px;font-family: tahoma;line-height: 15px;color: red">*</font></td>
                              </tr>
                              <tr class=TableTextNote>
                              <td>Description</td>
                              <td> <textarea maxlength="200" rows="4" cols="50" onkeyup="return ismaxlength(this)" name="<%=SCMInterfaceKey.CONTROL_TEXT_DESCRIPTION %>" ><%=description%></textarea><font style="font-size: 11px;font-family: tahoma;line-height: 15px;color: red">*</font><br><font style="font-size: 11px;font-family: tahoma;line-height: 15px">Max. Characters: 200</font></td>
                              </tr>
                              <tr>
                                  <td colspan="2" align="center"><input type="button" name="submitButton" class="button" value="<%=buttonValue%>" style="font-size: 11px;font-family: tahoma;line-height: 15px" onclick="submitForm();"><input type="button" class="button" value="Back" style="font-size: 11px;font-family: tahoma;line-height: 15px" onclick="doBack();"></td>
                              </tr>
                            </table>
                                                          <div id="confMessage">
                                                                           <%

                        if(confMessage!=null)
                            {
                            if(confMessage.contains("Invalid")){
                        %>
                        <div id="confMessage"><font style="font-size: 14px;font-family: tahoma;line-height: 15px;color:red"><%=confMessage%></font></div>
                            <%}else{
                        %>      <div id="confMessage"><font style="font-size: 14px;font-family: tahoma;line-height: 15px;color:green"><%=confMessage%></font></div>
                        <%
                                }
                            }%>
                            </div>

               </form>
        </div>

    </body>
</html>
