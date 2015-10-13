/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exproject.mvnmailpro;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.Getter;
import lombok.Setter;
import org.apache.tomcat.dbcp.dbcp.ConnectionFactory;
import org.apache.tomcat.dbcp.dbcp.DriverManagerConnectionFactory;
import org.apache.tomcat.dbcp.dbcp.PoolableConnectionFactory;
import org.apache.tomcat.dbcp.dbcp.PoolingDataSource;
import org.apache.tomcat.dbcp.pool.impl.GenericObjectPool;

/**
 *
 * @author PC-USER
 */
public class DBaccess {
    
    @Getter
    @Setter
    private String url_;
    @Getter
    @Setter
    private String user_;
    @Getter
    @Setter
    private String password_;
        
    
    private PoolingDataSource ds_;
    
    
    /** Creates a new instance of MyDBConnection */
    public DBaccess() {
        init();
    }

    //DBの接続
    public void init(){
    
       try{
           
            //ドライバーのロード
            Class.forName("com.mysql.jdbc.Driver");

             // ObjectPoolインスタンスを生成します
            GenericObjectPool pool = new GenericObjectPool();

             // Connectionオブジェクトを生成するためのConnectionFactory
             // インスタンスを生成します
             ConnectionFactory conFactory
              = new DriverManagerConnectionFactory(url_, user_, password_);           

             // PoolableConnectionFactoryインスタンスを生成します。
             new PoolableConnectionFactory(
              conFactory, pool, null, null, false, true);

            // プーリング機能を持つDataSourceインスタンスを生成します(4)
             ds_ = new PoolingDataSource(pool);

        }
        catch( Throwable  e){
            System.out.println("Failed to get connection");
            Logger.getLogger(calendarView.class.getName()).log(Level.SEVERE, null,"Failed to get connection" +  e);
        }
    }
    
    public Connection getMyConnection() throws SQLException {

            return ds_.getConnection();
    }

     public void close(java.sql.Statement stmt){
        
        if(stmt !=null){
            try{
               stmt.close();
            }
            catch(Exception e){}
        }
    }
}

