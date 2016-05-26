<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<h:form>
    <% //String name = (String)request.getAttribute("User");
String name = (String)session.getAttribute("AdminUser");
System.out.println("admin username sent from server is : "+name);

%> 
<div id="header">

<p>Welcome: <%=name%> <span>|</span> <a><h:commandLink  action="#{leftMenu_Bean.logout_Action}">
                <h:outputText value="Logout"/>
            </h:commandLink></a></p>


</div>

</h:form>