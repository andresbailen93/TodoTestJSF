/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package todotest.ejb;

import java.util.List;
import todotest.entities.Test;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import todotest.entities.Usuario;

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

    public List<Test> getActiveTest(Usuario u) {
        //return (List<Test>) em.createNamedQuery("Test.findByActivo").setParameter("activo", 1).getResultList();
        return (List<Test>) em.createQuery("SELECT t FROM Test t WHERE t.activo = 1 AND t.idTest NOT IN(SELECT e.test.idTest FROM Examen e WHERE e.examenPK.dni = :dni)")
                .setParameter("dni", u.getDni())
                .getResultList();
    }

}
