package pl.tomaszlal.mybills.database.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "CATEGORY")
public class Category implements BaseModel{

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(columnName = "NAME",unique = true,canBeNull = false)
    private String name;

    @DatabaseField(columnName = "ACCOUNT_NUMBER", width = 26)
    private String accountNumber;

    @DatabaseField(columnName = "RECIPIENT")
    private String recipient; //odbiorca

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
}
