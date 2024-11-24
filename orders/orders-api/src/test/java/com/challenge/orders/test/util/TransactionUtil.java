package com.challenge.orders.test.util;

import java.util.function.Function;
import java.util.function.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@TestComponent
public class TransactionUtil {

	@Transactional
	public  <T> T transactionalSupplier(Supplier<T> supplier){
		return supplier.get();
	}

	@Transactional
	public void transactionalRunnable(Runnable runnable){
		runnable.run();
	}
}
