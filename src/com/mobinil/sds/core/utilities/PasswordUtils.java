package com.mobinil.sds.core.utilities;
import com.mobinil.sds.core.system.security.dao.passwordDAO;
public class PasswordUtils 
{
    public static Boolean checkSimilarChar(String password)
  {
  Boolean aa = Boolean.TRUE;
    password = password.toLowerCase();
    char[] passChars = password.toCharArray();
    Character nextPassChar = null;

    for (int i = 0; i < passChars.length; i++)
      {       
        Character passChar = new Character(passChars[i]);
        if (i == passChars.length - 1)
          {
            break;
          }
        else
          {
            nextPassChar = new Character(passChars[i + 1]);
          }

        if (passChar.compareTo(nextPassChar) == 0)
          {
            return aa.FALSE;
          }

      }
    return aa.TRUE;
  }

  public static Boolean checkSequanceAlphabetic(String password)
  {
    Boolean aa = Boolean.TRUE;
    int seqFlag = 0;
    password = password.toLowerCase();
    char[] passChars = password.toCharArray();
    Character currentPassChar = null;
    Character nextPassChar = null;
    for (int i = 0; i < passChars.length; i++)
      {
        if (i == passChars.length - 1)
          {
            break;
          }
        currentPassChar = new Character(passChars[i]);
        nextPassChar = new Character(passChars[i + 1]);

        if (currentPassChar.toString().compareTo(nextPassChar.toString()) == -1)
          {
            seqFlag = seqFlag + 1;
          }
      }

    if (seqFlag == 0)
      {
        return aa.TRUE;
      }
    else
      {
        return aa.FALSE;
      }
  }

  public static Boolean checkStringWithTxtAndNum(String password)
  {
    Boolean aa = Boolean.TRUE;
    int flagNumCount = 0;
    int flagCharCount = 0;

    char[] passChars = password.toCharArray();
    Character passChar = null;
    for (int i = 0; i < passChars.length; i++)
      {
        passChar = new Character(passChars[i]);
        try
          {
            Integer.parseInt(passChar.toString());
            flagNumCount = flagNumCount + 1;
          }
        catch (Exception e)
          {
            flagCharCount = flagCharCount + 1;
          }
      }

    if (flagCharCount != 0)
      {
        if (flagNumCount == 0)
          {
            return aa.FALSE;
          }
        else
          {
            return aa.TRUE;
          }
      }
    else
      {
        return aa.FALSE;
      }
  }

  public static Boolean checkIdInPassword(String password, String Id)
  {
        Boolean aa = Boolean.TRUE;
    password = password.toLowerCase();
    Id = Id.toLowerCase();
    
    for (int i = 0;i<password.length();i++){
      String pass1 = password.substring(i,password.length());
//      System.out.println("pass1 is "+pass1);
    for (int y=0;y<pass1.length();y++){
      String pass2 = pass1.substring(0,y+1);
//    System.out.println("pass is "+pass2);
    if (Id.compareTo(pass2)==0)
      {
        aa = Boolean.FALSE;
      }      
    }
    }
    return aa;
  }

  public static Boolean checkLength(String password, Integer passLength)
  {
    Boolean aa = Boolean.TRUE;
    if (password.length() < passLength.intValue())
      {
        return aa.FALSE;
      }
    else
      {
        return aa.TRUE;
      }

  }

  public static Boolean checkUpLow(String password)
  {
    Boolean aa = Boolean.TRUE;
    char[] passChars = password.toCharArray();    
    Character currPassChar = null;
    int isLower = 0;
    int isUpper = 0;

    for (int i = 0; i < passChars.length; i++)
      {
        currPassChar = new Character(passChars[i]);
        
        if (Character.isLetter(currPassChar.charValue()))
          {
            if (Character.isLowerCase(currPassChar.charValue()))
              {
                isLower += 1;
              }
            if (Character.isUpperCase(currPassChar.charValue()))
              {
                isUpper += 1;
              }

          }

        if (i == (passChars.length - 1))
          {
            if (isLower == 0 || isUpper == 0)
              {
                return aa.FALSE;
              }
            else
              {
                return aa.TRUE;
              }

          }


      }
    return null;

  }

  public static Boolean checkLastPassword(String passEncripted, String sysUserId)
  {  
    return passwordDAO.checkLastPassword(passEncripted, sysUserId);
  }

  public static Boolean checkLock(java.sql.Connection con, String userId)
  {
    return passwordDAO.checkLock( userId,con);
  }

  public static Boolean checkExpire(String userId)
  {
    return passwordDAO.checkExpire(userId);
  }
}