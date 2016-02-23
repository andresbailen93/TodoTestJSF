/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package todotest.bean;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import todotest.ejb.PreguntaFacade;
import todotest.entities.Pregunta;

/**
 *
 * @author csalas
 */
@ManagedBean
@ApplicationScoped
public class ImageBean {
    @EJB
    private PreguntaFacade preguntaFacade;
    
    /**
     * Creates a new instance of ImageBean
     */
    public ImageBean() {
    }
    
    public StreamedContent getQuestionImage() {
    FacesContext context = FacesContext.getCurrentInstance();

    if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the view. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
    }
    else {
        // So, browser is requesting the image. Get ID value from actual request param.
        String idImage = context.getExternalContext().getRequestParameterMap().get("image");
        Pregunta p = preguntaFacade.find(Long.valueOf(idImage));
        if (p.getImagen() != null)
            return new DefaultStreamedContent(new ByteArrayInputStream(p.getImagen()));
        return new DefaultStreamedContent();
    }
}
    
}
