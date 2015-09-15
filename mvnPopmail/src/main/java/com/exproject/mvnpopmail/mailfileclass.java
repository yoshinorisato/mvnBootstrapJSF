/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exproject.mvnpopmail;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.persistence.Column;
import lombok.Getter;
import lombok.Setter;


/**
 *
 * @author PC-USER
 */
public class mailfileclass {

    //メールID
    @Column(name = "AprvdBy1")
    @Getter
    @Setter
    private Integer idMail; 
    
    //メールファイル
    @Column(name = "file")
    @Getter
    @Setter
    private InputStream file; 
    
    //コンストラクタ
    public void mailfileclass(){
        
    }
    
    //メールファイルをメールファイルテーブルに登録する
    public void insert( Connection con )
    {        
        try {
             
            PreparedStatement stmt;
            
            // ファイル内容の取得
            InputStream fin = this.getFile();

            stmt = con.prepareStatement("INSERT INTO mailfile (idMail,file) VALUES(?,?)");

            stmt.setInt(1, this.getIdMail());
            stmt.setBinaryStream(2,fin);

            int num = stmt.executeUpdate();
            stmt.close();
         }

         catch(Exception e) {
           e.printStackTrace();
         }
        
    }
    
    
}
