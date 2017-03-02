package com.spring.rest.v2.utils;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Service
public class TxManager {
	
	private Logger log = Logger.getLogger(this.getClass());
	private TransactionStatus status;
	
	@Autowired
	private DataSourceTransactionManager txManager;
	
	public void createTx() {
		try {
			DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
		    
		    /*
		     * 按照SPC上的解釋，使用PROPAGATION_REQUIRED交易機制最為普遍，但在insert後想做別的操作會出現data lock，導致拋出lock timeout例外錯誤。
		     */
			defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
		    status = txManager.getTransaction(defaultTransactionDefinition);
		} catch (Exception e) {
			log.error(e, e);
		}
	}
	
	public void commit() {
		try {
			txManager.commit(status);	
		} catch (Exception e) {
			log.error(e, e);
		}
	}
	
	public void rollback() {
		try {
			txManager.rollback(status);	
		} catch (Exception e) {
			log.error(e, e);
		}
	}
	
}
