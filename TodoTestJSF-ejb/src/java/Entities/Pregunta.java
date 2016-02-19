/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author andresbailen93
 */
@Entity
@Table(name = "PREGUNTA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pregunta.findAll", query = "SELECT p FROM Pregunta p"),
    @NamedQuery(name = "Pregunta.findByIdPregunta", query = "SELECT p FROM Pregunta p WHERE p.idPregunta = :idPregunta"),
    @NamedQuery(name = "Pregunta.findByTexto", query = "SELECT p FROM Pregunta p WHERE p.texto = :texto")})
public class Pregunta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PREGUNTA")
    private Long idPregunta;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "TEXTO")
    private String texto;
    @Lob
    @Column(name = "IMAGEN")
    private Serializable imagen;
    @JoinTable(name = "PREGUNTA_TEST", joinColumns = {
        @JoinColumn(name = "ID_PREGUNTA", referencedColumnName = "ID_PREGUNTA")}, inverseJoinColumns = {
        @JoinColumn(name = "ID_TEST", referencedColumnName = "ID_TEST")})
    @ManyToMany
    private Collection<Test> testCollection;
    @JoinColumn(name = "ID_CATEGORIA", referencedColumnName = "ID_CATEGORIA")
    @ManyToOne(optional = false)
    private Categoria idCategoria;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPregunta")
    private Collection<Respuesta> respuestaCollection;

    public Pregunta() {
    }

    public Pregunta(Long idPregunta) {
        this.idPregunta = idPregunta;
    }

    public Pregunta(Long idPregunta, String texto) {
        this.idPregunta = idPregunta;
        this.texto = texto;
    }

    public Long getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(Long idPregunta) {
        this.idPregunta = idPregunta;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Serializable getImagen() {
        return imagen;
    }

    public void setImagen(Serializable imagen) {
        this.imagen = imagen;
    }

    @XmlTransient
    public Collection<Test> getTestCollection() {
        return testCollection;
    }

    public void setTestCollection(Collection<Test> testCollection) {
        this.testCollection = testCollection;
    }

    public Categoria getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Categoria idCategoria) {
        this.idCategoria = idCategoria;
    }

    @XmlTransient
    public Collection<Respuesta> getRespuestaCollection() {
        return respuestaCollection;
    }

    public void setRespuestaCollection(Collection<Respuesta> respuestaCollection) {
        this.respuestaCollection = respuestaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPregunta != null ? idPregunta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pregunta)) {
            return false;
        }
        Pregunta other = (Pregunta) object;
        if ((this.idPregunta == null && other.idPregunta != null) || (this.idPregunta != null && !this.idPregunta.equals(other.idPregunta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Pregunta[ idPregunta=" + idPregunta + " ]";
    }
    
}
