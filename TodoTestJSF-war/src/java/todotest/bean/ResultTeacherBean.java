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
import todotest.ejb.UsuarioFacade;
import todotest.entities.Test;
import todotest.entities.Usuario;

/**
 *
 * @author inftel23
 */
@ManagedBean
@RequestScoped
public class ResultTeacherBean {
    @EJB
    private UsuarioFacade usuarioFacade;
    
    @ManagedProperty(value="#{loginBean}")
    private LoginBean loginBean;
    private List<Test> testList;

    
    public LoginBean getLoginBean() {    
        return loginBean;
    }

    /**
     * Creates a new instance of ResultTeacherBean
     */
    public void setLoginBean(LoginBean loginBean) {    
        this.loginBean = loginBean;
    }

    public List<Test> getTestList() {
        return testList;
    }

    public void setTestList(List<Test> testList) {
        this.testList = testList;
    }
    
    

    public ResultTeacherBean() {
    }
    
    public String doResultList(){
        Usuario u = usuarioFacade.find(loginBean.user.getDni());
        testList = (List<Test>) u.getTestCollection();
        return "resultsTeacher";
    }
}
