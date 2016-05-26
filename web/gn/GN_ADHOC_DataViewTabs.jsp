<%@ page buffer="1024kb" %>
<%@ page contentType="text/html;charset=windows-1256"%>
<HTML>
<HEAD>
<TITLE></TITLE>
<SCRIPT LANGUAGE=javascript>
function enableOthers(argButID)
{
	var colInputs;	
	var objInput;
	colInputs = document.all.tags("INPUT");	
	for(var i=0;i <colInputs.length;i++)
	{
		objInput = 	colInputs[i];
		if(objInput.id != argButID)
		{
			objInput.disabled = false;
      objInput.style.fontWeight = "normal";
			objInput.style.borderWidth = 1;
      objInput.style.backgroundColor = "#f5f5f5";
      
		}	
	}
}
function butFrom_onclick() 
{
//	butFrom.disabled = true;
  butFrom.style.fontWeight = "bold";
	butFrom.style.borderWidth	= 0;
  butFrom.style.backgroundColor = "#dee7ef";
	enableOthers(butFrom.id);
  openWizardStep(1);
}
function butSelect_onclick() 
{
//	butSelect.disabled = true;
  butSelect.style.fontWeight = "bold";
	butSelect.style.borderWidth = 0;
  butSelect.style.backgroundColor = "#dee7ef";
	enableOthers(butSelect.id);
  openWizardStep(3);
}

function butWhere_onclick() 
{
//	butWhere.disabled = true;
  butWhere.style.fontWeight = "bold";
	butWhere.style.borderWidth = 0;
  butWhere.style.backgroundColor = "#dee7ef";
	enableOthers(butWhere.id);
  openWizardStep(5);
}

function butTerms_onclick() 
{
//	butTerms.disabled = true;
  butTerms.style.fontWeight = "bold";
	butTerms.style.borderWidth = 0;
  butTerms.style.backgroundColor = "#dee7ef";
	enableOthers(butTerms.id);
  openWizardStep(4);
}

function butGroupBy_onclick() 
{
//	butGroupBy.disabled = true;
  butGroupBy.style.fontWeight = "bold";
	butGroupBy.style.borderWidth = 0;
  butGroupBy.style.backgroundColor = "#dee7ef";
	enableOthers(butGroupBy.id);
  openWizardStep(6);
}

function butHaving_onclick() 
{
//	butHaving.disabled = true;
  butHaving.style.fontWeight = "bold";
	butHaving.style.borderWidth = 0;
  butHaving.style.backgroundColor = "#dee7ef";
	enableOthers(butHaving.id);
  openWizardStep(7);
}

function butManipulatedFields_onclick() 
{
//	butManipulatedFields.disabled = true;
  butManipulatedFields.style.fontWeight = "bold";
	butManipulatedFields.style.borderWidth = 0;
  butManipulatedFields.style.backgroundColor = "#dee7ef";
	enableOthers(butManipulatedFields.id);
  openWizardStep(2);
}

function butOrderBy_onclick() 
{
//	butOrderBy.disabled = true;
  butOrderBy.style.fontWeight = "bold";
	butOrderBy.style.borderWidth = 0;
  butOrderBy.style.backgroundColor = "#dee7ef";
	enableOthers(butOrderBy.id);
  openWizardStep(8);
}

function butPreview_onclick() 
{
//	butPreview.disabled = true;
  butPreview.style.fontWeight = "bold";
	butPreview.style.borderWidth = 0;
  butPreview.style.backgroundColor = "#dee7ef";
	enableOthers(butPreview.id);
}

function butPreviewSave_onclick() 
{
//	butPreviewSave.disabled = true;
  butPreviewSave.style.fontWeight = "bold";
	butPreviewSave.style.borderWidth = 0;
  butPreviewSave.style.backgroundColor = "#dee7ef";
	enableOthers(butPreviewSave.id);
}

function butWizardHelp_onclick() 
{
//	butWizardHelp.disabled = true;
  butWizardHelp.style.fontWeight = "bold";
	butWizardHelp.style.borderWidth = 0;
  butWizardHelp.style.backgroundColor = "#dee7ef";
	enableOthers(butWizardHelp.id);
  openWizardStep(9);
}

function openWizardStep(argAction)
{
  var strTarget = "";
  try
  {
    parent.frames("Down").addToCart();
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
          strTarget = "DataViewHelp.html";      
          break;      
      default: 
        alert("Action Not Supported");
         break;
  }
  if(strTarget != "")
      parent.frames("Down").navigate(strTarget);  
}
//-->
</SCRIPT>
</HEAD>
<script language="JavaScript" type="text/javascript" src="../clientscripts/utilities.js"></script>
<LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
<script language="JavaScript" type="text/javascript" src="../clientscripts/version.js"></script>
<BODY BGCOLOR=#ffffff height=10 valign=bottom LEFTMARGIN=3 TOPMARGIN=10 bottomMARGIN=0 MARGINWIDTH="0" scroll=no MARGINHEIGHT="0"><!--


<A 
id=helpLink href="../default.asp" target=_top><IMG alt=Logout 
src="../images/logout.gif" border=0></A><A id=helpLink0 href="../help/help.asp" 
target=_blank><IMG alt=Help src="../images/help.gif" border=0></A> <A id=feedback 
href="mailto:SAbdelAzim@mobinil.com"><IMG style="LEFT: 629px; TOP: 173px" 
alt=Feedback src="../images/feedback.gif" align=right 
border=0></A>

		<TD height="80" background="../images/back1.gif">
            &nbsp;</TD>
-->
<TABLE valign=bottom cellSpacing=0 cellPadding=1 width="100%" style="BORDER-BOTTOM-WIDTH: thick; BORDER-BOTTOM: 10px solid; BORDER-BOTTOM-COLOR:#dee7ef; BORDER-LEFT-WIDTH: 0px; BORDER-RIGHT-WIDTH: 0px;BORDER-TOP-WIDTH: 0px">
  
  <TR>
    <TD><INPUT id=butFrom  style="BACKGROUND-COLOR:#f5f5f5; FONT-SIZE: 11px; BACKGROUND: #dee7ef; BORDER-LEFT: #ffffff 2px solid; COLOR: #000000; FONT-FAMILY: Tahoma, Arial, Helvetica, Verdana; BORDER-TOP-WIDTH: 0px; BORDER-LEFT-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; WIDTH: 84px; HEIGHT: 24px; BORDER-RIGHT-WIDTH: 0px;FONT-WEIGHT: bold;cursor:hand" type=button size=28 value=From name=butFrom  LANGUAGE=javascript onclick="return butFrom_onclick()"></TD>
    <TD><INPUT id=butManipulatedFields class="button" style="FONT-SIZE: 11px; BACKGROUND: #f5f5f5; BORDER-LEFT: #ffffff 2px solid; COLOR: #000000; FONT-FAMILY: Tahoma, Arial, Helvetica, Verdana; WIDTH: 120px; HEIGHT: 24px; cursor:hand" type=button size=28 value="  Manipulated Fields  " name=butManipulatedFields LANGUAGE=javascript onclick="return butManipulatedFields_onclick()"></TD>
    <TD><INPUT id=butSelect  style="FONT-SIZE: 11px; BACKGROUND: #f5f5f5; BORDER-LEFT: #ffffff 2px solid; COLOR: #000000; FONT-FAMILY: Tahoma, Arial, Helvetica, Verdana; WIDTH: 84px; HEIGHT: 24px;cursor:hand" type=button size=28 value=" Select " name=butSelect LANGUAGE=javascript onclick="return butSelect_onclick()"></TD>
    <TD><INPUT id=butTerms  style="FONT-SIZE: 11px; BACKGROUND: #f5f5f5; BORDER-LEFT: #ffffff 2px solid; COLOR: #000000; FONT-FAMILY: Tahoma, Arial, Helvetica, Verdana; WIDTH: 84px; HEIGHT: 24px;cursor:hand" type=button size=28 value=" Terms " name=butTerms LANGUAGE=javascript onclick="return butTerms_onclick()"></TD>
    <TD><INPUT id=butWhere  style="FONT-SIZE: 11px; BACKGROUND: #f5f5f5; BORDER-LEFT: #ffffff 2px solid; COLOR: #000000; FONT-FAMILY: Tahoma, Arial, Helvetica, Verdana; WIDTH: 84px; HEIGHT: 24px;cursor:hand" type=button size=28 value=" Where " name=butWhere LANGUAGE=javascript onclick="return butWhere_onclick()"></TD>
    <TD><INPUT id=butGroupBy  style="FONT-SIZE: 11px; BACKGROUND: #f5f5f5; BORDER-LEFT: #ffffff 2px solid; COLOR: #000000; FONT-FAMILY: Tahoma, Arial, Helvetica, Verdana; WIDTH: 84px; HEIGHT: 24px;cursor:hand" type=button size=28 value="  Group By  " name=butGroupBy LANGUAGE=javascript onclick="return butGroupBy_onclick()"></TD>
    <TD><INPUT id=butHaving  style="FONT-SIZE: 11px; BACKGROUND: #f5f5f5; BORDER-LEFT: #ffffff 2px solid; COLOR: #000000; FONT-FAMILY: Tahoma, Arial, Helvetica, Verdana; WIDTH: 84px; HEIGHT: 24px;cursor:hand" type=button size=28 value=" Having " name=butHaving LANGUAGE=javascript onclick="return butHaving_onclick()"></TD>
    <TD><INPUT id=butOrderBy  style="FONT-SIZE: 11px; BACKGROUND: #f5f5f5; BORDER-LEFT: #ffffff 2px solid; COLOR: #000000; FONT-FAMILY: Tahoma, Arial, Helvetica, Verdana; WIDTH: 84px; HEIGHT: 24px;cursor:hand" type=button size=28 value="  Order By  " name=butOrderBy LANGUAGE=javascript onclick="return butOrderBy_onclick()"></TD>
    <TD><INPUT id=butPreview  style="display:none; WIDTH: 84px; HEIGHT: 24px" type=button size=28 value=Preview name=butPreview LANGUAGE=javascript onclick="return butPreview_onclick()"></TD>
    <TD><INPUT id=butPreviewSave  style="display:none; WIDTH: 84px; HEIGHT: 24px" type=button size=28 value="Preview &amp; Save" name=butPreviewSave LANGUAGE=javascript onclick="return butPreviewSave_onclick()"></TD>
    <TD><INPUT id=butWizardHelp  style="display:none; WIDTH: 84px; HEIGHT: 24px" type=button size=28 value="Wizard Help" name=butWizardHelp LANGUAGE=javascript onclick="return butWizardHelp_onclick()" ></TD>
    <TD width=25%></TD>
    </TR>
    </TABLE>
    
</BODY>
</HTML>