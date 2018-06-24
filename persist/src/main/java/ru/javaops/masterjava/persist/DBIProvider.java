package ru.javaops.masterjava.persist;

import static org.slf4j.LoggerFactory.getLogger;

import java.sql.DriverManager;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.logging.SLF4JLog;
import org.skife.jdbi.v2.tweak.ConnectionFactory;
import org.slf4j.Logger;
import ru.javaops.masterjava.persist.dao.AbstractDao;

public class DBIProvider {

  static final String BASE_URL = "jdbc:postgresql://localhost:5432/masterjava";
  static final String BASE_USER = "user";
  static final String BASE_PASSWORD = "password";
  private static final Logger log = getLogger(DBIProvider.class);
  private volatile static ConnectionFactory connectionFactory = null;

  public static void init(ConnectionFactory connectionFactory) {
    DBIProvider.connectionFactory = connectionFactory;
  }

  public static DBI getDBI() {
    return DBIHolder.jDBI;
  }

  public static <T extends AbstractDao> T getDao(Class<T> daoClass) {
    return DBIHolder.jDBI.onDemand(daoClass);
  }

  public static void initDBI(String dbUrl, String dbUser, String dbPassword) {
    DBIProvider.init(() -> {
      try {
        Class.forName("org.postgresql.Driver");
      } catch (ClassNotFoundException e) {
        throw new IllegalStateException("PostgreSQL driver not found", e);
      }
      return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    });
  }

  public static void initDBI() {
    initDBI(BASE_URL, BASE_USER, BASE_PASSWORD);
  }

  private static class DBIHolder {

    static final DBI jDBI;

    static {
      final DBI dbi;
      if (connectionFactory != null) {
        log.info("Init jDBI with  connectionFactory");
        dbi = new DBI(connectionFactory);
      } else {
        try {
          log.info("Init jDBI with  JNDI");
          InitialContext ctx = new InitialContext();
          dbi = new DBI((DataSource) ctx.lookup("java:/comp/env/jdbc/masterjava"));
        } catch (Exception ex) {
          throw new IllegalStateException("PostgreSQL initialization failed", ex);
        }
      }
      jDBI = dbi;
      jDBI.setSQLLog(new SLF4JLog());
    }
  }
}
