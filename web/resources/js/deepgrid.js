var UNDEFINED;  // do not assign!

function getX(obj) {
	return( obj.offsetParent==null ? obj.offsetLeft : obj.offsetLeft+getX(obj.offsetParent) );
}

function getY(obj) {
	return( obj.offsetParent==null ? obj.offsetTop : obj.offsetTop+getY(obj.offsetParent) );
}

function isvalidchar(achar,validstr) {
	return( validstr.toUpperCase().indexOf(achar.toString().toUpperCase(),0) >= 0 );
}

function onSelectFocus( objSelect, flgInput ) {
	if ( document.layers ) // NS4 (which doesn't work for SELECT objects anyway) 
	{
		//document.captureEvents( Event.KEYPRESS | Event.KEYDOWN | Event.KEYUP );
		//document.onkeydown  = selectKeyDownHandler;
		//document.onkeypress = selectKeyPressHandler;
		//document.onkeyup    = null;
	}
	else if ( document.all ) // IE4
	{
		if ( !document.all.divComboBubble )  // if this is the first time this function is called
		{                                    // then create the bubble text window (<DIV>).
			document.body.insertAdjacentHTML("beforeEnd", "<DIV id='divComboBubble' style='position:absolute;top:0px;left:0px;visibility:hidden'></div>");
		}
		// create/init data elements
		objSelect.X = getX(objSelect)+2;
		objSelect.Y = getY(objSelect)-20;
		objSelect.strKeyInBuf = '';
		objSelect.flgInput = false;

		// if flgInput is 'INPUT' then this is an enterable list box.
		if ( flgInput != UNDEFINED ) {
			objSelect.flgInput = ( (''+flgInput).toUpperCase() == 'INPUT' );
		}
	
		// setup event handlers
		objSelect.onmouseover = selectMouseOverHandler;
		objSelect.onmouseout  = selectMouseOutHandler;
		objSelect.onblur      = selectBlurHandler;
		objSelect.onkeydown   = selectKeyDownHandler;
		objSelect.onkeypress  = selectKeyPressHandler;
		objSelect.onkeyup     = null;

		// display bubble help (title)
		//objSelect.onmouseover(window.event);  // this should work, but in early versions of IE4 it doesn't!
		selectMouseOverHandler(window.event);
	}
}


function BubbleText(objSelect) {
	var s = divComboBubble.innerHTML = '';
	if ( !objSelect )  // for the onBlur event to clear out the bubble help
	{
		return(false);
	}

	with ( objSelect )
	{
		if ( strKeyInBuf.length > 0 )
		{
			if ( strKeyInBuf == title )
			{
				s = '<FONT color="blue">' + strKeyInBuf + '</font>';
			}
			else if ( ( selectedIndex >= 0 ) && ( strKeyInBuf == options[selectedIndex].text ) )  // unique match found
			{
				s = '<B>' + strKeyInBuf + '</b>';
			}
			else
			{
				var c = strKeyInBuf.substring(strKeyInBuf.length-1,strKeyInBuf.length);
				c = ( c == ' ' ) ? '&nbsp;' : '<B>' + c + '</b>';
				s = strKeyInBuf.substring(0,strKeyInBuf.length-1) + c;
			}
			divComboBubble.innerHTML = '<TABLE cellpadding=0 cellspacing=0 style="background-color:INFOBACKGROUND;'
			+ 'font:8pt ms sans serif;padding:2px 2px 2px 2px;color:INFOTEXT;border:1px solid INFOTEXT"><TR><TD align=left><NOBR>'+s+'</nobr></td></tr></table>';
		}
		divComboBubble.style.posLeft = X;
		divComboBubble.style.posTop  = Y;
		divComboBubble.style.visibility  = 'hidden';
	}

	return(true);
}


function findSelectEntry( objSelect, head, tail )  // uses divide-and-conquer search, assumes sorted list
{
	with ( objSelect )
	{
		if ( options.length <= 0 )
		{
			strKeyInBuf = ' <FONT color="red">No selections available.</font> ';
			BubbleText( objSelect );
			window.status = strKeyInBuf = '';
			return(-1);
		}

		if ( strKeyInBuf == '' )
		{
			strKeyInBuf = title;
			BubbleText( objSelect );
			window.status = strKeyInBuf = '';
			selectedIndex=0;  // set to top
			return( selectedIndex = options[selectedIndex].text.length ? -1 : 0 );
		}
		var mid = Math.round( (head+tail)/2 );

		if ( strKeyInBuf.toUpperCase() == options[mid].text.substring(0,strKeyInBuf.length).toUpperCase() )
		{
			while ( (mid>0)  &&  strKeyInBuf.toUpperCase() == options[mid-1].text.substring(0,strKeyInBuf.length).toUpperCase() )
			{
				mid--;  // get the topmost matching item in the list, not just the first found
			}

			selectedIndex=mid;
			window.status = strKeyInBuf = options[mid].text.substring(0,strKeyInBuf.length);
			if ( mid == Math.round( (head+tail)/2 ) )  // if the original mid is an exact match
			{
				if ( (mid==tail) || ( (mid<tail) && strKeyInBuf.toUpperCase() != options[mid+1].text.substring(0,strKeyInBuf.length).toUpperCase() ) )
				{
					window.status = strKeyInBuf = options[mid].text;  // unique match found
				}
			}

			BubbleText( objSelect );
			return( selectedIndex );
		}
		if ( head >= tail )  // then since no exact match was found, go back to previous strKeyInBuf (thus the length-1)
		{	
			strKeyInBuf = strKeyInBuf.substring(0,strKeyInBuf.length-1)
			return( findSelectEntry( objSelect, 0, options.length-1 ) );
		}
	
		if ( strKeyInBuf.toUpperCase() < options[mid].text.substring(0,strKeyInBuf.length).toUpperCase() )
		{
			return( findSelectEntry( objSelect, head, Math.max(head,mid-1) ) );
		}

		return( findSelectEntry( objSelect, Math.min(mid+1,tail), tail ) );
	}
}


function selectMouseOverHandler(e)
{
	var event = e ? e : window.event;  // to handle both NS4 and IE4 events
	if ( event.srcElement.strKeyInBuf == '' )
	{
		event.srcElement.strKeyInBuf = event.srcElement.title;
		BubbleText( event.srcElement );
		event.srcElement.strKeyInBuf = '';
	}
	else
	{
		BubbleText( event.srcElement );
	}
	return(true);
}


function selectMouseOutHandler(e)
{
	var event = e ? e : window.event;  // to handle both NS4 and IE4 events
	if ( event.srcElement != document.activeElement )
	{
		BubbleText( event.srcElement );
	}
	return(true);
}


function selectBlurHandler(e)
{
	var event = e ? e : window.event;  // to handle both NS4 and IE4 events

	event.srcElement.strKeyInBuf = '';
	window.status = (event.srcElement.selectedIndex>=0) ? event.srcElement.options[event.srcElement.selectedIndex].text : '';
	BubbleText( event.srcElement );

	return(true);
}


function selectKeyDownHandler(e)
{
	var event = e ? e : window.event;  // to handle both NS4 and IE4 events
	with ( event.srcElement )
	{
		// this is to correct the search bug when the list is left open after single-click
		if (  ( strKeyInBuf == '' ) && ( event.keyCode > 40 ) )
		{
			event.srcElement.blur();
			event.srcElement.focus();
		}

		switch(event.keyCode)
		{
			case(8):  // backspace
			{
				if ( ( selectedIndex >= 0 ) && ( strKeyInBuf == options[selectedIndex].text ) && !event.srcElement.flgInput )
				{
					window.status = strKeyInBuf = '';
				}
				else
				{
					window.status = strKeyInBuf = strKeyInBuf.substring(0,strKeyInBuf.length-1);
				}

				BubbleText( event.srcElement );
				event.keyCode = 0;
				return(false);
			}
			case(9):   // tab
			case(13):  // enter
			{
				event.keyCode = 9;  // convert enter to tab
				return(true);
			}
			case(27):  // escape
			{
				window.status = strKeyInBuf = '';
				BubbleText( event.srcElement );
				event.keyCode = 0;
				return(false);
			}

			case(32):  // space
			{
				window.status = strKeyInBuf += ' ';
				// this must be fired explicitely for spaces
				return( event.srcElement.flgInput ? event.srcElement.flgInput : selectKeyPressHandler(event) );
			}

			// I don't know if these are universal
			case(33):  // page up
			case(34):  // page down
			case(35):  // end
			case(36):  // home
			case(38):  // up arrow
			case(40):  // down arrow
			{
				window.status = strKeyInBuf = '';
				BubbleText( event.srcElement );
				return(true);
			}

		}  // end switch
	}  // end with

	return(true);
}


function selectKeyPressHandler(e)
{
	var event = e ? e : window.event;  // to handle both NS4 and IE4 events

	if ( event.keyCode == 13 ) { event.srcElement.blur(); event.srcElement.focus(); }
	if ( isvalidchar(String.fromCharCode(event.keyCode),"ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789 `~!@#$%^&*()_-+={}[]|:;'<>,./?\\\"" ) )
	{
		event.srcElement.strKeyInBuf += String.fromCharCode(event.keyCode);
	}

	if ( event.srcElement.flgInput )   // this is an enterable field
	{
		event.srcElement.options.length = 0;
		event.srcElement.strKeyInBuf    = event.srcElement.strKeyInBuf.substring(0,31);  // maxlength=32, should be set elsewhere
		event.srcElement.options[0]     = new Option( event.srcElement.strKeyInBuf, event.srcElement.strKeyInBuf );
		event.srcElement.selectedIndex  = 0;
	}
	else   // search to find the matching value
	{
		// must use setTimeout to override the default SELECT keypress event(s)
		var strSel = 'document.' + event.srcElement.form.name + '.' + event.srcElement.name;
		setTimeout( 'findSelectEntry(' + strSel + ',0,' + strSel + '.options.length-1);' , 1 );
	}
	return(true);
}


/*
 #################################################################################
 #
 #   DeepGrid version 1.0.
 #     DeepGrid is a object based javascript Grid.
 #
 #   Copyright (C) 2002-2003  Pradeep Palat
 #
 #   Pradeep.P
 #   deepgrid@yahoo.com
 #
 #   This program is free software; you can redistribute it and/or modify
 #   it under the terms of the GNU General Public License as published by
 #   the Free Software Foundation; either version 2 of the License, or
 #   (at your option) any later version.
 #
 #   This program is distributed in the hope that it will be useful,
 #   but WITHOUT ANY WARRANTY; without even the implied warranty of
 #   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 #   GNU General Public License for more details.
 #
 #   You should have received a copy of the GNU General Public License
 #   along with this program; if not, write to the Free Software
 #   Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 #   http://www.gnu.org/licenses/gpl.txt
 #
 ##################################################################################
*/

 /*  Basic Controls */
 var DG_TEXT     		= "text" 
 var DG_SELECT   		= "select"
 var DG_READONLY 		= "readonly"
 var DG_BUTTON     		= "button"
 var DG_SUBMIT     		= "submit"
 var DG_RESET     		= "reset"
 var DG_CHECKBOX 		= "checkbox"
 var DG_RADIO    		= "radio"
 var DG_PASSWORD 		= "password"
 var DG_TEXTAREA 		= "textarea"
 var DG_HIDDEN   		= "hidden"
 var DG_FILE 	  		= "file"
 var DG_TEXTX  			= "textx"
 var DG_IMAGE  			= "image"

 /*  Extended Controls */
 var DG_NUMERIC   		= "numeric"
 var DG_BOOLEAN 		= "boolean"
 var DG_LABEL    		= "label"

/*  Align Constants */
 var DG_LEFT     		= "left"
 var DG_CENTER   		= "center"
 var DG_RIGHT    		= "right"

/*  Style Constants */
 var DG_NONE	  		= "plain"
 var DG_PLAIN	  		= "plain"
 var DG_BOLD	  		= "Bold"
 var DG_ITALIC	  		= "Italic"
 var DG_BOLDITALIC		= "BoldItalic"

/*  Alternate Color Constants */
 var DG_DEFAULT_EVEN		= "#fff8DD"
 var DG_DEFAULT_ODD		= "#fff4E8"
 var DG_NOCOLOR			= ""

/*  Column Constants */
 var DG_APPLY_ALL      		= true
 var DG_APPLY_CELL		= false

/*  Column Attribute Positions */
 var DG_POS_KEY      		= 0
 var DG_POS_COLUMNNAME  	= 1
 var DG_POS_COLUMNICON		= 2
 var DG_POS_WIDTH      		= 3
 var DG_POS_ALIGNMENT   	= 4
 var DG_POS_COLUMNTYPE  	= 5
 var DG_POS_DEFAULTVALUE	= 6
 var DG_POS_EVENTS      	= 7
 var DG_POS_STYLE      		= 8
 var DG_POS_ICON      		= 9
 var DG_POS_COLNUM		= 10
 var DG_POS_VISIBLE		= 11

/*  Class IDs */
 var DG_DEEPGRID      		= "DeepGrid"
 var DG_COLUMNHEADERS   	= "ColumnHeader"
 var DG_COLUMN      		= "Column"
 var DG_ROWSET	  		= "RowSet"
 var DG_ROW      		= "Row"
 var DG_CELL  			= "Cell"
 var DG_FOOTER			= "Footer"

 var u_TableRegistry	= new Array()

 function moveToCell(f_object){
  var t_table=u_TableRegistry[f_object.arrName][0]
  var KeyCode=event.keyCode
  if(t_table.FocusRow==null) t_table.FocusRow=1
  if(t_table.FocusCol==null) t_table.FocusCol=1
  var r=eval(t_table.FocusRow)
  var c=eval(t_table.FocusCol)
  t_table.keyCode=KeyCode
  var t_rowCount=t_table.getRowCount()
  var t_colCount=t_table.getColumnCount()

  switch(KeyCode){
   case 37: // move left
     if(!window.event.ctrlKey) break;
     if(c==1) c=t_colCount
     else c--

     var cellObj = t_table.getCell(r,c)
     var cellType = cellObj.getColumnType()
     while (!cellObj.isVisible() || cellType==DG_IMAGE || cellType==DG_READONLY || cellType==DG_HIDDEN || cellType==DG_LABEL){ c--; if(c==0) c=t_colCount; cellObj = t_table.getCell(r,c); cellType = cellObj.getColumnType()}
	 t_table.getCell(r,c).setFocus()
	 cancelKey()
   break;

   case 38: // move top
	if(!window.event.ctrlKey) break;
	if(r==1) r=t_rowCount
	else r--
	while (!t_table.getRow(r).isVisible()){r--;if(r==0) r=t_rowCount;}
	t_table.getCell(r,c).setFocus()
	cancelKey()
   break;

   case 39: // move right
     if(!window.event.ctrlKey) break;
     if(c==t_colCount) c=1
     else c++

     var cellObj = t_table.getCell(r,c)
     var cellType = cellObj.getColumnType()
     while (!cellObj.isVisible() || cellType==DG_IMAGE || cellType==DG_READONLY || cellType==DG_HIDDEN || cellType==DG_LABEL){ c++; if(c>t_colCount) c=1; cellObj = t_table.getCell(r,c); cellType = cellObj.getColumnType()}
     t_table.getCell(r,c).setFocus()
     cancelKey()
   break;

   case 40: // move Down
    if(!window.event.ctrlKey) break;
    if(r==t_rowCount) r=1
    else r++
    while (!t_table.getRow(r).isVisible()){ r++; if(r>t_rowCount)r=1;}
    t_table.getCell(r,c).setFocus()
    cancelKey()
   break;
  }
 }

 function cancelKey(){
   event.keyCode=0
   event.returnValue=0
   event.cancelBubble=true;
   return false
 }

 function setAsCurrent(f_object){
	t_name=f_object.name
	t_colPos=t_name.indexOf("__C")+3
	t_nextDelimPos=t_name.indexOf("__",t_colPos)
	colNum=t_name.substring(t_colPos,(t_nextDelimPos==-1)?t_name.length:t_nextDelimPos)
	var t_table=u_TableRegistry[f_object.arrName][0]
	if(t_table.FocusObject==t_name) return

	for(var t_i=0;t_i<t_table.ColumnHeaders.arrColumns.length;t_i++){
        t_col=t_table.ColumnHeaders.arrColumns[t_i]
        if(t_col.colNum==colNum){
          colNum=t_i+1
          break
        }
	}

	t_table.FocusObject=t_name
	t_table.FocusCol=colNum
	t_table.FocusRow=getFocusRow(t_table,t_name,colNum)
 }

 function getOwnerCell(f_object){
	t_name=f_object.name
	t_tableName=f_object.arrName
	var t_tableObj=u_TableRegistry[t_tableName][0]

	/*Deduce the Column Number*/
	t_colPos=t_name.indexOf("__C")+3
	t_nextDelimPos=t_name.indexOf("__",t_colPos)
	colNum=t_name.substring(t_colPos,(t_nextDelimPos==-1)?t_name.length:t_nextDelimPos)

	for(var t_i=0;t_i<t_tableObj.ColumnHeaders.arrColumns.length;t_i++){
          t_col=t_tableObj.ColumnHeaders.arrColumns[t_i]
          if(t_col.colNum==colNum){
            colNum=t_i+1
            break
          }
	}

	/*Deduce the Row Number*/
	var t_rowCount=t_tableObj.getRowCount()
	for(t_i=1;t_i<=t_rowCount;t_i++){
	  t_ownerObj=t_tableObj.RowSet.Row(t_i).Cells(colNum)
     	  if(t_ownerObj.cellElement.name==t_name)  return t_ownerObj
    }
 }

 function getFocusRow(f_table,f_objname,f_colNum){
   t_key=f_table.keyCode
   f_table.keyCode=null
   t_rowCount=f_table.getRowCount()

   switch(t_key){
   case 37: //left
	if(f_table.FocusRow!=null) return f_table.FocusRow
   break;

   case 38: //Top
    var t_i=f_table.getRowCount()
    if(f_table.FocusRow!=null) t_i=f_table.FocusRow
    if(t_i==1) t_i = t_rowCount
    else t_i--
    while(!f_table.getRow(t_i).isVisible()){t_i--; if(t_i==0) t_i=t_rowCount}
    for(;t_i>=1;t_i--){
     if(f_table.RowSet.Row(t_i).Cells(f_colNum).cellElement.name==f_objname)  return t_i
    }
   break;

   case 39: //Right
    if(f_table.FocusRow!=null) return f_table.FocusRow
   break;

   case 40: //Down
    var t_i=1
    t_rowCount=f_table.getRowCount()
    if(f_table.FocusRow!=null) t_i=f_table.FocusRow
    if(t_i==t_rowCount) t_i=1
    else t_i++
    while(!f_table.getRow(t_i).isVisible()){t_i++; if(t_i>t_rowCount) t_i=1}
    for(;t_i<=t_rowCount;t_i++){
     if(f_table.RowSet.Row(t_i).Cells(f_colNum).cellElement.name==f_objname)  return t_i
    }
   break;

   default:
    for(var t_i=1;t_i<=t_rowCount;t_i++){
     if(f_table.RowSet.Row(t_i).Cells(f_colNum).cellElement.name==f_objname)  return t_i
    }
   break;

   }
   return 1
 }

 function deleteElement(array, n) {
  //delete the nth element of array
  var length = array.length;
  if (n >= length || n<0)
	n=length

  for(var i=n; i<length-1; i++)
	array[i] = array[i+1];
  array.length--;

   str=""
 }

 function addElement(array,element,n){
  //insert Cell at nth position
  if(n<0 || n==null)
	n=array.length

  for(var i=array.length;i>n;i--){
   array[i]=array[i-1]
  }

  array[n]=element
 }

 function isError(condition,message){
   if(condition){
     alert("ERROR : " + message)
     return true
   }
   return false
 }

 function valueChanged(f_obj, f_objType){
   if(f_objType==DG_CHECKBOX){
     if(event.propertyName!="checked") return
     var t_table = u_TableRegistry[f_obj.arrName][0]
     var t_name = f_obj.name

 var t_colPos=t_name.indexOf("__C")+3
	 var t_nextDelimPos=t_name.indexOf("__",t_colPos)

     var t_rowCount=t_table.getRowCount()
	 var t_colNum=t_name.substring(t_colPos,(t_nextDelimPos==-1)?t_name.length:t_nextDelimPos)

     for(var i=1;i<=t_rowCount;i++){
       var t_cell=t_table.getRow(i).Cells(t_colNum)
       if(t_cell.cellElement.name==t_name){
         eval("document.all."+t_cell.auxVariable).value=(f_obj.checked)?f_obj.value:null
         break
	   }
     }
   }


   if(f_objType==DG_BOOLEAN){
     if(event.propertyName!="checked") return
     var t_table=u_TableRegistry[f_obj.arrName][0]
     var t_name = f_obj.name

	 var t_colPos=t_name.indexOf("__C")+3
	 var t_nextDelimPos=t_name.indexOf("__",t_colPos)

     var t_rowCount=t_table.getRowCount()
     var t_colNum=t_name.substring(t_colPos,(t_nextDelimPos==-1)?t_name.length:t_nextDelimPos)

     for(var i=1;i<=t_rowCount;i++){
       var t_cell=t_table.getRow(i).Cells(t_colNum)
       if(t_cell.cellElement.name==t_name){
         eval("document.all."+t_cell.auxVariable).value=f_obj.checked
         break
       }
     }
   }

   if(f_objType==DG_RADIO){
     if(event.propertyName!="checked" || (event.propertyName=="checked" && !f_obj.checked)) return

     var t_table=u_TableRegistry[f_obj.arrName][0]
     var t_name = f_obj.name

     var t_colPos=t_name.indexOf("__C")+3
     var t_nextDelimPos=t_name.indexOf("__",t_colPos)

     var t_rowCount=t_table.getRowCount()
     var t_colNum=t_table.getColumnNumber(f_obj)

     for(var i=1;i<=t_rowCount;i++){
       var t_cell=t_table.getRow(i).Cells(t_colNum)
       if(t_cell.radioLocked) return
       var t_value  = (f_obj.checked)?f_obj.value:null
       var t_auxVar = eval("document.all." + t_cell.auxVariable)
       var canExit  = false

       if(t_cell.cellElement.name==t_name){
         t_auxVar.value     = t_value
         canExit=true
       }else{
	 if(t_cell.cellElement.checked){
	     t_auxVar.value=null
	     t_cell.radioLocked=true
	     t_cell.cellElement.checked=false
	     t_cell.radioLocked=false
	     if(canExit) break
	 }
       }
     }
   }
 }

 function isNumeric()
 {
   var keyCode=event.keyCode
  
   if ((keyCode>=96) && (keyCode<=105))
   {
   return true
   }
   
   if((keyCode<48 || keyCode >58) && (keyCode!=8 && keyCode!=9 && keyCode!=37 && keyCode!=38 && keyCode!=39 && keyCode!=40 && keyCode!=46 )){ // Allow only integers and the keys for backspace, Down, Up, Right, Left and delete
     return false
   }
   return true
 }

 function DeepGrid(f_tableName, f_border, f_width){
   if(isError(f_tableName==="" || f_tableName==null,"Argument 'tableName' expected for function - DeepGrid(tableName,[borderWidth],[tableWidth])")) return null;

   var tObj=eval("document.all."+ f_tableName);
   if(isError(tObj!=null,"Object by name " + f_tableName + " already exists")) return null;

  /* ~~~~~~~~~~~~~ Member Variables ~~~~~~~~~~~~~ */
   this.object        	= null
   this.ColumnHeaders 	= null
   this.columnheaders 	= null
   this.columnHeaders 	= null
   this.RowSet     	= null
   this.rowset     	= null
   this.Rowset     	= null
   this.rowSet     	= null
   this.Footer        	= null
   this.footer		= null

   this.FocusRow	= null
   this.FocusCol	= null
   this.FocusObject	= null
   this.arrName		= null
   this.keyCode		= null

   this.tableName     	= ""
   this.border		 	= 0
   this.width		 	= "100%"
   this.visible			= true
   this.gridLines		= 1;

   this.backgroundImage = ""
   this.wallpaper= ""
   this.backgroundColor	= DG_NOCOLOR;
   this.foregroundColor	= "";
   this.fontName		= "verdana";
   this.fontSize		= "10";
   this.fontStyle		= DG_NONE;
   this.align			= DG_LEFT
   this.altColor		= false
   this.evenColor		= DG_DEFAULT_EVEN
   this.oddColor		= DG_DEFAULT_ODD

   /* ~~~~~~~~~~~~~ Constructor Code ~~~~~~~~~~~~~ */

   if (f_tableName!=null) this.tableName = f_tableName
   if (f_border!=null) 	 this.border	= f_border
   if (f_width!=null) 	 this.width		= f_width

   document.write("<table cellpadding=0 cellspacing=1 style='border-width:" + this.border + ";' width=" + this.width + " name='" + this.tableName + "' id='" + this.tableName + "'></table>");

   this.object        = eval("document.all."+ this.tableName)
   this.ColumnHeaders = new ColumnHeaders(this)
   this.columnheaders = this.ColumnHeaders
   this.columnHeaders = this.ColumnHeaders
   this.RowSet     = new RowSet(this)
   this.rowset     = this.RowSet
   this.Rowset     = this.RowSet
   this.rowSet     = this.RowSet
   this.Footer     = new Footer(this);
   this.footer	   = this.Footer

   this.pseudoRow     = 1
   this.pseudoCol	 = 1
   this.arrName		 = this.tableName

   var t_arr=new Array()
   t_arr[0]=this

   u_TableRegistry[this.arrName]=t_arr

   /* ~~~~~~~~~~~~~ Member Functions ~~~~~~~~~~~~~ */

   this.getObject     		= getObject
   this.getObjectID		= getObjectID

   this.getName       		= getTableName
   this.setBorder     		= setBorder
   this.getBorder     		= getBorder
   this.setWidth      		= setWidth
   this.getWidth      		= getWidth
   this.getColumnHeaders    	= getColumnHeaders
   this.getRowSet		= getRowSet
   this.getFooter		= getFooter
   this.getPseudoRowNum		= getPseudoRowNum
   this.getPseudoColNum		= getPseudoColNum
   this.getColumn		= getColumn
   this.getRow			= getRow
   this.getCell			= getCell
   this.getFocusRow		= getFocusRow
   this.getFocusColumn		= getFocusColumn
   this.getFocusObject		= getFocusObject
   this.getRowNumber	= getRowNumber
   this.getColumnNumber = getColumnNumber

   this.getRowCount		= getRowCount
   this.getColumnCount		= getColumnCount
   this.setVisible		= setVisible
   this.isVisible		= isVisible
   this.setGridLines		= setGridLines
   this.showGridLines		= setGridLines
   this.getGridLines		= getGridLines
   this.hasGridLines		= getGridLines

   this.setWallpaper	    = setWallpaper
   this.getWallpaper 	    = getWallpaper
   this.setBackgroundImage  = setBackgroundImage
   this.getBackgroundImage  = getBackgroundImage
   this.setBackgroundColor  = setBackgroundColor
   this.getBackgroundColor  = getBackgroundColor
   this.setForegroundColor  = setForegroundColor
   this.getForegroundColor  = getForegroundColor
   this.setFont   	    = setFont
   this.setFontName	    = setFontName
   this.getFontName	    = getFontName
   this.setFontSize	    = setFontSize
   this.getFontSize	    = getFontSize
   this.setFontStyle	    = setFontStyle
   this.getFontStyle	   	= getFontStyle
   this.setAlignment	   	= setAlignment
   this.getAlignment	   	= getAlignment
   this.setAltColor		= setAltColor
   this.isAltColor		= isAltColor
   this.setEvenColor		= setEvenColor
   this.getEvenColor		= getEvenColor
   this.setOddColor		    = setOddColor
   this.getOddColor		    = getOddColor

   function getObject(){
    return this.object
   }

   function getObjectID(){
    return DG_TABLE
   }

   function getTableName(){
     return this.tableName
   }

   function getBorder(){
    return this.border
   }

   function setBorder(f_border){
     if(isError(f_border==="" || f_border==null,"Argument 'borderWidth' expected for function - DeepGrid.setBorder(borderWidth)")) return

     this.border=f_border
     this.object.style.borderWidth=f_border
   }

   function getWidth(){
     return this.width
   }

   function setWidth(f_width){
     if(isError(f_width==="" || f_width==null,"Argument 'tableWidth' expected for function - DeepGrid.setWidth(tableWidth)")) return

     this.width=f_width
	 this.object.style.width=f_width
   }

   function getColumnHeaders(){
     return this.columnHeaders
   }

   function getRowSet(){
     return this.RowSet
   }

   function getFooter(){
     return this.Footer
   }

   function getPseudoRowNum(){
     return this.pseudoRow++
   }

   function getPseudoColNum(){
     return this.pseudoCol++
   }

   function getCell(f_row,f_col){
     if(isError(f_row==="" || f_row==null,"Argument 'rowIndex' expected for function - DeepGrid.getCell(rowIndex, columnIndex)")) return
     if(isError(f_col==="" || f_col==null,"Argument 'columnIndex' expected for function - DeepGrid.getCell(rowIndex, columnIndex)")) return
     if(isError(isNaN(f_row) || f_row<1,"Invalid Argument " + f_row + " 'rowIndex' passed for DeepGrid.getCell(rowIndex, columnIndex)")) return
     if(isError(isNaN(f_col) || f_col<1,"Invalid Argument " + f_col + " 'columnIndex' passed for DeepGrid.getCell(rowIndex, columnIndex)")) return
     return this.RowSet.Row(f_row).Cells(f_col)
   }

   function getFocusRow(){
     return this.FocusRow
   }

   function getFocusColumn(){
     return this.FocusCol
   }

   function getFocusObject(){
     return this.FocusObject
   }

   function getRowNumber(f_object){
	 /*Deduce the Row Number*/
	 var t_rowCount=this.getRowCount()
	 var colNum=this.getColumnNumber(f_object)
	 var t_name=f_object.name
	 for(t_i=1;t_i<=t_rowCount;t_i++){
	   t_ownerObj=this.RowSet.Row(t_i).Cells(colNum)
      	  if(t_ownerObj.cellElement.name==t_name)  return t_i
     }
   }

   function getColumnNumber(f_object){
	 /*Deduce the Column Number*/
	 var t_name=f_object.name
	 t_colPos=t_name.indexOf("__C")+3
	 t_nextDelimPos=t_name.indexOf("__",t_colPos)
	 colNum=t_name.substring(t_colPos,(t_nextDelimPos==-1)?t_name.length:t_nextDelimPos)

	 for(var t_i=0;t_i<this.ColumnHeaders.arrColumns.length;t_i++){
           t_col=this.ColumnHeaders.arrColumns[t_i]
           if(t_col.colNum==colNum){
             return t_i+1
           }
	 }
   }

   function getColumn(f_col){
     if(isError(f_col==="" || f_col==null,"Argument 'columnIndex' expected for function - DeepGrid.getColumn(columnIndex)")) return
     if(isError(f_col<=0 || f_col>this.ColumnHeaders.getLength(),"DeepGrid.getColumn(columnIndex) : Specified column does not exist")) return
     return this.ColumnHeaders.Columns(f_col)
   }

   function getRow(f_row){
     if(isError(f_row==="" || f_row==null,"Argument 'rowIndex' expected for function - DeepGrid.getRow(rowIndex)")) return
     if(isError(isNaN(f_row),"Invalid Argument " + f_row + " passed for DeepGrid.getRow(rowIndex)")) return
     if(isError(f_row<=0 || f_row>this.getRowCount(),"DeepGrid.getRow(rowIndex) : Specified Row does not exist")) return
     return this.RowSet.Row(f_row)
   }

   function getRowCount(){
     return this.RowSet.arrItems.length
   }

   function getColumnCount(){
     return this.object.cols
   }

   function setVisible(f_state){
     if(isError(f_state==="" || f_state==null,"Argument 'state' expected for function - DeepGrid.setVisible(state)")) return
     if(isError(typeof(f_state)!="boolean","Invalid Argument " + f_state + "  'state' passed for DeepGrid.setVisible(state)")) return
     displayState="none"
     if(f_state) displayState="block"
     this.visible=f_state
     this.object.style.display=displayState
   }

   function isVisible(){
     return this.visible
   }

   function setGridLines(f_state){
     if(isError(f_state==="" || f_state==null,"Argument 'state' expected for function - DeepGrid.setGridLines(state)")) return
     if(isError(typeof(f_state)!="boolean","Invalid Argument " + f_state + "  'state' passed for DeepGrid.setGridLines(state)")) return
     this.gridLines=f_state
     if(f_state) this.object.cellSpacing=1
     else this.object.cellSpacing=0
   }

   function getGridLines(){
     return this.gridLines
   }

   function setWallpaper(f_image){
     if(f_image==="" || f_image==null){
       this.wallpaper=""
     }else{
       this.wallpaper=f_image
     }
     this.object.style.backgroundImage="url(" + f_image + ")"
   }

   function getWallpaper(){
     return this.wallpaper
   }

   function setBackgroundImage(f_image){
     if(f_image==="" || f_image==null){
       this.backgroundImage=""
     }
     else{
       this.ColumnHeaders.setBackgroundImage(f_image)
       this.RowSet.setBackgroundImage(f_image)
       this.Footer.setBackgroundImage(f_image)
       this.backgroundImage=f_image
     }
   }

   function getBackgroundImage(){
     return this.backgroundImage
   }

   function setBackgroundColor(f_thisColor){
     if(f_thisColor==="" || f_thisColor==null) return

     this.backgroundColor=f_thisColor
     this.ColumnHeaders.setBackgroundColor(f_thisColor)
     this.RowSet.setBackgroundColor(f_thisColor)
     this.Footer.setBackgroundColor(f_thisColor)
   }

   function getBackgroundColor(){
     return this.backgroundColor
   }

   function setForegroundColor(f_thisColor){
     if(f_thisColor==="" || f_thisColor==null) return

     this.foregroundColor=f_thisColor
     this.ColumnHeaders.setForegroundColor(f_thisColor)
     this.RowSet.setForegroundColor(f_thisColor)
     this.Footer.setForegroundColor(f_thisColor)
   }

   function getForegroundColor(){
     return this.foregroundColor
   }

   function setFont(f_fontName, f_fontStyle ,f_fontSize){
     if(f_fontName!=""  || f_fontName!=null) this.setFontName(f_fontName)
     if(f_fontStyle!="" || f_fontStyle!=null) this.setFontStyle(f_fontStyle)
     if(f_fontSize!=""  || f_fontSize!=null) this.setFontSize(f_fontSize)
   }

   function setFontName(f_thisName){
     if(f_thisName==="" || f_thisName==null) return
     this.fontName=f_thisName
     this.ColumnHeaders.setFontName(f_thisName)
     this.RowSet.setFontName(f_thisName)
     this.Footer.setFontName(f_thisName)
   }

   function getFontName(){
     return this.fontName
   }

   function setFontStyle(f_thisStyle){
     if(f_thisStyle==="" || f_thisStyle==null) return
     this.fontStyle=f_thisStyle
     this.ColumnHeaders.setFontStyle(f_thisStyle)
     this.RowSet.setFontStyle(f_thisStyle)
     this.Footer.setFontStyle(f_thisStyle)
   }

   function getFontStyle(){
     return this.fontStyle
   }

   function setFontSize(f_thisSize){
     if(f_thisSize==="" || f_thisSize==null) return
     this.fontSize=f_thisSize
     this.ColumnHeaders.setFontSize(f_thisSize)
     this.RowSet.setFontSize(f_thisSize)
     this.Footer.setFontSize(f_thisSize)
   }

   function getFontSize(){
     return this.fontSize
   }

   function setAlignment(f_thisAlign){
     if(f_thisAlign==="" || f_thisAlign==null) return
     this.align=f_thisAlign
     this.ColumnHeaders.setAlignment(f_thisAlign)
     this.RowSet.setAlignment(f_thisAlign)
     this.Footer.setAlignment(f_thisAlign)
   }

   function getAlignment(){
     return this.align
   }

   function setAltColor(f_state, f_oddColor, f_evenColor){
     if(f_oddColor==="" || f_oddColor==null) f_oddColor=this.oddColor
     if(f_evenColor==="" || f_evenColor==null) f_evenColor=this.evenColor

	 this.setOddColor(f_oddColor)
	 this.setEvenColor(f_evenColor)
	 this.altColor=f_state

     this.RowSet.setAltColor(f_state)
   }

   function setEvenColor(f_thisColor){
     if(f_thisColor==="" || f_thisColor==null){
       if(this.evenColor != "" && this.evenColor != null) return
       f_thisColor=DG_DEFAULT_EVEN
     }
     this.evenColor=f_thisColor
   }

   function setOddColor(f_thisColor){
     if(f_thisColor==="" || f_thisColor==null){
       if(this.oddColor != "" && this.oddColor != null) return
       f_thisColor=DG_DEFAULT_ODD
     }

     this.oddColor=f_thisColor
   }

   function isAltColor(){
	 return this.altColor
   }

   function getEvenColor(){
     return this.evenColor
   }

   function getOddColor(){
	 return this.oddColor
   }

 }

 function ColumnHeaders(f_table){

  /* ~~~~~~~~~~~~~ Member Variables ~~~~~~~~~~~~~ */
   this.parent  		= f_table
   this.object  		= this
   this.tHead 			= null
   this.row     		= null;
   this.arrColumns		= new Array()
   this.arrcolumns		= this.arrColumns

   this.backgroundImage 	= ""
   this.backgroundColor		= "";
   this.foregroundColor		= "";
   this.fontName		= "";
   this.fontSize		= "11";
   this.fontStyle		= DG_NONE;
   this.align			= DG_CENTER
   this.raised			= false

  /* ~~~~~~~~~~~~~ Member Functions ~~~~~~~~~~~~~ */

   this.getParent		= getParent
   this.getTable		= getParent
   this.getGrid			= getParent
   this.getObject		= getObject
   this.getObjectID		= getObjectID

   this.add			= addColumn
   this.addColumn		= addColumn
   this.getColumnDetails	= getColumnDetails
   this.getHeaderDetails	= getHeaderDetails
   this.getColumnCount		= getColumnCount
   this.getLength 		= getColumnCount
   this.Columns			= Columns
   this.columns			= Columns
   this.getTHead		= getTHead
   this.getRow			= getRow
   this.deleteColumn		= deleteColumn

   this.setBackgroundImage  	= setBackgroundImage
   this.getBackgroundImage  	= getBackgroundImage
   this.setBackgroundColor  	= setBackgroundColor
   this.getBackgroundColor  	= getBackgroundColor
   this.setForegroundColor  	= setForegroundColor
   this.getForegroundColor  	= getForegroundColor
   this.setFont   		= setFont
   this.setFontName		= setFontName
   this.getFontName		= getFontName
   this.setFontSize		= setFontSize
   this.getFontSize		= getFontSize
   this.setFontStyle   	= setFontStyle
   this.getFontStyle   	= getFontStyle
   this.setAlignment   	= setAlignment
   this.getAlignment   	= getAlignment
   this.setRaised		= setRaised
   this.isRaised		= isRaised

   function getParent(){
     return this.parent
   }

   function getObject(){
     return this.object
   }

   function getObjectID(){
    return DG_COLUMNHEADERS
   }

   function addColumn(f_index, f_Key, f_ColumnName, f_ColumnIcon, f_Width, f_Alignment, f_ColumnType, f_DefaultValue, f_Icon, f_Events, f_Style){
	 if(f_index==null || f_index==="") f_index=-1
	 else f_index--
	 if(isError(f_index>this.getColumnCount(),"Argument 'columnIndex' is greater than available columns - columnHeaders.add(columnIndex, key, columnName, columnIcon, width, columnType, defaultValue, alignment, icon, events, style)")) return
	 t_StopAtCol=(f_index<0)?this.getColumnCount():f_index;
	 t_hidArr=new Array()
	 for(var t_ci=1;t_ci<=t_StopAtCol;t_ci++){
	   t_curCol=this.Columns(t_ci)
	   if(!t_curCol.isVisible()){
		t_hidArr[t_hidArr.length]=t_ci
		t_curCol.setVisible(true)
	   }
     }
     t_col = new Column(this, f_index, f_Key, f_ColumnName, f_ColumnIcon, f_Width, f_Alignment, f_ColumnType, f_DefaultValue, f_Icon, f_Events, f_Style)
     for(var t_i=0;t_i<t_hidArr.length;t_i++){ this.Columns(t_hidArr[t_i]).setVisible(false) }

     addElement(this.arrColumns,t_col,f_index)
     t_colLen = this.arrColumns.length
     this.parent.object.cols = t_colLen
     this.parent.Footer.setColspan(t_colLen)
     if(!this.parent.Footer.isVisible())this.parent.Footer.setVisible(true)
     return t_col
   }

   function getColumnDetails(f_colIndex){
     if(isError(f_colIndex==="" || f_colIndex==null,"Argument 'columnIndex' expected for function - columnHeaders.getColumnDetails(columnIndex)")) return
     if(isError(isNaN(f_colIndex),"Invalid Argument " + f_colIndex + " passed for columnHeaders.getColumnDetails(columnIndex)")) return
     if(isError(f_colIndex<0 || f_colIndex>this.arrColumns.length ,"columnHeaders.getColumnDetails(columnIndex) : Specified column does not exist.")) return
     t_arr=this.arrColumns[f_colIndex].getColumnDetails()
     return t_arr
   }

   function getHeaderDetails(){
     t_arr=new Array()
     for(var i=0;i<this.arrColumns.length;i++){
       t_arr[i]=new Array()
       t_arr[i]=this.arrColumns[i].getColumnDetails()
     }
     return t_arr
   }

   function getColumnCount(){
     return this.arrColumns.length
   }

   function Columns(f_colIndex){
     if(isError(f_colIndex==="" || f_colIndex==null,"Argument 'columnIndex' expected for function - columnHeaders.Columns(columnIndex)")) return
     if(isError(f_colIndex<1 || f_colIndex>this.arrColumns.length ,"columnHeaders.Columns(columnIndex) : Specified column does not exist.")) return

     if(typeof(f_colIndex)=="string"){
      for(var i=0;i<this.arrColumns.length;i++){
        if(this.arrColumns[i].key==f_colIndex){
          return this.arrColumns[i]
        }
      }
     }
     else{
      return this.arrColumns[f_colIndex-1]
     }
   }

   function getTHead(){
     return this.tHead
   }

   function getRow(){
     return this.row
   }

   function deleteColumn(f_colIndex){
     if(isError(f_colIndex==="" || f_colIndex==null,"Argument 'columnIndex' expected for function - columnHeaders.deleteColumn(columnIndex)")) return
     if(isError(isNaN(f_colIndex),"Invalid Argument " + f_colIndex + " passed for columnHeaders.deleteColumn(columnIndex)")) return
     if(isError(f_colIndex<=0 || f_colIndex>this.arrColumns.length ,"columnHeaders.deleteColumn(columnIndex) : Specified column does not exist.")) return

     if(f_colIndex==null || f_colIndex == "" || f_colIndex==-1) f_colIndex = -1
     else f_colIndex--
     t_table=this.parent
     var t_hidArr=new Array()

     t_colIndexPlusOne=(f_colIndex+1)
     t_rowCount=t_table.getRowCount()

     for(var t_ri=1;t_ri<=t_rowCount;t_ri++){
       t_row=t_table.RowSet.Row(t_ri)
       for(var t_ci=1;t_ci<=t_colIndexPlusOne;t_ci++){
         var t_Col=this.Columns(t_ci)

         if(!t_Col.isVisible()){
           if(t_ci!=t_colIndexPlusOne) t_hidArr[t_hidArr.length]=t_ci
           t_Col.setVisible(true)
         }

         if(t_ci==t_colIndexPlusOne){
           if(t_ri==t_rowCount){ // delete the Header
           	 this.row.deleteCell(f_colIndex)
		   	 deleteElement(this.arrColumns,f_colIndex)
		   }
           deleteElement(t_row.arrCells,f_colIndex)
           t_row.object.deleteCell(f_colIndex)
           break
         }
       }
     }

     for(var t_i=0;t_i<t_hidArr.length;t_i++){this.Columns(t_hidArr[t_i]).setVisible(false) }
     if(this.arrColumns.length>0) this.parent.Footer.setColspan(this.arrColumns.length)
     else
      this.parent.Footer.setVisible(false)
     t_table.object.cols--
   }

   function setBackgroundImage(f_image){
     if(f_image==="" || f_image==null){
       this.backgroundImage=""
     }
     else{
       this.tHead.style.backgroundImage="url('" + f_image + "')"
       this.backgroundImage=f_image
     }
   }

   function getBackgroundImage(){
     return this.backgroundImage
   }

   function setBackgroundColor(f_thisColor){
     if(f_thisColor==="" || f_thisColor==null){
         if(this.parent.backgroundColor != "" && this.parent.backgroundColor != null) f_thisColor=this.parent.backgroundColor
         else return
     }

     this.backgroundColor=f_thisColor
     var arrLen=this.arrColumns.length
     for(var i=0;i<arrLen;i++){
       this.arrColumns[i].setBackgroundColor(f_thisColor,DG_APPLY_CELL)
     }
   }

   function getBackgroundColor(){
     return this.backgroundColor
   }

   function setForegroundColor(f_thisColor){
     if(f_thisColor==="" || f_thisColor==null){
         if(this.parent.foregroundColor != "" && this.parent.foregroundColor != null) f_thisColor=this.parent.foregroundColor
         else return
     }

     this.foregroundColor=f_thisColor

     var arrLen=this.arrColumns.length
     for(var i=0;i<arrLen;i++){
       this.arrColumns[i].setForegroundColor(f_thisColor)
     }
   }

   function getForegroundColor(){
     return this.foregroundColor
   }

   function setFont(f_fontName, f_fontStyle ,f_fontSize){
     if(f_fontName!=""  || f_fontName!=null) this.setFontName(f_fontName)
     if(f_fontStyle!="" || f_fontStyle!=null) this.setFontStyle(f_fontStyle)
     if(f_fontSize!=""  || f_fontSize!=null) this.setFontSize(f_fontSize)
   }

   function setFontName(f_thisName){
     if(f_thisName==="" || f_thisName==null){
         if(this.parent.fontName != "" && this.parent.fontName != null) f_thisName=this.parent.fontName
         else return
     }

     this.fontName=f_thisName
     var arrLen=this.arrColumns.length
     for(var i=0;i<arrLen;i++){
       this.arrColumns[i].setFontName(f_thisName)
     }
   }

   function getFontName(){
     return this.fontName
   }

   function setFontStyle(f_thisStyle){
     if(f_thisStyle==="" || f_thisStyle==null){
         if(this.parent.fontStyle != "" && this.parent.fontStyle != null) f_thisStyle=this.parent.fontStyle
         else return
     }

     this.fontStyle=f_thisStyle
     var arrLen=this.arrColumns.length
     for(var i=0;i<arrLen;i++){
       this.arrColumns[i].setFontStyle(f_thisStyle)
     }
   }

   function getFontStyle(){
     return this.fontStyle
   }

   function setFontSize(f_thisSize){
     if(f_thisSize==="" || f_thisSize==null){
         if(this.parent.fontSize != "" && this.parent.fontSize != null) f_thisSize=this.parent.fontSize
         else return
     }

     this.fontSize=f_thisSize
     var arrLen=this.arrColumns.length
     for(var i=0;i<arrLen;i++){
       this.arrColumns[i].setFontSize(f_thisSize)
     }
   }

   function getFontSize(){
     return this.fontSize
   }

   function setAlignment(f_thisAlign){
     if(f_thisAlign==="" || f_thisAlign==null){
         if(this.parent.align != "" && this.parent.align != null) f_thisAlign=this.parent.align
         else return
     }

     this.align=f_thisAlign
     var arrLen=this.arrColumns.length

     for(var i=0;i<arrLen;i++){
       this.arrColumns[i].setAlignment(f_thisAlign)
     }
   }

   function getAlignment(){
     return this.align
   }

   function setRaised(f_raiseStatus){
     var arrLen=this.arrColumns.length
     this.raised=f_raiseStatus

     for(var i=0;i<arrLen;i++){
       this.arrColumns[i].setRaised(f_raiseStatus)
     }
   }

   function isRaised(){
     return this.raised
   }

  /* ~~~~~~~~~~~~~ Constructor Code ~~~~~~~~~~~~~ */
   this.tHead = f_table.object.createTHead();
   this.row = this.tHead.insertRow()

   this.setBackgroundColor(this.backgroundColor)
   this.setForegroundColor(this.foregroundColor)
   this.setFontName(this.fontName)
   this.setFontSize(this.fontSize)
   this.setFontStyle(this.fontStyle)
   this.setAlignment(this.align)
   this.setRaised(this.raised)

 }

 function Column(f_ColumnHeaders, f_index, f_Key, f_ColumnName, f_ColumnIcon, f_Width, f_Alignment, f_ColumnType, f_DefaultValue, f_Icon, f_Events, f_Style){


 /* ~~~~~~~~~~~~~ Member Variables ~~~~~~~~~~~~~ */
   this.parent			= f_ColumnHeaders
   this.object			= this;
   this.key				= null
   this.columnName		= ""
   this.columnIcon		= ""
   this.width			= ""
   this.columnType		= DG_LABEL
   this.defaultValue		= ""
   this.events			= ""
   this.style			= ""
   this.cell			= null
   this.icon			= ""
   this.colNum			= null
   this.visible			= true
   this.cellElement		= null

   this.backgroundColor	= "";
   this.foregroundColor	= "";
   this.fontName		= "";
   this.fontSize		= "";
   this.fontStyle		= "";
   this.align			= "";

 /* ~~~~~~~~~~~~~ Member Functions ~~~~~~~~~~~~~ */
   this.getParent 		= getParent
   this.getColumnHeaders	= getParent
   this.getObject	    	= getObject
   this.getObjectID	    	= getObjectID

   this.getColumnDetails	= getColumnDetails
   this.setKey			= setKey
   this.getKey			= getKey
   this.getColumnName		= getColumnName
   this.setColumnName		= setColumnName
   this.setColumnIcon		= setColumnIcon
   this.getColumnIcon		= getColumnIcon
   this.setWidth		= setWidth
   this.getWidth		= getWidth
   this.getColumnType		= getColumnType
   this.getDefaultValue		= getDefaultValue
   this.getEvents		= getEvents
   this.getStyle		= getStyle
   this.getCell			= getCell
   this.getIcon			= getIcon
   this.getColNum		= getColNum
   this.deleteColumn		= deleteColumn
   this.setVisible		= setVisible
   this.isVisible		= isVisible
   this.getColumnIndex		= getColumnIndex
   this.getCellElement  	= getCellElement

   this.setBackgroundColor  = setBackgroundColor
   this.getBackgroundColor  = getBackgroundColor
   this.setForegroundColor  = setForegroundColor
   this.getForegroundColor  = getForegroundColor
   this.setFont   		    = setFont
   this.setFontName		    = setFontName
   this.getFontName		    = getFontName
   this.setFontSize		    = setFontSize
   this.getFontSize		    = getFontSize
   this.setFontStyle	    = setFontStyle
   this.getFontStyle	    = getFontStyle
   this.setAlignment	    = setAlignment
   this.getAlignment	    = getAlignment
   this.setRaised		    = setRaised
   this.isRaised		    = isRaised

   function getParent(){
     return this.parent
   }

   function getObject(){
	 return this.object
   }

   function getObjectID(){
    return DG_COLUMN
   }

   function getColumnDetails(){
     t_tarr=new Array()
     t_tarr[DG_POS_KEY]			=this.key
     t_tarr[DG_POS_COLUMNNAME]	 	=this.columnName
     t_tarr[DG_POS_COLUMNICON]	 	=this.columnIcon
     t_tarr[DG_POS_WIDTH]			=this.width
     t_tarr[DG_POS_ALIGNMENT]		=this.align
     t_tarr[DG_POS_COLUMNTYPE]		=this.columnType
     t_tarr[DG_POS_DEFAULTVALUE]	=this.defaultValue
     t_tarr[DG_POS_EVENTS]			=this.events
     t_tarr[DG_POS_STYLE]			=this.style
     t_tarr[DG_POS_ICON]			=this.icon
     t_tarr[DG_POS_COLNUM]			=this.colNum
     t_tarr[DG_POS_VISIBLE]		=this.visible
     return t_tarr
   }

   function setKey(f_key){
     if(isError(f_key==="" || f_key==null,"Argument 'key' expected for function - Column.setKey(key)")) return
     var arr=this.parent.arrColumns
     for(var i=0;i<arr.length;i++){
       if(arr[i].key==f_key){
         alert("ERROR: Key already exists")
         return
       }
     }

     this.key=f_key
   }

   function getKey(){
     return this.key
   }

   function setColumnName(f_Name){
   	 if(isError(f_Name==="" || f_Name==null,"Argument 'ColumnName' expected for function - Column.setColumnName(ColumnName)")) return
     this.columnName=f_Name
     this.cellElement.value=f_Name
   }

   function getColumnName(){
     return this.columnName
   }

   function setColumnIcon(f_Image){
   	 if(isError(f_Image==="" || f_Image==null,"Argument 'columnIcon' expected for function - Column.setColumnIcon(columnIcon)")) return
     imgObj=eval("document.all.DG_COL__" + this.colNum + "__img")
     this.columnIcon=f_Image
	 imgObj.src=f_Image
	 imgObj.style.display="inline"
   }

   function getColumnIcon(){
     return this.columnIcon
   }

   function setWidth(f_width){
     if(isError(f_width==="" || f_width==null,"Argument 'columnWidth' expected for function - Column.setWidth(columnWidth)")) return
     this.cellElement.style.width=f_width
     this.cell.style.width=f_width
     this.width=f_width
   }

   function getWidth(){
     return this.width
   }

   function getColumnType(){
     return this.columnType
   }

   function getDefaultValue(){
     return this.defaultValue
   }

   function getAlignment(){
     return this.align
   }

   function getEvents(){
     return this.events
   }

   function getStyle(){
     return this.style
   }

   function getCell(){
     return this.cell
   }

   function getIcon(){
     return this.icon
   }

   function getColNum(){
     return this.colNum
   }

   function setVisible(f_state){
  	 if(isError(f_state==="" || f_state==null,"Argument 'state' expected for function - Column.setVisible(state)")) return
     displayState="none"
     if(f_state) displayState="block"
     this.visible=f_state
     this.cell.style.display=displayState

     trows=this.parent.parent.RowSet.arrItems
     cindex=this.getColumnIndex()
     for(var i=0;i<trows.length;i++){
       trows[i].Cells(cindex).setVisible(f_state)
     }
   }

   function isVisible(){
     return this.visible
   }

   function getColumnIndex(){
     return this.cell.cellIndex + 1
   }

   function deleteColumn(){
     this.parent.deleteColumn(this.getColumnIndex())
   }

   function getCellElement(){
     return this.cellElement
   }

   function parseEvents(f_events){
     var lSpace=/^\s*/g
     var rSpace=/\s*$/g
     var space=/\s/g;
     var spaces=/\s*/g
     var delimSpace=/\s*\;\s*/

     var newEvent=""
     var delimiter="\""
     var events = f_events
     var events=events.replace(lSpace,"").replace(rSpace,"").replace(delimSpace,";")
     t_ELCase=new String(events).toLowerCase()
     t_onFocusPos=t_ELCase.indexOf("onfocus")

     if(t_onFocusPos>=0){
			var eventExp=/onfocus[\w|\W]*\s/gi
			eventExp.multiLine=true
			newEvent=newEvent.replace(eventExp,"")
			s=t_ELCase.indexOf("=",t_onFocusPos)
			for(startPos=s;t_ELCase.charAt(startPos)<'a' || t_ELCase.charAt(startPos)>'z';startPos++){};
			delimiter=t_ELCase.substring(s+1,startPos).replace(space,'')

			if(delimiter!=""){ // determine the End Position of the Events
				   endPos = t_ELCase.indexOf(delimiter,startPos)
			}else{
				   delimiter="\""
				   nextEq = t_ELCase.indexOf("=",startPos)

				   if(nextEq<0)
						 endPos=t_ELCase.length
				   else
						 endPos = t_ELCase.lastIndexOf(" ",nextEq-2)
			}
			t_fEvent=";" + events.substring(startPos,endPos)

			events=events.substring(0,t_onFocusPos)  + events.substring(endPos+1)
			t_ELCase=t_ELCase.substring(0,t_onFocusPos)  + t_ELCase.substring(endPos+1)

			newEvent = newEvent + " onFocus=" + delimiter + "setAsCurrent(this)" + t_fEvent + delimiter + " "
     }
     else{
			newEvent = newEvent + " " + " onFocus=\"setAsCurrent(this)\""
     }

     t_onKeyDownPos=t_ELCase.indexOf("onkeydown")
     if(t_onKeyDownPos>=0){
	        var eventExp=/onkeydown[\w|\W]*\s/gi
	        eventExp.multiLine=true
	        newEvent=newEvent.replace(eventExp,"")

		 	s=t_ELCase.indexOf("=",t_onKeyDownPos)
	        for(startPos=s;t_ELCase.charAt(startPos)<'a' || t_ELCase.charAt(startPos)>'z';startPos++){};

		    delimiter=t_ELCase.substring(s+1,startPos).replace(space,'')
			if(delimiter!=""){ // determine the end Position of the events
		   		    endPos = t_ELCase.indexOf(delimiter,startPos)
			}else{
		   		    nextEq = t_ELCase.indexOf("=",startPos)
		   		    delimiter="\""
		   		    if(nextEq<0)
		   		   		endPos=t_ELCase.length
		   			else
		     			endPos = t_ELCase.lastIndexOf(" ",nextEq-2)
			}

			t_fEvent=";" + events.substring(startPos,endPos)





			var NumericControl=(f_ColumnType==DG_NUMERIC)?";return isNumeric()":""
			events=events.substring(0,t_onKeyDownPos)  + events.substring(endPos+1)
			t_ELCase=t_ELCase.substring(0,t_onKeyDownPos)  + t_ELCase.substring(endPos+1)
			newEvent = newEvent + " onKeyDown=" + delimiter + "moveToCell(this)" + NumericControl + t_fEvent + delimiter + " "
     }else{

			var NumericControl=(f_ColumnType==DG_NUMERIC)?";return isNumeric();":""
			newEvent = newEvent + " " + " onKeyDown=\"moveToCell(this)" + NumericControl + "\""
     }

     if(f_ColumnType==DG_CHECKBOX || f_ColumnType==DG_BOOLEAN || f_ColumnType==DG_RADIO){
       t_onPropChPos=t_ELCase.indexOf("onpropertychange")
       if(t_onPropChPos>=0){
       	    var eventExp=/onpropertychange[\w|\W]*\s/gi
       	    newEvent=newEvent.replace(eventExp,"")
	 	    s=t_ELCase.indexOf("=",t_onPropChPos)
            for(startPos=s;t_ELCase.charAt(startPos)<'a' || t_ELCase.charAt(startPos)>'z';startPos++){};

            delimiter=t_ELCase.substring(s+1,startPos).replace(space,'')

	  	    if(delimiter!=""){ // determine the end Position of the events
	  			    endPos = t_ELCase.indexOf(delimiter,startPos)
			}else{
		     	    nextEq = t_ELCase.indexOf("=",startPos)
		     	    delimiter="\""
		     		if(nextEq<0)
		     			  endPos=t_ELCase.length
				    else
		       			  endPos = t_ELCase.lastIndexOf(" ",nextEq-2)
		    }
		    t_fEvent=";" + events.substring(startPos,endPos)

		    events=events.substring(0,t_onPropChPos)  + events.substring(endPos+1)
		    t_ELCase=t_ELCase.substring(0,t_onPropChPos)  + t_ELCase.substring(endPos+1)
		  	newEvent = newEvent + " onPropertyChange=" + delimiter + "valueChanged(this,\"" + f_ColumnType + "\")" + t_fEvent + delimiter + " "
       }else{
	   	    newEvent = newEvent + " " + " onPropertyChange=valueChanged(this,\"" + f_ColumnType + "\")"
       }
     }

	 newEvent=newEvent + " " + events // Adding the Extra Scripts provided by the user
     return newEvent
   }

   function parseStyle(f_style){
     var spaces=/\s+/g
     var style=f_style

     style=style.replace(spaces,"")

     newStyle=""
	 if(style==="") return ""
     if(style.indexOf("class")!=0 && style.indexOf("style")!=0){
		if(style.indexOf(":")>=0){
			newStyle = "style=" + style + ""
		}
		else{
			newStyle = "class=" + style + ""
		}
     }
     else{
     	newStyle=style
     }
     return newStyle
   }

   function setBackgroundColor(f_thisColor,f_entireColumn){
     t_applyAll=true
     if(f_thisColor==="" || f_thisColor==null){
         if(this.parent.backgroundColor != "" && this.parent.backgroundColor != null) f_thisColor=this.parent.backgroundColor
         else return
     }
     this.backgroundColor=f_thisColor
     this.cell.style.backgroundColor=f_thisColor

     if(f_entireColumn==false) t_applyAll=false
     if(!t_applyAll) return // stop here if only the header cell has to be colored

     var colIndex=this.getColumnIndex()
     var tab=this.parent.parent
     var numrows=tab.getRowCount()
     for(var t=1;t<=numrows;t++){
       tab.getCell(t,colIndex).setBackgroundColor(f_thisColor)
     }
   }

   function getBackgroundColor(){
     return this.backgroundColor
   }

   function setForegroundColor(f_thisColor,f_entireColumn){
     t_applyAll=true
     if(f_thisColor==="" || f_thisColor==null){
         if(this.parent.foregroundColor != "" && this.parent.foregroundColor != null) f_thisColor=this.parent.foregroundColor
         else return
     }
     this.foregroundColor=f_thisColor
     this.cellElement.style.color=f_thisColor

     if(f_entireColumn==false) t_applyAll=false
     if(!t_applyAll)return // stop here if only the header cell has to be colored

     var colIndex=this.getColumnIndex()
     var tab=this.parent.parent
     var numrows=tab.getRowCount()
     for(var t=1;t<=numrows;t++){
       tab.getCell(t,colIndex).setForegroundColor(f_thisColor)
     }
   }

   function getForegroundColor(){
     return this.foregroundColor
   }

   function setFont(f_fontName, f_fontStyle ,f_fontSize, f_entireColumn){
     if(f_fontName!=""  || f_fontName!=null) this.setFontName(f_fontName,f_entireColumn)
     if(f_fontStyle!="" || f_fontStyle!=null) this.setFontStyle(f_fontStyle,f_entireColumn)
     if(f_fontSize!=""  || f_fontSize!=null) this.setFontSize(f_fontSize,f_entireColumn)
   }

   function setFontName(f_thisName,f_entireColumn){
     t_applyAll=true
     if(f_thisName==="" || f_thisName==null){
         if(this.parent.fontName != "" && this.parent.fontName != null) f_thisName=this.parent.fontName
         else return
     }
     this.fontName=f_thisName
     this.cellElement.style.fontFamily=f_thisName

     if(f_entireColumn==false) t_applyAll=false
     if(!t_applyAll)return // stop here if only the header cell has to be colored

     var colIndex=this.getColumnIndex()
     var tab=this.parent.parent
     var numrows=tab.getRowCount()
     for(var t=1;t<=numrows;t++){
       tab.getCell(t,colIndex).setFontName(f_thisName)
     }
   }

   function getFontName(){
     return this.fontName
   }

   function setFontStyle(f_thisStyle,f_entireColumn){
     t_applyAll=true
     if(f_thisStyle==="" || f_thisStyle==null){
         if(this.parent.fontStyle != "" && this.parent.fontStyle != null) f_thisStyle=this.parent.fontStyle
         else return
     }
     this.fontStyle=f_thisStyle
     obj=this.cellElement

     switch(f_thisStyle){
      case DG_BOLD:
       obj.style.fontWeight="bold"
       obj.style.fontStyle="normal"
      break;

      case DG_ITALIC:
       obj.style.fontWeight="normal"
       obj.style.fontStyle="italic"

      break;

      case DG_BOLDITALIC:
       obj.style.fontWeight="bold"
       obj.style.fontStyle="italic"
      break;

      case DG_NONE:
       obj.style.fontWeight="normal"
       obj.style.fontStyle="normal"
      break;

     }

     if(f_entireColumn==false) t_applyAll=false
     if(!t_applyAll)return // stop here if only the header cell has to be colored

     var colIndex=this.getColumnIndex()
     var tab=this.parent.parent
     var numrows=tab.getRowCount()
     for(var t=1;t<=numrows;t++){
       tab.getCell(t,colIndex).setFontStyle(f_thisStyle)
     }
   }

   function getFontStyle(){
     return this.fontStyle
   }

   function setFontSize(f_thisSize,f_entireColumn){
     t_applyAll=true
     if(f_thisSize==="" || f_thisSize==null){
         if(this.parent.fontSize != "" && this.parent.fontSize != null) f_thisSize=this.parent.fontSize
         else return
     }
     this.fontSize=f_thisSize
     this.cellElement.style.fontSize=f_thisSize

     if(f_entireColumn==false) t_applyAll=false
     if(!t_applyAll)return // stop here if only the header cell has to be colored

     var colIndex=this.getColumnIndex()
     var tab=this.parent.parent
     var numrows=tab.getRowCount()
     for(var t=1;t<=numrows;t++){
       tab.getCell(t,colIndex).setFontSize(f_thisSize)
     }
   }

   function getFontSize(){
     return this.fontSize
   }

   function setAlignment(f_thisAlign,f_entireColumn){
     t_applyAll=true
     if(f_thisAlign==="" || f_thisAlign==null){
         if(this.parent.align != "" && this.parent.align != null) f_thisAlign=this.parent.align
         else return
     }
     this.align=f_thisAlign
     this.object.style.textAlign="center"
     this.cellElement.style.textAlign=f_thisAlign

     if(f_entireColumn==false) t_applyAll=false
     if(!t_applyAll)return // stop here if only the header cell has to be colored

     var colIndex=this.getColumnIndex()
     var tab=this.parent.parent
     var numrows=tab.getRowCount()
     for(var t=1;t<=numrows;t++){
       tab.getCell(t,colIndex).setAlignment(f_thisAlign)
     }
   }

   function getAlignment(){
     return this.align
   }

   function setRaised(f_state){
     if(isError(f_state==="" || f_state==null,"Argument 'state' expected for function - Column.setRaised(state)")) return
     this.raised=f_state
     this.cell.style.borderWidth="1"
     this.cell.style.borderStyle=f_state?"ridge":"none"
   }

   function isRaised(){
     return this.parent.raised
   }

/* ~~~~~~~~~~~~~ Constructor Code ~~~~~~~~~~~~~ */
   for(var i=0;i<f_ColumnHeaders.arrColumns.length;i++){
     if(f_Key !=="" && f_Key!=null && f_ColumnHeaders.arrColumns[i].getKey()==f_Key){
       alert("ERROR : Grid -> " + f_ColumnHeaders.parent.getName() + " \nKey " + f_Key + " is not unique in the Headers")
       return
     }
   }

   if (f_Key!=null)          this.key=f_Key
   if (f_ColumnName!=null)   this.columnName=f_ColumnName
   if (f_ColumnIcon!=null)   this.columnIcon=f_ColumnIcon
   if (f_Width!=null)        this.width=f_Width
   if (f_Alignment!=null)    this.align=f_Alignment
   if (f_ColumnType!=null)   this.columnType=f_ColumnType
   if (f_DefaultValue!=null) this.defaultValue=f_DefaultValue
   if (f_Icon!=null)         this.icon=f_Icon
   if (f_Events!=null)       this.events=f_Events
   if (f_Style!=null)        this.style=f_Style

   this.events=parseEvents(this.events)
   this.style=parseStyle(this.style)
   this.cell=f_ColumnHeaders.row.insertCell(f_index);
   var t_table=f_ColumnHeaders.parent
   this.colNum=t_table.getPseudoColNum();
   for(var j=1;j<=t_table.getRowCount();j++){
	 row=t_table.RowSet.Row(j)
	 row.addCell(row.pseudoRowNum,(f_index+1),this.getColumnDetails())
   }

   this.cell.style.width = this.width
   if(this.width==0 && this.width!=="")
   this.cell.style.display	=  "none"

   this.setRaised(f_ColumnHeaders.raised)

   t_iconName="t__ico__" + f_ColumnHeaders.columns.length

   t_imageSyn=""
   if(this.columnIcon!="")
    t_imageSyn="&nbsp;<img align='center' valign='bottom' name='DG_COL__" + this.colNum + "__img' id='DG_COL__" + this.colNum + "__img' src='" + this.columnIcon + "' width=16 height=16 >"
   else
    t_imageSyn="&nbsp;<img align='center' valign='bottom' style='display:none' name='DG_COL__" + this.colNum + "__img' id='DG_COL__" + this.colNum + "__img' src='' width=16 height=16 >"

   this.cell.innerHTML = t_imageSyn + "&nbsp;<input type='text' name='DG_COL__" + this.colNum  + "__" + t_table.getName() + "' id='DG_COL__" + this.colNum + "' style='width:85%;background-color:transparent;border-width:0' value='" + this.columnName + "' readonly>"
   this.cellElement=eval("document.all.DG_COL__" + this.colNum + "__" + t_table.getName())

   this.setBackgroundColor(this.backgroundColor,DG_APPLY_CELL)
   this.setForegroundColor(this.foregroundColor,DG_APPLY_CELL)
   this.setFontName(this.fontName,DG_APPLY_CELL)
   this.setFontSize(this.fontSize,DG_APPLY_CELL)
   this.setFontStyle(this.fontStyle,DG_APPLY_CELL)
   this.setAlignment(this.align,DG_APPLY_CELL)
   if(this.width==="" || this.width==null){
     if(this.fontSize==="" || this.fontSize==null) f_size=1
     else f_size=this.fontSize
     this.width=this.columnName.length + " em"
   }
   //this.cellElement.style.width=this.width
 }

 function RowSet(f_table){

 /* ~~~~~~~~~~~~~ Member Variables ~~~~~~~~~~~~~ */
  this.parent=f_table;
  this.object=this;
  this.arrItems=new Array()
  this.arrItems=this.arrItems

  this.backgroundImage="";
  this.backgroundColor="";
  this.foregroundColor="";
  this.fontName="";
  this.fontSize="";
  this.fontStyle=DG_NONE;
  this.align=DG_LEFT

 /* ~~~~~~~~~~~~~ Member Functions ~~~~~~~~~~~~~ */
  this.getParent	= getParent
  this.getTable		= getParent
  this.getGrid		= getParent
  this.getObject	= getObject
  this.getObjectID	= getObjectID

  this.add 		= addRow
  this.addRow 		= addRow
  this.deleteRow	= deleteRow
  this.getLength 	= getLength
  this.Row		= RowItem
  this.row		= RowItem
  this.getRow		= RowItem
  this.getCell		= getCell
  this.getRowCount	= getRowCount

  this.setBackgroundImage = setBackgroundImage
  this.getBackgroundImage = getBackgroundImage
  this.setBackgroundColor = setBackgroundColor
  this.getBackgroundColor = getBackgroundColor
  this.setForegroundColor = setForegroundColor
  this.getForegroundColor = getForegroundColor
  this.setFont   		=	setFont
  this.setFontName		=	setFontName
  this.getFontName		=	getFontName
  this.setFontSize		=	setFontSize
  this.getFontSize		=	getFontSize
  this.setFontStyle		=	setFontStyle
  this.getFontStyle		=	getFontStyle
  this.setAlignment		=	setAlignment
  this.getAlignment		=	getAlignment
  this.setAltColor		=	setAltColor

  function getParent(){
    return this.parent
  }

  function getObject(){
    return this.object
  }

  function getObjectID(){
   return DG_ROWSET
  }

  function addRow(f_pos){
    if(isError(f_pos!=="" && f_pos!=null && isNaN(f_pos),"Invalid Argument " + f_pos + " passed for RowSet.add(rowPosition)")) return
    if(isError(f_pos!=="" && f_pos!=null && f_pos>(this.arrItems.length+1),"Invalid Argument " + f_pos + " passed for RowSet.add(rowPosition). rowPosition exceeds total number of rows")) return

    if(arguments.length==0 || f_pos<0) f_pos=-1
    t_row = new Row(this,f_pos)

    addElement(this.arrItems,t_row,f_pos-1) // -1 coz in the array numbering starts from 0 and not 1
    if(this.parent.isAltColor()){
      if(f_pos==-1) f_pos=this.parent.object.tBodies[0].rows.length
      this.setAltColor(true,f_pos)
    }
    return t_row
  }

  function deleteRow(f_pos){
    if(isError(f_pos!=="" && f_pos!=null && isNaN(f_pos),"Invalid Argument " + f_pos + " passed for RowSet.deleteRow(rowPosition)")) return
    if(arguments.length==0 || f_pos==null || f_pos==="" || f_pos<=0) f_pos=-1
    else f_pos--

    if(isError(f_pos!=="" && f_pos!=null && ((f_pos+1)>this.arrItems.length || this.arrItems.length<=0),"RowSet.deleteRow(rowPosition) : Specified row does not exist")) return

    deleteElement(this.arrItems,f_pos)
    f_table.object.tBodies[0].deleteRow(f_pos)

    if(this.parent.isAltColor()){
      if(f_pos==-1) f_pos=this.parent.object.rows.length-1
      this.setAltColor(true,f_pos)
    }
  }

  function getLength(){
   return this.arrItems.length
  }

  function RowItem(f_rowIndex){
    if(isError(f_rowIndex==="" || f_rowIndex==null,"Argument 'rowIndex' expected for function - RowSet.Items(rowIndex)")) return
    if(isError(isNaN(f_rowIndex) || f_rowIndex<1,"Invalid Argument " + f_rowIndex + " 'rowIndex' passed for RowSet.Items(rowIndex)")) return
    if(isError(f_rowIndex>this.arrItems.length,"RowSet.Items(rowIndex) : Specified row does not exist")) return
    return this.arrItems[f_rowIndex-1]
  }

  function getRowCount(){
    return this.arrItems.length
  }

  function getCell(f_row,f_col){
    if(isError(f_row==="" || f_row==null,"Argument 'rowIndex' expected for function - RowSet.getCell(rowIndex,columnIndex)")) return
    if(isError(f_col==="" || f_col==null,"Argument 'columnIndex' expected for function - RowSet.getCell(rowIndex,columnIndex)")) return
    if(isError(isNaN(f_row) || f_row<1,"Invalid Argument " + f_row + " 'rowIndex' passed for RowSet.getCell(rowIndex,columnIndex)")) return
    if(isError(isNaN(f_col) || f_col<1,"Invalid Argument " + f_col + " 'colIndex' passed for RowSet.getCell(rowIndex,columnIndex)")) return
    if(isError(f_row>this.arrItems.length,"RowSet.getCell(rowIndex,columnIndex) : Specified row does not exist")) return
    if(isError(f_col>this.arrItems[f_row-1].arrCells.length,"RowSet.getCell(rowIndex,columnIndex) : Specified row does not exist")) return
    return this.arrItems[f_row-1].arrCells[f_col-1]
  }

  function setBackgroundImage(f_image){
    if(f_image==="" || f_image==null){
      this.backgroundImage=""
    }
    else{
      this.parent.object.tBodies[0].style.backgroundImage="url('" + f_image + "')"
      this.backgroundImage=f_image
    }
  }

  function getBackgroundImage(){
    return this.backgroundImage
  }

  function setBackgroundColor(f_thisColor){
    if(f_thisColor==="" || f_thisColor==null){
        if(this.parent.backgroundColor !="") f_thisColor=this.parent.backgroundColor
        else return
    }

    this.backgroundColor=f_thisColor
    var arrlen=this.arrItems.length
    for(var i=0;i<arrlen;i++){
      this.arrItems[i].setBackgroundColor(f_thisColor)
    }
  }

  function getBackgroundColor(){
    return this.backgroundColor
  }

  function setForegroundColor(f_thisColor){
     if(f_thisColor==="" || f_thisColor==null){
         if(this.parent.foregroundColor !="") f_thisColor=this.parent.foregroundColor
         else return
     }

    this.foregroundColor=f_thisColor
    var arrlen=this.arrItems.length
    for(var i=0;i<arrlen;i++){
      this.arrItems[i].setForegroundColor(f_thisColor)
    }
  }

  function getForegroundColor(){
    return this.foregroundColor
  }

  function setFont(f_thisName,f_thisStyle,f_thisSize){
    this.fontName=f_thisName
    this.fontStyle=f_thisStyle
    this.fontSize=f_thisSize
    var arrlen=this.arrItems.length
    for(var i=0;i<arrlen;i++){
      this.arrItems[i].setFont(f_thisName,f_thisStyle,f_thisSize)
    }
  }

  function setFontName(f_thisName){
    if(f_thisName==="" || f_thisName==null){
        if(this.parent.fontName !="") f_thisName=this.parent.fontName
        else return
    }

    this.fontName=f_thisName
    var arrlen=this.arrItems.length
    for(var i=0;i<arrlen;i++){
      this.arrItems[i].setFontName(f_thisName)
    }
  }

  function getFontName(){
    return this.fontName
  }

  function setFontStyle(f_thisStyle){
    if(f_thisStyle==="" || f_thisStyle==null){
        if(this.parent.fontStyle !="") f_thisStyle=this.parent.fontStyle
        else return
    }

    this.fontStyle=f_thisStyle
    var arrlen=this.arrItems.length
    for(var i=0;i<arrlen;i++){
      this.arrItems[i].setFontStyle(f_thisStyle)
    }
  }

  function getFontStyle(){
    return this.fontStyle
  }

  function setFontSize(f_thisSize){
    if(f_thisSize==="" || f_thisSize==null){
        if(this.parent.fontSize !="") f_thisSize=this.parent.fontSize
        else return
    }

    this.fontSize=f_thisSize
    var arrlen=this.arrItems.length
    for(var i=0;i<arrlen;i++){
      this.arrItems[i].setFontSize(f_thisSize)
    }
  }

  function getFontSize(){
    return this.fontSize
  }

  function setAlignment(f_thisAlign){
     if(f_thisAlign==="" || f_thisAlign==null){
         if(this.parent.align != "" && this.parent.align != null) f_thisAlign=this.parent.align
         else return
     }

    this.align=f_thisAlign
    for(var i=0;i<this.arrItems.length;i++){
      this.arrItems[i].setAlignment(f_thisAlign)
    }
  }

  function getAlignment(){
    return this.align
  }

  function setAltColor(f_state, f_startFrom){
    if(isError(f_state==="" || f_state==null,"Argument 'state' expected for function - RowSet.setAltColor(state)")) return
    if(f_state){
      var arrlen=this.arrItems.length
      var oc=this.parent.oddColor
      var ec=this.parent.evenColor
      var i=0
      if(f_startFrom != "" && f_startFrom != null) i=(f_startFrom-1)
      t_visRow=0

      for(var j=0;j<arrlen;j++){
        if(j<i){
          if(this.arrItems[j].isVisible()) t_visRow++;
          continue;
        }

        if(this.arrItems[j].isVisible()){
          t_visRow++;
          this.arrItems[j].setBackgroundColor((t_visRow%2==0)?oc:ec);
        }
      }
    }
  }


 /* ~~~~~~~~~~~~~ Constructor Code ~~~~~~~~~~~~~ */
  this.setBackgroundColor(this.backgroundColor)
  this.setForegroundColor(this.foregroundColor)
  this.setFontName(this.fontName)
  this.setFontSize(this.fontSize)
  this.setFontStyle(this.fontStyle)
  this.setAlignment(this.align)

 }


 function Row(f_RowSet,f_pos){

 /* ~~~~~~~~~~~~~ Member Variables ~~~~~~~~~~~~~ */
  this.parent=f_RowSet;
  this.object=null
  this.arrCells=new Array()
  this.arrcells=this.arrCells
  this.pseudoRowNum=null
  this.visible=true
  this.rowCreation=true

  this.backgroundColor="";
  this.foregroundColor="";
  this.fontName="";
  this.fontSize="";
  this.fontStyle=DG_NONE;
  this.align=DG_LEFT


 /* ~~~~~~~~~~~~~ Member Functions ~~~~~~~~~~~~~ */

  this.getParent		= 	getParent
  this.getRowSet		=	getParent
  this.getObject		=	getObject
  this.getObjectID		=	getObjectID

  this.Cells			=	Cells
  this.cells			=	Cells
  this.getCell			=	Cells
  this.getTable			=	getTable
  this.getGrid			=	getTable
  this.deleteRow		=	deleteRow
  this.addCell			=	addCell
  this.getRowIndex		=   	getRowIndex
  this.setVisible		= 	setVisible
  this.isVisible		= 	isVisible

  this.setBackgroundColor 	= 	setBackgroundColor
  this.getBackgroundColor 	= 	getBackgroundColor
  this.setForegroundColor 	= 	setForegroundColor
  this.getForegroundColor 	= 	getForegroundColor
  this.setFont   		=	setFont
  this.setFontName		=	setFontName
  this.getFontName		=	getFontName
  this.setFontSize		=	setFontSize
  this.getFontSize		=	getFontSize
  this.setFontStyle		=	setFontStyle
  this.getFontStyle		=	getFontStyle
  this.setAlignment		=	setAlignment
  this.getAlignment		=	getAlignment

  function getParent(){
    return this.parent
  }

  function getObject(){
    return this.object
  }

  function getObjectID(){
   return DG_ROW
  }

  function Cells(f_colIndex){
    if(isError(f_colIndex==="" || f_colIndex==null,"Argument 'colIndex' expected for function - Row.Cells(columnIndex)")) return
    if(isError(isNaN(f_colIndex) || f_colIndex<1,"Invalid Argument " + f_colIndex + " passed for Row.Cells(columnIndex)")) return
    if(isError(f_colIndex>this.arrCells.length,"Row.Cells(columnIndex) : Specified cell does not exist")) 
    	{
    	alert ("f_in: "+f_colIndex+" arrCell: "+this.arrCells.length)
    	return
    	}
    return this.arrCells[f_colIndex-1]
  }

  function getTable(){
    return this.parent.parent
  }

  function deleteRow(){
    this.parent.deleteRow(this.object.rowIndex)
  }

  function addCell(f_pseudoRowNum, f_colIndex,f_details){
    if(f_colIndex==null || f_colIndex==="") f_colIndex=-1
    addElement(this.arrCells, new Cell(this, f_pseudoRowNum, f_colIndex, f_details ), (f_colIndex-1))
  }

  function getRowIndex(){
    t_arr=this.parent.arrItems
    for(var i=0;i<t_arr.length;i++){
      if(t_arr[i]==this) return ++i
    }
  }

  function setVisible(f_state){
    if(isError(f_state==="" || f_state==null,"Argument 'state' expected for function - Row.setVisible(state)")) return
    displayState="none"
    if(f_state) displayState="block"
    this.visible=f_state
    this.object.style.display=displayState

    t_table=this.parent.parent

    if(t_table.isAltColor()){
      t_rowIndex=this.getRowIndex()
      this.parent.setAltColor(true,t_rowIndex)
    }
  }

  function isVisible(){
    return this.visible
  }

  function setBackgroundColor(f_thisColor){
    if(f_thisColor==="" || f_thisColor==null){
        if(this.parent.backgroundColor !="") f_thisColor=this.parent.backgroundColor
        else return
    }

    this.backgroundColor=f_thisColor
    if(this.rowCreation) return
    var arrlen=this.arrCells.length
    for(var i=0;i<arrlen;i++){
      this.arrCells[i].setBackgroundColor(f_thisColor)
    }
  }

  function getBackgroundColor(){
    return this.backgroundColor
  }

  function setForegroundColor(f_thisColor){
    if(f_thisColor==="" || f_thisColor==null){
        if(this.parent.foregroundColor !="") f_thisColor=this.parent.foregroundColor
        else return
    }

    this.foregroundColor=f_thisColor
    if(this.rowCreation) return
    var arrlen=this.arrCells.length
    for(var i=0;i<arrlen;i++){
      this.arrCells[i].setForegroundColor(f_thisColor)
    }
  }

  function getForegroundColor(){
    return this.foregroundColor
  }

  function setFont(f_thisName,f_thisStyle,f_thisSize){
    this.fontName=f_thisName
    this.fontStyle=f_thisStyle
    this.fontSize=f_thisSize
    if(this.rowCreation) return
    var arrlen=this.arrCells.length
    for(var i=0;i<arrlen;i++){
      this.arrCells[i].setFont(f_thisName,f_thisStyle,f_thisSize)
    }
  }

  function setFontName(f_thisName){
     if(f_thisName==="" || f_thisName==null){
         if(this.parent.fontName !="") f_thisName=this.parent.fontName
         else return
     }

    this.fontName=f_thisName
    if(this.rowCreation) return
    var arrlen=this.arrCells.length
    for(var i=0;i<arrlen;i++){
      this.arrCells[i].setFontName(f_thisName)
    }
  }

  function getFontName(){
    return this.fontName
  }

  function setFontStyle(f_thisStyle){
    if(f_thisStyle==="" || f_thisStyle==null){
        if(this.parent.fontStyle !="") f_thisStyle=this.parent.fontStyle
        else return
    }

    this.fontStyle=f_thisStyle
    if(this.rowCreation) return
    var arrlen=this.arrCells.length
    for(var i=0;i<arrlen;i++){
      this.arrCells[i].setFontStyle(f_thisStyle)
    }
  }

  function getFontStyle(){
    return this.fontStyle
  }

  function setFontSize(f_thisSize){
    if(f_thisSize==="" || f_thisSize==null){
        if(this.parent.fontSize !="") f_thisSize=this.parent.fontSize
        else return
    }

    this.fontSize=f_thisSize
    if(this.rowCreation) return
    var arrlen=this.arrCells.length
    var i=0
    for(var i=0;i<arrlen;i++){
      this.arrCells[i].setFontSize(f_thisSize)
    }
  }

  function getFontSize(){
    return this.fontSize
  }

  function setAlignment(f_thisAlign){
    if(f_thisAlign==="" || f_thisAlign==null){
        if(this.parent.align != "" && this.parent.align != null) f_thisAlign=this.parent.align
        else return
    }

    this.align=f_thisAlign
    if(this.rowCreation) return
    var arrlen=this.arrCells.length
    var i=0
    for(var i=0;i<arrlen;i++){
      this.arrCells[i].setAlignment(f_thisAlign)
    }
  }

  function getAlignment(){
    return this.align
  }


 /* ~~~~~~~~~~~~~ Constructor Code ~~~~~~~~~~~~~ */

  if(f_pos==null || f_pos<=0 || f_pos==="") f_pos=-1

  t_tableObj=f_RowSet.parent
  t_table=t_tableObj.object
  if(f_pos!=-1) f_pos--;
  this.object=t_table.tBodies[0].insertRow(f_pos);

  t_cellDetails=t_tableObj.ColumnHeaders.getHeaderDetails()
  this.pseudoRowNum=t_tableObj.getPseudoRowNum()

  this.rowCreation=true;
  this.setBackgroundColor(this.backgroundColor)
  this.setForegroundColor(this.foregroundColor)
  this.setFontName(this.fontName)
  this.setFontSize(this.fontSize)
  this.setFontStyle(this.fontStyle)
  this.setAlignment(this.align)
  this.rowCreation=false;

  for(var i=0;i<t_table.cols;i++)
   this.arrCells[i]=new Cell(this, this.pseudoRowNum, -1 , t_cellDetails[i])

  for(var i=0;i<t_table.cols;i++)
   if(!this.arrCells[i].visible) this.arrCells[i].setVisible(false)

 }


 function Cell(f_row, f_pseudoRow, f_colIndex, f_CellDetails){

 /* ~~~~~~~~~~~~~ Member Variables ~~~~~~~~~~~~~ */
  this.parent=f_row;
  this.object=null;
  this.variable=null;
  this.auxVariable=null;
  this.textObj=null;
  this.name="";
  this.columnType=DG_LABEL;
  this.icon=null;
  this.visible=true
  this.cellElement=null;
  this.radioLocked=false;
  this.imageWidth=16
  this.imageHeight=16

  this.backgroundColor="";
  this.foregroundColor="";
  this.fontName="";
  this.fontSize="";
  this.fontStyle="";
  this.align="";


 /* ~~~~~~~~~~~~~ Member Functions ~~~~~~~~~~~~~ */
  this.getParent		= 	getParent
  this.getRow			=	getParent
  this.getObject		=	getObject
  this.getObjectID		=	getObjectID

  this.setValue			= 	setValue
  this.getValue			= 	getValue
  this.setText			= 	setText
  this.getText			= 	getText
  this.getRowIndex  		=	getRowIndex
  this.getColumnType		=	getColumnType
  this.getVariable		=	getVariable
  this.getName			= 	getName
  this.getCellElement		=   	getCellElement
  this.setIcon			= 	setIcon
  this.getIcon			=	getIcon
  this.getTable			=   	getTable
  this.getColumnIndex		=	getColumnIndex
  this.getColumn		=	getColumn

  this.setImageWidth		=	setImageWidth
  this.setImageHeight		=	setImageHeight
  this.getImageWidth		=	getImageWidth
  this.getImageHeight		=	getImageHeight

  this.setFocus			= 	setFocus
  this.setVisible		= 	setVisible
  this.isVisible		= 	isVisible

  this.setBackgroundColor 	= 	setBackgroundColor
  this.getBackgroundColor 	= 	getBackgroundColor
  this.setForegroundColor 	= 	setForegroundColor
  this.getForegroundColor 	= 	getForegroundColor
  this.setFont			=	setFont
  this.setFontName		=	setFontName
  this.getFontName		=	getFontName
  this.setFontSize		=	setFontSize
  this.getFontSize		=	getFontSize
  this.setFontStyle		=	setFontStyle
  this.getFontStyle		=	getFontStyle
  this.setAlignment		=	setAlignment
  this.getAlignment		=	getAlignment

  function getParent(){
    return this.parent
  }

  function getObject(){
    return this.object
  }

  function getObjectID(){
   return DG_CELL
  }

  function setValue(f_val){
   var t_obj=this.cellElement
   var t_auxobj=eval("document.all."+this.auxVariable)

   switch(this.columnType){

     case DG_TEXT:
     case DG_TEXTAREA:
     case DG_TEXTX:
     case DG_LABEL:
     case DG_READONLY:
     case DG_PASSWORD:
     case DG_HIDDEN:
     case DG_BUTTON:
     case DG_SUBMIT:
     case DG_RESET:
     case DG_FILE:
     case DG_NUMERIC:
       t_obj.value=f_val
     break;

     case DG_BOOLEAN:
      if(typeof(f_val)=="string" || typeof(f_val)=="boolean"){
        t_checkState=(f_val==true || f_val.toLowerCase()=="true")?true:false
      }

      if(typeof(f_val)=="object"){
        t_checkState=(f_val[0]==true || f_val[0].toLowerCase()=="true")?true:false
      }

      t_obj.checked = t_checkState
      t_auxobj.value=t_checkState
     break;

     case DG_CHECKBOX:
      var t_checkState=false
      if(typeof(f_val)=="string" || typeof(f_val)=="boolean"){
	t_checkState=(f_val==true || f_val.toLowerCase()=="true")?true:false
      }

      if(typeof(f_val)=="object"){
         if(f_val[0]==true || f_val[0].toLowerCase()=="true"){
            t_checkState=true
         }
         t_obj.value=(f_val[1]==null)?"on":f_val[1]
      }
	  t_obj.checked=t_checkState
	  t_auxobj.value = ((t_checkState)?t_obj.value:null)
     break;

     case DG_RADIO:
      var t_colIndex = this.object.cellIndex + 1
      var t_checkState=false
      if(typeof(f_val)=="string" || typeof(f_val)=="boolean"){
        t_checkState=(f_val==true || f_val.toLowerCase()=="true")?true:false
      }

      if(typeof(f_val)=="object"){
         if(f_val[0]==true || f_val[0].toLowerCase()=="true"){
            t_checkState=true
         }
         t_obj.value=(f_val[1]==null)?"on":f_val[1]
         eval("document.all."+this.textObj).innerHTML=(f_val[2]==null)?"":f_val[2]
      }

      var li=this.parent.parent
      var rc=li.getRowCount()
      var t_cell=null

	  for(var i=0;i<rc;i++){
		if(!t_checkState) break
	    t_cell=li.arrItems[i].arrCells[t_colIndex-1]
	    t_cell.radioLocked=true
	    t_cell.cellElement.checked=false
	    eval("document.all."+t_cell.auxVariable).value=null
	    t_cell.radioLocked=false
	  }

      t_obj.checked = t_checkState
	  t_auxobj.value=((t_checkState)?t_obj.value:null)
     break;

     case DG_SELECT:
	  for(var i=0;i<t_obj.options.length;i++){
	     if(t_obj.options[i].value==f_val){
	  		t_obj.options[i].selected=true
	  		break
	  	 }
	  }
     break;

     case DG_IMAGE:
        if(typeof(f_val)=="string")
       	  t_obj.src=f_val

       	if(typeof(f_val)=="object")
       	  t_obj.src=f_val.src
       	  
       	t_auxobj.value=t_obj.src 
     break;
   }
  }

  function getValue(){
   t_obj=eval("document.all."+this.variable)
   switch(this.columnType){
     case DG_TEXT:
     case DG_TEXTAREA:
     case DG_TEXTX:
     case DG_LABEL:
     case DG_READONLY:
     case DG_PASSWORD:
     case DG_HIDDEN:
     case DG_BUTTON:
     case DG_SUBMIT:
     case DG_RESET:
     case DG_FILE:
     case DG_NUMERIC:
       return t_obj.value
     break;

     case DG_CHECKBOX:
       return (t_obj.value=="null" || t_obj.value===null)?null:t_obj.value
     break;

     case DG_BOOLEAN:
       return (t_obj.value=="true" || t_obj.value==true )
     break;

     case DG_RADIO:
       return (t_obj.value=="null" || t_obj.value===null)?null:t_obj.value
     break;

     case DG_SELECT:
       return t_obj.value
     break;

     case DG_IMAGE:
       return t_obj.value
     break;
   }
  }

  function setText(f_val){
    if(this.columnType==DG_TEXTX){   
      t_obj=eval("document.all.TXTX__"+this.variable)
      t_obj.value=f_val
      return
    }
    alert(this.columnType)
    if(this.columnType==DG_CHECKBOX){   
      t_obj=eval("document.all." + this.textObj)
      t_obj.innerHTML=f_val
      return
    }

    if(this.columnType==DG_BOOLEAN){   
      t_obj=eval("document.all." + this.textObj)
      t_obj.innerHTML=f_val
      return
    }

    if(this.columnType==DG_RADIO){   
      t_obj=eval("document.all." + this.textObj)
      t_obj.innerHTML=f_val
      return
    }
      
    this.setValue(f_val)
    return    
  }

  function getText(){
    if(this.columnType!=DG_TEXTX){
      this.getValue()
      return
    }

    t_obj=eval("document.all.TXTX__"+this.variable)
    return t_obj.value
  }

  function getRowIndex(){
    return this.parent.object.rowIndex
  }

  function getColumnIndex(){
    return this.object.cellIndex + 1
  }

  function getVariable(){
    return this.variable
  }

  function getColumnType(){
    return this.columnType
  }

  function getName(){
    return this.name
  }

  function getCellElement(){
   return this.cellElement
  }

  function getIcon(){
    return this.icon
  }

  function setFocus(){
    this.cellElement.focus()
  }

  function getTable(){
    return this.parent.parent.parent
  }

  function setIcon(f_image){
    if(isError(arguments.length==0,"Argument 'Icon' expected for function - Cell.setIcon(Icon)")) return
    imgobj=eval("document.all." + this.variable + "__img");
    imgtd=eval("document.all." + this.variable + "__imgTD");

    if(f_image==="" || f_image==null){
      imgtd.style.display="none"
    }
    else{
      imgtd.style.display="inline"
      imgtd.style.width="20"
	  this.icon.src=f_image
	  imgobj.src=f_image
	}
  }

  function getRowIndex(){
    return this.parent.getRowIndex()
  }

  function getColumn(){
    return this.getTable().getColumn(this.getColumnIndex())
  }

  function setImageWidth(t_imgW){
    if(isError(this.columnType!=DG_IMAGE,"Invalid function call for this Column Type")) return
    this.imageWidth=t_imgW
    this.cellElement.width=t_imgW
  }

  function setImageHeight(t_imgH){
    if(isError(this.columnType!=DG_IMAGE,"Invalid function call for this Column Type")) return
    this.imageHeight=t_imgH
    this.cellElement.height=t_imgH
  }

  function getImageWidth(){
    if(isError(this.columnType!=DG_IMAGE,"Invalid function call for this Column Type")) return
    return this.imageWidth
  }

  function getImageHeight(){
    if(isError(this.columnType!=DG_IMAGE,"Invalid function call for this Column Type")) return
    return this.imageHeight
  }

  function setVisible(f_state){
    if(isError(f_state==="" || f_state==null,"Argument 'state' expected for function - Cell.setVisible(state)")) return
    displayState="none"
    if(f_state) displayState="block"
    this.visible=f_state
    this.object.style.display=displayState
    if(this.textObj!=null) eval("document.all."+this.textObj).style.display=displayState
  }

  function isVisible(){
    return this.visible
  }

  function setBackgroundColor(f_thisColor){
    if(f_thisColor==="" || f_thisColor==null){
        if(this.cellElement.style.backgroundColor!="") f_thisColor=this.cellElement.style.backgroundColor
        else if(this.parent.backgroundColor != "" && this.parent.backgroundColor != null) f_thisColor=this.parent.backgroundColor
        else return
    }

    this.backgroundColor=f_thisColor
    this.object.style.backgroundColor=f_thisColor
    this.cellElement.style.backgroundColor=f_thisColor
    if(this.textObj!=null) eval("document.all."+this.textObj).style.backgroundColor=f_thisColor
  }

  function getBackgroundColor(){
    return this.backgroundColor
  }

  function setForegroundColor(f_thisColor){
    if(f_thisColor==="" || f_thisColor==null){
        if(this.cellElement.style.color!="") f_thisColor=this.cellElement.style.color
        else if(this.parent.foregroundColor!="") f_thisColor=this.parent.foregroundColor
        else return
    }

    this.foregroundColor=f_thisColor
    this.cellElement.style.color=f_thisColor
    if(this.textObj!=null) eval("document.all."+this.textObj).style.color=f_thisColor
  }

  function getForegroundColor(){
    return this.foregroundColor
  }

  function setFont(f_fontName, f_fontStyle ,f_fontSize){
    if(f_fontName!=""  || f_fontName!=null) this.setFontName(f_fontName)
    if(f_fontStyle!="" || f_fontStyle!=null) this.setFontStyle(f_fontStyle)
    if(f_fontSize!=""  || f_fontSize!=null) this.setFontSize(f_fontSize)
  }

  function setFontName(f_thisName){
    if(f_thisName==="" || f_thisName==null){
        if(this.cellElement.style.fontFamily!="") f_thisName=this.cellElement.style.fontFamily
        else if(this.parent.fontName != "") f_thisName=this.parent.fontName
        else return
    }

    obj=this.cellElement
    this.fontName=f_thisName
    obj.style.fontFamily=f_thisName
    if(this.textObj!=null) eval("document.all."+this.textObj).style.fontFamily=f_thisName
  }

  function getFontName(){
    return this.fontName
  }

  function setFontStyle(f_thisStyle){
    if(f_thisStyle==="" || f_thisStyle==null){
        t_weight=this.cellElement.style.fontWeight
        t_style=this.cellElement.style.fontStyle
        if(t_weight!="" || t_style!=""){
          f_thisStyle=DG_NONE
          if(t_weight!=""){
            if(t_weight=="bold" || (!isNaN(t_weight) && parseInt(t_weight)>500)) f_thisStyle=DG_BOLD
          }

          if(t_style!=""){
            if(t_style=="italic") f_thisStyle=(f_thisStyle==DG_NONE)?DG_ITALIC:DG_BOLDITALIC
          }
        }
        else if(this.parent.fontStyle !="") f_thisStyle=this.parent.fontStyle
        else f_thisStyle=DG_NONE
    }

    this.fontStyle=f_thisStyle
    obj=this.cellElement

    switch(f_thisStyle){
     case DG_BOLD:
      obj.style.fontWeight="bold"
      obj.style.fontStyle="normal"
     break;

     case DG_ITALIC:
      obj.style.fontWeight="normal"
      obj.style.fontStyle="italic"

     break;

     case DG_BOLDITALIC:
      obj.style.fontWeight="bold"
      obj.style.fontStyle="italic"
     break;

     case DG_NONE:
      obj.style.fontWeight="normal"
      obj.style.fontStyle="normal"
     break;

    }
    if(this.textObj!=null) eval("document.all."+this.textObj).style.fontStyle=obj.style.fontStyle
    if(this.textObj!=null) eval("document.all."+this.textObj).style.fontWeight=obj.style.fontWeight
  }

  function getFontStyle(){
    return this.fontStyle
  }

  function setFontSize(f_thisSize){
    if(f_thisSize==="" || f_thisSize==null){
        if(this.cellElement.style.fontSize!="") f_thisSize=this.cellElement.style.fontSize
        else if(this.parent.fontSize != "" && this.parent.fontSize != null) f_thisSize=this.parent.fontSize
        else return
    }

    this.fontSize=f_thisSize
    this.cellElement.style.fontSize=f_thisSize
    if(this.textObj!=null) eval("document.all."+this.textObj).style.fontSize=f_thisSize
  }

  function getFontSize(){
    return this.fontSize
  }

  function setAlignment(f_thisAlign){
    if(f_thisAlign==="" || f_thisAlign==null){
        if(this.cellElement.style.textAlign!="") f_thisAlign=this.cellElement.style.textAlign
        else if(this.parent.align != "" && this.parent.align!=null) f_thisAlign=this.parent.align
        else f_thisAlign=DG_LEFT
    }

    this.align=f_thisAlign
    this.cellElement.style.textAlign=f_thisAlign
    if(this.textObj!=null) eval("document.all."+this.textObj).style.textAlign=f_thisAlign
  }

  function getAlignment(){
    return this.align
  }

 /* ~~~~~~~~~~~~~ Constructor Code ~~~~~~~~~~~~~ */
  if(f_colIndex==null || f_colIndex==="" || f_colIndex==-1) f_colIndex=-1
  else f_colIndex--
  this.object=f_row.object.insertCell(f_colIndex);
  t_tableObj=f_row.parent.parent

  if(f_CellDetails==null){
    t_arrDetails=t_tableObj.ColumnHeaders.getColumnDetails(f_row.object.cells.length)
  }
  else{
    t_arrDetails=f_CellDetails
  }
  t_rowNum	=	f_pseudoRow
  t_colNum	=	t_arrDetails[DG_POS_COLNUM]

  t_tableName	= 	t_tableObj.getName()
  this.variable	=	t_tableName + "__R" + t_rowNum + "__C" + t_colNum
  this.auxVariable =    t_tableName + "__R" + t_rowNum + "__C" + t_colNum // This is reset for cells that hold a hidden control as the primary variable
  
  t_defaultValue = t_arrDetails[DG_POS_DEFAULTVALUE]
  t_colWidth	 = t_arrDetails[DG_POS_WIDTH]
  t_align	 = t_arrDetails[DG_POS_ALIGNMENT]
  this.columnType= t_arrDetails[DG_POS_COLUMNTYPE]
  this.icon	 = t_arrDetails[DG_POS_ICON]
  this.visible	 = t_arrDetails[DG_POS_VISIBLE]

  if(this.icon!="")
   t_imageSyn="<table width=100% border=0 cellspacing=0 cellpadding=0><tr><td width=20 id='" + this.variable + "__imgTD'>&nbsp;<img name='" + this.variable + "__img" + "' align='center' valign='bottom' src='" + this.icon + "' width=16 height=16 >"
  else
   t_imageSyn="<table width=100% border=0 cellspacing=0 cellpadding=0><tr><td width=0 id='" + this.variable + "__imgTD' style='display:none'><img name='" + this.variable + "__img" + "' align='center' valign='bottom' src='#' width=16 height=16 >"

  if(this.columnType==DG_TEXT){
   this.object.innerHTML = "</td><td><CENTER><input class='input2' size=25 type='text' name='" + this.variable + "' id='" + this.variable + "' value='" + t_defaultValue + "' " + t_arrDetails[DG_POS_EVENTS] + " " + t_arrDetails[DG_POS_STYLE] + " arrName='" + t_tableName + "'></td></tr></table>"
   this.cellElement=eval("document.all."+this.variable);
  }

  if(this.columnType==DG_TEXTX){
   this.auxVariable=this.variable
   t_dval=""
   t_dtext=""
   if(typeof(t_defaultValue)=="string"){
     t_dtext=t_dval=t_defaultValue
   }

   if(typeof(t_defaultValue)=="object"){
     t_dtext=t_defaultValue[0]
     if(typeof(t_defaultValue[1])=="string") t_dval=t_defaultValue[1]
     else t_dval=t_dtext
   }

   this.object.innerHTML = "</td><td><CENTER><input style='width:95%;border-width:1' type='text' name='TXTX__" + this.variable + "' id='TXTX__" + this.variable + "' value='" + t_dtext + "' " + t_arrDetails[DG_POS_EVENTS] + " " + t_arrDetails[DG_POS_STYLE] + " arrName='" + t_tableName + "'><INPUT type='hidden' name='" + this.variable + "' id='" + this.variable + "' value='" + t_dval + "'></td></tr></table>"
   this.cellElement=eval("document.all."+this.variable);
  }

  if(this.columnType==DG_PASSWORD){
   this.object.innerHTML = "</td><td><CENTER><input style='width:95%;border-width:1' type='password' name='" + this.variable + "' id='" + this.variable + "' value='" + t_defaultValue + "' " + t_arrDetails[DG_POS_EVENTS] + " " + t_arrDetails[DG_POS_STYLE] + " arrName='" + t_tableName + "'></td></tr></table>"
   this.cellElement=eval("document.all."+this.variable);
  }

  if(this.columnType==DG_READONLY){
   this.object.innerHTML = "</td><td><CENTER><input style='border-width:1;color:gray'  type='text' size='20' name='" + this.variable + "' id='" + this.variable + "' value='" + t_defaultValue + "'  " + t_arrDetails[DG_POS_EVENTS] + " " + t_arrDetails[DG_POS_STYLE] + "  arrName='" + t_tableName + "' READONLY></td></tr></table>"
   this.cellElement=eval("document.all."+this.variable);
  }

  if(this.columnType==DG_LABEL){
   this.object.innerHTML = "</td><td><CENTER><input style='width:96%;border-width:0' type='text' name='" + this.variable + "' id='" + this.variable + "' value='" + t_defaultValue + "'  " + t_arrDetails[DG_POS_EVENTS] + " " + t_arrDetails[DG_POS_STYLE] + "  arrName='" + t_tableName + "' READONLY></td></tr></table>"
   this.cellElement=eval("document.all."+this.variable);
  }

  if(this.columnType==DG_BUTTON){
   this.object.innerHTML = "</td><td><CENTER><input style='width:95%;border-width:1;BACKGROUND: #dee7ef;BORDER-LEFT: #ffffff 2px solid;COLOR: #000000;FONT-FAMILY: Tahoma, Arial, Helvetica, Verdana' type='button' name='" + this.variable + "' id='" + this.variable + "' value='" + t_defaultValue + "' " + t_arrDetails[DG_POS_EVENTS] + " " + t_arrDetails[DG_POS_STYLE] + " arrName='" + t_tableName + "'></td></tr></table>"
   this.cellElement=eval("document.all."+this.variable);
  }

  if(this.columnType==DG_SUBMIT){
   this.object.innerHTML = "</td><td><CENTER><input style='width:95%;border-width:1' type='submit' name='" + this.variable + "' id='" + this.variable + "' value='" + t_defaultValue + "' " + t_arrDetails[DG_POS_EVENTS] + " " + t_arrDetails[DG_POS_STYLE] + " arrName='" + t_tableName + "'></td></tr></table>"
   this.cellElement=eval("document.all."+this.variable);
  }

  if(this.columnType==DG_RESET){
   this.object.innerHTML = "</td><td><CENTER><input style='width:95%;border-width:1' type='reset' name='" + this.variable + "' id='" + this.variable + "' value='" + t_defaultValue + "' " + t_arrDetails[DG_POS_EVENTS] + " " + t_arrDetails[DG_POS_STYLE] + " arrName='" + t_tableName + "'></td></tr></table>"
   this.cellElement=eval("document.all."+this.variable);
  }

  if(this.columnType==DG_BOOLEAN){
   t_checkedValue=""
   t_text=""
   t_value=false

   if((typeof(t_defaultValue)=="boolean" && t_defaultValue==true) || (typeof(t_defaultValue)=="string" && t_defaultValue.toLowerCase()=="true")) {
     t_checkedValue="CHECKED";
     t_value=true
   }

   if(typeof(t_defaultValue)=="object"){
     if((typeof(t_defaultValue[0])=="boolean" && t_defaultValue[0]==true) || (typeof(t_defaultValue[0])=="string" && t_defaultValue[0].toLowerCase()=="true")) {
       t_checkedValue="CHECKED";
       t_value=true
     }
     t_text=(t_defaultValue[1]==null || t_defaultValue[1]==="")?"":t_defaultValue[1]
   }
   this.auxVariable = this.variable
   this.textObj="TXT__"+this.variable
   this.object.innerHTML = "</td><td><CENTER><input type='hidden' name='" + this.variable + "' id='" + this.variable + "' value='" + ((t_checkedValue=="CHECKED")?t_value:false) + "'><input type='checkbox' style='border-width:1' name='CHK__" + this.variable + "' id='CHK__" + this.variable + "' value='" + t_value + "' " + t_checkedValue + "  " + t_arrDetails[DG_POS_EVENTS] + " " + t_arrDetails[DG_POS_STYLE] + " arrName='" + t_tableName + "'><span name='" + this.textObj + "' id='" + this.textObj + "' " + t_arrDetails[DG_POS_STYLE] + ">" + t_text + "</span></td></tr></table>"
   this.cellElement=eval("document.all.CHK__"+this.variable)
  }

  if(this.columnType==DG_CHECKBOX){
   t_checkedValue=""
   t_text=""
   t_value="on"

   if((typeof(t_defaultValue)=="boolean" && t_defaultValue==true) || (typeof(t_defaultValue)=="string" && t_defaultValue.toLowerCase()=="true")) {
     t_checkedValue="CHECKED";
     t_value="on"
   }

   if(typeof(t_defaultValue)=="object"){
     if((typeof(t_defaultValue[0])=="boolean" && t_defaultValue[0]==true) || (typeof(t_defaultValue[0])=="string" && t_defaultValue[0].toLowerCase()=="true")) {
       t_checkedValue="CHECKED";
     }
     t_value=(t_defaultValue[1]==null)?"on":t_defaultValue[1];
     t_text=(t_defaultValue[2]==null || t_defaultValue[2]==="")?"":t_defaultValue[2]
   }
   this.auxVariable = this.variable
   this.textObj="TXT__"+this.variable
   this.object.innerHTML = "</td><td><CENTER><input type='hidden' name='" + this.variable + "' id='" + this.variable + "' value='" + ((t_checkedValue=="CHECKED")?t_value:null) + "'><input type='checkbox' style='border-width:1' name='CHK__" + this.variable + "' id='CHK__" + this.variable + "' value='" + t_value + "' " + t_checkedValue + "  " + t_arrDetails[DG_POS_EVENTS] + " " + t_arrDetails[DG_POS_STYLE] + " arrName='" + t_tableName + "'><span name='" + this.textObj + "' id='" + this.textObj + "' " + t_arrDetails[DG_POS_STYLE] + ">" + t_text + "</span></td></tr></table>"
   this.cellElement=eval("document.all.CHK__"+this.variable)
  }

  if(this.columnType==DG_RADIO){
   t_checkedValue=""
   t_text=""
   t_value="on"

   if((typeof(t_defaultValue)=="boolean" && t_defaultValue==true) || (typeof(t_defaultValue)=="string" && t_defaultValue.toLowerCase()=="true")) {
     t_checkedValue="CHECKED";
     t_value="on"
   }

   if(typeof(t_defaultValue)=="object"){
     if((typeof(t_defaultValue[0])=="boolean" && t_defaultValue[0]==true) || (typeof(t_defaultValue[0])=="string" && t_defaultValue[0].toLowerCase()=="true")) {
       t_checkedValue="CHECKED"
     }
     t_value=(t_defaultValue[1]==null)?"on":t_defaultValue[1];
     t_text=(t_defaultValue[2]==null || t_defaultValue[2]==="")?"":t_defaultValue[2]
   }

   this.auxVariable = this.variable
   this.textObj="TXT__"+this.variable
   this.object.innerHTML = "</td><td><CENTER><input type='hidden' name='" + this.variable + "' id='" + this.variable + "' value='" + ((t_checkedValue=='CHECKED')?t_value:null) + "'><input type='radio' style='border-width:1' name='OPT__" + this.variable + "' id='OPT__" + this.variable + "' value='" + t_value + "' " + t_checkedValue + "  " + t_arrDetails[DG_POS_EVENTS] + " " + t_arrDetails[DG_POS_STYLE] + " arrName='" + t_tableName + "'><span name='" + this.textObj + "' id='" + this.textObj + "' " + t_arrDetails[DG_POS_STYLE] + "> " + t_text + "</span></td></tr></table>"
   this.cellElement=eval("document.all.OPT__" + this.variable)
   if(t_checkedValue=="CHECKED") this.cellElement.checked=true // this ensures that the clicked event gets fired (and calls function valueChanged) and only this radio is in the selected state
  }

  if(this.columnType==DG_TEXTAREA){
   this.object.innerHTML = "</td><td>&nbsp;<textarea style='width:95%;border-width:1' name='" + this.variable + "' id='" + this.variable + "' " + t_arrDetails[DG_POS_EVENTS] + " " + t_arrDetails[DG_POS_STYLE] + " arrName='" + t_tableName + "'>" +  t_defaultValue + " </textarea></td></tr></table>"
   this.cellElement=eval("document.all."+this.variable);
  }

  if(this.columnType==DG_HIDDEN){
   this.object.innerHTML = "</td><td><CENTER><input type='hidden' name='" + this.variable + "' id='" + this.variable + "' value='" + t_defaultValue + "'  " + t_arrDetails[DG_POS_EVENTS] + " " + t_arrDetails[DG_POS_STYLE] + " arrName='" + t_tableName + "'></td></tr></table>"
   this.cellElement=eval("document.all."+this.variable);
  }

  if(this.columnType==DG_SELECT){

   t_value=""
   t_text=""
   t_outStr="</td><td><CENTER><select onFocus='onSelectFocus(this);' style='width:95%;border-width:1' name='" + this.variable + "' id='" + this.variable + "' value='" + t_defaultValue + "'  " + t_arrDetails[DG_POS_EVENTS] + " " + t_arrDetails[DG_POS_STYLE] + " arrName='" + t_tableName + "'>"
   t_outStr = t_outStr + "</select></td></tr></table>"
   this.object.innerHTML = t_outStr
   this.cellElement=eval("document.all."+this.variable);

   if(typeof(t_defaultValue)=="string"){
     t_text=t_defaultValue
     t_value=t_text
     var o=new Option(t_text,t_value)
     this.cellElement.options[0]=o
   }

   if(typeof(t_defaultValue)=="object"){
     for(var mi=0;mi<t_defaultValue.length;mi++){
       t_text=t_defaultValue[mi][0]
       t_value=t_text
       if(typeof(t_defaultValue[mi][1])!="undefined" || t_defaultValue[mi][1] != null) t_value=t_defaultValue[mi][1]

        var o=new Option(t_text,t_value)
        this.cellElement.options[mi]=o
     }
   }
  }

  if(this.columnType==DG_FILE){
   this.object.innerHTML = "</td><td><CENTER><input type='file' style='width:95%' class='style_textbox' name='" + this.variable + "' id='" + this.variable + "' value='" + t_defaultValue + "'" + t_arrDetails[DG_POS_EVENTS] + " " + t_arrDetails[DG_POS_STYLE] + " arrName='" + t_tableName + "'></td></tr></table>"
   this.cellElement=eval("document.all."+this.variable);
  }

  if(this.columnType==DG_NUMERIC){
   this.object.innerHTML = "</td><td><CENTER><input  class='input2' size=10  type='text' name='" + this.variable + "' id='" + this.variable + "' value='" + t_defaultValue + "' " + t_arrDetails[DG_POS_EVENTS] + " " + t_arrDetails[DG_POS_STYLE] + " arrName='" + t_tableName + "'></td></tr></table>"
   this.cellElement=eval("document.all."+this.variable);
  }

  if(this.columnType==DG_IMAGE){
   t_width=t_height=16
   t_defaImage=t_defaultValue
   if(typeof(t_defaultValue)=="object"){
     t_defaImage=t_defaultValue[0]
     t_width=typeof(t_defaultValue[1]=="undefined")?16:t_defaultValue[1]
     t_height=typeof(t_defaultValue[2]=="undefined")?16:t_defaultValue[2]
   }

   this.auxVariable = this.variable
   this.object.innerHTML = "</td><td><CENTER>&nbsp;<IMG style='border-width:0' height=" + t_height + " width=" + t_width + " name='IMG__" + this.variable + "' id='IMG__" + this.variable + "' src='" + t_defaImage + "'  " + t_arrDetails[DG_POS_EVENTS] + " " + t_arrDetails[DG_POS_STYLE] + "  arrName='" + t_tableName + "'><input type='hidden' name='" + this.variable + "' id='" + this.variable + "' value='" + t_defaImage + "'></td></tr></table>"
   this.cellElement=eval("document.all.IMG__"+this.variable);
  }

  this.name=this.cellElement.name
  if(this.columnType!=DG_BUTTON && this.columnType!=DG_SUBMIT && this.columnType!=DG_RESET)
  this.setBackgroundColor(this.backgroundColor)

  this.setForegroundColor(this.foregroundColor)
  this.setFontName(this.fontName)
  this.setFontSize(this.fontSize)
  this.setFontStyle(this.fontStyle)
  this.setAlignment(this.align)

 }


 function Footer(f_table){

 /* ~~~~~~~~~~~~~ Member Variables ~~~~~~~~~~~~~ */
   this.parent  		= f_table
   this.object  		= this
   this.tFoot 			= null
   this.row     		= null;
   this.cell     		= null;
   this.colspan			= 0
   this.footerText		= ""
   this.visible			= true

   this.backgroundImage		="";
   this.backgroundColor		="";
   this.foregroundColor		="";
   this.fontName		="";
   this.fontSize		="";
   this.fontStyle		=DG_NONE;
   this.align			=DG_RIGHT

 /* ~~~~~~~~~~~~~ Member Functions ~~~~~~~~~~~~~ */
   this.getParent		= getParent
   this.getTable		= getParent
   this.getGrid			= getParent
   this.getObject		= getObject
   this.getObjectID		= getObjectID

   this.getTFoot		= getTFoot
   this.getRow			= getRow
   this.getCell			= getCell
   this.setColspan		= setColspan
   this.getColspan		= getColspan
   this.setText			= setText
   this.setValue		= setText
   this.getText 		= getText
   this.getValue 		= getText
   this.setVisible		= setVisible
   this.isVisible		= isVisible

   this.setBackgroundImage  = setBackgroundImage
   this.getBackgroundImage  = getBackgroundImage
   this.setBackgroundColor  = setBackgroundColor
   this.getBackgroundColor  = getBackgroundColor
   this.setForegroundColor  = setForegroundColor
   this.getForegroundColor  = getForegroundColor
   this.setFont   		    = setFont
   this.setFontName		    = setFontName
   this.getFontName		    = getFontName
   this.setFontSize		    = setFontSize
   this.getFontSize		    = getFontSize
   this.setFontStyle	    = setFontStyle
   this.getFontStyle	    = getFontStyle
   this.setAlignment	    = setAlignment
   this.getAlignment	    = getAlignment

   function getParent(){
	 return this.parent
   }

   function getObject(){
     return this.object
   }

   function getObjectID(){
    return DG_FOOTER
   }

   function getTFoot(){
     return this.tFoot
   }

   function getRow(){
     return this.row
   }

   function getCell(){
     return this.cell
   }

   function setColspan(f_colspan){
     this.colspan=f_colspan
     this.cell.colSpan=f_colspan
   }

   function getColspan(){
     return this.colspan
   }

   function setText(f_text){
     if(isError(f_text==null,"Argument 'footerText' expected for function - Footer.setText(footerText)")) return
     this.footerText=f_text
     this.cell.innerHTML=f_text
   }

   function getText(){
     return this.footerText
   }

   function setVisible(f_state){
     if(isError(f_state==="" || f_state==null,"Argument 'state' expected for function - Cell.setVisible(state)")) return
     this.visible=f_state
     var visibility="none"
     if(f_state) visibility="inline"
	 this.tFoot.style.display=visibility
   }

   function isVisible(){
     return this.visible
   }

   function setBackgroundImage(f_image){
     if(f_image==="" || f_image==null){
       this.backgroundImage=""
     }
     else{
       this.tFoot.style.backgroundImage="url('" + f_image + "')"
       this.backgroundImage=f_image
     }
   }

   function getBackgroundImage(){
     return this.backgroundImage
   }

   function setBackgroundColor(f_thisColor){
     if(f_thisColor==="" || f_thisColor==null){
         if(this.parent.backgroundColor != "" && this.parent.backgroundColor != null) f_thisColor=this.parent.backgroundColor
         else return
     }

     this.backgroundColor=f_thisColor
     this.row.style.backgroundColor=f_thisColor
   }

   function getBackgroundColor(){
     return this.backgroundColor
   }

   function setForegroundColor(f_thisColor){
     if(f_thisColor==="" || f_thisColor==null){
         if(this.parent.foregroundColor != "" && this.parent.foregroundColor != null) f_thisColor=this.parent.foregroundColor
         else return
     }

     this.foregroundColor=f_thisColor
     this.row.style.color=f_thisColor
   }

   function getForegroundColor(){
     return this.foregroundColor
   }

   function setFont(f_fontName, f_fontStyle ,f_fontSize){
     if(f_fontName!=""  || f_fontName!=null) this.setFontName(f_fontName)
     if(f_fontStyle!="" || f_fontStyle!=null) this.setFontStyle(f_fontStyle)
     if(f_fontSize!=""  || f_fontSize!=null) this.setFontSize(f_fontSize)
   }

   function setFontName(f_thisName){
     if(f_thisName==="" || f_thisName==null){
         if(this.parent.fontName != "" && this.parent.fontName != null) f_thisName=this.parent.fontName
         else return
     }

     this.fontName=f_thisName
     this.cell.style.fontFamily=f_thisName
   }

   function getFontName(){
     return this.fontName
   }

   function setFontStyle(f_thisStyle){
     if(f_thisStyle==="" || f_thisStyle==null){
         if(this.parent.fontStyle != "" && this.parent.fontStyle != null) f_thisStyle=this.parent.fontStyle
         else return
     }

     this.fontStyle=f_thisStyle
     obj=this.cell

     switch(f_thisStyle){
      case DG_BOLD:
       obj.style.fontWeight="bold"
       obj.style.fontStyle="normal"
      break;

      case DG_ITALIC:
       obj.style.fontWeight="normal"
       obj.style.fontStyle="italic"

      break;

      case DG_BOLDITALIC:
       obj.style.fontWeight="bold"
       obj.style.fontStyle="italic"
      break;

      case DG_NONE:
       obj.style.fontWeight="normal"
       obj.style.fontStyle="normal"
      break;

     }
   }

   function getFontStyle(){
     return this.fontStyle
   }

   function setFontSize(f_thisSize){
     if(f_thisSize==="" || f_thisSize==null){
         if(this.parent.fontSize != "" && this.parent.fontSize != null) f_thisSize=this.parent.fontSize
         else return
     }

     this.fontSize=f_thisSize
	 this.cell.style.fontSize=f_thisSize
   }

   function getFontSize(){
     return this.fontSize
   }

   function setAlignment(f_thisAlign){
     if(f_thisAlign==="" || f_thisAlign==null){
         if(this.parent.align != "" && this.parent.align != null) f_thisAlign=this.parent.align
         else return
     }

     this.align=f_thisAlign
	 this.cell.style.textAlign=f_thisAlign
   }

   function getAlignment(){
     return this.align
   }


 /* ~~~~~~~~~~~~~ Constructor Code ~~~~~~~~~~~~~ */
  this.tFoot=f_table.object.createTFoot()
  this.row=this.tFoot.insertRow()
  this.cell=this.row.insertCell()

  this.cell.innerHTML=this.footerText

  this.setBackgroundColor(this.backgroundColor)
  this.setForegroundColor(this.foregroundColor)
  this.setFontName(this.fontName)
  this.setFontSize(this.fontSize)
  this.setFontStyle(this.fontStyle)
  this.setAlignment(this.align)
 }

