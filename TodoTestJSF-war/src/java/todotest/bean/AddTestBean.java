/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package todotest.bean;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
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
public class AddTestBean {
    @EJB
    private TestFacade testFacade;
    @ManagedProperty(value="#{loginBean}")
    private LoginBean loginBean;
    private String name, dni, duration, substraction;
    private boolean errorAddTest = false;
    private ArrayList<String> time,config = null;
    private Test test;


    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getSubstraction() {
        return substraction;
    }

    public void setSubstraction(String substraction) {
        this.substraction = substraction;
    }

    public boolean isErrorAddTest() {
        return errorAddTest;
    }

    public void setErrorAddTest(boolean errorAddTest) {
        this.errorAddTest = errorAddTest;
    }

    public ArrayList<String> getTime() {
        return time;
    }

    public void setTime(ArrayList<String> time) {
        this.time = time;
    }

    public ArrayList<String> getConfig() {
        return config;
    }

    public void setConfig(ArrayList<String> config) {
        this.config = config;
    }


    /**
     * Creates a new instance of AddTestBean
     */
    public AddTestBean() {
    }
    
    
    public String doInit (){
        errorAddTest = false;
        time = new ArrayList <String> ();
        config = new ArrayList <String> ();
        time.add("-");
        for(int i=1 ; i<21 ; i++){
            time.add(String.valueOf(i));
        }
        for(int i=0 ; i<6 ; i++){
            config.add(String.valueOf(i));
        }
        return "addTest";
    }
    
    public String doAddTest(){
        errorAddTest = false;
        List<Test> list_test = testFacade.findByNameAndDni(name, loginBean.user);
        if(list_test.isEmpty()){  
            
            test = new Test();
            test.setNombre(name);
            test.setDni(loginBean.user);
            if(duration.equals("-"))
                test.setDuracion(0);
            else
                test.setDuracion(Integer.parseInt(duration));
            test.setResta(Short.parseShort(substraction));
            test.setActivo((short) 0);
            testFacade.create(test);
            
            this.name="";

            return "addQuestion";
            
        }
        else{
            this.errorAddTest = true;
            this.name="";
            return "addTest";
        }
        
        
        
    }
}
