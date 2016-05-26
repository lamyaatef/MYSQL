/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.spring.util;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *
 * @author Gado
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface GExcel {
    int excelColumn();
}
