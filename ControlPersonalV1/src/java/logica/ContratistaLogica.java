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
            throw new Exception("El contratista no exixte");
        }
        Contratista objcontratista=contratistaDAO.find(contratista.getNitcontratista());
        if (objcontratista != null) {
            throw new Exception("El contratista que desea registrar ya existe");
        } 
                      
        else {
            contratistaDAO.create(contratista);
        }

    }

    @Override
    public void modificar(Contratista contratista) throws Exception {

        if (contratista == null) {
            throw new Exception("El contratista no exixte");
        }
        Contratista objcontratista = contratistaDAO.find(contratista.getNitcontratista());
        if (objcontratista == null) {
            throw new Exception("El contratista que desea modificar no existe");
        } else {
            
            objcontratista.setNitcontratista(contratista.getNitcontratista());
            objcontratista.setNombrecontratista(contratista.getNombrecontratista());
            
            contratistaDAO.edit(objcontratista);
        }

    }

    @Override
    public void inactivar(Contratista contratista) throws Exception {

            if (contratista == null) {
            throw new Exception("El contratista esta vacio");
        }
        Contratista objContratista = contratistaDAO.find(contratista.getNitcontratista());
        if (objContratista == null) {
            throw new Exception("El contratista no existe");
        } else {
            objContratista.setEstadocontratista("INACTIVO");
            contratistaDAO.edit(objContratista);
        }

    }
    
    
       @Override
    public void activar(Contratista contratista) throws Exception {

            if (contratista == null) {
            throw new Exception("El contratista esta vacio");
        }
        Contratista objContratista = contratistaDAO.find(contratista.getNitcontratista());
        if (objContratista == null) {
            throw new Exception("El contratista no existe");
        } else {
            objContratista.setEstadocontratista("ACTIVO");
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
             return contratistaDAO.find(nit);
        }
        
        
       
    }

}
