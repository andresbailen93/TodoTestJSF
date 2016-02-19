/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package todotest.bean;

import javax.inject.Named;
import javax.enterprise.context.Dependent;

/**
 *
 * @author andresbailen93
 */
@Named(value = "loginBean")
@Dependent
public class LoginBean {

    /**
     * Creates a new instance of LoginBean
     */
    public LoginBean() {
    }
    
}
