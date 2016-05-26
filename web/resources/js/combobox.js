var UNDEFINED;  // do not assign!

function changeAll(combobox) {
  comboboxs=document.getElementsByTagName("SELECT");
  for (i=0;i<comboboxs.length;i++) 
  {
    comboboxs[i].value = combobox.value;
  }
}

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

function xaddToList(list1, list2) 
{
  for(i=0;i<list1.options.length;i++) 
  {
    if(list1.options[i].selected == true && list1.options[i].text!='-- AVAILABLE WARNINGS --') 
    {
      var no   = new Option();
      no.value = list1.options[i].value;
      no.text  = list1.options[i].text;
      list2.options[list2.options.length] = no;		
    }
  }//end for
	for(i=list1.options.length-1;i>=0;i--) 
  {
		if(list1.options[i].selected == true && list1.options[i].text!='-- AVAILABLE WARNINGS --') 
    {
			list1.options[i]=null;		
		}
	}//end for
}
function xaddAll(list1, list2) 
{
	for(i=1;i<list1.options.length;i++) 
  {
		list1.options[i].selected = true
	}//end for
	xaddToList(list1, list2);
}

function xremoveFromList(list1, list2) 
{
  for(i=0;i<list2.options.length;i++) 
  {
    if(list2.options[i].selected == true && list2.options[i].text!='-- SELECTED WARNINGS --') 
    {
      var no   = new Option();
      no.value = list2.options[i].value;
      no.text  = list2.options[i].text;
      list1.options[list1.options.length] = no;		
    }
  }//end for
	for(i=list2.options.length-1;i>=0;i--) 
  {
		if(list2.options[i].selected == true && list2.options[i].text!='-- SELECTED WARNINGS --') 
    {
			list2.options[i]=null;		
		}
	}//end for
}
function xremoveAll(list1, list2) 
{
	for(i=1;i<list2.options.length;i++) 
  {
		list2.options[i].selected = true
	}//end for
	xremoveFromList(list1, list2);
}

function selectAll(list) 
{
	for(i=1;i<list.options.length;i++) 
  {
		list.options[i].selected = true
	}//end for

  list.options[0].selected=false;
}
