import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        int x = 10;
        List<Integer> list = new ArrayList<>();
        System.out.println("Hello new branch :D");
        System.out.println(x);
        for(int i = 0;i<=10;i++){
            System.out.println("New int"+Integer.toString(i));
            list.add(i);
        }
    }
}
