package excel.app.db;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import excel.app.db.model.Fastway;

@Repository
public class FastwayDao {
	@PersistenceContext
	private EntityManager entityManager;

	public void update(Fastway fastway) {
		entityManager.merge(fastway);
	}

	public List<Fastway> getAll() {
		return entityManager.createQuery("Select a from Fastway a", Fastway.class).getResultList();
	}

	public void removeAll() {
		entityManager.createQuery("delete from Fastway a").executeUpdate();
	}

	public void remove(String barcode) {
		Fastway fastway = entityManager.find(Fastway.class, barcode);
		entityManager.remove(fastway);
	}
}
