//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///																											               ///
///				General Data Validation Library			               
///																											               ///
///		Interface :																							               ///
///		-----------																							               ///
///																											               ///
///		1)  NonBlank(item, SetFocusFlag, Caption) ---created by baseil zaki																		   ///
///		2)  ValidEmail(item, SetFocusFlag, Caption)-- created by baseil zaki                                                                      
///		3)  ValidNumber(item, DisplayFlag, Format, LowerLimit, UpperLimit, Exception1, Exception2, SetFocusFlag, Caption)--created by baseil zaki  	
///             
///																											               ///
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



function validateLogin() {

 if (document.frmLogin.UserName.value.length == 0) {
  alert("Enter a user Name, please!");
  document.frmLogin.UserName.focus();
  
return false;
 }

 else {
  return true;
}
}




function emailvalid(inputStr) {
 apos=inputStr.indexOf("@");
 //alert(apos)
 dpos=inputStr.lastIndexOf(".");
 //alert(dpos)
 //lpos=inputStr.length-1;
 mpos =inputStr.lastIndexOf(" ");
 
 if (apos>1) { return false;}
 else if (dpos>1) {return false;}
 else if (mpos>1) { return false; }
 else {return true;}
}


function emailInValid(inputStr) {
 apos=inputStr.indexOf("@");
 //alert(apos);
 dpos=inputStr.lastIndexOf(".");
 //alert(dpos);
 //lpos=inputStr.length-1;
 mpos =inputStr.lastIndexOf(" ");
 //alert(mpos);
 if (inputStr.length >250){ alert("You entered an invalid E-Mail address.");return false;}
 if (apos<2) { alert("You entered an invalid E-Mail address.");return false;}
 else if (dpos<1) {alert("You entered an invalid E-Mail address.");return false;}
 else if (mpos>1) { alert("You entered an invalid E-Mail address.");return false; }
 else {return true;}
}


function NonBlank(item, SetFocusFlag, Caption)
{
	if (Caption!=null){
		var strErrorMsg = Caption + " must have a non-blank value";
	}
	else {
		var strErrorMsg = item.name + " must have a non-blank value";
	}
	if (item.type == 'text' || item.type == 'password' || item.type == 'textarea'|| item.type == 'file')
        {
		if (item.value.length==0)
		{
			if (SetFocusFlag == true) 
                        {
				item.focus();
			}
			alert(strErrorMsg);
			return false;
		}
            else
            {
              var tempString = "";      
              for (var intLoop = 0; intLoop < item.value.length; intLoop++)
              {
                if (item.value.charAt(intLoop) != " ")
                {
                  tempString = tempString + item.value.charAt(intLoop);
                }
              }
              if(tempString.length==0)
              {
                if (SetFocusFlag == true) 
                {
                  item.focus();
                }
                alert("'"+item.value+"' is not a valid " + item.name + ".");
                return false;
              }
            }
	}
	else
        {    
		if (item.selectedIndex==-1 || item.options[item.selectedIndex].value.length==0)
		{
			if (SetFocusFlag == true) {
				item.focus();
			}
			alert(strErrorMsg);
			return false;
		}
        }
	return true;
}

function validPassword(item, SetFocusFlag, Caption)
{
	if (Caption == null)
  {
		var Caption = item.name;
	}
	if (item.type == 'text' || item.type == 'password' || item.type == 'textarea')
        {
		if (item.value.length==0)
		{
			if (SetFocusFlag == true) 
      {
				item.focus();
			}
			alert(Caption + " must have a non-blank value");
			return false;
		}
    else
      if(item.value.lastIndexOf(" ") > -1)
      {
        if (SetFocusFlag == true) 
        {
          item.focus();
        }
        alert(Caption + " can not have spaces.");
        return false;
      }
     
     	return true;
}
}

function equal(item1, item2, Caption1, Caption2)
{
	if (item1.type == 'text' || item1.type == 'password' || item1.type == 'textarea')
  {
    if(item2.type == 'text' || item2.type == 'password' || item2.type == 'textarea')
    {
      if (item1.value==item2.value)
      {
        return true;
      }
    }
	}
  alert(Caption2 + " must be the same as " + Caption1);
	return false;
}

function notEqual(item1, item2, Caption1, Caption2)
{
	if (item1.type == 'text' || item1.type == 'password' || item1.type == 'textarea')
  {
    if(item2.type == 'text' || item2.type == 'password' || item2.type == 'textarea')
    {
      if (item1.value==item2.value)
      {
        alert(Caption2 + " must not be the same as " + Caption1);
        return false;
      }
    }
	}
	return true;
}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

function ValidEmail(item, SetFocusFlag, Caption)
{
	if (Caption!=null){
		var strErrorMsg = Caption + " must have a valid format";
	}
	else {
		var strErrorMsg = item.name + " must have a valid format";
	}

	if (!(/^[^@]+@[^@.]+.+[^@]*[^@.]$/.test(item.value)) || (item.value.search(String.fromCharCode(34)) != -1))
	{
		if ((SetFocusFlag == true) || (SetFocusFlag == null)) {
			item.focus();
		}
		alert(strErrorMsg);
		return false;
	}

	return true;
}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

function ValidNumberV2(item,  Caption , minNumber,maxNumber)
{


var flag = ValidateNumberFormat(item.value,2 );



   
 if (flag )
  {
    if (item.value.length == minNumber || item.value.length == maxNumber)
    {
    return true; 
    }
    else
    {
        alert(Caption + " must have a valid value");	
    	return false;
    }
  }
  else
  {
        alert(Caption + " must have a valid value");	
      	return false;
  
  }
  return false; 
}


function ValidNumber(item, DisplayFlag, Format, LowerLimit, UpperLimit, Exception1, Exception2, SetFocusFlag, Caption)
{

	var value = item.value;
	if (item.value.length==0)
	{
		return(true);			// Blank is always valid!
	
}

	LowerLimit = "" + LowerLimit;
	UpperLimit = "" + UpperLimit;
	Exception1 = "" + Exception1;
	Exception2 = "" + Exception2;

	var ErrorValue = DoValidation(value, Format, LowerLimit, UpperLimit, Exception1, Exception2);

	if (!ErrorValue)		// No Error
	{
		return (true);
	}
	else if ( (DisplayFlag == false) || (DisplayFlag == null))
	{
		return (false);
	}
	else
	{
		if (SetFocusFlag == true) {
			item.value="";		
		}
		switch (ErrorValue)
		{
			case 1:			// Wrong format
				if (Caption!=null){
					alert(Caption + " must be a valid Format");
					item.value="";
					item.focus();
				}
				else {
					alert(item.name + " must be a valid Format");
					item.value="";
					item.focus();
				}
				break;
			case 2:			// Wrong value
				if (Caption!=null){
					alert(Caption + " must have a valid value");
					item.value="";
					item.focus();
				}
				else {
					alert(item.name + " must have a valid value");
					item.value="";
					item.focus();
				}
				break;
			case 3:			// Invalid parameters
				if (Caption!=null){
					alert(Caption + " Check called with Invalid Parameters");
				}
				else {
					alert(item.name + " Check called with Invalid Parameters");
				}
				break;
			default:
				alert ("Error!");
				break;
		}
		return (false);
	}
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

function DoValidation(value, Format, LowerLimit, UpperLimit, Exception1, Exception2)

// returns (0) if valid
// Otherwise returns the error code
// 1 = Invalid Format
// 2 = Invalid value
// 3 = Invalid Limits
{

	if (!ValidateNumberFormat(value, Format))
	{
		return (1);				// Invalid Format
	}
	value = StandardNumericFormat(value, Format);
	if ( (LowerLimit != "undefined") && (LowerLimit != "null") && (LowerLimit != "") )
	{
		if  (!ValidateNumberFormat(LowerLimit, Format))
		{
			return (3);				// Invalid Limits Format
		}
		LowerLimit = StandardNumericFormat(LowerLimit, Format);
		if (value < LowerLimit)
		{
			return (2);				// Invalid value
		}
	}
	if ( (UpperLimit != "undefined") && (UpperLimit != "null") && (UpperLimit != "") )
	{
		if  (!ValidateNumberFormat(UpperLimit, Format))
		{
			return (3);				// Invalid Limits Format
		}
		UpperLimit = StandardNumericFormat(UpperLimit, Format);
		if (value > UpperLimit)
		{
			return (2);				// Invalid value
		}
	}
	if ( (Exception1 != "undefined") && (Exception1 != "null") && (Exception1 != "") )
	{
		if  (!ValidateNumberFormat(Exception1, Format))
		{
			return (3);				// Invalid Limits Format
		}
		Exception1 = StandardNumericFormat(Exception1, Format);
		if (value == Exception1)
		{
			return (2);				// Invalid value
		}
	}
	if ( (Exception2 != "undefined") && (Exception2 != "null") && (Exception2 != "") )
	{
		if  (!ValidateNumberFormat(Exception2, Format))
		{
			return (3);				// Invalid Limits Format
		}
		Exception2 = StandardNumericFormat(Exception2, Format);
		if (value == Exception2)
		{
			return (2);				// Invalid value
		}
	}
	return(0);
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

function ValidateNumberFormat(value, Format)
{
	var StValue=value;
	
	

	if (typeof(value) == "number")
	{
		StValue = "" + value;
	}

	if (typeof(value) != "string")
	{
		return (false);			// Wrong Format
	}

	if (StValue.length == 0)
		return (true);			// Valid Field

	var ValidChars;

	switch (Format)
	{
		case 1:					// Numeric
			ValidChars = "-.0123456789Ee";
			break;
		case 2:					// Integer
			ValidChars = "-0123456789Ee";
			break;
		case 3:					// Numeric With Commas
			ValidChars = "-,.0123456789Ee";
			break;
		case 4:					// Integer With Commas
			ValidChars = "-,0123456789Ee";
			break;
		default:				// Default Numeric
			ValidChars = "-.0123456789Ee";
			break;
	}
	for (var intLoop = 0; intLoop < StValue.length; intLoop++)
	{
		if (ValidChars.indexOf(StValue.charAt(intLoop)) == -1)
		{
			return (false);			// Wrong Format
		}
	}
	if (StValue.indexOf(".")!=StValue.lastIndexOf("."))
	{
		return (false);			// Wrong Format
	}
	if (StValue.indexOf("E")!=StValue.lastIndexOf("E"))
	{
		return (false);			// Wrong Format
	}
	if (StValue.indexOf("e")!=StValue.lastIndexOf("e"))
	{
		return (false);			// Wrong Format
	}
	if ( (StValue.indexOf("e")!= -1) && (StValue.lastIndexOf("E") != -1) )
	{
		return (false);			// Wrong Format
	}
	if ( (StValue.indexOf("e") == 0) || (StValue.lastIndexOf("E") == 0) )
	{
		return (false);			// Wrong Format
	}
	if ( (StValue.indexOf("e") == StValue.length) || (StValue.lastIndexOf("E") == StValue.length) )
	{
		return (false);			// Wrong Format
	}
	if ( (StValue.indexOf("-")!= -1) )
	{
		if ( (StValue.indexOf("-")!=StValue.lastIndexOf("-")) ||
			 (StValue.indexOf("-")!= 0) )
		{
			return (false);			// Wrong Format
		}
	}
	return (true);
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

function StandardNumericFormat(value, Format)		// Assumes a valid format already been checked
{
var s;
var i;
var StValue;
var Commas = new RegExp(",");

	StValue = "" + value;
	switch (Format)
	{
		case 1:					// Numeric
			value = parseFloat(value);
			return (value);
			break;
		case 2:					// Integer
			value = parseInt(value);
			return (value);
			break;
		case 3:					// Numeric With Commas
			s = StValue.split(Commas);
			StValue = "";
			for (i=0; (s[i] != null); i++)
			{
				StValue += s[i];
			}
			value = parseFloat(StValue);
			return (value);
			break;
		case 4:					// Integer With Commas
			s = StValue.split(Commas);
			StValue = "";
			for (i=0; (s[i] != null); i++)
			{
				StValue += s[i];
			}
			value = parseInt(StValue);
			return (value);
			break;
		default:				// Default Numeric
			return (value);
			break;
	}
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

function ValidDate(item, DisplayFlag, Format, LowerLimit, UpperLimit, Exception1, Exception2, SetFocusFlag, Caption)
{
	var value = item.value;
	if (item.value.length==0)
	{
		return(true);			// Blank is always valid!
	}
	
	LowerLimit = "" + LowerLimit;
	UpperLimit = "" + UpperLimit;
	Exception1 = "" + Exception1;
	Exception2 = "" + Exception2;

	var	ErrorValue = DoDateValidation(value, Format, LowerLimit, UpperLimit, Exception1, Exception2);

	if (!ErrorValue)		// No Error
	{
		return (true);
	}
	else if ( (DisplayFlag == false) || (DisplayFlag == null))
	{
		return (false);
	}
	else
	{
		if (SetFocusFlag == true) {
			item.focus();
		}
		switch (ErrorValue)
		{
			case 1:			// Wrong format
				if (Caption!=null){
					alert(Caption + " must xx1be a valid Format");
				}
				else {
					alert(item.name + " must xx2be a valid Format");
				}
				break;
			case 2:			// Wrong value
				if (Caption!=null){
					alert(Caption + " must ssshave a valid value");
				}
				else {
					alert(item.name + " must sssxhave a valid value");
				}
				break;
			case 3:			// Invalid parameters
				if (Caption!=null){
					alert(Caption + " Check called with Invalid Parameters");
				}
				else {
					alert(item.name + " Check called with Invalid Parameters");
				}
				break;
			default:
				alert ("Error!");
				break;
		}
		return (false);
	}

}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

function DoDateValidation(value, Format, LowerLimit, UpperLimit, Exception1, Exception2)
// returns (0) if valid
// Otherwise returns the error code
// 1 = Invalid Format
// 2 = Invalid value
// 3 = Invalid Limits
{
	if (!ValidateDateFormat(value, Format))
	{
		return (1);				// Invalid Format
	}
	value = StandardDateFormat(value, Format);

	if ( (LowerLimit != "undefined") && (LowerLimit != "null") && (LowerLimit != "") )
	{
		if  (!ValidateDateFormat(LowerLimit, Format))
		{
			return (3);				// Invalid Limits Format
		}
		LowerLimit = StandardDateFormat(LowerLimit, Format);
		if (Date.parse(value) < Date.parse(LowerLimit))
		{
			return (2);				// Invalid value
		}
	}
	if ( (UpperLimit != "undefined") && (UpperLimit != "null") && (UpperLimit != "") )
	{
		if  (!ValidateDateFormat(UpperLimit, Format))
		{
			return (3);				// Invalid Limits Format
		}
		UpperLimit = StandardDateFormat(UpperLimit, Format);
		if (Date.parse(value) > Date.parse(UpperLimit))
		{
			return (2);				// Invalid value
		}
	}
	if ( (Exception1 != "undefined") && (Exception1 != "null") && (Exception1 != "") )
	{
		if  (!ValidateDateFormat(Exception1, Format))
		{
			return (3);				// Invalid Limits Format
		}
		Exception1 = StandardDateFormat(Exception1, Format);
		if (Date.parse(value) == Date.parse(Exception1))
		{
			return (2);				// Invalid value
		}
	}
	if ( (Exception2 != "undefined") && (Exception2 != "null") && (Exception2 != "") )
	{
		if  (!ValidateDateFormat(Exception2, Format))
		{
			return (3);				// Invalid Limits Format
		}
		Exception2 = StandardDateFormat(Exception2, Format);
		if (Date.parse(value) == Date.parse(Exception2))
		{
			return (2);				// Invalid value
		}
	}
	return(0);
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
