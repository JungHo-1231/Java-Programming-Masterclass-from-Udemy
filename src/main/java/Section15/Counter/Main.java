package Section15.Counter;

import Section15.Intro.ThreadColor;

import static Section15.Intro.ThreadColor.ANSI_CYAN;
import static Section15.Intro.ThreadColor.ANSI_PURPLE;

public class Main {
    public static void main(String[] args) {
        Countdown countdown = new Countdown();

        CountDownThread t1 = new CountDownThread(countdown);

        t1.setName("Thread 1");

        CountDownThread t2 = new CountDownThread(countdown);
        t2.setName("Thread 2");

        t1.start();
        t2.start();
    }


}

class Countdown {
    private int i;

    public void doCountdown(){
        String color;

        switch (Thread.currentThread().getName()){
            case "Thread 1" :
                color = ANSI_CYAN;
                break;
            case "Thread 2":
                color = ANSI_PURPLE;
                break;
            default:
                color = ThreadColor.ANSI_GREEN;
        }
        synchronized (this){
            for (i = 10; i > 0; i--) {
                System.out.println(color + Thread.currentThread().getName()+ ": i = " + i);
            }
        }
    }
}

class CountDownThread extends Thread {
    private Countdown threadCountdown;

    public CountDownThread(Countdown threadCountdown) {
        this.threadCountdown = threadCountdown;
    }

    public void run() {
        threadCountdown.doCountdown();
    }
}
