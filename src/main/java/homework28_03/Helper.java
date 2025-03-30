package homework28_03;

import java.util.List;
import java.util.Random;

public class Helper {

    public static String generateRandomString(int length) {
        return new Random().ints(length, 0, 62)
                .mapToObj("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"::charAt)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }

    public static boolean areAllNamesContainingSearch(List<String> names, String search) {
        if (names.isEmpty() || search == null) {
            return false;
        }
        return names.stream().allMatch(name -> name.contains(search.toLowerCase()));
    }
}
