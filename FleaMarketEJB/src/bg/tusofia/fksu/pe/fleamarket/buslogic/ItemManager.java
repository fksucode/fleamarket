package bg.tusofia.fksu.pe.fleamarket.buslogic;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import bg.tusofia.fksu.pe.fleamarket.buslogic.exception.ItemCreationException;
import bg.tusofia.fksu.pe.fleamarket.domain.Item;

@Local
public interface ItemManager {

	Item addItem(String title, Double initialPrice, Date bidEndDate, String description, String userId)
			throws ItemCreationException;

	void deleteItem(Long itemId);

	Item getItem(Long itemId);

	List<Item> getItems(String userId);

	List<Item> getAvaiableItems();

	boolean isItemWonByUser(Long itemId, String userId);
	
	boolean isItemWon(Long itemId);

	boolean isItemOrdered(Long itemId);

}
