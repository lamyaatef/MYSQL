package com.mobinil.sds.core.utilities.parser;
import javax.xml.parsers.*; 
import org.w3c.dom.*; 
import com.mobinil.sds.core.utilities.*;
import java.io.*;
import java.util.*;

public class ParsingXML 
{
  public ParsingXML()
  {
   
  }
  public ActionMapperModel parse(String XMLFileName,String ActionKey)
  {
  Utility.logger.debug("Parse function");
    ActionMapperModel actionMapperModel = null;
    try
    {
      MyDOMParserBean domparser = new MyDOMParserBean();
      String x = new File(XMLFileName).getAbsolutePath();
      Utility.logger.debug("file found: " + x);
      Document doc = domparser.getDocument(x);
      Utility.logger.debug("LOOKING FOR ACTION " + ActionKey);
      actionMapperModel = traverseTree(doc,ActionKey);  
    }
    catch(Exception ex)
    {
      Utility.logger.debug(ex.getMessage());
    }
    
    return actionMapperModel;
  }
  
  
  private ActionMapperModel traverseTree(Node node,String ActionValue) throws Exception {
    try{
         if(node == null) {
            Utility.logger.debug("RETURNING NULL");
            return null;
         }
        
         int type = node.getNodeType();
         
         switch (type) {
            // handle document nodes
            case Node.DOCUMENT_NODE: {
              
               traverseTree(((Document)node).getDocumentElement(),ActionValue);
               
               break;
            }
            // handle element nodes
            case Node.ELEMENT_NODE: {
              
               String elementName = node.getNodeName();
               if(node.hasAttributes())
               {
              
                  String attr_Name  = node.getAttributes().item(0).getNodeName();//.getLocalName();
         
                  String attr_Value = node.getAttributes().item(0).getNodeValue();
                  if (attr_Name == null) attr_Name = "Action";
                  if (("Action".equals(attr_Name)) && attr_Value.equals(ActionValue) )
                  {
                    Utility.logger.debug("######## FOUND THE CORRECT ONE");
                    myActionMapperModel = new ActionMapperModel();
                  }
                  
               }
                 NodeList childNodes =  node.getChildNodes();
                 if(childNodes != null) 
                 {
                    int length = childNodes.getLength();
                    for (int loopIndex = 0; loopIndex < length ; loopIndex++)
                    {
                       
                       traverseTree(childNodes.item(loopIndex),ActionValue);
         
                    }
                 }
                 
               break;
            }
            // handle text nodes
            case Node.TEXT_NODE: {
         
               String data            = node.getNodeValue().trim();
               
               String PNode           = (node.getParentNode()).getParentNode().getNodeName();//.getLocalName();
               
               String FirstParent     = (node.getParentNode()).getNodeName();//.getLocalName();
               
               //Utility.logger.debug(FirstParent);
               String NodeAttr_Name   = null;
               String NodeAttr_Value  = null;
                if((node.getParentNode()).getParentNode().hasAttributes())
                {
                  
                  NodeAttr_Name  = (node.getParentNode()).getParentNode().getAttributes().item(0).getLocalName();
                  if (NodeAttr_Name == null) NodeAttr_Name = "Action";
                  NodeAttr_Value = (node.getParentNode()).getParentNode().getAttributes().item(0).getNodeValue();
                  
                  
                 }
                 
                 
               if((data.indexOf("\r")  <0) && (data.length() > 0)) {
                 if (("Action".equals(NodeAttr_Name)) && NodeAttr_Value.equals(ActionValue) )
                 {
                    /*  Utility.logger.debug("INSIDE THE IMPORTANT IF");
                      Utility.logger.debug("FP "+ FirstParent);
                      Utility.logger.debug("DT "+ data);
                      */
                       if("Handler".equals(FirstParent))
                          myActionMapperModel.setHandler(data);
                       else if("URL".equals(FirstParent))
                          myActionMapperModel.setURL(data);
                       else if("Error_URL".equals(FirstParent))
                          myActionMapperModel.setError_URL(data);
                       else if("ButtonText".equals(FirstParent))
                         myActionMapperModel.setButtonText(data);
         
                  }
                 // else
                  //  Utility.logger.debug("ELSE OF action");  
                 
               }
               //else
                //Utility.logger.debug("ELSE OF endline");
            }
            
         }
         //Utility.logger.debug("URL#"+myActionMapperModel.getURL());
         return myActionMapperModel;
    }
    catch (Exception e)
    {
      Utility.logger.debug("INSIDE EXCEPTION " + e.toString());
      e.printStackTrace();
      throw e;
    }
    }//2

       private Vector vector = new Vector();
       private ActionMapperModel myActionMapperModel = null;
    
}//1