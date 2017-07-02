package excel.app.db;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import excel.app.db.model.Inventory;

@Repository
public class InventoryDao {
	@PersistenceContext
	private EntityManager entityManager;

	public void update(Inventory inventory) {
		entityManager.merge(inventory);
	}

	public List<Inventory> getAll() {
		return entityManager.createQuery("Select a from Inventory a", Inventory.class).getResultList();
	}

	public void removeAll() {
		entityManager.createQuery("delete from Inventory a").executeUpdate();
	}

	public void remove(String shipmentNumber) {
		Inventory inventory = entityManager.find(Inventory.class, shipmentNumber);
		entityManager.remove(inventory);
	}
}
