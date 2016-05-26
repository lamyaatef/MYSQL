<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<html>
    <head>    
        <title>Factor Data</title>
        <link rel="stylesheet" type="text/css"
              href="/SDS/resources/css/Template1.css"/>

        <style>
            .error {
                color: #ff0000;
            }

            .errorblock {
                color: #000;
                background-color: #ffEEEE;
                border: 3px solid #ff0000;
                padding: 8px;
                margin: 16px;
            }
        </style>
    </head>
    <body>
    <center>
        <c:url var="saveNameUrl" value="/dp/factor/insertMon.htm" />        
            <h3>Add New Month Factor</h3>
            <form:form modelAttribute="monthFactor" id="monFactForm" name="monFactForm" method="GET" action="${saveNameUrl}" onsubmit="document.monFactForm.hdpid.value=document.monFactForm.dpid.value;">                            
                <form:hidden path="dpId" id="hdpid"/>
                <input type="hidden" name="factorType" id="factorType" value="${factorType}" />
                <input type="hidden" name="dpid" id="dpid" value="${dpid}" />
        <TABLE style="WIDTH: 746px; BORDER-COLLAPSE: collapse; HEIGHT: 91px"
               cellSpacing=2 cellPadding=1 width="746" border=1>
            
            <TR class="TableTextNote">
	<TD class="TableTextNote" style="WIDTH: 20%;"><form:label path="nameId">Factor Name:</form:label>    </TD>
            <TD class="TableTextNote" style="WIDTH: 30%;"><form:select cssStyle="WIDTH: 100%;" path="nameId" multiple="false">
					   <form:option value="0" label="--- Select ---" />
                                               <form:options items="${name_list}" itemLabel="factorName" itemValue="factorNameId"/>
					</form:select></TD>        				
				</TD>        
                                    <TD class="TableTextNote" style="WIDTH: 50%;"><font color="red"><form:errors path="nameId" /></font></TD>            
	</TR>
        <TR class="TableTextNote">
                <TD class="TableTextNote" style="WIDTH: 20%;" ><form:label path="factorValue">Factor Value:</form:label>    </TD>
                <TD class="TableTextNote" style="WIDTH: 30%;"><form:input cssStyle="WIDTH: 100%;" path="factorValue" />    </TD>            
                <TD class="TableTextNote" style="WIDTH: 50%;"><font color="red"><form:errors path="factorValue" /></font></TD>            
            
            </TR>     
        <TR class="TableTextNote">
                <TD class="TableTextNote" style="WIDTH: 20%;" ><form:label path="monthBck">Month Backward:</form:label>    </TD>
                <TD class="TableTextNote" style="WIDTH: 30%;"><form:input cssStyle="WIDTH: 100%;" path="monthBck" />    </TD>            
                <TD class="TableTextNote" style="WIDTH: 50%;"><font color="red"><form:errors path="monthBck" /></font></TD>            
            
            </TR>     
        <TR class="TableTextNote">
                <TD class="TableTextNote" style="WIDTH: 20%;" ><form:label path="monthFrw">Month Forward</form:label>    </TD>
                <TD class="TableTextNote" style="WIDTH: 30%;"><form:input cssStyle="WIDTH: 100%;" path="monthFrw" />    </TD>            
                <TD class="TableTextNote" style="WIDTH: 50%;"><font color="red"><form:errors path="monthFrw" /></font></TD>            
            
            </TR>     
                            
            <TR class="TableTextNote">
                <TD  class="TableTextNote" colspan="3" align="center" >
                    <input type="submit" value="Save Factor" /></TD>
            </TR>
        </table>        
    </form:form>
        
        <br>
                        <input type="button" value="Back" onClick="resetandBackforms();" />
        

       
    </center>
</body>  
<script>
    function resetandBackforms(){                
        document.monFactForm.reset();
        history.go(-1);
    }
</script>
</html>