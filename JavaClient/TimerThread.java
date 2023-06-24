public class TimerThread extends Thread {

    int start = 10;
    int currTime;

    @Override
    public void run() {

        try {

            for (int i = start; i > 0; i--) {
                Thread.sleep(1000);
                currTime = i;

            }// for ends

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }// try-catch ends

    }// method run ends

    public int getTime() {
        return currTime;
    }// method getTime ends

}// class TimerThread ends
