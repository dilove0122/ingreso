/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.util.List;
import javax.ejb.Local;
import modelo.Contratos;

/**
 *
 * @author dilov
 */
@Local
public interface ContratosLogicaLocal {
    public void registrar(Contratos contrato) throws Exception;

    public void modificar(Contratos contrato) throws Exception;

    public List<Contratos> consultar() throws Exception;
}
