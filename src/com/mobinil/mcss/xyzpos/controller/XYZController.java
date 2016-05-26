/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.xyzpos.controller;

import com.mobinil.mcss.dp.dpManagement.model.GenUser;
import com.mobinil.mcss.xyzpos.model.POS;
import com.mobinil.mcss.xyzpos.services.XYZService;
import com.mobinil.sds.web.interfaces.InterfaceKey;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Gado
 */
@Controller
@RequestMapping("/xyz")
public class XYZController {
    
    @Autowired
    private XYZService xyzService;
    
    @RequestMapping(value = "/POSListManagement.htm", method = RequestMethod.GET)
    public ModelAndView getListOfList(HttpServletRequest request) {
        
        List listpos = xyzService.getALLPOSs();        
        String userId = request.getParameter(InterfaceKey.HASHMAP_KEY_USER_ID);
        request.getSession().setAttribute(InterfaceKey.HASHMAP_KEY_USER_ID, userId);
        ModelAndView mv = new ModelAndView();
        mv.addObject("results", listpos);
        return mv;
    }
    
    @RequestMapping(value = "/delete.htm", method = RequestMethod.GET)
    public ModelAndView deletePosFromList(HttpServletRequest request) {
        
        String posCode = request.getParameter("hiddendl");
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute(InterfaceKey.HASHMAP_KEY_USER_ID);
        GenUser user = new GenUser();
        user.setUserId(Long.valueOf(userId));
        
        POS pos = new POS();
        pos.setUSER_ID(user);
        pos.setPosCode(posCode);
        xyzService.deletePOS(pos);
        List listpos = xyzService.getALLPOSs();        
        ModelAndView mv = new ModelAndView("/xyz/POSListManagement");
        mv.addObject("results", listpos);
        
        return mv;
    }
    
    @RequestMapping(value = "/add.htm", method = RequestMethod.GET)
    public ModelAndView addPosToList(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute(InterfaceKey.HASHMAP_KEY_USER_ID);
        String posCode = request.getParameter("poscode");
        POS pos = new POS();
        pos.setPosCode(posCode);
        GenUser user = new GenUser();
        
        if (userId == null) {
            user.setUserId(426L);
        } else {
            user.setUserId(Long.valueOf(userId));
        }
        pos.setUSER_ID(user);
        boolean result = xyzService.addPOS(pos);
        String cause;
        if (result) {
            cause = "POS inserted sucessfully";
        } else {
            cause = "Invalid POS Code";
        }
        
        List listpos = xyzService.getALLPOSs();        
        ModelAndView mv = new ModelAndView("/xyz/Result");
        mv.addObject("cause", cause);
        
        return mv;
    }
}
