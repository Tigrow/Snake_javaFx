import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        int y = 10;
        List<Integer> list = new ArrayList<>();
        System.out.println("Hello new branch :D");
        System.out.println(y);
        for(int i = 0;i<=10;i++){
            System.out.println("New int = "+Double.toString(getResult((double)list.size())));
            list.add(i);
        }
        System.out.println("int = " +Double.toString(getResult((double)list.size())));
    }
    private static double getResult(double c){
        return Math.sqrt(c*25+c/25);
    }
}
