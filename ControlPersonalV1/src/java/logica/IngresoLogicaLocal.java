/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.util.List;
import javax.ejb.Local;
import modelo.Ingreso;

/**
 *
 * @author arios
 */
@Local
public interface IngresoLogicaLocal {

    public void registrar(Ingreso ingreso) throws Exception;

    public void modificar(Ingreso ingreso) throws Exception;

    public void eliminar(Ingreso ingreso) throws Exception;

    public List<Ingreso> consultar() throws Exception;

}
