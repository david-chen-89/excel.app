package excel.app.db;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import excel.app.db.model.Fastway;
import excel.app.db.model.Inventory;

@Transactional
@Service
public class DbService {

	@Autowired
	private FastwayDao fastwayDao;
	@Autowired
	private InventoryDao inventoryDao;

	public void addOrUpdateFastways(List<Fastway> fastways) {
		for (Fastway fastway : fastways) {
			fastwayDao.update(fastway);
		}
	}

	public void addOrUpdateFastway(Fastway fastway) {
		fastwayDao.update(fastway);
	}

	public List<Fastway> getAllFastway() {
		return fastwayDao.getAll();
	}

	public void reloadFastway(List<Fastway> fastways) {
		fastwayDao.removeAll();
		addOrUpdateFastways(fastways);
	}

	public void removeFastway(String barcode) {
		fastwayDao.remove(barcode);
	}

	// Inventory
	public void addOrUpdateInventories(List<Inventory> inventories) {
		for (Inventory inventory : inventories) {
			inventoryDao.update(inventory);
		}
	}

	public void addOrUpdateInventory(Inventory inventory) {
		inventoryDao.update(inventory);
	}

	public List<Inventory> getAllInventory() {
		return inventoryDao.getAll();
	}

	public void reloadInventory(List<Inventory> inventories) {
		inventoryDao.removeAll();
		addOrUpdateInventories(inventories);
	}

	public void removeInventory(String barcode) {
		inventoryDao.remove(barcode);
	}
}