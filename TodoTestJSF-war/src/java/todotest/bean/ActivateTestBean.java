/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package todotest.bean;

import todotest.aux.TestManager;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import todotest.ejb.TestFacade;
import todotest.entities.Test;

/**
 *
 * @author alejandroruiz
 */
@ManagedBean
@RequestScoped
public class ActivateTestBean {

    @EJB
    private TestFacade testFacade;
    
    @ManagedProperty(value="#{loginBean}")
    private LoginBean loginBean;
    
    private List<Test> list_test;
    private boolean msg = false;

    private ArrayList<TestManager> list_test_manager = null;

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

    public ArrayList<TestManager> getList_test_manager() {
        return list_test_manager;
    }

    public void setList_test_manager(ArrayList<TestManager> list_test_manager) {
        this.list_test_manager = list_test_manager;
    }

    public boolean isMsg() {
        return msg;
    }

    public void setMsg(boolean msg) {
        this.msg = msg;
    }
    
    public ActivateTestBean() {
    }
    
    @PostConstruct
    public void doListTest(){
        msg = false;
        list_test = testFacade.returnTestfromUser(loginBean.user);
        list_test_manager = new ArrayList<> ();
         for(Test test: list_test){
            TestManager tm = new TestManager(test, test.getActivo()!=0);
            list_test_manager.add(tm);
        }

        //return "activateTest";
    }
    
   public String doActualizar(){
      for(TestManager t : list_test_manager){
          Test test = t.getTest();
          test.setActivo((short) ((t.getActivo()) ? 1 :0));
          testFacade.edit(test);    
      }
      msg = true;
      return "activateTest";
       
    }
    
}
