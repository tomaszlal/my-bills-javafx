package pl.tomaszlal.mybills.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import pl.tomaszlal.mybills.modelFx.BillsModel;
import pl.tomaszlal.mybills.modelFx.CategoryFx;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class AddBillsController {

    @FXML
    private Button addBillButton;

    @FXML
    private ComboBox<CategoryFx> categoryComboBox;

    @FXML
    private TextField invoiceTextField;

    @FXML
    private DatePicker dateOfIssueTextField;

    @FXML
    private DatePicker dueDateTextField;

    @FXML
    private TextField amountTextField;

    private BillsModel billsModel;

    @FXML
    public void initialize(){
        this.billsModel = new BillsModel();
        this.billsModel.init();

        this.categoryComboBox.setItems(this.billsModel.getCategoryFxObservableList());

        this.billsModel.getBillsFxObjectProperty().categoryFxProperty().bind(this.categoryComboBox.valueProperty());
        this.billsModel.getBillsFxObjectProperty().invoiceNumberProperty().bind(this.invoiceTextField.textProperty());

        this.amountTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            newValue = newValue.replaceAll(",",".");

            if (!newValue.matches(" [0-9]{10}(\\.[0-9]*)?")) {
                String valueToParse = newValue.replaceAll("[^\\d.]", "");

                try {
                    amountTextField.setText(valueToParse);
                    this.billsModel.getBillsFxObjectProperty().setAmount(Double.parseDouble(valueToParse));
                    System.out.println(billsModel.getBillsFxObjectProperty().getAmount());
                } catch (Exception e) {
                    amountTextField.clear();

//                    System.out.println("bledna liczba");
                }
            }

        });

        this.billsModel.getBillsFxObjectProperty().dateOfIssueProperty().bind(this.dateOfIssueTextField.valueProperty());
        this.billsModel.getBillsFxObjectProperty().dateDueProperty().bind(this.dueDateTextField.valueProperty());

//        wyłączenie przycisku dopuki nie są wypełnione dane
;
        this.addBillButton.disableProperty().bind(
                this.amountTextField.textProperty().isEmpty()
                .or(this.categoryComboBox.valueProperty().isNull())
                .or(this.invoiceTextField.textProperty().isEmpty())
                .or(this.dueDateTextField.valueProperty().isNull())
        );



    }

    public void onActionAddBill() {
        System.out.println(billsModel.getBillsFxObjectProperty().toString());
        this.billsModel.saveBillToDataBase();

        this.categoryComboBox.setValue(null);
        this.invoiceTextField.clear();
        this.amountTextField.clear();
        this.dateOfIssueTextField.setValue(null);
        this.dueDateTextField.setValue(null);
    }
}
