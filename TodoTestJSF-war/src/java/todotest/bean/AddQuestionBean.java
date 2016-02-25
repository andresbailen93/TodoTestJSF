/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package todotest.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.Part;
import todotest.aux.RespuestaManager;
import todotest.ejb.CategoriaFacade;
import todotest.ejb.PreguntaFacade;
import todotest.ejb.RespuestaFacade;
import todotest.entities.Categoria;
import todotest.entities.Pregunta;
import todotest.entities.Respuesta;
import todotest.entities.Test;

/**
 *
 * @author inftel23
 */
@ManagedBean
@RequestScoped
public class AddQuestionBean implements Serializable {
    
    @ManagedProperty(value = "#{loginBean}")
    private LoginBean loginBean;

    @EJB
    private RespuestaFacade respuestaFacade;
    @EJB
    private PreguntaFacade preguntaFacade;
    @EJB
    private CategoriaFacade categoriaFacade;
    
    //@ManagedProperty(value = "#{testListTeacherBean}")
    //private TestListTeacherBean testListTeacher;

    private String question, categoryName;
    private Long category;
    private Part image = null;
    private Collection<Respuesta> respuestaCollection;
    private ArrayList<String> numPreguntas;
    private boolean addQuestion = false;
    private boolean addCategory = false;
    private boolean errorAddCategory = false;
    private boolean errorAddQuestion = false;
    private Categoria categoriaAdd;
    private String correctTestAnswer = "";
    private String incorrectTestAnswer1 = "";
    private String incorrectTestAnswer2 = "";
    private String incorrectTestAnswer3 = "";
    private String incorrectTestAnswer4 = "";

    
    private List<Categoria> list_categoria;

    public ArrayList<String> getNumPreguntas() {
        return numPreguntas;
    }

    public void setNumPreguntas(ArrayList<String> numPreguntas) {
        this.numPreguntas = numPreguntas;
    }

    public boolean isErrorAddQuestion() {
        return errorAddQuestion;
    }

    public void setErrorAddQuestion(boolean errorAddQuestion) {
        this.errorAddQuestion = errorAddQuestion;
    }

    public String getIncorrectTestAnswer1() {
        return incorrectTestAnswer1;
    }

    public void setIncorrectTestAnswer1(String incorrectTestAnswer1) {
        this.incorrectTestAnswer1 = incorrectTestAnswer1;
    }

    public String getIncorrectTestAnswer2() {
        return incorrectTestAnswer2;
    }

    public void setIncorrectTestAnswer2(String incorrectTestAnswer2) {
        this.incorrectTestAnswer2 = incorrectTestAnswer2;
    }

    public String getIncorrectTestAnswer3() {
        return incorrectTestAnswer3;
    }

    public void setIncorrectTestAnswer3(String incorrectTestAnswer3) {
        this.incorrectTestAnswer3 = incorrectTestAnswer3;
    }

    public String getIncorrectTestAnswer4() {
        return incorrectTestAnswer4;
    }

    public void setIncorrectTestAnswer4(String incorrectTestAnswer4) {
        this.incorrectTestAnswer4 = incorrectTestAnswer4;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }
    
    

    /*public TestListTeacherBean getTestListTeacher() {
        return testListTeacher;
    }

    public void setTestListTeacher(TestListTeacherBean testListTeacher) {
        this.testListTeacher = testListTeacher;
    }*/

    public String getCorrectTestAnswer() {
        return correctTestAnswer;
    }

    public void setCorrectTestAnswer(String correctTestAnswer) {
        this.correctTestAnswer = correctTestAnswer;
    }

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

    public Part getImage() {
        return image;
    }

    public void setImage(Part image) {
        this.image = image;
    }

    public Collection<Respuesta> getRespuestaCollection() {
        return respuestaCollection;
    }

    public void setRespuestaCollection(Collection<Respuesta> respuestaCollection) {
        this.respuestaCollection = respuestaCollection;
    }

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }

    private final byte[] getImageBytes(Part p) {
        if (p == null)
            return null;

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
        //test = testListTeacher.getTest();
    }

    public String doAddQuestion() {
        addCategory = false;
        addQuestion = false;
        this.errorAddCategory = false;
        this.errorAddQuestion = false;
        
        Categoria categoria = new Categoria();
        categoria.setIdCategoria(category);
        categoria = categoriaFacade.find(category);
        //Creamos la pregunta
        Pregunta pregunta = new Pregunta();
        pregunta.setIdCategoria(categoria);
        pregunta.setImagen(getImageBytes(image));
        pregunta.setTexto(question);
        

        List<Test> listaTest = new ArrayList<>();
        listaTest.add(loginBean.getTestAdded());
        /*Test debera ser una inyeccion de dependecias o sacado de la sesion */
        pregunta.setTestCollection(listaTest);
        preguntaFacade.create(pregunta);
        
        
        if(question != null){
        //Añadir respuestas.
        if (correctTestAnswer.length() !=0) {
            Respuesta correctAnswer = new Respuesta();
            correctAnswer.setCorrecta((short) 1);
            correctAnswer.setIdPregunta(pregunta);
            correctAnswer.setTexto(this.correctTestAnswer);
            respuestaFacade.create(correctAnswer);
        }
        Respuesta incorrectAnswer;
        if (incorrectTestAnswer1.length() !=0) {
            incorrectAnswer = new Respuesta();
            incorrectAnswer.setCorrecta((short) 0);
            incorrectAnswer.setIdPregunta(pregunta);
            incorrectAnswer.setTexto(this.incorrectTestAnswer1);
            respuestaFacade.create(incorrectAnswer);
        }

        if (incorrectTestAnswer2.length() !=0) {
            incorrectAnswer = new Respuesta();
            incorrectAnswer.setCorrecta((short) 0);
            incorrectAnswer.setIdPregunta(pregunta);
            incorrectAnswer.setTexto(this.incorrectTestAnswer2);
            respuestaFacade.create(incorrectAnswer);
        }
        if (incorrectTestAnswer3.length() !=0) {

            incorrectAnswer = new Respuesta();
            incorrectAnswer.setCorrecta((short) 0);
            incorrectAnswer.setIdPregunta(pregunta);
            incorrectAnswer.setTexto(this.incorrectTestAnswer3);
            respuestaFacade.create(incorrectAnswer);
        }
        if (incorrectTestAnswer4.length() !=0) {

            incorrectAnswer = new Respuesta();
            incorrectAnswer.setCorrecta((short) 0);
            incorrectAnswer.setIdPregunta(pregunta);
            incorrectAnswer.setTexto(this.incorrectTestAnswer4);
            respuestaFacade.create(incorrectAnswer);
        }
        }else{
            this.errorAddQuestion = true;
        }
        this.addQuestion = true;
        this.categoryName = "";
        this.question = "";
        this.correctTestAnswer = "";
        this.incorrectTestAnswer1 = "";
        this.incorrectTestAnswer2 = "";
        this.incorrectTestAnswer3 = "";
        this.incorrectTestAnswer4 = "";

        return "addQuestion";
    }

    public String doAddCategory() {
        addCategory = false;
        addQuestion = false;
        this.errorAddCategory = false;
        this.errorAddQuestion = false;
        List<Categoria> listas_categorias = categoriaFacade.findByName(categoryName);

        if (listas_categorias.isEmpty()) { //No existe ninguno con el nombre. buscado
            this.categoriaAdd = new Categoria();
            this.categoriaAdd.setNombre(categoryName);
            this.categoriaFacade.create(this.categoriaAdd);
            this.list_categoria.add(this.categoriaAdd);

            this.addCategory = true;
            return "addQuestion";
        } else {
            this.errorAddCategory = true;
            return "addQuestion";
        }
    }
    
    public String doRedirectByCategory() {
        this.numPreguntas = new ArrayList<>();
        for ( int i = 1; i <= 15; i++){
            numPreguntas.add(String.valueOf(i));
        }
        return "addQuestionByCategory";
    }

}
