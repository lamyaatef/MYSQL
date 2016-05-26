package com.mobinil.sds.core.utilities;
import com.mobinil.sds.core.system.security.dao.securityDAO;
import com.mobinil.sds.core.system.security.dto.securityDTO;
import java.util.*;
public class SecurityUtils 
{
  public static final int mandatory = 2;
  public static final int optional = 1;
  
  public static String checkPassInLogin (java.sql.Connection con , String password ,String encriptedPassword,String userName, String userId) {


       Vector mandatoryFields = new Vector();     
       Vector optionalFields = new Vector();
       
          mandatoryFields = securityDAO.getSystemSetting(con,"SDS_SECURITY_CONTROLLER", mandatory + "");
          optionalFields = securityDAO.getSystemSetting(con,"SDS_SECURITY_CONTROLLER", optional + "");
          Integer maxMandatory = securityDAO.getMandatory(con);
          Integer minOptional = securityDAO.getProps(con,"MINIMUM_OPTIONAL");
          int mandatoryCounter = 0;
          int optionalCounter = 0;

          for (int i = 0; i < mandatoryFields.size(); i++)
            {
              securityDTO mandatoryObj = (securityDTO)mandatoryFields.get(i);
              if (mandatoryObj.getSECURITY_STATUS().booleanValue())
                {
                  //System.out.println("security status " + mandatoryObj.getSECURITY_STATUS());
                  boolean abc = false;
                  Integer a = Integer.valueOf(mandatoryObj.getID()) ;
                  switch (a.intValue())
                    {
                      case 1:
                        Integer ss = securityDAO.getProps(con,"PASSWORD_LENGTH");
                        abc = PasswordUtils.checkLength(password, ss).booleanValue();
                        if (!abc)
                          {
                            return "Invalid password length, it must be " + ss + " Character(s).";
                          }
                        else
                          {
                            mandatoryCounter++;
                          }
                        break;
                      case 2:
                        abc = PasswordUtils.checkStringWithTxtAndNum(password).booleanValue();
                        if (!abc)
                          {
                            return "Invalid password not contain text or numbers.";
                          }
                        else
                          {
                            mandatoryCounter++;
                          }
                        break;
                    /*  case 3:
                        abc = PasswordUtils.checkLastPassword(encriptedPassword, userId).booleanValue();
                        if (!abc)
                          {
                            return "This password contain in last " + 
                              securityDAO.getProps("LAST_PASSWORD_COUNT") + " password(s).";
                          }
                        else
                          {
                            mandatoryCounter++;
                          }
                        break;
                    */
                      case 4:
                        abc = PasswordUtils.checkSequanceAlphabetic(password).booleanValue();
                        if (!abc)
                          {
                            return "Invalid sequence characters.";
                          }
                        else
                          {
                            mandatoryCounter++;
                          }
                        break;
                      case 5:
                        abc = PasswordUtils.checkSimilarChar(password).booleanValue();
                        if (!abc)
                          {

                            return "Invalid similar characters.";

                          }
                        else
                          {
                            mandatoryCounter++;
                          }
                        break;
                      case 6:
                        abc = PasswordUtils.checkUpLow(password).booleanValue();
                        if (!abc)
                          {
                            return "Invalid password lower and upper cases.";

                          }
                        else
                          {
                            mandatoryCounter++;
                          }
                        break;
                      case 7:
                        abc = PasswordUtils.checkIdInPassword(password, userName).booleanValue();
                        if (!abc)
                          {
                            return "This password contains user name.";

                          }
                        else
                          {
                            mandatoryCounter++;
                          }
                        break;
                      case 8:
                        abc = PasswordUtils.checkLock(con,userId).booleanValue();
                        //System.out.println("abc lock is "+abc);
                        
                        if (!abc)
                          {
                            return "This user Locked.";

                          }
                        else
                          {
                            mandatoryCounter++;
                          }
                        break;
                      case 9:
                        abc = PasswordUtils.checkExpire(userId).booleanValue();
                        //System.out.println("abc expire is "+abc);
                        if (!abc)
                          {
                            return "This user Expired.";

                          }
                        else
                          {
                            mandatoryCounter++;
                          }
                        break;

                    }
                }
            }
         
              for (int i = 0; i < optionalFields.size(); i++)
                {
                  securityDTO optionalObj = (securityDTO)optionalFields.get(i);
                  if (optionalObj.getSECURITY_STATUS().booleanValue())
                    {
                      //System.out.println("security status " + optionalObj.getSECURITY_STATUS());
                      boolean abc = false;
                      Integer a = Integer.valueOf(optionalObj.getID());
                      switch (a.intValue())
                        {
                          case 1:
                            Integer ss = securityDAO.getProps(con,"PASSWORD_LENGTH");
                            abc = PasswordUtils.checkLength(password, ss).booleanValue();
                            if (abc)
                              {
                                optionalCounter++;
                              }
                            break;
                          case 2:
                            abc = PasswordUtils.checkStringWithTxtAndNum(password).booleanValue();
                            if (abc)
                              {
                                optionalCounter++;
                              }
                            break;
                          case 3:
                            abc = PasswordUtils.checkLastPassword(encriptedPassword, userId).booleanValue();
                            if (abc)
                              {
                                optionalCounter++;
                              }
                            break;

                          case 4:
                            abc = PasswordUtils.checkSequanceAlphabetic(password).booleanValue();
                            if (abc)
                              {
                                optionalCounter++;
                              }
                            break;
                          case 5:
                            abc = PasswordUtils.checkSimilarChar(password).booleanValue();
                            if (abc)
                              {
                                optionalCounter++;
                              }
                            break;
                          case 6:
                            abc = PasswordUtils.checkUpLow(password).booleanValue();
                            if (abc)
                              {
                                optionalCounter++;
                              }
                            break;
                          case 7:
                            abc = PasswordUtils.checkIdInPassword(password, userName).booleanValue();
                            if (abc)
                              {
                                optionalCounter++;
                              }
                            break;
                          case 8:
                            abc = PasswordUtils.checkLock(con,userId).booleanValue();
                            if (abc)
                              {
                                optionalCounter++;
                              }
                            break;
                          case 9:
                            abc = PasswordUtils.checkExpire(userId).booleanValue();
                            if (abc)
                              {
                                optionalCounter++;
                              }
                            break;

                        }
                    }
                }
                 
           
              if (optionalCounter < minOptional.intValue() && optionalCounter!=0&&mandatoryFields.size()!=0&&optionalFields.size()!=0)
                {
                  return "Wrong Password.";
                }



          return "";

        
  }
  
    public static String checkPass(java.sql.Connection con,String password,String encriptedPassword, String userName,String userId)
  {

 Vector mandatoryFields = new Vector();     
 Vector optionalFields = new Vector();
 
    mandatoryFields = securityDAO.getSystemSetting(con,"SDS_SECURITY_CONTROLLER", mandatory + "");
    optionalFields = securityDAO.getSystemSetting(con,"SDS_SECURITY_CONTROLLER", optional + "");
    Integer maxMandatory = securityDAO.getMandatory(con);
    Integer minOptional = securityDAO.getProps(con,"MINIMUM_OPTIONAL");
    int mandatoryCounter = 0;
    int optionalCounter = 0;

    for (int i = 0; i < mandatoryFields.size(); i++)
      {
        securityDTO mandatoryObj = (securityDTO)mandatoryFields.get(i);
        if (mandatoryObj.getSECURITY_STATUS().booleanValue())
          {
            //System.out.println("security status " + mandatoryObj.getSECURITY_STATUS());
            boolean abc = false;
            Integer a = Integer.valueOf(mandatoryObj.getID()) ;
            switch (a.intValue())
              {
                case 1:
                  Integer ss = securityDAO.getProps(con,"PASSWORD_LENGTH");
                  abc = PasswordUtils.checkLength(password, ss).booleanValue();
                  if (!abc)
                    {
                      return "Invalid password length, it must be " + ss + " Character(s).";
                    }
                  else
                    {
                      mandatoryCounter++;
                    }
                  break;
                case 2:
                  abc = PasswordUtils.checkStringWithTxtAndNum(password).booleanValue();
                  if (!abc)
                    {
                      return "Invalid password not contain text or numbers.";
                    }
                  else
                    {
                      mandatoryCounter++;
                    }
                  break;
                case 3:
                  abc = PasswordUtils.checkLastPassword(encriptedPassword, userId).booleanValue();
                  if (!abc)
                    {
                      return "This password contain in last " + 
                        securityDAO.getProps(con,"LAST_PASSWORD_COUNT") + " password(s).";
                    }
                  else
                    {
                      mandatoryCounter++;
                    }
                  break;

                case 4:
                  abc = PasswordUtils.checkSequanceAlphabetic(password).booleanValue();
                  if (!abc)
                    {
                      return "Invalid sequence characters.";
                    }
                  else
                    {
                      mandatoryCounter++;
                    }
                  break;
                case 5:
                  abc = PasswordUtils.checkSimilarChar(password).booleanValue();
                  if (!abc)
                    {

                      return "Invalid similar characters.";

                    }
                  else
                    {
                      mandatoryCounter++;
                    }
                  break;
                case 6:
                  abc = PasswordUtils.checkUpLow(password).booleanValue();
                  if (!abc)
                    {
                      return "Invalid password lower and upper cases.";

                    }
                  else
                    {
                      mandatoryCounter++;
                    }
                  break;
                case 7:
                  abc = PasswordUtils.checkIdInPassword(password, userName).booleanValue();
                  if (!abc)
                    {
                      return "This password contains user name.";

                    }
                  else
                    {
                      mandatoryCounter++;
                    }
                  break;
                case 8:
                  abc = PasswordUtils.checkLock(con,userId).booleanValue();
                  //System.out.println("abc lock is "+abc);
                  
                  if (!abc)
                    {
                      return "This user Locked.";

                    }
                  else
                    {
                      mandatoryCounter++;
                    }
                  break;
                case 9:
                  abc = PasswordUtils.checkExpire(userId).booleanValue();
                  //System.out.println("abc expire is "+abc);
                  if (!abc)
                    {
                      return "This user Expired.";

                    }
                  else
                    {
                      mandatoryCounter++;
                    }
                  break;

              }
          }
      }
   
        for (int i = 0; i < optionalFields.size(); i++)
          {
            securityDTO optionalObj = (securityDTO)optionalFields.get(i);
            if (optionalObj.getSECURITY_STATUS().booleanValue())
              {
                //System.out.println("security status " + optionalObj.getSECURITY_STATUS());
                boolean abc = false;
                Integer a = Integer.valueOf(optionalObj.getID());
                switch (a.intValue())
                  {
                    case 1:
                      Integer ss = securityDAO.getProps(con,"PASSWORD_LENGTH");
                      abc = PasswordUtils.checkLength(password, ss).booleanValue();
                      if (abc)
                        {
                          optionalCounter++;
                        }
                      break;
                    case 2:
                      abc = PasswordUtils.checkStringWithTxtAndNum(password).booleanValue();
                      if (abc)
                        {
                          optionalCounter++;
                        }
                      break;
                    case 3:
                      abc = PasswordUtils.checkLastPassword(encriptedPassword, userId).booleanValue();
                      if (abc)
                        {
                          optionalCounter++;
                        }
                      break;

                    case 4:
                      abc = PasswordUtils.checkSequanceAlphabetic(password).booleanValue();
                      if (abc)
                        {
                          optionalCounter++;
                        }
                      break;
                    case 5:
                      abc = PasswordUtils.checkSimilarChar(password).booleanValue();
                      if (abc)
                        {
                          optionalCounter++;
                        }
                      break;
                    case 6:
                      abc = PasswordUtils.checkUpLow(password).booleanValue();
                      if (abc)
                        {
                          optionalCounter++;
                        }
                      break;
                    case 7:
                      abc = PasswordUtils.checkIdInPassword(password, userName).booleanValue();
                      if (abc)
                        {
                          optionalCounter++;
                        }
                      break;
                    case 8:
                      abc = PasswordUtils.checkLock(con,userId).booleanValue();
                      if (abc)
                        {
                          optionalCounter++;
                        }
                      break;
                    case 9:
                      abc = PasswordUtils.checkExpire(userId).booleanValue();
                      if (abc)
                        {
                          optionalCounter++;
                        }
                      break;

                  }
              }
          }
           
     
        if (optionalCounter < minOptional.intValue() &&mandatoryFields.size()!=0&&optionalFields.size()!=0)
          {
            return "New password does not achieve security rules.";
          }



    return "";

  }
}