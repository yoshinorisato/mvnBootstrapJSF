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
import java.text.SimpleDateFormat;
import java.util.Date;
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
    public Integer insert(Connection conn) throws SQLException{
        
        PreparedStatement stmt;
        
            
        stmt = conn.prepareStatement("INSERT INTO ("
            + "RcvDateTime,"
            + "OperatorID,"
            + "AprvStatus1,"
            + "Subject,"
            + "AttachedFilePath "
        + ") VALUES(?,?,?,?,?)");
        
        //受信日時

        stmt.setDate(1, new java.sql.Date(System.currentTimeMillis()));
        
        //送信元アドレス
        stmt.setString(2, this.getOperatorID());
        //未承認のため固定
        stmt.setInt(3, 1);
        //表題
        stmt.setString(4, this.getSubject());
        
        //添付ファイル名
        if(this.getAttachedFilePath() != null)
            stmt.setString(5, this.getAttachedFilePath());
        else
            stmt.setString(5, "");

        //SQLを実行
        int num = stmt.executeUpdate();

        // インデックス指定でアクセスする必要がある。
        try ( 

                ResultSet keys = stmt.getGeneratedKeys() ) {

            // ResultSetと同じ感じに値を取得する。
            keys.next();
            Integer id = keys.getInt(1);
            System.out.println("idMail = " + id);  
            this.setIdMail(id);
            stmt.close();  
            keys.close();        

        }
        return num;
    }

}
