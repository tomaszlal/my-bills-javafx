package pl.tomaszlal.mybills.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import pl.tomaszlal.mybills.database.dbutils.Utils;
import pl.tomaszlal.mybills.modelFx.CategoryFx;
import pl.tomaszlal.mybills.modelFx.ListBillsModel;
import pl.tomaszlal.mybills.utils.FxmlUtils;

import java.util.ResourceBundle;

public class EditBillsController {

    ListBillsModel listBillsModel;

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

    @FXML
    private DatePicker dateOfPaymentTextField;

    @FXML
    private ToggleButton wasPaidToggleButton;

    @FXML
    private Button saveEditableBillButton;

    @FXML
    private Button clearDateOfPayButton;

    private ResourceBundle bundle = FxmlUtils.getResourceBundle();

    @FXML
    private void initialize(){
        this.listBillsModel = new ListBillsModel();
        this.listBillsModel.initForDialog();

        // wypełnienie danymi pół w formularzu
        this.categoryComboBox.setItems(this.listBillsModel.getCategoryFxObservableList());
        this.categoryComboBox.getSelectionModel().select(this.listBillsModel.getSingleBills().getCategoryFx());
        this.invoiceTextField.setText(this.listBillsModel.getSingleBills().getInvoiceNumber());
        this.amountTextField.setText(String.valueOf(this.listBillsModel.getSingleBills().getAmount()));
        this.dateOfIssueTextField.setValue(this.listBillsModel.getSingleBills().getDateOfIssue());
        this.dueDateTextField.setValue(this.listBillsModel.getSingleBills().getDateDue());
        this.dateOfPaymentTextField.setValue(this.listBillsModel.getSingleBills().getDateOfPayment());
        this.wasPaidToggleButton.setSelected(this.listBillsModel.getSingleBills().getWasPaid());
        if (this.wasPaidToggleButton.isSelected()){
            this.wasPaidToggleButton.setText(bundle.getString("was.paid"));
        }else {
            this.wasPaidToggleButton.setText(bundle.getString("was.not.paid"));
        }

        //kopia single bills do bilsObjectPropertyEddit - kopiowanie starych danych przed  przed zapiseme
        this.listBillsModel.copyValueBilsFxToBilsFxObjectPropertyEdit();
        //bindowanie
        this.listBillsModel.getBillsFxObjectPropertyEdit().categoryFxProperty().bind(this.categoryComboBox.valueProperty());
        this.listBillsModel.getBillsFxObjectPropertyEdit().invoiceNumberProperty().bind(this.invoiceTextField.textProperty());

        this.amountTextField.textProperty().addListener((observable, oldValue, newValue) -> {

            if (!newValue.matches("[0-9]{10}(\\.[0-9]*)?")) {
                String valueToParse = newValue.replaceAll("[^\\d.]", "");


                try {
                    amountTextField.setText(valueToParse);
                    this.listBillsModel.getBillsFxObjectPropertyEdit().setAmount(Double.parseDouble(valueToParse));
                    //System.out.println(listBillsModel.getBillsFxObjectPropertyEdit().getAmount());
                } catch (Exception e) {
                    amountTextField.clear();

                    //System.out.println("bledna liczba");
                }
            }

        });


        this.listBillsModel.getBillsFxObjectPropertyEdit().dateOfIssueProperty().bind(this.dateOfIssueTextField.valueProperty());
        this.listBillsModel.getBillsFxObjectPropertyEdit().dateDueProperty().bind(this.dueDateTextField.valueProperty());
        this.listBillsModel.getBillsFxObjectPropertyEdit().dateOfPaymentProperty().bind(this.dateOfPaymentTextField.valueProperty());
        this.listBillsModel.getBillsFxObjectPropertyEdit().wasPaidProperty().bind(this.wasPaidToggleButton.selectedProperty());

        //włączenie przycisku wyczyść jesli jest data płatności
        this.clearDateOfPayButton.disableProperty().bind(this.dateOfPaymentTextField.valueProperty().isNull());




    }



    @FXML
    void onActionCancel(ActionEvent event) {


        closeStage(event);

    }

    @FXML
    void onActionSaveEditedBill(ActionEvent event) {
        System.out.println(this.listBillsModel.getBillsFxObjectPropertyEdit());
        this.listBillsModel.saveBilsEditInDataBase();

        closeStage(event);
    }

    @FXML
    void onActionWasPaidButton() {
        if (this.wasPaidToggleButton.isSelected()){
            this.wasPaidToggleButton.setText(bundle.getString("was.paid"));
        }else {
            this.wasPaidToggleButton.setText(bundle.getString("was.not.paid"));
            this.dateOfPaymentTextField.setValue(null);
        }


    }

    @FXML
    void onActionClearDateOfPayButton() {
        this.dateOfPaymentTextField.setValue(null);

    }

    private void closeStage(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

}
