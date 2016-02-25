/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package todotest.bean;

import com.itextpdf.text.DocumentException;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import todotest.aux.CurrentTest;
import todotest.entities.Examen;
import todotest.entities.ExamenPK;
import todotest.entities.Pregunta;
import todotest.entities.Respuesta;
import todotest.utils.PDF;

/**
 *
 * @author csalas
 */
@ManagedBean
@SessionScoped
public class DoTestBean {

    @ManagedProperty(value = "#{loginBean}")
    private LoginBean loginBean;
    @ManagedProperty(value = "#{testListStudentBean}")
    private TestListStudentBean testListBean;
    
    private CurrentTest currentTest;

    // Variables asociadas a información de la vista
    private String testName;
    private Pregunta question;
    private List<Respuesta> answerList;
    private boolean lastQuestion;
    private int currentQuestion;
    private int totalQuestions;
    private int currentTestTime;
    private int timeLeft;
    private String currentTestTimeWithFormat;
    private boolean generatedPdf;
    private boolean testWithoutTime;
    private Long currentUserAnswer;
    private boolean isImageQuestion;
    private boolean errNoAnswerSelected;
    private String path;
    private BigDecimal mark;
    private String htmlChronometer = "<p id=\"chronometer\" style=\"text-align: right; font-size: 18px\"></p>";

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public BigDecimal getMark() {
        return mark;
    }

    public void setMark(BigDecimal mark) {
        this.mark = mark;
    }

    public boolean isErrNoAnswerSelected() {
        return errNoAnswerSelected;
    }

    public void setErrNoAnswerSelected(boolean errNoAnswerSelected) {
        this.errNoAnswerSelected = errNoAnswerSelected;
    }

    public String getHtmlChronometer() {
        return htmlChronometer;
    }

    public void setHtmlChronometer(String htmlChronometer) {
        this.htmlChronometer = htmlChronometer;
    }

    public CurrentTest getCurrentTest() {
        return currentTest;
    }

    public void setCurrentTest(CurrentTest currentTest) {
        this.currentTest = currentTest;
    }
   
    
    /**
     * Creates a new instance of DoTestBean
     */
    public DoTestBean() {
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
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

    public void setAnswerList(List<Respuesta> answerList) {
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

    public boolean isGeneratedPdf() {
        return generatedPdf;
    }

    public void setGeneratedPdf(boolean generatedPdf) {
        this.generatedPdf = generatedPdf;
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

    public int getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;
    }
    
    public void init() {
        if (!testListBean.isStarted())
            start();
    }

    public void start() {
        // Inicializamos el estado del test (examen)
        isImageQuestion = false;
        generatedPdf = false;
        currentTest = new CurrentTest(testListBean.getSelectedTest(), 0);
        // Seteamos la primera pregunta
        List<Pregunta> preguntaList = (List<Pregunta>) testListBean.getSelectedTest().getPreguntaCollection();
        question = preguntaList.get(0);
        if (question.getImagen() != null) {
            isImageQuestion = true;
        }
        currentQuestion = 1;
        totalQuestions = preguntaList.size();
        // Recuperamos las respuestas para la primera pregunta
        answerList = (List<Respuesta>) question.getRespuestaCollection();
        // Hacemos que las respuestas aparezcan de forma aleatoria
        Long seed = System.nanoTime();
        Collections.shuffle(answerList, new Random(seed));
        
        // Nombre del test
        testName = currentTest.getTest().getNombre();
        if (currentTest.getTest().getDuracion() == 0) {
            testWithoutTime = true;
            currentTest.setTestWithoutTime(testWithoutTime);
            timeLeft = 0;
        } else {
            currentTest.setTimeLeft(currentTest.getTest().getDuracion() * 60, System.currentTimeMillis());
            timeLeft = currentTest.getTimeLeft();
        }
        testListBean.setStarted(true);
        lastQuestion = false;
        errNoAnswerSelected = false;
        
    }

    public String doNextQuestion() {
        if (currentTest == null) {
            start();
            return "testAnswer?faces-redirect=true";
        }
        
        // Si es un test con tiempo, actualizamos el tiempo restante
        if (!currentTest.getNoTime()) {
            currentTest.setTimestamp(System.currentTimeMillis());
            timeLeft = currentTest.getTimeLeft();
        }
        
        if (currentUserAnswer == null) {
            errNoAnswerSelected = true;
            return "testAnswer?faces-redirect=true";
        }

        if (!lastQuestion && currentTest.getTimeLeft() > 0) { // Si hay preguntas
            isImageQuestion = false;
            errNoAnswerSelected = false;
            // Guardamos la respuesta del usuario
            currentTest.addUserAnswer(currentUserAnswer);
            // Siguiente pregunta
            currentQuestion++;
            List<Pregunta> preguntaList = (List<Pregunta>) currentTest.getTest().getPreguntaCollection();
            question = preguntaList.get(currentQuestion - 1);
            if (question.getImagen() != null) {
                isImageQuestion = true;
            }
            // Obtenemos las respuestas
            answerList = (List<Respuesta>) question.getRespuestaCollection();
            Long seed = System.nanoTime();
            Collections.shuffle(answerList, new Random(seed));
            if (currentQuestion == totalQuestions) {
                lastQuestion = true;
            }
        } else {
            // Se ha terminado el test
            currentTest.addUserAnswer(currentUserAnswer);
            correctTest();
            testListBean.setGeneratePdf(generatePDF());
            testListBean.setStarted(false);
        }

        return "testAnswer?faces-redirect=true";
    }

    private void correctTest() {
        int correct = 0;
        int fail = 0;
        List<Respuesta> questionsAnswer = null;
        List<Pregunta> testQuestions = new ArrayList(currentTest.getTest().getPreguntaCollection());
        // Si ha respondido preguntas
        if (currentTest.getUserAnswers().size() > 0) {
            for (int i = 0; i < testQuestions.size(); i++) {
                Pregunta p = testQuestions.get(i);
                // Recuperamos cual es su respuesta correcta
                questionsAnswer = new ArrayList(p.getRespuestaCollection());
                Respuesta correctAnswer = null;
                for (Respuesta r : questionsAnswer) {
                    if (r.getCorrecta() == 1) {
                        correctAnswer = r;
                        break;
                    }
                }
                // Si la respuesta es correcta
                if (currentTest.getUserAnswers().size() > i) {
                    if (correctAnswer.getIdRespuesta().equals(currentTest.getUserAnswers().get(i))) {
                        correct++;
                    } else {
                        fail++;
                    }
                }
            }
        }
        calcMark(correct, fail, testQuestions.size());

    }

    private void calcMark(int correct, int fail, int numQuestions) {
        double mark;
        double answerMark = (double) 10 / (numQuestions);
        // Comprobamos la configuración del test

        if (currentTest.getTest().getResta() == 0) {
            mark = answerMark * correct;
        } else { // Una mal resta una bien, por tanto sería tener el doble de fallos
            if (currentTest.getTest().getResta() == 1) {
                mark = (answerMark * correct) - (answerMark * fail);
            } else {
                mark = (answerMark * correct) - (answerMark * fail / currentTest.getTest().getResta());
            }

            mark = Math.round(mark * 100.0) / 100.0;
        }
        if (mark < 0) {
            mark = 0;
        }

        // Guardamos el examen realizado
        Examen e = new Examen();
        ExamenPK ePK = new ExamenPK();

        ePK.setDni(loginBean.user.getDni());
        ePK.setIdTest(currentTest.getTest().getIdTest());

        e.setExamenPK(ePK);
        e.setAciertos((short) correct);
        e.setFallos((short) fail);
        e.setFecha(new Date(Calendar.getInstance().getTime().getTime()));
        e.setNota(new BigDecimal(mark).setScale(2, RoundingMode.CEILING));
        e.setTest(currentTest.getTest());
        this.mark = new BigDecimal(mark).setScale(2, RoundingMode.CEILING);
        currentTest.setMark(this.mark);
        testListBean.setFinished(true); // Se ha terminado el test, se muestra la calificación

        //examenFacade.create(e);
    }

    private boolean generatePDF() {
        this.path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
        if (testListBean.isFinished() && currentTest.getUserAnswers().size() == currentTest.getTest().getPreguntaCollection().size()) {
            ArrayList<ArrayList<Respuesta>> questionAnswerList = new ArrayList();
            ArrayList<Pregunta> questionList = new ArrayList(currentTest.getTest().getPreguntaCollection());
            for (Pregunta p : questionList) {
                questionAnswerList.add(new ArrayList(p.getRespuestaCollection()));
            }
            PDF pdf = new PDF(path, questionList, questionAnswerList);
            try { 
                pdf.createPDF(currentTest.getTest().getNombre(), loginBean.user.getNombre() + " " + loginBean.user.getApellidos(), String.valueOf(currentTest.getMark()), loginBean.user.getDni() + ".pdf");
                return true;
            } catch (FileNotFoundException ex) {
                
            } catch (DocumentException ex) {
                
            }
        }

        return false;
    }

}
