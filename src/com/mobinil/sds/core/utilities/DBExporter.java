package com.mobinil.sds.core.utilities;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.SimpleDateFormat;


public class DBExporter
{
	public static final String DEFAULT_DELIMITER = "|";

	private Connection		_exportConnection = null;	
	private String			_exportTableName = null;
	private String			_delimiter = DEFAULT_DELIMITER;
	private SimpleDateFormat dateFormat = DEFUALT_FORMAT;
		

	public static final SimpleDateFormat DEFUALT_FORMAT = 
		new SimpleDateFormat("dd/mm/yyyy"); //new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss.SSS");

	/**
	 * Exports a file to delimited text.
	 * @param fileName
	 */
	public void exportFile(String fileName) 
	{		
		if (_exportConnection == null || _exportTableName == null)
		{
			throw new IllegalStateException("Connection and table name required.");
		}

		PrintStream output = null;
		PreparedStatement pStmt = null;
		ResultSet results = null;
		try
		{
			output = new PrintStream(new FileOutputStream(fileName));

			String sqlText = "select * from " + _exportTableName;
			//			SQLAssistant sql = new SQLAssistant(_exportConnection);

			//	sql.setDefaultDateFormat(TIMESTAMP_FORMAT);

			pStmt = _exportConnection.prepareStatement(sqlText);
			results = pStmt.executeQuery();

			ResultSetMetaData metaData = results.getMetaData();
			for (int i = 1 ; i <= metaData.getColumnCount(); i++)
			{
				if (i > 1) output.print(_delimiter);
				output.print(metaData.getColumnName(i));
			}
			output.println("");

			int col = 0;
			while (results.next())
			{
				col= 0;
				for (int offset = 1; offset <= metaData.getColumnCount(); offset++)
				{
					if (col > 0) output.print(_delimiter);					
					Object tempObj = results.getObject(offset);					  
					if (results.wasNull()) 
					{
						output.print("");						  
					}
				    else if (tempObj instanceof java.util.Date && dateFormat != null)
					{					
						output.print(tempObj);
					}
					else if (tempObj instanceof String )							 
					{
						output.print(((String) tempObj).trim());
					}
					else 
					{
						output.println(tempObj.toString());
					}
					col++;
				}
				output.println("");
			}
			results.close();

		}
		catch (Throwable t)
		{
			StringBuffer message = new StringBuffer();
			message.append("Error exporting table ");
			message.append(_exportTableName);
			message.append(" to file ");
			message.append(fileName);
			System.out.println("message = "+message);

			//	throw new Exception (message.toString());
		}
		finally
		{
			DBUtil.close(pStmt);						
			if (output != null) output.close();
		}
	}

	public static void main(String[] args)
	{
		try
		{			
			Connection con = Utility.getConnection();			
			DBExporter dbB = new DBExporter();
			dbB.setDelimiter("|");
			dbB.setExportConnection(con);
		
			//dbB.setExportTableName("gen_dcm");
			//dbB.exportFile("c://eclipse//eclipse//bb.txt");
			
			//this example is based on AMR Hesham request that we can send sql query instead of table name 
			dbB.setExportTableName("(select dcm_code,dcm_name from gen_dcm)");
			dbB.exportFile("c://eclipse//eclipse//bc.txt");
			
			Utility.closeConnection(con);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}

	/**
	 * Connection used for the export operation.
	 * @return
	 */
	public Connection getExportConnection()
	{
		return _exportConnection;
	}

	/**
	 * Table name used for export.
	 * @return
	 */
	public String getExportTableName()
	{
		return _exportTableName;
	}


	/**
	 * Connection used for the export operation.
	 * @param connection
	 */
	public void setExportConnection(Connection connection)
	{
		_exportConnection = connection;
	}

	/**
	 * Table name used for export.
	 * @param string
	 */
	public void setExportTableName(String string)
	{
		_exportTableName = string;
	}



	/**
	 * Delimiter used in export file.
	 * @return
	 */
	public String getDelimiter()
	{
		return _delimiter;
	}

	/**
	 * Delimiter used in export file.
	 * @param string
	 */
	public void setDelimiter(String string)
	{
		_delimiter = string;
	}

}
