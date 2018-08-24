import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args){
        Listener listener = new Listener() {
            @Override
            public void doit() {
                System.out.println("From Listener in thread " + Thread.currentThread().getName());
            }
        };
        //System.out.println("from " + Thread.currentThread().getName());
        new Thread(listener::doit).start();

        System.out.println("Thread :" + Thread.currentThread().getName());
        listener.doit();
    }
    interface Listener{
        void doit();
    }
}
