package com.exproject.mvnbootstrapjsf;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-09-14T14:43:20")
@StaticMetamodel(Mailtable.class)
public class Mailtable_ { 

    public static volatile SingularAttribute<Mailtable, Integer> idMail;
    public static volatile SingularAttribute<Mailtable, String> mailBodyPath;
    public static volatile SingularAttribute<Mailtable, String> recipiantCc;
    public static volatile SingularAttribute<Mailtable, String> recipiantto;
    public static volatile SingularAttribute<Mailtable, String> subject;
    public static volatile SingularAttribute<Mailtable, String> recipiantBcc;
    public static volatile SingularAttribute<Mailtable, Date> RcvDateTime;
    public static volatile SingularAttribute<Mailtable, String> aprvdBy1;
    public static volatile SingularAttribute<Mailtable, Date> aprvDateTime2;
    public static volatile SingularAttribute<Mailtable, String> OperatorID;
    public static volatile SingularAttribute<Mailtable, String> aprvdBy2;
    public static volatile SingularAttribute<Mailtable, String> AttachedFilePath;
    public static volatile SingularAttribute<Mailtable, Date> aprvDateTime1;
    public static volatile SingularAttribute<Mailtable, String> sentBy;
    public static volatile SingularAttribute<Mailtable, String> sentUid;
    public static volatile SingularAttribute<Mailtable, Long> serialID;
    public static volatile SingularAttribute<Mailtable, Integer> AprvStatus1;
    public static volatile SingularAttribute<Mailtable, Date> sentDateTime;
    public static volatile SingularAttribute<Mailtable, Integer> aprvStatus2;
    public static volatile SingularAttribute<Mailtable, String> customerSent;

}