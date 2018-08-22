import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException {
        int y = 10;
        List<Integer> list = new ArrayList<>();
        System.out.println("Hello new branch :D");
        System.out.println(y);
        for(int i = 0;i<=10;i++){
            System.out.println("New int = "+Double.toString(getResult((double)list.size())));
            list.add(i);
        }
        Testing testing = new Testing();
        System.out.println("int = " +Double.toString(getResult((double)list.size())));
        for(Method m: Class.forName("Testing").getMethods()){
            if(m.isAnnotationPresent(AnnotationTest.class)){
                try{
                    System.out.printf(m.invoke(testing).toString());
                }catch (Exception ex){
                    System.out.printf("Test %s failed %s",m,ex.getCause());
                }
            }
        }
    }
    private static double getResult(double c){
        return Math.sqrt(c*25+c/25);
    }

}
