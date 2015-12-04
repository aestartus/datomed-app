package cl.wisse.datomed.core.persistence;

import cl.wisse.datomed.core.persistence.entities.Empresa;
import cl.wisse.datomed.core.persistence.entities.Usuario;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nhakor on 22-04-14.
 */
public class EmpresaDAO extends AbstractGenericDAO<Empresa>{

    private static final String institucion_X_NRO_institucion = "SELECT e FROM Empresa e WHERE e.nroinstitucion=:nroinstitucion";
    private static final String EMPRESAS_X_USUARIO_ACTIVO = "SELECT e FROM Empresa e WHERE e.usuario=:usuario";

    public EmpresaDAO(){
        super(Empresa.class);
    }

    public Empresa crearEmpresa(Empresa empresa){
        return crearTX(empresa);
    }

    public Empresa actualizarEmpresa(Empresa empresa){
       return actualizarTX(empresa);
    }

    public Empresa obtenerEmpresa(String nroinstitucion){
        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("nroinstitucion", nroinstitucion);
        return buscarTX(institucion_X_NRO_institucion, parametros);
    }

    public List<Empresa> obtenerEmpresas(Usuario usuario){
        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("usuario", usuario.getId());
        return listaTX(EMPRESAS_X_USUARIO_ACTIVO, parametros);
    }

}
