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
         import = "com.mobinil.sds.core.system.ccm.entityproject.model.*"
%>
<html>
<head>
<style type="text/css">
input.addBtn { 
	  color:white; 
	  border:1px solid; 
	  border-color:white; 
	   hieght: 16px;
	  width: 25px; 
      background-image: url('../resources/img/add.gif');
      background-repeat: no-repeat;	  
      background-color:white;
	}
	input.deleteBtn { 
	  color:white; 
	  border:1px solid; 
	  border-color:white; 
	  hieght: 16px;
	  width: 25px;
      background-image: url('../resources/img/deactivate.gif');
      background-repeat: no-repeat;	
      background-color:white; 
		
	} 
	input.btn1 { 
	  color:white; 
	  font: bold 84% 'trebuchet ms',helvetica,sans-serif; 
	  background-color:#747170; 
	  border:1px solid; 
	  border-color:white; 
	  
	} 
</style>
<script type="text/javascript" src="../resources/js/dtree.js"></script>
<link rel="StyleSheet" href="../resources/css/dtree.css" type="text/css" />

<!-- by heba -->
<script>
function addEvent() {
  var ni = document.getElementById('myDiv');
  var numi = document.getElementById('theValue');
  var num = (document.getElementById("theValue").value -1)+ 2;
  numi.value = num;
  var divIdName = "my"+num+"Div";
  var newdiv = document.createElement('div');
  newdiv.setAttribute("id",divIdName);
  newdiv.innerHTML = "Element Number " + num + " has been added! <a href=\"javascript:;\" onclick=\"removeElement(\'"+divIdName+"\')\">Remove the element &quot;"+divIdName+"&quot;</a>";
  newdiv.innerHTML+= "         "+ "<a href='#' onclick='addChild();'>Add Child</a>";
   ni.appendChild(newdiv);
}

function addChild() 
{
 	window.location = 'AddChildPage'; 
}

function removeElement(divNum) 
{
	window.location = 'RemoveChildPage'; 
  /*var d = document.getElementById('myDiv');
  var olddiv = document.getElementById(divNum);
  d.removeChild(olddiv); */
}

</script>

<!-- by heba -->


</head>
<body >

 <form name='CCMform' id='CCMform' action='' method='post'>
 
 
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
			d.closeAll();
			}
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

<script>

</script>
<%  
   HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

   String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
   
   String  projectId = (String)objDataHashMap.get(CCMInterfaceKey.CONTROL_HIDDEN_PROJECT_ID);
   
 String   parentID=(String)objDataHashMap.get(CCMInterfaceKey.INPUT_HIDDEN_PARENT_ID);
   
  // System.out.println("projectId IN TREEEE PAGE ISS"+projectId);
   
   
  // Vector dcmRegion = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2);
  Vector projectentity = (Vector)objDataHashMap.get(CCMInterfaceKey.VECTOR_ENTITY_ASSIGNED_PROJECT);
 // System.out.println("projectentity size isssssssssss"+projectentity.size());
  Vector allentity = (Vector)objDataHashMap.get(CCMInterfaceKey.VECTOR_ENTITY_MANDATORY_DATA);
  
  Vector allprojectentity=  (Vector)objDataHashMap.get(CCMInterfaceKey.VECTOR_ENTITY_ALL_PROJECT);

   //System.out.println("all entity size isssssssssss"+allentity.size());
   String appName = request.getContextPath();
 // System.out.println("path isssssssssss"+appName);
   
   String projectName=(String ) objDataHashMap.get(CCMInterfaceKey.CONTROL_HIDDEN_PROJECT_NAME);
   out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
           " value=\""+"\">");
   
   out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
           " value=\""+strUserID+"\">");   
   //out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
     //      " value=\""+strUserID+"\">");
   out.println("<input type=\"hidden\" name=\""+CCMInterfaceKey.INPUT_HIDDEN_PARENT_ID+"\""+
		   " value=\""+"\">");
   
   out.println("<input type=\"hidden\" name=\""+CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_ID+"\""+
		   " value=\"0"+"\">");
   
   out.println("<input type=\"hidden\" name=\""+CCMInterfaceKey.CONTROL_HIDDEN_PROJECT_ID+"\""+
           " value=\""+projectId+"\">");
   
   out.println("<input type=\"hidden\" name=\""+CCMInterfaceKey.CONTROL_HIDDEN_PROJECT_NAME+"\""+
           " value=\""+projectName+"\">");
   
   out.println("<input type=\"hidden\" name=\""+CCMInterfaceKey.CONTROL_HIDDEN_FLAGN+"\""+
           " value=\"\">");
   
   Utility.logger.debug("APP NAME:  "+appName);
   
   String page_header=projectName;
   
   
	
	String Message="";
	Message =(String) objDataHashMap.get(InterfaceKey.HASHMAP_KEY_MESSAGE);
	
     //String flag="";
	// flag = (String) objDataHashMap.get("flag");
	
	  
	

%>

<script LANGUAGE="JavaScript">
function confirmSubmit()
{
var agree=confirm("Are you sure you wish to Delete this Entity with its Sub entities?");
   //alert(agree);
if (agree){
	//return true ;
 
	document.CCMform.<%=CCMInterfaceKey.CONTROL_HIDDEN_FLAGN%>.value = "secondTime";
	document.CCMform.<%=CCMInterfaceKey.CONTROL_HIDDEN_PROJECT_ID%>.value = '<%=projectId%>';
	document.CCMform.<%=CCMInterfaceKey.CONTROL_HIDDEN_PROJECT_NAME%>.value = '<%=projectName%>';
	document.CCMform.<%=CCMInterfaceKey.INPUT_HIDDEN_PARENT_ID%>.value = '<%=parentID%>';

    document.CCMform.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = '<%=CCMInterfaceKey.ACTION_DELETE_SUB_ENTITIES%>';
	document.CCMform.submit();
}
 
}
</script>
<%
if(objDataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_MESSAGE))
{
	  out.print( "<script>");
	  out.print("confirmSubmit();");
 
     out.print( "</script>");
}
%>

   <center> 
   <h2> <%=page_header%></h2>
   
   </center>

  
 <%-- 
 
<table>
  <tr class=TableTextNote>
  <td align=middle>Select Parent Entity</td>
  <td align=middle>   <select name='<%=CCMInterfaceKey.INPUT_SEARCH_TEXT_ENTITY_PARENT%>' id='<%=CCMInterfaceKey.INPUT_SEARCH_TEXT_ENTITY_PARENT%>'>
          <option value=''></option>
          
          <%
         // System.out.println("here in project entities"+projectentity);
       String   ProjectName="";
      // System.out.println("4");
         for (int j=0; j<projectentity.size(); j++)
          {
        	// System.out.println("sss dddd "+projectentity.get(j));
           EntityDto dto = (EntityDto) projectentity.get(j);
           entityProjectModel prjectModel = (entityProjectModel)dto.getModel();
             // String regionTypeLevelId = regionModel.getRegionLevelTypeId();
              String enityName = prjectModel.getEntityName();
              ProjectName=prjectModel.getProjectName();
              //System.out.println("The entity name isssssssssssss " + enityName);
              String enityId = prjectModel.getEntityId();
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
     
      //  System.out.println("here in child entities"+allentity);
        // System.out.println("1");
        for (int j=0; j<projectentity.size(); j++)
        {
          System.out.println("sss dddd "+projectentity.get(j));
           EntityDto dto = (EntityDto) projectentity.get(j);
          entityProjectModel prjectModel = (entityProjectModel)dto.getModel();
          //  String regionTypeLevelId = regionModel.getRegionLevelTypeId();
             String enityName = prjectModel.getEntityName();
           System.out.println("The entity name isssssssssssss " + enityName);
            String enityId = prjectModel.getEntityId();
          %>
              <option value='<%=enityId%>'><%=enityName%></option>    
          <%
       }
      
          %>
          </select>
        </td>
        
        
          <td align="center" >
          
   <%
 
   
   out.print("<INPUT class=button type=button value=\" Move Sub Entity\" name=\"add_entity_butn\" id=\"add_entity_butn\" onclick=\"document.CCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value ='"+CCMInterfaceKey.ACTION_ADD_SUB_ENTITIES+"';"+
   "submit();\">");
  %>
  </td>
  
  
        </tr>
     
     </table>

  <table>
  <tr class=TableTextNote>
  <td align=middle>Select Parent Entity</td>
  <td align=middle>   <select name='<%=CCMInterfaceKey.INPUT_SEARCH_TEXT_ENTITY_PARENT22%>' id='<%=CCMInterfaceKey.INPUT_SEARCH_TEXT_ENTITY_PARENT22%>'>
          <option value=''></option>
          <%
          System.out.println("here in project entities"+projectentity);
          System.out.println("2");
         System.out.println("allprojectentity.size() "+allprojectentity.size());
          for (int j=0; j<allprojectentity.size(); j++)
         {
        	  System.out.println("sss dddd "+projectentity.get(j));
        	System.out.println("j="+j);
            EntityDto dto = (EntityDto) allprojectentity.get(j);
           entityProjectModel prjectModel = (entityProjectModel)dto.getModel();
             // String regionTypeLevelId = regionModel.getRegionLevelTypeId();
             String enityName = prjectModel.getEntityName();
             //System.out.println("The entity name isssssssssssss " + enityName);
             String enityId = prjectModel.getEntityId();
          %>
              <option value='<%=enityId%>'><%=enityName%></option>    
          <%
          }
          %>
          </select>
        </td>
     
     
  <td align=middle>Select Child Entity</td>
  <td align=middle> <select name='<%=CCMInterfaceKey.INPUT_SEARCH_TEXT_ENTITY_NAME22%>' id='<%=CCMInterfaceKey.INPUT_SEARCH_TEXT_ENTITY_NAME22%>'>
          <option value=''></option>
          <%
          System.out.println("here in child entities"+allentity);
          System.out.println("3");
          for (int i=0; i<allentity.size(); i++)
          {
        	  System.out.println("zzzzz ttt "+allentity.get(i));
            EntityMandatoryDataModel model = (EntityMandatoryDataModel) allentity.get(i); 
         //     String regionTypeLevelId = regionModel.getRegionLevelTypeId();
              String enityName = model.getEntityyName();
             String enityId = model.getEntityId();
          %>
              <option value='<%=enityId%>'><%=enityName%></option>    
          <%
          }
          %>
          </select>
        </td>
        
        
          <td align="center" >
          
   <%
 
   
    out.print("<INPUT class=button type=button value=\" Add Sub Entity\" name=\"add_entity_butn1\" id=\"add_entity_butn1\" onclick=\"document.CCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value ='"+CCMInterfaceKey.ACTION_ADD_SUB_ENTITIES_NEW+"';"+
  "submit();\">");
   
   
   
   out.print("<INPUT class=button type=button value=\" Add Entity \" name=\"add_entity_butn2\" id=\"add_entity_butn2\" onclick=\"document.CCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value ='"+CCMInterfaceKey.ACTION_ADD_SUB_ENTITIES_LEVEL1+"';"+
  "submit();\">");
  %>
  </td>
  
  
        </tr>
     
     </table>
 --%>
 
 
<%--
    <%

   out.print("<INPUT class=button type=button value=\" Add Entity \" name=\"add_entity_butn2\" id=\"add_entity_butn2\" onclick=\"document.CCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value ='"+CCMInterfaceKey.ACTION_CHOOSE__ENTITY_FIRST_LEVEL+"';"+
  "submit();\">");
   %>
  
  

    </table>
    --%> 

       <table width=75% height="61" border="0" align="center" bgcolor="white">
        <tr class=TableHeader>
          <td colspan="3" >  Entity Tree View </td>
          <td >  <a  href="javascript: toggleTreeOpen();" name='expand'>Expand All</a></td>
          <td >  <a  href="javascript: toggleTreeClose();" name='collapse' >Collapse All</a></td>
        </tr>
        <tr bgcolor="WHITE">
        <td colspan="2" ><%= projectName %></td> 
        <td><INPUT class="btn1" type="button" value="Add Entity" name="add_entity_butn2" id="add_entity_butn2" onclick="document.CCMform.<%=InterfaceKey.HASHMAP_KEY_ACTION %>.value ='<%=CCMInterfaceKey.ACTION_CHOOSE_ENTITY_FIRST_LEVEL%>';submit();"></td>
        </tr>
        <tr>
          
          <td width="900" colspan=5><div id="chooseRegionFrom" style="display:block" >
           <%showRegionTreeFrom(request, response, out,projectId,projectName);%>
          </div></td>
        <table width=75% border="0" align="center" bgcolor="#EFEFEF">
<tr>
    <td align='left' colspan=5>
      <div id="chooseRegionTo" style="display:none"> <%//showRegionTree(request, response, out);%>
      </div>
      
      </td>
              </tr>

      </table>
      
  </tr>
  
  <tr>
    <td height="43" colspan="5" bordercolor="#CCCCCC" bgcolor="#FFFFFF"></td>
  </tr>
<%!

public void showRegionTreeFrom(HttpServletRequest request, HttpServletResponse response, JspWriter out,String projID,String projectName)
throws ServletException, IOException
{ int counter=1;
  String appName = request.getContextPath(); 
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
 // System.out.print(objDataHashMap.size());
  Vector Regions=(Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
  Vector projectentity = (Vector)objDataHashMap.get(CCMInterfaceKey.VECTOR_ENTITY_ASSIGNED_PROJECT);
  
 
 
  System.out.print(projectentity.size());
   if(!projectentity.isEmpty())
   {
	  // String   projectName="";
    out.print("<SCRIPT LANGUAGE=\"Javascript\">");
    out.print("d = new dTree('d');");
    //out.print("d.config.useIcons = true;");
    out.print("d.add("+projID+",-1,'');");
    //out.print("d.add("+projID+",-1,"+projectName+");");
    for(int i = 0; i<projectentity.size(); i++)
    {
     EntityDto vv=(EntityDto)projectentity.get(i);
    // System.out.println("Test: "+vv.getModel().getId());
    // System.out.println("Test: "+vv.getModel().getEntityName());
    // System.out.println("Test: "+vv.getModel().getEntityId());
    // System.out.println("Test: "+vv.getModel().getProjectName());
    //  projectName=vv.getModel().getProjectName(); 
     }
   
    for(int i = 0; i<projectentity.size(); i++)
    {
       drawTree((EntityDto)projectentity.get(i),out,appName,projID);
       
      
    }
    out.print("document.write(d);");
    out.print("</SCRIPT>");
  }
}

public void drawTree(EntityDto drawentityDto, JspWriter out,String appName,String parentID) throws ServletException, IOException
{    
	
	System.out.println("in drawTree");

  
  out.print("d.add("+drawentityDto.getModel().getEntityId()+","+parentID+",'"+drawentityDto.getModel().getEntityName()+"',' ', 'Entity  "+drawentityDto.getModel().getEntityId()+"', '', '', '', '','\"" + CCMInterfaceKey.ACTION_CHOOSE_SUB_ENTITY + "\"','\""+CCMInterfaceKey.ACTION_DELETE_SUB_ENTITIES+"\"','CCMform','"+CCMInterfaceKey.INPUT_HIDDEN_PARENT_ID+"');");
  
    
  
  

  //System.out.println("d.add("+drawentityDto.getModel().getEntityId()+",'"+parentID+"','"+drawentityDto.getModel().getEntityName()+"','  ', 'Region "+drawentityDto.getModel().getEntityId()+"', '', '','', '','button1','button2');");
   if(!drawentityDto.getChildEntities().isEmpty())
  { 
    for(int i=0;i<drawentityDto.getChildEntities().size();i++)
    {
    	 
    	     EntityDto vvv=(EntityDto)drawentityDto.getChildEntities().get(i);
    	   //  System.out.println("Test Child: "+vvv.getModel().getParentid());
    	   //  System.out.println("Test Child: "+vvv.getModel().getParentId());
    	   //  System.out.println("Test Child: "+vvv.getModel().getEntityName());
    	   //  System.out.println("Test Child: "+vvv.getModel().getEntityId());
    	   
        drawTree((EntityDto)drawentityDto.getChildEntities().get(i),out,appName,vvv.getModel().getParentId());

    }
  }
   
}
/**
  public void showRegionTree(HttpServletRequest request, HttpServletResponse response, JspWriter out)
throws ServletException, IOException
{
  int counter=1;
  String appName = request.getContextPath(); 
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
  System.out.print(objDataHashMap.size());
  Vector projectentity = (Vector)objDataHashMap.get(CCMInterfaceKey.VECTOR_ENTITY_ASSIGNED_PROJECT);
  System.out.print(projectentity.size());
  if(!projectentity.isEmpty())
  {
   out.print("<SCRIPT LANGUAGE=\"Javascript\">");
   out.print("d2 = new dTree('d2');");
   out.print("d2.add(0,-1,'Select a Region To Insert into');");
   
   for(int i = 0; i<projectentity.size(); i++)
   {
    drawTreeRegion((EntityDto)projectentity.get(i),out,appName,"0");
    }
    out.print("document.write(d2);");
    out.print("</SCRIPT>");
   }
 }
  public void drawTreeRegion(EntityDto drawentityDto, JspWriter out,String appName,String parentID) throws ServletException, IOException
 {
	 System.out.println("in drawTree 2");
   out.print("d2.add("+drawentityDto.getModel().getId()+","+parentID+",'"+drawentityDto.getModel().getName()+"','  ', 'Entity "+drawentityDto.getModel().getId()+"', '', '', '', '','button1','button1');");
   if(!drawentityDto.getChildEntities().isEmpty())
   { 
     for(int i=0;i<drawentityDto.getChildEntities().size();i++)
     {

        drawTreeRegion((EntityDto)drawentityDto.getChildEntities().get(i),out,appName,drawentityDto.getModel().getId());
    
     }
    }
   }
*/
public void showRegionTree(HttpServletRequest request, HttpServletResponse response, JspWriter out)
throws ServletException, IOException
{
  int counter=1;
  String appName = request.getContextPath(); 
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
  System.out.print(objDataHashMap.size());
  Vector projectentity = (Vector)objDataHashMap.get(CCMInterfaceKey.VECTOR_ENTITY_ASSIGNED_PROJECT);
 // System.out.print(projectentity.size());
  if(!projectentity.isEmpty())
  {
   out.print("<SCRIPT LANGUAGE=\"Javascript\">");
   out.print("d2 = new dTree('d2');");
   out.print("d2.add(0,-1,'Select a Region To Insert into');");
   
   for(int i = 0; i<projectentity.size(); i++)
   {
    drawTreeRegion((EntityDto)projectentity.get(i),out,appName,"0");
    }
    out.print("document.write(d2);");
    out.print("</SCRIPT>");
  }
}
 public void drawTreeRegion(EntityDto drawentityDto, JspWriter out,String appName,String parentID) throws ServletException, IOException
{
	// System.out.println("in drawTree 2");
   out.print("d2.add("+drawentityDto.getModel().getId()+","+parentID+",'"+drawentityDto.getModel().getName()+"','  ', 'Entity "+drawentityDto.getModel().getId()+"', '', '');");
  if(!drawentityDto.getChildEntities().isEmpty())
  { 
    for(int i=0;i<drawentityDto.getChildEntities().size();i++)
    {

        drawTreeRegion((EntityDto)drawentityDto.getChildEntities().get(i),out,appName,drawentityDto.getModel().getId());
    
    }
  }
  }


 %>
</table>
<center>

 </center>
 
   </form>
   <script>
    
    toggleTreeClose();
    </script>
    </body>
    </html>

