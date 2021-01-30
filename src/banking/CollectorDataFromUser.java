package banking;

import java.util.Scanner;

public class CollectorDataFromUser {

    public static String getTextFromUser() {
        Scanner in = new Scanner(System.in);
        return in.nextLine();
    }
}
