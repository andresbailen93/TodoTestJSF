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
import javax.persistence.Query;
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

    public List<Test> findByNameAndDni(String testName, Usuario u) {

        Query query = em.createQuery("SELECT t FROM Test t WHERE t.nombre = :nombre AND t.dni = :usuario")
                .setParameter("nombre", testName)
                .setParameter("usuario", u);

        List<Test> listTest = query.getResultList();
        return listTest;
    }

    public List returnTestfromUser(Usuario user) {
        Query query = em.createQuery("SELECT t FROM Test t WHERE t.dni = :nombre").setParameter("nombre", user);
        List<Test> test_list = query.getResultList();
        return test_list;
    }

}
