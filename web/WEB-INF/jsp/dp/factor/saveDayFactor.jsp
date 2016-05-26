<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
    <head>    
        <title>Factor Data</title>
        <link rel="stylesheet" type="text/css"
              href="/SDS/resources/css/Template1.css"/>

        <script language="JavaScript" src="/SDS/resources/js/calendar.js" type="text/javascript"></script>        
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
        <c:url var="saveNameUrl" value="/dp/factor/insertDay.htm" />        
        <h3>Add New Day Factor</h3>
        <form:form modelAttribute="dayFactor" id="dayFactForm" name="dayFactForm" method="GET" action="${saveNameUrl}" onsubmit="document.dayFactForm.hdpid.value=document.dayFactForm.dpid.value;">                            
            <form:hidden path="dpId" id="hdpid"/>
            <input type="hidden" name="dpid" id="dpid" value="${dpid}" />   
            <input type="hidden" name="factorType" id="factorType" value="${factorType}" />   
            <TABLE  style="WIDTH: 746px; BORDER-COLLAPSE: collapse; HEIGHT: 91px"
                   cellSpacing=2 cellPadding=1 width="746" border=1>

                <TR class="TableTextNote">
                    <TD class="TableTextNote" style="WIDTH: 20%;white-space:nowrap;"><form:label path="nameId">Factor Name:</form:label>    </TD>
                    <TD class="TableTextNote" style="WIDTH: 30%;"><form:select cssStyle="WIDTH: 100%;" path="nameId" multiple="false">
                            <form:option value="0" label="--- Select ---" />
                            <form:options items="${name_list}" itemLabel="factorName" itemValue="factorNameId"/>
                        </form:select></TD>        				                          
                    <TD class="TableTextNote" style="WIDTH: 50%;white-space: nowrap;"><font color="red"><form:errors path="nameId" /></font></TD>            
                </TR>
                <TR class="TableTextNote">
                    <TD class="TableTextNote" style="WIDTH: 20%;white-space:nowrap;" >Factor Value:</TD>
                    <TD class="TableTextNote" style="WIDTH: 30%;">

                        <table id="tblSample" border="1">
                            <thead>
                                <tr>
                                    <th class="TableTextNote">From</th>
                                    <th class="TableTextNote">To</th>
                                    <th class="TableTextNote">Value</th>
                                    <th class="TableTextNote">Delete</th>
                                </tr>
                            </thead>
                            <c:forEach items="${valueList}" var="value">
                                <c:url value="${valueList.indexOf(value)+1}" var="rowNumber"/>                                                                
                                
                                
                                <tr id="tr<c:out value="${rowNumber}"/>" >
                                    <td class="TableTextNote"><input name="from${rowNumber}" id="from${rowNumber}"  value="${fn:replace(value.factorRangeFrom, " 00:00:00.0", "")}" readonly="true" type="text"/><input type="button" onClick="showCalendar(from${rowNumber},'yyyy-mm-dd','Choose date')" name="Button" id="Button" value="..."></td>
                                <td class="TableTextNote"><input name="to${rowNumber}" id="to${rowNumber}" value="${fn:replace(value.factorRangeTo, " 00:00:00.0", "")}" readonly="true" type="text"/><input type="button" onClick="showCalendar(to${rowNumber},'yyyy-mm-dd','Choose date')" name="Button" id="Button" value="..."></td>
                                <td class="TableTextNote"><input name="val${rowNumber}" id="val${rowNumber}" type="text" value="${value.factorValue}"/></td>
                                <td class="TableTextNote"><input name="del${rowNumber}" id="del${rowNumber}" type="button" value="Delete" onClick="removeRowFromTable(getRowIndex('tr${rowNumber}'))"/></td>
                            </tr>
                            </c:forEach>
                        </table>
                        <button type="button" onClick="addRowToTable()">Add range value</button>

                    </TD>            
                    <TD class="TableTextNote" style="WIDTH: 50%; white-space: nowrap;"><font color="red"><form:errors path="factorId" /></font></TD>            

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
<script type="text/javascript">
            var iteration = <c:out value="${valueListSize}"/>;
            function getRowIndex(trName){
                
                var tbl = document.getElementById('tblSample');
                var rows = tbl.rows;
                
                for( var i = 0; i < rows.length; i++ ) {                    
                    if (rows[i].id==trName)	
                    {                        
                        return rows[i].rowIndex;
                    }

                }
                
                

            }
            function addRowToTable()
            {
                iteration++;
                var tbl = document.getElementById('tblSample');
                var lastRow = tbl.rows.length;
                // if there's no header row in the table, then iteration = lastRow + 1
                //var iteration = lastRow;
                var row = tbl.insertRow(lastRow);
                row.id='tr'+ iteration;
                row.name='tr'+ iteration;
                var temIter = iteration;
                // form cell
                var fromDateFieldName = 'from' + temIter;
                var toDateFieldName = 'to' + temIter;  
                var cellFrom = row.insertCell(0);
                var el = document.createElement('input');  
                el.type = 'text';
                el.name = fromDateFieldName;
                el.id = fromDateFieldName;  
                el.readOnly = true;
                cellFrom.appendChild(el);
                var elCal = document.createElement('input');  
                elCal.type = 'button';
                elCal.name = 'button';
                elCal.id = 'button';  
                elCal.value = '...';  
                
        elCal.onclick=function(){showCalendar(eval(document.getElementById(fromDateFieldName)),'yyyy-mm-dd','Choose date');};         
                cellFrom.appendChild(elCal);  
  
                var cellto = row.insertCell(1);
                var elTo = document.createElement('input');
                elTo.type = 'text';
                elTo.name = toDateFieldName;
                elTo.id = toDateFieldName;  
                elTo.readOnly=true;
                cellto.appendChild(elTo);
                var elToCal = document.createElement('input');  
                elToCal.type = 'button';
                elToCal.name = 'button';
                elToCal.id = 'button';  
                elToCal.value = '...';  
                elToCal.onclick=function(){showCalendar(eval(document.getElementById(toDateFieldName)),'yyyy-mm-dd','Choose date');};         
                cellto.appendChild(elToCal);    
  
                var cellVal = row.insertCell(2);
                var elval = document.createElement('input');
                elval.type = 'text';
                elval.name = 'val' + iteration;
                elval.id = 'val' + iteration;
                cellVal.appendChild(elval);  
  
                var celldel = row.insertCell(3);
                var eldel = document.createElement('input');
                eldel.type = 'button';
                eldel.name = 'del' + iteration;
                eldel.id = 'del' + iteration;
                eldel.value = 'Delete';
                
                eldel.onclick=function(){removeRowFromTable(getRowIndex("tr"+temIter));};
                celldel.appendChild(eldel); 
                
                
            }
            function removeRowFromTable(elem)
            {
                var tbl = document.getElementById('tblSample');
                tbl.deleteRow(elem);
            }
            function resetandBackforms(){        
        
        document.dayFactForm.reset();
        history.go(-1);
    }
        </script>
</html>