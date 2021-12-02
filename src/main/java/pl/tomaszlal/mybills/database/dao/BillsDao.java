package pl.tomaszlal.mybills.database.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import pl.tomaszlal.mybills.database.dbutils.DbManager;
import pl.tomaszlal.mybills.database.model.Bills;
import pl.tomaszlal.mybills.database.model.Category;

import java.sql.SQLException;
import java.util.List;

public class BillsDao  {
    Dao<Bills, Integer> billsIntegerDao;

    public BillsDao() {
        try {
            billsIntegerDao = DaoManager.createDao(DbManager.getConnectionSource(),Bills.class);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void creatOrUpdate(Bills bills){
        try {
            billsIntegerDao.createOrUpdate(bills);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public List<Bills> queryForAll(){
        try {
            return  billsIntegerDao.queryForAll();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public Bills findByid(Integer id){
        try {
            return billsIntegerDao.queryForId(id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }


    public void deleteById(Integer id) {
        try {
            billsIntegerDao.deleteById(id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public long findIdInCategory(Integer id){
        long result = 0;
        try {
            result = billsIntegerDao.queryRawValue("SELECT COUNT(*) FROM BILLS where Category_id = ?",id.toString());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }
}
