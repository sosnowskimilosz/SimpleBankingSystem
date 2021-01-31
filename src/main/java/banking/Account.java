package banking;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Account {

    Integer id;
    String cardNumber;
    String pin;
    Integer balance;

    public Account(Integer id, String cardNumber, String pin, Integer balance) {
        this.id=id;
        this.cardNumber = cardNumber;
        this.pin=pin;
        this.balance=balance;
    }

    void showInitializedInfo(){
        System.out.println("\nYour card has been created");
        System.out.println("Your card number:");
        System.out.println(cardNumber);
        System.out.println("Your card PIN:");
        System.out.println(pin+"\n");
    }

    void showBalance(){
        System.out.println("\nBalance: "+balance);
    }

    void showMsgAboutLogOut(){
        System.out.println("\nYou have successfully logged out!\n");
    }

    public Integer getId() {
        return id;
    }
}
