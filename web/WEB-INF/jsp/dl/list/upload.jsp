<%-- 
    Document   : upload
    Created on : Nov 10, 2011, 11:59:11 AM
    Author     : Gado
--%>

<%@page contentType="text/html;charset=UTF-8" %>
<%@page pageEncoding="UTF-8" %>
<%@ page session="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 
<html>
    <head>
        <META http-equiv="Content-Type" content="text/html;charset=UTF-8">
        <title>Upload Example</title>
    </head>
    <body>
        <form:form modelAttribute="dcmList" method="post" enctype="multipart/form-data">
                    <form:hidden path="DL_LIST_ID"/>
                    <input type="hidden" id="historyId" name="historyId" value="${historyId}"/>
                    <c:if test="${lock == 0}">
                    <select name="month" id="month">
                    </c:if>
                    <c:if test="${lock == 1}">
                        <input type="hidden" id="month" name="month" />
                    <select name="monthhide" id="monthhide" disabled ="true">
                    </c:if>    
                    
                        <option <c:if test="${month == 1}"> selected </c:if> value="1">
                            January
                        </option>
                        <option <c:if test="${month == 2}"> selected </c:if> value="2">
                            February
                        </option>
                        <option <c:if test="${month == 3}"> selected </c:if> value="3">
                            March
                        </option>
                        <option <c:if test="${month == 4}"> selected </c:if> value="4">
                            April
                        </option>
                        <option  <c:if test="${month == 5}"> selected </c:if> value="5">
                            May
                        </option>
                        <option <c:if test="${month == 6}"> selected </c:if> value="6">
                            June
                        </option>
                        <option <c:if test="${month == 7}"> selected </c:if> value="7">
                            July
                        </option>
                        <option <c:if test="${month == 8}"> selected </c:if> value="8">
                            August
                        </option>
                        <option <c:if test="${month == 9}"> selected </c:if> value="9">
                            September
                        </option>
                        <option <c:if test="${month == 10}"> selected </c:if> value="10">
                            October
                        </option>
                        <option <c:if test="${month == 11}"> selected </c:if> value="11">
                            November
                        </option>
                        <option <c:if test="${month == 12}"> selected </c:if> value="12">
                            December
                        </option>
                    </select>
                   <c:if test="${lock == 0}">
                    <select name="year" id="year">
                    </c:if>
                    <c:if test="${lock == 1}">
                         <input type="hidden" id="year" name="year" />
                    <select name="yearhide" id="yearhide" disabled="true">
                       
                    </c:if>
                    <option <c:if test="${year == 2000}"> selected </c:if> value="2000">
                            2000
                    </option>
                    <option <c:if test="${year == 2001}"> selected </c:if> value="2001">
                            2001
                     </option>
                        
                        <option <c:if test="${year == 2002}"> selected </c:if> value="2002">
                            2002
                        </option>
                        <option  <c:if test="${year == 2003}"> selected </c:if> value="2003">
                            2003
                        </option>
                        <option <c:if test="${year == 2004}"> selected </c:if> value="2004">
                            2004
                        </option>
                        <option <c:if test="${year == 2005}"> selected </c:if> value="2005">
                            2005
                        </option>
                        <option <c:if test="${year == 2006}"> selected </c:if> value="2006">
                            2006
                        </option>
                        <option <c:if test="${year == 2007}"> selected </c:if> value="2007">
                            2007
                        </option>
                        <option <c:if test="${year == 2008}"> selected </c:if> value="2008">
                            2008
                        </option>
                        <option <c:if test="${year == 2009}"> selected </c:if> value="2009">
                            2009
                        </option>
                        <option <c:if test="${year == 2010}"> selected </c:if> value="2010">
                            2010
                        </option>
                        <option  <c:if test="${year == 2011}"> selected </c:if> value="2011">
                            2011
                        </option>
                        <option <c:if test="${year == 2012}"> selected </c:if> value="2012">
                            2012
                        </option>
                        <option <c:if test="${year == 2013}"> selected </c:if> value="2013">
                            2013
                        </option>
                        <option <c:if test="${year == 2014}"> selected </c:if> value="2014">
                            2014
                        </option>
                        <option <c:if test="${year == 2015}"> selected </c:if> value="2015">
                            2015
                        </option>
                        <option <c:if test="${year == 2016}"> selected </c:if> value="2016">
                            2016
                        </option>
                        <option <c:if test="${year == 2017}"> selected </c:if> value="2017">
                            2017
                        </option>
                        <option <c:if test="${year == 2018}"> selected </c:if> value="2018">
                            2018
                        </option>
                        <option <c:if test="${year == 2019}"> selected </c:if> value="2019">
                            2019
                        </option>
                    </select>
                    <form:label for="fileData" path="fileData">File</form:label><br/>
                    <form:input path="fileData" type="file"/>
                    <input type="submit" value="upload"/>
        </form:form> 
                    <script>
                        document.getElementById("month").value=document.getElementById("monthhide").value;
                        document.getElementById("year").value=document.getElementById("yearhide").value;
                        </script>
    </body>
</html>