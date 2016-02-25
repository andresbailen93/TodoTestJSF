/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package todotest.bean;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
//import javax.faces.bean.SessionScoped;
import todotest.ejb.TestFacade;
import todotest.entities.Test;

/**
 *
 * @author andresbailen93
 */
@ManagedBean
@SessionScoped
public class TestListTeacherBean implements Serializable {
    @EJB
    private TestFacade testFacade;
    @ManagedProperty(value = "#{loginBean}")
    private LoginBean loginBean;
    private List<Test> testList;
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

    public List<Test> getTestList() {
        return testList;
    }

    public void setTestList(List<Test> testList) {
        this.testList = testList;
    }
    
    
    /*
     * Creates a new instance of TestListTeacherBean
     */
    public TestListTeacherBean() {
    }

    public String doListTest() {
        testList = testFacade.returnTestfromUser(loginBean.user);
        return "testListTeacher";
    }
    
    public String doAddQuestion(Test test){
        this.test = test;
        return "addQuestion";
    }
}
