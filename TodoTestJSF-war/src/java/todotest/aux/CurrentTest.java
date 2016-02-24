/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package todotest.aux;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import todotest.entities.Pregunta;
import todotest.entities.Test;

/**
 *
 * @author csalas
 */
public class CurrentTest {
    private Test test;
    private int currentQuestion;
    private ArrayList<Long> userAnswers;
    private boolean lastQuestion = false;
    private BigDecimal mark = new BigDecimal(0);
    private boolean noTime = false;
    private int timeLeft = 1;
    private long timestamp;

    public CurrentTest(Test test, int currentQuestion) {
        this.test = test;
        Long seed = System.nanoTime();
        Collections.shuffle((List<Pregunta>)test.getPreguntaCollection(), new Random(seed));
        this.currentQuestion = currentQuestion;
        userAnswers = new ArrayList();
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public int getCurrentQuestion() {
        return currentQuestion;
    }

    public void setCurrentQuestion(int currentQuestion) {
        this.currentQuestion = currentQuestion;
    }
    
    public void addUserAnswer(Long ans) {
        userAnswers.add(ans);
    }
    
    public ArrayList<Long> getUserAnswers() {
        return userAnswers;
    }
    
    public void setLastQuestion(boolean lastQuestion) {
        this.lastQuestion = lastQuestion;
    }
    
    public boolean isLastQuestion() {
        return lastQuestion;
    }
    
    public void setMark(BigDecimal mark) {
        this.mark = mark;
    }
    
    public BigDecimal getMark() {
        return mark;
    }
    
    public void setTestWithoutTime(boolean noTime) {
        this.noTime = noTime;
    }
    
    public boolean getNoTime() {
        return noTime;
    }
    
    public void setTimeLeft(int time, long timestamp) {
        this.timeLeft = time;
        this.timestamp = timestamp;
    }
    
    public int getTimeLeft() {
        return timeLeft;
    }
    
    public long getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(long timestamp) {
        // Solo editamos el tiempo si es un test con duración > 0
        if (!noTime) {
            // Tiempo que ha pasado entre respuestas del usuario
            long time = (timestamp - this.timestamp) / 1000;
            timeLeft -= (int)time;
            this.timestamp = timestamp;
        }
    }
    
}
