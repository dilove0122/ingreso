/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import logica.ContratistaLogicaLocal;
import logica.EmpleadoLogicaLocal;
import modelo.Contratista;
import modelo.Empleado;

import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.primefaces.component.selectoneradio.SelectOneRadio;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author arios
 */
@ManagedBean(name = "empleadoVista")
@RequestScoped
public class EmpleadoVista {

    /**
     * Creates a new instance of EmpleadoVista
     */
    @EJB
    private EmpleadoLogicaLocal empleadoLogica;

    @EJB
    private ContratistaLogicaLocal contratistaLogica;

    private InputText txtCedula;
    private InputText txtNombre;
    private InputText txtNombre2;
    private InputText txtApellido;
    private InputText txtApellido2;
    private InputText txtTelefono;
    private InputText txtCorreo;
    private InputText txtCargo;
    private SelectOneRadio cmbEps;
    private SelectOneRadio cmbArl;
    private InputText txtEstadoEmpleado;
    private InputText txtCodigoContratista;

    private List<Empleado> listaEmpleado = null;
    private List<Contratista> listaContratista = null;

    private Empleado selectedEmpleado;
    private Contratista selectedContratista;

    private CommandButton btnLimpiar;
    private CommandButton btnModificar;
    private CommandButton btnActivar;
    private CommandButton btnInactivo;
    private CommandButton btnRegistrar;
    private CommandButton btnSeleccionar;

    public EmpleadoVista() {
    }

    /**
     * @return the txtCedula
     */
    public InputText getTxtCedula() {
        return txtCedula;
    }

    /**
     * @param txtCedula the txtCedula to set
     */
    public void setTxtCedula(InputText txtCedula) {
        this.txtCedula = txtCedula;
    }

    /**
     * @return the txtNombre
     */
    public InputText getTxtNombre() {
        return txtNombre;
    }

    /**
     * @param txtNombre the txtNombre to set
     */
    public void setTxtNombre(InputText txtNombre) {
        this.txtNombre = txtNombre;
    }

    /**
     * @return the txtApellido
     */
    public InputText getTxtApellido() {
        return txtApellido;
    }

    /**
     * @param txtApellido the txtApellido to set
     */
    public void setTxtApellido(InputText txtApellido) {
        this.txtApellido = txtApellido;
    }

    /**
     * @return the txtTelefono
     */
    public InputText getTxtTelefono() {
        return txtTelefono;
    }

    /**
     * @param txtTelefono the txtTelefono to set
     */
    public void setTxtTelefono(InputText txtTelefono) {
        this.txtTelefono = txtTelefono;
    }

    /**
     * @return the txtCorreo
     */
    public InputText getTxtCorreo() {
        return txtCorreo;
    }

    /**
     * @param txtCorreo the txtCorreo to set
     */
    public void setTxtCorreo(InputText txtCorreo) {
        this.txtCorreo = txtCorreo;
    }

    /**
     * @return the txtEstadoEmpleado
     */
    public InputText getTxtEstadoEmpleado() {
        return txtEstadoEmpleado;
    }

    /**
     * @param txtEstadoEmpleado the txtEstadoEmpleado to set
     */
    public void setTxtEstadoEmpleado(InputText txtEstadoEmpleado) {
        this.txtEstadoEmpleado = txtEstadoEmpleado;
    }

    /**
     * @return the txtCodigoContratista
     */
    public InputText getTxtCodigoContratista() {
        return txtCodigoContratista;
    }

    /**
     * @param txtCodigoContratista the txtCodigoContratista to set
     */
    public void setTxtCodigoContratista(InputText txtCodigoContratista) {
        this.txtCodigoContratista = txtCodigoContratista;
    }

    /**
     * @return the listaEmpleado
     */
    public List<Empleado> getListaEmpleado() {

        if (listaEmpleado == null) {
            try {
                listaEmpleado = empleadoLogica.consultar();
            } catch (Exception ex) {
                Logger.getLogger(vista.EmpleadoVista.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listaEmpleado;

    }

    /**
     * @param listaEmpleado the listaEmpleado to set
     */
    public void setListaEmpleado(List<Empleado> listaEmpleado) {
        this.listaEmpleado = listaEmpleado;
    }

    /**
     * @return the selectedEmpleado
     */
    public Empleado getSelectedEmpleado() {
        return selectedEmpleado;
    }

    /**
     * @param selectedEmpleado the selectedEmpleado to set
     */
    public void setSelectedEmpleado(Empleado selectedEmpleado) {
        this.selectedEmpleado = selectedEmpleado;
    }

    /**
     * @return the btnLimpiar
     */
    public CommandButton getBtnLimpiar() {
        return btnLimpiar;
    }

    /**
     * @param btnLimpiar the btnLimpiar to set
     */
    public void setBtnLimpiar(CommandButton btnLimpiar) {
        this.btnLimpiar = btnLimpiar;
    }

    /**
     * @return the btnModificar
     */
    public CommandButton getBtnModificar() {
        return btnModificar;
    }

    /**
     * @param btnModificar the btnModificar to set
     */
    public void setBtnModificar(CommandButton btnModificar) {
        this.btnModificar = btnModificar;
    }

    /**
     * @return the btnEliminar
     */
    public CommandButton getBtnInactivo() {
        return btnInactivo;
    }

    /**
     * @param btnInactivo the btnEliminar to set
     */
    public void setBtnInactivo(CommandButton btnInactivo) {
        this.btnInactivo = btnInactivo;
    }

    /**
     * @return the btnRegistrar
     */
    public CommandButton getBtnRegistrar() {
        return btnRegistrar;
    }

    /**
     * @param btnRegistrar the btnRegistrar to set
     */
    public void setBtnRegistrar(CommandButton btnRegistrar) {
        this.btnRegistrar = btnRegistrar;
    }

    public void registrar_action() {
        try {

            Empleado empleado = new Empleado();
            Contratista contratistaObj = new Contratista();
            empleado.setCedulaempleado(Long.parseLong(txtCedula.getValue().toString()));
            empleado.setNombreempleado(txtNombre.getValue().toString().trim().toUpperCase() + " " + txtNombre2.getValue().toString().trim().toUpperCase());
            empleado.setApellidoempleado(txtApellido.getValue().toString().trim().toUpperCase() + " " + txtApellido2.getValue().toString().trim().toUpperCase());
            empleado.setTelefonoempleado(txtTelefono.getValue().toString());
            empleado.setCorreoempleado(txtCorreo.getValue().toString());
            empleado.setCargoempleado(txtCargo.getValue().toString().trim().toUpperCase());
            empleado.setEpsempleado((getCmbEps().getValue().toString()));
            empleado.setArlempleado(getCmbArl().getValue().toString());
            empleado.setEstadoempleado("ACTIVO");
            if (txtCodigoContratista.getValue() != null) {
                contratistaObj.setNitcontratista(Long.parseLong(txtCodigoContratista.getValue().toString()));
                empleado.setContratistaempleado(contratistaObj);
            }
            
            empleadoLogica.registrar(empleado);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje: ", "¡El Empleado se registró con Éxito!"));
        } catch (NumberFormatException e) {
            FacesContext.getCurrentInstance().addMessage("mensajes", new FacesMessage("¡La cédula y el teléfono debe ser numérica!", " "));
        } catch (NullPointerException e) {
            FacesContext.getCurrentInstance().addMessage("mensajes", new FacesMessage("¡La ARL y la EPS son obligatorias!", " "));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", ex.getMessage()));
            Logger.getLogger(EmpleadoVista.class.getName()).log(Level.SEVERE, null, ex);
        }
        listaEmpleado = null;
        limpiar();
    }

    public void modificar_action() {
        try {

            Empleado empleado = new Empleado();
            Contratista contratistaObj = new Contratista();
            empleado.setCedulaempleado(Long.parseLong(txtCedula.getValue().toString()));
            empleado.setNombreempleado(txtNombre.getValue().toString().trim() + " " + txtNombre2.getValue().toString().trim());
            empleado.setApellidoempleado(txtApellido.getValue().toString().trim() + " " + txtApellido2.getValue().toString().trim());
            empleado.setTelefonoempleado(txtTelefono.getValue().toString());
            empleado.setCorreoempleado(txtCorreo.getValue().toString());
            empleado.setCargoempleado(txtCargo.getValue().toString());
            empleado.setEpsempleado((getCmbEps().getValue().toString()));
            empleado.setArlempleado(getCmbArl().getValue().toString());
            contratistaObj.setNitcontratista(Long.parseLong(txtCodigoContratista.getValue().toString()));
            empleado.setContratistaempleado(contratistaObj);
            empleadoLogica.modificar(empleado);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje: ", "¡El Empleado se modificó con Éxito!"));
        } catch (NumberFormatException e) {
            FacesContext.getCurrentInstance().addMessage("mensajes", new FacesMessage("¡La cédula y el teléfono debe ser numérica!", " "));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", ex.getMessage()));
            Logger.getLogger(EmpleadoVista.class.getName()).log(Level.SEVERE, null, ex);
        }
        listaEmpleado = null;
        limpiar();
    }

    public void inactivar_action() {
        try {

            Empleado empleado = new Empleado();

            empleado.setCedulaempleado(Long.parseLong(txtCedula.getValue().toString()));

            empleadoLogica.inactivar(empleado);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje: ", "¡El Empleado se desactivó con Éxito!"));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", ex.getMessage()));
            Logger.getLogger(EmpleadoVista.class.getName()).log(Level.SEVERE, null, ex);
        }
        listaEmpleado = null;
        limpiar();
    }

    public void activar_action() {
        try {

            Empleado empleado = new Empleado();

            empleado.setCedulaempleado(Long.parseLong(txtCedula.getValue().toString()));

            empleadoLogica.activar(empleado);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje: ", "¡El Empleado se activó con Éxito!"));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", ex.getMessage()));
            Logger.getLogger(EmpleadoVista.class.getName()).log(Level.SEVERE, null, ex);
        }
        listaEmpleado = null;
        limpiar();
    }

    public void limpiar() {

        txtNombre.setValue("");
        txtApellido.setValue("");
        txtNombre2.setValue("");
        txtApellido2.setValue("");
        txtCedula.setValue("");
        txtCorreo.setValue("");
        getCmbEps().setValue("-1");
        getCmbArl().setValue("-1");
        txtTelefono.setValue("");
        txtEstadoEmpleado.setValue("");
        btnRegistrar.setDisabled(false);
        txtCodigoContratista.setValue("");
        txtCargo.setValue("");
        btnActivar.setDisabled(false);
        btnInactivo.setDisabled(false);
        listaContratista = null;

    }

    public void seleccionarEmpleado(SelectEvent event) {

        Empleado empleado = (Empleado) event.getObject();
        String nombre = empleado.getNombreempleado();
        String apellido = empleado.getApellidoempleado();
        String[] datosN = nombre.split(" ");
        String[] datosA = apellido.split(" ");
        txtNombre.setValue(datosN[0]);
        if (datosN.length > 1) {
            txtNombre2.setValue(datosN[1]);
        } else {
            txtNombre2.setValue("");
        }
        txtApellido.setValue(datosA[0]);
        if (datosA.length > 1) {
            txtApellido2.setValue(datosA[1]);
        } else {
            txtApellido2.setValue("");
        }
        txtCedula.setValue(empleado.getCedulaempleado());
        txtCorreo.setValue(empleado.getCorreoempleado());
        txtTelefono.setValue(empleado.getTelefonoempleado());
        cmbEps.setValue(empleado.getEpsempleado());
        cmbArl.setValue(empleado.getArlempleado());
        txtEstadoEmpleado.setValue(empleado.getEstadoempleado());
        txtCargo.setValue(empleado.getCargoempleado());
        if (empleado.getEstadoempleado().equals("ACTIVO")) {
            btnActivar.setDisabled(true);
            btnInactivo.setDisabled(false);

        } else {
            if (empleado.getEstadoempleado().equals("INACTIVO")) {
                btnInactivo.setDisabled(true);
                btnActivar.setDisabled(false);
            }
        }
        txtEstadoEmpleado.setDisabled(true);

        btnRegistrar.setDisabled(true);
        if (empleado.getContratistaempleado() != null) {
            txtCodigoContratista.setValue(empleado.getContratistaempleado().getNitcontratista());
        } else {
            txtCodigoContratista.setValue("");

        }
        listaContratista = null;
        // btnEliminar.setDisabled(false);
        // btnModificar.setDisabled(false);
    }

    /**
     * @return the listaContratista
     */
    public List<Contratista> getListaContratista() {
        System.out.println("Llama lista?");
        if (listaContratista == null) {
            try {
                listaContratista = contratistaLogica.consultar();
            } catch (Exception ex) {
                Logger.getLogger(vista.EmpleadoVista.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listaContratista;

    }

    /**
     * @param listaContratista the listaContratista to set
     */
    public void setListaContratista(List<Contratista> listaContratista) {
        this.listaContratista = listaContratista;
    }

    /**
     * @return the cmbEps
     */
    public SelectOneRadio getCmbEps() {
        return cmbEps;
    }

    /**
     * @param cmbEps the cmbEps to set
     */
    public void setCmbEps(SelectOneRadio cmbEps) {
        this.cmbEps = cmbEps;
    }

    /**
     * @return the cmbArl
     */
    public SelectOneRadio getCmbArl() {
        return cmbArl;
    }

    /**
     * @param cmbArl the cmbArl to set
     */
    public void setCmbArl(SelectOneRadio cmbArl) {
        this.cmbArl = cmbArl;
    }

    /**
     * @return the btnActivar
     */
    public CommandButton getBtnActivar() {
        return btnActivar;
    }

    /**
     * @param btnActivar the btnActivar to set
     */
    public void setBtnActivar(CommandButton btnActivar) {
        this.btnActivar = btnActivar;
    }

    public InputText getTxtCargo() {
        return txtCargo;
    }

    public void setTxtCargo(InputText txtCargo) {
        this.txtCargo = txtCargo;
    }

    public CommandButton getBtnSeleccionar() {
        return btnSeleccionar;
    }

    public void setBtnSeleccionar(CommandButton btnSeleccionar) {
        this.btnSeleccionar = btnSeleccionar;
    }

    public void seleccionarContratista(SelectEvent event) {
        Contratista cs = (Contratista) event.getObject();
        txtCodigoContratista.setValue(cs.getNitcontratista());
    }

    public Contratista getSelectedContratista() {
        return selectedContratista;
    }

    public void setSelectedContratista(Contratista selectedContratista) {
        this.selectedContratista = selectedContratista;
    }

    public InputText getTxtNombre2() {
        return txtNombre2;
    }

    public void setTxtNombre2(InputText txtNombre2) {
        this.txtNombre2 = txtNombre2;
    }

    public InputText getTxtApellido2() {
        return txtApellido2;
    }

    public void setTxtApellido2(InputText txtApellido2) {
        this.txtApellido2 = txtApellido2;
    }

}
