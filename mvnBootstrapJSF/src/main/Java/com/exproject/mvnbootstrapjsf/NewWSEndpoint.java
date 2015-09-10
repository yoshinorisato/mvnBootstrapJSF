/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exproject.mvnbootstrapjsf;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.websocket.Session;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author PC-USER
 */
@ServerEndpoint("/endpoint")
public class NewWSEndpoint {

    private static Set<Session> sessionSet
        = Collections.synchronizedSet(new HashSet<Session>());
    
    static {
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleWithFixedDelay(NewWSEndpoint::broadcast, 5, 5, TimeUnit.SECONDS);
    }    
    
    @OnOpen
    public void open(Session session){ sessionSet.add(session); }

    @OnClose
    public void close(Session session){ sessionSet.remove(session); }
    
    public static void broadcast() {
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        sessionSet.forEach(session -> {
            session.getAsyncRemote().sendText("Broadcast : " + formatter.format(now));
        });
    }
    
}
