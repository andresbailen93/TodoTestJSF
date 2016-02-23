/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package todotest.bean;

import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import todotest.aux.CurrentTest;
import todotest.ejb.ExamenFacade;
import todotest.ejb.TestFacade;
import todotest.entities.Pregunta;
import todotest.entities.Respuesta;

/**
 *
 * @author csalas
 */
@ManagedBean
@ViewScoped
public class DoTestBean {
    @EJB
    private TestFacade testFacade;
    @EJB
    private ExamenFacade examenFacade;
    @ManagedProperty(value = "#{loginBean}")
    private LoginBean loginBean;
    @ManagedProperty(value= "#{testListBean}")
    private TestListBean testListBean;
    
    // Datos acerca del test actual
    private CurrentTest currentTest;
    
    // Variables asociadas a información de la vista
    private String testName;
    private Pregunta question;
    private Collection<Respuesta> answerList;
    private boolean lastQuestion = false;
    private String mark;
    private int currentQuestion;
    private int totalQuestions;
    private int currentTestTime;
    private String currentTestTimeWithFormat;
    private boolean pdf = false;
    private boolean testWithoutTime = false;
    private Long currentUserAnswer;
    private boolean isImageQuestion = false;
    

    /**
     * Creates a new instance of DoTestBean
     */
    public DoTestBean() {
    }
    
    @PostConstruct
    public void init() {
        // Inicializamos el estado del test (examen)
        currentTest = new CurrentTest(testListBean.getSelectedTest(), 0);
        // Seteamos la primera pregunta
        List<Pregunta> preguntaList = (List<Pregunta>) testListBean.getSelectedTest().getPreguntaCollection();
        question = preguntaList.get(0);
        if (question.getImagen() != null)
            isImageQuestion = true;
        currentQuestion = 1;
        totalQuestions = preguntaList.size();
        // Recuperamos las respuestas para la primera pregunta
        answerList = question.getRespuestaCollection();
        // Nombre del test
        testName = currentTest.getTest().getNombre();
        if (currentTest.getTest().getDuracion() == 0)
            testWithoutTime = true;
        else {
            currentTest.setTimeLeft(currentTest.getTest().getDuracion() * 60, System.currentTimeMillis());
        }
        
       
    }
    
    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public CurrentTest getCurrentTest() {
        return currentTest;
    }

    public void setCurrentTest(CurrentTest currentTest) {
        this.currentTest = currentTest;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public Pregunta getQuestion() {
        return question;
    }

    public void setQuestion(Pregunta question) {
        this.question = question;
    }

    public Collection<Respuesta> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(Collection<Respuesta> answerList) {
        this.answerList = answerList;
    }

    public boolean isLastQuestion() {
        return lastQuestion;
    }

    public void setLastQuestion(boolean lastQuestion) {
        this.lastQuestion = lastQuestion;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public int getCurrentQuestion() {
        return currentQuestion;
    }

    public void setCurrentQuestion(int currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public int getCurrentTestTime() {
        return currentTestTime;
    }

    public void setCurrentTestTime(int currentTestTime) {
        this.currentTestTime = currentTestTime;
    }

    public String getCurrentTestTimeWithFormat() {
        return currentTestTimeWithFormat;
    }

    public void setCurrentTestTimeWithFormat(String currentTestTimeWithFormat) {
        this.currentTestTimeWithFormat = currentTestTimeWithFormat;
    }

    public boolean isPdf() {
        return pdf;
    }

    public void setPdf(boolean pdf) {
        this.pdf = pdf;
    }

    public boolean isTestWithoutTime() {
        return testWithoutTime;
    }

    public void setTestWithoutTime(boolean testWithoutTime) {
        this.testWithoutTime = testWithoutTime;
    }

    public TestListBean getTestListBean() {
        return testListBean;
    }

    public void setTestListBean(TestListBean testListBean) {
        this.testListBean = testListBean;
    }

    public Long getCurrentUserAnswer() {
        return currentUserAnswer;
    }

    public void setCurrentUserAnswer(Long currentUserAnswer) {
        this.currentUserAnswer = currentUserAnswer;
    }
    
    public String doNextQuestion() {
        isImageQuestion = false;
        // Guardamos la respuesta del usuario
        currentTest.addUserAnswer(currentUserAnswer);
        // Siguiente pregunta
        currentQuestion++;
        List<Pregunta> preguntaList = (List<Pregunta>) currentTest.getTest().getPreguntaCollection();
        question = preguntaList.get(currentQuestion-1);
        if (question.getImagen() != null)
            isImageQuestion = true;
        // Obtenemos las respuestas
        answerList = question.getRespuestaCollection();
        // Actualizamos el tiempo restante
        if (!testWithoutTime) {
            currentTest.setTimestamp(System.currentTimeMillis());
        }
        if (currentQuestion == totalQuestions)
            lastQuestion = true;
        return "textAnswer";
    }

    public boolean isIsImageQuestion() {
        return isImageQuestion;
    }

    public void setIsImageQuestion(boolean isImageQuestion) {
        this.isImageQuestion = isImageQuestion;
    }
    
    
    
    
    
}
