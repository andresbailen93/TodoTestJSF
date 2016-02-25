/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package todotest.bean;

import javax.inject.Named;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import todotest.ejb.TestFacade;
import todotest.entities.Test;

/**
 *
 * @author andresbailen93
 */
@ManagedBean
@SessionScoped
public class TestListTeacherBean implements Serializable {
    @ManagedProperty(value = "#{loginBean}")
    private LoginBean loginBean;
    private Test test;

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }
    
    
    public LoginBean getLoginBean() {
        return loginBean;
    }
    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }
    /*
     * Creates a new instance of TestListTeacherBean
     */
    public TestListTeacherBean() {
    }

    public String doListTest() {
        return "testListTeacher";
    }
    
    public String doAddQuestion(Test test){
        this.test = test;
        return "addQuestion";
    }
}
