package com.ingconti;


import java.util.TimerTask;

class TimeoutCounter extends TimerTask
{
    TimeOutCheckerInterface timeOutChecker;

    TimeoutCounter( TimeOutCheckerInterface timeOutChecker){
        this.timeOutChecker = timeOutChecker;
    }
    public static int i = 0;
    public void run()
    {
        Boolean stop = timeOutChecker.check(++i);
        if (stop) {
            System.out.println("Got stop inside TimerTask");
            this.cancel();
        }
    }
}