package bg.tusofia.fksu.pe.fleamarket.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2013-03-11T09:41:17.283+0200")
@StaticMetamodel(Group.class)
public class Group_ {
	public static volatile SingularAttribute<Group, String> groupId;
	public static volatile ListAttribute<Group, User> users;
}
