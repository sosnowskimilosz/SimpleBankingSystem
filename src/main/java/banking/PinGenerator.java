package banking;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PinGenerator {

    public static String generatePin() {
        ArrayList<Integer> listWithPin = new ArrayList<>();
        Random random = new Random();
        while (listWithPin.size() < 4) {
            listWithPin.add(random.nextInt(10));
        }
        return convertArrayToString(listWithPin);
    }

    static String convertArrayToString(List<Integer> list) {
        return list.toString()
                .replace("[", "")
                .replace(",", "")
                .replace("]", "")
                .replace(" ", "");
    }
}
