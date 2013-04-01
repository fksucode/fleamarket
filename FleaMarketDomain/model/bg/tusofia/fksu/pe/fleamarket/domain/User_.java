package bg.tusofia.fksu.pe.fleamarket.domain;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2013-03-11T09:41:17.486+0200")
@StaticMetamodel(User.class)
public class User_ {
	public static volatile SingularAttribute<User, String> userId;
	public static volatile SingularAttribute<User, String> password;
	public static volatile ListAttribute<User, Group> groups;
	public static volatile SingularAttribute<User, String> email;
	public static volatile SingularAttribute<User, String> firstName;
	public static volatile SingularAttribute<User, String> lastName;
	public static volatile SingularAttribute<User, byte[]> picture;
	public static volatile SingularAttribute<User, Date> birthDate;
	public static volatile SingularAttribute<User, String> telephone;
	public static volatile SingularAttribute<User, Address> address;
}
