/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import modelo.Contratista;
import modelo.Empleado;
import modelo.Ingreso;

/**
 *
 * @author ADMIN
 */
@Stateless
public class IngresoFacade extends AbstractFacade<Ingreso> implements IngresoFacadeLocal {

    @PersistenceContext(unitName = "ControlPersonalV1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IngresoFacade() {
        super(Ingreso.class);
    }

    @Override
    public Ingreso consultarIngresoxFecha(String fecha, Empleado empleado) {
        getEntityManager().getEntityManagerFactory().getCache().evictAll();
        String consulta = "select ingreso from Ingreso ingreso where ingreso.fechaingreso = '" + fecha + "' and ingreso.empleadoingreso.cedulaempleado=" + empleado.getCedulaempleado() + " and ingreso.horasalidaingreso=null";
        try {
            Query query = em.createQuery(consulta);
            return (Ingreso) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }

    }

    @Override
    public List<Ingreso> consultarIngresosContratista(Contratista c) {
        String consulta = "select ingreso from Ingreso ingreso where ingreso.empleadoingreso.contratistaempleado.nitcontratista=" + c.getNitcontratista() + " order by ingreso.empleadoingreso.cedulaempleado";
        Query query = em.createQuery(consulta);
        return query.getResultList();
    }

    @Override
    public List<Ingreso> consultarIngresosContratista(Contratista c, String fechai, String fechaf) {
        String consulta = "select ingreso from Ingreso ingreso where ingreso.empleadoingreso.contratistaempleado.nitcontratista=" + c.getNitcontratista() + " and ingreso.fechaingreso between '" + fechai + "' and '" + fechaf + "' order by ingreso.empleadoingreso.cedulaempleado";
        System.out.println("COnsulta " + consulta);
        Query query = em.createQuery(consulta);
        return query.getResultList();
    }

    @Override
    public List<Ingreso> consultarIngresosEmpleado(Empleado e) {
        String consulta = "select ingreso from Ingreso ingreso where ingreso.empleadoingreso.cedulaempleado=" + e.getCedulaempleado() + " order by ingreso.empleadoingreso.cedulaempleado";
        System.out.println("Consulta " + consulta);
        Query query = em.createQuery(consulta);
        return query.getResultList();
    }

    @Override
    public List<Ingreso> consultarIngresosEmpleado(Empleado e, String fechai, String fechaf) {
        String consulta = "select ingreso from Ingreso ingreso where ingreso.empleadoingreso.cedulaempleado=" + e.getCedulaempleado() + " and ingreso.fechaingreso between '" + fechai + "' and '" + fechaf + "' order by ingreso.empleadoingreso.cedulaempleado";
        System.out.println("COnsulta " + consulta);
        Query query = em.createQuery(consulta);
        return query.getResultList();
    }

    @Override
    public List<Ingreso> consultarIngresosDiarios(String fechaf) {
        getEntityManager().getEntityManagerFactory().getCache().evictAll();
        String consulta = "select ingreso from Ingreso ingreso where ingreso.fechaingreso = '" + fechaf + "'";
        Query query = em.createQuery(consulta);
        return query.getResultList();
    }

}
