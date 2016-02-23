/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package todotest.bean;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.servlet.http.Part;
import todotest.ejb.CategoriaFacade;
import todotest.entities.Categoria;
import todotest.entities.Respuesta;

/**
 *
 * @author inftel23
 */
@ManagedBean
@RequestScoped
public class AddQuestionBean {
    @EJB
    private CategoriaFacade categoriaFacade;
    
    //@ManagedProperty(value = "#{addTestBean}")
    //private AddTestBean addTestBean;
    
    private String category, question;
    private byte[] image;
    private Collection<Respuesta> respuestaCollection;
    private boolean addQuestion = false;
    private boolean addCategory = false;
    private boolean errorAddCategory = false;
    private Categoria categoriaAdd;

    //@ManagerProperty(value="#{activateTestBean}")
    // private ActivateTestBean activateTestBean;
    private List<Categoria> list_categoria;

    public boolean isErrorAddCategory() {
        return errorAddCategory;
    }

    public void setErrorAddCategory(boolean errorAddCategory) {
        this.errorAddCategory = errorAddCategory;
    }

    public boolean isAddCategory() {
        return addCategory;
    }

    public void setAddCategory(boolean addCategory) {
        this.addCategory = addCategory;
    }

    public List<Categoria> getList_categoria() {
        return list_categoria;
    }

    public void setList_categoria(List<Categoria> list_categoria) {
        this.list_categoria = list_categoria;
    }

    public boolean isAddQuestion() {
        return addQuestion;
    }

    public void setAddQuestion(boolean addQuestion) {
        this.addQuestion = addQuestion;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Collection<Respuesta> getRespuestaCollection() {
        return respuestaCollection;
    }

    public void setRespuestaCollection(Collection<Respuesta> respuestaCollection) {
        this.respuestaCollection = respuestaCollection;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    private final byte[] getImageBytes(Part p) {

        byte[] imageByteArray = null;
        int read = 0;

        try {
            imageByteArray = new byte[(int) p.getSize()];
            read = p.getInputStream().read(imageByteArray);

        } catch (IOException ex) {
            Logger.getLogger(AddQuestionBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        return imageByteArray;
    }

    /**
     * Creates a new instance of AddQuestionBean
     */
    public AddQuestionBean() {
    }

    @PostConstruct
    public void init() {
        list_categoria = categoriaFacade.findAll();
    }

    public String doAddQuestion() {
        return "";
    }

    public String doAddCategory() {
        List<Categoria> listas_categorias = categoriaFacade.findByName(category);
        
        if (listas_categorias.isEmpty()) { //No existe ninguno con el nombre. buscado
            this.categoriaAdd = new Categoria();
            this.categoriaAdd.setNombre(category);
            System.out.println(category);
            this.categoriaFacade.create(this.categoriaAdd);
            this.list_categoria.add(this.categoriaAdd);
            
            this.addCategory = true;
            return "addQuestion";
        } else {
            this.errorAddCategory = true;
            return "addQuestion";
        }
    }

    
}
