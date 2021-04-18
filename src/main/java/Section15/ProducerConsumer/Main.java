package Section15.ProducerConsumer;

import Section15.Intro.ThreadColor;

import java.util.Random;
import java.util.concurrent.*;

import static Section15.ProducerConsumer.Main.EOF;

public class Main {
    public static final String EOF = "EOF";

    public static void main(String[] args) {
//        List<String> buffer = new ArrayList<>();
        ArrayBlockingQueue<String> buffer = new ArrayBlockingQueue<String>(6);
//        ReentrantLock bufferLock = new ReentrantLock();

        ExecutorService executorService = Executors.newFixedThreadPool(3);

//        MyProducer producer = new MyProducer(buffer, ThreadColor.ANSI_GREEN, bufferLock);
//        MyConsumer consumer1 = new MyConsumer(buffer, ThreadColor.ANSI_PURPLE, bufferLock);
//        MyConsumer consumer2 = new MyConsumer(buffer, ThreadColor.ANSI_CYAN, bufferLock);

        MyProducer producer = new MyProducer(buffer, ThreadColor.ANSI_GREEN );
        MyConsumer consumer1 = new MyConsumer(buffer, ThreadColor.ANSI_PURPLE );
        MyConsumer consumer2 = new MyConsumer(buffer, ThreadColor.ANSI_CYAN );

        executorService.execute(producer);
        executorService.execute(consumer1);
        executorService.execute(consumer2);

        final Future<String> future = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println(ThreadColor.ANSI_RESET + "I'm being printed for Callable class");
                return "This is the callable result";
            }
        });

        try {
            System.out.println(future.get());
        } catch (ExecutionException e){
            System.out.println("Something went wrong");
        } catch (InterruptedException e){
            System.out.println("Thread running the task was interrupted");
        }

        executorService.shutdown();

//        new Thread(producer).start();
//        new Thread(consumer1).start();
//        new Thread(consumer2).start();
    }
}

class MyProducer implements Runnable {
//    private List<String> buffer;
    private ArrayBlockingQueue<String> buffer;
    private String color;
//    private ReentrantLock bufferLock;

    public MyProducer(ArrayBlockingQueue<String> buffer, String color) {
        this.buffer = buffer;
        this.color = color;
    }

    public void run(){
        Random random = new Random();
        String[] nums = {"1", "2", "3","4", "5"};

        for (String num : nums) {
            try{
                System.out.println(color + "Adding..." + num);
//                bufferLock.lock();
                buffer.put(num);
//                try {
//                    buffer.add(num);
//                } finally {
//                    bufferLock.unlock();
//                }
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e){
                System.out.println("Producer was interrupted");
            }
        }

        System.out.println(color + "Adding EOF exiting...");

//        bufferLock.lock();
        try {
            buffer.put("EOF");
        } catch (InterruptedException e ){

        }
//        } finally {
//            bufferLock.unlock();
//        }
    }
}

class MyConsumer implements Runnable {
    private ArrayBlockingQueue<String> buffer;
    private String color;
//    private ReentrantLock bufferLock;

    public MyConsumer(ArrayBlockingQueue<String> buffer, String color) {
        this.buffer = buffer;
        this.color = color;
    }

    @Override
    public void run() {

        while (true){
//            bufferLock.lock();
//            if (bufferLock.tryLock()){
            synchronized (buffer){
                try {
                    if (buffer.isEmpty()) {
                        continue;
                    }
//                    if(buffer.get(0).equals(EOF)){
                    if (buffer.peek().equals(EOF)) {
                        System.out.println(color + "Exiting");
                        break;
                    } else {
                        System.out.println(color + "Removed " + buffer.take());
                    }
//                } finally {
//                    bufferLock.unlock();
//                }
                }catch (InterruptedException e){

                }
            }
        }
    }
}

