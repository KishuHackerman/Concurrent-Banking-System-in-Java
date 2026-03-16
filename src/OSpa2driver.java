/**
 *
 * @author Kerly Titus
 * Assignment 2
 * Written by: Ayush Patel (40285846) and Krishna Patel (40200870)
 */

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kerly Titus
 */
public class OSpa2driver {

    public static final int RUN_MODE = 3;
    // 1 = unsynchronized
    // 2 = synchronized
    // 3 = semaphores

    public static void main(String[] args) {

        Network objNetwork = new Network();              /* Activate the network */
        Server objServer1 = new Server("Thread1");      /* Start server thread 1 */
        Server objServer2 = new Server("Thread2");      /* Start server thread 2 */
        Client objClient1 = new Client("sending");      /* Start sending client thread */
        Client objClient2 = new Client("receiving");    /* Start receiving client thread */

        objNetwork.start();
        objServer1.start();
        objServer2.start();
        objClient1.start();
        objClient2.start();

        try {
            objClient1.join();
            objClient2.join();
            objServer1.join();
            objServer2.join();
            objNetwork.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\n Main thread terminated.");
    }
}
