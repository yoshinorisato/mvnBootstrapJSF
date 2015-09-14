/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exproject.mvnpopmail;

import com.exproject.util.DBaccess;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import javax.mail.Address;
import javax.mail.AuthenticationFailedException;
import javax.mail.Authenticator;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Message.RecipientType;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeUtility;
import javax.mail.internet.MimeMessage;
        
/**
 * シンプルなメール受信サンプル。
 */
public class getPop {

    public static void main(final String[] args) throws IOException {

        System.out.println("メール受信: 開始");
        new getPop().process();
        System.out.println("メール受信: 終了");
    }

    //POP3でメールを取得する
    public void process() throws IOException, SQLException {
        final Properties props = new Properties();

        // 基本情報。ここでは gmailへの接続例を示します。
        props.setProperty("mail.pop3.host", "192.168.12.178");
        props.setProperty("mail.pop3.port", "110");

        // タイムアウト設定
        props.setProperty("mail.pop3.connectiontimeout", "60000");
        props.setProperty("mail.pop3.timeout", "60000");

        // SSL関連設定
        //props.setProperty("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        //props.setProperty("mail.pop3.socketFactory.fallback", "false");
        //props.setProperty("mail.pop3.socketFactory.port", "995");

        final Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("test1", "test1");
            }
        });

        // デバッグを行います。標準出力にトレースが出ます。
        session.setDebug(true);

        Store store = null;
        try {
            try {
                store = session.getStore("pop3");
            } catch (NoSuchProviderException e) {
                System.out.println("メール受信: 指定プロバイダ[pop3]の取得に失敗しました。"
                        + e.toString());
                return;
            }

            try {
                store.connect();
            } catch (AuthenticationFailedException e) {
                System.out.println("メール受信: サーバ接続時に認証に失敗しました。" + e.toString());
                return;
            } catch (MessagingException e) {
                System.out.println("メール受信: サーバ接続に失敗しました。" + e.toString());
                return;
            }

            Folder folder = null;
            try {
                try {
                    // INBOXは予約語です。
                    folder = store.getFolder("INBOX");
                } catch (MessagingException e) {
                    System.out.println("メール受信: INBOXフォルダ取得に失敗しました。"
                            + e.toString());
                    return;
                }
                try {
                    // 読み書きモードでオープン
                    folder.open(Folder.READ_ONLY);
                } catch (MessagingException e) {
                    System.out
                            .println("メール受信: フォルダオープンに失敗しました。" + e.toString());
                    return;
                }

                // メッセージ一覧を取得
                try {
                    //メールファイルクラス
                    mailfileclass fileclass = new mailfileclass();
                    
                    DBaccess DBObj = new DBaccess();
                    
                    Connection conn = DBObj.getMyConnection();
                    
                    final Message messages[] = folder.getMessages();

                    //取得したメール件数分ループを行う
                    for (int index = 0; index < messages.length; index++) {
                        
                        final Message message = messages[index];

                        //メールDBにデータをInsertする
                        insertMailtable mailtable = new insertMailtable();
                        
                        // このAPI利用範囲であれば TOPコマンド止まりで、RETRコマンドは送出されない。
                        System.out.println("  Date: "
                                + message.getSentDate().toString());
                        
                        //表題の取得
                        mailtable.setSubject(message.getSubject());

                        //送信元アドレスを設定                        
                        // TODO 0番目の配列アクセスをおこなっている点に注意。
                        final InternetAddress addrFrom = (InternetAddress) message.getFrom()[0];
                        mailtable.setOperatorID(addrFrom.getAddress());
                        
                        try {
                            final Object objContent = message.getContent();
                            
                            
                            //添付ファイルが存在したとき
                            if (objContent instanceof Multipart) {
                                final Multipart multiPart = (Multipart) objContent;
                                for (int indexPart = 0; indexPart < multiPart.getCount(); indexPart++) {
                                    final Part part = multiPart.getBodyPart(indexPart);
                                    final String disposition = part.getDisposition();
                                    if (Part.ATTACHMENT.equals(disposition)
                                            || Part.INLINE.equals(disposition)) {
                                        
                                        //添付ファイル名を登録
                                        System.out.println("添付ファイル: ファイル名["
                                                + MimeUtility.decodeText(part
                                                        .getFileName()) + "]");
                                        mailtable.setAttachedFilePath(MimeUtility.decodeText(part
                                                        .getFileName()));
                                        // 本当はここでストリーム読み込み処理を行う。
                                        // part.getInputStream();
                                    } else {
                                        System.out.println("メール本文(添付ファイル付) ["
                                                + part.getContent().toString()
                                                + "]");
                                    }
                                }
                            } else {
                                System.out.println("メール本文[" + objContent.toString() + "]");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        //メールテーブルに登録
                        mailtable.insert(conn);
                        
                        //メール本文をストリームより取得
                        InputStream is =message.getInputStream();

                        //ファイルを設定する
                        fileclass.setFile(is);

                        //DB登録
                        fileclass.insert(conn );
                        
                        
                        // 読み込んだメールについては削除マークします。
                        // ※これで読み込み済みメールが削除されます。
                        message.setFlag(Flags.Flag.DELETED, true);
                    }
                                        
                } catch (MessagingException e) {
                    System.out.println("メール受信: メッセージ取得に失敗しました。" + e.toString());
                    return;
                }
            } finally {
                if (folder != null) {
                    try {
                        // 削除マークされたメールを実際に削除
                        folder.close(true);
                    } catch (MessagingException e) {
                        System.out.println("メール受信: フォルダクローズに失敗しました。"
                                + e.toString());
                    }
                }
            }
        } finally {
            if (store != null) {
                try {
                    store.close();
                } catch (MessagingException e) {
                    System.out.println("メール受信: サーバ切断に失敗しました。" + e.toString());
                }
            }
        }
    }
}