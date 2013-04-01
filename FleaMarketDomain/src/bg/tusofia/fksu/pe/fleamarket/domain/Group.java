package bg.tusofia.fksu.pe.fleamarket.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Table(name = "T_GROUP")
@Entity
public class Group implements Serializable {

	private static final long serialVersionUID = 1L;

	private String groupId;

	private List<User> users;

	public Group() {
	}

	public Group(String groupId) {
		this.groupId = groupId;
	}

	@Id
	@Column(name = "GROUP_ID", nullable = false)
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	@ManyToMany(mappedBy = "groups")
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
}
