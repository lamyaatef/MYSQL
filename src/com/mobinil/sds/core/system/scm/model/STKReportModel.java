/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mobinil.sds.core.system.scm.model;

import com.mobinil.sds.core.system.Model;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 *
 * @author Ahmed Adel
 */
public class STKReportModel extends Model{

    private Date date;
    private String userMail;
    private int quantity;

    public void setDate(Date date)
    {

        this.date=date;
    }
    public void setUserMail(String userMail)
    {
        this.userMail=userMail;
    }
    public void setQuantity(int quantity)
    {
        this.quantity=quantity;
    }
    public Date getDate()
    {
    return this.date;
    }
     public String getUserMail()
    {
    return this.userMail;
    }
      public int getQuantity()
    {
    return this.quantity;
    }


    @Override
    public void fillInstance(ResultSet res) {
        try{
       this.setDate(res.getDate("DATE"));
       this.setUserMail(res.getString("USER"));
       this.setQuantity(res.getInt("COUNT"));
        }catch(SQLException se){
            se.printStackTrace();
        }

    }

}
