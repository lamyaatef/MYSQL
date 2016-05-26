package com.mobinil.sds.core.utilities;
import java.io.*;
import java.security.*;
import sun.misc.*;
import java.util.*;

public class EncryptUtil 
{
  public EncryptUtil()
  {
  }

  static Random r;

  static
  {
    r = new Random(System.currentTimeMillis());
  }

  
private static int getRandomNum()
{
  int num = r.nextInt();
  if (num<0)
  num*=-1;

  num = num % 10;
  return num;
}

  public static boolean comparePasswords(String pass1, String pass2)
  {
    if (getStripedPassword(pass1).compareTo(getStripedPassword(pass2))==0)
    {
      return true; 
    }
    else
    return false; 
  
  }
  
    public static synchronized String encryptIt(String plainText)
  {
    MessageDigest md = null;
    try {
      md = MessageDigest.getInstance("MD5");
    }
    catch (NoSuchAlgorithmException e) {
    }
    try {
      md.update(plainText.getBytes("UTF-8"));
    }
    catch (Exception e) {
    }

    byte raw[] = md.digest();
    String hash = (new BASE64Encoder()).encode(raw);
    //Utility.logger.debug(hash);
    
    /* commented because the new security enhancments
    if ((getRandomNum()%2)==1)
    {
      StringBuffer buf = new StringBuffer(hash);
      
      buf.insert(oddFirstLoc,getRandomNum());
      buf.insert(oddSecondLoc,getRandomNum());
      buf.insert(buf.length(),1);
      hash = buf.toString();
    }
    else
    {
      StringBuffer buf = new StringBuffer(hash); 
      
      buf.insert(evenFirstLoc,getRandomNum());
      buf.insert(evenSecondLoc,getRandomNum());
      buf.insert(buf.length(),0);
      hash = buf.toString();
    }
    */
    return hash;
  }

  public static String getStripedPassword(String pass)
  {
    StringBuffer buf = new StringBuffer(pass);
    //Utility.logger.debug(buf.charAt(buf.length()-1));
    if (pass.length()==24)
    return pass;
    
    
    if (buf.charAt(buf.length()-1)=='1')    
    {
      buf.delete(buf.length()-1,buf.length());
      buf.delete(oddSecondLoc,oddSecondLoc+1);
      buf.delete(oddFirstLoc,oddFirstLoc+1);
      
      
    }
    else if (buf.charAt(buf.length()-1)=='0')
    {            
      buf.delete(buf.length()-1,buf.length());
      buf.delete(evenSecondLoc,evenSecondLoc+1);
      buf.delete(evenFirstLoc,evenFirstLoc+1);
    }
    return buf.toString();
  }

  private static final int evenSecondLoc = 6;
  private static final int evenFirstLoc = 2;
  private static final int oddFirstLoc = 4; 
  private static final int oddSecondLoc = 9;
  

  public static void main(String [] args)
  {
    String test =  "http:\\www.cnn.com";
    String temp1 = encryptIt(test);
    //Utility.logger.debug(temp1 );    
    //Utility.logger.debug(getStripedPassword(temp1));
    
    
    String temp2 = encryptIt(test);

    //Utility.logger.debug(temp2);

    //Utility.logger.debug(getStripedPassword(temp1));
    //Utility.logger.debug(getStripedPassword(temp2));
    

    //Utility.logger.debug(temp1);
    Utility.logger.debug(""+(temp1.compareTo(temp2)==0));

    Utility.logger.debug(""+(getStripedPassword(temp1).compareTo(getStripedPassword(temp2))==0));
    Utility.logger.debug(""+(comparePasswords(temp1,temp2)));

  }
}








