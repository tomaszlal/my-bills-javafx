package pl.tomaszlal.mybills.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pl.tomaszlal.mybills.modelFx.ListBillsModel;

import java.text.DecimalFormat;
import java.time.LocalDate;

public class MarkAsPaidDialogController {

    @FXML
    private TextField recipientTextField;

    @FXML
    private TextField numberAccountTextField;

    @FXML
    private TextField numberInvoiceTextField;

    @FXML
    private TextField amountTextField;

    @FXML
    private DatePicker dateOfPaymentDatePicker;

    @FXML
    private Button okButton;

    @FXML
    private Button cancelButton;

    ListBillsModel listBillsModel;

    @FXML
    private void initialize() {
        this.listBillsModel = new ListBillsModel();
        this.listBillsModel.initForDialog();
        numberInvoiceTextField.setText(this.listBillsModel.getSingleBills().getInvoiceNumber());
        numberAccountTextField.setText(this.listBillsModel.getSingleBills().getAccountNumber());

        //wyświetlenie 2 miejsc po przecinku
        String amount = (new DecimalFormat("#0.00")).format(this.listBillsModel.getSingleBills().getAmount());

        amountTextField.setText(amount);
        recipientTextField.setText(this.listBillsModel.getSingleBills().getRecipient());


        // kopia single bills do BilsFxObjectPropertyEdit
        this.listBillsModel.copyValueBilsFxToBilsFxObjectPropertyEdit();
        //bind date of payment
        this.listBillsModel.billsFxObjectPropertyEditProperty().get().dateOfPaymentProperty().bind(this.dateOfPaymentDatePicker.valueProperty());

        //włączenie przycisku OK do zapisu zapłaconej faktury
        this.okButton.disableProperty().bind(this.dateOfPaymentDatePicker.valueProperty().isNull());
;

    }

    @FXML
    void cancelButtonAction(ActionEvent event) {
        closeStage(event);

    }

    @FXML
    void okButtonAction(ActionEvent event) {
        this.listBillsModel.getBillsFxObjectPropertyEdit().setWasPaid(true);
        this.listBillsModel.saveBilsEditInDataBase();
        //this.listBillsModel.getBillsFxObjectPropertyEdit().setDateOfPayment(this.dateOfPaymentDatePicker.getValue()); // zbedne działa bind

        closeStage(event);

    }


    private void closeStage(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

}
