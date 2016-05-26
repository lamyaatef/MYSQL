package com.mobinil.sds.core.utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;


public class DBImporter
{	
	private Connection		_importConnection = null;
	private String			_importTableName = null;
	private String			_delimiter = DEFAULT_DELIMITER;

	public static final String DEFAULT_DELIMITER = "|";
	public static final SimpleDateFormat DEFAULT_DATE_FORMAT = 
		new SimpleDateFormat("dd/mm/yyyy"); //new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss.SSS");

	
	/**
	 * Imports a file from delimited text.
	 * @param fileName
	 */
	public void importFile(String fileName) 
	{

		if (_importConnection == null || _importTableName == null)
		{
			throw new IllegalStateException("Connection and table name required.");
		}

		BufferedReader input = null;
		PreparedStatement pStmt = null;
		PreparedStatement insertPStmt = null;
		ResultSet results = null;

		try
		{
			input = new BufferedReader(new FileReader(fileName));

			String selectText = "select * from " + _importTableName + " where 1 = 2";
			pStmt = _importConnection.prepareStatement(selectText);
			results = pStmt.executeQuery();

			HashMap fileToTableMap = new HashMap();
			ArrayList columnsToImport = new ArrayList();

			ResultSetMetaData metaData = results.getMetaData();
			String headerLine = input.readLine();
			StringTokenizer headerToken = new StringTokenizer(headerLine, _delimiter, false);
			String importColumnName = null;

			while (headerToken.hasMoreTokens())
			{
				importColumnName = headerToken.nextToken();
				for (int i = 1 ; i <= metaData.getColumnCount(); i++)
				{
					if (importColumnName.equalsIgnoreCase(metaData.getColumnName(i)))
					{
						columnsToImport.add(importColumnName);
						fileToTableMap.put(importColumnName, new Integer(i));
						i = metaData.getColumnCount();
					}
				}
			}

			StringBuffer insertSqlText = new StringBuffer(256);
			insertSqlText.append("insert into ");
			insertSqlText.append(_importTableName);
			insertSqlText.append(" (");
			insertSqlText.append(this.getDelimitedString(columnsToImport, ","));
			insertSqlText.append(" ) values (");
			insertSqlText.append(this.getDelimitedString("?", ",", columnsToImport.size()));
			insertSqlText.append(")");
			insertPStmt = _importConnection.prepareStatement(insertSqlText.toString());

			String dataLine = input.readLine();
			int startOffset, endOffset;
			String value;
			Object javaValue = null;
			int commitCount = 0;

			while (dataLine != null)
			{
				startOffset = 0;
				endOffset = dataLine.indexOf(_delimiter);
				int sqlType = 0;
				int colOffset = 0;
				if (endOffset < 0) endOffset = dataLine.length();

				for (int i = 0; i < columnsToImport.size(); i++)
				{
					if (endOffset > dataLine.length()) break;
					else if (endOffset > startOffset)
					{
						value = dataLine.substring(startOffset, endOffset);
					}
					else value = null;
					colOffset = ((Integer)fileToTableMap.get(columnsToImport.get(i))).intValue();
					sqlType = metaData.getColumnType(colOffset);

					if (value == null)
					{
						insertPStmt.setNull(i + 1, sqlType);
					}
					else
					{
						switch (sqlType)
						{
						case Types.DATE:
						case Types.TIME:
						case Types.TIMESTAMP:
						{
							javaValue = new java.sql.Timestamp(DEFAULT_DATE_FORMAT.parse(value).getTime());
							break;
						}
						case Types.DECIMAL:
						case Types.DOUBLE:
						case Types.FLOAT:
						case Types.NUMERIC:
						{
							javaValue = new Double(value);
							break;
						}
						case Types.BIGINT:
						case Types.INTEGER:
						case Types.SMALLINT:
						case Types.TINYINT:
						{
							javaValue = new Long(value);
							break;
						}
						default:
						{
							javaValue = value;
						}
						}
						insertPStmt.setObject(i + 1, javaValue);
					}

					startOffset = endOffset + 1;
					endOffset = dataLine.indexOf(_delimiter, startOffset);
					if (endOffset < 0) endOffset = dataLine.length();
				}
				insertPStmt.executeUpdate();
				if (commitCount > 100)
				{
					commitCount = 0;
					_importConnection.commit();
				}
				else commitCount++;

				dataLine = input.readLine();
			}
			_importConnection.commit();

			input.close();

		}
		catch (Throwable t)
		{
			StringBuffer message = new StringBuffer();

			message.append("Error importing table ");
			message.append(_importTableName);
			message.append(" from file ");
			message.append(fileName);
			System.out.println(message);
		}
		finally
		{			
			DBUtil.close(pStmt);

		}
	}

	private String getDelimitedString(List list, String delimiter)
	{
		StringBuffer delimitedStr = new StringBuffer();

		for (int i = 0 ; i < list.size(); i++)
		{
			if (i > 0) delimitedStr.append(delimiter);
			delimitedStr.append(list.get(i));
		}

		return delimitedStr.toString();
	}

	private String getDelimitedString(String value, String delimiter, int nbrTimes)
	{
		StringBuffer delimitedStr = new StringBuffer();

		for (int i = 0 ; i < nbrTimes; i++)
		{
			if (i > 0) delimitedStr.append(delimiter);
			delimitedStr.append(value);
		}

		return delimitedStr.toString();
	}

	public static void main(String[] args)
	{
		try
		{
			DBExporter dbB = new DBExporter();
			dbB.setDelimiter("|");
			Connection con = Utility.getConnection();

			dbB.setExportConnection(con);
			dbB.setExportTableName("gen_dcm");
			dbB.exportFile("c://eclipse//eclipse//bb.txt");
			Utility.closeConnection(con);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}
	/**
	 * Connection used for Import operation.
	 * @return
	 */
	public Connection getImportConnection()
	{
		return _importConnection;
	}

	/**
	 * Table name used for import.
	 * @return
	 */
	public String getImportTableName()
	{
		return _importTableName;
	}
	/**
	 * Connection used for Import operation.
	 * @param connection
	 */
	public void setImportConnection(Connection connection)
	{
		_importConnection = connection;
	}

	/**
	 * Table name used for import.
	 * @param string
	 */
	public void setImportTableName(String string)
	{
		_importTableName = string;
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
