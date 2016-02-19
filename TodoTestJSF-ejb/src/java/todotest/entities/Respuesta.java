/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package todotest.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author andresbailen93
 */
@Entity
@Table(name = "RESPUESTA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Respuesta.findAll", query = "SELECT r FROM Respuesta r"),
    @NamedQuery(name = "Respuesta.findByIdRespuesta", query = "SELECT r FROM Respuesta r WHERE r.idRespuesta = :idRespuesta"),
    @NamedQuery(name = "Respuesta.findByTexto", query = "SELECT r FROM Respuesta r WHERE r.texto = :texto"),
    @NamedQuery(name = "Respuesta.findByCorrecta", query = "SELECT r FROM Respuesta r WHERE r.correcta = :correcta")})
public class Respuesta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_RESPUESTA")
    private Long idRespuesta;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "TEXTO")
    private String texto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CORRECTA")
    private short correcta;
    @JoinColumn(name = "ID_PREGUNTA", referencedColumnName = "ID_PREGUNTA")
    @ManyToOne(optional = false)
    private Pregunta idPregunta;

    public Respuesta() {
    }

    public Respuesta(Long idRespuesta) {
        this.idRespuesta = idRespuesta;
    }

    public Respuesta(Long idRespuesta, String texto, short correcta) {
        this.idRespuesta = idRespuesta;
        this.texto = texto;
        this.correcta = correcta;
    }

    public Long getIdRespuesta() {
        return idRespuesta;
    }

    public void setIdRespuesta(Long idRespuesta) {
        this.idRespuesta = idRespuesta;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public short getCorrecta() {
        return correcta;
    }

    public void setCorrecta(short correcta) {
        this.correcta = correcta;
    }

    public Pregunta getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(Pregunta idPregunta) {
        this.idPregunta = idPregunta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRespuesta != null ? idRespuesta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Respuesta)) {
            return false;
        }
        Respuesta other = (Respuesta) object;
        if ((this.idRespuesta == null && other.idRespuesta != null) || (this.idRespuesta != null && !this.idRespuesta.equals(other.idRespuesta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Respuesta[ idRespuesta=" + idRespuesta + " ]";
    }
    
}
