/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package todotest.bean;

import todotest.entities.Test;

/**
 *
 * @author alejandroruiz
 */
public class TestManager {
    
    private Test test;
    private Boolean activo;

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public TestManager(Test test, Boolean activo) {
        this.test = test;
        this.activo = activo;
    }
    
    
    
}
