/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package todotest.bean;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import todotest.ejb.UsuarioFacade;
import todotest.entities.Usuario;

/**
 *
 * @author inftel23
 */
@ManagedBean
@RequestScoped
public class LoginBean {
    @EJB
    private UsuarioFacade usuarioFacade;
    
    private String dni = "33333333P";
    private String password = "1234";

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
    
    /**
     * Creates a new instance of LoginBean
     */
    public LoginBean() {
    }
    
    public String doLogin(){
        Usuario user = usuarioFacade.find(this.dni);
        if (user == null)
            return "index";
        if (!user.getPassword().equals(this.password))
            return "index";
        if (user.getEsProf() == 1)
            return "mainPageTeacher";
        else
            return "mainPageStudent";
    }
    
}