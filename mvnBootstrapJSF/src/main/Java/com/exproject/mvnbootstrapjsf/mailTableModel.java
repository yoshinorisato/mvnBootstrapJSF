/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exproject.mvnbootstrapjsf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author PC-USER
 */

public class mailTableModel {
    
    /** Creates a new instance of FoodTableModel */
    public mailTableModel() {
    }
     
    // @param date
    public List<Mailtable> getMailTableData(Date dt) {
    // 結果格納リスト
    List<Mailtable> mailList = new ArrayList<Mailtable>();

    try {
            ResultSet rs;

            //Nullの場合は終了
            if(dt == null ){
                return null ;
            }
                    
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            
           
            //DBアクセスクラスを作成
            DBaccess DBclass = new DBaccess();

            // データの取得
            try {
                Connection con = DBclass.getMyConnection(); // データの追加
                //Statement stmt1 = con.createStatement();
                //String sql1 = "insert into authentication (ID, PASSWORD) values ('jdbc', 'ABC')";
                //stmt1.execute(sql1);
                //stmt1.close();

                // データの取得
                String sql2 = "select "
                        + "idMail,"
                        + "RcvDateTime,"
                        + "OperatorID,"
                        + "AprvStatus1,"
                        + "AprvdBy1,"
                        + "AprvDateTime1,"
                        + "AprvStatus2,"
                        + "AprvdBy2,"
                        + "AprvDateTime2,"
                        + "SentBy,"
                        + "SentDateTime,"
                        + "SentUid,"
                        + "Subject,"
                        + "CustomerSent,"
                        + "MailBodyPath,"
                        + "AttachedFilePath,"
                        + "recipiantto,"
                        + "RecipiantCc,"
                        + "RecipiantBcc"
                        + " from mailtable where RcvDateTime >= ? and RcvDateTime <= ? order by RcvDateTime DESC;";
                //パラメータの設定
                try (PreparedStatement pstmt = con.prepareStatement(sql2)) {
                    //パラメータの設定
                    pstmt.setString(1, sdf.format(dt).concat(" 00:00:00") );
                    
                    pstmt.setString(2, sdf.format(dt).concat(" 23:59:59") );
                    
                    //SQL 実行
                    rs = pstmt.executeQuery();
                    
                    // 取得したデータをEntityに格納
                    while(rs.next()){
                        
                        Mailtable mtable = new Mailtable();
                        

                        //メールID
                        mtable.setIdMail( Integer.parseInt(rs.getString("idMail")) );
                        
                        mtable.setRcvDateTime(rs.getTimestamp("RcvDateTime"));
                        
                        mtable.setOperatorID(rs.getString("OperatorID"));
                        
                        //ステータス
                        mtable.setAprvStatus1(Integer.parseInt(rs.getString("AprvStatus1")));
                        
                        mtable.setAprvdBy1( rs.getString("AprvdBy1") );

                        mtable.setAprvDateTime1(rs.getTimestamp("AprvDateTime1"));
                        
                        //ステータス
                        mtable.setAprvStatus2(Integer.parseInt(rs.getString("AprvStatus2")));
                        
                        mtable.setAprvdBy2( rs.getString("AprvdBy2") );

                        mtable.setAprvDateTime2(rs.getTimestamp("AprvDateTime2"));                        
                        mtable.setSentBy(rs.getString("SentBy"));                        

                        mtable.setSentDateTime(rs.getTimestamp("SentDateTime"));                        

                        mtable.setSentUid(rs.getString("SentUid"));                        

                        mtable.setSubject(rs.getString("Subject"));
                        
                        mtable.setCustomerSent(rs.getString("CustomerSent"));

                        mtable.setMailBodyPath(rs.getString("MailBodyPath"));

                        mtable.setAttachedFilePath(rs.getString("AttachedFilePath"));
                        
                        mtable.setRecipiantto(rs.getString("recipiantto"));
                        
                        mtable.setRecipiantCc(rs.getString("recipiantCc"));
                        
                        mtable.setRecipiantBcc(rs.getString("RecipiantBcc"));

                        
                        mailList.add(mtable);
                        
                    }
                }
                rs.close();
                con.close();
                
            }catch(Exception e) {
            
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        return mailList;
    }    
}
