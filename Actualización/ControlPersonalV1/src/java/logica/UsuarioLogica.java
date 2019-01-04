/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import modelo.Usuario;
import persistencia.UsuarioFacadeLocal;

/**
 *
 * @author arios
 */
@Stateless
public class UsuarioLogica implements UsuarioLogicaLocal {

    @EJB
    private UsuarioFacadeLocal usuarioDAO;

    @Override
    public void registrar(Usuario usuario) throws Exception {

        if (usuario == null) {
            throw new Exception("el usuario no exixte");
        }

        if (usuario.getNombreusuario().equals("")) {

            throw new Exception("el nombre es obligatorio");
        }

        if (usuario.getDocumentousuario() < 1) {

            throw new Exception("el documentos  no es correcto");
        }

        if (usuario.getClaveusuario().equals("")) {

            throw new Exception("la clave es obligatoria");
        }

        if (usuario.getClaveusuario().equals(usuario.getNombreusuario())) {

            throw new Exception("la clave no puede ser la misma que el nombre");
        }

        Usuario objEmpleado = usuarioDAO.find(usuario.getDocumentousuario());
        if (objEmpleado != null) {
            throw new Exception("el usuario que desea registrar ya existe");
        } else {

            usuarioDAO.create(usuario);
        }

    }

    @Override
    public void modificar(Usuario usuario) throws Exception {

        if (usuario == null) {
            throw new Exception("el usuario no exixte");
        }

        if (usuario.getNombreusuario().equals("")) {

            throw new Exception("el nombre es obligatorio");
        }

        if (usuario.getDocumentousuario() < 1) {

            throw new Exception("el documentos  no es correcto");
        }

        if (usuario.getClaveusuario().equals("")) {

            throw new Exception("la clave es obligatoria");
        }

        if (usuario.getClaveusuario().equals(usuario.getNombreusuario())) {

            throw new Exception("la clave no puede ser la misma que el nombre");
        }

        Usuario objUsuario = usuarioDAO.find(usuario.getDocumentousuario());
        if (objUsuario == null) {
            throw new Exception("el usuario que desea modificar no existe");
        } else {

            objUsuario.setDocumentousuario(usuario.getDocumentousuario());
            objUsuario.setNombreusuario(usuario.getNombreusuario());
            objUsuario.setClaveusuario(usuario.getClaveusuario());

            usuarioDAO.edit(objUsuario);
        }

    }

    @Override
    public void eliminar(Usuario usuario) throws Exception {

        if (usuario == null) {
            throw new Exception("el usuario no exixte");
        }
        Usuario objUsuario = usuarioDAO.find(usuario.getDocumentousuario());
        if (objUsuario == null) {
            throw new Exception("el usuario que desea eliminar no existe");
        } else {

            objUsuario.setDocumentousuario(usuario.getDocumentousuario());
            objUsuario.setNombreusuario(usuario.getNombreusuario());
            objUsuario.setClaveusuario(usuario.getClaveusuario());

            usuarioDAO.remove(objUsuario);
        }

    }

    @Override
    public Usuario consultarPorId(Integer idUsuario) throws Exception {

        if (idUsuario == null || idUsuario == 0) {
            throw new Exception("Numero de usuario es incorrecto");
        } else {

            return usuarioDAO.find((Object) idUsuario);
        }

    }

    @Override
    public List<Usuario> consultar() throws Exception {

        return usuarioDAO.findAll();
    }

    @Override
    public Usuario ingresar(Usuario u) throws Exception {

        if (u.getClaveusuario().equals("")) {
            throw new Exception("¡La clave es obligatoria!");
        }

        if (u.getNombreusuario()==null || u.getNombreusuario().equals("")) {
            throw new Exception("¡El nombre de usuario  es obligatorio!");
        }
        Usuario objUsuario = usuarioDAO.findUsuario(u.getNombreusuario());
        if (objUsuario == null) {
            throw new Exception("¡El usuario y la clave son incorrectas!");
        }

        if (!(objUsuario.getClaveusuario().equals(u.getClaveusuario()))) {
            throw new Exception("La clave de Usuario es incorrecta!");
        }

        return objUsuario;

    }

    
    @Override
    public Usuario consultarClave(String clave) throws Exception {

        return usuarioDAO.consultarPorClave(clave);

    }

    @Override
    public Usuario consultarPorDocumento(Long documento) throws Exception {

        if (documento == null || documento == 0) {
            throw new Exception("Numero de documento es incorrecto");
        } else {

            return usuarioDAO.find(documento);
        }

    }
    
    
        @Override
    public void cambiarClave(Usuario u) throws Exception {
            usuarioDAO.edit(u);
       
    }
}
