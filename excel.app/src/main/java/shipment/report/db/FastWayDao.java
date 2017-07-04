package shipment.report.db;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import shipment.report.Constants;

@Repository
public class FastWayDao {
	@PersistenceContext
	private EntityManager entityManager;

	public List<Object[]> getAll() {
		Query query = entityManager.createNativeQuery(Constants.FastwayQuery);
		@SuppressWarnings("unchecked")
		List<Object[]> results = query.getResultList();
		return results;
	}
}
