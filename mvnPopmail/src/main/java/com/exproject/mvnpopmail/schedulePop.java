package com.exproject.mvnpopmail;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
 
public class schedulePop  {
    //スレッドを開始する
    /**
     * @param args
     */
    public static void main(String[] args) {
 
 
        final ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
        ses.scheduleWithFixedDelay(new Runnable() {
            
            @Override
            public void run() {
                // ここに繰り返し処理を書く
                System.out.println("開始");
                getPop obj = new getPop();
                try {
                    obj.process();
                } catch (IOException ex) {
                    Logger.getLogger(schedulePop.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(schedulePop.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                System.out.println(new Date());
            }
        }, 0, 2, TimeUnit.SECONDS);
  
        // 10秒待つ
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
 
        ses.shutdown();
    }
}
