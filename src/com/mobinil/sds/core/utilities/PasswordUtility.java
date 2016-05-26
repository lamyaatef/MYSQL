package com.mobinil.sds.core.utilities;

public class PasswordUtility  {
private static final String Chars = "0ECVh158xrFSmjonzBA6cDKGOwud7gsWPLfiQRqaIvH3NklZy4JptMUXY2bTe9";

  /**
     *  This function is used to encrypt the password, and return the encrypted
     *  password
     *
     *  @param plainText is the text to be encrypted
     *
     *  @return String containing the encrypted password
     */
    public static String encrypt ( String plainText,int base)
    {
    if (base%2!=1)
    base+=1;
       int randomLength =plainText.length()%base;
        int cipherLength =plainText.length()+randomLength;
        String ret  = "";
        int    nLength   = plainText.length(),
               nLength1  = Chars.length();
        for (int i = 0; i < plainText.length(); i++) {

                  for ( int additional=1; additional<base;additional++)
                  
                    {
                      if(randomLength==additional)
                  //only enters when 3    
                      {
                        for(int n=0;n<additional;n++)
                        {
                          if(ret.length()==((cipherLength*n)/additional))
                           {
                           ret+=Chars.charAt(i);
                           }
                          ;
                        }
                      }
                    }
            int i1 = Chars.indexOf(plainText.charAt(i));

            if(i1 == -1)
                ret = ret + plainText.charAt(i);
            else
                if ( (i1+nLength) < nLength1 )
                    ret = ret + Chars.charAt( (i1 + nLength) );
                else
                    ret = ret + Chars.charAt( (nLength - ( nLength1 - i1 )) );

        }
        return ret;
    }

    /**
     *  This function is used to decrypt the password, and return the decrypted
     *  password
     *
     *  @param cipherText is the text to be decrypted
     *
     *  @return String containing the decrypted password
     */
     
    public static String decrypt ( String cipherText,int base)
    {
    if (base%2!=1)
    base+=1;
    int randomLength=PasswordUtility.calcAdditional(cipherText.length(),base);
        String ret  = "";
        int    nLength   = cipherText.length()-randomLength,
               nLength1  = Chars.length();

        for (int i = 0; i < cipherText.length(); i++) {
for ( int additional=1; additional<base;additional++)
                  
                    {
                      if(randomLength==additional)
                  //only enters when 3    
                      {
                        for(int n=0;n<additional;n++)
                        {
                          if(i==((cipherText.length()*n)/additional))
                            i++;
                        }
                      }
                    }
         char ch=cipherText.charAt(i);
            int i1 = Chars.indexOf( ch );
            if ( i1 == -1 )
                ret = ret + cipherText.charAt(i);
            else
                if( ((i1 - nLength ) >=  0 ) )
                    ret = ret + Chars.charAt((i1 - nLength));
                else
                    ret = ret + Chars.charAt(((nLength1 + i1 ) - nLength));
        }

        return ret;
    }

    public static int calcAdditional(int n,int base)
    {
      for( int size = 1;size <= n; size++)
      {
        if(size+(size%base)==n)
        return n-size;
      }
      return -1;
    }



    
    public static void main (String [] args)
    {
    String st="abcdefghijklmnopqrstuvwxyz123456789ABFGHIJKLMNOPQRSTUVWXYZ";//26+7=36
      Utility.logger.debug("************"+PasswordUtility.encrypt("sand",5));
      Utility.logger.debug("************"+PasswordUtility.decrypt("0fE3C6VW",5));
      //Utility.logger.debug(PasswordFactory.calcAdditional(1));
    }
    
}