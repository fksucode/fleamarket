package bg.tusofia.fksu.pe.fleamarket.buslogic.exception;

//NOTE: ejb/exceptions - runtime exception
public class OrderingException extends IllegalArgumentException {

	private static final long serialVersionUID = 5708435712240371894L;

	public OrderingException(String description) {
		super(description);
	}

}
