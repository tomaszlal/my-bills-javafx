package pl.tomaszlal.mybills.modelFx;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.tomaszlal.mybills.database.dao.BillsDao;
import pl.tomaszlal.mybills.database.dao.CategoryDao;
import pl.tomaszlal.mybills.database.dbutils.DbManager;
import pl.tomaszlal.mybills.database.model.Bills;
import pl.tomaszlal.mybills.database.model.Category;

import java.util.List;

public class CategoryModel {

    private ObjectProperty<CategoryFx> categoryFxObjectProperty = new SimpleObjectProperty<>(new CategoryFx());
    private ObjectProperty<CategoryFx> categoryFxObjectPropertyEdit = new SimpleObjectProperty<>(new CategoryFx());
    private ObservableList<CategoryFx> categoryFxObservableList = FXCollections.observableArrayList();


    public void init(){
        CategoryDao categoryDao = new CategoryDao();
        this.categoryFxObservableList.clear();
        List<Category> categoryList = categoryDao.queryForAll();
        for (Category category:categoryList){
            CategoryFx categoryFx = new CategoryFx();
            categoryFx.setId(category.getId());
            categoryFx.setName(category.getName());
            categoryFx.setAccountNumber(category.getAccountNumber());
            categoryFx.setRecipient(category.getRecipient());
            this.categoryFxObservableList.add(categoryFx);
        }
        DbManager.closeConnection();
    }

    public void saveCategoryInDataBase(){
        CategoryDao categoryDao = new CategoryDao();
        Category category =new Category();
        category.setName(this.categoryFxObjectProperty.get().getName());
        category.setAccountNumber(this.categoryFxObjectProperty.get().getAccountNumber());
        category.setRecipient(this.categoryFxObjectProperty.get().getRecipient());
        categoryDao.creatOrUpdate(category);
        DbManager.closeConnection();
        this.init();
    }

    public void saveCategoryEditInDataBase(){
        CategoryDao categoryDao = new CategoryDao();
        Category category =new Category();
        category.setId(this.categoryFxObjectPropertyEdit.get().getId());
        category.setName(this.categoryFxObjectPropertyEdit.get().getName());
        category.setAccountNumber(this.categoryFxObjectPropertyEdit.get().getAccountNumber());
        category.setRecipient(this.categoryFxObjectPropertyEdit.get().getRecipient());
        categoryDao.creatOrUpdate(category);
        DbManager.closeConnection();
        this.init();
    }

    public void deleteCategoryInDataBase(){
        CategoryDao categoryDao = new CategoryDao();
        categoryDao.deleteById(this.categoryFxObjectPropertyEdit.get().getId());

        DbManager.closeConnection();
        this.init();
    }

    public long findByIdCategoryInBills(Integer id){
        BillsDao billsDao = new BillsDao();
        long counter = billsDao.findIdInCategory(id);
        DbManager.closeConnection();
        return counter;
    }


    public CategoryFx getCategoryFxObjectProperty() {
        return categoryFxObjectProperty.get();
    }

    public ObjectProperty<CategoryFx> categoryFxObjectPropertyProperty() {
        return categoryFxObjectProperty;
    }

    public void setCategoryFxObjectProperty(CategoryFx categoryFxObjectProperty) {
        this.categoryFxObjectProperty.set(categoryFxObjectProperty);
    }

    public ObservableList<CategoryFx> getCategoryFxObservableList() {
        return categoryFxObservableList;
    }

    public void setCategoryFxObservableList(ObservableList<CategoryFx> categoryFxObservableList) {
        this.categoryFxObservableList = categoryFxObservableList;
    }

    public CategoryFx getCategoryFxObjectPropertyEdit() {
        return categoryFxObjectPropertyEdit.get();
    }

    public ObjectProperty<CategoryFx> categoryFxObjectPropertyEditProperty() {
        return categoryFxObjectPropertyEdit;
    }

    public void setCategoryFxObjectPropertyEdit(CategoryFx categoryFxObjectPropertyEdit) {
        this.categoryFxObjectPropertyEdit.set(categoryFxObjectPropertyEdit);
    }
}
