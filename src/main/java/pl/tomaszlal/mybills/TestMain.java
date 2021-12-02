package pl.tomaszlal.mybills;

import pl.tomaszlal.mybills.database.dbutils.Utils;

public class TestMain {
    public static void main(String[] args) {
        String accno = "26175013125020000000005886";
        System.out.println(accno.length());
        Utils.isCorrectAccountNumber(accno);
    }
}
