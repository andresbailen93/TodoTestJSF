/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package todotest.bean;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
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
import todotest.entities.Examen;
import todotest.entities.ExamenPK;
import todotest.entities.Pregunta;
import todotest.entities.Respuesta;

/**
 *
 * @author csalas
 */
@ManagedBean
@ViewScoped
public class DoTestBean {
    @ManagedProperty(value = "#{loginBean}")
    private LoginBean loginBean;
    @ManagedProperty(value= "#{testListStudentBean}")
    private TestListStudentBean testListBean;
    
    // Datos acerca del test actual
    private CurrentTest currentTest;
    
    // Variables asociadas a información de la vista
    private String testName;
    private Pregunta question;
    private Collection<Respuesta> answerList;
    private boolean lastQuestion = false;
    private int currentQuestion;
    private int totalQuestions;
    private int currentTestTime;
    private String currentTestTimeWithFormat;
    private boolean pdf = false;
    private boolean testWithoutTime = false;
    private Long currentUserAnswer;
    private boolean isImageQuestion = false;
    private boolean finishedTest = false;
    

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

    public TestListStudentBean getTestListBean() {
        return testListBean;
    }

    public void setTestListBean(TestListStudentBean testListBean) {
        this.testListBean = testListBean;
    }

    public Long getCurrentUserAnswer() {
        return currentUserAnswer;
    }

    public void setCurrentUserAnswer(Long currentUserAnswer) {
        this.currentUserAnswer = currentUserAnswer;
    }
    
    public boolean isIsImageQuestion() {
        return isImageQuestion;
    }

    public void setIsImageQuestion(boolean isImageQuestion) {
        this.isImageQuestion = isImageQuestion;
    }
    
        public boolean isFinishedTest() {
        return finishedTest;
    }

    public void setFinishedTest(boolean finishedTest) {
        this.finishedTest = finishedTest;
    }
    
    public String doNextQuestion() {
        if (!lastQuestion) { // Si hay preguntas
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
        } else {
            // Se ha terminado el test
            correctTest();
        }
        return "textAnswer";
    }
    
    private void correctTest() {
        int correct = 0;
        int fail = 0;
        List<Respuesta> questionsAnswer = null;
        List<Pregunta> testQuestions = new ArrayList(currentTest.getTest().getPreguntaCollection());
        // Si ha respondido preguntas
        if (currentTest.getUserAnswers().size() > 0) {
            for (int i=0; i<testQuestions.size(); i++) {
                Pregunta p = testQuestions.get(i);
                // Recuperamos cual es su respuesta correcta
                questionsAnswer = new ArrayList(p.getRespuestaCollection());
                Respuesta correctAnswer = null;
                for(Respuesta r: questionsAnswer) {
                    if (r.getCorrecta() == 1) {
                        correctAnswer = r;
                        break;
                    }
                }
                // Si la respuesta es correcta
                if (currentTest.getUserAnswers().size() > i) {
                    if (correctAnswer.getIdRespuesta().equals(currentTest.getUserAnswers().get(i)))
                        correct++;
                    else
                        fail++;
                }
            }
        }
        calcMark(correct, fail, testQuestions.size());
    
    }
    
    private void calcMark(int correct, int fail, int numQuestions) {
        double mark;
        double answerMark = (double)10/(numQuestions);
        // Comprobamos la configuración del test
     
        if (currentTest.getTest().getResta() == 0)
            mark = answerMark*correct;
        else { // Una mal resta una bien, por tanto sería tener el doble de fallos
            if (currentTest.getTest().getResta() == 1)
                mark = (answerMark * correct) - (answerMark * fail);
            else 
                mark = (answerMark * correct) - (answerMark * fail/currentTest.getTest().getResta());
            
            mark = Math.round(mark*100.0)/100.0;
        }
        if (mark < 0)
            mark = 0;
        
        // Guardamos el examen realizado
        Examen e = new Examen();
        ExamenPK ePK= new ExamenPK();
        
        ePK.setDni(loginBean.user.getDni());
        ePK.setIdTest(currentTest.getTest().getIdTest());
        
        e.setExamenPK(ePK);
        e.setAciertos((short) correct);
        e.setFallos((short) fail);
        e.setFecha(new Date(Calendar.getInstance().getTime().getTime()));
        e.setNota(new BigDecimal(mark).setScale(2, RoundingMode.CEILING));
        e.setTest(currentTest.getTest());
        currentTest.setMark(new BigDecimal(mark).setScale(2, RoundingMode.CEILING));
        finishedTest = true; // Se ha terminado el test, se muestra la calificación
        
        //examenFacade.create(e);
        
    }


    
    
    
    
}
