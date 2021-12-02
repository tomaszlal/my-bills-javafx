package pl.tomaszlal.mybills.database.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import pl.tomaszlal.mybills.database.dbutils.DbManager;
import pl.tomaszlal.mybills.database.model.BaseModel;
import pl.tomaszlal.mybills.database.model.Category;

import java.sql.SQLException;
import java.util.List;

public class CategoryDao {

    Dao<Category,Integer> categoryIntegerDao;
    public CategoryDao() {
        try {
            categoryIntegerDao = DaoManager.createDao(DbManager.getConnectionSource(),Category.class);
            // inicjalizacja dao
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public void creatOrUpdate(Category category) {
        try {
            categoryIntegerDao.createOrUpdate(category);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public List<Category> queryForAll() {
        try {
            return categoryIntegerDao.queryForAll();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }


    public void deleteById(Integer id){
        try {
            categoryIntegerDao.deleteById(id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Category findById(Integer id){
        try {
            return categoryIntegerDao.queryForId(id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

}
