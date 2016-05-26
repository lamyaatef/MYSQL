
function removeRow(rowId)
{
  var table = document.getElementsByTagName("table")[2];
  var tBody = table.getElementsByTagName("tbody")[0];
  var row = document.getElementById(rowId);
  tBody.removeChild(row);
}
function load()
        {
        window.status = "Risk Managment System"
        }
//image Function mouse over
function over(element) {
element.style.borderStyle= "solid";
element.style.borderWidth = "1px";
element.style.borderColor = "#000000";
element.style.backgroundColor = "#F1F1F1";
element.style.cursor = "hand";
}
// image Function mouse out
function out(element) {
element.style.borderColor = "#f5f5f5";
element.style.backgroundColor = "#f5f5f5"
element.style.cursor = "default";
}
//insert the html tags
function readyDiv() {
var theHTML;
theHTML = document.all.tags('div')['pageHTML'].innerText;
document.all.tags('div')['pageHTML'].innerHTML = theHTML;
}
//Function for execution to create bold/italic/...
function cmdExec(cmd,opt) {
pageHTML.document.execCommand(cmd,"",opt);
document.body.all.tags('div')['pageHTML'].focus();
}
//Function to execute Link
function createLink() {
cmdExec("CreateLink");
}
//Function to insert impage
function insertImage() {
var sImgSrc = prompt("Image: ", "http://www.[domain].[ext]/[folder]/[file]");
if(sImgSrc!=null)
cmdExec("InsertImage",sImgSrc);
}
function div2hidden(objForm) {
objForm.pageHTML.value = document.all.tags('div')['pageHTML'].innerHTML;
}
//if (screen.width <= 800) {
//alert("Warning:you are using a large resolution(800x600) Site is best viewed with 1024x768");
//}
//var url="<%=conSite&conSiteRoot%>";var title="<%=strProjectname%>";
function  AddToFaves(url,title)
{
window.external.AddFavorite(url,title);
}
   
   
function openWindow(url) {
  popupWin = window.open(url,'New_Page', 'width=550,height=220,resizable=NO')
}   
 
 
function openWindowfull(url) {
  popupWin = window.open(url,'', 'left=200,top=200, resizable=yes,scrollbars=yes,status=yes,width=550,height=350');
} 

function openWindowfull2(url,feature) {
  popupWin = window.open(url,'', feature)
  //popupWin = window.open(url,'', 'left=200,top=200, resizable=yes,scrollbars=yes,status=yes,width=550,height=350')
} 

function OpenNewPopUpWindow(pageURL, windowOptions, objSelected, objName, frmName)
	{
	 if (NewPopUpWindow!=null)
		{
		NewPopUpWindow.close();
		}
	/* sample windowOptions = "scrollbars=yes,toolbar=no,location=no,status=no,width=400,height=300" */
	var NewPopUpWindow = window.open('', 'NewpopWin', windowOptions)
	NewPopUpWindow.location.href=pageURL + '?text=' + objSelected + '&objSelected=' + objName + '&frmName=' + frmName;
	NewPopUpWindow.focus();
	}



function openWindow2(url) {
  popupWin = window.open(url,'New_Page', 'width=555,height=330,resizable=no,status=1')
}   
function openWindowgenerate(url) {
  popupWin = window.open(url,'New_Page', 'left=400,top=300,width=300,height=150,resizable=no,status=1')
}   
var ie4 = document.all ? true:false;
var dom = document.getElementById ? true:false;
 function show(table, tablename) {
          if(ie4) {
    
        table.style.display = "";
          }
          if(dom) {
            document.getElementById(tablename).style.display = "";
          }
          return false;
        }
   
     function hide(table, tablename) {
          if(ie4) {
            table.style.display = "none";
          }
          if(dom) {
            document.getElementById(tablename).style.display = "none";
          }
          return false;
        }
        function Highlight(element) 
        {
          spans=document.getElementsByTagName("SPAN");
          for (i=0;i<spans.length;i++) 
          {
            spans[i].style.color='#7c8698';
          }
          element.style.color='red';
        }

        function toggle(table, tablename) {
          if((ie4 && table.style.display == "")
          || (dom && document.getElementById(tablename).style.display == "")) {
            return hide(table, tablename);
          } else {
            return show(table, tablename);
          }
        function toggle1(table, tablename) {
          if((ie4 && table.style.display == "")
          || (dom && document.getElementById(tablename).style.display == "")) {
            return show(table, tablename);
          } else {
            return hide(table, tablename);
          }
}
	
   
   
        }
