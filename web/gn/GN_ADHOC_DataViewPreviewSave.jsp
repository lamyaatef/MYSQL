<%@ page buffer="1024kb" %>
<%@ page contentType="text/html;charset=windows-1256"
         import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.IOException"
         import="java.io.PrintWriter"
         import="java.util.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.gn.querybuilder.*"
         import="com.mobinil.sds.core.system.gn.querybuilder.dto.*"
         import="com.mobinil.sds.web.interfaces.cc.*" 
%>
<script language="javascript">
function butSave_onClick(argAction)
{
  //parent.frames("Right").frames("Top").butPreviewSave.click();
  var nDataViewsCount = 0;
  var nFieldsCount    = 0;
  var nConstantsCount = 0;
  var nFtFTermsCount  = 0;
  var nFtDVTermsCount = 0;
  var nWhereCount     = 0;
  var nHavingCount     = 0;
  var nOrderByCount  = 0;
  var nGroupByCount  = 0;
  var nParametersCount  = 0;

  var nFunctionsCount     = 0;
/*  try
  {
      top.frames(1).addToCart();
  }
  catch(e)
  {
  }*/
  try
  {

///////////////////////////////////////////////////////////////////////////////////
//                          Data View Name
////////////////////////////////////////////////////////////////////////////////////
    var coll = document.formDataViewSave.all.tags("INPUT");
    if (coll!=null)
    {
        while(coll.length > 5)
        {
             for (var i=0; i<coll.length; i++)
             {
                if(coll[i].id != 'Save' && coll[i].id != 'butBackToWizard' && coll[i].id != '<%=QueryBuilderInterfaceKey.PARAM_INITIALIZE_QUERY_BUILDER_QUERY_NAME%>'  && coll[i].id !='chkNew' && coll[i].id != 'textVersion') 
                {
                  document.formDataViewSave.removeChild(coll[i]);
                }   
             }  
             coll = document.formDataViewSave.all.tags("INPUT");
        }     

    }
    top.objNewDataView.m_strDataViewName = eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_INITIALIZE_QUERY_BUILDER_QUERY_NAME%>.value");
    if(top.objNewDataView.m_strDataViewName != "")
    {
        if(TrimInput(top.objNewDataView.m_strDataViewName) == "")
        {
            alert("The data view name can't be space characters only..");
            return false;
        }
    }

    top.objNewDataView.m_strDataViewDescription = eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_INITIALIZE_QUERY_BUILDER_QUERY_DESC%>.value");

    
     if(top.objNewDataView.m_strDataViewName == null || top.objNewDataView.m_strDataViewName == "" )
        throw(e);
      var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_NAME%>");
      if( objFormEelement != null)
      {
          eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_NAME%>.value  ='"+top.objNewDataView.m_strDataViewName+"';");
      }   
      else      
      { 
         var objHidden= document.createElement("INPUT");
         objHidden.type ="hidden";
         objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_NAME%>";
         objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_NAME%>";
         objHidden.value = top.objNewDataView.m_strDataViewName;
         document.formDataViewSave.appendChild(objHidden);
      }  

///////////////////////////////////////////////////////////////////////////////////
//                          Data View Description
////////////////////////////////////////////////////////////////////////////////////
     if(top.objNewDataView.m_strDataViewDescription == null || top.objNewDataView.m_strDataViewDescription == "")
           throw(e);
      var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_DESC%>");
      if( objFormEelement != null)
      {
          eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_DESC%>.value  ='"+top.objNewDataView.m_strDataViewDescription+"';");
      }   
      else      
      { 
        var objHidden= document.createElement("INPUT");
        objHidden.type ="hidden";
        objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_DESC%>";
        objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_DESC%>";
        objHidden.value = top.objNewDataView.m_strDataViewDescription;
        document.formDataViewSave.appendChild(objHidden);
      }

      var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_UNIVERSE%>");
      if( objFormEelement != null)
      {
          eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_UNIVERSE%>.value  ='"+top.objNewDataView.m_strUniverseName+"';");
      }   
      else      
      { 
        var objHidden= document.createElement("INPUT");
        objHidden.type ="hidden";
        objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_UNIVERSE%>";
        objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_UNIVERSE%>";
        objHidden.value = top.objNewDataView.m_strUniverseName;
        document.formDataViewSave.appendChild(objHidden);
      }

      var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_SAVE%>");
      if( objFormEelement != null)
      {
          if(eval("document.formDataViewSave.chkNew.checked"))
              eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_SAVE%>.value  ='<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_SAVE_FALSE%>';");
          else
              eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_SAVE%>.value  ='<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_SAVE_TRUE%>';");
      }   
      else      
      { 
        var objHidden= document.createElement("INPUT");
        objHidden.type ="hidden";
        objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_SAVE%>";
        objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_SAVE%>";
        if(eval("document.formDataViewSave.chkNew.checked"))
            objHidden.value = "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_SAVE_FALSE%>";
        else
            objHidden.value = "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_SAVE_TRUE%>";
        document.formDataViewSave.appendChild(objHidden);
      }
      
      var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_ISSUE%>");
      if( objFormEelement != null)
      {
          eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_ISSUE%>.value  ='"+top.objNewDataView.m_nDataViewIssue+"';");
      }   
      else      
      { 
        var objHidden= document.createElement("INPUT");
        objHidden.type ="hidden";
        objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_ISSUE%>";
        objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_ISSUE%>";
        objHidden.value = top.objNewDataView.m_nDataViewIssue;
        document.formDataViewSave.appendChild(objHidden);
      }

      var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_UNIQUE%>");
      if( objFormEelement != null)
      {
          if(top.objNewDataView.m_bDataViewUnique)
              eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_UNIQUE%>.value  ='<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_UNIQUE_TRUE%>';");
          else    
              eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_UNIQUE%>.value   ='<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_UNIQUE_FALSE%>';");
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
        document.formDataViewSave.appendChild(objHidden);
      }
///////////////////////////////////////////////////////////////////////////////////
//                          Data Views
////////////////////////////////////////////////////////////////////////////////////


      for(var i=0;i<top.objNewDataView.m_arrSelectedDataViews.length;i++)
      {
        if(top.objNewDataView.m_arrSelectedDataViews[i] != null)
        {
            
            var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_DATAVIEWS_IDS%>"+nDataViewsCount);
            if( objFormEelement != null)
            {
              eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_DATAVIEWS_IDS%>"+nDataViewsCount+".value  ='"+top.objNewDataView.m_arrSelectedDataViews[i].m_nDataViewID+"';");
            }   
            else      
            {  
              var objHidden= document.createElement("INPUT");
              objHidden.type ="hidden";
              objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_DATAVIEWS_IDS%>"+nDataViewsCount;
              objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_DATAVIEWS_IDS%>"+nDataViewsCount;
              objHidden.value = top.objNewDataView.m_arrSelectedDataViews[i].m_nDataViewID;
              document.formDataViewSave.appendChild(objHidden);
            }

            var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_DATAVIEWS_NAMES%>"+nDataViewsCount);
            if( objFormEelement != null)
            {
              eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_DATAVIEWS_NAMES%>"+nDataViewsCount+".value  ='"+top.objNewDataView.m_arrSelectedDataViews[i].m_strDataViewName+"';");
            }   
            else      
            {
              var objHidden= document.createElement("INPUT");
              objHidden.type ="hidden";
              objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_DATAVIEWS_NAMES%>"+nDataViewsCount;
              objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_DATAVIEWS_NAMES%>"+nDataViewsCount;
              objHidden.value = top.objNewDataView.m_arrSelectedDataViews[i].m_strDataViewName;
              document.formDataViewSave.appendChild(objHidden);
            }  

            var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_DATAVIEWS_TYPE_IDS%>"+nDataViewsCount);
            if( objFormEelement != null)
            {
              eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_DATAVIEWS_TYPE_IDS%>"+nDataViewsCount+".value  ='"+top.objNewDataView.m_arrSelectedDataViews[i].m_nDataViewType+"';");
            }   
            else      
            {
              var objHidden= document.createElement("INPUT");
              objHidden.type ="hidden";
              objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_DATAVIEWS_TYPE_IDS%>"+nDataViewsCount;
              objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_DATAVIEWS_TYPE_IDS%>"+nDataViewsCount;
              objHidden.value = top.objNewDataView.m_arrSelectedDataViews[i].m_nDataViewType;
              document.formDataViewSave.appendChild(objHidden);
            }
            nDataViewsCount++;
        }
     }   
     
      if(nDataViewsCount > 0)
      {
        var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_DATAVIEWS_NUM%>");
        if( objFormEelement != null)
        {
            eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_DATAVIEWS_NUM%>.value  ='"+nDataViewsCount+"';");
        }   
        else      
        { 
          var objHidden= document.createElement("INPUT");
          objHidden.type ="hidden";
          objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_DATAVIEWS_NUM%>";
          objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_DATAVIEWS_NUM%>";
          objHidden.value = nDataViewsCount;
          document.formDataViewSave.appendChild(objHidden);
        }
      }
      else
      {
        throw(e)
      }


///////////////////////////////////////////////////////////////////////////////////
//                          Displayed Fields
////////////////////////////////////////////////////////////////////////////////////

      if(top.objNewDataView.m_arrDataViewFields.length == 0)
          throw(e);
      for(var j=0;j<top.objNewDataView.m_arrDataViewFields.length;j++)
      {
        if(top.objNewDataView.m_arrDataViewFields[j] != null)
        {
          var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_IDS%>"+nFieldsCount);
          if( objFormEelement != null)
          {
            eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_IDS%>"+nFieldsCount+".value  ='"+top.objNewDataView.m_arrDataViewFields[j].m_nFieldID+"';");
          }   
          else      
          {
            var objHidden= document.createElement("INPUT");
            objHidden.type ="hidden";
            objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_IDS%>"+nFieldsCount;
            objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_IDS%>"+nFieldsCount;
            objHidden.value = top.objNewDataView.m_arrDataViewFields[j].m_nFieldID;
            document.formDataViewSave.appendChild(objHidden);
          }

          var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_NAMES%>"+nFieldsCount);
          if( objFormEelement != null)
          {
            eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_NAMES%>"+nFieldsCount+".value  ='"+top.objNewDataView.m_arrDataViewFields[j].m_strFieldName+"';");
          }   
          else      
          {
            var objHidden= document.createElement("INPUT");
            objHidden.type ="hidden";
            objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_NAMES%>"+nFieldsCount;
            objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_NAMES%>"+nFieldsCount;
            objHidden.value = top.objNewDataView.m_arrDataViewFields[j].m_strFieldName;
            document.formDataViewSave.appendChild(objHidden);        
          }        
          var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_TYPES%>"+nFieldsCount);
          if( objFormEelement != null)
          {
            eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_TYPES%>"+nFieldsCount+".value  ='"+top.objNewDataView.m_arrDataViewFields[j].m_nFieldType+"';");
          }   
          else      
          {
            var objHidden= document.createElement("INPUT");
            objHidden.type ="hidden";
            objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_TYPES%>"+nFieldsCount;
            objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_TYPES%>"+nFieldsCount;
            objHidden.value = top.objNewDataView.m_arrDataViewFields[j].m_nFieldType;
            document.formDataViewSave.appendChild(objHidden);
          }  

          if(top.objNewDataView.m_arrDataViewFields[j].m_nFieldType == '<%=QueryBuilderInterfaceKey.FIELD_TYPE_CONST_FIELD%>')
          {
            var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_VALUES%>"+nFieldsCount);
            if( objFormEelement != null)
            {
              eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_VALUES%>"+nFieldsCount+".value  ='"+top.objNewDataView.m_arrDataViewConstants[top.objNewDataView.m_arrDataViewFields[j].m_nFieldIndex].m_strConstantValue+"';");
            }   
            else      
            {
              var objHidden= document.createElement("INPUT");
              objHidden.type ="hidden";
              objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_VALUES%>"+nFieldsCount;
              objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_VALUES%>"+nFieldsCount;
              objHidden.value = top.objNewDataView.m_arrDataViewConstants[top.objNewDataView.m_arrDataViewFields[j].m_nFieldIndex].m_strConstantValue;
              document.formDataViewSave.appendChild(objHidden);
            }  
          }
          var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_ALIASES%>"+nFieldsCount);
          if( objFormEelement != null)
          {
            eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_ALIASES%>"+nFieldsCount+".value  ='"+top.objNewDataView.m_arrDataViewFields[j].m_strFieldAlias+"';");
          }   
          else      
          {
            var objHidden= document.createElement("INPUT");
            objHidden.type ="hidden";
            objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_ALIASES%>"+nFieldsCount;
            objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_ALIASES%>"+nFieldsCount;
            objHidden.value = top.objNewDataView.m_arrDataViewFields[nFieldsCount].m_strFieldAlias;
            document.formDataViewSave.appendChild(objHidden);
          }  

          var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_DATAVIEWS_IDS%>"+nFieldsCount);
          if( objFormEelement != null)
          {
            eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_DATAVIEWS_IDS%>"+nFieldsCount+".value  ='"+top.objNewDataView.m_arrDataViewFields[j].m_nDataViewID+"';");
          }   
          else      
          {
            var objHidden= document.createElement("INPUT");
            objHidden.type ="hidden";
            objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_DATAVIEWS_IDS%>"+nFieldsCount;
            objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_DATAVIEWS_IDS%>"+nFieldsCount;
            objHidden.value = top.objNewDataView.m_arrDataViewFields[j].m_nDataViewID;
            document.formDataViewSave.appendChild(objHidden);
          }  

          var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_DATAVIEWS_NAMES%>"+nFieldsCount);
          if( objFormEelement != null)
          {
            eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_DATAVIEWS_NAMES%>"+nFieldsCount+".value  ='"+top.objNewDataView.m_arrDataViewFields[j].m_strDataViewName+"';");
          }   
          else      
          {
            var objHidden= document.createElement("INPUT");
            objHidden.type ="hidden";
            objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_DATAVIEWS_NAMES%>"+nFieldsCount;
            objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_DATAVIEWS_NAMES%>"+nFieldsCount;
            objHidden.value = top.objNewDataView.m_arrDataViewFields[j].m_strDataViewName;
            document.formDataViewSave.appendChild(objHidden);
          }  

          var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_DATAVIEWS_TYPES%>"+nFieldsCount);
          if( objFormEelement != null)
          {
            eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_DATAVIEWS_TYPES%>"+nFieldsCount+".value  ='"+top.objNewDataView.m_arrDataViewFields[j].m_nDataViewType+"';");
          }   
          else      
          {
            var objHidden= document.createElement("INPUT");
            objHidden.type ="hidden";
            objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_DATAVIEWS_TYPES%>"+nFieldsCount;
            objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_DATAVIEWS_TYPES%>"+nFieldsCount;
            objHidden.value = top.objNewDataView.m_arrDataViewFields[j].m_nDataViewType;
            document.formDataViewSave.appendChild(objHidden);
          }
          
          var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_DESCRIPS%>"+nFieldsCount);
          if( objFormEelement != null)
          {
            eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_DESCRIPS%>"+nFieldsCount+".value  ='"+top.objNewDataView.m_arrDataViewFields[j].m_strFieldDescription+"';");
          }   
          else      
          {
            var objHidden= document.createElement("INPUT");
            objHidden.type ="hidden";
            objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_DESCRIPS%>"+nFieldsCount;
            objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_DESCRIPS%>"+nFieldsCount;
            objHidden.value = top.objNewDataView.m_arrDataViewFields[j].m_strFieldDescription;
            document.formDataViewSave.appendChild(objHidden);
          }  
          nFieldsCount++;
        }
      }
      
      if(nFieldsCount > 0)
      {
        var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_NUM%>");
        if( objFormEelement != null)
        {
          eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_NUM%>.value  ='"+nFieldsCount+"';");
        }   
        else      
        { 
          var objHidden= document.createElement("INPUT");
          objHidden.type ="hidden";
          objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_NUM%>";
          objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_NUM%>";
          objHidden.value = nFieldsCount;
          document.formDataViewSave.appendChild(objHidden);
        }        
      }
      else
      {
        throw(e)
      }

      if(!validateFields())
          throw(e);    
      if(!validateRules(top.objNewDataView.m_strUniverseName))    
          throw(e);      
///////////////////////////////////////////////////////////////////////////////////
//                          CONSTANTS
////////////////////////////////////////////////////////////////////////////////////

      for(var k=0;k<top.objNewDataView.m_arrDataViewConstants.length;k++)
      {
        if(top.objNewDataView.m_arrDataViewConstants[k] != null)
        {
          if(top.objNewDataView.m_arrDataViewConstants[k].m_strConstantValue == '' || top.objNewDataView.m_arrDataViewConstants[k].m_nConstantType == '')
          {
               throw(e);
          } 
         
          var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_CONST_NAME%>"+nConstantsCount);
          if( objFormEelement != null)
          {
            eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_CONST_NAME%>"+nConstantsCount+".value  ='"+top.objNewDataView.m_arrDataViewConstants[k].m_strConstantName+"';");
          }   
          else      
          {
            var objHidden= document.createElement("INPUT");
            objHidden.type ="hidden";
            objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_CONST_NAME%>"+nConstantsCount;
            objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_CONST_NAME%>"+nConstantsCount;
            objHidden.value = top.objNewDataView.m_arrDataViewConstants[k].m_strConstantName;
            document.formDataViewSave.appendChild(objHidden);
          }
         
          var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_CONST_TYPE%>"+nConstantsCount);
          if( objFormEelement != null)
          {
            eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_CONST_TYPE%>"+nConstantsCount+".value  ='"+top.objNewDataView.m_arrDataViewConstants[k].m_nConstantType+"';");
          }   
          else      
          {
            var objHidden= document.createElement("INPUT");
            objHidden.type ="hidden";
            objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_CONST_TYPE%>"+nConstantsCount;
            objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_CONST_TYPE%>"+nConstantsCount;
            objHidden.value = top.objNewDataView.m_arrDataViewConstants[k].m_nConstantType;
            document.formDataViewSave.appendChild(objHidden);        
          }     
         
          if(!validateValue(top.objNewDataView.m_arrDataViewConstants[k].m_nConstantType,top.objNewDataView.m_arrDataViewConstants[k].m_strConstantValue))
          {
                  throw(e);
          }      
          var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_CONST_VALUE%>"+nConstantsCount);
          if( objFormEelement != null)
          {
            eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_CONST_VALUE%>"+nConstantsCount+".value  ='"+top.objNewDataView.m_arrDataViewConstants[k].m_strConstantValue+"';");
          }   
          else      
          {
            var objHidden= document.createElement("INPUT");
            objHidden.type ="hidden";
            objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_CONST_VALUE%>"+nConstantsCount;
            objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_CONST_VALUE%>"+nConstantsCount;
            objHidden.value = top.objNewDataView.m_arrDataViewConstants[k].m_strConstantValue;
            document.formDataViewSave.appendChild(objHidden);        
          }

          var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_CONST_DESC%>"+nConstantsCount);
          if( objFormEelement != null)
          {
            eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_CONST_DESC%>"+nConstantsCount+".value  ='"+top.objNewDataView.m_arrDataViewConstants[k].m_strConstantDescription+"';");
          }   
          else      
          {
            var objHidden= document.createElement("INPUT");
            objHidden.type ="hidden";
            objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_CONST_DESC%>"+nConstantsCount;
            objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_CONST_DESC%>"+nConstantsCount;
            objHidden.value = top.objNewDataView.m_arrDataViewConstants[k].m_strConstantDescription;
            document.formDataViewSave.appendChild(objHidden);        
          }
                   
          nConstantsCount++;
       }   
    }
     if(nConstantsCount > 0)
     {
        var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_CONST_NUM%>");
        if( objFormEelement != null)
        {
          eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_CONST_NUM%>.value  ='"+nConstantsCount+"';");
        }   
        else      
        {
          var objHidden= document.createElement("INPUT");
          objHidden.type ="hidden";
          objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_CONST_NUM%>";
          objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_CONST_NUM%>";
          objHidden.value = nConstantsCount;
          document.formDataViewSave.appendChild(objHidden);        
        }
                           
     }

///////////////////////////////////////////////////////////////////////////////////
//                          PARAMETERS
////////////////////////////////////////////////////////////////////////////////////
                  
      for(var k=0;k<top.objNewDataView.m_arrDataViewInputParameters.length;k++)
      {
        if(top.objNewDataView.m_arrDataViewInputParameters[k] != null)
        {
          if(top.objNewDataView.m_arrDataViewInputParameters[k].m_strParameterValue == '' || top.objNewDataView.m_arrDataViewInputParameters[k].m_nParameterDataType == '')
          {
               throw(e);
          }  

          var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_NAME%>"+nParametersCount);
          if( objFormEelement != null)
          {
             eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_NAME%>"+nParametersCount+".value  ='"+top.objNewDataView.m_arrDataViewInputParameters[k].m_strParameterName+"';");
          }   
          else      
          {
              var objHidden= document.createElement("INPUT");
              objHidden.type ="hidden";
              objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_NAME%>"+nParametersCount;
              objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_NAME%>"+nParametersCount;
              objHidden.value = top.objNewDataView.m_arrDataViewInputParameters[k].m_strParameterName;
              document.formDataViewSave.appendChild(objHidden);
          }


          if(!validateValue(top.objNewDataView.m_arrDataViewInputParameters[k].m_nParameterDataType,top.objNewDataView.m_arrDataViewInputParameters[k].m_strParameterValue))
          {
                 throw(e)
          }
          
          var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_VALUE%>"+nParametersCount);
          if( objFormEelement != null)
          {
             eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_VALUE%>"+nParametersCount+".value  ='"+top.objNewDataView.m_arrDataViewInputParameters[k].m_strParameterValue+"';");
          }   
          else      
          {
              var objHidden= document.createElement("INPUT");
              objHidden.type ="hidden";
              objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_VALUE%>"+nParametersCount;
              objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_VALUE%>"+nParametersCount;
              objHidden.value = top.objNewDataView.m_arrDataViewInputParameters[k].m_strParameterValue;
              document.formDataViewSave.appendChild(objHidden);
           }
           
           var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_LABEL%>"+nParametersCount);
           if( objFormEelement != null)
           {
              eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_LABEL%>"+nParametersCount+".value  ='"+top.objNewDataView.m_arrDataViewInputParameters[k].m_strParameterLabel+"';");
           }   
           else      
           {
              var objHidden= document.createElement("INPUT");
              objHidden.type ="hidden";
              objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_LABEL%>"+nParametersCount;
              objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_LABEL%>"+nParametersCount;
              objHidden.value = top.objNewDataView.m_arrDataViewInputParameters[k].m_strParameterLabel;
              document.formDataViewSave.appendChild(objHidden);
            }

           var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_CONTROL_TYPE_ID%>"+nParametersCount);
           if( objFormEelement != null)
           {
              eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_CONTROL_TYPE_ID%>"+nParametersCount+".value  ='"+top.objNewDataView.m_arrDataViewInputParameters[k].m_nParameterControlType+"';");
           }   
           else      
           {
              var objHidden= document.createElement("INPUT");
              objHidden.type ="hidden";
              objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_CONTROL_TYPE_ID%>"+nParametersCount;
              objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_CONTROL_TYPE_ID%>"+nParametersCount;
              objHidden.value = top.objNewDataView.m_arrDataViewInputParameters[k].m_nParameterControlType;
              document.formDataViewSave.appendChild(objHidden);
            }

           var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_DESC%>"+nParametersCount);
           if( objFormEelement != null)
           {
              eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_DESC%>"+nParametersCount+".value  ='"+top.objNewDataView.m_arrDataViewInputParameters[k].m_strParameterDescription+"';");
           }   
           else      
           {
              var objHidden= document.createElement("INPUT");
              objHidden.type ="hidden";
              objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_DESC%>"+nParametersCount;
              objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_DESC%>"+nParametersCount;
              objHidden.value = top.objNewDataView.m_arrDataViewInputParameters[k].m_strParameterDescription;
              document.formDataViewSave.appendChild(objHidden);
            }

           var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_TYPE%>"+nParametersCount);
           if( objFormEelement != null)
           {
              eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_TYPE%>"+nParametersCount+".value  ='"+top.objNewDataView.m_arrDataViewInputParameters[k].m_nParameterDataType+"';");
           }   
           else      
           {
              var objHidden= document.createElement("INPUT");
              objHidden.type ="hidden";
              objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_TYPE%>"+nParametersCount;
              objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_TYPE%>"+nParametersCount;
              objHidden.value = top.objNewDataView.m_arrDataViewInputParameters[k].m_nParameterDataType;
              document.formDataViewSave.appendChild(objHidden);
            }
            nParametersCount++;
        }
    }
    if(nParametersCount > 0)
    {
        var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAMS_NUM%>");
        if( objFormEelement != null)
        {
          eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAMS_NUM%>.value  ='"+nParametersCount+"';");
        }   
        else      
        {
          var objHidden= document.createElement("INPUT");
          objHidden.type ="hidden";
          objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAMS_NUM%>";
          objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAMS_NUM%>";
          objHidden.value = nParametersCount;
          document.formDataViewSave.appendChild(objHidden);        
        }
    }


///////////////////////////////////////////////////////////////////////////////////
//                          FUNCTIONS
////////////////////////////////////////////////////////////////////////////////////

      for(var k=0;k<top.objNewDataView.m_arrDataViewFunctions.length;k++)
      {
        if(top.objNewDataView.m_arrDataViewFunctions[k] != null)
        {

           var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_ID%>"+nFunctionsCount);
           if( objFormEelement != null)
           {
              eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_ID%>"+nFunctionsCount+".value  ='"+top.objNewDataView.m_arrDataViewFunctions[k].m_nFunctionID+"';");
           }   
           else      
           {
              var objHidden= document.createElement("INPUT");
              objHidden.type ="hidden";
              objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_ID%>"+nFunctionsCount;
              objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_ID%>"+nFunctionsCount;
              objHidden.value = top.objNewDataView.m_arrDataViewFunctions[k].m_nFunctionID;
              document.formDataViewSave.appendChild(objHidden);
            }
 
           var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount);
           if( objFormEelement != null)
           {
              eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+".value  ='"+top.objNewDataView.m_arrDataViewFunctions[k].m_strFunctionName+"';");
           }   
           else      
           {
              var objHidden= document.createElement("INPUT");
              objHidden.type ="hidden";
              objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount;
              objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount;
              objHidden.value = top.objNewDataView.m_arrDataViewFunctions[k].m_strFunctionName;
              document.formDataViewSave.appendChild(objHidden);
            }

            var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_TYPE%>"+nFunctionsCount);
            if( objFormEelement != null)
            {
                eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_TYPE%>"+nFunctionsCount+".value  ='"+top.objNewDataView.m_arrDataViewFunctions[k].m_nFunctionType+"';");
            }   
            else      
            {
                var objHidden= document.createElement("INPUT");
                objHidden.type ="hidden";
                objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_TYPE%>"+nFunctionsCount;
                objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_TYPE%>"+nFunctionsCount;
                objHidden.value = top.objNewDataView.m_arrDataViewFunctions[k].m_nFunctionType;
                document.formDataViewSave.appendChild(objHidden);        
            }

            var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_SQLCALL%>"+nFunctionsCount);
            if( objFormEelement != null)
            {
                    eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_SQLCALL%>"+nFunctionsCount+".value  ='"+top.objNewDataView.m_arrDataViewFunctions[k].m_strFunctionSQL+"';");
            }   
            else      
            {
                var objHidden= document.createElement("INPUT");
                objHidden.type ="hidden";
                objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_SQLCALL%>"+nFunctionsCount;
                objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_SQLCALL%>"+nFunctionsCount;
                objHidden.value = top.objNewDataView.m_arrDataViewFunctions[k].m_strFunctionSQL;
                document.formDataViewSave.appendChild(objHidden);        
            }


            var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETERS_NUM%>"+nFunctionsCount);
            if( objFormEelement != null)
            {
                if(top.objNewDataView.m_arrDataViewFunctionParameters[k] != null)
                    eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETERS_NUM%>"+nFunctionsCount+".value  ='"+top.objNewDataView.m_arrDataViewFunctionParameters[k].length+"';");
                else    
                    eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETERS_NUM%>"+nFunctionsCount+".value  = 0;");
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
                document.formDataViewSave.appendChild(objHidden);        
            }

            var arrParameterValues  = top.objNewDataView.m_arrDataViewFunctionParameters[k];
            if(arrParameterValues != null)
            {
                  for (var i = 0; i < arrParameterValues.length; i++) 
                  {
                      var objParameterValue = arrParameterValues[i];
                      if(objParameterValue != null)
                      {
                          var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_ID%>"+i);
                          if( objFormEelement != null)
                          {
                              eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_ID%>"+i+".value  ='"+objParameterValue.m_nParameterDefinitionID+"';");
                          }   
                          else      
                          {
                              var objHidden= document.createElement("INPUT");
                              objHidden.type ="hidden";
                              objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_ID%>"+i;
                              objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_ID%>"+i
                              objHidden.value = objParameterValue.m_nParameterDefinitionID;
                              document.formDataViewSave.appendChild(objHidden);        

                          }
                      
                          var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_TYPE%>"+i);
                          if( objFormEelement != null)
                          {
                              eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_TYPE%>"+i+".value  ='"+objParameterValue.m_nParameterType+"';");
                          }   
                          else      
                          {
                              var objHidden= document.createElement("INPUT");
                              objHidden.type ="hidden";
                              objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_TYPE%>"+i;
                              objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_TYPE%>"+i
                              objHidden.value = objParameterValue.m_nParameterType;
                              document.formDataViewSave.appendChild(objHidden);        
                          }

                          if(objParameterValue.m_nParameterType == <%=QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD%>)
                          {
                              var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_FIELD_ID%>"+i);
                              if( objFormEelement != null)
                              {
                                  eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_FIELD_ID%>"+i+".value  ='"+objParameterValue.m_objField.m_nFieldID+"';");
                              }   
                              else      
                              {
                                  var objHidden= document.createElement("INPUT");
                                  objHidden.type ="hidden";
                                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_FIELD_ID%>"+i;
                                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_FIELD_ID%>"+i
                                  objHidden.value = objParameterValue.m_objField.m_nFieldID;
                                  document.formDataViewSave.appendChild(objHidden);        
                              }

                              var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_FIELD_NAME%>"+i);
                              if( objFormEelement != null)
                              {
                                  eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_FIELD_NAME%>"+i+".value  ='"+objParameterValue.m_objField.m_strFieldName+"';");
                              }   
                              else      
                              {
                                  var objHidden= document.createElement("INPUT");
                                  objHidden.type ="hidden";
                                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_FIELD_NAME%>"+i;
                                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_FIELD_NAME%>"+i
                                  objHidden.value = objParameterValue.m_objField.m_strFieldName;
                                  document.formDataViewSave.appendChild(objHidden);        
                              }

                              var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_FIELD_DATAVIEW_ID%>"+i);
                              if( objFormEelement != null)
                              {
                                  eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_FIELD_DATAVIEW_ID%>"+i+".value  ='"+objParameterValue.m_objField.m_nDataViewID+"';");
                              }   
                              else      
                              {
                                  var objHidden= document.createElement("INPUT");
                                  objHidden.type ="hidden";
                                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_FIELD_DATAVIEW_ID%>"+i;
                                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_FIELD_DATAVIEW_ID%>"+i
                                  objHidden.value = objParameterValue.m_objField.m_nDataViewID;
                                  document.formDataViewSave.appendChild(objHidden);        
                              }

                              var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_FIELD_DATAVIEW_NAME%>"+i);
                              if( objFormEelement != null)
                              {
                                  eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_FIELD_DATAVIEW_NAME%>"+i+".value  ='"+objParameterValue.m_objField.m_strDataViewName+"';");
                              }   
                              else      
                              {
                                  var objHidden= document.createElement("INPUT");
                                  objHidden.type ="hidden";
                                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_FIELD_DATAVIEW_NAME%>"+i;
                                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_FIELD_DATAVIEW_NAME%>"+i
                                  objHidden.value = objParameterValue.m_objField.m_strDataViewName;
                                  document.formDataViewSave.appendChild(objHidden);        
                              }

                              var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_FIELD_DATAVIEW_TYPE%>"+i);
                              if( objFormEelement != null)
                              {
                                  eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_FIELD_DATAVIEW_TYPE%>"+i+".value  ='"+objParameterValue.m_objField.m_nDataViewType+"';");
                              }   
                              else      
                              {
                                  var objHidden= document.createElement("INPUT");
                                  objHidden.type ="hidden";
                                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_FIELD_DATAVIEW_TYPE%>"+i;
                                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_FIELD_DATAVIEW_TYPE%>"+i
                                  objHidden.value = objParameterValue.m_objField.m_nDataViewType;
                                  document.formDataViewSave.appendChild(objHidden);        
                              }
                          }
                          else
                          {
                              if(objParameterValue.m_nParameterType == <%=QueryBuilderInterfaceKey.FIELD_TYPE_CONST_FIELD%>)                     
                              {
                                    var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_INDEX%>"+i);
                                    if( objFormEelement != null)
                                    {
                                        eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_INDEX%>"+i+".value  ='"+objParameterValue.m_nParameterIndex+"';");
                                    }   
                                    else      
                                    {
                                        var objHidden= document.createElement("INPUT");
                                        objHidden.type ="hidden";
                                        objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_INDEX%>"+i;
                                        objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_INDEX%>"+i
                                        objHidden.value = objParameterValue.m_nParameterIndex;
                                        document.formDataViewSave.appendChild(objHidden);        
                                    }

                                    var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_VALUE%>"+i);
                                    if( objFormEelement != null)
                                    {
                                        eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_VALUE%>"+i+".value  ='"+objParameterValue.m_strParameterValue+"';");
                                    }   
                                    else      
                                    {
                                        var objHidden= document.createElement("INPUT");
                                        objHidden.type ="hidden";
                                        objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_VALUE%>"+i;
                                        objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_VALUE%>"+i
                                        objHidden.value = objParameterValue.m_strParameterValue;
                                        document.formDataViewSave.appendChild(objHidden);        
                                    }
                              }
                              else if(objParameterValue.m_nParameterType == <%=QueryBuilderInterfaceKey.FIELD_TYPE_PARAMETER_FIELD%>)                     
                              {
                                    //waseem
                                    var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_INDEX%>"+i);
                                    if( objFormEelement != null)
                                    {
                                        eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_INDEX%>"+i+".value  ='"+objParameterValue.m_nParameterIndex+"';");
                                    }   
                                    else      
                                    {
                                        var objHidden= document.createElement("INPUT");
                                        objHidden.type ="hidden";
                                        objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_INDEX%>"+i;
                                        objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_INDEX%>"+i
                                        objHidden.value = objParameterValue.m_nParameterIndex;
                                        document.formDataViewSave.appendChild(objHidden);        
                                    }

                                    var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_VALUE%>"+i);
                                    if( objFormEelement != null)
                                    {
                                        eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_VALUE%>"+i+".value  ='"+objParameterValue.m_strParameterValue+"';");
                                    }   
                                    else      
                                    {
                                        var objHidden= document.createElement("INPUT");
                                        objHidden.type ="hidden";
                                        objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_VALUE%>"+i;
                                        objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_VALUE%>"+i
                                        objHidden.value = objParameterValue.m_strParameterValue;
                                        document.formDataViewSave.appendChild(objHidden);        
                                    }
                              }
                              else
                                  if(objParameterValue.m_nParameterType == <%=QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_FIELD%>)
                                  {
                                      var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_INDEX%>"+i);
                                      if( objFormEelement != null)
                                      {
                                          eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_INDEX%>"+i+".value  ='"+objParameterValue.m_nParameterIndex+"';");
                                      }   
                                      else      
                                      {
                                          var objHidden= document.createElement("INPUT");
                                          objHidden.type ="hidden";
                                          objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_INDEX%>"+i;
                                          objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME%>"+nFunctionsCount+"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_INDEX%>"+i
                                          objHidden.value = objParameterValue.m_nParameterIndex;
                                          document.formDataViewSave.appendChild(objHidden);        
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
        var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELDS_NUM%>");
        if( objFormEelement != null)
        {
          eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELDS_NUM%>.value  ='"+nFunctionsCount+"';");
        }   
        else      
        {
          var objHidden= document.createElement("INPUT");
          objHidden.type ="hidden";
          objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELDS_NUM%>";
          objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELDS_NUM%>";
          objHidden.value = nFunctionsCount;
          document.formDataViewSave.appendChild(objHidden);        
        }
     }



                  




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
                var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_TERM_NAME%>"+nFtFTermsCount);
                if( objFormEelement != null)
                {
                  eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_TERM_NAME%>"+nFtFTermsCount+".value  ='"+objTerm.m_strTermName+"';");
                }   
                else      
                {
                  var objHidden= document.createElement("INPUT");
                  objHidden.type ="hidden";
                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_TERM_NAME%>"+nFtFTermsCount;
                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_TERM_NAME%>"+nFtFTermsCount;
                  objHidden.value = objTerm.m_strTermName;
                  document.formDataViewSave.appendChild(objHidden);
                }

////////////////////////////////////////////////////////////////////////////////////
//  Field to Field Term 1st Operand Filling
/////////////////////////////////////////////////////////////////////////////////////
                
                var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_1ST_FIELD_ID%>"+nFtFTermsCount);
                if( objFormEelement != null)
                {
                  eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_1ST_FIELD_ID%>"+nFtFTermsCount+".value  ='"+objTerm.m_obj1stOperand.m_nFieldID+"';");
                }   
                else      
                {
                  var objHidden= document.createElement("INPUT");
                  objHidden.type ="hidden";
                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_1ST_FIELD_ID%>"+nFtFTermsCount;
                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_1ST_FIELD_ID%>"+nFtFTermsCount;
                  objHidden.value = objTerm.m_obj1stOperand.m_nFieldID;
                  document.formDataViewSave.appendChild(objHidden);
                }

                var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_1ST_FIELD_NAME%>"+nFtFTermsCount);
                if( objFormEelement != null)
                {
                  eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_1ST_FIELD_NAME%>"+nFtFTermsCount+".value  ='"+objTerm.m_obj1stOperand.m_strFieldName+"';");
                }   
                else      
                {
                  var objHidden= document.createElement("INPUT");
                  objHidden.type ="hidden";
                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_1ST_FIELD_NAME%>"+nFtFTermsCount;
                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_1ST_FIELD_NAME%>"+nFtFTermsCount;
                  objHidden.value = objTerm.m_obj1stOperand.m_strFieldName;
                  document.formDataViewSave.appendChild(objHidden);
                }

                var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_1ST_FIELD_TYPE%>"+nFtFTermsCount);
                if( objFormEelement != null)
                {
                  eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_1ST_FIELD_TYPE%>"+nFtFTermsCount+".value  ='"+objTerm.m_obj1stOperand.m_nFieldType+"';");
                }   
                else      
                {
                  var objHidden= document.createElement("INPUT");
                  objHidden.type ="hidden";
                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_1ST_FIELD_TYPE%>"+nFtFTermsCount;
                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_1ST_FIELD_TYPE%>"+nFtFTermsCount;
                  objHidden.value = objTerm.m_obj1stOperand.m_nFieldType;
                  document.formDataViewSave.appendChild(objHidden);
                }

                if(objTerm.m_obj1stOperand.m_nFieldType == <%=QueryBuilderInterfaceKey.FIELD_TYPE_CONST_FIELD%>)
                {
                  var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_1ST_FIELD_VALUE%>"+nFtFTermsCount);
                  if( objFormEelement != null)
                  {
                    eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_1ST_FIELD_VALUE%>"+nFtFTermsCount+".value  ='"+top.objNewDataView.m_arrDataViewConstants[objTerm.m_obj1stOperand.m_nFieldIndex].m_strConstantValue+"';");
                  }   
                  else      
                  {
                    var objHidden= document.createElement("INPUT");
                    objHidden.type ="hidden";
                    objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_1ST_FIELD_VALUE%>"+nFtFTermsCount;
                    objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_1ST_FIELD_VALUE%>"+nFtFTermsCount;
                    objHidden.value = top.objNewDataView.m_arrDataViewConstants[objTerm.m_obj1stOperand.m_nFieldIndex].m_strConstantValue;
                    document.formDataViewSave.appendChild(objHidden);
                  }   
                }
                else
                {
                  if(objTerm.m_obj1stOperand.m_nFieldType == <%=QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD%>)
                  {
                    var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_1ST_FIELD_DATAVIEW_ID%>"+nFtFTermsCount);
                    if( objFormEelement != null)
                    {
                      eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_1ST_FIELD_DATAVIEW_ID%>"+nFtFTermsCount+".value  ='"+objTerm.m_obj1stOperand.m_nDataViewID+"';");
                    }   
                    else      
                    {
                      var objHidden= document.createElement("INPUT");
                      objHidden.type ="hidden";
                      objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_1ST_FIELD_DATAVIEW_ID%>"+nFtFTermsCount;
                      objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_1ST_FIELD_DATAVIEW_ID%>"+nFtFTermsCount;
                      objHidden.value = objTerm.m_obj1stOperand.m_nDataViewID;
                      document.formDataViewSave.appendChild(objHidden);
                    }   

                    var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_1ST_FIELD_DATAVIEW_NAME%>"+nFtFTermsCount);
                    if( objFormEelement != null)
                    {
                      eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_1ST_FIELD_DATAVIEW_NAME%>"+nFtFTermsCount+".value  ='"+objTerm.m_obj1stOperand.m_strDataViewName+"';");
                    }   
                    else      
                    {
                      var objHidden= document.createElement("INPUT");
                      objHidden.type ="hidden";
                      objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_1ST_FIELD_DATAVIEW_NAME%>"+nFtFTermsCount;
                      objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_1ST_FIELD_DATAVIEW_NAME%>"+nFtFTermsCount;
                      objHidden.value = objTerm.m_obj1stOperand.m_strDataViewName;
                      document.formDataViewSave.appendChild(objHidden);
                    }   

                    var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_1ST_FIELD_DATAVIEW_TYPE%>"+nFtFTermsCount);
                    if( objFormEelement != null)
                    {
                      eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_1ST_FIELD_DATAVIEW_TYPE%>"+nFtFTermsCount+".value  ='"+objTerm.m_obj1stOperand.m_nDataViewType+"';");
                    }   
                    else      
                    {
                      var objHidden= document.createElement("INPUT");
                      objHidden.type ="hidden";
                      objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_1ST_FIELD_DATAVIEW_TYPE%>"+nFtFTermsCount;
                      objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_1ST_FIELD_DATAVIEW_TYPE%>"+nFtFTermsCount;
                      objHidden.value = objTerm.m_obj1stOperand.m_nDataViewType;
                      document.formDataViewSave.appendChild(objHidden);
                    }   
                   }
                }

////////////////////////////////////////////////////////////////////////////////////
//  Field to Field Term Operator Filling
/////////////////////////////////////////////////////////////////////////////////////
                var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_OPERATOR_ID%>"+nFtFTermsCount);
                if( objFormEelement != null)
                {
                  eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_OPERATOR_ID%>"+nFtFTermsCount+".value  ='"+top.objDataViewWizard.m_arrDataViewFtFOperators[objTerm.m_nOperatorIndex].m_nOperatorID+"';");
                }   
                else      
                {
                  var objHidden= document.createElement("INPUT");
                  objHidden.type ="hidden";
                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_OPERATOR_ID%>"+nFtFTermsCount;
                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_OPERATOR_ID%>"+nFtFTermsCount;
                  objHidden.value = top.objDataViewWizard.m_arrDataViewFtFOperators[objTerm.m_nOperatorIndex].m_nOperatorID;
                  document.formDataViewSave.appendChild(objHidden);
                }

                var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_OPERATOR_SQL%>"+nFtFTermsCount);
                if( objFormEelement != null)
                {
                  eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_OPERATOR_SQL%>"+nFtFTermsCount+".value  ='"+top.objDataViewWizard.m_arrDataViewFtFOperators[objTerm.m_nOperatorIndex].m_strOperatorSQL+"';");
                }   
                else      
                {
                  var objHidden= document.createElement("INPUT");
                  objHidden.type ="hidden";
                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_OPERATOR_SQL%>"+nFtFTermsCount;
                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_OPERATOR_SQL%>"+nFtFTermsCount;
                  objHidden.value = top.objDataViewWizard.m_arrDataViewFtFOperators[objTerm.m_nOperatorIndex].m_strOperatorSQL;
                  document.formDataViewSave.appendChild(objHidden);
                }

                var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_OPERATOR_NAME%>"+nFtFTermsCount);
                if( objFormEelement != null)
                {
                  eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_OPERATOR_NAME%>"+nFtFTermsCount+".value  ='"+top.objDataViewWizard.m_arrDataViewFtFOperators[objTerm.m_nOperatorIndex].m_strOperatorName+"';");
                }   
                else      
                {
                  var objHidden= document.createElement("INPUT");
                  objHidden.type ="hidden";
                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_OPERATOR_NAME%>"+nFtFTermsCount;
                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_OPERATOR_NAME%>"+nFtFTermsCount;
                  objHidden.value = top.objDataViewWizard.m_arrDataViewFtFOperators[objTerm.m_nOperatorIndex].m_strOperatorName;
                  document.formDataViewSave.appendChild(objHidden);
                }

////////////////////////////////////////////////////////////////////////////////////
//  Field to Field Term 2nd Operand Filling
/////////////////////////////////////////////////////////////////////////////////////

                var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_2ND_FIELD_ID%>"+nFtFTermsCount);
                if( objFormEelement != null)
                {
                  eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_2ND_FIELD_ID%>"+nFtFTermsCount+".value  ='"+objTerm.m_obj2ndOperand.m_nFieldID+"';");
                }   
                else      
                {
                  var objHidden= document.createElement("INPUT");
                  objHidden.type ="hidden";
                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_2ND_FIELD_ID%>"+nFtFTermsCount;
                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_2ND_FIELD_ID%>"+nFtFTermsCount;
                  objHidden.value = objTerm.m_obj2ndOperand.m_nFieldID;
                  document.formDataViewSave.appendChild(objHidden);
                }

                var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_2ND_FIELD_NAME%>"+nFtFTermsCount);
                if( objFormEelement != null)
                {
                  eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_2ND_FIELD_NAME%>"+nFtFTermsCount+".value  ='"+objTerm.m_obj2ndOperand.m_strFieldName+"';");
                }   
                else      
                {
                  var objHidden= document.createElement("INPUT");
                  objHidden.type ="hidden";
                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_2ND_FIELD_NAME%>"+nFtFTermsCount;
                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_2ND_FIELD_NAME%>"+nFtFTermsCount;
                  objHidden.value = objTerm.m_obj2ndOperand.m_strFieldName;
                  document.formDataViewSave.appendChild(objHidden);
                }

                var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_2ND_FIELD_TYPE%>"+nFtFTermsCount);
                if( objFormEelement != null)
                {
                  eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_2ND_FIELD_TYPE%>"+nFtFTermsCount+".value  ='"+objTerm.m_obj2ndOperand.m_nFieldType+"';");
                }   
                else      
                {
                  var objHidden= document.createElement("INPUT");
                  objHidden.type ="hidden";
                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_2ND_FIELD_TYPE%>"+nFtFTermsCount;
                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_2ND_FIELD_TYPE%>"+nFtFTermsCount;
                  objHidden.value = objTerm.m_obj2ndOperand.m_nFieldType;
                  document.formDataViewSave.appendChild(objHidden);
                }

                if(objTerm.m_obj2ndOperand.m_nFieldType == <%=QueryBuilderInterfaceKey.FIELD_TYPE_CONST_FIELD%>)
                {
                  var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_2ND_FIELD_VALUE%>"+nFtFTermsCount);
                  if( objFormEelement != null)
                  {
                    eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_2ND_FIELD_VALUE%>"+nFtFTermsCount+".value  ='"+top.objNewDataView.m_arrDataViewConstants[objTerm.m_obj2ndOperand.m_nFieldIndex].m_strConstantValue+"';");
                  }   
                  else      
                  {
                    var objHidden= document.createElement("INPUT");
                    objHidden.type ="hidden";
                    objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_2ND_FIELD_VALUE%>"+nFtFTermsCount;
                    objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_2ND_FIELD_VALUE%>"+nFtFTermsCount;
                    objHidden.value = top.objNewDataView.m_arrDataViewConstants[objTerm.m_obj2ndOperand.m_nFieldIndex].m_strConstantValue;
                    document.formDataViewSave.appendChild(objHidden);
                  }   
                }
                else
                {
                  if(objTerm.m_obj2ndOperand.m_nFieldType == <%=QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD%>)
                  {
                    var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_2ND_FIELD_DATAVIEW_ID%>"+nFtFTermsCount);
                    if( objFormEelement != null)
                    {
                      eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_2ND_FIELD_DATAVIEW_ID%>"+nFtFTermsCount+".value  ='"+objTerm.m_obj2ndOperand.m_nDataViewID+"';");
                    }   
                    else      
                    {
                      var objHidden= document.createElement("INPUT");
                      objHidden.type ="hidden";
                      objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_2ND_FIELD_DATAVIEW_ID%>"+nFtFTermsCount;
                      objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_2ND_FIELD_DATAVIEW_ID%>"+nFtFTermsCount;
                      objHidden.value = objTerm.m_obj2ndOperand.m_nDataViewID;
                      document.formDataViewSave.appendChild(objHidden);
                    }   
                    
                    var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_2ND_FIELD_DATAVIEW_NAME%>"+nFtFTermsCount);
                    if( objFormEelement != null)
                    {
                      eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_2ND_FIELD_DATAVIEW_NAME%>"+nFtFTermsCount+".value  ='"+objTerm.m_obj2ndOperand.m_strDataViewName+"';");
                    }   
                    else      
                    {
                      var objHidden= document.createElement("INPUT");
                      objHidden.type ="hidden";
                      objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_2ND_FIELD_DATAVIEW_NAME%>"+nFtFTermsCount;
                      objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_2ND_FIELD_DATAVIEW_NAME%>"+nFtFTermsCount;
                      objHidden.value = objTerm.m_obj2ndOperand.m_strDataViewName;
                      document.formDataViewSave.appendChild(objHidden);
                    }   

                    var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_2ND_FIELD_DATAVIEW_TYPE%>"+nFtFTermsCount);
                    if( objFormEelement != null)
                    {
                      eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_2ND_FIELD_DATAVIEW_TYPE%>"+nFtFTermsCount+".value  ='"+objTerm.m_obj2ndOperand.m_nDataViewType+"';");
                    }   
                    else      
                    {
                      var objHidden= document.createElement("INPUT");
                      objHidden.type ="hidden";
                      objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_2ND_FIELD_DATAVIEW_TYPE%>"+nFtFTermsCount;
                      objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_2ND_FIELD_DATAVIEW_TYPE%>"+nFtFTermsCount;
                      objHidden.value = objTerm.m_obj2ndOperand.m_nDataViewType;
                      document.formDataViewSave.appendChild(objHidden);
                    }   

                  }   
                }
                nFtFTermsCount++;
          }   
          else // else Field to Data View Term
          {
               var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_TERM_NAME%>"+nFtDVTermsCount);
               if( objFormEelement != null)
               {
                  eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_TERM_NAME%>"+nFtDVTermsCount+".value  ='"+objTerm.m_strTermName+"';");
               }   
               else      
               {
                  var objHidden= document.createElement("INPUT");
                  objHidden.type ="hidden";
                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_TERM_NAME%>"+nFtDVTermsCount;
                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_TERM_NAME%>"+nFtDVTermsCount;
                  objHidden.value = objTerm.m_strTermName;
                  document.formDataViewSave.appendChild(objHidden);
               }

////////////////////////////////////////////////////////////////////////////////////
//  Field to Data View Term 1st Operand Filling
/////////////////////////////////////////////////////////////////////////////////////
                
                var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_1ST_FIELD_ID%>"+nFtDVTermsCount);
                if( objFormEelement != null)
                {
                  eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_1ST_FIELD_ID%>"+nFtDVTermsCount+".value  ='"+objTerm.m_obj1stOperand.m_nFieldID+"';");
                }   
                else      
                {
                  var objHidden= document.createElement("INPUT");
                  objHidden.type ="hidden";
                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_1ST_FIELD_ID%>"+nFtDVTermsCount;
                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_1ST_FIELD_ID%>"+nFtDVTermsCount;
                  objHidden.value = objTerm.m_obj1stOperand.m_nFieldID;
                  document.formDataViewSave.appendChild(objHidden);
                }

                var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_1ST_FIELD_NAME%>"+nFtDVTermsCount);
                if( objFormEelement != null)
                {
                  eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_1ST_FIELD_NAME%>"+nFtDVTermsCount+".value  ='"+objTerm.m_obj1stOperand.m_strFieldName+"';");
                }   
                else      
                {
                  var objHidden= document.createElement("INPUT");
                  objHidden.type ="hidden";
                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_1ST_FIELD_NAME%>"+nFtDVTermsCount;
                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_1ST_FIELD_NAME%>"+nFtDVTermsCount;
                  objHidden.value = objTerm.m_obj1stOperand.m_strFieldName;
                  document.formDataViewSave.appendChild(objHidden);
                }

                var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_1ST_FIELD_TYPE%>"+nFtDVTermsCount);
                if( objFormEelement != null)
                {
                  eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_1ST_FIELD_TYPE%>"+nFtDVTermsCount+".value  ='"+objTerm.m_obj1stOperand.m_nFieldType+"';");
                }   
                else      
                {
                  var objHidden= document.createElement("INPUT");
                  objHidden.type ="hidden";
                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_1ST_FIELD_TYPE%>"+nFtDVTermsCount;
                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_1ST_FIELD_TYPE%>"+nFtDVTermsCount;
                  objHidden.value = objTerm.m_obj1stOperand.m_nFieldType;
                  document.formDataViewSave.appendChild(objHidden);
                }

                if(objTerm.m_obj1stOperand.m_nFieldType == <%=QueryBuilderInterfaceKey.FIELD_TYPE_CONST_FIELD%>)
                {
                  var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_1ST_FIELD_VALUE%>"+nFtDVTermsCount);
                  if( objFormEelement != null)
                  {
                    eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_1ST_FIELD_VALUE%>"+nFtDVTermsCount+".value  ='"+top.objNewDataView.m_arrDataViewConstants[objTerm.m_obj1stOperand.m_nFieldIndex].m_strConstantValue+"';");
                  }   
                  else      
                  {
                    var objHidden= document.createElement("INPUT");
                    objHidden.type ="hidden";
                    objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_1ST_FIELD_VALUE%>"+nFtDVTermsCount;
                    objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_1ST_FIELD_VALUE%>"+nFtDVTermsCount;
                    objHidden.value = top.objNewDataView.m_arrDataViewConstants[objTerm.m_obj1stOperand.m_nFieldIndex].m_strConstantValue;
                    document.formDataViewSave.appendChild(objHidden);
                  }   
                }
                else
                {
                  if(objTerm.m_obj1stOperand.m_nFieldType == <%=QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD%>)
                  {
                    var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_1ST_FIELD_DATAVIEW_ID%>"+nFtDVTermsCount);
                    if( objFormEelement != null)
                    {
                      eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_1ST_FIELD_DATAVIEW_ID%>"+nFtDVTermsCount+".value  ='"+objTerm.m_obj1stOperand.m_nDataViewID+"';");
                    }   
                    else      
                    {
                      var objHidden= document.createElement("INPUT");
                      objHidden.type ="hidden";
                      objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_1ST_FIELD_DATAVIEW_ID%>"+nFtDVTermsCount;
                      objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_1ST_FIELD_DATAVIEW_ID%>"+nFtDVTermsCount;
                      objHidden.value = objTerm.m_obj1stOperand.m_nDataViewID;
                      document.formDataViewSave.appendChild(objHidden);
                    }   

                    var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_1ST_FIELD_DATAVIEW_NAME%>"+nFtDVTermsCount);
                    if( objFormEelement != null)
                    {
                      eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_1ST_FIELD_DATAVIEW_NAME%>"+nFtDVTermsCount+".value  ='"+objTerm.m_obj1stOperand.m_strDataViewName+"';");
                    }   
                    else      
                    {
                      var objHidden= document.createElement("INPUT");
                      objHidden.type ="hidden";
                      objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_1ST_FIELD_DATAVIEW_NAME%>"+nFtDVTermsCount;
                      objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_1ST_FIELD_DATAVIEW_NAME%>"+nFtDVTermsCount;
                      objHidden.value = objTerm.m_obj1stOperand.m_strDataViewName;
                      document.formDataViewSave.appendChild(objHidden);
                    }   

                    var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_1ST_FIELD_DATAVIEW_TYPE%>"+nFtDVTermsCount);
                    if( objFormEelement != null)
                    {
                      eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_1ST_FIELD_DATAVIEW_TYPE%>"+nFtDVTermsCount+".value  ='"+objTerm.m_obj1stOperand.m_nDataViewType+"';");
                    }   
                    else      
                    {
                      var objHidden= document.createElement("INPUT");
                      objHidden.type ="hidden";
                      objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_1ST_FIELD_DATAVIEW_TYPE%>"+nFtDVTermsCount;
                      objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_1ST_FIELD_DATAVIEW_TYPE%>"+nFtDVTermsCount;
                      objHidden.value = objTerm.m_obj1stOperand.m_nDataViewType;
                      document.formDataViewSave.appendChild(objHidden);
                    }   
                   }
                }

////////////////////////////////////////////////////////////////////////////////////
//  Field to Data View Term Operator Filling
/////////////////////////////////////////////////////////////////////////////////////
                var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_OPERATOR_ID%>"+nFtDVTermsCount);
                if( objFormEelement != null)
                {
                  eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_OPERATOR_ID%>"+nFtDVTermsCount+".value  ='"+top.objDataViewWizard.m_arrDataViewFtDVOperators[objTerm.m_nOperatorIndex].m_nOperatorID+"';");
                }   
                else      
                {
                  var objHidden= document.createElement("INPUT");
                  objHidden.type ="hidden";
                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_OPERATOR_ID%>"+nFtDVTermsCount;
                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_OPERATOR_ID%>"+nFtDVTermsCount;
                  objHidden.value = top.objDataViewWizard.m_arrDataViewFtDVOperators[objTerm.m_nOperatorIndex].m_nOperatorID;
                  document.formDataViewSave.appendChild(objHidden);
                }

                var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_OPERATOR_SQL%>"+nFtDVTermsCount);
                if( objFormEelement != null)
                {
                  eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_OPERATOR_SQL%>"+nFtDVTermsCount+".value  ='"+top.objDataViewWizard.m_arrDataViewFtDVOperators[objTerm.m_nOperatorIndex].m_strOperatorSQL+"';");
                }   
                else      
                {
                  var objHidden= document.createElement("INPUT");
                  objHidden.type ="hidden";
                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_OPERATOR_SQL%>"+nFtDVTermsCount;
                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_OPERATOR_SQL%>"+nFtDVTermsCount;
                  objHidden.value = top.objDataViewWizard.m_arrDataViewFtDVOperators[objTerm.m_nOperatorIndex].m_strOperatorSQL;
                  document.formDataViewSave.appendChild(objHidden);
                }

////////////////////////////////////////////////////////////////////////////////////
//  Field to Data View Term 2nd Operand Filling
/////////////////////////////////////////////////////////////////////////////////////

                var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_RELATED_DATAVIEW_ID%>"+nFtDVTermsCount);
                if( objFormEelement != null)
                {
                  eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_RELATED_DATAVIEW_ID%>"+nFtDVTermsCount+".value  ='"+objTerm.m_obj2ndOperand.m_nDataViewID+"';");
                }   
                else      
                {
                  var objHidden= document.createElement("INPUT");
                  objHidden.type ="hidden";
                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_RELATED_DATAVIEW_ID%>"+nFtDVTermsCount;
                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_RELATED_DATAVIEW_ID%>"+nFtDVTermsCount;
                  objHidden.value = objTerm.m_obj2ndOperand.m_nDataViewID;
                  document.formDataViewSave.appendChild(objHidden);
                }

                var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_RELATED_DATAVIEW_NAME%>"+nFtDVTermsCount);
                if( objFormEelement != null)
                {
                  eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_RELATED_DATAVIEW_NAME%>"+nFtDVTermsCount+".value  ='"+objTerm.m_obj2ndOperand.m_strDataViewName+"';");
                }   
                else      
                {
                  var objHidden= document.createElement("INPUT");
                  objHidden.type ="hidden";
                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_RELATED_DATAVIEW_NAME%>"+nFtDVTermsCount;
                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_RELATED_DATAVIEW_NAME%>"+nFtDVTermsCount;
                  objHidden.value = objTerm.m_obj2ndOperand.m_strDataViewName;
                  document.formDataViewSave.appendChild(objHidden);
                }
                var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_RELATED_DATAVIEW_TYPE_ID%>"+nFtDVTermsCount);
                if( objFormEelement != null)
                {
                  eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_RELATED_DATAVIEW_TYPE_ID%>"+nFtDVTermsCount+".value  ='"+objTerm.m_obj2ndOperand.m_nDataViewType+"';");
                }   
                else      
                {
                  var objHidden= document.createElement("INPUT");
                  objHidden.type ="hidden";
                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_RELATED_DATAVIEW_TYPE_ID%>"+nFtDVTermsCount;
                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_RELATED_DATAVIEW_TYPE_ID%>"+nFtDVTermsCount;
                  objHidden.value = objTerm.m_obj2ndOperand.m_nDataViewType;
                  document.formDataViewSave.appendChild(objHidden);
                }
             nFtDVTermsCount++;   
             }
          }
       }
       if(nFtFTermsCount > 0)
       {
          var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_NUM%>");
          if( objFormEelement != null)
          {
            eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_NUM%>.value  ='"+nFtFTermsCount+"';");
          }   
          else      
          {
            var objHidden= document.createElement("INPUT");
            objHidden.type ="hidden";
            objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_NUM%>";
            objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_NUM%>";
            objHidden.value = nFtFTermsCount;
            document.formDataViewSave.appendChild(objHidden);        
          }
       }
       if(nFtDVTermsCount > 0)
       {
          var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_NUM%>");
          if( objFormEelement != null)
          {
            eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_NUM%>.value  ='"+nFtDVTermsCount+"';");
          }   
          else      
          {
            var objHidden= document.createElement("INPUT");
            objHidden.type ="hidden";
            objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_NUM%>";
            objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_NUM%>";
            objHidden.value = nFtDVTermsCount;
            document.formDataViewSave.appendChild(objHidden);        
          }
       }


///////////////////////////////////////////////////////////////////////////////////
//                          Where Clause
////////////////////////////////////////////////////////////////////////////////////

      for(var m=0;m<top.objNewDataView.m_arrDataViewWhereElements.length;m++)
      {
        if(top.objNewDataView.m_arrDataViewWhereElements[m] != null)
        {
            var objWhereElement = top.objNewDataView.m_arrDataViewWhereElements[m];
            var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE%>"+nWhereCount);
            if( objFormEelement != null)
            {
              eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE%>"+nWhereCount+".value  ='"+objWhereElement.m_nElementTypeID+"';");
            }   
            else      
            {
              var objHidden= document.createElement("INPUT");
              objHidden.type ="hidden";
              objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE%>"+nWhereCount;
              objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE%>"+nWhereCount;
              objHidden.value = objWhereElement.m_nElementTypeID;
              document.formDataViewSave.appendChild(objHidden);
            }

            if(objWhereElement.m_nElementTypeID == <%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE_TERM%>)
            {
                var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_ELEMENT_ID%>"+nWhereCount);
                if( objFormEelement != null)
                {
                  eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_ELEMENT_ID%>"+nWhereCount+".value  ='"+objWhereElement.m_nElementIndex+"';");
                }   
                else      
                {
                  var objHidden= document.createElement("INPUT");
                  objHidden.type ="hidden";
                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_ELEMENT_ID%>"+nWhereCount;
                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_ELEMENT_ID%>"+nWhereCount;
                  objHidden.value = objWhereElement.m_nElementIndex;
                  document.formDataViewSave.appendChild(objHidden);
                }
           }
           else
           {
               if(objWhereElement.m_nElementTypeID == <%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE_SYMBOL%>)
               {
                    var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_ELEMENT_ID%>"+nWhereCount);
                    if( objFormEelement != null)
                    {
                      eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_ELEMENT_ID%>"+nWhereCount+".value  ='"+objWhereElement.m_nRelationSymbolID+"';");
                    }   
                    else      
                    {
                      var objHidden= document.createElement("INPUT");
                      objHidden.type ="hidden";
                      objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_ELEMENT_ID%>"+nWhereCount;
                      objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_ELEMENT_ID%>"+nWhereCount;
                      objHidden.value = objWhereElement.m_nRelationSymbolID;
                      document.formDataViewSave.appendChild(objHidden);
                    }

                    var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_SYMBOL_SQL%>"+nWhereCount);
                    if( objFormEelement != null)
                    {
                      eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_SYMBOL_SQL%>"+nWhereCount+".value  ='"+objWhereElement.m_strRelationSymbolSQL+"';");
                    }   
                    else      
                    {
                      var objHidden= document.createElement("INPUT");
                      objHidden.type ="hidden";
                      objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_SYMBOL_SQL%>"+nWhereCount;
                      objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_SYMBOL_SQL%>"+nWhereCount;
                      objHidden.value = objWhereElement.m_strRelationSymbolSQL;
                      document.formDataViewSave.appendChild(objHidden);
                    }
               }   
           }// End Else for the type
           nWhereCount++;
       }// End If element == null

     }// End For 
     if(nWhereCount > 0)
     {
        var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERES_NUM%>");
        if( objFormEelement != null)
        {
          eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERES_NUM%>.value  ='"+nWhereCount+"';");
        }   
        else      
        {
          var objHidden= document.createElement("INPUT");
          objHidden.type ="hidden";
          objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERES_NUM%>";
          objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERES_NUM%>";
          objHidden.value = nWhereCount;
          document.formDataViewSave.appendChild(objHidden);        
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
          
          var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_TYPE%>"+nOrderByCount);
          if( objFormEelement != null)
          {
            eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_TYPE%>"+nOrderByCount+".value  ='"+objOrderBy.m_objOrderByField.m_nFieldType+"';");
          }   
          else      
          {
            var objHidden= document.createElement("INPUT");
            objHidden.type ="hidden";
            objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_TYPE%>"+nOrderByCount;
            objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_TYPE%>"+nOrderByCount;
            objHidden.value = objOrderBy.m_objOrderByField.m_nFieldType;
            document.formDataViewSave.appendChild(objHidden);
          }

          var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_ORDER_TYPE%>"+nOrderByCount);
          if( objFormEelement != null)
          {
            eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_ORDER_TYPE%>"+nOrderByCount+".value  ='"+objOrderBy.m_strOrderByType+"';");
          }   
          else      
          {
            var objHidden= document.createElement("INPUT");
            objHidden.type ="hidden";
            objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_ORDER_TYPE%>"+nOrderByCount;
            objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_ORDER_TYPE%>"+nOrderByCount;
            objHidden.value = objOrderBy.m_strOrderByType;
            document.formDataViewSave.appendChild(objHidden);
          }
          
          if(objOrderBy.m_nOrderByElementTypeID == <%=QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD%>)
          {
                var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_ID%>"+nOrderByCount);
                if( objFormEelement != null)
                {
                  eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_ID%>"+nOrderByCount+".value  ='"+objOrderBy.m_objOrderByField.m_nFieldID+"';");
                }   
                else      
                {
                  var objHidden= document.createElement("INPUT");
                  objHidden.type ="hidden";
                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_ID%>"+nOrderByCount;
                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_ID%>"+nOrderByCount;
                  objHidden.value = objOrderBy.m_objOrderByField.m_nFieldID;
                  document.formDataViewSave.appendChild(objHidden);
                }

                var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_NAME%>"+nOrderByCount);
                if( objFormEelement != null)
                {
                  eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_NAME%>"+nOrderByCount+".value  ='"+objOrderBy.m_objOrderByField.m_strFieldName+"';");
                }   
                else      
                {
                  var objHidden= document.createElement("INPUT");
                  objHidden.type ="hidden";
                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_NAME%>"+nOrderByCount;
                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_NAME%>"+nOrderByCount;
                  objHidden.value = objOrderBy.m_objOrderByField.m_strFieldName;
                  document.formDataViewSave.appendChild(objHidden);
                }

                var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_DATAVIEW_ID%>"+nOrderByCount);
                if( objFormEelement != null)
                {
                  eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_DATAVIEW_ID%>"+nOrderByCount+".value  ='"+objOrderBy.m_objOrderByField.m_nDataViewID+"';");
                }   
                else      
                {
                  var objHidden= document.createElement("INPUT");
                  objHidden.type ="hidden";
                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_DATAVIEW_ID%>"+nOrderByCount;
                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_DATAVIEW_ID%>"+nOrderByCount;
                  objHidden.value = objOrderBy.m_objOrderByField.m_nDataViewID;
                  document.formDataViewSave.appendChild(objHidden);
                }   

                var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_DATAVIEW_NAME%>"+nOrderByCount);
                if( objFormEelement != null)
                {
                  eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_DATAVIEW_NAME%>"+nOrderByCount+".value  ='"+objOrderBy.m_objOrderByField.m_strDataViewName+"';");
                }   
                else      
                {
                  var objHidden= document.createElement("INPUT");
                  objHidden.type ="hidden";
                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_DATAVIEW_NAME%>"+nOrderByCount;
                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_DATAVIEW_NAME%>"+nOrderByCount;
                  objHidden.value = objOrderBy.m_objOrderByField.m_strDataViewName;
                  document.formDataViewSave.appendChild(objHidden);
                }

                var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_DATAVIEW_TYPE%>"+nOrderByCount);
                if( objFormEelement != null)
                {
                  eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_DATAVIEW_TYPE%>"+nOrderByCount+".value  ='"+objOrderBy.m_objOrderByField.m_nDataViewType+"';");
                }   
                else      
                {
                  var objHidden= document.createElement("INPUT");
                  objHidden.type ="hidden";
                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_DATAVIEW_TYPE%>"+nOrderByCount;
                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_DATAVIEW_TYPE%>"+nOrderByCount;
                  objHidden.value = objOrderBy.m_objOrderByField.m_nDataViewType;
                  document.formDataViewSave.appendChild(objHidden);
                }

                var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_DESCRIPTION%>"+nOrderByCount);
                if( objFormEelement != null)
                {
                  eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_DESCRIPTION%>"+nOrderByCount+".value  ='"+objOrderBy.m_objOrderByField.m_strFieldDescription+"';");
                }   
                else      
                {
                  var objHidden= document.createElement("INPUT");
                  objHidden.type ="hidden";
                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_DESCRIPTION%>"+nOrderByCount;
                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_DESCRIPTION%>"+nOrderByCount;
                  objHidden.value = objOrderBy.m_objOrderByField.m_strFieldDescription;
                  document.formDataViewSave.appendChild(objHidden);
                } 
                
            }
            else
            {
                var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_ID%>"+nOrderByCount);
                if( objFormEelement != null)
                {
                  eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_ID%>"+nOrderByCount+".value  ='"+objOrderBy.m_nOrderByIndex+"';");
                }   
                else      
                {
                  var objHidden= document.createElement("INPUT");
                  objHidden.type ="hidden";
                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_ID%>"+nOrderByCount;
                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_ID%>"+nOrderByCount;
                  objHidden.value = objOrderBy.m_nOrderByIndex;
                  document.formDataViewSave.appendChild(objHidden);
                }
            }
          }
          nOrderByCount++;
        }
        if(nOrderByCount > 0)
        {
        var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELDS_NUM%>");
        if( objFormEelement != null)
        {
          eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELDS_NUM%>.value  ='"+nOrderByCount+"';");
        }   
        else      
        {
          var objHidden= document.createElement("INPUT");
          objHidden.type ="hidden";
          objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELDS_NUM%>";
          objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELDS_NUM%>";
          objHidden.value = nOrderByCount;
          document.formDataViewSave.appendChild(objHidden);        
        }
     }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//                                                       GROUP BY CLAUSE
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

      for(var l=0;l<top.objNewDataView.m_arrDataViewGroupBy.length;l++)
      {
        if(top.objNewDataView.m_arrDataViewGroupBy[l] != null)
        {
          var objGroupBy = top.objNewDataView.m_arrDataViewGroupBy[l];
          
          var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_TYPE%>"+nGroupByCount);
          if( objFormEelement != null)
          {
            eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_TYPE%>"+nGroupByCount+".value  ='"+objGroupBy.m_objGroupByField.m_nFieldType+"';");
          }   
          else      
          {
            var objHidden= document.createElement("INPUT");
            objHidden.type ="hidden";
            objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_TYPE%>"+nGroupByCount;
            objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_TYPE%>"+nGroupByCount;
            objHidden.value = objGroupBy.m_objGroupByField.m_nFieldType;
            document.formDataViewSave.appendChild(objHidden);
          }

          if(objGroupBy.m_nGroupByElementTypeID == <%=QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD%>)
          {
                var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_ID%>"+nGroupByCount);
                if( objFormEelement != null)
                {
                  eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_ID%>"+nGroupByCount+".value  ='"+objGroupBy.m_objGroupByField.m_nFieldID+"';");
                }   
                else      
                {
                  var objHidden= document.createElement("INPUT");
                  objHidden.type ="hidden";
                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_ID%>"+nGroupByCount;
                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_ID%>"+nGroupByCount;
                  objHidden.value = objGroupBy.m_objGroupByField.m_nFieldID;
                  document.formDataViewSave.appendChild(objHidden);
                }

                var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_NAME%>"+nGroupByCount);
                if( objFormEelement != null)
                {
                  eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_NAME%>"+nGroupByCount+".value  ='"+objGroupBy.m_objGroupByField.m_strFieldName+"';");
                }   
                else      
                {
                  var objHidden= document.createElement("INPUT");
                  objHidden.type ="hidden";
                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_NAME%>"+nGroupByCount;
                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_NAME%>"+nGroupByCount;
                  objHidden.value = objGroupBy.m_objGroupByField.m_strFieldName;
                  document.formDataViewSave.appendChild(objHidden);
                }

                var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_DATAVIEW_ID%>"+nGroupByCount);
                if( objFormEelement != null)
                {
                  eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_DATAVIEW_ID%>"+nGroupByCount+".value  ='"+objGroupBy.m_objGroupByField.m_nDataViewID+"';");
                }   
                else      
                {
                  var objHidden= document.createElement("INPUT");
                  objHidden.type ="hidden";
                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_DATAVIEW_ID%>"+nGroupByCount;
                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_DATAVIEW_ID%>"+nGroupByCount;
                  objHidden.value = objGroupBy.m_objGroupByField.m_nDataViewID;
                  document.formDataViewSave.appendChild(objHidden);
                }   

                var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_DATAVIEW_NAME%>"+nGroupByCount);
                if( objFormEelement != null)
                {
                  eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_DATAVIEW_NAME%>"+nGroupByCount+".value  ='"+objGroupBy.m_objGroupByField.m_strDataViewName+"';");
                }   
                else      
                {
                  var objHidden= document.createElement("INPUT");
                  objHidden.type ="hidden";
                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_DATAVIEW_NAME%>"+nGroupByCount;
                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_DATAVIEW_NAME%>"+nGroupByCount;
                  objHidden.value = objGroupBy.m_objGroupByField.m_strDataViewName;
                  document.formDataViewSave.appendChild(objHidden);
                }

                var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_DATAVIEW_TYPE%>"+nGroupByCount);
                if( objFormEelement != null)
                {
                  eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_DATAVIEW_TYPE%>"+nGroupByCount+".value  ='"+objGroupBy.m_objGroupByField.m_nDataViewType+"';");
                }   
                else      
                {
                  var objHidden= document.createElement("INPUT");
                  objHidden.type ="hidden";
                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_DATAVIEW_TYPE%>"+nGroupByCount;
                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_DATAVIEW_TYPE%>"+nGroupByCount;
                  objHidden.value = objGroupBy.m_objGroupByField.m_nDataViewType;
                  document.formDataViewSave.appendChild(objHidden);
                }

                var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_DESCRIPTION%>"+nGroupByCount);
                if( objFormEelement != null)
                {
                  eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_DESCRIPTION%>"+nGroupByCount+".value  ='"+objGroupBy.m_objGroupByField.m_strFieldDescription+"';");
                }   
                else      
                {
                  var objHidden= document.createElement("INPUT");
                  objHidden.type ="hidden";
                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_DESCRIPTION%>"+nGroupByCount;
                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_DESCRIPTION%>"+nGroupByCount;
                  objHidden.value = objGroupBy.m_objGroupByField.m_strFieldDescription;
                  document.formDataViewSave.appendChild(objHidden);
                } 
                
            }
            else
            {
                var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_ID%>"+nGroupByCount);
                if( objFormEelement != null)
                {
                  eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_ID%>"+nGroupByCount+".value  ='"+objGroupBy.m_nGroupByIndex+"';");
                }   
                else      
                {
                  var objHidden= document.createElement("INPUT");
                  objHidden.type ="hidden";
                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_ID%>"+nGroupByCount;
                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_ID%>"+nGroupByCount;
                  objHidden.value = objGroupBy.m_nGroupByIndex;
                  document.formDataViewSave.appendChild(objHidden);
                }
            }
          }
          nGroupByCount++;
        }
        if(nGroupByCount > 0)
        {
        var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELDS_NUM%>");
        if( objFormEelement != null)
        {
          eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELDS_NUM%>.value  ='"+nGroupByCount+"';");
        }   
        else      
        {
          var objHidden= document.createElement("INPUT");
          objHidden.type ="hidden";
          objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELDS_NUM%>";
          objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELDS_NUM%>";
          objHidden.value = nGroupByCount;
          document.formDataViewSave.appendChild(objHidden);        
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
            var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_HAVING_TYPE%>"+nHavingCount);
            if( objFormEelement != null)
            {
              eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_HAVING_TYPE%>"+nHavingCount+".value  ='"+objHavingElement.m_nElementTypeID+"';");
            }   
            else      
            {
              var objHidden= document.createElement("INPUT");
              objHidden.type ="hidden";
              objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_HAVING_TYPE%>"+nHavingCount;
              objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_HAVING_TYPE%>"+nHavingCount;
              objHidden.value = objHavingElement.m_nElementTypeID;
              document.formDataViewSave.appendChild(objHidden);
            }

            if(objHavingElement.m_nElementTypeID == <%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE_TERM%>)
            {
                var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_HAVING_ELEMENT_ID%>"+nHavingCount);
                if( objFormEelement != null)
                {
                  eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_HAVING_ELEMENT_ID%>"+nHavingCount+".value  ='"+objHavingElement.m_nElementIndex+"';");
                }   
                else      
                {
                  var objHidden= document.createElement("INPUT");
                  objHidden.type ="hidden";
                  objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_HAVING_ELEMENT_ID%>"+nHavingCount;
                  objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_HAVING_ELEMENT_ID%>"+nHavingCount;
                  objHidden.value = objHavingElement.m_nElementIndex;
                  document.formDataViewSave.appendChild(objHidden);
                }
           }
           else
           {
               if(objHavingElement.m_nElementTypeID == <%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE_SYMBOL%>)
               {
                    var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_HAVING_ELEMENT_ID%>"+nHavingCount);
                    if( objFormEelement != null)
                    {
                      eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_HAVING_ELEMENT_ID%>"+nHavingCount+".value  ='"+objHavingElement.m_nRelationSymbolID+"';");
                    }   
                    else      
                    {
                      var objHidden= document.createElement("INPUT");
                      objHidden.type ="hidden";
                      objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_HAVING_ELEMENT_ID%>"+nHavingCount;
                      objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_HAVING_ELEMENT_ID%>"+nHavingCount;
                      objHidden.value = objHavingElement.m_nRelationSymbolID;
                      document.formDataViewSave.appendChild(objHidden);
                    }

                    var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_HAVING_SYMBOL_SQL%>"+nHavingCount);
                    if( objFormEelement != null)
                    {
                      eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_HAVING_SYMBOL_SQL%>"+nHavingCount+".value  ='"+objHavingElement.m_strRelationSymbolSQL+"';");
                    }   
                    else      
                    {
                      var objHidden= document.createElement("INPUT");
                      objHidden.type ="hidden";
                      objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_HAVING_SYMBOL_SQL%>"+nHavingCount;
                      objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_HAVING_SYMBOL_SQL%>"+nHavingCount;
                      objHidden.value = objHavingElement.m_strRelationSymbolSQL;
                      document.formDataViewSave.appendChild(objHidden);
                    }
               }   
           }// End Else for the type
           nHavingCount++;
       }// End If element == null

     }// End For 
     if(nHavingCount > 0)
     {
        var objFormEelement = document.formDataViewSave.all.item("<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_HAVINGS_NUM%>");
        if( objFormEelement != null)
        {
          eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_HAVINGS_NUM%>.value  ='"+nHavingCount+"';");
        }   
        else      
        {
          var objHidden= document.createElement("INPUT");
          objHidden.type ="hidden";
          objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_HAVINGS_NUM%>";
          objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_HAVINGS_NUM%>";
          objHidden.value = nHavingCount;
          document.formDataViewSave.appendChild(objHidden);        
        }
     }









     if(argAction == "<%=QueryBuilderInterfaceKey.ACTION_PREVIEW_CHECK_QUERY%>")
     {
         if(!document.formDataViewSave.chkNew.checked)
         {  
              argAction = "<%=QueryBuilderInterfaceKey.ACTION_SAVE_QUERY%>";
              document.formDataViewSave.target="_top"
         }
         else
         {
              document.formDataViewSave.target="Right";
         }
     }
     else    
     if(argAction == "<%=QueryBuilderInterfaceKey.ACTION_SAVE_QUERY%>")
         document.formDataViewSave.target="_top";

     var objHidden= document.createElement("INPUT");
     objHidden.type ="hidden";
     objHidden.id= "<%=InterfaceKey.HASHMAP_KEY_ACTION%>";
     objHidden.name= "<%=InterfaceKey.HASHMAP_KEY_ACTION%>";
     objHidden.value = argAction;
     document.formDataViewSave.appendChild(objHidden);        

     document.formDataViewSave.submit();
  }
  catch(e)
  {
    alert(e.description);
    if(top.objNewDataView == null )
    {
        alert("You have to select at lease one data view..");
    }
    else
    if(top.objNewDataView.m_strDataViewName == null || top.objNewDataView.m_strDataViewName == "")
    {
        alert("The data view must have a name..");
    }
    else
    if(top.objNewDataView.m_strDataViewDescription == null || top.objNewDataView.m_strDataViewDescription == "")
    {
        alert("The data view must have a description..");
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
        alert("top.objNewDataView.m_arrDataViewFields.length"+top.objNewDataView.m_arrDataViewFields.length);
        alert("top.objNewDataView.m_KPIRulesViolated"+top.objNewDataView.m_KPIRulesViolated);
        alert("top.objNewDataView.m_EligibleRulesViolated"+top.objNewDataView.m_EligibleRulesViolated);
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
      //top.objNewDataView = new NewDataView();
      window.open("../gn/ua/Role_Privileges_Main.jsp","_parent");
    }
  }
}

</script>   
<HTML>
   <HEAD>
      <META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=windows-1256"/>
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/leftmenu.css">
      <SCRIPT language=JavaScript src="../resources/js/utilities.js" type=text/javascript></SCRIPT>
      <SCRIPT language=JavaScript src="../resources/js/validation.js" type=text/javascript></SCRIPT>
      <SCRIPT language=JavaScript src="../resources/js/Template1.js" type=text/javascript></SCRIPT>
      <SCRIPT language=JavaScript src="../resources/js/DataViews.js" type=text/javascript></SCRIPT>
      <TITLE>Data View Preview & Save</TITLE>

   </HEAD>

   <div style="display:none" id=<%out.print(InterfaceKey.HIDDEN_DIV_FOR_PAGE_LOADING);%>>
   
   <BODY leftmargin=0 topmargin=0>
   <form id="formDataViewSave" name="formDataViewSave" action="../servlet/com.mobinil.sds.web.controller.WebControllerServlet" method="post">
       <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_ACTION%>" id="<%=InterfaceKey.HASHMAP_KEY_ACTION%>" >    
       <input type="hidden" name="<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_SAVE%>" id="<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_SAVE%>" >    
       

<%!     
/**
 * Draw the dataview preview 
 * @param argRequest HttpServletRequest,argOut JspWriter 
 * @throws Exception if the IO operation failed
 * @return 
 * @see 
 */
 
 private void drawDataViewResults(HttpServletRequest argRequest,JspWriter argOut) throws IOException
 {
    StringBuffer strDataViewPreviewHTML;
   
    HashMap hmData                             = new HashMap(100);
    QueryViewerDTO dtoQueryViewer              = null;
    DataRowDTO dtoDataRow                      = null;
    Vector colRows                             = null;
    Vector colCells                            = null;
    Vector colHeaders                          = null;
    String strHeaderName                       = null;
    String strCellValue                        = null;
    String strErrorEncountered                 = null;
    String strSQL                              = null;
    try
    {    
        hmData = (HashMap)argRequest.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT); 
        strErrorEncountered = (String)hmData.get(InterfaceKey.HASHMAP_KEY_MESSAGE); 
        dtoQueryViewer = (QueryViewerDTO)hmData.get(InterfaceKey.HASHMAP_KEY_DTO_OBJECT); 
        strSQL =  dtoQueryViewer.getSQLCode();

//        strErrorEncountered = dtoQueryViewer.getErrorMessage();
        if(strErrorEncountered != "null" && strErrorEncountered != "" && strErrorEncountered != null && !strErrorEncountered.equalsIgnoreCase("Valid"))
        {
            if(strErrorEncountered == null)
                strErrorEncountered = " Empty value found while was expecting a full one..";
            strDataViewPreviewHTML = new StringBuffer("<TR><TD colspan=2>SQL: "+ strSQL +"</TD></TR><TR><TD colspan=2><font size=2 color=red><b><u> The following error has encountered while trying to preview your data view:</b></u></font><br>");
            strDataViewPreviewHTML.append(" <font size=2 color=black><b> - Error Message:" + strErrorEncountered +"</b></font><br><br>");
            strDataViewPreviewHTML.append(" <font size=2 color=black><b>Please fix the problem then try again.</b></font></TD></TR>");
            if(dtoQueryViewer.getHeaderInterfaceFields() != null)
            {
                colHeaders = dtoQueryViewer.getHeaderInterfaceFields();
                  colRows = dtoQueryViewer.getRows();
    
                  strDataViewPreviewHTML.append("<TR><TD colspan=2 class=TableHeader>SQL: "+ strSQL + "</TD></TR><TR><TD colspan=2><Table style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>");
                  strDataViewPreviewHTML.append("<TR class=TableHeader>");
    
                  for (int i = 0; i < colHeaders.size(); i++) 
                  {
                      strHeaderName = (String)colHeaders.elementAt(i);
                      strDataViewPreviewHTML.append("<TD>"+strHeaderName+"</TD>");
                  }
                  strDataViewPreviewHTML.append("</TR>");    

                  for (int j = 0; j < colRows.size(); j++) 
                  {
                      strDataViewPreviewHTML.append("<TR class=TableTextNote>");
                      dtoDataRow = (DataRowDTO)colRows.elementAt(j);
                      colCells = dtoDataRow.getValues();
                      for (int k = 0; k < colCells.size(); k++) 
                      {
                          strCellValue = (String)colCells.elementAt(k);  
                          strDataViewPreviewHTML.append("<TD class=TableTextNote>"+strCellValue+"</TD>");
                      }
                      strDataViewPreviewHTML.append("</TR>");
                   }
                   strDataViewPreviewHTML.append("</table></TD></TR>");
             
                   strDataViewPreviewHTML.append("<tr class=TableHeader>");
                   strDataViewPreviewHTML.append("<td class=TableHeader>Data View Name</td>");
                   strDataViewPreviewHTML.append("<td class=TableHeader>");
                   strDataViewPreviewHTML.append("<input name="+QueryBuilderInterfaceKey.PARAM_INITIALIZE_QUERY_BUILDER_QUERY_NAME+" onkeypress=\"checkQuotes()\" id="+QueryBuilderInterfaceKey.PARAM_INITIALIZE_QUERY_BUILDER_QUERY_NAME+" style=\"WIDTH: 350px; HEIGHT: 22px\" size=50>");
                   strDataViewPreviewHTML.append(" <label> Version: </label><input name=\"textVersion\" id=\"textVersion\" style=\"WIDTH: 50px; HEIGHT: 22px;\" readonly>");
                   strDataViewPreviewHTML.append("<INPUT id=chkNew type=checkbox onClick=\"adjustMode()\"  name=chkNew>&nbsp;Save As New Dataview");
                   strDataViewPreviewHTML.append(" </td></tr>");
                   strDataViewPreviewHTML.append("<tr class=TableHeader><td class=TableHeader valign = top>Data View Description</td>");
                   strDataViewPreviewHTML.append("<td class=TableHeader><TEXTAREA onkeypress=\"checkQuotes()\" style=\"WIDTH: 409px; HEIGHT: 86px\" name="+QueryBuilderInterfaceKey.PARAM_INITIALIZE_QUERY_BUILDER_QUERY_DESC+" id="+QueryBuilderInterfaceKey.PARAM_INITIALIZE_QUERY_BUILDER_QUERY_DESC+" onkeypress=\"limitSize()\" onbeforepaste=\"limitClipboard()\" rows=5 cols=43></TEXTAREA></td>");
                   strDataViewPreviewHTML.append("</tr>");
                   strDataViewPreviewHTML.append("<tr class=TableTextNote>");
                   strDataViewPreviewHTML.append("<td class=TableHeader colspan=2 align=center><center>");
                   strDataViewPreviewHTML.append("<input type=\"button\" id=\"Save\" name=\"Save\" value=\"Save\" class=\"button\" onclick=\"return butSave_onClick('"+QueryBuilderInterfaceKey.ACTION_PREVIEW_CHECK_QUERY+"')\">&nbsp; ");
                   strDataViewPreviewHTML.append("<input type=button class=button name=butBackToWizard id=butBackToWizard value=\"Back To Wizard\" onclick=\"window.open('../gn/GN_ADHOC_DataViewWizard.jsp','Right')\"></center></td></tr></table>");
                   strDataViewPreviewHTML.append("<script language=javascript>");
                   strDataViewPreviewHTML.append("if(top.objNewDataView.m_nSaveMode =="+QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_SAVE_FALSE+")");
                   
                   strDataViewPreviewHTML.append("{ eval(\"document.formDataViewSave.chkNew.checked = true;\");");
                   strDataViewPreviewHTML.append("eval(\"document.formDataViewSave.chkNew.disabled = true;\");");
                   strDataViewPreviewHTML.append("eval(\"document.formDataViewSave."+QueryBuilderInterfaceKey.PARAM_INITIALIZE_QUERY_BUILDER_QUERY_NAME+".value = top.objNewDataView.m_strDataViewName\");");    
                   strDataViewPreviewHTML.append("eval(\"document.formDataViewSave."+QueryBuilderInterfaceKey.PARAM_INITIALIZE_QUERY_BUILDER_QUERY_DESC+".value = top.objNewDataView.m_strDataViewDescription\");");
                   strDataViewPreviewHTML.append("eval(\"document.formDataViewSave.textVersion.value = top.objNewDataView.m_nDataViewVersion\");");
                   strDataViewPreviewHTML.append("} else {");
                   
                   strDataViewPreviewHTML.append(" eval(\"document.formDataViewSave."+QueryBuilderInterfaceKey.PARAM_INITIALIZE_QUERY_BUILDER_QUERY_NAME+".value = top.objNewDataView.m_strDataViewName\");");    
                   strDataViewPreviewHTML.append("eval(\"document.formDataViewSave."+QueryBuilderInterfaceKey.PARAM_INITIALIZE_QUERY_BUILDER_QUERY_DESC+".value = top.objNewDataView.m_strDataViewDescription\");");
                   strDataViewPreviewHTML.append("eval(\"document.formDataViewSave.textVersion.value = top.objNewDataView.m_nDataViewVersion\");");
                   strDataViewPreviewHTML.append("eval(\"document.formDataViewSave."+QueryBuilderInterfaceKey.PARAM_INITIALIZE_QUERY_BUILDER_QUERY_NAME+".readOnly = true\");");
                   strDataViewPreviewHTML.append("eval(\"document.formDataViewSave."+QueryBuilderInterfaceKey.PARAM_INITIALIZE_QUERY_BUILDER_QUERY_DESC+".readOnly = true\");}");
                   strDataViewPreviewHTML.append("</script>");
           }
           else
           {
              strDataViewPreviewHTML.append("<Tr><TD colspan=2 class=TableTextNote><Center><input type=button class=button name=butBackToWizard id=butBackToWizard value=\"Back To Wizard\" onclick=\"window.open('../gn/GN_ADHOC_DataViewWizard.jsp','Right')\"></Center></Td></tr></Table>");      
           }
        }
        else
        {
            hmData = (HashMap)argRequest.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT); 
            dtoQueryViewer = (QueryViewerDTO)hmData.get(InterfaceKey.HASHMAP_KEY_DTO_OBJECT); 

            colHeaders = dtoQueryViewer.getHeaderInterfaceFields();
              colRows = dtoQueryViewer.getRows();
    
              strDataViewPreviewHTML = new StringBuffer("<TR><TD colspan=2 class=TableHeader>SQL: "+ strSQL + "</TD></TR><TR><TD colspan=2><Table style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>");
              strDataViewPreviewHTML.append("<TR class=TableHeader>");
    
              for (int i = 0; i < colHeaders.size(); i++) 
              {
                  strHeaderName = (String)colHeaders.elementAt(i);
                  strDataViewPreviewHTML.append("<TD>"+strHeaderName+"</TD>");
              }
              strDataViewPreviewHTML.append("</TR>");    

              for (int j = 0; j < colRows.size(); j++) 
              {
                  strDataViewPreviewHTML.append("<TR class=TableTextNote>");
                  dtoDataRow = (DataRowDTO)colRows.elementAt(j);
                  colCells = dtoDataRow.getValues();
                  for (int k = 0; k < colCells.size(); k++) 
                  {
                      strCellValue = (String)colCells.elementAt(k);  
                      strDataViewPreviewHTML.append("<TD class=TableTextNote>"+strCellValue+"</TD>");
                  }
                  strDataViewPreviewHTML.append("</TR>");
               }
               strDataViewPreviewHTML.append("</table></TD></TR>");
             
               strDataViewPreviewHTML.append("<tr class=TableHeader>");
               strDataViewPreviewHTML.append("<td class=TableHeader>Data View Name</td>");
               strDataViewPreviewHTML.append("<td class=TableHeader>");
               strDataViewPreviewHTML.append("<input name="+QueryBuilderInterfaceKey.PARAM_INITIALIZE_QUERY_BUILDER_QUERY_NAME+" onkeypress=\"checkQuotes()\" id="+QueryBuilderInterfaceKey.PARAM_INITIALIZE_QUERY_BUILDER_QUERY_NAME+" style=\"WIDTH: 350px; HEIGHT: 22px\" size=50>");
               strDataViewPreviewHTML.append(" <label> Version: </label><input name=\"textVersion\" id=\"textVersion\" style=\"WIDTH: 50px; HEIGHT: 22px;\" readonly>");
               strDataViewPreviewHTML.append("<INPUT id=chkNew type=checkbox onClick=\"adjustMode()\"  name=chkNew>&nbsp;Save As New Dataview");
               strDataViewPreviewHTML.append(" </td></tr>");
               strDataViewPreviewHTML.append("<tr class=TableHeader><td class=TableHeader valign = top>Data View Description</td>");
               strDataViewPreviewHTML.append("<td class=TableHeader><TEXTAREA onkeypress=\"checkQuotes()\" style=\"WIDTH: 409px; HEIGHT: 86px\" name="+QueryBuilderInterfaceKey.PARAM_INITIALIZE_QUERY_BUILDER_QUERY_DESC+" id="+QueryBuilderInterfaceKey.PARAM_INITIALIZE_QUERY_BUILDER_QUERY_DESC+" onkeypress=\"limitSize()\" onbeforepaste=\"limitClipboard()\" rows=5 cols=43></TEXTAREA></td>");
               strDataViewPreviewHTML.append(" </tr>");
               strDataViewPreviewHTML.append(" <tr class=TableTextNote>");
               strDataViewPreviewHTML.append(" <td class=TableHeader colspan=2 align=center><center>");
               strDataViewPreviewHTML.append(" <input type=\"button\" id=\"Save\" name=\"Save\" value=\"Save\" class=\"button\" onclick=\"return butSave_onClick('"+QueryBuilderInterfaceKey.ACTION_PREVIEW_CHECK_QUERY+"')\">&nbsp; ");
               strDataViewPreviewHTML.append(" <input type=button class=button name=butBackToWizard id=butBackToWizard value=\"Back To Wizard\" onclick=\"window.open('../gn/GN_ADHOC_DataViewWizard.jsp','Right')\"></center></td></tr></table>");
               strDataViewPreviewHTML.append(" <script language=javascript>");
               strDataViewPreviewHTML.append(" if(top.objNewDataView.m_nSaveMode =="+QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_SAVE_FALSE+")");
               strDataViewPreviewHTML.append(" { eval(\"document.formDataViewSave.chkNew.checked = true;\");");
               strDataViewPreviewHTML.append(" eval(\"document.formDataViewSave.chkNew.disabled = true;\");");
               strDataViewPreviewHTML.append(" eval(\"document.formDataViewSave."+QueryBuilderInterfaceKey.PARAM_INITIALIZE_QUERY_BUILDER_QUERY_NAME+".value = top.objNewDataView.m_strDataViewName\");");    
               strDataViewPreviewHTML.append(" eval(\"document.formDataViewSave."+QueryBuilderInterfaceKey.PARAM_INITIALIZE_QUERY_BUILDER_QUERY_DESC+".value = top.objNewDataView.m_strDataViewDescription\");");
               strDataViewPreviewHTML.append("eval(\"document.formDataViewSave.textVersion.value = top.objNewDataView.m_nDataViewVersion\");");
               
               strDataViewPreviewHTML.append("} else {");
               strDataViewPreviewHTML.append(" eval(\"document.formDataViewSave."+QueryBuilderInterfaceKey.PARAM_INITIALIZE_QUERY_BUILDER_QUERY_NAME+".value = top.objNewDataView.m_strDataViewName\");");    
               strDataViewPreviewHTML.append(" eval(\"document.formDataViewSave."+QueryBuilderInterfaceKey.PARAM_INITIALIZE_QUERY_BUILDER_QUERY_DESC+".value = top.objNewDataView.m_strDataViewDescription\");");
               strDataViewPreviewHTML.append("eval(\"document.formDataViewSave.textVersion.value = top.objNewDataView.m_nDataViewVersion\");");
               strDataViewPreviewHTML.append(" eval(\"document.formDataViewSave."+QueryBuilderInterfaceKey.PARAM_INITIALIZE_QUERY_BUILDER_QUERY_NAME+".readOnly = true\");");
               strDataViewPreviewHTML.append(" eval(\"document.formDataViewSave."+QueryBuilderInterfaceKey.PARAM_INITIALIZE_QUERY_BUILDER_QUERY_DESC+".readOnly = true\");}");
               strDataViewPreviewHTML.append("</script>");
        }
        argOut.println(strDataViewPreviewHTML.toString());
     }
     catch(Exception objExp)
     {
      
     }
 }
%>


  <!-- <CENTER>
      <H2>Data View Preview & Save</H2>
    </CENTER>-->
    <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
            <tr class=TableHeader>
            <td colspan=2 class=TableHeader>&nbsp;</td>
           </tr>
           <tr class=TableHeader>
            <td colspan=2 class=TableHeader><font color=darkblue size=3><Center>Data View Preview & Save</Center></td>
           </tr>
          <tr class=TableHeader>
            <td colspan=2 class=TableHeader>&nbsp;</td>
           </tr>

    <% drawDataViewResults(request,out);%>

    </form>

<script>
function validateFields()
{
  var str1stFieldName = "";
  var str2ndFieldName = "";
  var bValid = true;
  for (var i = 0; i < top.objNewDataView.m_arrDataViewFields.length-1; i++) 
  {
      if(!bValid)
        break;
      str1stFieldName = top.objNewDataView.m_arrDataViewFields[i].m_strFieldAlias;
      if(str1stFieldName == "" || str1stFieldName == "null" || str1stFieldName == null)
          str1stFieldName = top.objNewDataView.m_arrDataViewFields[i].m_strFieldName;
      for (var j = i+1; j < top.objNewDataView.m_arrDataViewFields.length; j++) 
      {
        str2ndFieldName = top.objNewDataView.m_arrDataViewFields[j].m_strFieldAlias;
        if(str2ndFieldName == "" || str2ndFieldName == "null" || str2ndFieldName == null)
            str2ndFieldName = top.objNewDataView.m_arrDataViewFields[j].m_strFieldName;
        if( str1stFieldName.toUpperCase() == str2ndFieldName.toUpperCase())  
        {
            bValid = false;
            break;
        }   
      }  
  }
  return bValid;
}


function validateRules(argUniverseName)
{
  var strFieldName = "";
  var strRuleFieldName = "";
  var bValid = false;
  if(argUniverseName == '<%=QueryBuilderInterfaceKey.UNIVERSE_TYPE_KPI%>')
  {
      for (var i = 0; i < top.objNewDataView.m_arrDataViewFields.length; i++) 
      {
        strFieldName = top.objNewDataView.m_arrDataViewFields[i].m_strFieldAlias;
        if(strFieldName == "" || strFieldName == "null" || strFieldName == null)
          strFieldName = top.objNewDataView.m_arrDataViewFields[i].m_strFieldName;
          strRuleFieldName = '<%=CommissionInterfaceKey.PARAM_KPI_CALCULATE_FIELD_EXCLUDED%>';
          if( strFieldName.toUpperCase() == strRuleFieldName.toUpperCase())  
          {
             bValid = true;
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
      for (var i = 0; i < top.objNewDataView.m_arrDataViewFields.length; i++) 
      {
        strFieldName = top.objNewDataView.m_arrDataViewFields[i].m_strFieldAlias;
        if(strFieldName == "" || strFieldName == "null" || strFieldName == null)
          strFieldName = top.objNewDataView.m_arrDataViewFields[i].m_strFieldName;
          strRuleFieldName = '<%=CommissionInterfaceKey.PARAM_KPI_CALCULATE_FIELD_EXCLUDED%>';
          if( strFieldName.toUpperCase() == strRuleFieldName.toUpperCase())  
          {
             bValid = true;
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
  return bValid;
}
function validateValue(argType,argValue)
{
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
    objDate = new Date(arrDate[1]+"/"+arrDate[0]+"/"+arrDate[2]);

    
    //if(objDate.getDate() == Number(arrDate[0]) && Number(objDate.getMonth()+1) == Number(arrDate[1]) && objDate.getFullYear()== Number(arrDate[2]))
      bValid = true;
    //else  
    //  bValid = false;
  }    
  else
  if(argType == "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_CONST_TYPE_NUMERIC%>")
  {
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
    if(argValue.length > 8000)
      bValid = false;
    else
      bValid = true;
  }
  return bValid;
}


function limitSize()
{
  var strDesc = eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_INITIALIZE_QUERY_BUILDER_QUERY_DESC%>.value");
  var nKeyCode = event.keyCode;
  if(strDesc.length >= 250)
  {
    event.keyCode = 0;
    return false;
  }
}
function limitClipboard()
{
  var strPastedText = new String();
  if(window.clipboardData.getData("Text") != null)
  {
    strPastedText = window.clipboardData.getData("Text");
  }  
  var strDesc = eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_INITIALIZE_QUERY_BUILDER_QUERY_DESC%>.value");
  var nCount= 0;
  if(Number(strDesc.length) < 250)
  {
    while(Number(strDesc.length) < 250)
    {
      strDesc += strPastedText[nCount];
      nCount++;
    }
  }
  else
    window.clipboardData.clearData("Text");
}

function TrimInput(strText)
{
    //this will get rid of leading spaces
    while (strText.substring(0,1) == ' ')
    {
        strText = strText.substring(1, strText.length);
    }
 
    //this will get rid of trailing spaces
    while (strText.substring(strText.length-1,strText.length) == ' ')
        strText = strText.substring(0, strText.length-1);
    return strText;
}
                      function checkQuotes()
                      {
                        var nKeyCode = event.keyCode;
                        if( Number(nKeyCode)== 34 )
                        {
                            alert("You are not allowed to use the (\") character");
                            event.keyCode =0;
                            return false;
                        }    
                        if( Number(nKeyCode)== 39 )
                        {
                           alert("You are not allowed to use the (\') character");
                           event.keyCode = 0;
                           return false;
                        }   
                          if( Number(nKeyCode)== 13 )
                        {
                           
                           event.keyCode = 0;
                           return false;
                        }   
                        return true;
                      }



function adjustMode()
{
    if(eval("document.formDataViewSave.chkNew.checked"))
    {
      eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_SAVE%>.value = <%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_SAVE_FALSE%>");
      eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_INITIALIZE_QUERY_BUILDER_QUERY_NAME%>.value = ''");
      eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_INITIALIZE_QUERY_BUILDER_QUERY_NAME%>.readOnly = false");
      eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_INITIALIZE_QUERY_BUILDER_QUERY_DESC%>.value = ''");      
      eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_INITIALIZE_QUERY_BUILDER_QUERY_DESC%>.readOnly = false");      
      top.objNewDataView.m_nSaveMode = <%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_SAVE_FALSE%>;
    }
    else
    {
       eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_SAVE%>.value = <%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_SAVE_TRUE%>");
       eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_INITIALIZE_QUERY_BUILDER_QUERY_NAME%>.value = top.objNewDataView.m_strDataViewName");
       eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_INITIALIZE_QUERY_BUILDER_QUERY_NAME%>.readOnly = true");
       eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_INITIALIZE_QUERY_BUILDER_QUERY_DESC%>.value = top.objNewDataView.m_strDataViewDescription");
       eval("document.formDataViewSave.<%=QueryBuilderInterfaceKey.PARAM_INITIALIZE_QUERY_BUILDER_QUERY_DESC%>.readOnly = true");
       top.objNewDataView.m_nSaveMode = <%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_SAVE_TRUE%>;
    }
}

<%
        HashMap hmData = new HashMap();
        hmData = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT); 
        String strErrorEncountered = (String)hmData.get(InterfaceKey.HASHMAP_KEY_MESSAGE);
        if(strErrorEncountered != null){
        if(strErrorEncountered.equalsIgnoreCase("Valid"))
        {%>
             butSave_onClick('<%=QueryBuilderInterfaceKey.ACTION_SAVE_QUERY%>');
       <%}
}%>



</script>

</BODY>
</div>
<script><%out.print(InterfaceKey.HIDDEN_DIV_FOR_PAGE_LOADING);%>.style.display = "block"</script>

</HTML>
