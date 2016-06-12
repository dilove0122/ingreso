/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import logica.EmpleadoLogicaLocal;
import logica.IngresoLogicaLocal;
import modelo.Empleado;
import modelo.Ingreso;
import org.primefaces.component.commandbutton.CommandButton;

import org.primefaces.component.inputtext.InputText;

/**
 *
 * @author arios
 */
@ManagedBean(name = "ingresosVista")
@RequestScoped
public class IngresoVista {

    private InputText txtCedula;
    private InputText txtPrimerNombre;
    private InputText txtSegundoNombre;
    private InputText txtPrimerApellido;
    private InputText txtSegundoApellido;
    private InputText txtSexo;
    private InputText txtTipoSangre;
    private InputText txtFechaN;
    private InputText txtCodigo1;
    private InputText txtCodigo2;
    private InputText txtCodigo3;
    private String documento;
    private String datoFechaIngreso;
    private String datoHoraIngreso;
    private String datoCargoIngreso;
    private String datoNombreIngreso;
    private CommandButton btnPrueba;
    
    @EJB
    private IngresoLogicaLocal ingresoLogica;
    @EJB
    private EmpleadoLogicaLocal empleadoLogica;

    /**
     * Creates a new instance of IngresoVista
     */
    public IngresoVista() {
    }
    
    public InputText getTxtCedula() {
        return txtCedula;
    }
    
    public void setTxtCedula(InputText txtCedula) {
        this.txtCedula = txtCedula;
    }
    
    public InputText getTxtPrimerNombre() {
        return txtPrimerNombre;
    }
    
    public void setTxtPrimerNombre(InputText txtPrimerNombre) {
        this.txtPrimerNombre = txtPrimerNombre;
    }
    
    public InputText getTxtSegundoNombre() {
        return txtSegundoNombre;
    }
    
    public void setTxtSegundoNombre(InputText txtSegundoNombre) {
        this.txtSegundoNombre = txtSegundoNombre;
    }
    
    public InputText getTxtPrimerApellido() {
        return txtPrimerApellido;
    }
    
    public void setTxtPrimerApellido(InputText txtPrimerApellido) {
        this.txtPrimerApellido = txtPrimerApellido;
    }
    
    public InputText getTxtSegundoApellido() {
        return txtSegundoApellido;
    }
    
    public void setTxtSegundoApellido(InputText txtSegundoApellido) {
        this.txtSegundoApellido = txtSegundoApellido;
    }
    
    public InputText getTxtSexo() {
        return txtSexo;
    }
    
    public void setTxtSexo(InputText txtSexo) {
        this.txtSexo = txtSexo;
    }
    
    public InputText getTxtTipoSangre() {
        return txtTipoSangre;
    }
    
    public void setTxtTipoSangre(InputText txtTipoSangre) {
        this.txtTipoSangre = txtTipoSangre;
    }
    
    public InputText getTxtCodigo1() {
        return txtCodigo1;
    }
    
    public void setTxtCodigo1(InputText txtCodigo1) {
        this.txtCodigo1 = txtCodigo1;
    }
    
    public InputText getTxtCodigo2() {
        return txtCodigo2;
    }
    
    public void setTxtCodigo2(InputText txtCodigo2) {
        this.txtCodigo2 = txtCodigo2;
    }
    
    public String getDatoFechaIngreso() {
        return datoFechaIngreso;
    }
    
    public void setDatoFechaIngreso(String datoFechaIngreso) {
        this.datoFechaIngreso = datoFechaIngreso;
    }
    
    public String getDatoHoraIngreso() {
        return datoHoraIngreso;
    }
    
    public void setDatoHoraIngreso(String datoHoraIngreso) {
        this.datoHoraIngreso = datoHoraIngreso;
    }

    public String getDatoCargoIngreso() {
        return datoCargoIngreso;
    }

    public void setDatoCargoIngreso(String datoCargoIngreso) {
        this.datoCargoIngreso = datoCargoIngreso;
    }
   
    
    
    public String getDatoNombreIngreso() {
        return datoNombreIngreso;
    }
    
    public void setDatoNombreIngreso(String datoNombreIngreso) {
        this.datoNombreIngreso = datoNombreIngreso;
    }
    
    public InputText getTxtFechaN() {
        return txtFechaN;
    }
    
    public void setTxtFechaN(InputText txtFechaN) {
        this.txtFechaN = txtFechaN;
    }
    
    public InputText getTxtCodigo3() {
        return txtCodigo3;
    }
    
    public void setTxtCodigo3(InputText txtCodigo3) {
        this.txtCodigo3 = txtCodigo3;
    }
    
    public void action_registrar_ingreso() {
        try {
            empleadoLogica.limpiarCache();
            System.out.println("Documento "+documento);
            Empleado empleadoIngreso = empleadoLogica.consultarPorDocumento(Long.parseLong(documento));
            if (empleadoIngreso == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "INGRESO NO AUTORIZADO", "El empleado no se encuentra registrado"));
            } else {
                Ingreso nuevoIngreso = new Ingreso();
                nuevoIngreso.setEmpleadoingreso(empleadoIngreso);
                Date fechaActual = new Date();
                SimpleDateFormat formatoF = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat formatoH = new SimpleDateFormat("HH:mm:ss");
                nuevoIngreso.setFechaingreso(fechaActual);
                nuevoIngreso.setHoraentradaingreso(fechaActual);
                datoFechaIngreso = formatoF.format(fechaActual);
                datoHoraIngreso = formatoH.format(fechaActual);
                datoNombreIngreso = empleadoIngreso.getNombreempleado() + " " + empleadoIngreso.getApellidoempleado();
                datoCargoIngreso = empleadoIngreso.getCargoempleado();
                Ingreso tieneIngreso = ingresoLogica.consultarxFecha(datoFechaIngreso, empleadoIngreso);
                if (tieneIngreso != null && tieneIngreso.getHorasalidaingreso()==null) {
                    Date fechaSalida = new Date();
                    tieneIngreso.setHorasalidaingreso(fechaSalida);
                    ingresoLogica.modificar(tieneIngreso);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "SALIDA REGISTRADA", ""));
                } else {
                    if (empleadoIngreso.getEpsempleado().equals("N")) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "INGRESO NO AUTORIZADO", "El empleado no tiene Activa la EPS"));
                        nuevoIngreso.setAutorizadoingreso("N");
                    } else if (empleadoIngreso.getArlempleado().equals("N")) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "INGRESO NO AUTORIZADO", "El empleado no tiene Activa la ARL"));
                        nuevoIngreso.setAutorizadoingreso("N");
                    } else if (empleadoIngreso.getEstadoempleado().equals("INACTIVO")) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "INGRESO NO AUTORIZADO", "El empleado esta INACTIVO."));
                        nuevoIngreso.setAutorizadoingreso("N");
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "INGRESO AUTORIZADO", ""));                        
                        nuevoIngreso.setAutorizadoingreso("S");
                        ingresoLogica.registrar(nuevoIngreso);
                    }
                    
                }
            }      
            limpiar();
        } catch (Exception ex) {
            Logger.getLogger(IngresoVista.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void limpiar(){
        txtCedula.setValue("");
        txtPrimerNombre.setValue("");
        txtSegundoNombre.setValue("");
        txtPrimerApellido.setValue("");
        txtSegundoApellido.setValue("");
        txtCodigo1.setValue("");
        txtCodigo2.setValue("");
        txtCodigo3.setValue("");
        txtFechaN.setValue("");
        txtSexo.setValue("");
        txtTipoSangre.setValue("");
        documento = "";                    
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }
    
    
}
