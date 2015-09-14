package com.exproject.mvnpopmail;

import java.io.IOException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
 
public class schedulePop implements Runnable {
    int number;
    long start;
    public schedulePop(int number, long start){
        this.number = number;
        this.start = start;
    }
    //スレッドの実行
    public void run() {
        System.out.print("Task" + number + " Start");
        System.out.println("(" + (System.currentTimeMillis() - start) + ")");
        
//メール取得オブジェクト
        getPop obj = new getPop();
        
        try {
            try {
                //POP処理
                obj.process();
            } catch (IOException ex) {
                Logger.getLogger(schedulePop.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.print("Task" + number + " End  ");
        System.out.println("(" + (System.currentTimeMillis() - start) + ")");
    }
     
    public static void main(String[] args){
        ScheduledExecutorService ex = Executors.newSingleThreadScheduledExecutor();
        long start = System.currentTimeMillis();
        for(int i=0; i<3; i++){
            System.out.print("Task"+i+" Send ");
            System.out.println("(" + (System.currentTimeMillis() - start) + ")");
            ex.schedule(new schedulePop(i,start),1000,TimeUnit.MILLISECONDS);
        }
    }
}