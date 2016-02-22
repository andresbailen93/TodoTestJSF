/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package todotest.bean;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import todotest.ejb.UsuarioFacade;
import todotest.entities.Usuario;

/**
 *
 * @author inftel23
 */
@ManagedBean
@RequestScoped
public class AddUserBean {

    @EJB
    private UsuarioFacade usuarioFacade;
    @ManagedProperty(value = "#{loginBean}")
    private LoginBean loginBean;
    private String dni;
    private String name, surname, password;
    private short isPermit;
    private boolean userExisterror = false;

    /**
     * Creates a new instance of AddUserBean
     */
    public AddUserBean() {
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public short getIsPermit() {
        return isPermit;
    }

    public void setIsPermit(short isPermit) {
        this.isPermit = isPermit;
    }

    public boolean isUserExisterror() {
        return userExisterror;
    }

    public void setUserExisterror(boolean userExisterror) {
        this.userExisterror = userExisterror;
    }
    

    public String doAddUser() {
        Usuario user = new Usuario(dni, name, surname, password, isPermit);
        Usuario bdUser = usuarioFacade.find(user.getDni());
        if (bdUser == null) {
            usuarioFacade.create(user);
            return "mainPageStudent";
        }else{
            userExisterror = true;
            return "addUser";
        }

    }

}
