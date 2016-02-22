/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package todotest.ejb;

import java.util.Collection;
import todotest.entities.Usuario;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import todotest.entities.Examen;

/**
 *
 * @author andresbailen93
 */
@Stateless
public class UsuarioFacade extends AbstractFacade<Usuario> {

    @PersistenceContext(unitName = "TodoTestJSF-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }

    public int totalSuccess(Usuario user) {
        int total = 0;
        Collection<Examen> ExamenList = user.getExamenCollection();
        for (Examen ex : ExamenList) {
            total = total + ex.getAciertos();
        }
        return total;
    }

    public int totalFail(Usuario user) {
        int total = 0;
        Collection<Examen> ExamenList = user.getExamenCollection();
        for (Examen ex : ExamenList) {
            total = total + ex.getFallos();
        }
        return total;
    }

    public double average(Usuario user) {

        double total = 0.0;
        int contador = 0;
        Collection<Examen> ExamenList = user.getExamenCollection();
        for (Examen ex : ExamenList) {
            contador++;
            total = total + (ex.getNota().doubleValue());
        }
        return total / contador;
    }

    public int totalTest(Usuario user) {

        int total = 0;
        Collection<Examen> ExamenList = user.getExamenCollection();
        for (Examen ex : ExamenList) {
            total++;
        }
        return total;
    }
}
