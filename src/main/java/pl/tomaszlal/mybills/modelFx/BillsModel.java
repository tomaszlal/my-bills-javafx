package pl.tomaszlal.mybills.modelFx;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.tomaszlal.mybills.database.dao.BillsDao;
import pl.tomaszlal.mybills.database.dao.CategoryDao;
import pl.tomaszlal.mybills.database.dbutils.DbManager;
import pl.tomaszlal.mybills.database.dbutils.Utils;
import pl.tomaszlal.mybills.database.model.Bills;
import pl.tomaszlal.mybills.database.model.Category;

import java.util.List;

public class BillsModel {

    private ObjectProperty<BillsFx> billsFxObjectProperty = new SimpleObjectProperty<>(new BillsFx());
    private ObservableList<CategoryFx> categoryFxObservableList = FXCollections.observableArrayList();


    public void init(){
        initCategoryList();

    }

    private void initCategoryList() {
        CategoryDao categoryDao =new CategoryDao();
        List<Category> categoryList = categoryDao.queryForAll();
        this.categoryFxObservableList.clear();
        for (Category category:categoryList){
            CategoryFx categoryFx = new CategoryFx();
            categoryFx.setId(category.getId());
            categoryFx.setName(category.getName());
            categoryFx.setAccountNumber(category.getAccountNumber());
            this.categoryFxObservableList.add(categoryFx);
        }
        DbManager.closeConnection();
    }

    public void saveBillToDataBase(){
        BillsDao billsDao = new BillsDao();
        Bills bills = new Bills();
        //bills.setId(this.billsFxObjectProperty.get().getId());
        bills.setInvoiceNumber(this.billsFxObjectProperty.get().getInvoiceNumber());
        bills.setAmount(this.billsFxObjectProperty.get().getAmount());
        bills.setDateOfIssue(Utils.convertLocalDateToDate(this.billsFxObjectProperty.get().getDateOfIssue()));
        bills.setDueDate(Utils.convertLocalDateToDate(this.billsFxObjectProperty.get().getDateDue()));

        CategoryDao categoryDao= new CategoryDao();
        Category category = categoryDao.findById(this.billsFxObjectProperty.get().getCategoryFx().getId());
        bills.setCategory(category);
        billsDao.creatOrUpdate(bills);
        DbManager.closeConnection();

    }

    public BillsFx getBillsFxObjectProperty() {
        return billsFxObjectProperty.get();
    }

    public ObjectProperty<BillsFx> billsFxObjectPropertyProperty() {
        return billsFxObjectProperty;
    }

    public void setBillsFxObjectProperty(BillsFx billsFxObjectProperty) {
        this.billsFxObjectProperty.set(billsFxObjectProperty);
    }

    public ObservableList<CategoryFx> getCategoryFxObservableList() {
        return categoryFxObservableList;
    }

    public void setCategoryFxObservableList(ObservableList<CategoryFx> categoryFxObservableList) {
        this.categoryFxObservableList = categoryFxObservableList;
    }
}
