package pl.tomaszlal.mybills.database.dbutils;

import pl.tomaszlal.mybills.database.model.Category;
import pl.tomaszlal.mybills.modelFx.CategoryFx;

import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Utils {
    public static Date convertLocalDateToDate(LocalDate localDate){
        if(localDate!=null){
            return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }else {
            return null;
        }
    }

    public static LocalDate convertDateToLocalDate(Date date){
        if (date!=null){
            return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }else {
            return null;
        }


        //return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
        //return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }


    public static CategoryFx convertCategoryToCategoryFx(Category category){
        CategoryFx categoryFx = new CategoryFx();
        categoryFx.setId(category.getId());
        categoryFx.setName(category.getName());
        categoryFx.setAccountNumber(category.getAccountNumber());
        categoryFx.setRecipient(category.getRecipient());
        return categoryFx;
    }

    public static Category convertCategoryFxToCategory(CategoryFx categoryFx){
        Category category = new Category();
        category.setId(categoryFx.getId());
        category.setName(category.getName());
        category.setAccountNumber(categoryFx.getAccountNumber());
        category.setRecipient(categoryFx.getRecipient());
        return category;
    }

    public static boolean isCorrectAccountNumber(String accountNumber){
        if (accountNumber.length()==26){
            String controlSum = accountNumber.substring(0,2);
            accountNumber = accountNumber.substring(2);
//            System.out.println(accountNumber);
//            System.out.println(controlSum);
            String pl = "2521";
            accountNumber = accountNumber + pl + controlSum;
//            System.out.println(accountNumber);
            BigInteger accountNumberBigInt = new BigInteger(accountNumber);
            BigInteger ninetySeven = new BigInteger("97");
            //System.out.println("wynik reszty z dzelenia przez 97: "+accountNumberBigInt.remainder(ninetySeven));
            if ((accountNumberBigInt.remainder(ninetySeven)).equals(BigInteger.ONE)){
//                System.out.println("Nr konta prawidłowy");
                return true;
            }else {
//                System.out.println("nr konta błędny");
                return false;
            }
        }else{
            //System.out.println("niema 26 znakow");
            return false;
        }
    }
}
