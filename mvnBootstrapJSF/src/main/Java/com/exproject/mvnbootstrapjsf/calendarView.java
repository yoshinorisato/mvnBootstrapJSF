/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exproject.mvnbootstrapjsf;
import com.exproject.mvnbootstrapjsf.enumpkg.mailBodyClass;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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



//    @ManagedProperty("#{mtablelist}")
    public void onDateSelect(SelectEvent event){
        Mailtable mailobj = new Mailtable();
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        
        //facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
        mailTableModel model = new mailTableModel();
        try {
            //メールデータの取得
            Date tmpdate = DateFormat.getDateInstance().parse(format.format(event.getObject()));
            mtablelist = model.getMailTableData(tmpdate);
            
        } catch (ParseException ex) {
            Logger.getLogger(calendarView.class.getName()).log(Level.SEVERE, null, ex);            
        }
    }
    @PostConstruct
     public void init(){
        Mailtable mailobj = new Mailtable();
        Date today = new Date();
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        
        mailTableModel model = new mailTableModel();
        //メールデータの取得
        mtablelist = model.getMailTableData(today);
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
