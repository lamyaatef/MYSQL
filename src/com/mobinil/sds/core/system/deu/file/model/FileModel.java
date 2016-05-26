package com.mobinil.sds.core.system.deu.file.model;

import java.sql.ResultSet;
import java.io.Serializable;
/*
 * this class is the file model that has all the setters and getters of the fields that map to the databas entity of file
 */
public class FileModel implements Serializable
{
  private static final String FILE_ID="OUTPUT_FILE_ID";
  private static final String NAME="NAME";
  private static final String FILE_NAME="FILE_NAME";
  private static final String FILE_PATH="DESTINATION";
  private static final String FILE_EXTENSION="FILE_TYPE";
  private static final String FILE_SEPARATOR="SECTOR_SEPARATOR";
  private static final String FILE_TIMESTAMPED="TIME_STAMPED";
  private static final String FILE_ENCODING="ENCODING";
  private static final String FILE_DESCRIPTION="DESCRIPTION";
  private String fileID;
  private String fileName;
  private String filePath;
  private String fileExtension;
  private String separator;
  private String timeStamped;
  private String encoding;
  private String description;
  private String name;

/*
 * constructor take the result set as input and extract from it the values of its fields 
 */
  public FileModel(ResultSet res)
  {
  try
  {
    this.fileID = res.getString(FILE_ID);
    this.name = res.getString(NAME);
    this.fileName = res.getString(FILE_NAME);
    this.filePath = res.getString(FILE_PATH);
    this.fileExtension = res.getString(FILE_EXTENSION);
    this.separator = res.getString(FILE_SEPARATOR);    
    this.timeStamped = res.getString(FILE_TIMESTAMPED);
    this.encoding = res.getString(FILE_ENCODING);
    this.description = res.getString(FILE_DESCRIPTION); 
  }
  catch(Exception  e)
  {
    e.printStackTrace();
  }
  }

  public String getFileID() {
    return fileID;
  }

  public void setFileID(String newFileID) {
    fileID = newFileID;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String newFileName) {
    fileName = newFileName;
  }

  public String getFilePath() {
    return filePath;
  }

  public void setFilePath(String newFilePath) {
    filePath = newFilePath;
  }

  public String getFileExtension() {
    return fileExtension;
  }

  public void setFileExtension(String newFileExtension) {
    fileExtension = newFileExtension;
  }

  public String getSeparator() {
    return separator;
  }

  public void setSeparator(String newSeparator) {
    separator = newSeparator;
  }

  public String getTimeStamped() {
    return timeStamped;
  }

  public void setTimeStamped(String newTimeStamped) {
    timeStamped = newTimeStamped;
  }

  public String getEncoding() {
    return encoding;
  }

  public void setEncoding(String newEncoding) {
    encoding = newEncoding;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String newDescription) {
    description = newDescription;
  }

  public String getName() {
    return name;
  }

  public void setName(String newName) {
    name = newName;
  }
}

