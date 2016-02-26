/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package todotest.bean;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import todotest.ejb.UsuarioFacade;
import todotest.entities.Examen;
import todotest.entities.Usuario;

/**
 *
 * @author inftel23
 */
@ManagedBean
@RequestScoped
public class ResultStudentBean {

    @EJB
    private UsuarioFacade usuarioFacade;
    private List<Examen> exam_list;
    private int success, fails, totalTest;
    private BigDecimal average;
    @ManagedProperty(value = "#{loginBean}")
    private LoginBean loginBean;


    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public int getFails() {
        return fails;
    }

    public void setFails(int fails) {
        this.fails = fails;
    }

    public int getTotalTest() {
        return totalTest;
    }

    public void setTotalTest(int totalTest) {
        this.totalTest = totalTest;
    }

    public BigDecimal getAverage() {
        return average;
    }

    public void setAverage(BigDecimal average) {
        this.average = average;
    }

    public UsuarioFacade getUsuarioFacade() {
        return usuarioFacade;
    }

    public void setUsuarioFacade(UsuarioFacade usuarioFacade) {
        this.usuarioFacade = usuarioFacade;
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public List<Examen> getExam_list() {
        return exam_list;
    }

    public void setExam_list(List<Examen> exam_list) {
        this.exam_list = exam_list;
    }

    public ResultStudentBean() {
    }

    public String doResultList() {
        // Problema de caché si se accede por loginBean
        Usuario u =  this.usuarioFacade.find(loginBean.user.getDni());
        this.exam_list = (List<Examen>) u.getExamenCollection();
        
        this.success = usuarioFacade.totalSuccess(u);
        try {
            this.average = new BigDecimal(usuarioFacade.average(u)).setScale(2, RoundingMode.CEILING);
        } catch (NumberFormatException e) {
            this.average = new BigDecimal(0);
        }
        this.totalTest = usuarioFacade.totalTest(u);
        this.fails = usuarioFacade.totalFail(u);
        
        return "resultsStudent";
    }

}
