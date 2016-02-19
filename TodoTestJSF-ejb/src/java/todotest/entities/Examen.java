/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package todotest.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author andresbailen93
 */
@Entity
@Table(name = "EXAMEN")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Examen.findAll", query = "SELECT e FROM Examen e"),
    @NamedQuery(name = "Examen.findByDni", query = "SELECT e FROM Examen e WHERE e.examenPK.dni = :dni"),
    @NamedQuery(name = "Examen.findByIdTest", query = "SELECT e FROM Examen e WHERE e.examenPK.idTest = :idTest"),
    @NamedQuery(name = "Examen.findByFecha", query = "SELECT e FROM Examen e WHERE e.fecha = :fecha"),
    @NamedQuery(name = "Examen.findByAciertos", query = "SELECT e FROM Examen e WHERE e.aciertos = :aciertos"),
    @NamedQuery(name = "Examen.findByFallos", query = "SELECT e FROM Examen e WHERE e.fallos = :fallos"),
    @NamedQuery(name = "Examen.findByNota", query = "SELECT e FROM Examen e WHERE e.nota = :nota")})
public class Examen implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ExamenPK examenPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ACIERTOS")
    private short aciertos;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FALLOS")
    private short fallos;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "NOTA")
    private BigDecimal nota;
    @JoinColumn(name = "ID_TEST", referencedColumnName = "ID_TEST", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Test test;
    @JoinColumn(name = "DNI", referencedColumnName = "DNI", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Usuario usuario;

    public Examen() {
    }

    public Examen(ExamenPK examenPK) {
        this.examenPK = examenPK;
    }

    public Examen(ExamenPK examenPK, Date fecha, short aciertos, short fallos) {
        this.examenPK = examenPK;
        this.fecha = fecha;
        this.aciertos = aciertos;
        this.fallos = fallos;
    }

    public Examen(String dni, long idTest) {
        this.examenPK = new ExamenPK(dni, idTest);
    }

    public ExamenPK getExamenPK() {
        return examenPK;
    }

    public void setExamenPK(ExamenPK examenPK) {
        this.examenPK = examenPK;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public short getAciertos() {
        return aciertos;
    }

    public void setAciertos(short aciertos) {
        this.aciertos = aciertos;
    }

    public short getFallos() {
        return fallos;
    }

    public void setFallos(short fallos) {
        this.fallos = fallos;
    }

    public BigDecimal getNota() {
        return nota;
    }

    public void setNota(BigDecimal nota) {
        this.nota = nota;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (examenPK != null ? examenPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Examen)) {
            return false;
        }
        Examen other = (Examen) object;
        if ((this.examenPK == null && other.examenPK != null) || (this.examenPK != null && !this.examenPK.equals(other.examenPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Examen[ examenPK=" + examenPK + " ]";
    }
    
}
