/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.util.List;
import javax.ejb.Local;
import modelo.Contratista;

/**
 *
 * @author arios
 */
@Local
public interface ContratistaLogicaLocal {

    public void registrar(Contratista contratista) throws Exception;

    public void modificar(Contratista contratista) throws Exception;

    public void inactivar(Contratista contratista) throws Exception;

    public void activar(Contratista contratista) throws Exception;
    
    public void eliminar(Contratista contratista) throws Exception;

    public List<Contratista> consultar() throws Exception;

    public Contratista consultarPorNit(Long nit) throws Exception;
}
