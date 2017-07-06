package shipment.report.db;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import shipment.report.db.model.TradeMe;
import shipment.report.db.model.TradeMeId;

@Repository
public class TradeMeDao {
	private static Log logger = LogFactory.getLog(TradeMeDao.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void update(TradeMe tradeMe) {
		entityManager.merge(tradeMe);
	}

	public List<TradeMe> getAll() {
		return entityManager.createQuery("Select a from TradeMe a", TradeMe.class).getResultList();
	}

	public void removeAll() {
		entityManager.createQuery("delete from TradeMe a").executeUpdate();
	}

	public void remove(TradeMeId id) {
		TradeMe tradeMe = entityManager.find(TradeMe.class, id);
		if (tradeMe == null) {
			logger.warn("Failed to find TradeMe record of Shipment Number: " + id.getShipmentNumber() + " and  Product Code" + id.getProductCode() + ".");
			throw new RuntimeException("invalid Shipment Number");
		} else {
			entityManager.remove(tradeMe);
		}
	}
}
