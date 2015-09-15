/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exproject.mvnpopmail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;
import static org.eclipse.persistence.expressions.ExpressionOperator.NotNull;

/**
 *
 * @author PC-USER
 */
public class insertMailtable {
    @Id
    private Long serialID;
 
    private static final long serialVersionUID = 1L;

    @Column(name = "idMail")
    @Getter
    @Setter
    private Integer idMail;     

    //メールID
    @Column(name = "RcvUID")
    @Getter
    @Setter
    private String RcvUID;
    
    @Column(name = "RcvDateTime")
    @Temporal(TemporalType.TIMESTAMP)
    @Getter
    @Setter
    private Date RcvDateTime;
    
    @Column(name = "OperatorID")
    @Getter
    @Setter
    private String OperatorID;
    
    @Setter
    private Integer AprvStatus1;
    
    //作成確認日時
    @Column(name = "AprvDateTime1")
    @Temporal(TemporalType.TIMESTAMP)
    @Getter
    @Setter
    private Date aprvDateTime1;
            
    //表題
    @Column(name = "Subject")
    @Getter
    @Setter
    private String subject;

    //添付ファイル
    @Getter
    @Setter
    private String AttachedFilePath;

    
    public Long getSerialID() {
        return serialID;
    }

    public void setSerialID(Long serialID) {
        this.serialID = serialID;
    }


    
    //メールデータを登録する
    public Integer insert(Connection conn) throws SQLException {
        PreparedStatement stmt ;        
        int num = 0;
        ResultSet idkeys = null;
        
        stmt = conn.prepareStatement("INSERT INTO mailtable ("
                + "RcvUID,"
                + "RcvDateTime,"
                + "OperatorID,"
                + "AprvStatus1,"
                + "Subject,"
                + "AttachedFilePath "
                + ") VALUES(?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        try {            
            //RCVUID
            stmt.setString(1, this.getRcvUID());

            //受信日時
            stmt.setTimestamp(2,new Timestamp(System.currentTimeMillis()));
            
            //送信元アドレス
            stmt.setString(3, this.getOperatorID());
            
            //未承認のため固定
            stmt.setInt(4, 1);
            
            //表題
            stmt.setString(5, this.getSubject());
            
            //添付ファイル名
            if(this.getAttachedFilePath() != null)
                stmt.setString(6, this.getAttachedFilePath());
            else
                stmt.setString(6, "");
            
            //SQLを実行
            stmt.execute();
                
            idkeys = stmt.getGeneratedKeys();
            

            // ResultSetと同じ感じに値を取得する。
            while(idkeys.next()){
                
                //idMailを取得する
                Integer id = idkeys.getInt(1);
                System.out.println("idMail = " + id + "メールを登録しました");
                this.setIdMail(id);
            }

        }   catch (SQLException ex) {
            Logger.getLogger(insertMailtable.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            
            if(idkeys != null) idkeys.close();
            if(stmt != null) stmt.close();
            

                
        }
        return num;
    }

}
