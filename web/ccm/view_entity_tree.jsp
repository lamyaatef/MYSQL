 <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="com.mobinil.sds.core.utilities.Utility"
         import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.dcm.*"
         import="com.mobinil.sds.web.interfaces.ccm.*"
         import="com.mobinil.sds.core.system.ccm.entity.dto.*"
          import="com.mobinil.sds.web.interfaces.ccm.CCMInterfaceKey"
         import = "com.mobinil.sds.core.system.ccm.entity.model.*"
%>

<form name='CCMform' id='CCMform' action='' method='post'>
<%  
   HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

   String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

  // Vector dcmRegion = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2);
  Vector projectentity = (Vector)objDataHashMap.get(CCMInterfaceKey.VECTOR_ENTITY_ASSIGNED_PROJECT);
  System.out.println("projectentity size isssssssssss"+projectentity.size());
  Vector allentity = (Vector)objDataHashMap.get(CCMInterfaceKey.VECTOR_ENTITY_MANDATORY_DATA);

   System.out.println("all entity size isssssssssss"+allentity.size());
   String appName = request.getContextPath();
   
   Utility.logger.debug("APP NAME:  "+appName);
%>
<title>Javascript Tree Wizard By Zapatec</title>
<script type="text/javascript" src="<%out.print(appName);%>/resources/js/dtree.js"></script>
<LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
<link rel="StyleSheet" href="<%out.print(appName);%>/resources/css/dtree.css" type="text/css" />
<style type="text/css">
</style>
</head>
<body>
<script language=javascript type='text/javascript'> 
function hidediv(pass) { 
var divs = document.getElementsByTagName('div'); 
for(i=0;i<divs.length;i++){ 
if(divs[i].id.match(pass)){//if they are 'see' divs 
if (document.getElementById) // DOM3 = IE5, NS6 
divs[i].style.display="none";// show/hide 
else 
if (document.layers) // Netscape 4 
document.layers[divs[i]].display = 'hidden'; 
else // IE 4 
document.all.hideshow.divs[i].display = 'none'; 
} 
} 
} 

function showdiv(pass) { 
var divs = document.getElementsByTagName('div'); 
for(i=0;i<divs.length;i++){ 
if(divs[i].id.match(pass)){ 
if (document.getElementById) 
divs[i].style.display="block"; 
else 
if (document.layers) // Netscape 4 
document.layers[divs[i]].display = 'visible'; 
else // IE 4 
document.all.hideshow.divs[i].display = 'block'; 
} 
} 
} 


function togglediv(divID)
{
	var obj=document.getElementById(divID).style;
	if (obj.display != 'none') {
	hidediv(divID);
	} else 
	{
	showdiv(divID);
	}
}


function toggleTreeClose()
{
		var obj1=document.getElementById("chooseRegionFrom").style;
		var ob2=document.getElementById("chooseRegionTo").style;
		if(obj1!=null)
		{
			if(obj1.displany='none')
			{
			d.closeAll()
		;}
		}else if(obj2 != null)
		{
			if(obj2.displany='none')
		{
			d2.closeAll()
      ;}
		}
}

function toggleTreeOpen()
{
		var obj1=document.getElementById("chooseRegionFrom").style;
		var ob2=document.getElementById("chooseRegionTo").style;
		if(obj1!=null)
		{
			if(obj1.displany='block')
			{
			d.openAll()
			;}
		}else if(obj2 != null)
		{
			if(obj2.displany='block')
			{
			d2.openAll()
			;}
		}
}


 
</script> 

<%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");


%>


  <tr class=TableHeader>




  <tr class=TableTextNote>
  <td align=middle>Select Parent Entity</td>
  <td align=middle>   <select name='<%=CCMInterfaceKey.INPUT_SEARCH_TEXT_ENTITY_NAME%>' id='<%=CCMInterfaceKey.INPUT_SEARCH_TEXT_ENTITY_NAME%>'>
          <option value=''></option>
          <%
          for (int j=0; j<projectentity.size(); j++)
          {
            EntityMandatoryDataModel model = (EntityMandatoryDataModel) projectentity.get(j); 
             // String regionTypeLevelId = regionModel.getRegionLevelTypeId();
              String enityName = model.getEntityyName();
              System.out.println("The entity name isssssssssssss " + enityName);
              String enityId = model.getEntityId();
          %>
              <option value='<%=enityId%>'><%=enityName%></option>    
          <%
          }
          %>
          </select>
        </td>
        
          <td align=middle>Select Child Entity</td>
  <td align=middle> <select name='<%=CCMInterfaceKey.INPUT_SEARCH_TEXT_ENTITY_NAME%>' id='<%=CCMInterfaceKey.INPUT_SEARCH_TEXT_ENTITY_NAME%>'>
          <option value=''></option>
          <%
          for (int j=0; j<allentity.size(); j++)
          {
            EntityMandatoryDataModel model = (EntityMandatoryDataModel) projectentity.get(j); 
             // String regionTypeLevelId = regionModel.getRegionLevelTypeId();
              String enityName = model.getEntityyName();
              String enityId = model.getEntityId();
          %>
              <option value='<%=enityId%>'><%=enityName%></option>    
          <%
          }
          %>
          </select>
        </td>
        
          <td align="center">
  <%
//   out.print("<INPUT class=button type='button' value=\" Save Sub Entity \" name=\"new\" id=\"new\" onclick=\"document.CCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value ='"+//CCMInterfaceKey.ACTION_INSERT_SUB_ENTITY+"';"+
  //"checkBeforeSubmit();\">");
  %>
  </td>
  
  </tr>
  </body>
  </form>
  





     