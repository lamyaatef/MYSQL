<%@ page import="java.sql.*" %> 
<%
String emp_id = request.getParameter("emp_id").toString();
String  data ="";

Connection conn = null;
String url = "jdbc:oracle:thin:@(DESCRIPTION = (ADDRESS = (PROTOCOL = TCP)(HOST = 10.0.0.130)(PORT = 1521)) (CONNECT_DATA = (SERVICE_NAME = SDSTEST)))";
String dbName = "user_register";
String driver = "oracle.jdbc.driver.OracleDriver";
String userName = "sds"; 
String password = "sand";


    int sumcount=0; 
	Statement st;
    try {
      Class.forName(driver).newInstance();
	 
      conn = DriverManager.getConnection(url,userName,password);
	    String query = "select * from gen_channel where channel_id = '"+emp_id+"'";
	
       st = conn.createStatement();
	   ResultSet  rs = st.executeQuery(query);
	   while(rs.next())
		{
		    data = ":" + rs.getString(1) + " " + rs.getString(2) +":"+ emp_id;
		}

       
		out.println(data);
	}
	catch (Exception e) {
      e.printStackTrace();
    }
 %>