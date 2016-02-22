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
public class TestList {
    @EJB
    private TestFacade testFacade;

    
    @ManagedProperty(value="#{loginBean}")
    private LoginBean loginBean;
    private List <Test> list_test;
    /**
     * Creates a new instance of TestList
     */
    public TestList() {
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
        return "testList";
    }
    
    
}