package shipment.report.db;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import shipment.report.db.model.Stock;
import shipment.report.db.model.StockSummary;

@Repository
public class StockDao {
	private static Log logger = LogFactory.getLog(StockDao.class);
	private static String SQL_STOCK_SUMMARY = "SELECT s.barcode, s.quantity, b.description, b.bag, b.location FROM "
			+ " (SELECT barcode, sum(quantity) as quantity FROM IN_STOCK  group by barcode) s " + " left join "
			+ " (SELECT distinct barcode, description, bag, location FROM FASTWAY_BAGS) b " + "on s.barcode = b.barcode";

	@PersistenceContext
	private EntityManager entityManager;

	public void save(List<Stock> stocks) {
		for (Stock stock : stocks) {
			entityManager.merge(stock); //TODO need verify
			logger.info(stock.toString() + " added.");
		}
	}

	public void removeAll() {
		entityManager.createQuery("delete from Stock s").executeUpdate();
	}

	public List<Stock> getAll() {
		return entityManager.createQuery("Select s from Stock s", Stock.class).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<StockSummary> getStockSummary() {
		return entityManager.createNativeQuery(SQL_STOCK_SUMMARY, StockSummary.class).getResultList();
	}

	public List<Stock> getAll(String barcode) {
		return entityManager.createQuery("Select s from Stock s where s.barcode = :barcode ORDER BY s.id DESC", Stock.class).setParameter("barcode", barcode)
				.getResultList();
	}
}
