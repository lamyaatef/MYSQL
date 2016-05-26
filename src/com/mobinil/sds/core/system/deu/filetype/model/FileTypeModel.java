package com.mobinil.sds.core.system.deu.filetype.model;

import java.sql.ResultSet;
import java.io.Serializable;
/*
 * this class is the file type model taht has the file type id and the file 
 * type extension 
 * 
 */ 

public class FileTypeModel implements Serializable
{
  private static final String FILE_TYPE_ID="FILE_TYPE_ID";
  private static final String FILE_TYPE_EXT="FILE_TYPE_EXT";
  
  private String fileTypeID;
  private String fileTypeExt;

 /*
  * constructor that take a result set as input and extract from it 
  * the instance variables values
  */
  public FileTypeModel(ResultSet res)
  {
  try
  {
    this.fileTypeID = res.getString(FILE_TYPE_ID);
    this.fileTypeExt = res.getString(FILE_TYPE_EXT);                               
  }
  catch(Exception  e)
  {
    e.printStackTrace();
  }
  }

/*
 * get file type id as  string
 */
  public String getFileTypeID() {
    return fileTypeID;
  }

/*
 * set file id take a file type id as a string and set the 
 * instance filetypeid field with the value of the input string
 */
  public void setFileTypeID(String newFileTypeID) {
    fileTypeID = newFileTypeID;
  }

/*
 * get file type extension as a string
 */
  public String getFileTypeExt() {
    return fileTypeExt;
  }

/*
 * set file type extension take a string as input and set the 
 * instance field of filetypeext as the value of the input vield to the method
 */
  public void setFileTypeExt(String newFileTypeExt) {
    fileTypeExt = newFileTypeExt;
  }
}

