package bg.tusofia.fksu.pe.fleamarket.buslogic.exception;

import javax.ejb.ApplicationException;

//NOTE: ejb/exceptions - application exception declaration
@ApplicationException(rollback = true)
public class ItemCreationException extends Exception {

	private static final long serialVersionUID = 1422757058550176315L;

	public ItemCreationException(String description) {
		super(description);
	}

}
