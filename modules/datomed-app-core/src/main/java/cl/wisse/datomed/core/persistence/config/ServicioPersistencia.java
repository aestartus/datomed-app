package cl.wisse.datomed.core.persistence.config;

import javax.persistence.EntityManager;

public interface ServicioPersistencia {
	
	public void setEntityManager(EntityManager em);

}
