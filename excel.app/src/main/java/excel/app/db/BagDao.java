package excel.app.db;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import excel.app.db.model.Bag;

@Repository
public class BagDao {
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
		entityManager.remove(bag);
	}
}
