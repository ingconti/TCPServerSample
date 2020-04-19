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
// it wil NOT block socket (for now..) when timeout.

public class ServerMain
{
    static final int portNumber = 1234;
    static final int maxRetries = 10;


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
            e.printStackTrace();
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

        // lambda we pass down (to shoe another way to be called back by another class)
        TimeOutCheckerInterface timeOutChecker = (l) -> {
            System.out.println(l);
            Boolean timeoutReached = l>maxRetries;
            if (timeoutReached){
                System.out.println("Got timeout");
                return true;
            }
            return false;
        };

        TimerTask task = new TimeoutCounter(timeOutChecker);
        int intialDelay = 50;
        int delta = 1000;
        timer.schedule(task, intialDelay, delta);

    }


}

