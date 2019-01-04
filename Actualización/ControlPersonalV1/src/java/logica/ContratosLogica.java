/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import modelo.Contratos;
import persistencia.ContratosFacadeLocal;

/**
 *
 * @author dilov
 */
@Stateless
public class ContratosLogica implements ContratosLogicaLocal {
    
    @EJB
    private ContratosFacadeLocal contratoDAO;

    @Override
    public void registrar(Contratos contrato) throws Exception {
        if (contrato == null) {
            throw new Exception("¡El contrato no existe!");
        }                      
        else {
            contratoDAO.create(contrato);
        }
    }

    @Override
    public void modificar(Contratos contrato) throws Exception {
        if (contrato == null) {
            throw new Exception("¡El contrato no existe!");
        }
        Contratos objcontrato = contratoDAO.find(contrato.getNumerocontrato());
        if (objcontrato == null) {
            throw new Exception("¡El contrato que desea modificar no existe!");
        } else {
            
            objcontrato.setCodigocontratista(contrato.getCodigocontratista());
            objcontrato.setCodigoempleado(contrato.getCodigoempleado());
            objcontrato.setFechaingreso(contrato.getFechaingreso());
            objcontrato.setFechasalida(contrato.getFechasalida());
            contratoDAO.edit(objcontrato);
        }
    }

    @Override
    public List<Contratos> consultar() throws Exception {
        return contratoDAO.findAll();
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
