package excel.app.db;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import excel.app.db.model.Bag;
import excel.app.db.model.TradeMe;

@Transactional
@Service
public class DbService {

	@Autowired
	private BagDao bagDao;
	@Autowired
	private TradeMeDao tradeMeDao;

	public void addOrUpdateBags(List<Bag> bags) {
		for (Bag bag : bags) {
			bagDao.update(bag);
		}
	}

	public void addOrUpdateBag(Bag bag) {
		bagDao.update(bag);
	}

	public List<Bag> getAllBag() {
		return bagDao.getAll();
	}

	public void reloadBag(List<Bag> bags) {
		bagDao.removeAll();
		addOrUpdateBags(bags);
	}

	public void removeBag(String sku) {
		bagDao.remove(sku);
	}

	// TradeMe
	public void addOrUpdateTradeMes(List<TradeMe> tradeMes) {
		for (TradeMe tradeMe : tradeMes) {
			tradeMeDao.update(tradeMe);
		}
	}

	public void addOrUpdateTradeMe(TradeMe tradeMe) {
		tradeMeDao.update(tradeMe);
	}

	public List<TradeMe> getAllTradeMe() {
		return tradeMeDao.getAll();
	}

	public void reloadTradeMe(List<TradeMe> tradeMes) {
		tradeMeDao.removeAll();
		addOrUpdateTradeMes(tradeMes);
	}

	public void removeTradeMe(String customerReference) {
		tradeMeDao.remove(customerReference);
	}
}