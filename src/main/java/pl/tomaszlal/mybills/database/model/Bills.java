package pl.tomaszlal.mybills.database.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "BILLS")
public class Bills implements BaseModel{

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(columnName = "CATEGORY_ID",foreign = true,foreignAutoRefresh = true,foreignAutoCreate = true,canBeNull = false)
    private Category category;

    @DatabaseField(columnName = "INVOICE_NUMBER",canBeNull = false)
    private String invoiceNumber;   //nr faktury i lub tytuł zapłaty

    @DatabaseField(columnName = "AMOUNT",canBeNull = false)
    private double amount;          //kwota

    @DatabaseField(columnName = "DATE_OF_ISSUE")
    private Date dateOfIssue;       // data wystawienia

    @DatabaseField(columnName = "DUE_DATE", canBeNull = false)
    private Date dueDate;           // termin płatności

    @DatabaseField(columnName = "DATE_OF_PAYMENT")
    private Date dateOfPayment;     // data zaplaty z afakture

    @DatabaseField(columnName = "WAS_PAID",defaultValue = "false")
    private boolean wasPaid;        //czy został opłacony domyślnnie false

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDateOfIssue() {
        return dateOfIssue;
    }

    public void setDateOfIssue(Date dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getDateOfPayment() {
        return dateOfPayment;
    }

    public void setDateOfPayment(Date dateOfPayment) {
        this.dateOfPayment = dateOfPayment;
    }

    public boolean getWasPaid() {
        return wasPaid;
    }

    public void setWasPaid(boolean wasPaid) {
        this.wasPaid = wasPaid;
    }


}
