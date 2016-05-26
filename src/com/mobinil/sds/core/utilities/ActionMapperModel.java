package com.mobinil.sds.core.utilities;

import com.mobinil.sds.core.system.Model;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ActionMapperModel extends Model
{
  private String Action_Name;
  private String Handler;
  private String URL;  
  private String Action_Id,Action_Flag;
  
  
  private String Error_URL;
  private String ButtonText;
  
  public ActionMapperModel()
  {
  }
  public ActionMapperModel(String Handler,String URL,String Error_URL,String ButtonText,String Action_Id,String Action_Flag)
  {
     this.Handler = Handler;
     this.URL  = URL;
     this.Error_URL = Error_URL;
     this.ButtonText = ButtonText;
     this.Action_Id = Action_Id;
     this.Action_Flag = Action_Flag;
  }
  public String getHandler()
  {
    return Handler;
  }
  public String getURL()
    {
    return URL;
  }
  public String getError_URL()
  {
    return Error_URL;
  }
  public String getButtonText()
  {
    return ButtonText;
  }
  
   public void setHandler(String Handler)
  {
     this.Handler = Handler;
  }
  public void setURL(String URL)
  {
     this.URL  = URL;
  }
  public void setError_URL(String Error_URL)
  {
     this.Error_URL = Error_URL;
  }
  public void setButtonText(String ButtonText)
  {
     this.ButtonText = ButtonText;
  }
  /**
     * @return the Action_Name
     */
    public String getAction_Name() {
        return Action_Name;
    }

    /**
     * @param Action_Name the Action_Name to set
     */
    public void setAction_Name(String Action_Name) {
        this.Action_Name = Action_Name;
    }

    @Override
    public void fillInstance(ResultSet res) {
        try {
            this.setAction_Name(res.getString("ACTION_NAME"));
            this.setHandler(res.getString("HANDLER_CLASS"));
            this.setURL(res.getString("DESTINATION_URL"));
            this.setAction_Id(res.getString("ACTION_ID"));
            this.setAction_Flag(res.getString("ACTION_FLAG"));
        } catch (SQLException ex) {
            Logger.getLogger(ActionMapperModel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * @return the Action_Id
     */
    public String getAction_Id() {
        return Action_Id;
    }

    /**
     * @param Action_Id the Action_Id to set
     */
    public void setAction_Id(String Action_Id) {
        this.Action_Id = Action_Id;
    }

    /**
     * @return the Action_Flag
     */
    public String getAction_Flag() {
        return Action_Flag;
    }

    /**
     * @param Action_Flag the Action_Flag to set
     */
    public void setAction_Flag(String Action_Flag) {
        this.Action_Flag = Action_Flag;
    }

    
  
}