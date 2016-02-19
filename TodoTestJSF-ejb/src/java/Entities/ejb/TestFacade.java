/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities.ejb;

import Entities.Test;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author andresbailen93
 */
@Stateless
public class TestFacade extends AbstractFacade<Test> {

    @PersistenceContext(unitName = "TodoTestJSF-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TestFacade() {
        super(Test.class);
    }
    
}
