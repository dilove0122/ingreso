/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.util.Date;
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
 * @author dilov
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
        String consulta = "select i from Ingreso i where i.fechaingreso = '" + fecha + "' and i.empleadoingreso.cedulaempleado=" + empleado.getCedulaempleado() + " and i.horasalidaingreso=null";
        try {
            Query query = em.createQuery(consulta);
            return (Ingreso) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }

    }

    @Override
    public List<Ingreso> consultarIngresosContratista(Contratista c) {
        String consulta = "select i from Ingreso i where i.contratistaingreso.nitcontratista=" + c.getNitcontratista() + " order by i.empleadoingreso.cedulaempleado";
        Query query = em.createQuery(consulta);
        return query.getResultList();
    }

    @Override
    public List<Ingreso> consultarIngresosContratista(Contratista c, String fechai, String fechaf) {
        String consulta = "select i from Ingreso i where i.contratistaingreso.codigocontratista=" + c.getCodigocontratista()+ " and i.fechaingreso between '" + fechai + "' and '" + fechaf + "' order by i.fechaingreso";
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

    @Override
    public int consultarIngresosContratistasDiarios(String fecha) {
        getEntityManager().getEntityManagerFactory().getCache().evictAll();
        String consulta = "select distinct ingreso.contratistaingreso from ingreso where ingreso.fechaingreso = '" + fecha + "'";
        Query query = em.createNativeQuery(consulta);
        return query.getResultList().size();
    }

    @Override
    public Ingreso consultarIngresoFecha(Contratista c, String fecha, Empleado e) {
        String consulta = "select i from Ingreso i where i.contratistaingreso.nitcontratista=" + c.getNitcontratista() + " and i.fechaingreso = '" + fecha + "' and  i.empleadoingreso.cedulaempleado= " + e.getCedulaempleado() + " order by i.fechaingreso";
        System.out.println("COnsulta " + consulta);
        try {
            Query query = em.createQuery(consulta);
            List<Ingreso> resultado = query.getResultList();
            if (resultado.isEmpty()) {
                return null;
            } else {
                return resultado.get(0);
            }
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public List<Ingreso> consultarIngresosContratistaEmpleado(Contratista c, Empleado e, String fechaI, String fechaS) {
        String consulta = "";
        if (fechaS != null) {
            consulta = "select i from Ingreso i where i.contratistaingreso.nitcontratista=" + c.getNitcontratista() + " and  i.empleadoingreso.cedulaempleado= " + e.getCedulaempleado() + " and i.fechaingreso between '" + fechaI + "' and '" + fechaS + "' order by i.fechaingreso";
        } else {
            consulta = "select i from Ingreso i where i.contratistaingreso.nitcontratista=" + c.getNitcontratista() + " and  i.empleadoingreso.cedulaempleado= " + e.getCedulaempleado() + " and i.fechaingreso >= '" + fechaI + "' order by i.fechaingreso";
        }

        System.out.println("Consulta " + consulta);
        Query query = em.createQuery(consulta);
        return query.getResultList();
    }

}
