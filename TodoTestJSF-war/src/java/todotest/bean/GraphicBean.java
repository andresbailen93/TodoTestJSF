/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package todotest.bean;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import todotest.entities.Examen;

/**
 *
 * @author alejandroruiz
 */
@ManagedBean
@SessionScoped
public class GraphicBean implements Serializable{

    /**
     * Creates a new instance of GraphicBean
     */
     private BarChartModel barModel;
      @ManagedProperty(value = "#{loginBean}")
    private LoginBean loginBean;
     
    @PostConstruct
    public void init() {
        createBarModel();
    }

    public BarChartModel getBarModel() {
        return barModel;
    }

    public void setBarModel(BarChartModel barModel) {
        this.barModel = barModel;
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }
    
    

    private void createBarModel() {
        barModel = initBarModel();
         
        barModel.setTitle("Resultados");
        barModel.setLegendPosition("ne");
        barModel.setBarWidth(25);
        
         
        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel("Test");
        
        
        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("Nota");
        yAxis.setMin(0);
        yAxis.setMax(12);
    }
    
    
    
    
    private BarChartModel initBarModel() {
        BarChartModel model = new BarChartModel();
 
        ChartSeries notas = new ChartSeries();
        notas.setLabel("Puntuación");
        for(Examen ex : loginBean.user.getExamenCollection()){
            notas.set(ex.getTest().getNombre(), ex.getNota());
        }
        

        model.addSeries(notas);
        
         
        return model;
    }
    
    public GraphicBean() {
    }
    
}
