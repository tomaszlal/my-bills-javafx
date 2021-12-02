package pl.tomaszlal.mybills.modelFx;

import javafx.beans.property.*;

import java.time.LocalDate;

public class BillsFx {

    private IntegerProperty id = new SimpleIntegerProperty();
    private ObjectProperty<CategoryFx> categoryFx = new SimpleObjectProperty<>();
    private StringProperty invoiceNumber = new SimpleStringProperty();
    private DoubleProperty amount = new SimpleDoubleProperty();
    private ObjectProperty<LocalDate> dateOfIssue = new SimpleObjectProperty<>(LocalDate.now());
    private ObjectProperty<LocalDate> dateDue = new SimpleObjectProperty<>(LocalDate.now());
    private ObjectProperty<LocalDate> dateOfPayment = new SimpleObjectProperty<>(LocalDate.now());
    private BooleanProperty wasPaid = new SimpleBooleanProperty();
    private StringProperty accountNumber = new SimpleStringProperty();
    private StringProperty recipient = new SimpleStringProperty();


    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public CategoryFx getCategoryFx() {
        return categoryFx.get();
    }

    public ObjectProperty<CategoryFx> categoryFxProperty() {
        return categoryFx;
    }

    public void setCategoryFx(CategoryFx categoryFx) {
        this.categoryFx.set(categoryFx);
    }

    public String getInvoiceNumber() {
        return invoiceNumber.get();
    }

    public StringProperty invoiceNumberProperty() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber.set(invoiceNumber);
    }

    public double getAmount() {
        return amount.get();
    }

    public DoubleProperty amountProperty() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount.set(amount);
    }

    public LocalDate getDateOfIssue() {
        return dateOfIssue.get();
    }

    public ObjectProperty<LocalDate> dateOfIssueProperty() {
        return dateOfIssue;
    }

    public void setDateOfIssue(LocalDate dateOfIssue) {
        this.dateOfIssue.set(dateOfIssue);
    }

    public LocalDate getDateDue() {
        return dateDue.get();
    }

    public ObjectProperty<LocalDate> dateDueProperty() {
        return dateDue;
    }

    public void setDateDue(LocalDate dateDue) {
        this.dateDue.set(dateDue);
    }

    public LocalDate getDateOfPayment() {
        return dateOfPayment.get();
    }

    public ObjectProperty<LocalDate> dateOfPaymentProperty() {
        return dateOfPayment;
    }

    public void setDateOfPayment(LocalDate dateOfPayment) {
        this.dateOfPayment.set(dateOfPayment);
    }

    public boolean getWasPaid() {
        return wasPaid.get();
    }

    public BooleanProperty wasPaidProperty() {
        return wasPaid;
    }

    public void setWasPaid(boolean wasPaid) {
        this.wasPaid.set(wasPaid);
    }

    public boolean isWasPaid() {
        return wasPaid.get();
    }

    public String getAccountNumber() {
        return accountNumber.get();
    }

    public StringProperty accountNumberProperty() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber.set(accountNumber);
    }

    public String getRecipient() {
        return recipient.get();
    }

    public StringProperty recipientProperty() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient.set(recipient);
    }

    @Override
    public String toString() {
        return "BillsFx{" +
                "id=" + id.get() +
                ", categoryFx=" + categoryFx.get() +
                ", invoiceNumber=" + invoiceNumber.get() +
                ", amount=" + amount.get() +
                ", dateOfIssue=" + dateOfIssue.get() +
                ", dateDue=" + dateDue.get() +
                ", dateOfPayment=" + dateOfPayment.get() +
                ", wasPaid=" + wasPaid.get() +
                '}';
    }
}
