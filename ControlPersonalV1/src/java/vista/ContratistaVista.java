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
import modelo.Contratista;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.event.SelectEvent;

@ManagedBean(name = "contratistaVista")
@RequestScoped
public class ContratistaVista {

    /**
     * Creates a new instance of ContratistaVista
     */
    @EJB
    private ContratistaLogicaLocal contratistaLogica;

    private InputText txtNitContratista;
    private InputText txtNombreContratista;
    private InputText txtEstadoContratista;

    private List<Contratista> listaContratista = null;

    private Contratista selectedContratista;

    private CommandButton btnLimpiar;
    private CommandButton btnModificar;
    private CommandButton btnInactivo;
    private CommandButton btnActivo;
    private CommandButton btnRegistrar;
    private CommandButton btnEliminar;

    public ContratistaVista() {
    }

    /**
     * @return the contratistaLogica
     */
    public ContratistaLogicaLocal getContratistaLogica() {
        return contratistaLogica;
    }

    /**
     * @param contratistaLogica the contratistaLogica to set
     */
    public void setContratistaLogica(ContratistaLogicaLocal contratistaLogica) {
        this.contratistaLogica = contratistaLogica;
    }

    /**
     * @return the txtCedula
     */
    public InputText getTxtNitContratista() {
        return txtNitContratista;
    }

    /**
     * @param txtNitContratista the txtCedula to set
     */
    public void setTxtNitContratista(InputText txtNitContratista) {
        this.txtNitContratista = txtNitContratista;
    }

    /**
     * @return the txtNombre
     */
    public InputText getTxtNombreContratista() {
        return txtNombreContratista;
    }

    /**
     * @param txtNombreContratista the txtNombre to set
     */
    public void setTxtNombreContratista(InputText txtNombreContratista) {
        this.txtNombreContratista = txtNombreContratista;
    }

    /**
     * @return the txtEstadoContratista
     */
    public InputText getTxtEstadoContratista() {
        return txtEstadoContratista;
    }

    /**
     * @param txtEstadoContratista the txtEstadoContratista to set
     */
    public void setTxtEstadoContratista(InputText txtEstadoContratista) {
        this.txtEstadoContratista = txtEstadoContratista;
    }

    /**
     * @return the listaContratista
     */
    /**
     * @return the selectedContratista
     */
    public Contratista getSelectedContratista() {
        return selectedContratista;
    }

    /**
     * @param selectedContratista the selectedContratista to set
     */
    public void setSelectedContratista(Contratista selectedContratista) {
        this.selectedContratista = selectedContratista;
    }

    public List<Contratista> getListaContratista() {
        if (listaContratista == null) {
            try {
                listaContratista = contratistaLogica.consultar();
            } catch (Exception ex) {
                Logger.getLogger(vista.ContratistaVista.class.getName()).log(Level.SEVERE, null, ex);
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
     * @return the btnInactivo
     */
    public CommandButton getBtnInactivo() {
        return btnInactivo;
    }

    /**
     * @param btnInactivo the btnInactivo to set
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

        Contratista objcontratista = new Contratista();

        try {
            String nombre = txtNombreContratista.getValue().toString();
            Long nit = Long.parseLong(txtNitContratista.getValue().toString());
            objcontratista.setNitcontratista(nit);
            objcontratista.setNombrecontratista(txtNombreContratista.getValue().toString());
            objcontratista.setEstadocontratista("ACTIVO");

            if (nombre.equals("")) {
                FacesContext.getCurrentInstance().addMessage("Mensajes", new FacesMessage(FacesMessage.SEVERITY_INFO, " ", "El nombre es obligatorio!"));
            } else {
                contratistaLogica.registrar(objcontratista);
                listaContratista = null;
                limpiar();
                resetearFitrosTabla("formulario:tablaC");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje: ", "¡El contratista se Registró con Éxito!"));

            }
        } catch (NumberFormatException e) {
            FacesContext.getCurrentInstance().addMessage("mensajes", new FacesMessage("¡El nit debe ser numérico!", " "));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", ex.getMessage()));
            Logger.getLogger(ContratistaVista.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void modificar_action() {
        Contratista objcontratista = new Contratista();
        try {
            Contratista objContratistaAnterior = selectedContratista;
            objcontratista.setCodigocontratista(objContratistaAnterior.getCodigocontratista());
            objcontratista.setNitcontratista(Long.parseLong(txtNitContratista.getValue().toString()));
            objcontratista.setNombrecontratista(txtNombreContratista.getValue().toString());

            contratistaLogica.modificar(objcontratista);
            listaContratista = null;
            limpiar();
            resetearFitrosTabla("formulario:tablaC");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje: ", "¡El contratista se Modificó con Éxito!"));
        } catch (NumberFormatException e) {
            FacesContext.getCurrentInstance().addMessage("mensajes", new FacesMessage("¡El nit debe ser numérico!"));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", ex.getMessage()));
            Logger.getLogger(ContratistaVista.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void inactivar_action() {
        try {

            Contratista contratista = new Contratista();

            contratista.setNitcontratista(Long.parseLong(txtNitContratista.getValue().toString()));

            contratistaLogica.inactivar(contratista);
            resetearFitrosTabla("formulario:tablaC");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje: ", "¡El Contratista se desactivó con Éxito!"));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", ex.getMessage()));
            Logger.getLogger(ContratistaVista.class.getName()).log(Level.SEVERE, null, ex);
        }
        listaContratista = null;
        limpiar();
    }

    public void activar_action() {
        try {

            Contratista contratista = new Contratista();

            contratista.setNitcontratista(Long.parseLong(txtNitContratista.getValue().toString()));

            contratistaLogica.activar(contratista);
            resetearFitrosTabla("formulario:tablaC");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje: ", "¡El contratista se activó con Éxito!"));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", ex.getMessage()));
            Logger.getLogger(ContratistaVista.class.getName()).log(Level.SEVERE, null, ex);
        }
        listaContratista = null;
        limpiar();
    }

    public void eliminar_action() {
        try {

            Contratista contratista = new Contratista();

            contratista.setNitcontratista(Long.parseLong(txtNitContratista.getValue().toString()));

            contratistaLogica.eliminar(contratista);
            resetearFitrosTabla("formulario:tablaC");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje: ", "¡El contratista se eliminó con Éxito!"));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", ex.getMessage()));
            Logger.getLogger(ContratistaVista.class.getName()).log(Level.SEVERE, null, ex);
        }
        listaContratista = null;
        limpiar();
    }

    public void limpiar() {

        txtNitContratista.setValue("");
        txtNombreContratista.setValue("");
        txtEstadoContratista.setValue("");
        btnRegistrar.setDisabled(false);
        btnActivo.setDisabled(true);
        btnInactivo.setDisabled(true);
        btnModificar.setDisabled(true);
        btnEliminar.setDisabled(true);
    }

    public void seleccionarContratista(SelectEvent event) {

        Contratista contratista = (Contratista) event.getObject();

        txtNitContratista.setValue(contratista.getNitcontratista());
        txtNombreContratista.setValue(contratista.getNombrecontratista());
        txtEstadoContratista.setValue(contratista.getEstadocontratista());
        txtEstadoContratista.setDisabled(true);
        btnRegistrar.setDisabled(true);
        btnModificar.setDisabled(false);
        btnEliminar.setDisabled(false);
        if (contratista.getEstadocontratista().equals("ACTIVO")) {
            btnActivo.setDisabled(true);
            btnInactivo.setDisabled(false);

        } else {
            if (contratista.getEstadocontratista().equals("INACTIVO")) {
                btnInactivo.setDisabled(true);
                btnActivo.setDisabled(false);
            }
        }
    }

    /**
     * @return the btnActivo
     */
    public CommandButton getBtnActivo() {
        return btnActivo;
    }

    /**
     * @param btnActivo the btnActivo to set
     */
    public void setBtnActivo(CommandButton btnActivo) {
        this.btnActivo = btnActivo;
    }

    public CommandButton getBtnEliminar() {
        return btnEliminar;
    }

    public void setBtnEliminar(CommandButton btnEliminar) {
        this.btnEliminar = btnEliminar;
    }
    
    public void resetearFitrosTabla(String id) {
        DataTable table = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent(id);
        table.reset();
    }

}
