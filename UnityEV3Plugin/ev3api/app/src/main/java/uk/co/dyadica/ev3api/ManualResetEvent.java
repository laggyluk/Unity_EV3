package uk.co.dyadica.ev3api;

/**
 * Created by dyadica.co.uk on 04/02/2016.
 * <p>
 * THIS CODE AND INFORMATION ARE PROVIDED "AS IS" WITHOUT WARRANTY OF ANY
 * KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND/OR FITNESS FOR A
 * PARTICULAR PURPOSE.
 * <p/>
 */

class ManualResetEvent {

    private final Object monitor = new Object();
    private volatile boolean open = false;

    public ManualResetEvent(boolean open) {
        this.open = open;
    }

    public void waitOne() throws InterruptedException {
        synchronized (monitor) {
            while (open==false) {
                monitor.wait();
            }
        }
    }

    public boolean waitOne(long milliseconds) throws InterruptedException {
        synchronized (monitor) {
            if (open)
                return true;
            monitor.wait(milliseconds);
            return open;
        }
    }

    public void set() {//open start
        synchronized (monitor) {
            open = true;
            monitor.notifyAll();
        }
    }

    public void reset() {//close stop
        open = false;
    }
}
