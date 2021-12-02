package pl.tomaszlal.mybills.controllers;

import com.gembox.spreadsheet.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pl.tomaszlal.mybills.modelFx.BillsFx;
import pl.tomaszlal.mybills.modelFx.CategoryFx;
import pl.tomaszlal.mybills.modelFx.ListBillsModel;
import pl.tomaszlal.mybills.utils.DialogUtils;
import pl.tomaszlal.mybills.utils.FxmlUtils;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ListBillsController {

    @FXML
    private MenuItem buttonEditBill;

    @FXML
    private ComboBox<CategoryFx> categoryComboBox;

    @FXML
    private CheckBox wasPaidCheckBox;

    @FXML
    private Button markAsPaidButton;

    @FXML
    private MenuItem buttonDeleteBill;


    @FXML
    private TableView<BillsFx> billsTableView;

    @FXML
    private TableColumn<BillsFx, CategoryFx> categoryColumn;

    @FXML
    private TableColumn<BillsFx, String> invoiceColumn;

    @FXML
    private TableColumn<BillsFx, LocalDate> dateOfIssueColumn;

    @FXML
    private TableColumn<BillsFx, LocalDate> dueDateColumn;

    @FXML
    private TableColumn<BillsFx, Double> amountColumn;

    @FXML
    private TableColumn<BillsFx, LocalDate> dateOfPaymentColumn;

    @FXML
    private TableColumn<BillsFx, Boolean> wasPaidColumn;

    @FXML
    private Button saveToXls;


    private ListBillsModel listBillsModel;

    private ResourceBundle bundle = FxmlUtils.getResourceBundle();

    @FXML
    public void initialize(){
        this.listBillsModel = new ListBillsModel();
        this.listBillsModel.init();


        this.billsTableView.setItems(this.listBillsModel.getBillsFxObservableList());
        this.categoryColumn.setCellValueFactory(cellData -> cellData.getValue().categoryFxProperty() );
        this.invoiceColumn.setCellValueFactory(cellData -> cellData.getValue().invoiceNumberProperty());
        this.amountColumn.setCellValueFactory(cellData -> cellData.getValue().amountProperty().asObject());
        this.dateOfIssueColumn.setCellValueFactory(cellData -> cellData.getValue().dateOfIssueProperty());
        this.dueDateColumn.setCellValueFactory(cellData -> cellData.getValue().dateDueProperty());
        this.dateOfPaymentColumn.setCellValueFactory(cellData -> cellData.getValue().dateOfPaymentProperty());
        this.wasPaidColumn.setCellValueFactory(cellData -> cellData.getValue().wasPaidProperty());

        //podmiana wartości kolumny zapłacone z boolean na tak /nie
        this.wasPaidColumn.setCellFactory(param -> new TableCell<BillsFx, Boolean>(){
            @Override
            protected void updateItem(Boolean item, boolean empty){
                super.updateItem(item,empty);
                setText(empty ? null : item ? bundle.getString("yes") : bundle.getString("no"));
            }
        });

        //poprawa wyświetlania wartości kwoty do zapłaty
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        this.amountColumn.setCellFactory(param -> new TableCell<BillsFx, Double>(){
            @Override
            protected void updateItem(Double amount, boolean empty){
                super.updateItem(amount,empty);
                if (empty){
                    setText(null);
                }else{
                    setText(currencyFormat.format(amount));
                }
            }
        });





        this.categoryComboBox.setItems(this.listBillsModel.getCategoryFxObservableList());
        this.listBillsModel.categoryFxObjectPropertyProperty().bind(this.categoryComboBox.valueProperty());

        this.listBillsModel.isPaidProperty().bind(this.wasPaidCheckBox.selectedProperty());


        this.billsTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->{
            this.listBillsModel.setBillsFxObjectPropertyEdit(newValue);
            if (this.listBillsModel.getBillsFxObjectPropertyEdit()!=null){
                this.markAsPaidButton.disableProperty().bind(this.listBillsModel.getBillsFxObjectPropertyEdit().wasPaidProperty());
            }

        });

        //button zaznacz jako zapłacone włączony kiedy wybrany jest model;
        this.markAsPaidButton.disableProperty().bind(this.billsTableView.getSelectionModel().selectedItemProperty().isNull());

        //wyłączeni opcji usun z menu kontekstowego
        this.buttonDeleteBill.disableProperty().bind(this.billsTableView.getSelectionModel().selectedItemProperty().isNull());
        //wyłączenie opcji usuń z manu kontekstowego
        this.buttonEditBill.disableProperty().bind(this.billsTableView.getSelectionModel().selectedItemProperty().isNull());

//        this.saveToXls.disableProperty().bind(this.billsTableView.proper);

        this.dueDateColumn.setSortType(TableColumn.SortType.ASCENDING);//sortowanie
        this.billsTableView.getSortOrder().add(dueDateColumn);//sortowanie


        //uruchomienie edycji komórek

        this.invoiceColumn.setCellFactory(TextFieldTableCell.forTableColumn());


    }

    public void filterOnAction() {
        this.listBillsModel.initBilsWithFilter();

    }

    public void clearCategoryComboBox() {
        this.categoryComboBox.setItems(null);
        this.listBillsModel.initBilsWithFilter();
        this.categoryComboBox.setItems(this.listBillsModel.getCategoryFxObservableList());
    }

    public void onActionMarkAsPaidButton() {
//        ustawienie biezacego ID bills w zmiennej ststic klasy ListBillsModel
        this.listBillsModel.setIdBills(this.listBillsModel.getBillsFxObjectPropertyEdit().getId());
        System.out.println(this.listBillsModel.getIdBills());


        Parent parent = FxmlUtils.fxmlLoader("/fxml/markAsPaidDialog.fxml");
        Scene scene = new Scene(parent);
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
        this.listBillsModel.init();
        this.markAsPaidButton.disableProperty().bind(this.billsTableView.getSelectionModel().selectedItemProperty().isNull());
    }

    public void onActionButtonDeleteBill() {
        Optional<ButtonType> buttonType = DialogUtils.dialogConfirmationDelete();
        if (buttonType.get() == ButtonType.OK){
            this.listBillsModel.deleteBillInDataBase();
        }
        this.markAsPaidButton.disableProperty().bind(this.billsTableView.getSelectionModel().selectedItemProperty().isNull());


    }

    public void onEditCommitInvoice(TableColumn.CellEditEvent<BillsFx, String> billsFxStringCellEditEvent) {
        this.listBillsModel.getBillsFxObjectPropertyEdit().setInvoiceNumber(billsFxStringCellEditEvent.getNewValue());
        this.listBillsModel.saveBilsEditInDataBase();
    }

    public void onActionButtonEditBill() {
        //        ustawienie biezacego ID bills w zmiennej ststic klasy ListBillsModel
        this.listBillsModel.setIdBills(this.listBillsModel.getBillsFxObjectPropertyEdit().getId());
        System.out.println(this.listBillsModel.getIdBills());


        Parent parent = FxmlUtils.fxmlLoader("/fxml/editBills.fxml");
        Scene scene = new Scene(parent);
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
        this.listBillsModel.init();
        //odswieżenie włączenia przycisku "zapznacz jako zapłacone"
        this.markAsPaidButton.disableProperty().bind(this.billsTableView.getSelectionModel().selectedItemProperty().isNull());

    }

    static {
        SpreadsheetInfo.setLicense("FREE-LIMITED-KEY");
    }


    public void saveToXlsOnAction()  {
        final int cm = 1300;
        ExcelFile excelFile = new ExcelFile();
        ExcelWorksheet worksheet = excelFile.addWorksheet("Bills");
        for (int row=0; row < billsTableView.getItems().size();row++){
            BillsFx billsFxRow = billsTableView.getItems().get(row);
            List observableListCells = FXCollections.observableArrayList();
            observableListCells.add(billsFxRow.getCategoryFx().getName());
            observableListCells.add(billsFxRow.getInvoiceNumber());
//            observableListCells.add(billsFxRow.getDateOfIssue().toEpochDay()+25569);
//            observableListCells.add(LocalDateTime.now());    //nalezy tulkomprzekonwertować z LocalDate do LocalDateTime
            observableListCells.add(billsFxRow.getDateOfIssue().atStartOfDay());
            observableListCells.add(billsFxRow.getDateDue().atStartOfDay());
            observableListCells.add(billsFxRow.getAmount());
            if (billsFxRow.getDateOfPayment()!=null){
                observableListCells.add(billsFxRow.getDateOfPayment().atStartOfDay());
            }else {
                observableListCells.add(null);
            }
            if(billsFxRow.isWasPaid()){
                observableListCells.add("Zapłacone");
            }else {
                observableListCells.add("Nie zapłacone");
            }


            for (int column = 0; column<observableListCells.size();column++){
                if (observableListCells.get(column)!=null){
                    worksheet.getCell(row,column).setValue(observableListCells.get(column));
                    worksheet.getCell(row,column).getStyle().getBorders().setBorders(MultipleBorders.outside(),SpreadsheetColor.fromArgb(0,0,0),LineStyle.THIN);
                    switch (column){
                        case 2:
                            setNumberFormatInCell(worksheet, row, column, "DD.MM.YYYY");
                            break;
                        case 3:
                            setNumberFormatInCell(worksheet, row, column, "DD.MM.YYYY");
                            break;
                        case 4:
                            setNumberFormatInCell(worksheet, row, column, "#,##0.00 [$zł]");
                            break;
                        case 5:
                            setNumberFormatInCell(worksheet, row, column, "DD.MM.YYYY");
                            break;
                    }
                }
            }
            settingColumnWidtchInsExcel(worksheet);
        }

        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter xlsxFilter = new FileChooser.ExtensionFilter("XLSX (*.xlsx)","*.xlsx");
        FileChooser.ExtensionFilter xlsFilter = new FileChooser.ExtensionFilter("XLS (*.xls)","*.xls");
        fileChooser.getExtensionFilters().addAll(xlsFilter,xlsxFilter);
        fileChooser.setSelectedExtensionFilter(xlsxFilter);
        File plik = fileChooser.showSaveDialog(billsTableView.getScene().getWindow());
        if (plik!=null){
            final String selectedDescription = fileChooser.getSelectedExtensionFilter().getDescription();
            String path = plik.getAbsolutePath();
            if (!path.endsWith(".xlsx") && selectedDescription.equals(xlsxFilter.getDescription()) ){
                path += ".xlsx";
            } else if (!path.endsWith(".xls") && selectedDescription.equals(xlsFilter.getDescription())){
                path += ".xls";
            }
            try {
              excelFile.save(path);
            } catch (IOException e) {
                System.out.println(" z excelem coś nie poszło");;
            }
        }

    }

    private void setNumberFormatInCell(ExcelWorksheet worksheet, int row, int column, String s) {
        worksheet.getCell(row, column).getStyle().setNumberFormat(s);
    }

    private void settingColumnWidtchInsExcel(ExcelWorksheet worksheet) {
        worksheet.getColumn(0).setWidth(3,LengthUnit.CENTIMETER);
        worksheet.getColumn(1).setWidth(8,LengthUnit.CENTIMETER);
        worksheet.getColumn(2).setWidth(3,LengthUnit.CENTIMETER);
        worksheet.getColumn(3).setWidth(3,LengthUnit.CENTIMETER);
        worksheet.getColumn(4).setWidth(2,LengthUnit.CENTIMETER);
        worksheet.getColumn(5).setWidth(3,LengthUnit.CENTIMETER);
        worksheet.getColumn(6).setWidth(4,LengthUnit.CENTIMETER);
    }
}
