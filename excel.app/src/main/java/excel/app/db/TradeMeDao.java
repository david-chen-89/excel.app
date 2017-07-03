package excel.app.db;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import excel.app.db.model.TradeMe;

@Repository
public class TradeMeDao {
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

	public void remove(String customerReference) {
		TradeMe tradeMe = entityManager.find(TradeMe.class, customerReference);
		entityManager.remove(tradeMe);
	}
}
