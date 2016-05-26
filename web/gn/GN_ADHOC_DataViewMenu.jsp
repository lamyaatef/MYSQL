<%@ page buffer="1024kb" %>
<%@ page contentType="text/html;charset=windows-1256"%>
<%@ page import="com.mobinil.sds.web.interfaces.*" 
         import="com.mobinil.sds.web.interfaces.gn.querybuilder.*" 
         import="com.mobinil.sds.web.interfaces.cc.*" 
         %>

<HTML>
   <HEAD>
      <META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=windows-1256"/>
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/leftmenu.css">
      <SCRIPT language=JavaScript src="../resources/js/utilities.js" type=text/javascript></SCRIPT>
      <TITLE>Data View Builder</TITLE>
   </HEAD>
  <BODY bgColor=#ffffff leftMargin=0 topMargin=0 marginheight="0" marginwidth="0">
  <form id="formDataViewMenu" name="formDataViewMenu" method="post" action="../servlet/com.mobinil.sds.web.controller.WebControllerServlet" target="Right">
    
<SCRIPT LANGUAGE="javascript">

function openWizardStep(argAction)
{
  var strTarget = "";
  try
  {
     top.frames("Right").frames("Down").addToCart();
//      top.frames("Down").addToCart();
  }
  catch(e)
  {
  }
  switch(argAction)
  {
      case 1:
          strTarget = "GN_ADHOC_DataViewMain.jsp";
          break;
      case 2:
          strTarget = "GN_ADHOC_DataViewVariables.jsp";      
          break;
      case 3:
          strTarget = "GN_ADHOC_DataViewSelectedFields.jsp";      
          break;
      case 4:
          strTarget = "GN_ADHOC_DataViewTerms.jsp";      
          break;
      case 5:
          strTarget = "GN_ADHOC_DataViewWhere.jsp";      
          break;      
      case 6:
          strTarget = "GN_ADHOC_DataViewGroupBy.jsp";      
          break;      
      case 7:
          strTarget = "GN_ADHOC_DataViewHaving.jsp";      
          break;      
      case 8:
          strTarget = "GN_ADHOC_DataViewOrderBy.jsp";      
          break;      
      case 9:
      {
          //top.frames("Right").frames("Top").butWizardHelp.click();
          strTarget = "DataViewHelp.html";      
      }    
          break;      
      default: 
        alert("Action Not Supported");
         break;
  }
  if(strTarget != "")
//      top.frames("Right").frames("Down").navigate(strTarget);  
//  top.frames("Right").navigate(strTarget);  
    top.frames("Right").navigate(strTarget);  
}

function submitPage(argAction)
{
 // parent.frames("Right").frames("Top").butPreview.click();
  var nDataViewsCount = 0;
  var nFieldsCount = 0;
  var nConstantsCount = 0;
  var nFtFTermsCount = 0;
  var nFtDVTermsCount = 0;
  var nWhereCount  = 0;
  var nHavingCount  = 0;
  var nOrderByCount  = 0;
  var nGroupByCount  = 0;
  var nFunctionsCount  = 0;
  var nParametersCount = 0;
  try
  {
//    parent.frames("Right").frames("Down").addToCart();
    top.frames("Right").frames("Down").addToCart();
  }
  catch(e)
  {
  }


  
  try
  {
    var coll = document.formDataViewMenu.all.tags("INPUT");
    if (coll!=null)
    {
        while(coll.length > 0)
        {
          for (var i=0; i<coll.length; i++) 
            document.formDataViewMenu.removeChild(coll[i]);
          coll = document.formDataViewMenu.all.tags("INPUT");  
        }    
    }

     
//    alert("COL LEN" + coll.length);
        
///////////////////////////////////////////////////////////////////////////////////
//                          Data View Name
////////////////////////////////////////////////////////////////////////////////////
        
      var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_NAME%>");
      if( objFormEelement != null)
      {
          eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_NAME%>.value  ='"+top.objNewDataView.m_strDataViewName+"';");
      }   
      else      
      { 
         var objHidden= document.createElement("INPUT");
         objHidden.type ="hidden";
         objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_NAME%>";
         objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_NAME%>";
         objHidden.value = top.objNewDataView.m_strDataViewName;
         document.formDataViewMenu.appendChild(objHidden);
      }  

///////////////////////////////////////////////////////////////////////////////////
//                          Data View Description
////////////////////////////////////////////////////////////////////////////////////
  //  alert('data view description ');
    
      var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_DESC%>");
      if( objFormEelement != null)
      {
          eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_DESC%>.value  ='"+top.objNewDataView.m_strDataViewDescription+"';");
      }   
      else      
      { 
        var objHidden= document.createElement("INPUT");
        objHidden.type ="hidden";
        objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_DESC%>";
        objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_DESC%>";
        objHidden.value = top.objNewDataView.m_strDataViewDescription;
        document.formDataViewMenu.appendChild(objHidden);
      }

      var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_UNIVERSE%>");
      if( objFormEelement != null)
      {
          eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_UNIVERSE%>.value  ='"+top.objNewDataView.m_strUniverseName+"';");
      }   
      else      
      { 
        var objHidden= document.createElement("INPUT");
        objHidden.type ="hidden";
        objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_UNIVERSE%>";
        objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_UNIVERSE%>";
        objHidden.value = top.objNewDataView.m_strUniverseName;
        document.formDataViewMenu.appendChild(objHidden);
      }


      var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_ISSUE%>");
      if( objFormEelement != null)
      {
          eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_ISSUE%>.value  ='"+top.objNewDataView.m_nDataViewIssue+"';");
      }   
      else      
      { 
        var objHidden= document.createElement("INPUT");
        objHidden.type ="hidden";
        objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_ISSUE%>";
        objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_ISSUE%>";
        objHidden.value = top.objNewDataView.m_nDataViewIssue;
        document.formDataViewMenu.appendChild(objHidden);
      }
     // alert("mmm1");
      var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_UNIQUE%>");
      if( objFormEelement != null)
      {
           if(top.objNewDataView.m_bDataViewUnique)
               eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_UNIQUE%>.value  ='<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_UNIQUE_TRUE%>';");
           else
               eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_UNIQUE%>.value  ='<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_UNIQUE_FALSE%>';");
      }   
      else      
      { 
        var objHidden= document.createElement("INPUT");
        objHidden.type ="hidden";
        objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_UNIQUE%>";
        objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_UNIQUE%>";
        if(top.objNewDataView.m_bDataViewUnique)
          eval("objHidden.value = '<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_UNIQUE_TRUE%>';");
        else
          eval("objHidden.value = '<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_UNIQUE_FALSE%>';");
        document.formDataViewMenu.appendChild(objHidden);
      }

      var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_SAVE%>");
      if( objFormEelement != null)
      {
            eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_SAVE%>.value  ='<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_SAVE_FALSE%>';");
      }   
      else      
      { 
        var objHidden= document.createElement("INPUT");
        objHidden.type ="hidden";
        objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_SAVE%>";
        objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_SAVE%>";
        eval("objHidden.value = '<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_SAVE_FALSE%>';");
        document.formDataViewMenu.appendChild(objHidden);
      }
///////////////////////////////////////////////////////////////////////////////////
//                          Data Views
////////////////////////////////////////////////////////////////////////////////////
//alert('data views');

      for(var i=0;i<top.objNewDataView.m_arrSelectedDataViews.length;i++)
      {
        if(top.objNewDataView.m_arrSelectedDataViews[i] != null)
        {
            
            var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_DATAVIEWS_IDS%>"+nDataViewsCount);
            if( objFormEelement != null)
            {
              eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_DATAVIEWS_IDS%>"+nDataViewsCount+".value  ='"+top.objNewDataView.m_arrSelectedDataViews[i].m_nDataViewID+"';");
            }   
            else      
            {  
              var objHidden= document.createElement("INPUT");
              objHidden.type ="hidden";
              objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_DATAVIEWS_IDS%>"+nDataViewsCount;
              objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_DATAVIEWS_IDS%>"+nDataViewsCount;
              objHidden.value = top.objNewDataView.m_arrSelectedDataViews[i].m_nDataViewID;
              document.formDataViewMenu.appendChild(objHidden);
            }

            var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_DATAVIEWS_NAMES%>"+nDataViewsCount);
            if( objFormEelement != null)
            {
              eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_DATAVIEWS_NAMES%>"+nDataViewsCount+".value  ='"+top.objNewDataView.m_arrSelectedDataViews[i].m_strDataViewName+"';");
            }   
            else      
            {
              var objHidden= document.createElement("INPUT");
              objHidden.type ="hidden";
              objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_DATAVIEWS_NAMES%>"+nDataViewsCount;
              objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_DATAVIEWS_NAMES%>"+nDataViewsCount;
              objHidden.value = top.objNewDataView.m_arrSelectedDataViews[i].m_strDataViewName;
              document.formDataViewMenu.appendChild(objHidden);
            }  

            var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_DATAVIEWS_TYPE_IDS%>"+nDataViewsCount);
            if( objFormEelement != null)
            {
              eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_DATAVIEWS_TYPE_IDS%>"+nDataViewsCount+".value  ='"+top.objNewDataView.m_arrSelectedDataViews[i].m_nDataViewType+"';");
            }   
            else      
            {
              var objHidden= document.createElement("INPUT");
              objHidden.type ="hidden";
              objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_DATAVIEWS_TYPE_IDS%>"+nDataViewsCount;
              objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_DATAVIEWS_TYPE_IDS%>"+nDataViewsCount;
              objHidden.value = top.objNewDataView.m_arrSelectedDataViews[i].m_nDataViewType;
              document.formDataViewMenu.appendChild(objHidden);
            }  

            nDataViewsCount++;
        }
     }   
     
      if(nDataViewsCount > 0)
      {
        var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_DATAVIEWS_NUM%>");
        if( objFormEelement != null)
        {
            eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_DATAVIEWS_NUM%>.value  ='"+nDataViewsCount+"';");
        }   
        else      
        { 
          var objHidden= document.createElement("INPUT");
          objHidden.type ="hidden";
          objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_DATAVIEWS_NUM%>";
          objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_DATAVIEWS_NUM%>";
          objHidden.value = nDataViewsCount;
          document.formDataViewMenu.appendChild(objHidden);
        }
      }
      else
      {
        //alert("khaled1");   // added by khaled reda 22/6/2004
        throw(e)
      }

//alert('displayed fields');
///////////////////////////////////////////////////////////////////////////////////
//                          Displayed Fields
////////////////////////////////////////////////////////////////////////////////////

      if(top.objNewDataView.m_arrDataViewFields.length == 0)
      {
        //  alert("khaled2");   // added by khaled reda 22/6/2004
          throw(e);
      }
      
      for(var j=0;j<top.objNewDataView.m_arrDataViewFields.length;j++)
      {
        if(top.objNewDataView.m_arrDataViewFields[j] != null)
        {
          var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_IDS%>"+nFieldsCount);
          if( objFormEelement != null)
          {
            eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_IDS%>"+nFieldsCount+".value  ='"+top.objNewDataView.m_arrDataViewFields[j].m_nFieldID+"';");
          }   
          else      
          {
            var objHidden= document.createElement("INPUT");
            objHidden.type ="hidden";
            objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_IDS%>"+nFieldsCount;
            objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_IDS%>"+nFieldsCount;
            objHidden.value = top.objNewDataView.m_arrDataViewFields[j].m_nFieldID;
            document.formDataViewMenu.appendChild(objHidden);
          }

          var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_NAMES%>"+nFieldsCount);
          if( objFormEelement != null)
          {
            eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_NAMES%>"+nFieldsCount+".value  ='"+top.objNewDataView.m_arrDataViewFields[j].m_strFieldName+"';");
          }   
          else      
          {
            var objHidden= document.createElement("INPUT");
            objHidden.type ="hidden";
            objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_NAMES%>"+nFieldsCount;
            objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_NAMES%>"+nFieldsCount;
            objHidden.value = top.objNewDataView.m_arrDataViewFields[j].m_strFieldName;
            document.formDataViewMenu.appendChild(objHidden);        
          }        
          var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_TYPES%>"+nFieldsCount);
          if( objFormEelement != null)
          {
            eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_TYPES%>"+nFieldsCount+".value  ='"+top.objNewDataView.m_arrDataViewFields[j].m_nFieldType+"';");
          }   
          else      
          {
            var objHidden= document.createElement("INPUT");
            objHidden.type ="hidden";
            objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_TYPES%>"+nFieldsCount;
            objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_TYPES%>"+nFieldsCount;
            objHidden.value = top.objNewDataView.m_arrDataViewFields[j].m_nFieldType;
            //alert("objHidden.value: " + objHidden.value);
            document.formDataViewMenu.appendChild(objHidden);
          }  
          if(top.objNewDataView.m_arrDataViewFields[j].m_nFieldType == '<%=QueryBuilderInterfaceKey.FIELD_TYPE_CONST_FIELD%>')
          {
            var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_VALUES%>"+nFieldsCount);
            if( objFormEelement != null)
            {
              eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_VALUES%>"+nFieldsCount+".value  ='"+top.objNewDataView.m_arrDataViewConstants[top.objNewDataView.m_arrDataViewFields[j].m_nFieldIndex].m_strConstantValue+"';");
            }   
            else      
            {
              var objHidden= document.createElement("INPUT");
              objHidden.type ="hidden";
              objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_VALUES%>"+nFieldsCount;
              objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_VALUES%>"+nFieldsCount;
              objHidden.value = top.objNewDataView.m_arrDataViewConstants[top.objNewDataView.m_arrDataViewFields[j].m_nFieldIndex].m_strConstantValue;
              document.formDataViewMenu.appendChild(objHidden);
            }  
          }
          var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_ALIASES%>"+nFieldsCount);
          if( objFormEelement != null)
          {
            eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_ALIASES%>"+nFieldsCount+".value  ='"+top.objNewDataView.m_arrDataViewFields[j].m_strFieldAlias+"';");
          }   
          else      
          {
            var objHidden= document.createElement("INPUT");
            objHidden.type ="hidden";
            objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_ALIASES%>"+nFieldsCount;
            objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_ALIASES%>"+nFieldsCount;
            objHidden.value = top.objNewDataView.m_arrDataViewFields[nFieldsCount].m_strFieldAlias;
            document.formDataViewMenu.appendChild(objHidden);
          }  
          var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_DATAVIEWS_IDS%>"+nFieldsCount);
          if( objFormEelement != null)
          {
            eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_DATAVIEWS_IDS%>"+nFieldsCount+".value  ='"+top.objNewDataView.m_arrDataViewFields[j].m_nDataViewID+"';");
          }   
          else      
          {
            var objHidden= document.createElement("INPUT");
            objHidden.type ="hidden";
            objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_DATAVIEWS_IDS%>"+nFieldsCount;
            objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_DATAVIEWS_IDS%>"+nFieldsCount;
            objHidden.value = top.objNewDataView.m_arrDataViewFields[j].m_nDataViewID;
            document.formDataViewMenu.appendChild(objHidden);
          }  

          var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_DATAVIEWS_NAMES%>"+nFieldsCount);
          if( objFormEelement != null)
          {
            eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_DATAVIEWS_NAMES%>"+nFieldsCount+".value  ='"+top.objNewDataView.m_arrDataViewFields[j].m_strDataViewName+"';");
          }   
          else      
          {
            var objHidden= document.createElement("INPUT");
            objHidden.type ="hidden";
            objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_DATAVIEWS_NAMES%>"+nFieldsCount;
            objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_DATAVIEWS_NAMES%>"+nFieldsCount;
            objHidden.value = top.objNewDataView.m_arrDataViewFields[j].m_strDataViewName;
            document.formDataViewMenu.appendChild(objHidden);
          }  

          var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_DATAVIEWS_TYPES%>"+nFieldsCount);
          if( objFormEelement != null)
          {
            eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_DATAVIEWS_TYPES%>"+nFieldsCount+".value  ='"+top.objNewDataView.m_arrDataViewFields[j].m_nDataViewType+"';");
          }   
          else      
          {
            var objHidden= document.createElement("INPUT");
            objHidden.type ="hidden";
            objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_DATAVIEWS_TYPES%>"+nFieldsCount;
            objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_DATAVIEWS_TYPES%>"+nFieldsCount;
            objHidden.value = top.objNewDataView.m_arrDataViewFields[j].m_nDataViewType;
            document.formDataViewMenu.appendChild(objHidden);
          }  

          
          var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_DESCRIPS%>"+nFieldsCount);
          if( objFormEelement != null)
          {
            eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_DESCRIPS%>"+nFieldsCount+".value  ='"+top.objNewDataView.m_arrDataViewFields[j].m_strFieldDescription+"';");
          }   
          else      
          {
            var objHidden= document.createElement("INPUT");
            objHidden.type ="hidden";
            objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_DESCRIPS%>"+nFieldsCount;
            objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_DESCRIPS%>"+nFieldsCount;
            objHidden.value = top.objNewDataView.m_arrDataViewFields[j].m_strFieldDescription;
            document.formDataViewMenu.appendChild(objHidden);
          }  

          nFieldsCount++;
        }
      }
      
      if(nFieldsCount > 0)
      {
        var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_NUM%>");
        if( objFormEelement != null)
        {
          eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_NUM%>.value  ='"+nFieldsCount+"';");
        }   
        else      
        { 
          var objHidden= document.createElement("INPUT");
          objHidden.type ="hidden";
          objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_NUM%>";
          objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_NUM%>";
          objHidden.value = nFieldsCount;
          document.formDataViewMenu.appendChild(objHidden);
        }        
      }
      else
      {
     ///   alert("khaled3");   // added by khaled reda 22/6/2004
        throw(e)
      }
      if(!validateFields())
      {
          //alert("validateFields");   // added by khaled reda 22/6/2004
          throw(e);  
      }
      if(!validateRules(top.objNewDataView.m_strUniverseName))    
      {
      //    alert("validateRules");   // added by khaled reda 22/6/2004
          throw(e);  
      }
///////////////////////////////////////////////////////////////////////////////////
//                          CONSTANTS
////////////////////////////////////////////////////////////////////////////////////
//alert('constants');
      for(var k=0;k<top.objNewDataView.m_arrDataViewConstants.length;k++)
      {
        if(top.objNewDataView.m_arrDataViewConstants[k] != null)
        {
          if(top.objNewDataView.m_arrDataViewConstants[k].m_strConstantValue == '' || top.objNewDataView.m_arrDataViewConstants[k].m_nConstantType == '')
          {
            //   alert("khaled4");   // added by khaled reda 22/6/2004
               throw(e);
          } 
         
          var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_CONST_NAME%>"+nConstantsCount);
          if( objFormEelement != null)
          {
            eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_CONST_NAME%>"+nConstantsCount+".value  ='"+top.objNewDataView.m_arrDataViewConstants[k].m_strConstantName+"';");
          }   
          else      
          {
            var objHidden= document.createElement("INPUT");
            objHidden.type ="hidden";
            objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_CONST_NAME%>"+nConstantsCount;
            objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_CONST_NAME%>"+nConstantsCount;
            objHidden.value = top.objNewDataView.m_arrDataViewConstants[k].m_strConstantName;
            document.formDataViewMenu.appendChild(objHidden);
          }

          var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_CONST_TYPE%>"+nConstantsCount);
          if( objFormEelement != null)
          {
            eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_CONST_TYPE%>"+nConstantsCount+".value  ='"+top.objNewDataView.m_arrDataViewConstants[k].m_nConstantType+"';");
          }   
          else      
          {
            var objHidden= document.createElement("INPUT");
            objHidden.type ="hidden";
            objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_CONST_TYPE%>"+nConstantsCount;
            objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_CONST_TYPE%>"+nConstantsCount;
            objHidden.value = top.objNewDataView.m_arrDataViewConstants[k].m_nConstantType;
            document.formDataViewMenu.appendChild(objHidden);        
          }   

          if(!validateValue(top.objNewDataView.m_arrDataViewConstants[k].m_nConstantType,top.objNewDataView.m_arrDataViewConstants[k].m_strConstantValue))
          {
            //     alert("khaled5");   // added by khaled reda 22/6/2004
                 throw(e)
           }      

          var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_CONST_VALUE%>"+nConstantsCount);
          if( objFormEelement != null)
          {
            eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_CONST_VALUE%>"+nConstantsCount+".value  ='"+top.objNewDataView.m_arrDataViewConstants[k].m_strConstantValue+"';");
          }   
          else      
          {
            var objHidden= document.createElement("INPUT");
            objHidden.type ="hidden";
            objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_CONST_VALUE%>"+nConstantsCount;
            objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_CONST_VALUE%>"+nConstantsCount;
            objHidden.value = top.objNewDataView.m_arrDataViewConstants[k].m_strConstantValue;
            document.formDataViewMenu.appendChild(objHidden);        
          }

          var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_CONST_DESC%>"+nConstantsCount);
          if( objFormEelement != null)
          {
            eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_CONST_DESC%>"+nConstantsCount+".value  ='"+top.objNewDataView.m_arrDataViewConstants[k].m_strConstantDescription+"';");
          }   
          else      
          {
            var objHidden= document.createElement("INPUT");
            objHidden.type ="hidden";
            objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_CONST_DESC%>"+nConstantsCount;
            objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_CONST_DESC%>"+nConstantsCount;
            objHidden.value = top.objNewDataView.m_arrDataViewConstants[k].m_strConstantDescription;
            document.formDataViewMenu.appendChild(objHidden);        
          }

          nConstantsCount++;
       }   
    }

     if(nConstantsCount > 0)
     {
        var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_CONST_NUM%>");
        if( objFormEelement != null)
        {
          eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_CONST_NUM%>.value  ='"+nConstantsCount+"';");
        }   
        else      
        {
          var objHidden= document.createElement("INPUT");
          objHidden.type ="hidden";
          objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_CONST_NUM%>";
          objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_CONST_NUM%>";
          objHidden.value = nConstantsCount;
          document.formDataViewMenu.appendChild(objHidden);        
        }
     }



///////////////////////////////////////////////////////////////////////////////////
//                          PARAMETERS
////////////////////////////////////////////////////////////////////////////////////
 //  alert('parameters');          
      for(var k=0;k<top.objNewDataView.m_arrDataViewInputParameters.length;k++)
      {
        if(top.objNewDataView.m_arrDataViewInputParameters[k] != null)
        {
          if(top.objNewDataView.m_arrDataViewInputParameters[k].m_strParameterValue == '' || top.objNewDataView.m_arrDataViewInputParameters[k].m_nParameterDataType == '')
          {
              // alert("khaled6");   // added by khaled reda 22/6/2004
               throw(e);
          }  

          var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_NAME%>"+nParametersCount);
          if( objFormEelement != null)
          {
             eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_NAME%>"+nParametersCount+".value  ='"+top.objNewDataView.m_arrDataViewInputParameters[k].m_strParameterName+"';");
          }   
          else      
          {
              var objHidden= document.createElement("INPUT");
              objHidden.type ="hidden";
              objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_NAME%>"+nParametersCount;
              objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_NAME%>"+nParametersCount;
              objHidden.value = top.objNewDataView.m_arrDataViewInputParameters[k].m_strParameterName;
              document.formDataViewMenu.appendChild(objHidden);
          }

		//	alert("param number");
		//	alert(k);
		//	alert(top.objNewDataView.m_arrDataViewInputParameters[k].m_nParameterDataType,top.objNewDataView.m_arrDataViewInputParameters[k].m_strParameterValue);
          if(!validateValue(top.objNewDataView.m_arrDataViewInputParameters[k].m_nParameterDataType,top.objNewDataView.m_arrDataViewInputParameters[k].m_strParameterValue))
          {
               //  alert("khaled7");   // added by khaled reda 22/6/2004
                 throw(e)
          }
          
          var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_VALUE%>"+nParametersCount);
          if( objFormEelement != null)
          {
             eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_VALUE%>"+nParametersCount+".value  ='"+top.objNewDataView.m_arrDataViewInputParameters[k].m_strParameterValue+"';");
          }   
          else      
          {
              var objHidden= document.createElement("INPUT");
              objHidden.type ="hidden";
              objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_VALUE%>"+nParametersCount;
              objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_VALUE%>"+nParametersCount;
              objHidden.value = top.objNewDataView.m_arrDataViewInputParameters[k].m_strParameterValue;
              document.formDataViewMenu.appendChild(objHidden);
           }
           
           var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_LABEL%>"+nParametersCount);
           if( objFormEelement != null)
           {
              eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_LABEL%>"+nParametersCount+".value  ='"+top.objNewDataView.m_arrDataViewInputParameters[k].m_strParameterLabel+"';");
           }   
           else      
           {
              var objHidden= document.createElement("INPUT");
              objHidden.type ="hidden";
              objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_LABEL%>"+nParametersCount;
              objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_LABEL%>"+nParametersCount;
              objHidden.value = top.objNewDataView.m_arrDataViewInputParameters[k].m_strParameterLabel;
              document.formDataViewMenu.appendChild(objHidden);
            }

           var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_CONTROL_TYPE_ID%>"+nParametersCount);
           if( objFormEelement != null)
           {
              eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_CONTROL_TYPE_ID%>"+nParametersCount+".value  ='"+top.objNewDataView.m_arrDataViewInputParameters[k].m_nParameterControlType+"';");
           }   
           else      
           {
              var objHidden= document.createElement("INPUT");
              objHidden.type ="hidden";
              objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_CONTROL_TYPE_ID%>"+nParametersCount;
              objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_CONTROL_TYPE_ID%>"+nParametersCount;
              objHidden.value = top.objNewDataView.m_arrDataViewInputParameters[k].m_nParameterControlType;
              document.formDataViewMenu.appendChild(objHidden);
            }

           var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_DESC%>"+nParametersCount);
           if( objFormEelement != null)
           {
              eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_DESC%>"+nParametersCount+".value  ='"+top.objNewDataView.m_arrDataViewInputParameters[k].m_strParameterDescription+"';");
           }   
           else      
           {
              var objHidden= document.createElement("INPUT");
              objHidden.type ="hidden";
              objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_DESC%>"+nParametersCount;
              objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_DESC%>"+nParametersCount;
              objHidden.value = top.objNewDataView.m_arrDataViewInputParameters[k].m_strParameterDescription;
              document.formDataViewMenu.appendChild(objHidden);
            }

           var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_TYPE%>"+nParametersCount);
           if( objFormEelement != null)
           {
              eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_TYPE%>"+nParametersCount+".value  ='"+top.objNewDataView.m_arrDataViewInputParameters[k].m_nParameterDataType+"';");
           }   
           else      
           {
              var objHidden= document.createElement("INPUT");
              objHidden.type ="hidden";
              objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_TYPE%>"+nParametersCount;
              objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_TYPE%>"+nParametersCount;
              objHidden.value = top.objNewDataView.m_arrDataViewInputParameters[k].m_nParameterDataType;
              document.formDataViewMenu.appendChild(objHidden);
            }
            nParametersCount++;
        }
    }
    if(nParametersCount > 0)
    {
        var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAMS_NUM%>");
        if( objFormEelement != null)
        {
          eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAMS_NUM%>.value  ='"+nParametersCount+"';");
        }   
        else      
        {
          var objHidden= document.createElement("INPUT");
          objHidden.type ="hidden";
          objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAMS_NUM%>";
          objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAMS_NUM%>";
          objHidden.value = nParametersCount;
          document.formDataViewMenu.appendChild(objHidden);        
        }
    }
      
///////////////////////////////////////////////////////////////////////////////////
//                          FUNCTIONS
////////////////////////////////////////////////////////////////////////////////////
//alert("functions");
      for(var k=0;k<top.objNewDataView.m_arrDataViewFunctions.length;k++)
      {
        if(top.objNewDataView.m_arrDataViewFunctions[k] != null)
        {
           var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_ID%>"+nFunctionsCount);
           if( objFormEelement != null)
           {
              eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_ID%>"+nFunctionsCount+".value  ='"+top.objNewDataView.m_arrDataViewFunctions[k].m_nFunctionID+"';");
           }   
           else      
           {
              var objHidden= document.createElement("INPUT");
              objHidden.type ="hidden";
              objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_ID%>"+nFunctionsCount;
              objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_ID%>"+nFunctionsCount;
              objHidden.value = top.objNewDataView.m_arrDataViewFunctions[k].m_nFunctionID;
              document.formDataViewMenu.appendChild(objHidden);
            }
            
           var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount);
           if( objFormEelement != null)
           {
              eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+".value  ='"+top.objNewDataView.m_arrDataViewFunctions[k].m_strFunctionName+"';");
           }   
           else      
           {
              var objHidden= document.createElement("INPUT");
              objHidden.type ="hidden";
              objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount;
              objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount;
              objHidden.value = top.objNewDataView.m_arrDataViewFunctions[k].m_strFunctionName;
              document.formDataViewMenu.appendChild(objHidden);
            }
            var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_TYPE%>"+nFunctionsCount);
            if( objFormEelement != null)
            {
                eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_TYPE%>"+nFunctionsCount+".value  ='"+top.objNewDataView.m_arrDataViewFunctions[k].m_nFunctionType+"';");
            }   
            else      
            {
                var objHidden= document.createElement("INPUT");
                objHidden.type ="hidden";
                objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_TYPE%>"+nFunctionsCount;
                objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_TYPE%>"+nFunctionsCount;
                objHidden.value = top.objNewDataView.m_arrDataViewFunctions[k].m_nFunctionType;
                document.formDataViewMenu.appendChild(objHidden);        
            }
            var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_SQLCALL%>"+nFunctionsCount);
            if( objFormEelement != null)
            {
                    eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_SQLCALL%>"+nFunctionsCount+".value  ='"+top.objNewDataView.m_arrDataViewFunctions[k].m_strFunctionSQL+"';");
            }   
            else      
            {
                var objHidden= document.createElement("INPUT");
                objHidden.type ="hidden";
                objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_SQLCALL%>"+nFunctionsCount;
                objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_SQLCALL%>"+nFunctionsCount;
                objHidden.value = top.objNewDataView.m_arrDataViewFunctions[k].m_strFunctionSQL;
                document.formDataViewMenu.appendChild(objHidden);        
            }


            var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETERS_NUM%>"+nFunctionsCount);
            if( objFormEelement != null)
            {
                if(top.objNewDataView.m_arrDataViewFunctionParameters[k] != null)
                    eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETERS_NUM%>"+nFunctionsCount+".value  ='"+top.objNewDataView.m_arrDataViewFunctionParameters[k].length+"';");
                else    
                    eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETERS_NUM%>"+nFunctionsCount+".value  = 0;");
            }   
            else      
            {
                var objHidden= document.createElement("INPUT");
                objHidden.type ="hidden";
                objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETERS_NUM%>"+nFunctionsCount;
                objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETERS_NUM%>"+nFunctionsCount;
                if(top.objNewDataView.m_arrDataViewFunctionParameters[k] != null)
                   objHidden.value = top.objNewDataView.m_arrDataViewFunctionParameters[k].length;
                else    
                   objHidden.value = 0;
                document.formDataViewMenu.appendChild(objHidden);        
            }
            var arrParameterValues  = top.objNewDataView.m_arrDataViewFunctionParameters[k];
            if(arrParameterValues != null)
            {
                  for (var i = 0; i < arrParameterValues.length; i++) 
                  {
                      var objParameterValue = arrParameterValues[i];
                      if(objParameterValue != null)
                      {
                          var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_ID%>"+i);
                          if( objFormEelement != null)
                          {
                              eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_ID%>"+i+".value  ='"+objParameterValue.m_nParameterDefinitionID+"';");
                          }   
                          else      
                          {
                              var objHidden= document.createElement("INPUT");
                              objHidden.type ="hidden";
                              objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_ID%>"+i;
                              objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_ID%>"+i
                              objHidden.value = objParameterValue.m_nParameterDefinitionID;
                              document.formDataViewMenu.appendChild(objHidden);        

                          }
                      
                          var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_TYPE%>"+i);
                          if( objFormEelement != null)
                          {
                              eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_TYPE%>"+i+".value  ='"+objParameterValue.m_nParameterType+"';");
                          }   
                          else      
                          {
                              var objHidden= document.createElement("INPUT");
                              objHidden.type ="hidden";
                              objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_TYPE%>"+i;
                              objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_TYPE%>"+i
                              objHidden.value = objParameterValue.m_nParameterType;
                              document.formDataViewMenu.appendChild(objHidden);        
                          }

                          if(objParameterValue.m_nParameterType == <%=QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD%>)
                          {
                              var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_FIELD_ID%>"+i);
                              if( objFormEelement != null)
                              {
                                  eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_FIELD_ID%>"+i+".value  ='"+objParameterValue.m_objField.m_nFieldID+"';");
                              }   
                              else      
                              {
                                  var objHidden= document.createElement("INPUT");
                                  objHidden.type ="hidden";
                                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_FIELD_ID%>"+i;
                                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_FIELD_ID%>"+i
                                  objHidden.value = objParameterValue.m_objField.m_nFieldID;
                                  document.formDataViewMenu.appendChild(objHidden);        
                              }
                              var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_FIELD_NAME%>"+i);
                              if( objFormEelement != null)
                              {
                                  eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_FIELD_NAME%>"+i+".value  ='"+objParameterValue.m_objField.m_strFieldName+"';");
                              }   
                              else      
                              {
                                  var objHidden= document.createElement("INPUT");
                                  objHidden.type ="hidden";
                                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_FIELD_NAME%>"+i;
                                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_FIELD_NAME%>"+i
                                  objHidden.value = objParameterValue.m_objField.m_strFieldName;
                                  document.formDataViewMenu.appendChild(objHidden);        
                              }

                              var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_FIELD_DATAVIEW_ID%>"+i);
                              if( objFormEelement != null)
                              {
                                  eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_FIELD_DATAVIEW_ID%>"+i+".value  ='"+objParameterValue.m_objField.m_nDataViewID+"';");
                              }   
                              else      
                              {
                                  var objHidden= document.createElement("INPUT");
                                  objHidden.type ="hidden";
                                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_FIELD_DATAVIEW_ID%>"+i;
                                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_FIELD_DATAVIEW_ID%>"+i
                                  objHidden.value = objParameterValue.m_objField.m_nDataViewID;
                                  document.formDataViewMenu.appendChild(objHidden);        
                              }

                              var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_FIELD_DATAVIEW_NAME%>"+i);
                              if( objFormEelement != null)
                              {
                                  eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_FIELD_DATAVIEW_NAME%>"+i+".value  ='"+objParameterValue.m_objField.m_strDataViewName+"';");
                              }   
                              else      
                              {
                                  var objHidden= document.createElement("INPUT");
                                  objHidden.type ="hidden";
                                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_FIELD_DATAVIEW_NAME%>"+i;
                                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_FIELD_DATAVIEW_NAME%>"+i
                                  objHidden.value = objParameterValue.m_objField.m_strDataViewName;
                                  document.formDataViewMenu.appendChild(objHidden);        
                              }

                              var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_FIELD_DATAVIEW_TYPE%>"+i);
                              if( objFormEelement != null)
                              {
                                  eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_FIELD_DATAVIEW_TYPE%>"+i+".value  ='"+objParameterValue.m_objField.m_nDataViewType+"';");
                              }   
                              else      
                              {
                                  var objHidden= document.createElement("INPUT");
                                  objHidden.type ="hidden";
                                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_FIELD_DATAVIEW_TYPE%>"+i;
                                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_FIELD_DATAVIEW_TYPE%>"+i
                                  objHidden.value = objParameterValue.m_objField.m_nDataViewType;
                                  document.formDataViewMenu.appendChild(objHidden);        
                              }
                          }
                          else
                          {
                              if(objParameterValue.m_nParameterType == <%=QueryBuilderInterfaceKey.FIELD_TYPE_CONST_FIELD%>)                     
                              {

                                    var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_INDEX%>"+i);
                                    if( objFormEelement != null)
                                    {
                                        eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_INDEX%>"+i+".value  ='"+objParameterValue.m_nParameterIndex+"';");
                                    }   
                                    else      
                                    {
                                        var objHidden= document.createElement("INPUT");
                                        objHidden.type ="hidden";
                                        objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_INDEX%>"+i;
                                        objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_INDEX%>"+i
                                        objHidden.value = objParameterValue.m_nParameterIndex;
                                        document.formDataViewMenu.appendChild(objHidden);        
                                    }

                                    var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_VALUE%>"+i);
                                    if( objFormEelement != null)
                                    {
                                        eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_VALUE%>"+i+".value  ='"+objParameterValue.m_strParameterValue+"';");
                                    }   
                                    else      
                                    {
                                        var objHidden= document.createElement("INPUT");
                                        objHidden.type ="hidden";
                                        objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_VALUE%>"+i;
                                        objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_VALUE%>"+i
                                        objHidden.value = objParameterValue.m_strParameterValue;
                                        document.formDataViewMenu.appendChild(objHidden);        
                                    }

                              }
                              else if(objParameterValue.m_nParameterType == <%=QueryBuilderInterfaceKey.FIELD_TYPE_PARAMETER_FIELD%>)                     
                              {
                                    //waseem
                                    var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_INDEX%>"+i);
                                    if( objFormEelement != null)
                                    {
                                        eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_INDEX%>"+i+".value  ='"+objParameterValue.m_nParameterIndex+"';");
                                    }   
                                    else      
                                    {
                                        var objHidden= document.createElement("INPUT");
                                        objHidden.type ="hidden";
                                        objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_INDEX%>"+i;
                                        objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_INDEX%>"+i
                                        objHidden.value = objParameterValue.m_nParameterIndex;
                                        document.formDataViewMenu.appendChild(objHidden);        
                                    }

                                    var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_VALUE%>"+i);
                                    if( objFormEelement != null)
                                    {
                                        eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_VALUE%>"+i+".value  ='"+objParameterValue.m_strParameterValue+"';");
                                    }   
                                    else      
                                    {
                                        var objHidden= document.createElement("INPUT");
                                        objHidden.type ="hidden";
                                        objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_VALUE%>"+i;
                                        objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_VALUE%>"+i
                                        objHidden.value = objParameterValue.m_strParameterValue;
                                        document.formDataViewMenu.appendChild(objHidden);        
                                    }

                              }
                              else
                                  if(objParameterValue.m_nParameterType == <%=QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_FIELD%>)
                                  {
                                      var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_INDEX%>"+i);
                                      if( objFormEelement != null)
                                      {
                                          eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_INDEX%>"+i+".value  ='"+objParameterValue.m_nParameterIndex+"';");
                                      }   
                                      else      
                                      {
                                          var objHidden= document.createElement("INPUT");
                                          objHidden.type ="hidden";
                                          objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_INDEX%>"+i;
                                          objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_INDEX%>"+i
                                          objHidden.value = objParameterValue.m_nParameterIndex;
                                          document.formDataViewMenu.appendChild(objHidden);        
                                      }

                                  }
                            }
                       }     
                  }
            }            

            nFunctionsCount++;
       }   
    }
    if(nFunctionsCount > 0)
    {
        var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELDS_NUM%>");
        if( objFormEelement != null)
        {
          eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELDS_NUM%>.value  ='"+nFunctionsCount+"';");
        }   
        else      
        {
          var objHidden= document.createElement("INPUT");
          objHidden.type ="hidden";
          objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELDS_NUM%>";
          objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELDS_NUM%>";
          objHidden.value = nFunctionsCount;
          document.formDataViewMenu.appendChild(objHidden);        
        }
     }














//alert('terms');
///////////////////////////////////////////////////////////////////////////////////
//                          Terms
////////////////////////////////////////////////////////////////////////////////////

      for(var l=0;l<top.objNewDataView.m_arrDataViewTerms.length;l++)
      {
        if(top.objNewDataView.m_arrDataViewTerms[l] != null)
        {
          var objTerm = top.objNewDataView.m_arrDataViewTerms[l];
          if(objTerm.m_nTermType == <%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_TYPE%>)
          {
                var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_TERM_NAME%>"+nFtFTermsCount);
                if( objFormEelement != null)
                {
                  eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_TERM_NAME%>"+nFtFTermsCount+".value  ='"+objTerm.m_strTermName+"';");
                }   
                else      
                {
                  var objHidden= document.createElement("INPUT");
                  objHidden.type ="hidden";
                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_TERM_NAME%>"+nFtFTermsCount;
                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_TERM_NAME%>"+nFtFTermsCount;
                  objHidden.value = objTerm.m_strTermName;
                  document.formDataViewMenu.appendChild(objHidden);
                }

////////////////////////////////////////////////////////////////////////////////////
//  Field to Field Term 1st Operand Filling
/////////////////////////////////////////////////////////////////////////////////////
                
                var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_1ST_FIELD_ID%>"+nFtFTermsCount);
                if( objFormEelement != null)
                {
                  eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_1ST_FIELD_ID%>"+nFtFTermsCount+".value  ='"+objTerm.m_obj1stOperand.m_nFieldID+"';");
                }   
                else      
                {
                  var objHidden= document.createElement("INPUT");
                  objHidden.type ="hidden";
                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_1ST_FIELD_ID%>"+nFtFTermsCount;
                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_1ST_FIELD_ID%>"+nFtFTermsCount;
                  objHidden.value = objTerm.m_obj1stOperand.m_nFieldID;
                  document.formDataViewMenu.appendChild(objHidden);
                }

                var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_1ST_FIELD_NAME%>"+nFtFTermsCount);
                if( objFormEelement != null)
                {
                  eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_1ST_FIELD_NAME%>"+nFtFTermsCount+".value  ='"+objTerm.m_obj1stOperand.m_strFieldName+"';");
                }   
                else      
                {
                  var objHidden= document.createElement("INPUT");
                  objHidden.type ="hidden";
                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_1ST_FIELD_NAME%>"+nFtFTermsCount;
                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_1ST_FIELD_NAME%>"+nFtFTermsCount;
                  objHidden.value = objTerm.m_obj1stOperand.m_strFieldName;
                  document.formDataViewMenu.appendChild(objHidden);
                }

                var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_1ST_FIELD_TYPE%>"+nFtFTermsCount);
                if( objFormEelement != null)
                {
                  eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_1ST_FIELD_TYPE%>"+nFtFTermsCount+".value  ='"+objTerm.m_obj1stOperand.m_nFieldType+"';");
                }   
                else      
                {
                  var objHidden= document.createElement("INPUT");
                  objHidden.type ="hidden";
                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_1ST_FIELD_TYPE%>"+nFtFTermsCount;
                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_1ST_FIELD_TYPE%>"+nFtFTermsCount;
                  objHidden.value = objTerm.m_obj1stOperand.m_nFieldType;
                  document.formDataViewMenu.appendChild(objHidden);
                }

                if(objTerm.m_obj1stOperand.m_nFieldType == <%=QueryBuilderInterfaceKey.FIELD_TYPE_CONST_FIELD%>)
                {
                  var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_1ST_FIELD_VALUE%>"+nFtFTermsCount);
                  if( objFormEelement != null)
                  {
                    eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_1ST_FIELD_VALUE%>"+nFtFTermsCount+".value  ='"+top.objNewDataView.m_arrDataViewConstants[objTerm.m_obj1stOperand.m_nFieldIndex].m_strConstantValue+"';");
                  }   
                  else      
                  {
                    var objHidden= document.createElement("INPUT");
                    objHidden.type ="hidden";
                    objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_1ST_FIELD_VALUE%>"+nFtFTermsCount;
                    objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_1ST_FIELD_VALUE%>"+nFtFTermsCount;
                    objHidden.value = top.objNewDataView.m_arrDataViewConstants[objTerm.m_obj1stOperand.m_nFieldIndex].m_strConstantValue;
                    document.formDataViewMenu.appendChild(objHidden);
                  }   
                }
                else
                {
                  if(objTerm.m_obj1stOperand.m_nFieldType == <%=QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD%>)
                  {
                    var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_1ST_FIELD_DATAVIEW_ID%>"+nFtFTermsCount);
                    if( objFormEelement != null)
                    {
                      eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_1ST_FIELD_DATAVIEW_ID%>"+nFtFTermsCount+".value  ='"+objTerm.m_obj1stOperand.m_nDataViewID+"';");
                    }   
                    else      
                    {
                      var objHidden= document.createElement("INPUT");
                      objHidden.type ="hidden";
                      objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_1ST_FIELD_DATAVIEW_ID%>"+nFtFTermsCount;
                      objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_1ST_FIELD_DATAVIEW_ID%>"+nFtFTermsCount;
                      objHidden.value = objTerm.m_obj1stOperand.m_nDataViewID;
                      document.formDataViewMenu.appendChild(objHidden);
                    }   

                    var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_1ST_FIELD_DATAVIEW_NAME%>"+nFtFTermsCount);
                    if( objFormEelement != null)
                    {
                      eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_1ST_FIELD_DATAVIEW_NAME%>"+nFtFTermsCount+".value  ='"+objTerm.m_obj1stOperand.m_strDataViewName+"';");
                    }   
                    else      
                    {
                      var objHidden= document.createElement("INPUT");
                      objHidden.type ="hidden";
                      objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_1ST_FIELD_DATAVIEW_NAME%>"+nFtFTermsCount;
                      objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_1ST_FIELD_DATAVIEW_NAME%>"+nFtFTermsCount;
                      objHidden.value = objTerm.m_obj1stOperand.m_strDataViewName;
                      document.formDataViewMenu.appendChild(objHidden);
                    }

                    var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_1ST_FIELD_DATAVIEW_TYPE%>"+nFtFTermsCount);
                    if( objFormEelement != null)
                    {
                      eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_1ST_FIELD_DATAVIEW_TYPE%>"+nFtFTermsCount+".value  ='"+objTerm.m_obj1stOperand.m_nDataViewType+"';");
                    }   
                    else      
                    {
                      var objHidden= document.createElement("INPUT");
                      objHidden.type ="hidden";
                      objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_1ST_FIELD_DATAVIEW_TYPE%>"+nFtFTermsCount;
                      objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_1ST_FIELD_DATAVIEW_TYPE%>"+nFtFTermsCount;
                      objHidden.value = objTerm.m_obj1stOperand.m_nDataViewType;
                      document.formDataViewMenu.appendChild(objHidden);
                    }   

                   }
                }

                
////////////////////////////////////////////////////////////////////////////////////
//  Field to Field Term Operator Filling
/////////////////////////////////////////////////////////////////////////////////////
                var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_OPERATOR_ID%>"+nFtFTermsCount);
                if( objFormEelement != null)
                {
                  eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_OPERATOR_ID%>"+nFtFTermsCount+".value  ='"+top.objDataViewWizard.m_arrDataViewFtFOperators[objTerm.m_nOperatorIndex].m_nOperatorID+"';");
                }   
                else      
                {
                  var objHidden= document.createElement("INPUT");
                  objHidden.type ="hidden";
                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_OPERATOR_ID%>"+nFtFTermsCount;
                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_OPERATOR_ID%>"+nFtFTermsCount;
                  objHidden.value = top.objDataViewWizard.m_arrDataViewFtFOperators[objTerm.m_nOperatorIndex].m_nOperatorID;
                  document.formDataViewMenu.appendChild(objHidden);
                }

                var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_OPERATOR_SQL%>"+nFtFTermsCount);
                if( objFormEelement != null)
                {
                  eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_OPERATOR_SQL%>"+nFtFTermsCount+".value  ='"+top.objDataViewWizard.m_arrDataViewFtFOperators[objTerm.m_nOperatorIndex].m_strOperatorSQL+"';");
                }   
                else      
                {
                  var objHidden= document.createElement("INPUT");
                  objHidden.type ="hidden";
                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_OPERATOR_SQL%>"+nFtFTermsCount;
                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_OPERATOR_SQL%>"+nFtFTermsCount;
                  objHidden.value = top.objDataViewWizard.m_arrDataViewFtFOperators[objTerm.m_nOperatorIndex].m_strOperatorSQL;
                  document.formDataViewMenu.appendChild(objHidden);
                }

                var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_OPERATOR_NAME%>"+nFtFTermsCount);
                if( objFormEelement != null)
                {
                  eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_OPERATOR_NAME%>"+nFtFTermsCount+".value  ='"+top.objDataViewWizard.m_arrDataViewFtFOperators[objTerm.m_nOperatorIndex].m_strOperatorName+"';");
                }   
                else      
                {
                  var objHidden= document.createElement("INPUT");
                  objHidden.type ="hidden";
                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_OPERATOR_NAME%>"+nFtFTermsCount;
                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_OPERATOR_NAME%>"+nFtFTermsCount;
                  objHidden.value = top.objDataViewWizard.m_arrDataViewFtFOperators[objTerm.m_nOperatorIndex].m_strOperatorName;
                  document.formDataViewMenu.appendChild(objHidden);
                }

////////////////////////////////////////////////////////////////////////////////////
//  Field to Field Term 2nd Operand Filling
/////////////////////////////////////////////////////////////////////////////////////

                var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_2ND_FIELD_ID%>"+nFtFTermsCount);
                if( objFormEelement != null)
                {
                  eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_2ND_FIELD_ID%>"+nFtFTermsCount+".value  ='"+objTerm.m_obj2ndOperand.m_nFieldID+"';");
                }   
                else      
                {
                  var objHidden= document.createElement("INPUT");
                  objHidden.type ="hidden";
                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_2ND_FIELD_ID%>"+nFtFTermsCount;
                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_2ND_FIELD_ID%>"+nFtFTermsCount;
                  objHidden.value = objTerm.m_obj2ndOperand.m_nFieldID;
                  document.formDataViewMenu.appendChild(objHidden);
                }
                
                var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_2ND_FIELD_NAME%>"+nFtFTermsCount);
                if( objFormEelement != null)
                {
                  eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_2ND_FIELD_NAME%>"+nFtFTermsCount+".value  ='"+objTerm.m_obj2ndOperand.m_strFieldName+"';");
                }   
                else      
                {
                  var objHidden= document.createElement("INPUT");
                  objHidden.type ="hidden";
                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_2ND_FIELD_NAME%>"+nFtFTermsCount;
                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_2ND_FIELD_NAME%>"+nFtFTermsCount;
                  objHidden.value = objTerm.m_obj2ndOperand.m_strFieldName;
                  document.formDataViewMenu.appendChild(objHidden);
                }

                var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_2ND_FIELD_TYPE%>"+nFtFTermsCount);
                if( objFormEelement != null)
                {
                  eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_2ND_FIELD_TYPE%>"+nFtFTermsCount+".value  ='"+objTerm.m_obj2ndOperand.m_nFieldType+"';");
                }   
                else      
                {
                  var objHidden= document.createElement("INPUT");
                  objHidden.type ="hidden";
                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_2ND_FIELD_TYPE%>"+nFtFTermsCount;
                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_2ND_FIELD_TYPE%>"+nFtFTermsCount;
                  objHidden.value = objTerm.m_obj2ndOperand.m_nFieldType;
                  document.formDataViewMenu.appendChild(objHidden);
                }

                if(objTerm.m_obj2ndOperand.m_nFieldType == <%=QueryBuilderInterfaceKey.FIELD_TYPE_CONST_FIELD%>)
                {
                  var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_2ND_FIELD_VALUE%>"+nFtFTermsCount);
                  if( objFormEelement != null)
                  {
                    eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_2ND_FIELD_VALUE%>"+nFtFTermsCount+".value  ='"+top.objNewDataView.m_arrDataViewConstants[objTerm.m_obj2ndOperand.m_nFieldIndex].m_strConstantValue+"';");
                  }   
                  else      
                  {
                    var objHidden= document.createElement("INPUT");
                    objHidden.type ="hidden";
                    objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_2ND_FIELD_VALUE%>"+nFtFTermsCount;
                    objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_2ND_FIELD_VALUE%>"+nFtFTermsCount;
                    objHidden.value = top.objNewDataView.m_arrDataViewConstants[objTerm.m_obj2ndOperand.m_nFieldIndex].m_strConstantValue;
                    document.formDataViewMenu.appendChild(objHidden);
                  }   
                }
                else
                {
                  if(objTerm.m_obj2ndOperand.m_nFieldType == <%=QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD%>)
                  {
                    var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_2ND_FIELD_DATAVIEW_ID%>"+nFtFTermsCount);
                    if( objFormEelement != null)
                    {
                      eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_2ND_FIELD_DATAVIEW_ID%>"+nFtFTermsCount+".value  ='"+objTerm.m_obj2ndOperand.m_nDataViewID+"';");
                    }   
                    else      
                    {
                      var objHidden= document.createElement("INPUT");
                      objHidden.type ="hidden";
                      objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_2ND_FIELD_DATAVIEW_ID%>"+nFtFTermsCount;
                      objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_2ND_FIELD_DATAVIEW_ID%>"+nFtFTermsCount;
                      objHidden.value = objTerm.m_obj2ndOperand.m_nDataViewID;
                      document.formDataViewMenu.appendChild(objHidden);
                    }   
                    
                    var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_2ND_FIELD_DATAVIEW_NAME%>"+nFtFTermsCount);
                    if( objFormEelement != null)
                    {
                      eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_2ND_FIELD_DATAVIEW_NAME%>"+nFtFTermsCount+".value  ='"+objTerm.m_obj2ndOperand.m_strDataViewName+"';");
                    }   
                    else      
                    {
                      var objHidden= document.createElement("INPUT");
                      objHidden.type ="hidden";
                      objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_2ND_FIELD_DATAVIEW_NAME%>"+nFtFTermsCount;
                      objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_2ND_FIELD_DATAVIEW_NAME%>"+nFtFTermsCount;
                      objHidden.value = objTerm.m_obj2ndOperand.m_strDataViewName;
                      document.formDataViewMenu.appendChild(objHidden);
                    }   

                    var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_2ND_FIELD_DATAVIEW_TYPE%>"+nFtFTermsCount);
                    if( objFormEelement != null)
                    {
                      eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_2ND_FIELD_DATAVIEW_TYPE%>"+nFtFTermsCount+".value  ='"+objTerm.m_obj2ndOperand.m_nDataViewType+"';");
                    }   
                    else      
                    {
                      var objHidden= document.createElement("INPUT");
                      objHidden.type ="hidden";
                      objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_2ND_FIELD_DATAVIEW_TYPE%>"+nFtFTermsCount;
                      objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_2ND_FIELD_DATAVIEW_TYPE%>"+nFtFTermsCount;
                      objHidden.value = objTerm.m_obj2ndOperand.m_nDataViewType;
                      document.formDataViewMenu.appendChild(objHidden);
                    }   
                  }   
                }

                nFtFTermsCount++;
          }   
          else // else Field to Data View Term
          {
               var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_TERM_NAME%>"+nFtDVTermsCount);
               if( objFormEelement != null)
               {
                  eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_TERM_NAME%>"+nFtDVTermsCount+".value  ='"+objTerm.m_strTermName+"';");
               }   
               else      
               {
                  var objHidden= document.createElement("INPUT");
                  objHidden.type ="hidden";
                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_TERM_NAME%>"+nFtDVTermsCount;
                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_TERM_NAME%>"+nFtDVTermsCount;
                  objHidden.value = objTerm.m_strTermName;
                  document.formDataViewMenu.appendChild(objHidden);
               }

////////////////////////////////////////////////////////////////////////////////////
//  Field to Data View Term 1st Operand Filling
/////////////////////////////////////////////////////////////////////////////////////
//alert ("m2");                
                var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_1ST_FIELD_ID%>"+nFtDVTermsCount);
                if( objFormEelement != null)
                {
                  eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_1ST_FIELD_ID%>"+nFtDVTermsCount+".value  ='"+objTerm.m_obj1stOperand.m_nFieldID+"';");
                }   
                else      
                {
                  var objHidden= document.createElement("INPUT");
                  objHidden.type ="hidden";
                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_1ST_FIELD_ID%>"+nFtDVTermsCount;
                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_1ST_FIELD_ID%>"+nFtDVTermsCount;
                  objHidden.value = objTerm.m_obj1stOperand.m_nFieldID;
                  document.formDataViewMenu.appendChild(objHidden);
                }

                var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_1ST_FIELD_NAME%>"+nFtDVTermsCount);
                if( objFormEelement != null)
                {
                  eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_1ST_FIELD_NAME%>"+nFtDVTermsCount+".value  ='"+objTerm.m_obj1stOperand.m_strFieldName+"';");
                }   
                else      
                {
                  var objHidden= document.createElement("INPUT");
                  objHidden.type ="hidden";
                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_1ST_FIELD_NAME%>"+nFtDVTermsCount;
                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_1ST_FIELD_NAME%>"+nFtDVTermsCount;
                  objHidden.value = objTerm.m_obj1stOperand.m_strFieldName;
                  document.formDataViewMenu.appendChild(objHidden);
                }

                var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_1ST_FIELD_TYPE%>"+nFtDVTermsCount);
                if( objFormEelement != null)
                {
                  eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_1ST_FIELD_TYPE%>"+nFtDVTermsCount+".value  ='"+objTerm.m_obj1stOperand.m_nFieldType+"';");
                }   
                else      
                {
                  var objHidden= document.createElement("INPUT");
                  objHidden.type ="hidden";
                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_1ST_FIELD_TYPE%>"+nFtDVTermsCount;
                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_1ST_FIELD_TYPE%>"+nFtDVTermsCount;
                  objHidden.value = objTerm.m_obj1stOperand.m_nFieldType;
                  document.formDataViewMenu.appendChild(objHidden);
                }

                if(objTerm.m_obj1stOperand.m_nFieldType == <%=QueryBuilderInterfaceKey.FIELD_TYPE_CONST_FIELD%>)
                {
                  var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_1ST_FIELD_VALUE%>"+nFtDVTermsCount);
                  if( objFormEelement != null)
                  {
                    eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_1ST_FIELD_VALUE%>"+nFtDVTermsCount+".value  ='"+top.objNewDataView.m_arrDataViewConstants[objTerm.m_obj1stOperand.m_nFieldIndex].m_strConstantValue+"';");
                  }   
                  else      
                  {
                    var objHidden= document.createElement("INPUT");
                    objHidden.type ="hidden";
                    objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_1ST_FIELD_VALUE%>"+nFtDVTermsCount;
                    objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_1ST_FIELD_VALUE%>"+nFtDVTermsCount;
                    objHidden.value = top.objNewDataView.m_arrDataViewConstants[objTerm.m_obj1stOperand.m_nFieldIndex].m_strConstantValue;
                    document.formDataViewMenu.appendChild(objHidden);
                  }   
                }
                else
                {
                  if(objTerm.m_obj1stOperand.m_nFieldType == <%=QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD%>)
                  {
                    var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_1ST_FIELD_DATAVIEW_ID%>"+nFtDVTermsCount);
                    if( objFormEelement != null)
                    {
                      eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_1ST_FIELD_DATAVIEW_ID%>"+nFtDVTermsCount+".value  ='"+objTerm.m_obj1stOperand.m_nDataViewID+"';");
                    }   
                    else      
                    {
                      var objHidden= document.createElement("INPUT");
                      objHidden.type ="hidden";
                      objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_1ST_FIELD_DATAVIEW_ID%>"+nFtDVTermsCount;
                      objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_1ST_FIELD_DATAVIEW_ID%>"+nFtDVTermsCount;
                      objHidden.value = objTerm.m_obj1stOperand.m_nDataViewID;
                      document.formDataViewMenu.appendChild(objHidden);
                    }   

                    var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_1ST_FIELD_DATAVIEW_NAME%>"+nFtDVTermsCount);
                    if( objFormEelement != null)
                    {
                      eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_1ST_FIELD_DATAVIEW_NAME%>"+nFtDVTermsCount+".value  ='"+objTerm.m_obj1stOperand.m_strDataViewName+"';");
                    }   
                    else      
                    {
                      var objHidden= document.createElement("INPUT");
                      objHidden.type ="hidden";
                      objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_1ST_FIELD_DATAVIEW_NAME%>"+nFtDVTermsCount;
                      objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_1ST_FIELD_DATAVIEW_NAME%>"+nFtDVTermsCount;
                      objHidden.value = objTerm.m_obj1stOperand.m_strDataViewName;
                      document.formDataViewMenu.appendChild(objHidden);
                    }   

                    var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_1ST_FIELD_DATAVIEW_TYPE%>"+nFtDVTermsCount);
                    if( objFormEelement != null)
                    {
                      eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_1ST_FIELD_DATAVIEW_TYPE%>"+nFtDVTermsCount+".value  ='"+objTerm.m_obj1stOperand.m_nDataViewType+"';");
                    }   
                    else      
                    {
                      var objHidden= document.createElement("INPUT");
                      objHidden.type ="hidden";
                      objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_1ST_FIELD_DATAVIEW_TYPE%>"+nFtDVTermsCount;
                      objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_1ST_FIELD_DATAVIEW_TYPE%>"+nFtDVTermsCount;
                      objHidden.value = objTerm.m_obj1stOperand.m_nDataViewType;
                      document.formDataViewMenu.appendChild(objHidden);
                    }   

                   }
                }

////////////////////////////////////////////////////////////////////////////////////
//  Field to Data View Term Operator Filling
/////////////////////////////////////////////////////////////////////////////////////
                var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_OPERATOR_ID%>"+nFtDVTermsCount);
                if( objFormEelement != null)
                {
                  eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_OPERATOR_ID%>"+nFtDVTermsCount+".value  ='"+top.objDataViewWizard.m_arrDataViewFtDVOperators[objTerm.m_nOperatorIndex].m_nOperatorID+"';");
                }   
                else      
                {
                  var objHidden= document.createElement("INPUT");
                  objHidden.type ="hidden";
                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_OPERATOR_ID%>"+nFtDVTermsCount;
                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_OPERATOR_ID%>"+nFtDVTermsCount;
                  objHidden.value = top.objDataViewWizard.m_arrDataViewFtDVOperators[objTerm.m_nOperatorIndex].m_nOperatorID;
                  document.formDataViewMenu.appendChild(objHidden);
                }

                var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_OPERATOR_SQL%>"+nFtDVTermsCount);
                if( objFormEelement != null)
                {
                  eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_OPERATOR_SQL%>"+nFtDVTermsCount+".value  ='"+top.objDataViewWizard.m_arrDataViewFtDVOperators[objTerm.m_nOperatorIndex].m_strOperatorSQL+"';");
                }   
                else      
                {
                  var objHidden= document.createElement("INPUT");
                  objHidden.type ="hidden";
                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_OPERATOR_SQL%>"+nFtDVTermsCount;
                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_OPERATOR_SQL%>"+nFtDVTermsCount;
                  objHidden.value = top.objDataViewWizard.m_arrDataViewFtDVOperators[objTerm.m_nOperatorIndex].m_strOperatorSQL;
                  document.formDataViewMenu.appendChild(objHidden);
                }

////////////////////////////////////////////////////////////////////////////////////
//  Field to Data View Term 2nd Operand Filling
/////////////////////////////////////////////////////////////////////////////////////

                var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_RELATED_DATAVIEW_ID%>"+nFtDVTermsCount);
                if( objFormEelement != null)
                {
                  eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_RELATED_DATAVIEW_ID%>"+nFtDVTermsCount+".value  ='"+objTerm.m_obj2ndOperand.m_nDataViewID+"';");
                }   
                else      
                {
                  var objHidden= document.createElement("INPUT");
                  objHidden.type ="hidden";
                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_RELATED_DATAVIEW_ID%>"+nFtDVTermsCount;
                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_RELATED_DATAVIEW_ID%>"+nFtDVTermsCount;
                  objHidden.value = objTerm.m_obj2ndOperand.m_nDataViewID;
                  document.formDataViewMenu.appendChild(objHidden);
                }

                var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_RELATED_DATAVIEW_NAME%>"+nFtDVTermsCount);
                if( objFormEelement != null)
                {
                  eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_RELATED_DATAVIEW_NAME%>"+nFtDVTermsCount+".value  ='"+objTerm.m_obj2ndOperand.m_strDataViewName+"';");
                }   
                else      
                {
                  var objHidden= document.createElement("INPUT");
                  objHidden.type ="hidden";
                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_RELATED_DATAVIEW_NAME%>"+nFtDVTermsCount;
                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_RELATED_DATAVIEW_NAME%>"+nFtDVTermsCount;
                  objHidden.value = objTerm.m_obj2ndOperand.m_strDataViewName;
                  document.formDataViewMenu.appendChild(objHidden);
                }

                var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_RELATED_DATAVIEW_TYPE_ID%>"+nFtDVTermsCount);
                if( objFormEelement != null)
                {
                  eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_RELATED_DATAVIEW_TYPE_ID%>"+nFtDVTermsCount+".value  ='"+objTerm.m_obj2ndOperand.m_nDataViewType+"';");
                }   
                else      
                {
                  var objHidden= document.createElement("INPUT");
                  objHidden.type ="hidden";
                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_RELATED_DATAVIEW_TYPE_ID%>"+nFtDVTermsCount;
                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_RELATED_DATAVIEW_TYPE_ID%>"+nFtDVTermsCount;
                  objHidden.value = objTerm.m_obj2ndOperand.m_nDataViewType;
                  document.formDataViewMenu.appendChild(objHidden);
                }

             nFtDVTermsCount++;   
             }
          }
       }

     if(nFtFTermsCount > 0)
     {
        var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_NUM%>");
        if( objFormEelement != null)
        {
          eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_NUM%>.value  ='"+nFtFTermsCount+"';");
        }   
        else      
        {
          var objHidden= document.createElement("INPUT");
          objHidden.type ="hidden";
          objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_NUM%>";
          objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_NUM%>";
          objHidden.value = nFtFTermsCount;
          document.formDataViewMenu.appendChild(objHidden);        
        }
     }
     if(nFtDVTermsCount > 0)
     {
          var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_NUM%>");
          if( objFormEelement != null)
          {
            eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_NUM%>.value  ='"+nFtDVTermsCount+"';");
          }   
          else      
          {
            var objHidden= document.createElement("INPUT");
            objHidden.type ="hidden";
            objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_NUM%>";
            objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_NUM%>";
            objHidden.value = nFtDVTermsCount;
            document.formDataViewMenu.appendChild(objHidden);        
          }
       }



///alert("where cl");
///////////////////////////////////////////////////////////////////////////////////
//                          Where Clause
////////////////////////////////////////////////////////////////////////////////////
      for(var m=0;m<top.objNewDataView.m_arrDataViewWhereElements.length;m++)
      {
        if(top.objNewDataView.m_arrDataViewWhereElements[m] != null)
        {
            var objWhereElement = top.objNewDataView.m_arrDataViewWhereElements[m];
            var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE%>"+nWhereCount);
            if( objFormEelement != null)
            {
              eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE%>"+nWhereCount+".value  ='"+objWhereElement.m_nElementTypeID+"';");
            }   
            else      
            {
              var objHidden= document.createElement("INPUT");
              objHidden.type ="hidden";
              objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE%>"+nWhereCount;
              objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE%>"+nWhereCount;
              objHidden.value = objWhereElement.m_nElementTypeID;
              document.formDataViewMenu.appendChild(objHidden);
            }

            if(objWhereElement.m_nElementTypeID == <%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE_TERM%>)
            {
                var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_ELEMENT_ID%>"+nWhereCount);
                if( objFormEelement != null)
                {
                  eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_ELEMENT_ID%>"+nWhereCount+".value  ='"+objWhereElement.m_nElementIndex+"';");
                }   
                else      
                {
                  var objHidden= document.createElement("INPUT");
                  objHidden.type ="hidden";
                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_ELEMENT_ID%>"+nWhereCount;
                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_ELEMENT_ID%>"+nWhereCount;
                  objHidden.value = objWhereElement.m_nElementIndex;
                  document.formDataViewMenu.appendChild(objHidden);
                }
           }
           else
           {
               if(objWhereElement.m_nElementTypeID == <%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE_SYMBOL%>)
               {
                    var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_ELEMENT_ID%>"+nWhereCount);
                    if( objFormEelement != null)
                    {
                      eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_ELEMENT_ID%>"+nWhereCount+".value  ='"+objWhereElement.m_nRelationSymbolID+"';");
                    }   
                    else      
                    {
                      var objHidden= document.createElement("INPUT");
                      objHidden.type ="hidden";
                      objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_ELEMENT_ID%>"+nWhereCount;
                      objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_ELEMENT_ID%>"+nWhereCount;
                      objHidden.value = objWhereElement.m_nRelationSymbolID;
                      document.formDataViewMenu.appendChild(objHidden);
                    }

                    var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_SYMBOL_SQL%>"+nWhereCount);
                    if( objFormEelement != null)
                    {
                      eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_SYMBOL_SQL%>"+nWhereCount+".value  ='"+objWhereElement.m_strRelationSymbolSQL+"';");
                    }   
                    else      
                    {
                      var objHidden= document.createElement("INPUT");
                      objHidden.type ="hidden";
                      objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_SYMBOL_SQL%>"+nWhereCount;
                      objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_SYMBOL_SQL%>"+nWhereCount;
                      objHidden.value = objWhereElement.m_strRelationSymbolSQL;
                      document.formDataViewMenu.appendChild(objHidden);
                    }
               }   
           }// End Else for the type
           nWhereCount++;
       }// End If element == null

     }// End For 
     if(nWhereCount > 0)
     {
        var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERES_NUM%>");
        if( objFormEelement != null)
        {
          eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERES_NUM%>.value  ='"+nWhereCount+"';");
        }   
        else      
        {
          var objHidden= document.createElement("INPUT");
          objHidden.type ="hidden";
          objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERES_NUM%>";
          objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERES_NUM%>";
          objHidden.value = nWhereCount;
          document.formDataViewMenu.appendChild(objHidden);        
        }
     }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//                                                                    ORDER BY CLAUSE 
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

      for(var l=0;l<top.objNewDataView.m_arrDataViewOrderBy.length;l++)
      {
        if(top.objNewDataView.m_arrDataViewOrderBy[l] != null)
        {
          var objOrderBy = top.objNewDataView.m_arrDataViewOrderBy[l];
          
          var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_TYPE%>"+nOrderByCount);
          if( objFormEelement != null)
          {
            eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_TYPE%>"+nOrderByCount+".value  ='"+objOrderBy.m_objOrderByField.m_nFieldType+"';");
          }   
          else      
          {
            var objHidden= document.createElement("INPUT");
            objHidden.type ="hidden";
            objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_TYPE%>"+nOrderByCount;
            objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_TYPE%>"+nOrderByCount;
            objHidden.value = objOrderBy.m_objOrderByField.m_nFieldType;
            document.formDataViewMenu.appendChild(objHidden);
          }

          var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_ORDER_TYPE%>"+nOrderByCount);
          if( objFormEelement != null)
          {
            eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_ORDER_TYPE%>"+nOrderByCount+".value  ='"+objOrderBy.m_strOrderByType+"';");
          }   
          else      
          {
            var objHidden= document.createElement("INPUT");
            objHidden.type ="hidden";
            objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_ORDER_TYPE%>"+nOrderByCount;
            objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_ORDER_TYPE%>"+nOrderByCount;
            objHidden.value = objOrderBy.m_strOrderByType;
            document.formDataViewMenu.appendChild(objHidden);
          }
          
          if(objOrderBy.m_nOrderByElementTypeID == <%=QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD%>)
          {
                var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_ID%>"+nOrderByCount);
                if( objFormEelement != null)
                {
                  eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_ID%>"+nOrderByCount+".value  ='"+objOrderBy.m_objOrderByField.m_nFieldID+"';");
                }   
                else      
                {
                  var objHidden= document.createElement("INPUT");
                  objHidden.type ="hidden";
                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_ID%>"+nOrderByCount;
                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_ID%>"+nOrderByCount;
                  objHidden.value = objOrderBy.m_objOrderByField.m_nFieldID;
                  document.formDataViewMenu.appendChild(objHidden);
                }

                var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_NAME%>"+nOrderByCount);
                if( objFormEelement != null)
                {
                  eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_NAME%>"+nOrderByCount+".value  ='"+objOrderBy.m_objOrderByField.m_strFieldName+"';");
                }   
                else      
                {
                  var objHidden= document.createElement("INPUT");
                  objHidden.type ="hidden";
                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_NAME%>"+nOrderByCount;
                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_NAME%>"+nOrderByCount;
                  objHidden.value = objOrderBy.m_objOrderByField.m_strFieldName;
                  document.formDataViewMenu.appendChild(objHidden);
                }

                var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_DATAVIEW_ID%>"+nOrderByCount);
                if( objFormEelement != null)
                {
                  eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_DATAVIEW_ID%>"+nOrderByCount+".value  ='"+objOrderBy.m_objOrderByField.m_nDataViewID+"';");
                }   
                else      
                {
                  var objHidden= document.createElement("INPUT");
                  objHidden.type ="hidden";
                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_DATAVIEW_ID%>"+nOrderByCount;
                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_DATAVIEW_ID%>"+nOrderByCount;
                  objHidden.value = objOrderBy.m_objOrderByField.m_nDataViewID;
                  document.formDataViewMenu.appendChild(objHidden);
                }   

                var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_DATAVIEW_NAME%>"+nOrderByCount);
                if( objFormEelement != null)
                {
                  eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_DATAVIEW_NAME%>"+nOrderByCount+".value  ='"+objOrderBy.m_objOrderByField.m_strDataViewName+"';");
                }   
                else      
                {
                  var objHidden= document.createElement("INPUT");
                  objHidden.type ="hidden";
                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_DATAVIEW_NAME%>"+nOrderByCount;
                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_DATAVIEW_NAME%>"+nOrderByCount;
                  objHidden.value = objOrderBy.m_objOrderByField.m_strDataViewName;
                  document.formDataViewMenu.appendChild(objHidden);
                }

                var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_DATAVIEW_TYPE%>"+nOrderByCount);
                if( objFormEelement != null)
                {
                  eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_DATAVIEW_TYPE%>"+nOrderByCount+".value  ='"+objOrderBy.m_objOrderByField.m_nDataViewType+"';");
                }   
                else      
                {
                  var objHidden= document.createElement("INPUT");
                  objHidden.type ="hidden";
                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_DATAVIEW_TYPE%>"+nOrderByCount;
                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_DATAVIEW_TYPE%>"+nOrderByCount;
                  objHidden.value = objOrderBy.m_objOrderByField.m_nDataViewType;
                  document.formDataViewMenu.appendChild(objHidden);
                }

                var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_DESCRIPTION%>"+nOrderByCount);
                if( objFormEelement != null)
                {
                  eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_DESCRIPTION%>"+nOrderByCount+".value  ='"+objOrderBy.m_objOrderByField.m_strFieldDescription+"';");
                }   
                else      
                {
                  var objHidden= document.createElement("INPUT");
                  objHidden.type ="hidden";
                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_DESCRIPTION%>"+nOrderByCount;
                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_DESCRIPTION%>"+nOrderByCount;
                  objHidden.value = objOrderBy.m_objOrderByField.m_strFieldDescription;
                  document.formDataViewMenu.appendChild(objHidden);
                } 
                
            }
            else
            {
                var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_ID%>"+nOrderByCount);
                if( objFormEelement != null)
                {
                  eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_ID%>"+nOrderByCount+".value  ='"+objOrderBy.m_nOrderByIndex+"';");
                }   
                else      
                {
                  var objHidden= document.createElement("INPUT");
                  objHidden.type ="hidden";
                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_ID%>"+nOrderByCount;
                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_ID%>"+nOrderByCount;
                  objHidden.value = objOrderBy.m_nOrderByIndex;
                  document.formDataViewMenu.appendChild(objHidden);
                }
            }
        }
        nOrderByCount++;
    }
    if(nOrderByCount > 0)
    {
        var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELDS_NUM%>");
        if( objFormEelement != null)
        {
          eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELDS_NUM%>.value  ='"+nOrderByCount+"';");
        }   
        else      
        {
          var objHidden= document.createElement("INPUT");
          objHidden.type ="hidden";
          objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELDS_NUM%>";
          objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELDS_NUM%>";
          objHidden.value = nOrderByCount;
          document.formDataViewMenu.appendChild(objHidden);        
        }
     }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//                                                       GROUP BY CLAUSE
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//alert("group by");
      for(var l=0;l<top.objNewDataView.m_arrDataViewGroupBy.length;l++)
      {
        if(top.objNewDataView.m_arrDataViewGroupBy[l] != null)
        {
          var objGroupBy = top.objNewDataView.m_arrDataViewGroupBy[l];
          
          var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_TYPE%>"+nGroupByCount);
          if( objFormEelement != null)
          {
            eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_TYPE%>"+nGroupByCount+".value  ='"+objGroupBy.m_objGroupByField.m_nFieldType+"';");
          }   
          else      
          {
            var objHidden= document.createElement("INPUT");
            objHidden.type ="hidden";
            objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_TYPE%>"+nGroupByCount;
            objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_TYPE%>"+nGroupByCount;
            //alert("hena kaman");           // added by khaled reda 22/6/2004
            //alert(objGroupBy.m_nGroupByElementTypeID);   // added by khaled reda 22/6/2004
          
            if( objGroupBy.m_objGroupByField == null )   // added by khaled reda 22/6/2004
                objHidden.value = objGroupBy.m_nGroupByElementTypeID ;   // added by khaled reda 22/6/2004
            else   // added by khaled reda 22/6/2004
            objHidden.value = objGroupBy.m_objGroupByField.m_nFieldType;

            //alert("objHidden.value: " + objHidden.value);   // added by khaled reda 22/6/2004
            document.formDataViewMenu.appendChild(objHidden);
          }

          // If the group by field is a dataview field
          if(objGroupBy.m_nGroupByElementTypeID == <%=QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD%>)
          {
                var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_ID%>"+nGroupByCount);
                if( objFormEelement != null)
                {
                  eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_ID%>"+nGroupByCount+".value  ='"+objGroupBy.m_objGroupByField.m_nFieldID+"';");
                }   
                else      
                {
                  var objHidden= document.createElement("INPUT");
                  objHidden.type ="hidden";
                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_ID%>"+nGroupByCount;
                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_ID%>"+nGroupByCount;
                  objHidden.value = objGroupBy.m_objGroupByField.m_nFieldID;
                  document.formDataViewMenu.appendChild(objHidden);
                }

                var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_NAME%>"+nGroupByCount);
                if( objFormEelement != null)
                {
                  eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_NAME%>"+nGroupByCount+".value  ='"+objGroupBy.m_objGroupByField.m_strFieldName+"';");
                }   
                else      
                {
                  var objHidden= document.createElement("INPUT");
                  objHidden.type ="hidden";
                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_NAME%>"+nGroupByCount;
                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_NAME%>"+nGroupByCount;
                  objHidden.value = objGroupBy.m_objGroupByField.m_strFieldName;
                  document.formDataViewMenu.appendChild(objHidden);
                }

                var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_DATAVIEW_ID%>"+nGroupByCount);
                if( objFormEelement != null)
                {
                  eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_DATAVIEW_ID%>"+nGroupByCount+".value  ='"+objGroupBy.m_objGroupByField.m_nDataViewID+"';");
                }   
                else      
                {
                  var objHidden= document.createElement("INPUT");
                  objHidden.type ="hidden";
                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_DATAVIEW_ID%>"+nGroupByCount;
                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_DATAVIEW_ID%>"+nGroupByCount;
                  objHidden.value = objGroupBy.m_objGroupByField.m_nDataViewID;
                  document.formDataViewMenu.appendChild(objHidden);
                }   

                var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_DATAVIEW_NAME%>"+nGroupByCount);
                if( objFormEelement != null)
                {
                  eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_DATAVIEW_NAME%>"+nGroupByCount+".value  ='"+objGroupBy.m_objGroupByField.m_strDataViewName+"';");
                }   
                else      
                {
                  var objHidden= document.createElement("INPUT");
                  objHidden.type ="hidden";
                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_DATAVIEW_NAME%>"+nGroupByCount;
                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_DATAVIEW_NAME%>"+nGroupByCount;
                  objHidden.value = objGroupBy.m_objGroupByField.m_strDataViewName;
                  document.formDataViewMenu.appendChild(objHidden);
                }

                var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_DATAVIEW_TYPE%>"+nGroupByCount);
                if( objFormEelement != null)
                {
                  eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_DATAVIEW_TYPE%>"+nGroupByCount+".value  ='"+objGroupBy.m_objGroupByField.m_nDataViewType+"';");
                }   
                else      
                {
                  var objHidden= document.createElement("INPUT");
                  objHidden.type ="hidden";
                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_DATAVIEW_TYPE%>"+nGroupByCount;
                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_DATAVIEW_TYPE%>"+nGroupByCount;
                  objHidden.value = objGroupBy.m_objGroupByField.m_nDataViewType;
                  document.formDataViewMenu.appendChild(objHidden);
                }

                var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_DESCRIPTION%>"+nGroupByCount);
                if( objFormEelement != null)
                {
                  eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_DESCRIPTION%>"+nGroupByCount+".value  ='"+objGroupBy.m_objGroupByField.m_strFieldDescription+"';");
                }   
                else      
                {
                  var objHidden= document.createElement("INPUT");
                  objHidden.type ="hidden";
                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_DESCRIPTION%>"+nGroupByCount;
                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_DESCRIPTION%>"+nGroupByCount;
                  objHidden.value = objGroupBy.m_objGroupByField.m_strFieldDescription;
                  document.formDataViewMenu.appendChild(objHidden);
                }                 
            }
            // If the group by field is a function
            else
            {            
                var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_ID%>"+nGroupByCount);
                if( objFormEelement != null)
                {
                  eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_ID%>"+nGroupByCount+".value  ='"+objGroupBy.m_nGroupByIndex+"';");
                }   
                else      
                {
                  var objHidden= document.createElement("INPUT");
                  objHidden.type ="hidden";
                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_ID%>"+nGroupByCount;
                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_ID%>"+nGroupByCount;
                  objHidden.value = objGroupBy.m_nGroupByIndex;
                  document.formDataViewMenu.appendChild(objHidden);
                }
            }
        }
        nGroupByCount++;
      }
      if(nGroupByCount > 0)
      {
        var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELDS_NUM%>");
        if( objFormEelement != null)
        {
          eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELDS_NUM%>.value  ='"+nGroupByCount+"';");
        }   
        else      
        {
          var objHidden= document.createElement("INPUT");
          objHidden.type ="hidden";
          objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELDS_NUM%>";
          objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELDS_NUM%>";
          objHidden.value = nGroupByCount;
          document.formDataViewMenu.appendChild(objHidden);        
        }
      }

/////////////////////////////////////////////////////////////////////////////////////////////////////////
//                                            HAVING CLAUSE
//////////////////////////////////////////////////////////////////////////////////////////////////////////

      for(var m=0;m<top.objNewDataView.m_arrDataViewHavingElements.length;m++)
      {
        if(top.objNewDataView.m_arrDataViewHavingElements[m] != null)
        {
            var objHavingElement = top.objNewDataView.m_arrDataViewHavingElements[m];
            var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_HAVING_TYPE%>"+nHavingCount);
            if( objFormEelement != null)
            {
              eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_HAVING_TYPE%>"+nHavingCount+".value  ='"+objHavingElement.m_nElementTypeID+"';");
            }   
            else      
            {
              var objHidden= document.createElement("INPUT");
              objHidden.type ="hidden";
              objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_HAVING_TYPE%>"+nHavingCount;
              objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_HAVING_TYPE%>"+nHavingCount;
              objHidden.value = objHavingElement.m_nElementTypeID;
              document.formDataViewMenu.appendChild(objHidden);
            }

            if(objHavingElement.m_nElementTypeID == <%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE_TERM%>)
            {
                var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_HAVING_ELEMENT_ID%>"+nHavingCount);
                if( objFormEelement != null)
                {
                  eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_HAVING_ELEMENT_ID%>"+nHavingCount+".value  ='"+objHavingElement.m_nElementIndex+"';");
                }   
                else      
                {
                  var objHidden= document.createElement("INPUT");
                  objHidden.type ="hidden";
                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_HAVING_ELEMENT_ID%>"+nHavingCount;
                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_HAVING_ELEMENT_ID%>"+nHavingCount;
                  objHidden.value = objHavingElement.m_nElementIndex;
                  document.formDataViewMenu.appendChild(objHidden);
                }
           }
           else
           {
               if(objHavingElement.m_nElementTypeID == <%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE_SYMBOL%>)
               {
                    var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_HAVING_ELEMENT_ID%>"+nHavingCount);
                    if( objFormEelement != null)
                    {
                      eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_HAVING_ELEMENT_ID%>"+nHavingCount+".value  ='"+objHavingElement.m_nRelationSymbolID+"';");
                    }   
                    else      
                    {
                      var objHidden= document.createElement("INPUT");
                      objHidden.type ="hidden";
                      objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_HAVING_ELEMENT_ID%>"+nHavingCount;
                      objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_HAVING_ELEMENT_ID%>"+nHavingCount;
                      objHidden.value = objHavingElement.m_nRelationSymbolID;
                      document.formDataViewMenu.appendChild(objHidden);
                    }

                    var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_HAVING_SYMBOL_SQL%>"+nHavingCount);
                    if( objFormEelement != null)
                    {
                      eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_HAVING_SYMBOL_SQL%>"+nHavingCount+".value  ='"+objHavingElement.m_strRelationSymbolSQL+"';");
                    }   
                    else      
                    {
                      var objHidden= document.createElement("INPUT");
                      objHidden.type ="hidden";
                      objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_HAVING_SYMBOL_SQL%>"+nHavingCount;
                      objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_HAVING_SYMBOL_SQL%>"+nHavingCount;
                      objHidden.value = objHavingElement.m_strRelationSymbolSQL;
                      document.formDataViewMenu.appendChild(objHidden);
                    }
               }   
           }// End Else for the type
           nHavingCount++;
       }// End If element == null

     }// End For 
     if(nHavingCount > 0)
     {
        var objFormEelement = document.formDataViewMenu.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_HAVINGS_NUM%>");
        if( objFormEelement != null)
        {
          eval("document.formDataViewMenu.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_HAVINGS_NUM%>.value  ='"+nHavingCount+"';");
        }   
        else      
        {
          var objHidden= document.createElement("INPUT");
          objHidden.type ="hidden";
          objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_HAVINGS_NUM%>";
          objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_HAVINGS_NUM%>";
          objHidden.value = nHavingCount;
          document.formDataViewMenu.appendChild(objHidden);        
        }
     }

     var objHidden= document.createElement("INPUT");
     objHidden.type ="hidden";
     objHidden.id= "<%=InterfaceKey.HASHMAP_KEY_ACTION%>";
     objHidden.name= "<%=InterfaceKey.HASHMAP_KEY_ACTION%>";
     objHidden.value = argAction;
     document.formDataViewMenu.appendChild(objHidden);        
       
//     eval("document.formDataViewMenu.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = \""+argAction+"\";");
     document.formDataViewMenu.submit();
  }
  catch(e)
  {
    //alert("khaled8");   // added by khaled reda 22/6/2004
    //alert("e.name: " + e.name + "e.message: " + e.message);
    if(top.objNewDataView == null )
    {
        alert("You have to select at lease one data view..");
    }
    else
    if(top.objNewDataView.m_arrSelectedDataViews.length == 0 || nDataViewsCount == 0)
    {
      alert("You have to select at lease one data view..");
    }
    else
    if(top.objNewDataView.m_arrDataViewFields == null || top.objNewDataView.m_arrDataViewFields.length == 0 || nFieldsCount == 0)
    {
        alert("You have to select at lease one field to be displayed..");    
    } 
    else
    if(top.objNewDataView.m_arrDataViewFields.length > 0 && top.objNewDataView.m_KPIRulesViolated == 0 && top.objNewDataView.m_EligibleRulesViolated == 0)
    {
    //  alert( top.objNewDataView.m_KPIRulesViolated );  // added by khaled reda 22/6/2004
    //  alert( top.objNewDataView.m_EligibleRulesViolated );   // added by khaled reda 22/6/2004
    ///   alert("hgi");
    //  alert(e);
        alert("Two or more fields having the same field name or alias..");    
    }
    else
    if(top.objNewDataView.m_KPIRulesViolated == 1)
    {
        alert("The data view constructed for the KPI violates the KPI data view rules..It should maintain the following rules:\n 1- A field named DCM_ID or with alias having this name must exsist in the select clause of the data view.");    
    }
    else
    if(top.objNewDataView.m_EligibleRulesViolated == 1)
    {
        alert("The data view constructed for the Eligible number of contracts for payment violates the number of contracts eligible for payment data view rules..It should maintain the following rules:\n 1- A field named DCM_ID or with alias having this name must exsist in the select clause of the data view.");    
    }
    else
    if(top.objNewDataView.m_arrDataViewConstants.length > 0)
    {
        alert("One or more of the constants defined are missing the value, the type or having invalid format..");
    }
    else
    if(top.objNewDataView.m_arrDataViewInputParameters.length > 0)
    {
        alert("One or more of the input parameters defined are missing the value, the type or having invalid format..");
    }
    else
    {
      alert("The data view is corrupted or has expired..Unhandeled exception has occured, You will be redirected to the main page");
//      top.objNewDataView = new NewDataView();
      window.open("../gn/ua/Role_Privileges_Main.jsp","_parent");
    }  
  }
}

function link_onclick()
{
    top.frames(1).validate();
//      top.frames(1).document.formDataView.submit();
}
function validateFields()
{
    //alert( "validateFields" );   // added by khaled reda 22/6/2004
  var str1stFieldName = "";
  var str2ndFieldName = "";
  var bValid = true;
  //alert( "length: " + top.objNewDataView.m_arrDataViewFields.length);   // added by khaled reda 22/6/2004
  for (var i = 0; i < top.objNewDataView.m_arrDataViewFields.length-1; i++) 
  {
      if(!bValid)
        break;
      str1stFieldName = top.objNewDataView.m_arrDataViewFields[i].m_strFieldAlias;
      if(str1stFieldName == "" || str1stFieldName == "null" || str1stFieldName == null)
          str1stFieldName = top.objNewDataView.m_arrDataViewFields[i].m_strFieldName;
      //alert("str1stFieldName: " + str1stFieldName);  // added by khaled reda 22/6/2004
      for (var j = i+1; j < top.objNewDataView.m_arrDataViewFields.length; j++) 
      {
        str2ndFieldName = top.objNewDataView.m_arrDataViewFields[j].m_strFieldAlias;
        if(str2ndFieldName == "" || str2ndFieldName == "null" || str2ndFieldName == null)
            str2ndFieldName = top.objNewDataView.m_arrDataViewFields[j].m_strFieldName;
        //alert("str2ndFieldName: " + str2ndFieldName);  // added by khaled reda 22/6/2004
        if( str1stFieldName.toUpperCase() == str2ndFieldName.toUpperCase())  
        {
            bValid = false;
            //alert("bValid: " + bValid);  // added by khaled reda 22/6/2004
            break;
        }   
      }  
  }
  //alert("end of validateFields");
  return bValid;
}

function validateRules(argUniverseName)
{
    //alert( "validateRules" );   // added by khaled reda 22/6/2004
  var strFieldName = "";
  var strRuleFieldName = "";
  var bValid = false;
  if(argUniverseName == '<%=QueryBuilderInterfaceKey.UNIVERSE_TYPE_KPI%>')
  {
      //alert("argUniverseName == '<%=QueryBuilderInterfaceKey.UNIVERSE_TYPE_KPI%>'");  // added by khaled reda 22/6/2004
      for (var i = 0; i < top.objNewDataView.m_arrDataViewFields.length; i++) 
      {
        //alert("top.objNewDataView.m_arrDataViewFields.length: " + top.objNewDataView.m_arrDataViewFields.length);  // added by khaled reda 22/6/2004
        strFieldName = top.objNewDataView.m_arrDataViewFields[i].m_strFieldAlias;
        if(strFieldName == "" || strFieldName == "null" || strFieldName == null)
          strFieldName = top.objNewDataView.m_arrDataViewFields[i].m_strFieldName;
        //alert("strFieldName: " + strFieldName);  // added by khaled reda 22/6/2004
        strRuleFieldName = '<%=CommissionInterfaceKey.PARAM_KPI_CALCULATE_FIELD_EXCLUDED%>';
        //alert("strRuleFieldName: " + strRuleFieldName);  // added by khaled reda 22/6/2004
        if( strFieldName.toUpperCase() == strRuleFieldName.toUpperCase())
        {
           bValid = true;
           //alert("bValid: " + bValid);   // added by khaled reda 22/6/2004
           break;
        }    
      }
      if(bValid)
         top.objNewDataView.m_KPIRulesViolated         = 0;
      else
         top.objNewDataView.m_KPIRulesViolated         = 1;
  }
  else
  if(argUniverseName == '<%=QueryBuilderInterfaceKey.UNIVERSE_TYPE_LINES_ELIGIBLE_FOR_COMMISSIONING%>')
  {
      //alert("argUniverseName == '<%=QueryBuilderInterfaceKey.UNIVERSE_TYPE_LINES_ELIGIBLE_FOR_COMMISSIONING%>'");   // added by khaled reda 22/6/2004
      for (var i = 0; i < top.objNewDataView.m_arrDataViewFields.length; i++) 
      {
        //alert("top.objNewDataView.m_arrDataViewFields.length: " + top.objNewDataView.m_arrDataViewFields.length);
        strFieldName = top.objNewDataView.m_arrDataViewFields[i].m_strFieldAlias;
        if(strFieldName == "" || strFieldName == "null" || strFieldName == null)
          strFieldName = top.objNewDataView.m_arrDataViewFields[i].m_strFieldName;
        //alert("strFieldName: " + strFieldName);   // added by khaled reda 22/6/2004
        strRuleFieldName = '<%=CommissionInterfaceKey.PARAM_KPI_CALCULATE_FIELD_EXCLUDED%>';
        //alert("strRuleFieldName: " + strRuleFieldName);    // added by khaled reda 22/6/2004
        if( strFieldName.toUpperCase() == strRuleFieldName.toUpperCase())  
        {
           bValid = true;
           //alert("bValid: " + bValid);  // added by khaled reda 22/6/2004
           break;
        }    
      }
      if(bValid)
          top.objNewDataView.m_EligibleRulesViolated    = 0;
      else
          top.objNewDataView.m_EligibleRulesViolated    = 1;
  }
  else
    bValid = true;
  //alert("end of validateRules");   // added by khaled reda 22/6/2004
  return bValid;
}

function validateValue(argType,argValue)
{
// alert( "validateValue" );   // added by khaled reda 22/6/2004
  
  var bValid = true;

  if(argType == "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_CONST_TYPE_DATE%>")
  {
    var objDate;
    var nMonth;
    var nDay;
    var nYear;
    var strDate = new String(argValue);
    var arrDate = new Array();
    arrDate = strDate.split("/");

    
    objDate = new Date();
 	objDate.setFullYear(arrDate[2]);	
 	objDate.setMonth(arrDate[1]-1);
 	objDate.setDate(arrDate[0]);
 	 	
 	objDate.setHours(5);
    
  
      bValid = true;
  
  }    
  else
  if(argType == "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_CONST_TYPE_NUMERIC%>")
  {
	  //alert("number in validate");
    var objNumber = argValue;  
    if(isNaN(Number(objNumber)))
      bValid = false;
    else
    if(argValue.length > 38)
      bValid = false;
    else
      bValid = true;
  }
  else
  if(argType == "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_CONST_TYPE_TEXT%>")
  {
	//alert("text in validate ");  
    if(argValue.length > 8000)
      bValid = false;
    else
      bValid = true;
  }
  return bValid;
}
</script>    
    <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=0 cellPadding=0 width=0 border=0>
      <TBODY>
      <TR class=TableHeader>
        <TD vAlign=top width=163>
          <IMG height=16 alt="" src="../resources/img/index_10.gif" width=163>
        </TD>
      </TR>
      <TR class=TableHeader>
        <TD vAlign=top width=163>&nbsp;</TD>
      </TR>
      <TR>
        <TD vAlign=top width=163 background=../resources/img/allmenuleft.gif bgColor=#f6f6f6 height=32>
          
                <TABLE cellSpacing=0 cellPadding=0 width="100%" bgColor=#f1f1ed 
                  border=0>
                  <TBODY>
                  <TR>
                  <TD vAlign=top width="100%">
                  <DIV class=option onmouseover="this.style.background='#DEE7EF'" 
                  onmouseout="this.style.background='#f5f5f5'">
                  <A href="javascript:submitPage('<%=QueryBuilderInterfaceKey.ACTION_PREVIEW_QUERY%>')">
                  <SPAN style="FONT-SIZE: 11px; COLOR: #7c8698; TEXT-DECORATION: none; text-indext: 15px; hover: #7c8698" onclick="Highlight(this);">
                  Preview Data View</SPAN></A></DIV></TD></TR></TBODY></TABLE>
                  <TABLE cellSpacing=0 cellPadding=0 width="100%" bgColor=#f1f1ed 
                  border=0>
                  <TBODY>
                  <TR>
                  <TD vAlign=top width="100%">
                  <DIV class=option onmouseover="this.style.background='#DEE7EF'" 
                  onmouseout="this.style.background='#f5f5f5'">
                  <A href="javascript:submitPage('<%=QueryBuilderInterfaceKey.ACTION_PREVIEW_SAVE_QUERY%>')">
                  <SPAN style="FONT-SIZE: 11px; COLOR: #7c8698; TEXT-DECORATION: none; text-indext: 15px; hover: #7c8698" onclick="Highlight(this);">
                  Preview & Save Data View</SPAN></A></DIV></TD></TR></TBODY></TABLE>
                  <TABLE cellSpacing=0 cellPadding=0 width="100%" bgColor=#f1f1ed 
                  border=0>
                  <TBODY>
                  <TR>
                  <TD vAlign=top width="100%">
                  <DIV class=option onmouseover="this.style.background='#DEE7EF'" 
                  onmouseout="this.style.background='#f5f5f5'">
                  <A href="javascript:openWizardStep(9)">
                  <SPAN style="FONT-SIZE: 11px; COLOR: #7c8698; TEXT-DECORATION: none; text-indext: 15px; hover: #7c8698" onclick="Highlight(this);">
                  Wizard Help</SPAN></A></DIV></TD></TR></TBODY></TABLE>
          </TD>
        </TR>
        <TR id=section1>
          <TD vAlign=top bgColor=#f6f6f6 colSpan=7>&nbsp;&nbsp;
          <IMG height=70 alt="" src="../resources/img/index_37.jpg" width=69>
        </TD>
      </TR>
        <TR>
          <TD vAlign=top bgColor=#f6f6f6 colSpan=7>&nbsp;&nbsp;
          <TABLE cellSpacing=0 cellPadding=0 width="100%" bgColor=#f1f1ed 
                  border=0>
                  <TBODY>
                  <TR>
                  <TD vAlign=top width="100%">
                  <DIV class=option onmouseover="this.style.background='#DEE7EF'" 
                  onmouseout="this.style.background='#f5f5f5'">
                  <A href="ua/Role_Privileges_Main.jsp" target="_parent">
                  <SPAN style="FONT-SIZE: 11px; COLOR: #7c8698; TEXT-DECORATION: none; text-indext: 15px; hover: #7c8698" onclick="Highlight(this);">
                  Back to main menu</SPAN></A></DIV></TD></TR></TBODY></TABLE>
        </TD>
      </TR>
    </TBODY>
    </TABLE>
   
</form>
   </BODY>
</HTML>







