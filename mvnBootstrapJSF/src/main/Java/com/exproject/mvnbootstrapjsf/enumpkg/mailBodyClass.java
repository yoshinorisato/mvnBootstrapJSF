/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exproject.mvnbootstrapjsf.enumpkg;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.internet.*;
import javax.mail.*;
/**
 *
 * @author PC-USER
 */
public class mailBodyClass {
    
    //タイトル
    private String subject ;

    //本文
    private String context ;

    
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
    
  public void main(String[] args) throws IOException, javax.mail.MessagingException, MessagingException  {
        try {
          String context = "";
          InputStream in = new FileInputStream(new File("C:\\\\tmp\\dummy.txt"));
          Session session = Session.getDefaultInstance( new java.util.Properties(), null);
          
          MimeMessage mes = new MimeMessage(session, in);
          
          String ss;
            print(mes);
        } catch (javax.mail.MessagingException | FileNotFoundException e) {
            e.printStackTrace();
        }
  }
  //メールの読込み
  // @parameter ファイル名
  public void mailBodyRead(String args) throws IOException, javax.mail.MessagingException, MessagingException  {
      try {
          InputStream in = new FileInputStream(new File("C:\\\\tmp\\dummy.txt"));
          Session session = Session.getDefaultInstance( new java.util.Properties(), null);

          MimeMessage mes = new MimeMessage(session, in);
         
          String Error_message;
          Error_message = print(mes);
          if(Error_message != "")
                    throw new IOException
                ("メールの読み込みに失敗しました。");

        } catch (javax.mail.MessagingException | FileNotFoundException e) {
            e.printStackTrace();
        }

  }
  // メッセージを出力に出力
  private String print(MimeMessage mes) throws IOException, MessagingException {

    System.out.println("---- 全ヘッダをリストします ----");
    Enumeration<javax.mail.Header> allHeaders;
    try {
          allHeaders = mes.getAllHeaders();
        while (allHeaders.hasMoreElements()) {
            javax.mail.Header mailheader = allHeaders.nextElement();
            System.out.println(mailheader.getName() + " : " + mailheader.getValue());
        }
        System.out.println("---- 全ヘッダ 以上 ----");
        System.out.println("---- ヘッダ名指定で取ってみる(Received) ----");
        String[] headers = mes.getHeader("Received");
        for (String headerline : headers) {
            System.out.println(headerline);
        }
        System.out.println("---- ヘッダ名指定で取ってみる(Return-Path) ----");
        headers = mes.getHeader("Return-Path");
        for (String headerline : headers) {
            System.out.println(headerline);
        }
        
        
        System.out.println("---- 専用のメソッドで取ってみる(To) ----");
        Address[] allRecipients = mes.getAllRecipients();
        for (Address address : allRecipients) {
            System.out.println(address);
        }
 
        //添付ファイル
        String filename = mes.getFileName();
        if (filename != null)
            //添付ファイルがあった場合
            System.out.println("FILENAME: " + filename);
        
        
        //表題の取得(デコードを行う)
        setSubject(mes.getSubject());
        
        //本文の取得
        String body = (String)mes.getContent();
        setContext(body);
        
    } catch (Exception  ex) {
        Logger.getLogger(mailBodyClass.class.getName()).log(Level.SEVERE, null, ex);
    }
    return "";
  }
}