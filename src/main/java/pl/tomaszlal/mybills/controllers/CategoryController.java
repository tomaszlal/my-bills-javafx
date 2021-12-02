package pl.tomaszlal.mybills.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import pl.tomaszlal.mybills.database.dbutils.Utils;
import pl.tomaszlal.mybills.modelFx.CategoryFx;
import pl.tomaszlal.mybills.modelFx.CategoryModel;
import pl.tomaszlal.mybills.utils.DialogUtils;
import pl.tomaszlal.mybills.utils.FxmlUtils;

import java.util.ResourceBundle;

public class CategoryController {

    @FXML
    private TextField textFieldRecipient;

    @FXML
    private MenuItem deleteMenuItems;

    @FXML
    private TextField textFieldNameCategory;

    @FXML
    private TextField textFieldNoAccount;

    @FXML
    private Button addCategoryButton;

    @FXML
    private TableView<CategoryFx> categoryTableView;

    @FXML
    private TableColumn<CategoryFx, String> nameCategoryColumn;

    @FXML
    private TableColumn<CategoryFx, String> numberAccountColumn;

    @FXML
    private TableColumn<CategoryFx, String> recipientColumn;

    private static ResourceBundle bundle = FxmlUtils.getResourceBundle();

    private CategoryModel categoryModel;

    @FXML
    private void initialize() {
        this.categoryModel = new CategoryModel();
        this.categoryModel.categoryFxObjectPropertyProperty().get().nameProperty().bind(textFieldNameCategory.textProperty());
        this.categoryModel.categoryFxObjectPropertyProperty().get().accountNumberProperty().bind(textFieldNoAccount.textProperty());
        this.categoryModel.categoryFxObjectPropertyProperty().get().recipientProperty().bind(textFieldRecipient.textProperty());
        // połaczenie Category model poprzez property z polami tekstowymi w formularzu
//        this.addCategoryButton.disableProperty().bind(this.textFieldNameCategory.textProperty().isEmpty()
//                .or(this.textFieldNoAccount.textProperty().isEmpty()));
//        this.addCategoryButton.disableProperty().bind();

        buttonAddCategoryDisableEnable();

        this.textFieldNameCategory.textProperty().addListener((observable,oldValue,newValue) -> {
            buttonAddCategoryDisableEnable();

        });

        this.textFieldNoAccount.textProperty().addListener((observable,oldValue,newValue) -> {
            if (!newValue.matches("[0-9]*")){
                String afterPattern = newValue.replaceAll("[^\\d]", "");
                this.textFieldNoAccount.setText(afterPattern);

            }

            if (Utils.isCorrectAccountNumber(this.textFieldNoAccount.getText())){
                textFieldNoAccount.setStyle("");
            } else{
                textFieldNoAccount.setStyle("-fx-border-color: red; -fx-border-width: 1px; -fx-border-radius: 3px");
            }
            buttonAddCategoryDisableEnable();


        });

        //wyłącznie buttona jeśli nie ma danych w polu tekstowym
        this.deleteMenuItems.disableProperty().bind(this.categoryTableView.getSelectionModel().selectedItemProperty().isNull());


        this.categoryTableView.setItems(this.categoryModel.getCategoryFxObservableList());
        this.nameCategoryColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        this.numberAccountColumn.setCellValueFactory(cellData -> cellData.getValue().accountNumberProperty());
        this.recipientColumn.setCellValueFactory(cellData -> cellData.getValue().recipientProperty());
        this.categoryModel.init();

        this.nameCategoryColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.numberAccountColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.recipientColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        // uruchomienie edycji w kolumnach po dwukrotnym kliknieciu w pole

        this.categoryTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.categoryModel.setCategoryFxObjectPropertyEdit(newValue);
        });
    }

    @FXML
    void onActionAddCategoryButton() {
        categoryModel.saveCategoryInDataBase();
        this.textFieldNameCategory.clear();
        this.textFieldNoAccount.clear();
        this.textFieldRecipient.clear();

    }


    public void onEditCommitNameCategory(TableColumn.CellEditEvent<CategoryFx, String> categoryFxStringCellEditEvent) {
        this.categoryModel.getCategoryFxObjectPropertyEdit().setName(categoryFxStringCellEditEvent.getNewValue());
        this.categoryModel.saveCategoryEditInDataBase();
    }

    public void onEditCommitAccountNumber(TableColumn.CellEditEvent<CategoryFx, String> categoryFxStringCellEditEvent) {
        String afterPattern = categoryFxStringCellEditEvent.getNewValue().replaceAll("[^\\d]","");
        if (Utils.isCorrectAccountNumber(afterPattern)){
            this.categoryModel.getCategoryFxObjectPropertyEdit().setAccountNumber(afterPattern);
        }else{
            this.categoryModel.getCategoryFxObjectPropertyEdit().setAccountNumber(categoryFxStringCellEditEvent.getOldValue());
            DialogUtils.dialogError(bundle.getString("error.no.bills"), bundle.getString("account.number")+" \""+categoryFxStringCellEditEvent.getNewValue()+"\""+bundle.getString("error.no.account.content"));
        }
        this.categoryModel.saveCategoryEditInDataBase();
    }

    public void deleteCategoryOnAction() {
        int numberOfRecordsWhithCategory = (int)this.categoryModel.findByIdCategoryInBills(this.categoryModel.getCategoryFxObjectPropertyEdit().getId());
        if (numberOfRecordsWhithCategory==0){
            this.categoryModel.deleteCategoryInDataBase();
        }else {
            DialogUtils.dialogError(bundle.getString("error.delete.category"),bundle.getString("category.label")+" ("+this.categoryModel.getCategoryFxObjectPropertyEdit().getName()+") "+bundle.getString("error.delete.category.content"));
        }

    }

    public void onEditCommitRecipient(TableColumn.CellEditEvent<CategoryFx, String> categoryFxStringCellEditEvent) {
        this.categoryModel.getCategoryFxObjectPropertyEdit().setRecipient(categoryFxStringCellEditEvent.getNewValue());
        this.categoryModel.saveCategoryEditInDataBase();
    }

   private void buttonAddCategoryDisableEnable() {
       if (Utils.isCorrectAccountNumber(this.textFieldNoAccount.getText()) && !this.textFieldNameCategory.getText().isEmpty()) {
           this.addCategoryButton.setDisable(false);
       }else{
           this.addCategoryButton.setDisable(true);
       }
//
   }

}
