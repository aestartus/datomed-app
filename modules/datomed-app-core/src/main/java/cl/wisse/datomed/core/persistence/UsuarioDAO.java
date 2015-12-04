package cl.wisse.datomed.core.persistence;

import cl.wisse.datomed.core.persistence.entities.Usuario;
import cl.wisse.datomed.core.util.Utiles;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nhakor on 08-07-14.
 */
public class UsuarioDAO extends AbstractGenericDAO<Usuario>{

    private static final String USUARIO_X_CORREO = "SELECT u FROM Usuario u WHERE u.correo=:correo";

    public UsuarioDAO() {
        super(Usuario.class);
    }

    public Usuario crearUsuario(Usuario usuario){
        usuario.setPassword(Utiles.encriptar(usuario.getPassword()));
        return crearTX(usuario);
    }

    public Usuario actualizarUsuario(Usuario usuario){
        return actualizarTX(usuario);
    }

    public Usuario obtenerUsuario(String correo){
        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("correo", correo);
        return buscarTX(USUARIO_X_CORREO, parametros);
    }

}
