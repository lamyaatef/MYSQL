package com.mobinil.sds.core.system.deu.connection.model;

import java.sql.ResultSet;
import java.io.Serializable;

/*
 * this class represent connection 
 * carries all the connection fields in it 
 */
public class ConnectionModel implements Serializable
{
  private static final String CONNECTION_ID="CONNECTION_ID";
  private static final String CONNECTION_NAME="NAME";
  private static final String CONNECTION_IP="IP";
  private static final String CONNECTION_PORT="PORT";
  private static final String CONNECTION_SCHEMA="SCHEMA";
  private static final String CONNECTION_USERNAME="USERNAME";
  private static final String CONNECTION_PASSWORD="PASSWORD";
  private static final String CONNECTION_DESCRIPTION="DESCRIPTION";

  private String connectionID;
  private String connectionName;
  private String serverIP;
  private String serverPort;
  private String schema;
  private String userName;
  private String password;
  private String description;

/*
 * constructor that take result set as input and extract all information and fill the fields of the object
 */
  public ConnectionModel(ResultSet res)
  {
  try
  {
    this.connectionID = res.getString(CONNECTION_ID);
    this.connectionName = res.getString(CONNECTION_NAME);
    this.serverIP = res.getString(CONNECTION_IP);
    this.serverPort = res.getString(CONNECTION_PORT);
    this.schema = res.getString(CONNECTION_SCHEMA);
    this.userName = res.getString(CONNECTION_USERNAME);
    this.password = res.getString(CONNECTION_PASSWORD);
    this.description = res.getString(CONNECTION_DESCRIPTION);                                
  }
  catch(Exception  e)
  {
    e.printStackTrace();
  }
  }

/*
 * get connection ID
 */
  public String getConnectionID()
  {
    return connectionID;
  }

/*
 * set connection ID
 */
  public void setConnectionID(String newConnectionID)
  {
    connectionID = newConnectionID;
  }

/*
 * get connection name
 */
  public String getConnectionName()
  {
    return connectionName;
  }

/*
 * set connection name
 */
  public void setConnectionName(String newConnectionName)
  {
    connectionName = newConnectionName;
  }

/*
 * get server IP
 */
  public String getServerIP()
  {
    return serverIP;
  }

/*
 * set server IP
 */
  public void setServerIP(String newServerIP)
  {
    serverIP = newServerIP;
  }

/*
 * get server port
 */
  public String getServerPort()
  {
    return serverPort;
  }

/*
 * set server port
 */
  public void setServerPort(String newServerPort)
  {
    serverPort = newServerPort;
  }

/*
 * get schema name
 */
  public String getSchema()
  {
    return schema;
  }

/*
 * set schema name
 */
  public void setSchema(String newSchema)
  {
    schema = newSchema;
  }

/*
 * get user name
 */
  public String getUserName()
  {
    return userName;
  }

/*
 * set user name
 */
  public void setUserName(String newUserName)
  {
    userName = newUserName;
  }

/*
 * get password
 */
  public String getPassword()
  {
    return password;
  }

/*
 * set password
 */
  public void setPassword(String newPassword)
  {
    password = newPassword;
  }

/*
 * get description
 */
  public String getDescription()
  {
    return description;
  }

/*
 * set description
 */
  public void setDescription(String newDescription)
  {
    description = newDescription;
  }
}
