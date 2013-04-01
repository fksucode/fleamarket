package bg.tusofia.fksu.pe.fleamarket.buslogic.impl;

import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.LocalBean;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

/**
 * Session Bean implementation class CommisionRateBean
 */
// NOTE: ejb/beanTypes - singleton
// NOTE: ejb/other - init bean at startup
// NOTE: ejb/other - concurrency management (container)
// NOTE: ejb/transaction - bean managed (on transaction)
@Singleton
@Startup
@LocalBean
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
@TransactionManagement(TransactionManagementType.BEAN)
public class CommisionRateBean {

	private Double commisionRate;

	@PostConstruct
	private void initialize() {
		// call web service to get the daily rate
		commisionRate = 0.8;
	}

	@Lock(LockType.READ)
	public Double getCommisionRate() {
		return commisionRate;
	}

}
