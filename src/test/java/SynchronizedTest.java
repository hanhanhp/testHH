import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by hh on 2016/10/31.
 */
public class SynchronizedTest {
    public static void main(String[] args){
        final Outputter outputter = new Outputter();
        new Thread(){
            public void run(){
                outputter.output("wangxuejie");
            }
        }.run();
        new Thread(){
            public void run(){
                outputter.output("heze");
            }
        }.run();
    }
}

class Outputter{
    private Lock lock = new ReentrantLock();
    public void output(String name){
        lock.lock();
        try {
            for(int i = 0; i<name.length() ; i++){
                System.out.println(name.charAt(i));
            }
        } finally {
            lock.unlock();
        }
    }
}
