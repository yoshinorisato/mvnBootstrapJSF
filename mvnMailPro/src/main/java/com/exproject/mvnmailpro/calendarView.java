/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exproject.mvnmailpro;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author PC-USER
 */
@ManagedBean
@ViewScoped
public class calendarView implements Serializable{
            
    private boolean AttachedFile;

    @Getter
    @Setter
    private Date date1;
    
    //メール一覧
    @Getter
    @Setter
    private List<Mailtable> mtablelist;
    
    //メールクラス
    @Getter
    @Setter
    private mailBodyClass mbcls;
    
    @Getter
    @Setter
    private Connection conn;
    

//    @ManagedProperty("#{mtablelist}")
    public void onDateSelect(SelectEvent event){
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        
        //facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
        mailTableModel model = new mailTableModel();
        try {
            //メールデータの取得
            Date tmpdate = DateFormat.getDateInstance().parse(format.format(event.getObject()));
            
            mtablelist = model.getMailTableData(conn,tmpdate);
            
        } catch (ParseException | SQLException ex) {
            Logger.getLogger(calendarView.class.getName()).log(Level.SEVERE, null, ex);            
        }
    }
    @PostConstruct
     public void init(){

         //プロパティファイルの読み込み
        Properties conf = new Properties();
        
        try {
            conf.load(new FileInputStream("mvnPopMail.properties"));
        } catch (IOException e) {
            Logger.getLogger(calendarView.class.getName()).log(Level.SEVERE, null,
                    "プロパティファイル(mvnPopMail.properties)のオープンに失敗しました。" + e.getMessage());
            System.exit(-1);  // プログラム終了
        }
        
        //DBコネクションをオープン
        DBaccess Dba = new DBaccess();
        
        // プロパティデータの読込
        Dba.setUrl_( conf.getProperty("mail-server").toString());
        Dba.setUser_(conf.getProperty("mail-account").toString());
        Dba.setPassword_(conf.getProperty("mail-password").toString());
        
        try {
            //DBコネクションの取得
            conn = Dba.getMyConnection();
        
        } catch (SQLException ex) {
            Logger.getLogger(calendarView.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //メールオブジェクト
        Mailtable mailobj = new Mailtable();
                
        FacesContext facesContext = FacesContext.getCurrentInstance();
        
        mailTableModel model = new mailTableModel();
        
        try {
            //メールデータの取得
            Date today = new Date();            
            mtablelist = model.getMailTableData(conn,today);
            
        } catch (SQLException ex) {
            Logger.getLogger(calendarView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }   
    
     //添付ファイル有無判定
    public String isAttach(String attachepath) {
        return "".equals(attachepath) ? "無":"有";
    }
    
    //メールを選択したとき
    public void onRowSelect (SelectEvent event){
        
        //メールIDの取得
        Integer id = ((Mailtable) event.getObject()).getIdMail();
        
        mbcls = new mailBodyClass();
        try {
            // メール本体部分の取得
            mbcls.mailBodyRead("a");
        } catch (IOException | MessagingException ex) {
            Logger.getLogger(calendarView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
