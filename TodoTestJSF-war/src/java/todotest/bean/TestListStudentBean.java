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
    @ManagedProperty(value="#{doTestbean}")
    private DoTestBean doTestBean;
    private List <Test> list_test;
    private Test selectedTest;
    private boolean started = false;
    private boolean finished = false;
    private boolean generatePdf = false;
    private boolean errTestWithoutQuestions = false;
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

    public boolean isErrTestWithoutQuestions() {
        return errTestWithoutQuestions;
    }

    public void setErrTestWithoutQuestions(boolean errTestWithoutQuestions) {
        this.errTestWithoutQuestions = errTestWithoutQuestions;
    }
    
    
    
    public String doListTest(){
        list_test = this.testFacade.getActiveTest(loginBean.user);
        return "testListStudent";
    }
    
    public String doTest(Test t) {
        if (t.getPreguntaCollection().size() > 0) {
            selectedTest = t;
            started = false;
            finished = false;
            generatePdf = false;
            errTestWithoutQuestions = false;
            return "testAnswer?faces-redirect=true";
        }
        errTestWithoutQuestions = true;
        return "testListStudent";
    }

    public Test getSelectedTest() {
        return selectedTest;
    }

    public void setSelectedTest(Test selectedTest) {
        this.selectedTest = selectedTest;
    }

    public DoTestBean getDoTestBean() {
        return doTestBean;
    }

    public void setDoTestBean(DoTestBean doTestBean) {
        this.doTestBean = doTestBean;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public boolean isGeneratePdf() {
        return generatePdf;
    }

    public void setGeneratePdf(boolean generatePdf) {
        this.generatePdf = generatePdf;
    }
    
    
    
}
