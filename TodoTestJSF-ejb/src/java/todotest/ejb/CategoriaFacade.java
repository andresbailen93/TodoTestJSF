/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package todotest.ejb;

import java.util.List;
import todotest.entities.Categoria;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author andresbailen93
 */
@Stateless
public class CategoriaFacade extends AbstractFacade<Categoria> {

    @PersistenceContext(unitName = "TodoTestJSF-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CategoriaFacade() {
        super(Categoria.class);
    }

    public List<Categoria> findByName(String topicName) { //Devuelve una categoria apartir de su nombre.
        List<Categoria> list_cat = em.createNamedQuery("Categoria.findByNombre").setParameter("nombre", topicName).getResultList();
        return list_cat;
    }
}
