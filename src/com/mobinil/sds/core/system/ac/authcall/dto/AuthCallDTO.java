package com.mobinil.sds.core.system.ac.authcall.dto;
import com.mobinil.sds.core.system.ac.authcall.model.AuthCallModel;
import com.mobinil.sds.core.system.ac.authcall.model.AuthCallParamModel;
import java.io.Serializable;

/*
 * Authentication Call DTO responsible of holding the information of an authentication call
 * as an AuthCallModel object and its parameters as an AuthCallParamModel.
 * 
 * 1- Get the AuthCallModel object.
 * 2- Set the AuthCallModel object.
 * 3- Get the AuthCallParamModel object.
 * 4- Set the AuthCallParamModel object.
 */

public class AuthCallDTO implements Serializable 
{
  private AuthCallModel m_modAuthCallModel;
  private AuthCallParamModel m_modAuthCallParamModel;

  public AuthCallDTO()
  {
  }

/* 
 * 1- Get the AuthCallModel object.
 */
  public AuthCallModel getAuthCallModel()
  {
    return m_modAuthCallModel;
  }

/* 
 * 2- Set the AuthCallModel object.
 */
  public void setAuthCallModel(AuthCallModel argAuthCallModel)
  {
    m_modAuthCallModel = argAuthCallModel;
  }

/* 
 * 3- Get the AuthCallParamModel object.
 */
  public AuthCallParamModel getAuthCallParamModel()
  {
    return m_modAuthCallParamModel;
  }

/* 
 * 4- Set the AuthCallParamModel object.
 */
  public void setAuthCallParamModel(AuthCallParamModel argAuthCallParamModel)
  {
    m_modAuthCallParamModel = argAuthCallParamModel;
  }
}