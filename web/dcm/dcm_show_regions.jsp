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
         import="com.mobinil.sds.core.system.dcm.region.model.*"
         import="com.mobinil.sds.core.system.dcm.region.dao.*"
         import="com.mobinil.sds.core.system.dcm.region.dto.*"

%>
<form name='DCMform' id='DCMform' action='' method='post'>
<%  
   HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

   String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

   Vector dcmRegion = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2);

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


function checkBeforeSubmit()
    {
     if(eval("document.DCMform.<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_REGION_NAME%>.value") == "")
      {
        alert("You must select a parent region.");
        return;
      }
      if(eval("document.DCMform.<%=DCMInterfaceKey.INPUT_TEXT_REGION_NAME%>.value") == "")
      {
        alert("You must enter a Child Region.");
        return;
      }
      DCMform.submit();
    }
function checkBeforeSubmit2()
    {
      if(eval("document.DCMform.<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_REGION_NAME_TO%>.value") == "")
      {
        alert("You must select a region to transfer From.");
        return;
      }
      if(eval("document.DCMform.<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_REGION_NAME_FROM%>.value") == "")
      {
        alert("You must select a region to transfer To.");
        return;
      }
      DCMform.submit();
    }
function checkBeforeSubmit3()
    {
     if(eval("document.DCMform.<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_EDIT_REGION%>.value") == "")
      {
        alert("You must select a region.");
        return;
      }
      DCMform.submit();
    }
function checkBeforeSubmit4()
    {
     if(eval("document.DCMform.<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_DELETE_REGION%>.value") == "")
      {
        alert("You must select a region.");
        return;
      }
      DCMform.submit();
    }
</script> 

<%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");


%>
<center>
<H2>Regional Management</H2></center>
<form name='DCMform' id='DCMform' action='' method='post'>
<TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 align = "center" width="75%" border=1>
<tr class=TableHeader>
  <td align = "center" colspan=5>Move Region</td>
  
<tr class=TableTextNote>
  <td align=middle>Select Region</td>
  <td align=middle>          <select name='<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_REGION_NAME_TO%>' id='<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_REGION_NAME_TO%>'>
          <option value=''></option>
          <%
           String regionIdFrom="";
           String regionIdTo="";
           
          for (int j=0; j<dcmRegion.size(); j++)
          {
              RegionModel regionModel = (RegionModel)dcmRegion.get(j); 
              String regionTypeLevelId = regionModel.getRegionLevelTypeId();
              String regionName = regionModel.getRegionName();
              regionIdTo = regionModel.getRegionId();
          %>
              <option value='<%=regionIdTo%>'><%=regionName%></option>    
          <%
          }
          %>
          </select>
        </td>
  <td align=middle>To Region</td>
  <td align=middle>          <select name='<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_REGION_NAME_FROM%>' id='<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_REGION_NAME_FROM%>'>
          <option value=''></option>
          <%
          for (int j=0; j<dcmRegion.size(); j++)
          {
              RegionModel regionModel = (RegionModel) dcmRegion.get(j); 
              String regionTypeLevelId = regionModel.getRegionLevelTypeId();
              String regionName = regionModel.getRegionName();
               regionIdFrom = regionModel.getRegionId();
          %>
              <option value='<%=regionIdFrom%>'><%=regionName%></option>    
          <%
          }
          %>
          </select>
        </td>
  <td align="center" >
   <%
   out.print("<INPUT class=button type=button value=\" Move Region \" name=\"Submit2\" id=\"Submit2\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value ='"+DCMInterfaceKey.ACTION_MOVE_REGION_FROM_TO+"';"+
  "checkBeforeSubmit2();\">");
  %>
  </td>
  </tr>
  <tr class=TableHeader>
  <td align = "center" colspan=5>Add New Region</td>
  </tr>
  <tr class=TableTextNote>
  <td align=middle>Select Parent Region</td>
  <td align=middle>          <select name='<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_REGION_NAME%>' id='<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_REGION_NAME%>'>
          <option value=''></option>
          <%
          for (int j=0; j<dcmRegion.size(); j++)
          {
              RegionModel regionModel = (RegionModel)dcmRegion.get(j); 
              String regionTypeLevelId = regionModel.getRegionLevelTypeId();
              String regionName = regionModel.getRegionName();
              String regionId = regionModel.getRegionId();
          %>
              <option value='<%=regionId%>'><%=regionName%></option>    
          <%
          }
          %>
          </select>
        </td>
  <td align = "middle">Child Region </TD>
    <td align=middle><input type="text" name="region_name" size="10" value=""></td>
  <td align="center">
  <%
   out.print("<INPUT class=button type='button' value=\" Save new Region \" name=\"new\" id=\"new\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value ='"+DCMInterfaceKey.ACTION_INSERT_NEW_REGION+"';"+
  "checkBeforeSubmit();\">");
  %>
  </td>
</tr>

<tr class=TableHeader>
<td align = "center" colspan=5>Edit Region</td>
</tr>
  <tr class=TableTextNote>
  <td align=middle>Select Region</td>
  <td align=middle>          <select name='<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_EDIT_REGION%>' id='<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_EDIT_REGION%>'>
          <option value=''></option>
          <%
          for (int j=0; j<dcmRegion.size(); j++)
          {
              RegionModel regionModel = (RegionModel)dcmRegion.get(j); 
              String regionTypeLevelId = regionModel.getRegionLevelTypeId();
              String regionName = regionModel.getRegionName();
              String regionId = regionModel.getRegionId();
          %>
              <option value='<%=regionId%>'><%=regionName%></option>    
          <%
          }
          %>
          </select>
        </td>
 <td  align = "centers" COLSPAN=3  >
  <%
   out.print("<INPUT class=button type=button value=\" Edit Region \" name=\"edit\" id=\"edit\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value ='"+DCMInterfaceKey.ACTION_EDIT_REGION+"';"+
  "checkBeforeSubmit3();\">");
  %>
  </td>
  </tr>
 
 <tr class=TableHeader>
<td align = "center" colspan=5>Delete Region</td>
</tr>
  <tr class=TableTextNote>
  <td align=middle>Select Region</td>
  <td align=middle>          <select name='<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_DELETE_REGION%>' id='<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_DELETE_REGION%>'>
          <option value=''></option>
          <%
          for (int j=0; j<dcmRegion.size(); j++)
          {
              RegionModel regionModel = (RegionModel)dcmRegion.get(j); 
              String regionTypeLevelId = regionModel.getRegionLevelTypeId();
              String regionName = regionModel.getRegionName();
              String regionId = regionModel.getRegionId();
          %>
              <option value='<%=regionId%>'><%=regionName%></option>    
          <%
          }
          %>
          </select>
        </td>
 <td align = "center" colspan=3>
  <%
   out.print("<INPUT class=button type=button value=\" Delete Region \" name=\"delete\" id=\"delete\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value ='"+DCMInterfaceKey.ACTION_DELETE_REGION+"';"+
  "checkBeforeSubmit4();\">");
  %>
  </td>
  </tr> 
  
</table>

<br>


      <table width=75% height="61" border="0" align="center" bgcolor="#EFEFEF">
        <tr class=TableHeader>
          <td colspan="3" >Regions Tree View </td>
          <td >  <a  href="javascript: toggleTreeOpen();" name='expand'>Expand All</a></td>
          <td >  <a  href="javascript: toggleTreeClose();" name='collapse' >Collapse All</a></td>
        </tr>
        <tr>
          
          <td width="900" colspan=5><div id="chooseRegionFrom" style="display:block" >
           <%showRegionTreeFrom(request, response, out);%>
          </div></td>
        <table width=75% border="0" align="center" bgcolor="#EFEFEF">
<tr>
    <td align='left' colspan=5>
      <div id="chooseRegionTo" style="display:none"> <%showRegionTree(request, response, out);%>
      </div>
      </td>
              </tr>

      </table>
      </td>
  </tr>
  
  <tr>
    <td height="43" colspan="5" bordercolor="#CCCCCC" bgcolor="#FFFFFF"></td>
  </tr>
  
</table>


</form>
</body>
</html>
<%!

  public void showRegionTreeFrom(HttpServletRequest request, HttpServletResponse response, JspWriter out)
  throws ServletException, IOException
  { int counter=1;
    String appName = request.getContextPath(); 
    HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    System.out.print(objDataHashMap.size());
    Vector Regions=(Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
    
    System.out.print(Regions.size());
     if(!Regions.isEmpty())
     {
      out.print("<SCRIPT LANGUAGE=\"Javascript\">");
      out.print("d = new dTree('d');");
      out.print("d.add(0,-1,'Select Region');");
      for(int i = 0; i<Regions.size(); i++)
      {
         drawTree((RegionDto)Regions.get(i),out,appName,0);
      }
      out.print("document.write(d);");
      out.print("</SCRIPT>");
    }
  }

public void drawTree(RegionDto drawRegionDto, JspWriter out,String appName,int parentID) throws ServletException, IOException
  {    
    out.print("d.add("+drawRegionDto.getModel().getId()+","+parentID+",'"+drawRegionDto.getModel().getName()+"','  ', 'Region "+drawRegionDto.getModel().getId()+"', '', '');");

     if(!drawRegionDto.getChildRegions().isEmpty())
    { 
      for(int i=0;i<drawRegionDto.getChildRegions().size();i++)
      {
          drawTree((RegionDto)drawRegionDto.getChildRegions().get(i),out,appName,drawRegionDto.getModel().getId());
      }
    }
  }

  public void showRegionTree(HttpServletRequest request, HttpServletResponse response, JspWriter out)
  throws ServletException, IOException
  {
    int counter=1;
    String appName = request.getContextPath(); 
    HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    System.out.print(objDataHashMap.size());
    Vector Regions=(Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
    System.out.print(Regions.size());
    if(!Regions.isEmpty())
    {
     out.print("<SCRIPT LANGUAGE=\"Javascript\">");
     out.print("d2 = new dTree('d2');");
     out.print("d2.add(0,-1,'Select a Region To Insert into');");
     for(int i = 0; i<Regions.size(); i++)
     {
      drawTreeRegion((RegionDto)Regions.get(i),out,appName,0);
      }
      out.print("document.write(d2);");
      out.print("</SCRIPT>");
    }
  }
   public void drawTreeRegion(RegionDto drawRegionDto, JspWriter out,String appName,int parentID) throws ServletException, IOException
  {
     out.print("d2.add("+drawRegionDto.getModel().getId()+","+parentID+",'"+drawRegionDto.getModel().getName()+"','  ', 'Region "+drawRegionDto.getModel().getId()+"', '', '');");
    if(!drawRegionDto.getChildRegions().isEmpty())
    { 
      for(int i=0;i<drawRegionDto.getChildRegions().size();i++)
      {

          drawTreeRegion((RegionDto)drawRegionDto.getChildRegions().get(i),out,appName,drawRegionDto.getModel().getId());
      
      }
    }
    }
  %>
