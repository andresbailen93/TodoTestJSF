/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package todotest.bean;

import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import todotest.ejb.CategoriaFacade;
import todotest.ejb.PreguntaFacade;
import todotest.ejb.TestFacade;
import todotest.entities.Categoria;
import todotest.entities.Pregunta;
import todotest.entities.Test;

/**
 *
 * @author andresbailen93
 */
@ManagedBean
@RequestScoped
public class AddQuestionByCategoryBean implements Serializable {
    @EJB
    private PreguntaFacade preguntaFacade;

    @EJB
    private CategoriaFacade categoriaFacade;

    @ManagedProperty(value = "#{loginBean}")
    private LoginBean loginBean;

    private List<Categoria> list_categoria;
    private Long category;
    private ArrayList<String> lis_numPreg;
    private int numPreg;
    private boolean addQuestions = false;

    public boolean isAddQuestions() {
        return addQuestions;
    }

    public void setAddQuestions(boolean addQuestions) {
        this.addQuestions = addQuestions;
    }

    public ArrayList<String> getLis_numPreg() {
        return lis_numPreg;
    }

    public void setLis_numPreg(ArrayList<String> lis_numPreg) {
        this.lis_numPreg = lis_numPreg;
    }

    public int getNumPreg() {
        return numPreg;
    }

    public void setNumPreg(int numPreg) {
        this.numPreg = numPreg;
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }

    public List<Categoria> getList_categoria() {
        return list_categoria;
    }

    public void setList_categoria(List<Categoria> list_categoria) {
        this.list_categoria = list_categoria;
    }

    /**
     * Creates a new instance of AddQuestionByCategoryBean
     */
    @PostConstruct
    public void init() {
        this.list_categoria = categoriaFacade.findAll();
        lis_numPreg = new ArrayList<String>();
        for (int i = 1; i < 15; i++) {
            lis_numPreg.add(String.valueOf(i));
        }
    }
    

    public AddQuestionByCategoryBean() {
    }

    public String doAddQuestions() {
        this.addQuestions = false;
        Categoria categoria = categoriaFacade.find(category);
        //Collection<Pregunta> lista_preguntas = lista_categoria.get(0).getPreguntaCollection();

        List<Pregunta> lista_preguntas = preguntaFacade.findPreguntasByNum(categoria, loginBean.getTestAdded());

        //test = testFacade.find(testLi);
        List<Pregunta> lista_preguntas_add = new ArrayList<>();
        //Si marcamos un numero superior de preguntas de las que hay no las inserta.

        for (int i = (lista_preguntas.size() - 1); i >= 0; i--) {
            if (lista_preguntas.get(i).getTestCollection().contains((loginBean.getTestAdded()))) {
                lista_preguntas.remove(i);
            }
        }

        long seed = System.nanoTime();
        Collections.shuffle(lista_preguntas, new Random(seed));
        for (int j = 0; j < lista_preguntas.size(); j++) {
            if (j < this.numPreg) {
                if (lista_preguntas.get(j) != null) {
                    lista_preguntas_add.add(lista_preguntas.get(j));
                }
            }
        }

        Collection<Test> listaTest;
        for (Pregunta p : lista_preguntas_add) {
            listaTest = p.getTestCollection();

            //if (!listaTest.contains(test)) {
            listaTest.add(loginBean.getTestAdded());
            p.setTestCollection(listaTest);
            preguntaFacade.edit(p);
            //}
            // listaTest.add(test);
            // p.setTestCollection(listaTest);
            // preguntaFacade.edit(p);
        }

        this.addQuestions = true;
        return "addQuestionsByCategory";
    }

}
