package pl.byteit;

import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

public class DatabaseCleaner implements TestExecutionListener {

	@Override
	public void afterTestMethod(TestContext testContext) {
		ApplicationContext applicationContext = testContext.getApplicationContext();
		JdbcTemplate jdbc = getJdbcTemplate(applicationContext);
		PlatformTransactionManager txManager = applicationContext.getBean(PlatformTransactionManager.class);

		new TransactionTemplate(txManager).execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				jdbc.execute("SET REFERENTIAL_INTEGRITY FALSE");
				truncateAllTables(jdbc);
				jdbc.execute("SET REFERENTIAL_INTEGRITY TRUE");
			}
		});
	}

	private JdbcTemplate getJdbcTemplate(ApplicationContext applicationContext) {
		DataSource datasource = applicationContext.getBean(DataSource.class);
		return new JdbcTemplate(datasource);
	}

	private void truncateAllTables(JdbcTemplate jdbc) {
		var rows = jdbc.queryForList("SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA='PUBLIC'");
		rows.forEach(row -> {
			String tableName = ((String) row.values().iterator().next());
			jdbc.execute("TRUNCATE TABLE " + tableName);
		});
	}

}
