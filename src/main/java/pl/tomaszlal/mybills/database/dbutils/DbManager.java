package pl.tomaszlal.mybills.database.dbutils;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import pl.tomaszlal.mybills.database.model.Bills;
import pl.tomaszlal.mybills.database.model.Category;

import java.io.IOException;
import java.sql.SQLException;

public class DbManager {

    private static final String JDBC_DRIVER_H2 = "jdbc:h2:./billsDB";
    private static final String USER = "admin";
    private static final String PASS = "admin";

    private static ConnectionSource connectionSource;

    public static void initDatabase(){
        createConnectionSource();
        //dropTable();
        createTable();
        closeConnection();

    }

    private static void createConnectionSource(){
        try {
            connectionSource = new JdbcConnectionSource(JDBC_DRIVER_H2,USER,PASS);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println(throwables.getMessage()); // do obsłużenia przez okno dialogowe
        }

    }

    public static ConnectionSource getConnectionSource(){
        if (connectionSource==null){
            createConnectionSource();
        }
        return connectionSource;
    }

    public static void closeConnection(){
        try {
            connectionSource.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    private static void createTable(){
        try {
            TableUtils.createTableIfNotExists(connectionSource, Bills.class);
            TableUtils.createTableIfNotExists(connectionSource, Category.class);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println(throwables.getMessage());
        }

    }

    private static void dropTable(){
        try {
            TableUtils.dropTable(connectionSource,Bills.class,true);
            TableUtils.dropTable(connectionSource,Category.class, true);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println(throwables.getMessage());
        }


    }
}
