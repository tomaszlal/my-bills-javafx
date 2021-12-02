package pl.tomaszlal.mybills.modelFx;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
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

public class ListBillsModel {
    private static int idBills;

    private ObservableList<BillsFx> billsFxObservableList = FXCollections.observableArrayList();
    private ObservableList<CategoryFx> categoryFxObservableList = FXCollections.observableArrayList();
    private BillsFx singleBills = new BillsFx();


    //zmienna w której będzie zapisywanwiersz kolumny w trakcie edycji i
    //zmienna w której zapisane jest aktualny wiersz wybrany w tabeli
    private ObjectProperty<BillsFx> billsFxObjectPropertyEdit = new SimpleObjectProperty<>(new BillsFx());

    private ObjectProperty<CategoryFx> categoryFxObjectProperty = new SimpleObjectProperty<>();
    private BooleanProperty isPaid = new SimpleBooleanProperty();

    public int getIdBills() {
        return idBills;
    }

    public void setIdBills(int idBills) {
        ListBillsModel.idBills = idBills;
    }

    public void init(){
        initBilsWithFilter();

        CategoryDao categoryDao = new CategoryDao();
        List<Category> categoryList = categoryDao.queryForAll();
        this.categoryFxObservableList.clear();
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

    public void initForDialog(){
        CategoryDao categoryDao = new CategoryDao();
        List<Category> categoryList = categoryDao.queryForAll();
        this.categoryFxObservableList.clear();
        for (Category category:categoryList){
            CategoryFx categoryFx = new CategoryFx();
            categoryFx.setId(category.getId());
            categoryFx.setName(category.getName());
            categoryFx.setAccountNumber(category.getAccountNumber());
            categoryFx.setRecipient(category.getRecipient());
            this.categoryFxObservableList.add(categoryFx);
        }
        BillsDao billsDao = new BillsDao();
        Bills bills = billsDao.findByid(idBills);
        BillsFx billsFx = new BillsFx();
        billsFx.setId(bills.getId());
        billsFx.setCategoryFx(Utils.convertCategoryToCategoryFx(bills.getCategory()));
        billsFx.setInvoiceNumber(bills.getInvoiceNumber());
        billsFx.setAmount(bills.getAmount());
        billsFx.setDateOfIssue(Utils.convertDateToLocalDate(bills.getDateOfIssue()));
        billsFx.setDateDue(Utils.convertDateToLocalDate(bills.getDueDate()));
        billsFx.setDateOfPayment(Utils.convertDateToLocalDate(bills.getDateOfPayment()));
        billsFx.setWasPaid(bills.getWasPaid());
        billsFx.setAccountNumber(Utils.convertCategoryToCategoryFx(bills.getCategory()).getAccountNumber());
        billsFx.setRecipient(Utils.convertCategoryToCategoryFx(bills.getCategory()).getRecipient());
        singleBills = billsFx;
        DbManager.closeConnection();
    }

    public void initBilsWithFilter() {
        BillsDao billsDao = new BillsDao();
        List<Bills> billsList = billsDao.queryForAll();
        this.billsFxObservableList.clear();
        for (Bills bills:billsList){
            if (isIsPaid()){
                if (categoryFxObjectProperty.isNotNull().getValue()){
                    if (bills.getCategory().getId()==categoryFxObjectProperty.get().getId() && !bills.getWasPaid()){

                        setBilsFxObservableList(bills);
                    }
                }else if (!bills.getWasPaid()){
                    setBilsFxObservableList(bills);
                }
            }else {
                if (categoryFxObjectProperty.isNotNull().getValue()){
                    if (bills.getCategory().getId()==categoryFxObjectProperty.get().getId()){

                        setBilsFxObservableList(bills);
                    }
                }else {
                    setBilsFxObservableList(bills);
                }
            }
        DbManager.closeConnection();
        }
    }

    private void setBilsFxObservableList(Bills bills) {
        BillsFx billsFx = new BillsFx();
        billsFx.setId(bills.getId());
        billsFx.setCategoryFx(Utils.convertCategoryToCategoryFx(bills.getCategory()));
        billsFx.setInvoiceNumber(bills.getInvoiceNumber());
        billsFx.setAmount(bills.getAmount());
        billsFx.setDateOfIssue(Utils.convertDateToLocalDate(bills.getDateOfIssue()));
        billsFx.setDateDue(Utils.convertDateToLocalDate(bills.getDueDate()));
        billsFx.setDateOfPayment(Utils.convertDateToLocalDate(bills.getDateOfPayment()));
        billsFx.setWasPaid(bills.getWasPaid());
        billsFx.setAccountNumber(Utils.convertCategoryToCategoryFx(bills.getCategory()).getAccountNumber());
        billsFx.setRecipient(Utils.convertCategoryToCategoryFx(bills.getCategory()).getRecipient());
        this.billsFxObservableList.add(billsFx);
    }

    public ObservableList<BillsFx> getBillsFxObservableList() {
        return billsFxObservableList;
    }

    public void setBillsFxObservableList(ObservableList<BillsFx> billsFxObservableList) {
        this.billsFxObservableList = billsFxObservableList;
    }

    public ObservableList<CategoryFx> getCategoryFxObservableList() {
        return categoryFxObservableList;
    }

    public void setCategoryFxObservableList(ObservableList<CategoryFx> categoryFxObservableList) {
        this.categoryFxObservableList = categoryFxObservableList;
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

    public boolean isIsPaid() {
        return isPaid.get();
    }

    public BooleanProperty isPaidProperty() {
        return isPaid;
    }

    public void setIsPaid(boolean isPaid) {
        this.isPaid.set(isPaid);
    }

    public BillsFx getBillsFxObjectPropertyEdit() {
        return billsFxObjectPropertyEdit.get();
    }

    public ObjectProperty<BillsFx> billsFxObjectPropertyEditProperty() {
        return billsFxObjectPropertyEdit;
    }

    public void setBillsFxObjectPropertyEdit(BillsFx billsFxObjectPropertyEdit) {
        this.billsFxObjectPropertyEdit.set(billsFxObjectPropertyEdit);
    }

    public BillsFx getSingleBills() {
        return singleBills;
    }

    public void setSingleBills(BillsFx singleBills) {
        this.singleBills = singleBills;
    }

    public void saveBilsEditInDataBase() {
        BillsDao billsDao = new BillsDao();
        Bills bills = new Bills();
        bills.setId(this.billsFxObjectPropertyEdit.get().getId());
        bills.setCategory(Utils.convertCategoryFxToCategory(this.billsFxObjectPropertyEdit.get().getCategoryFx()));
        bills.setInvoiceNumber(this.billsFxObjectPropertyEdit.get().getInvoiceNumber());
        bills.setAmount(this.billsFxObjectPropertyEdit.get().getAmount());
        bills.setDateOfIssue(Utils.convertLocalDateToDate(this.billsFxObjectPropertyEdit.get().getDateOfIssue()));
        bills.setDueDate(Utils.convertLocalDateToDate(this.billsFxObjectPropertyEdit.get().getDateDue()));
        bills.setDateOfPayment(Utils.convertLocalDateToDate(this.billsFxObjectPropertyEdit.get().getDateOfPayment()));
        bills.setWasPaid(this.billsFxObjectPropertyEdit.get().getWasPaid());

        billsDao.creatOrUpdate(bills);

        DbManager.closeConnection();
        this.init();
    }

    public void copyValueBilsFxToBilsFxObjectPropertyEdit(){
        this.billsFxObjectPropertyEdit.set(singleBills);



    }


    public void deleteBillInDataBase() {
        BillsDao billsDao = new BillsDao();
        billsDao.deleteById(this.billsFxObjectPropertyEdit.get().getId());
        DbManager.closeConnection();
        this.init();
    }
}
