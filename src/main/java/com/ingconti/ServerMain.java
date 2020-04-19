package com.ingconti;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

// to test against console:
//  /usr/bin/nc 127.0.0.1 1234
// and type in console: server will receive.

public class ServerMain
{
    static int portNumber = 1234;
    static Boolean readLoop(BufferedReader in ){

        // waits for data and reads it in until connection dies
        // readLine() blocks until the server receives a new line from client
        String s = "";
        try {
            while ((s = in.readLine()) != null) {
                System.out.println(s);
            }

            return true;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }


    public static void main( String[] args )
    {
        System.out.println("Server started!");

        startMyTimer();

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Socket clientSocket = null;
        try {
            clientSocket = serverSocket.accept();
        } catch (IOException e) {

           // e.printStackTrace();
        }

            try {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
                readLoop(in);
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Server done!");

        }

    static void startMyTimer() {
        Timer timer = new Timer();

        TimeOutCheckerInterface timeOutChecker = (l) -> {
            System.out.println(l);
            Boolean timeoutReached = l>3;
            if (timeoutReached){
            }
        };

        TimerTask task = new TimeoutCounter(timeOutChecker);
        int intialDelay = 50;
        int delta = 1000;
        timer.schedule(task, intialDelay, delta);

    }


}



class TimeoutCounter extends TimerTask
{
    TimeOutCheckerInterface timeOutChecker;

    TimeoutCounter( TimeOutCheckerInterface timeOutChecker){
        this.timeOutChecker = timeOutChecker;
    }
    public static int i = 0;
    public void run()
    {
        timeOutChecker.check(++i);
    }
}