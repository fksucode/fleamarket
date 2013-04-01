package bg.tusofia.fksu.pe.fleamarket.buslogic;

import javax.ejb.Local;

import bg.tusofia.fksu.pe.fleamarket.domain.Group;
import bg.tusofia.fksu.pe.fleamarket.domain.User;

@Local
public interface UserManager {

	void create(User user) throws Exception;
	
	User getUser(String userId);
	
	void createGroup(Group group);
	
	Group getGroup(String groupName);

	void disableBidder(Long bidderId);

	void enableBidder(Long bidderId);

}