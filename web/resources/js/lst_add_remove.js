
function select_all_bia() {
	for(i=0;i<document.myform.dynalist.options.length;i++) {
		document.myform.dynalist.options[i].selected = true ;
	}//end for
}//end fun

function select_all_vd() {
	for(i=0;i<document.myform.dynalist.options.length;i++) {
		document.myform.dynalist.options[i].selected = true ;
	}//end for
	
	for(i=0;i<document.myform.recp_name.options.length;i++) {
		document.myform.recp_name.options[i].selected = true ;
		document.myform.recp_title.options[i].selected = true ;
		
		document.myform.doc_o.options[i].selected = true ;
		document.myform.doc_h.options[i].selected = true ;
		document.myform.doc_s.options[i].selected = true ;
		
		document.myform.doc_lang.options[i].selected = true ;
		
		document.myform.doc_o_h_loc.options[i].selected = true ;
		document.myform.doc_s_loc.options[i].selected = true ;
		document.myform.doc_retrival.options[i].selected = true ;
		document.myform.doc_note.options[i].selected = true ;
		document.myform.doc_contact.options[i].selected = true ;		
	}//end for
}//end fun

function addtolist()
{
   if(document.myform.textfield.value != "") 
   {
      var no   = new Option();
      no.value = document.myform.textfield.value;
      no.text  = document.myform.textfield.value;
      document.myform.dynalist.options[document.myform.dynalist.options.length] = no;
      document.myform.textfield.value="";
      if(document.myform.dynalist.options[0].value=='-1'|| document.myform.dynalist.options[0].value=='-- No Values Entered --' ) 
      	{
	document.myform.dynalist.options[0]=null;
	}      
   }
}
 
function removefromlist()
{
	i=0;
	j = document.myform.dynalist.options.length;
	index = 0;
	while (i<j)
	{
      		if(document.myform.dynalist.options[index].selected) 
      		{
      		   document.myform.dynalist.options[index]=null;
      		}
      		else index++;
      	i++;      	
	}
	if (document.myform.dynalist.options.length == 0) {
      		var no   = new Option();
      		no.value = '-1';
      		no.text  = '-- No Values Entered --';
      		document.myform.dynalist.options[document.myform.dynalist.options.length] = no;	
	}
  refresh();
}
 
function refresh()  
{
   for(var i=0; i<document.myform.dynalist.options.length; i++) 
   {
      if(document.myform.dynalist.options[i].value == "")  
      {
         for(var j=i; j<document.myform.dynalist.options.length-1; j++)  
         {
            document.myform.dynalist.options[j].value = document.myform.dynalist.options[j+1].value;
            document.myform.dynalist.options[j].text = document.myform.dynalist.options[j+1].text;
         }
         var ln = i;
         break;
      }
   }
   if(ln < document.myform.dynalist.options.length)  
   {
      document.myform.dynalist.options.length -= 1;
      refresh();
   }
}
