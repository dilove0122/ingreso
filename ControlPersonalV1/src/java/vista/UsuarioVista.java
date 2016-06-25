/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import logica.UsuarioLogicaLocal;
import modelo.Usuario;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.password.Password;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author arios
 */
@ManagedBean(name = "usuarioVista")
@RequestScoped
public class UsuarioVista {

    /**
     * Creates a new instance of UsuarioVista
     */
    @EJB
    private UsuarioLogicaLocal usuarioLogica;

    private Password txtClaveUsuario;
    private Password txtConfirmacion;
    private Password txtClaveAnterior;
    private InputText txtdocUsuario;
    private InputText txtNombre;

    private List<Usuario> listaUsuario = null;

    private Usuario selectedUsuario;

    private CommandButton btnIngresar;
    private CommandButton btnLimpiar;
    private CommandButton btnModificar;
 
    private CommandButton btnEliminar;
    private CommandButton btnRegistrar;

    public UsuarioVista() {
    }

    /**
     * @return the usuarioLogica
     */
    public UsuarioLogicaLocal getUsuarioLogica() {
        return usuarioLogica;
    }

    /**
     * @param usuarioLogica the usuarioLogica to set
     */
    public void setUsuarioLogica(UsuarioLogicaLocal usuarioLogica) {
        this.usuarioLogica = usuarioLogica;
    }

    /**
     * @return the txtdocUsuario
     */
    public InputText getTxtdocUsuario() {
        return txtdocUsuario;
    }

    /**
     * @param txtdocUsuario the txtdocUsuario to set
     */
    public void setTxtdocUsuario(InputText txtdocUsuario) {
        this.txtdocUsuario = txtdocUsuario;
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
     * @return the listaUsuario
     */
    public List<Usuario> getListaUsuario() {

        if (listaUsuario == null) {
            try {
                listaUsuario = usuarioLogica.consultar();
            } catch (Exception ex) {
                Logger.getLogger(vista.UsuarioVista.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listaUsuario;

    }

    /**
     * @param listaUsuario the listaUsuario to set
     */
    public void setListaUsuario(List<Usuario> listaUsuario) {
        this.listaUsuario = listaUsuario;
    }

    /**
     * @return the selectedUsuario
     */
    public Usuario getSelectedUsuario() {
        return selectedUsuario;
    }

    /**
     * @param selectedUsuario the selectedUsuario to set
     */
    public void setSelectedUsuario(Usuario selectedUsuario) {
        this.selectedUsuario = selectedUsuario;
    }

    /**
     * @return the btnIngresar
     */
    public CommandButton getBtnIngresar() {
        return btnIngresar;
    }

    /**
     * @param btnIngresar the btnIngresar to set
     */
    public void setBtnIngresar(CommandButton btnIngresar) {
        this.btnIngresar = btnIngresar;
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
    public CommandButton getBtnEliminar() {
        return btnEliminar;
    }

    /**
     * @param btnEliminar the btnEliminar to set
     */
    public void setBtnEliminar(CommandButton btnEliminar) {
        this.btnEliminar = btnEliminar;
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

    public void ingresar_usuario() {

        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext extContext = context.getExternalContext();
        String url = "";

        try {

            Usuario usr = new Usuario();

            usr.setClaveusuario(txtClaveUsuario.getValue().toString());
            usr.setNombreusuario(txtdocUsuario.getValue().toString()); //txtNombre hace referencia es al documento del usuario

            Usuario logeado = usuarioLogica.ingresar(usr);

            if (logeado != null) {
                url = extContext.encodeActionURL(context.getApplication().getViewHandler().getActionURL(context, "/ADMIN/gestionIngresos.xhtml"));
                extContext.getSessionMap().put("usuarioControl", logeado);
                extContext.redirect(url);
            }

        } catch (NumberFormatException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", "¡La Identificación debe ser Numérica!"));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", ex.getMessage()));
        }

    }

    public void registrar_Action() {
        Usuario objUsuario = new Usuario();
        try {

            Long doc = Long.parseLong(txtdocUsuario.getValue().toString());
            String nombre = txtNombre.getValue().toString();
            String clave = txtClaveUsuario.getValue().toString();
            String confirmacion = txtConfirmacion.getValue().toString();

            if (confirmacion.equals("")) {
                FacesContext.getCurrentInstance().addMessage("Mensajes", new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "¡La clave de confirmación es obligatoria!"));
            } else {

                if (clave.equals(confirmacion)) {

                    objUsuario.setDocumentousuario(doc);
                    objUsuario.setNombreusuario(nombre);
                    objUsuario.setClaveusuario(clave);

                    usuarioLogica.registrar(objUsuario);
                    FacesContext.getCurrentInstance().addMessage("Mensajes", new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "¡El Usuario se registró con Éxito!"));

                } else {
                    throw new Exception("NO SON IGUALES LA CONTRASEÑA CON LA CONFIRMACION");
                }

            }

        } catch (NumberFormatException e) {
            FacesContext.getCurrentInstance().addMessage("mensajes", new FacesMessage("¡Ingrese la informacion Nuevamente!"));
        } catch (Exception ex) {

            FacesContext.getCurrentInstance().addMessage("mensajes", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ex.getMessage()));
            Logger.getLogger(vista.UsuarioVista.class.getName()).log(Level.SEVERE, null, ex);
        }

        limpiar_action();
        listaUsuario = null;
    }

    public void modificar_action() {

        Usuario objUsuario = new Usuario();
        try {

            Long doc = Long.parseLong(txtdocUsuario.getValue().toString());
            String nombre = txtNombre.getValue().toString();
            String clave = txtClaveUsuario.getValue().toString();
             String claveAnterior = txtClaveAnterior.getValue().toString();
            String confirmacion = txtConfirmacion.getValue().toString();

            if (confirmacion.equals("")) {
                FacesContext.getCurrentInstance().addMessage("Mensajes", new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "¡La clave de confirmación es obligatoria!"));
            } else {

                if (objUsuario.getClaveusuario().equals(claveAnterior) || clave.equals(confirmacion)) {
                    
    // a qui qude para modificar la clave
                    objUsuario.setDocumentousuario(doc);
                    objUsuario.setNombreusuario(nombre);
                    objUsuario.setClaveusuario(clave);

                    usuarioLogica.modificar(objUsuario);
                    FacesContext.getCurrentInstance().addMessage("Mensajes", new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "¡El usuario se Modificó con Éxito!"));

                } else {
                    throw new Exception("NO SON IGUALES LA CONTRASEÑA CON LA CONFIRMACION");
                }

            }

        } catch (NumberFormatException e) {
            FacesContext.getCurrentInstance().addMessage("mensajes", new FacesMessage("Ingrese la informacion Nuevamente "));
        } catch (Exception ex) {

            FacesContext.getCurrentInstance().addMessage("mensajes", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ex.getMessage()));
            Logger.getLogger(vista.UsuarioVista.class.getName()).log(Level.SEVERE, null, ex);
        }

        limpiar_action();

        listaUsuario = null;
    }

    public void cambiarCalve_action() {

        try {
            Usuario logueado = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuarioControl");
            String claveAnterior = txtClaveAnterior.getValue().toString();
            String claveNueva = txtClaveUsuario.getValue().toString();
            String confirmacion = txtConfirmacion.getValue().toString();
            Usuario u = usuarioLogica.consultarPorDocumento(logueado.getDocumentousuario());
            if(claveAnterior.equals("")){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje: ", "¡La clave anterior es Obligatoria!"));
            }else if(!claveAnterior.equals(u.getClaveusuario())){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje: ", "¡La clave anterior no es correcta!"));
            }else if(claveNueva.equals("") || confirmacion.equals("")){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje: ", "¡La clave nueva y confirmación son Obligatorias!"));
            }else if (!claveNueva.equals(confirmacion)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje: ", "¡La clave nueva y confirmación no coinciden!"));
            }else{
                u.setClaveusuario(claveNueva);
                usuarioLogica.cambiarClave(u);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje: ", "¡La clave se actualizó con Éxito!"));
                txtClaveAnterior.setValue("");
                txtClaveUsuario.setValue("");
                txtConfirmacion.setValue("");
            }            
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", ex.getMessage()));
            Logger.getLogger(EmpleadoVista.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cerrarSesion_action() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            ExternalContext extContext = context.getExternalContext();
            extContext.getSessionMap().remove("usuarioControl");
            String url = extContext.encodeActionURL(
                    context.getApplication().getViewHandler().getActionURL(context, "/index.xhtml"));
            extContext.redirect(url);
        } catch (IOException ex) {
            Logger.getLogger(UsuarioVista.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void eliminar_action() throws Exception {
        try {

            String documento = txtdocUsuario.getValue().toString();
            Usuario usuario = usuarioLogica.consultarPorDocumento(Long.parseLong(documento));

            usuarioLogica.eliminar(usuario);
            FacesContext.getCurrentInstance().addMessage("mensajes", new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "¡El usuario se eliminó con Éxito!"));

        } catch (Exception e) {

            FacesContext.getCurrentInstance().addMessage("mensajes", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
            Logger.getLogger(vista.UsuarioVista.class.getName()).log(Level.SEVERE, null, e);

        }
        limpiar_action();

        listaUsuario = null;
    }

    public void seleccionarUsuario(SelectEvent e) {

        Usuario us = (Usuario) e.getObject();

        txtdocUsuario.setValue(us.getDocumentousuario());
        txtNombre.setValue(us.getNombreusuario());
        txtClaveUsuario.setDisabled(true);
        txtConfirmacion.setDisabled(true);
        txtNombre.setDisabled(true);
        txtdocUsuario.setDisabled(true);
        btnRegistrar.setDisabled(true);
    }

    public void limpiar_action() {

        txtdocUsuario.setValue("");
        txtNombre.setValue("");
        txtClaveUsuario.setValue("");
        txtConfirmacion.setValue("");
        btnRegistrar.setDisabled(false);
        txtClaveUsuario.setDisabled(false);
        txtConfirmacion.setDisabled(false);
        txtNombre.setDisabled(false);
        txtdocUsuario.setDisabled(false);
    }

    /**
     * @return the txtClaveUsuario
     */
    public Password getTxtClaveUsuario() {
        return txtClaveUsuario;
    }

    /**
     * @param txtClaveUsuario the txtClaveUsuario to set
     */
    public void setTxtClaveUsuario(Password txtClaveUsuario) {
        this.txtClaveUsuario = txtClaveUsuario;
    }

    /**
     * @return the txtConfirmacion
     */
    public Password getTxtConfirmacion() {
        return txtConfirmacion;
    }

    /**
     * @param txtConfirmacion the txtConfirmacion to set
     */
    public void setTxtConfirmacion(Password txtConfirmacion) {
        this.txtConfirmacion = txtConfirmacion;
    }

    /**
     * @return the txtClaveAnterior
     */
    public Password getTxtClaveAnterior() {
        return txtClaveAnterior;
    }

    /**
     * @param txtClaveAnterior the txtClaveAnterior to set
     */
    public void setTxtClaveAnterior(Password txtClaveAnterior) {
        this.txtClaveAnterior = txtClaveAnterior;
    }

}
