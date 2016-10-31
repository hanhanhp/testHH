import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by hh on 2016/10/31.
 */
public class ReadWriteLockTest {
    public static void main(String[] args){
        final Data1 data = new Data1();
        for (int i = 0; i < 3; i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                   for (int j = 0;j < 5; j++){
                       data.set(new Random().nextInt(30));
                   }
                }
            }).start();
        }
        for (int i = 0;i < 3; i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0 ;j<5 ;j++){
                        data.get();
                    }
                }
            }).start();
        }
    }
}

class Data{
    private int data;
    public synchronized void set(int data){
        System.out.println(Thread.currentThread().getName() + "准备写入数据");
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.data = data;
        System.out.println(Thread.currentThread().getName() + "写入" + data);
    }
    public synchronized void get(){
        System.out.println(Thread.currentThread().getName() + "准备读取数据");
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "读取" + data);
    }
}

class Data1{
    private int data;
    private ReadWriteLock rwl = new ReentrantReadWriteLock();
    public void set(int data){
        rwl.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "准备写入数据");
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.data = data;
            System.out.println(Thread.currentThread().getName() + "写入" + this.data);
        } finally {
            rwl.writeLock().unlock();
        }
    }
    public void get(){
        rwl.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "准备读取数据");
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "读取" + this.data);
        } finally {
            rwl.readLock().unlock();
        }
    }
}
