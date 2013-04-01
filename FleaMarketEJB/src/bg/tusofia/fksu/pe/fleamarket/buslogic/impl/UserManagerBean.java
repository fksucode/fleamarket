package bg.tusofia.fksu.pe.fleamarket.buslogic.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import bg.tusofia.fksu.pe.fleamarket.buslogic.UserManager;
import bg.tusofia.fksu.pe.fleamarket.domain.Bidder;
import bg.tusofia.fksu.pe.fleamarket.domain.BidderStatus;
import bg.tusofia.fksu.pe.fleamarket.domain.Group;
import bg.tusofia.fksu.pe.fleamarket.domain.User;

/**
 * Session Bean implementation class UserManagerBean
 */
// NOTE: ejb/security - declare roles
@DeclareRoles({ "USER", "ADMIN" })
@Stateless
public class UserManagerBean implements UserManager {

	private static final String HASH_ALGORITHM = "SHA-256";
	private static final int HEX_ENCODING = 16;
	private static final String CHARACTER_ENCODING = "UTF-8";

	private MessageDigest md;

	@Resource
	private SessionContext context;

	@PersistenceContext
	private EntityManager entityManager;

	@PostConstruct
	public void initialize() throws NoSuchAlgorithmException {
		md = MessageDigest.getInstance(HASH_ALGORITHM);
	}

	@PermitAll
	public void create(User user) throws Exception {
		user.setPassword(hashPassword(user.getPassword()));

		// NOTE: jpa/operations - persist
		entityManager.persist(user);
	}

	private String hashPassword(String plainPassword) throws UnsupportedEncodingException {
		// plain hashing, no random numbers, salts, etc.
		byte[] hash = md.digest(plainPassword.getBytes(CHARACTER_ENCODING));
		BigInteger bigInt = new BigInteger(1, hash);
		return bigInt.toString(HEX_ENCODING);
	}

	// NOTE: ejb/security - declarative access restriction
	@Override
	@RolesAllowed("ADMIN")
	public void disableBidder(Long bidderId) {
		// NOTE: jpa/operations - find
		Bidder user = entityManager.find(Bidder.class, bidderId);
		if (user == null) {
			throw new IllegalArgumentException("No bidder account was found with id: " + bidderId);
		}
		user.setBidderStatus(BidderStatus.INACTIVE);
	}

	@Override
	public void enableBidder(Long bidderId) {
		// NOTE: ejb/security - programatic access restriction
		if (!context.isCallerInRole("ADMIN")) {
			throw new SecurityException("Caller " + context.getCallerPrincipal().getName()
					+ " does not have permiossions to enable bidders");
		}

		Bidder user = entityManager.find(Bidder.class, bidderId);
		if (user == null) {
			throw new IllegalArgumentException("No bidder account was found with id: " + bidderId);
		}
		user.setBidderStatus(BidderStatus.ACTIVE);
	}

	@Override
	public Group getGroup(String groupName) {
		return entityManager.find(Group.class, groupName);
	}

	@Override
	public void createGroup(Group group) {
		entityManager.persist(group);
	}

	@Override
	public User getUser(String userId) {
		return entityManager.find(User.class, userId);
	}
}
