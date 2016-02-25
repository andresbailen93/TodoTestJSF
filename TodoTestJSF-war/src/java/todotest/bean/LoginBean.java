/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package todotest.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import todotest.aux.CurrentTest;
import todotest.ejb.UsuarioFacade;
import todotest.entities.Test;
import todotest.entities.Usuario;

/**
 *
 * @author inftel23
 */
@ManagedBean
@SessionScoped
public class LoginBean implements Serializable{

    @EJB
    private UsuarioFacade usuarioFacade;

    
    private String dni = "33333333P";
    private String password = "1234";
    protected Usuario user;
    protected Boolean error = false;
    private Test testAdded;
    //private CurrentTest currentTest;
    //error 0 -> no hay error
    //error 1 -> usuario/contraseña incorrecta

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public Test getTestAdded() {
        return testAdded;
    }

    public void setTestAdded(Test testAdded) {
        this.testAdded = testAdded;
    }
    
    

    /**
     * Creates a new instance of LoginBean
     */
    public LoginBean() {
    }
    
    public void init() {
        if (this.user == null)
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String doLogin() {
        
        
        
        this.user = usuarioFacade.find(this.dni);
        this.error = false;
        if (user == null) {
            this.error = true;
            return "index";
        }
        if (!user.getPassword().equals(this.password)) {
            this.error = true;
            return "index";
        }
        if (user.getEsProf() == 1) {
            return "mainPageTeacher";
        } else {
            return "mainPageStudent";
        }
    }
    
    public String doLogout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index";
    }	

}
