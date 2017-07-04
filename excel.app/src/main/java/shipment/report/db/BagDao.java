package shipment.report.db;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import shipment.report.db.model.Bag;

@Repository
public class BagDao {
	private static Log logger = LogFactory.getLog(BagDao.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void update(Bag bag) {
		entityManager.merge(bag);
	}

	public List<Bag> getAll() {
		return entityManager.createQuery("Select a from Bag a", Bag.class).getResultList();
	}

	public void removeAll() {
		entityManager.createQuery("delete from Bag a").executeUpdate();
	}

	public void remove(String sku) {
		Bag bag = entityManager.find(Bag.class, sku);
		if (bag == null) {
			logger.warn("Failed to find bag record of " + sku + ".");
			throw new RuntimeException("invalid SKU");
		} else {
			entityManager.remove(bag);
		}
	}
}
