/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import modelo.Contratista;
import modelo.Contratos;
import modelo.Empleado;
import persistencia.ContratistaFacadeLocal;

/**
 *
 * @author arios
 */
@Stateless
public class ContratistaLogica implements ContratistaLogicaLocal {

    @EJB
    private ContratistaFacadeLocal contratistaDAO;

    @Override
    public void registrar(Contratista contratista) throws Exception {

        if (contratista == null) {
            throw new Exception("¡El contratista no existe!");
        }
        Contratista objcontratista=contratistaDAO.findNit(contratista.getNitcontratista());
        if (objcontratista != null) {
            throw new Exception("¡El contratista que desea registrar ya existe!");
        } 
                      
        else {
            contratistaDAO.create(contratista);
        }

    }

    @Override
    public void modificar(Contratista contratista) throws Exception {

        if (contratista == null) {
            throw new Exception("¡El contratista no existe!");
        }
        Contratista objcontratista = contratistaDAO.find(contratista.getCodigocontratista());
        if (objcontratista == null) {
            throw new Exception("¡El contratista que desea modificar no existe!");
        } else {
            
            objcontratista.setNitcontratista(contratista.getNitcontratista());
            objcontratista.setNombrecontratista(contratista.getNombrecontratista());
            
            contratistaDAO.edit(objcontratista);
        }

    }

    @Override
    public void inactivar(Contratista contratista) throws Exception {

            if (contratista == null) {
            throw new Exception("¡El contratista está vacio!");
        }
        Contratista objContratista = contratistaDAO.findNit(contratista.getNitcontratista());
        if (objContratista == null) {
            throw new Exception("¡El contratista no existe!");
        } else {
            objContratista.setEstadocontratista("INACTIVO");
            List<Contratos> listaContratos = objContratista.getContratosList();
            for (Contratos contrato : listaContratos) {
                contrato.getCodigoempleado().setEstadoempleado("INACTIVO");
            }
            objContratista.setContratosList(listaContratos);
            contratistaDAO.edit(objContratista);
        }

    }
    
    
       @Override
    public void activar(Contratista contratista) throws Exception {

            if (contratista == null) {
            throw new Exception("¡El contratista esta vacio!");
        }
        Contratista objContratista = contratistaDAO.findNit(contratista.getNitcontratista());
        if (objContratista == null) {
            throw new Exception("¡El contratista no existe!");
        } else {
            objContratista.setEstadocontratista("ACTIVO");
            List<Contratos> listaContratos = objContratista.getContratosList();
            for (Contratos contrato : listaContratos) {
                contrato.getCodigoempleado().setEstadoempleado("ACTIVO");
            }
            objContratista.setContratosList(listaContratos);
            contratistaDAO.edit(objContratista);
        }

    }

    @Override
    public List<Contratista> consultar() throws Exception {

        return contratistaDAO.findAll();
    }

    @Override
    public Contratista consultarPorNit(Long nit) throws Exception {
        
             if (nit == null ) {
            throw new Exception("El nit  es incorrecto");
        } else {
             return contratistaDAO.findNit(nit);
        }
        
        
       
    }

    @Override
    public void eliminar(Contratista contratista) throws Exception {
        
            if (contratista == null) {
            throw new Exception("¡El contratista esta vacio!");
        }
        Contratista objContratista = contratistaDAO.findNit(contratista.getNitcontratista());
        if (objContratista == null) {
            throw new Exception("¡El contratista no existe!");
        } else {
            List<Contratos> listaContratos = objContratista.getContratosList();
            if(!listaContratos.isEmpty()){
                throw new Exception("¡El contratista tiene empleados asociados. No se puede Eliminar!");
            }else{
                contratistaDAO.remove(objContratista);
            }
        }
    }

}
