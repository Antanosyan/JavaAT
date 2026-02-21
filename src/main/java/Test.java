import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class Test {
    public static void main(String[] args) {

        try {
            System.out.println("Try started");
            System.exit(111);
        } catch (Exception e) {
            System.out.println("Catch");
        } finally {
            System.out.println("Finally");
        }

    }
}
