/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dilov
 */
@Entity
@Table(name = "contratos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Contratos.findAll", query = "SELECT c FROM Contratos c"),
    @NamedQuery(name = "Contratos.findByNumerocontrato", query = "SELECT c FROM Contratos c WHERE c.numerocontrato = :numerocontrato"),
    @NamedQuery(name = "Contratos.findByFechaingreso", query = "SELECT c FROM Contratos c WHERE c.fechaingreso = :fechaingreso"),
    @NamedQuery(name = "Contratos.findByFechasalida", query = "SELECT c FROM Contratos c WHERE c.fechasalida = :fechasalida")})
public class Contratos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "numerocontrato")
    private Integer numerocontrato;
    @Column(name = "fechaingreso")
    @Temporal(TemporalType.DATE)
    private Date fechaingreso;
    @Column(name = "fechasalida")
    @Temporal(TemporalType.DATE)
    private Date fechasalida;
    @JoinColumn(name = "codigocontratista", referencedColumnName = "codigocontratista")
    @ManyToOne
    private Contratista codigocontratista;
    @JoinColumn(name = "codigoempleado", referencedColumnName = "codigoempleado")
    @ManyToOne
    private Empleado codigoempleado;

    public Contratos() {
    }

    public Contratos(Integer numerocontrato) {
        this.numerocontrato = numerocontrato;
    }

    public Integer getNumerocontrato() {
        return numerocontrato;
    }

    public void setNumerocontrato(Integer numerocontrato) {
        this.numerocontrato = numerocontrato;
    }

    public Date getFechaingreso() {
        return fechaingreso;
    }

    public void setFechaingreso(Date fechaingreso) {
        this.fechaingreso = fechaingreso;
    }

    public Date getFechasalida() {
        return fechasalida;
    }

    public void setFechasalida(Date fechasalida) {
        this.fechasalida = fechasalida;
    }

    public Contratista getCodigocontratista() {
        return codigocontratista;
    }

    public void setCodigocontratista(Contratista codigocontratista) {
        this.codigocontratista = codigocontratista;
    }

    public Empleado getCodigoempleado() {
        return codigoempleado;
    }

    public void setCodigoempleado(Empleado codigoempleado) {
        this.codigoempleado = codigoempleado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numerocontrato != null ? numerocontrato.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Contratos)) {
            return false;
        }
        Contratos other = (Contratos) object;
        if ((this.numerocontrato == null && other.numerocontrato != null) || (this.numerocontrato != null && !this.numerocontrato.equals(other.numerocontrato))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Contratos[ numerocontrato=" + numerocontrato + " ]";
    }
    
}
