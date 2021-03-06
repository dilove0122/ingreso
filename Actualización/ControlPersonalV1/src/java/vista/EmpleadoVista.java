/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import logica.ContratistaLogicaLocal;
import logica.ContratosLogicaLocal;
import logica.EmpleadoLogicaLocal;
import modelo.Contratista;
import modelo.Contratos;
import modelo.Empleado;
import org.primefaces.component.calendar.Calendar;

import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.primefaces.component.selectoneradio.SelectOneRadio;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

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

    @EJB
    private ContratosLogicaLocal contratoLogica;

    private InputText txtCedula;
    private InputText txtNombre;
    private InputText txtNombre2;
    private InputText txtApellido;
    private InputText txtApellido2;
    private InputText txtTelefono;
    private InputText txtCargo;
    private InputText txtEstadoEmpleado;
    private InputText txtCodigoContratista;
    private Calendar txtFechaInicio;
    private Calendar txtFechaFin;

    private List<Empleado> listaEmpleado = null;
    private List<Contratos> listaContratos = null;
    private LazyDataModel<Empleado> listaEmpleadoFiltro;
    private List<Contratista> listaContratista = null;

    private Empleado selectedEmpleado;
    private Contratista selectedContratista;

    private CommandButton btnLimpiar;
    private CommandButton btnModificar;
    private CommandButton btnActivar;
    private CommandButton btnInactivo;
    private CommandButton btnEliminar;
    private CommandButton btnRegistrar;
    private CommandButton btnSeleccionar;
    private CommandButton btnContratos;

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
                empleadoLogica.limpiarCache();
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

    public Calendar getTxtFechaInicio() {
        return txtFechaInicio;
    }

    public void setTxtFechaInicio(Calendar txtFechaInicio) {
        this.txtFechaInicio = txtFechaInicio;
    }

    public Calendar getTxtFechaFin() {
        return txtFechaFin;
    }

    public void setTxtFechaFin(Calendar txtFechaFin) {
        this.txtFechaFin = txtFechaFin;
    }

    public void registrar_action() {
        try {

            Empleado empleado = new Empleado();
            Contratista contratistaObj = new Contratista();
            empleado.setCedulaempleado(Long.parseLong(txtCedula.getValue().toString()));
            if (txtNombre.getValue().toString().equals("") || txtApellido.getValue().toString().equals("")) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Mensaje: ", "¡Es obligatorio el primer nombre y primer apellido!"));
            } else {
                empleado.setNombreempleado(txtNombre.getValue().toString().trim().toUpperCase() + " " + txtNombre2.getValue().toString().trim().toUpperCase());
                empleado.setApellidoempleado(txtApellido.getValue().toString().trim().toUpperCase() + " " + txtApellido2.getValue().toString().trim().toUpperCase());
                empleado.setTelefonoempleado(txtTelefono.getValue().toString());
                empleado.setCargoempleado(txtCargo.getValue().toString().trim().toUpperCase());
                empleado.setEstadoempleado("ACTIVO");
                Contratos nuevoContrato = new Contratos();
                if (txtCodigoContratista.getValue() != null) {
                    contratistaObj = contratistaLogica.consultarPorNit(Long.parseLong(txtCodigoContratista.getValue().toString()));

                    nuevoContrato.setFechaingreso((Date) txtFechaInicio.getValue());
                    nuevoContrato.setFechasalida((Date) txtFechaFin.getValue());
                    nuevoContrato.setCodigocontratista(contratistaObj);

                }
                empleadoLogica.registrar(empleado);
                Empleado nuevoEmpleadoRegistrado = empleadoLogica.consultarPorDocumento(empleado.getCedulaempleado());
                nuevoContrato.setCodigoempleado(nuevoEmpleadoRegistrado);
                contratoLogica.registrar(nuevoContrato);
                resetearFitrosTabla("formulario:tablaE");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje: ", "¡El Empleado se registró con Éxito!"));
            }
        } catch (NumberFormatException e) {
            FacesContext.getCurrentInstance().addMessage("mensajes", new FacesMessage("¡La cédula y el contratista son obligatorios!", " "));
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
            Empleado objEmpleadoAnterior = selectedEmpleado;
            empleado.setCodigoempleado(objEmpleadoAnterior.getCodigoempleado());
            empleado.setCedulaempleado(Long.parseLong(txtCedula.getValue().toString()));
            empleado.setNombreempleado(txtNombre.getValue().toString().trim() + " " + txtNombre2.getValue().toString().trim());
            empleado.setApellidoempleado(txtApellido.getValue().toString().trim() + " " + txtApellido2.getValue().toString().trim());
            empleado.setTelefonoempleado(txtTelefono.getValue().toString());
            empleado.setCargoempleado(txtCargo.getValue().toString());
            empleado.setEstadoempleado(objEmpleadoAnterior.getEstadoempleado());
            contratistaObj = contratistaLogica.consultarPorNit(Long.parseLong(txtCodigoContratista.getValue().toString()));
            //Validar si es empleado ACTIVO es porque tiene contrato vigente
            List<Contratos> contratos = objEmpleadoAnterior.getContratosList();
            Contratista contratistaActual = null;
            Contratista ultimoContratista = null;
            Contratos contratoActual = null;
            Contratos contratoUltimo = null;

            if (objEmpleadoAnterior.getEstadoempleado().toUpperCase().equals("ACTIVO")) {
                for (Contratos contrato : contratos) {
                    if (contrato.getFechasalida() == null) {
                        contratistaActual = contrato.getCodigocontratista();
                        contratoActual = contrato;
                    }
                }
                Long nit = Long.parseLong(txtCodigoContratista.getValue().toString());
                if (contratistaActual.getNitcontratista() == nit && txtFechaFin.getValue() != null) {
                    contratoActual.setFechasalida((Date) txtFechaFin.getValue());
                    empleado.setEstadoempleado("INACTIVO");
                    contratoLogica.modificar(contratoActual);
                } else {
                    FacesContext.getCurrentInstance().addMessage("mensajes", new FacesMessage("¡No puede modificar el contratista sin haber puesto fecha de salida con el contratista anterior!", " "));
                    empleado.setEstadoempleado("ACTIVO");
                }
                empleadoLogica.modificar(empleado);
            } else {
                Long nit = Long.parseLong(txtCodigoContratista.getValue().toString());
                if (!contratos.isEmpty()) {
                    Date fechaMayor = contratos.get(0).getFechasalida();
                    int posicionContrato = 0;
                    for (int i = 1; i < contratos.size(); i++) {
                        if (contratos.get(i).getFechasalida().before(fechaMayor)) {
                            fechaMayor = contratos.get(i).getFechasalida();
                            posicionContrato = i;
                        }
                    }
                    ultimoContratista = contratos.get(posicionContrato).getCodigocontratista();
                    contratoUltimo = contratos.get(posicionContrato);
                }
                if (ultimoContratista != null) {
                    if (ultimoContratista.getNitcontratista() != nit) {
                        Contratos nuevoContrato = new Contratos();
                        nuevoContrato.setCodigoempleado(empleado);
                        nuevoContrato.setFechaingreso((Date) txtFechaInicio.getValue());
                        nuevoContrato.setCodigocontratista(contratistaObj);
                        empleado.setEstadoempleado("ACTIVO");
                        contratoLogica.registrar(nuevoContrato);
                    } else {
                        //iniciará con el mismo contratista otro contrato
                        if (contratoUltimo.getFechaingreso() != (Date) txtFechaInicio.getValue()) {
                            Contratos nuevoContrato = new Contratos();
                            nuevoContrato.setCodigoempleado(empleado);
                            nuevoContrato.setFechaingreso((Date) txtFechaInicio.getValue());
                            nuevoContrato.setCodigocontratista(contratistaObj);
                            empleado.setEstadoempleado("ACTIVO");
                            contratoLogica.registrar(nuevoContrato);
                        }else{}
                    }
                }
                empleadoLogica.modificar(empleado);
            }
            listaEmpleado = null;
            limpiar();
            resetearFitrosTabla("formulario:tablaE");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje: ", "¡El Empleado se modificó con Éxito!"));
        } catch (NumberFormatException e) {
            FacesContext.getCurrentInstance().addMessage("mensajes", new FacesMessage("¡La cédula, teléfono y contratista son obligatorios!", " "));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", ex.getMessage()));
            Logger.getLogger(EmpleadoVista.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void inactivar_action() {
        try {

            Empleado empleado = new Empleado();

            empleado.setCedulaempleado(Long.parseLong(txtCedula.getValue().toString()));

            empleadoLogica.inactivar(empleado);
            listaEmpleado = null;
            limpiar();
            resetearFitrosTabla("formulario:tablaE");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje: ", "¡El Empleado se desactivó con Éxito!"));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", ex.getMessage()));
            Logger.getLogger(EmpleadoVista.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void activar_action() {
        try {

            Empleado empleado = new Empleado();

            empleado.setCedulaempleado(Long.parseLong(txtCedula.getValue().toString()));

            empleadoLogica.activar(empleado);
            listaEmpleado = null;
            limpiar();
            resetearFitrosTabla("formulario:tablaE");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje: ", "¡El Empleado se activó con Éxito!"));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", ex.getMessage()));
            Logger.getLogger(EmpleadoVista.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void eliminar_action() {
        try {

            Empleado empleado = new Empleado();

            empleado.setCedulaempleado(Long.parseLong(txtCedula.getValue().toString()));

            empleadoLogica.eliminar(empleado);
            listaEmpleado = null;
            limpiar();
            resetearFitrosTabla("formulario:tablaE");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje: ", "¡El Empleado se eliminó con Éxito!"));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", ex.getMessage()));
            Logger.getLogger(EmpleadoVista.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void resetearFitrosTabla(String id) {
        DataTable table = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent(id);
        table.reset();
    }

    public void limpiar() {

        txtNombre.setValue("");
        txtApellido.setValue("");
        txtNombre2.setValue("");
        txtApellido2.setValue("");
        txtCedula.setValue("");
        txtTelefono.setValue("");
        txtEstadoEmpleado.setValue("");
        txtFechaInicio.setValue(null);
        txtFechaFin.setValue(null);
        btnRegistrar.setDisabled(false);
        txtCodigoContratista.setValue("");
        txtFechaFin.setDisabled(true);
        txtCargo.setValue("");
        listaContratista = null;
        btnModificar.setDisabled(true);
        btnEliminar.setDisabled(true);
        btnContratos.setDisabled(true);
    }

    public void seleccionarEmpleado(SelectEvent event) {

        Empleado empleado = (Empleado) event.getObject();
        String nombre = empleado.getNombreempleado();
        String apellido = empleado.getApellidoempleado();
        String[] datosN = nombre.split(" ");
        String[] datosA = apellido.split(" ");
        if (datosN.length > 0) {
            txtNombre.setValue(datosN[0]);
        }
        if (datosN.length > 1) {
            txtNombre2.setValue(datosN[1]);
        } else {
            txtNombre2.setValue("");
        }
        if (datosA.length > 0) {
            txtApellido.setValue(datosA[0]);
        }
        if (datosA.length > 1) {
            txtApellido2.setValue(datosA[1]);
        } else {
            txtApellido2.setValue("");
        }
        txtCedula.setValue(empleado.getCedulaempleado());
        txtTelefono.setValue(empleado.getTelefonoempleado());
        txtEstadoEmpleado.setValue(empleado.getEstadoempleado());
        txtCargo.setValue(empleado.getCargoempleado());
        txtFechaFin.setDisabled(false);
        if (empleado.getEstadoempleado().equals("ACTIVO")) {
            List<Contratos> contratosEmpleado = empleado.getContratosList();
            if (!contratosEmpleado.isEmpty()) {
                for (Contratos contrato : contratosEmpleado) {
                    if (contrato.getFechasalida() == null) {
                        txtCodigoContratista.setValue(contrato.getCodigocontratista().getNitcontratista());
                        txtFechaInicio.setValue(contrato.getFechaingreso());
                        txtFechaFin.setValue(contrato.getFechasalida());
                    }
                }
            } else {
                txtCodigoContratista.setValue("");
                txtFechaInicio.setValue(null);
                txtFechaFin.setValue(null);
            }

        } else {
            if (empleado.getEstadoempleado().equals("INACTIVO")) {
                List<Contratos> contratosEmpleado = empleado.getContratosList();
                int posicionContrato = 0;
                if (!contratosEmpleado.isEmpty()) {
                    Date fechaMayor = contratosEmpleado.get(0).getFechasalida();
                    for (int i = 1; i < contratosEmpleado.size(); i++) {
                        if (contratosEmpleado.get(i).getFechasalida().after(fechaMayor)) {
                            fechaMayor = contratosEmpleado.get(i).getFechasalida();
                            posicionContrato = i;
                        }
                    }
                    txtCodigoContratista.setValue(contratosEmpleado.get(posicionContrato).getCodigocontratista().getNitcontratista());
                    txtFechaInicio.setValue(contratosEmpleado.get(posicionContrato).getFechaingreso());
                    txtFechaFin.setValue(contratosEmpleado.get(posicionContrato).getFechasalida());
                } else {
                    txtCodigoContratista.setValue("");
                    txtFechaInicio.setValue(null);
                    txtFechaFin.setValue(null);
                }
            }
        }
        txtEstadoEmpleado.setDisabled(true);
        btnContratos.setDisabled(false);
        btnRegistrar.setDisabled(true);
        listaContratista = null;
        btnEliminar.setDisabled(false);
        btnModificar.setDisabled(false);
    }

    /**
     * @return the listaContratista
     */
    public List<Contratista> getListaContratista() {
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

    public CommandButton getBtnEliminar() {
        return btnEliminar;
    }

    public void setBtnEliminar(CommandButton btnEliminar) {
        this.btnEliminar = btnEliminar;
    }

    public List<Contratos> getListaContratos() {
        if (selectedEmpleado != null) {
            try {
                Empleado seleccionado = empleadoLogica.consultarPorDocumento(selectedEmpleado.getCedulaempleado());
                listaContratos = seleccionado.getContratosList();
            } catch (Exception ex) {
                Logger.getLogger(EmpleadoVista.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listaContratos;
    }

    public void setListaContratos(List<Contratos> listaContratos) {
        this.listaContratos = listaContratos;
    }

    public CommandButton getBtnContratos() {
        return btnContratos;
    }

    public void setBtnContratos(CommandButton btnContratos) {
        this.btnContratos = btnContratos;
    }

}
