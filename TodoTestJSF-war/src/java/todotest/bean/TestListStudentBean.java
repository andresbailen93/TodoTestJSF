/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package todotest.bean;

import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import todotest.ejb.TestFacade;
import todotest.entities.Test;

/**
 *
 * @author inftel23
 */
@ManagedBean
@SessionScoped
public class TestListStudentBean {
    @EJB
    private TestFacade testFacade;

    
    @ManagedProperty(value="#{loginBean}")
    private LoginBean loginBean;
    private List <Test> list_test;
    private Test selectedTest;
    /**
     * Creates a new instance of TestList
     */
    public TestListStudentBean() {
    }
    
    public List<Test> getList_test() {
        return list_test;
    }

    public void setList_test(List<Test> list_test) {
        this.list_test = list_test;
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }
    
    public String doListTest(){
        list_test = this.testFacade.getActiveTest(loginBean.user);
        return "testListStudent";
    }
    
    public String doTest(Test t) {
        selectedTest = t;
        return "testAnswer";
    }

    public Test getSelectedTest() {
        return selectedTest;
    }

    public void setSelectedTest(Test selectedTest) {
        this.selectedTest = selectedTest;
    }
    
    
}
