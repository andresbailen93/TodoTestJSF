/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package todotest.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author inftel23
 */
@ManagedBean
@RequestScoped
public class ResultTeacherBean {
    @ManagedProperty(value="#{loginBean}")
    private LoginBean loginBean;

    
    public LoginBean getLoginBean() {    
        return loginBean;
    }

    /**
     * Creates a new instance of ResultTeacherBean
     */
    public void setLoginBean(LoginBean loginBean) {    
        this.loginBean = loginBean;
    }

    public ResultTeacherBean() {
    }
    
    public String doResultList(){
        return "resultsTeacher";
    }
}
