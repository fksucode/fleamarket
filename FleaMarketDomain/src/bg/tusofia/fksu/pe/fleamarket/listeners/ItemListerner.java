package bg.tusofia.fksu.pe.fleamarket.listeners;

import java.util.Date;

import javax.persistence.PostLoad;
import javax.persistence.PrePersist;

import bg.tusofia.fksu.pe.fleamarket.domain.Item;

// NOTE: jpa/listener - class listener
public class ItemListerner {

	private static final int DESCRPTION_LIMIT = 200;

	@PrePersist
	public void setDate(Item item) {
		if (item.getCreatedDate() == null) {
			item.setCreatedDate(new Date());
		}
	}

	@PostLoad
	public void trimDescription(Item item) {
		String description = item.getDescription();
		if (description != null) {
			if (description.length() > DESCRPTION_LIMIT) {
				description = description.substring(0, DESCRPTION_LIMIT) + "...";
			}
			item.setShortDescription(description);
		}
	}
}
