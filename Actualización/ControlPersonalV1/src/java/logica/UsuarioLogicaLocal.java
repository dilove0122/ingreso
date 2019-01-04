/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.util.List;
import javax.ejb.Local;
import modelo.Usuario;

/**
 *
 * @author arios
 */
@Local
public interface UsuarioLogicaLocal {

    public void registrar(Usuario usuario) throws Exception;

    public void modificar(Usuario usuario) throws Exception;

    public void eliminar(Usuario usuario) throws Exception;

    public Usuario consultarPorId(Integer idUsuario) throws Exception;
        
    public Usuario consultarPorDocumento(Long documento) throws Exception;

    public List<Usuario> consultar() throws Exception;

    public Usuario ingresar(Usuario u) throws Exception;

        public Usuario consultarClave(String clave) throws Exception;
        
           public void cambiarClave(Usuario u) throws Exception;

}
