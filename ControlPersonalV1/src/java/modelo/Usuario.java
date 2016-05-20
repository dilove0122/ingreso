/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author DILOVE
 */
@Entity
@Table(name = "usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u"),
    @NamedQuery(name = "Usuario.findByDocumentousuario", query = "SELECT u FROM Usuario u WHERE u.documentousuario = :documentousuario"),
    @NamedQuery(name = "Usuario.findByClaveusuario", query = "SELECT u FROM Usuario u WHERE u.claveusuario = :claveusuario"),
    @NamedQuery(name = "Usuario.findByNombreusuario", query = "SELECT u FROM Usuario u WHERE u.nombreusuario = :nombreusuario")})
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "documentousuario")
    private Long documentousuario;
    @Size(max = 50)
    @Column(name = "claveusuario")
    private String claveusuario;
    @Size(max = 50)
    @Column(name = "nombreusuario")
    private String nombreusuario;

    public Usuario() {
    }

    public Usuario(Long documentousuario) {
        this.documentousuario = documentousuario;
    }

    public Long getDocumentousuario() {
        return documentousuario;
    }

    public void setDocumentousuario(Long documentousuario) {
        this.documentousuario = documentousuario;
    }

    public String getClaveusuario() {
        return claveusuario;
    }

    public void setClaveusuario(String claveusuario) {
        this.claveusuario = claveusuario;
    }

    public String getNombreusuario() {
        return nombreusuario;
    }

    public void setNombreusuario(String nombreusuario) {
        this.nombreusuario = nombreusuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (documentousuario != null ? documentousuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.documentousuario == null && other.documentousuario != null) || (this.documentousuario != null && !this.documentousuario.equals(other.documentousuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Usuario[ documentousuario=" + documentousuario + " ]";
    }
    
}
