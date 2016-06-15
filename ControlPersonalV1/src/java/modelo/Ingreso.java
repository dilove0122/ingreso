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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ADMIN
 */
@Entity
@Table(name = "ingreso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ingreso.findAll", query = "SELECT i FROM Ingreso i"),
    @NamedQuery(name = "Ingreso.findByNumeroingreso", query = "SELECT i FROM Ingreso i WHERE i.numeroingreso = :numeroingreso"),
    @NamedQuery(name = "Ingreso.findByFechaingreso", query = "SELECT i FROM Ingreso i WHERE i.fechaingreso = :fechaingreso"),
    @NamedQuery(name = "Ingreso.findByHoraentradaingreso", query = "SELECT i FROM Ingreso i WHERE i.horaentradaingreso = :horaentradaingreso"),
    @NamedQuery(name = "Ingreso.findByHorasalidaingreso", query = "SELECT i FROM Ingreso i WHERE i.horasalidaingreso = :horasalidaingreso"),
    @NamedQuery(name = "Ingreso.findByAutorizadoingreso", query = "SELECT i FROM Ingreso i WHERE i.autorizadoingreso = :autorizadoingreso")})
public class Ingreso implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "numeroingreso")
    private Integer numeroingreso;
    @Column(name = "fechaingreso")
    @Temporal(TemporalType.DATE)
    private Date fechaingreso;
    @Column(name = "horaentradaingreso")
    @Temporal(TemporalType.TIME)
    private Date horaentradaingreso;
    @Column(name = "horasalidaingreso")
    @Temporal(TemporalType.TIME)
    private Date horasalidaingreso;
    @Size(max = 50)
    @Column(name = "autorizadoingreso")
    private String autorizadoingreso;
    @JoinColumn(name = "empleadoingreso", referencedColumnName = "cedulaempleado")
    @ManyToOne
    private Empleado empleadoingreso;

    public Ingreso() {
    }

    public Ingreso(Integer numeroingreso) {
        this.numeroingreso = numeroingreso;
    }

    public Integer getNumeroingreso() {
        return numeroingreso;
    }

    public void setNumeroingreso(Integer numeroingreso) {
        this.numeroingreso = numeroingreso;
    }

    public Date getFechaingreso() {
        return fechaingreso;
    }

    public void setFechaingreso(Date fechaingreso) {
        this.fechaingreso = fechaingreso;
    }

    public Date getHoraentradaingreso() {
        return horaentradaingreso;
    }

    public void setHoraentradaingreso(Date horaentradaingreso) {
        this.horaentradaingreso = horaentradaingreso;
    }

    public Date getHorasalidaingreso() {
        return horasalidaingreso;
    }

    public void setHorasalidaingreso(Date horasalidaingreso) {
        this.horasalidaingreso = horasalidaingreso;
    }

    public String getAutorizadoingreso() {
        return autorizadoingreso;
    }

    public void setAutorizadoingreso(String autorizadoingreso) {
        this.autorizadoingreso = autorizadoingreso;
    }

    public Empleado getEmpleadoingreso() {
        return empleadoingreso;
    }

    public void setEmpleadoingreso(Empleado empleadoingreso) {
        this.empleadoingreso = empleadoingreso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numeroingreso != null ? numeroingreso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ingreso)) {
            return false;
        }
        Ingreso other = (Ingreso) object;
        if ((this.numeroingreso == null && other.numeroingreso != null) || (this.numeroingreso != null && !this.numeroingreso.equals(other.numeroingreso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Ingreso[ numeroingreso=" + numeroingreso + " ]";
    }
    
}
