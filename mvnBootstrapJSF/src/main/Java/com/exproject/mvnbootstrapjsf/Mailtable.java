/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exproject.mvnbootstrapjsf;

import com.exproject.mvnbootstrapjsf.enumpkg.statusEnum;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author PC-USER
 */
@Entity
public class Mailtable implements Serializable {
 
    @Id
    private Long serialID;
 
    private static final long serialVersionUID = 1L;

    @NotNull
    @Column(name = "idMail")
    @Getter
    @Setter
    private Integer idMail;     
    
    @Column(name = "RcvDateTime")
    @Temporal(TemporalType.TIMESTAMP)
    @Getter
    @Setter
    private Date RcvDateTime;
    
    @Column(name = "OperatorID")
    @Getter
    @Setter
    private String OperatorID;
    
    @Getter
    @Setter
    private Integer AprvStatus1;

    //ステータス文字列取得
    public String getAprvStatus1str() {
        String retstr = "";
        
        switch (AprvStatus1){
            case 1 :
                retstr = statusEnum.MISYOUNIN.getLabel();
                break;
            case 2:
               retstr = statusEnum.SAKUSEIKAKUNINNZUMI.getLabel();
                break;
            case 3:
               retstr = statusEnum.SYOUNINNZUMI.getLabel();
                break;
            case 4:
                retstr = statusEnum.SOUSINZUMI.getLabel();
                break;
            case 5:
                retstr = statusEnum.CANCEL.getLabel();
                break;
            case 99:
               retstr = statusEnum.ERROR.getLabel();
                break;
            default:
                retstr = "";
                break;
        }
        return retstr;
    }
    
    //作成確認者
    @Column(name = "AprvdBy1")
    @Getter
    @Setter
    private String aprvdBy1;    
    
    //作成確認日時
    @Column(name = "AprvDateTime1")
    @Temporal(TemporalType.TIMESTAMP)
    @Getter
    @Setter
    private Date aprvDateTime1;
    
    @Column(name = "aprvStatus2")
    @Getter
    @Setter
    private Integer aprvStatus2;
    
    //承認者
    @Column(name = "AprvdBy2")
    @Getter
    @Setter
    private String aprvdBy2;
    
    //承認日時
    @Column(name = "AprvDateTime2")
    @Temporal(TemporalType.TIMESTAMP)
    @Getter
    @Setter
    private Date aprvDateTime2;
    
    //送信先名
    @Column(name = "SentBy")
    @Getter
    @Setter
    private String sentBy;
    
    //送信日時
    @Column(name = "SentDateTime")
    @Temporal(TemporalType.TIMESTAMP)
    @Getter
    @Setter
    private Date sentDateTime;
    
    
    @Column(name = "SentUid")
    @Getter
    @Setter
    private String sentUid;
    
    //表題
    @Column(name = "Subject")
    @Getter
    @Setter
    private String subject;

    //送信カスタマ
    @Column(name = "CustomerSent")
    @Getter
    @Setter
    private String customerSent;
    
    //メール本文パス
    @Column(name = "MailBodyPath")
    @Getter
    @Setter
    private String mailBodyPath;
    

    //添付ファイルパス
    @Column(name = "AttachedFilePath")
    @Getter
    @Setter
    private String AttachedFilePath;
    //添付ファイルある

    //送信先
    @Column(name = "recipiantto")
    @Getter
    @Setter
    private String recipiantto;
    
    //送信CC
    @Column(name = "RecipiantCc")
    @Getter
    @Setter
    private String recipiantCc;
    
    //送信Bcc
    @Column(name = "RecipiantBcc")
    @Getter
    @Setter
    private String recipiantBcc;

    
    public Long getSerialID() {
        return serialID;
    }

    public void setSerialID(Long serialID) {
        this.serialID = serialID;
    }



}
