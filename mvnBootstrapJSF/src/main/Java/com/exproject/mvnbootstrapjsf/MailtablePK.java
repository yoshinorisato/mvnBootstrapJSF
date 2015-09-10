/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exproject.mvnbootstrapjsf;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author PC-USER
 */
@Embeddable
public class MailtablePK implements Serializable {
    @Basic(optional = false)
    @Column(name = "idMail")
    private int idMail;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "RcvUID")
    private String rcvUID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "AprvdBy1")
    private String aprvdBy1;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "AprvdBy2")
    private String aprvdBy2;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "CustomerSent")
    private String customerSent;

    public MailtablePK() {
    }

    public MailtablePK(int idMail, String rcvUID, String aprvdBy1, String aprvdBy2, String customerSent) {
        this.idMail = idMail;
        this.rcvUID = rcvUID;
        this.aprvdBy1 = aprvdBy1;
        this.aprvdBy2 = aprvdBy2;
        this.customerSent = customerSent;
    }

    public int getIdMail() {
        return idMail;
    }

    public void setIdMail(int idMail) {
        this.idMail = idMail;
    }

    public String getRcvUID() {
        return rcvUID;
    }

    public void setRcvUID(String rcvUID) {
        this.rcvUID = rcvUID;
    }

    public String getAprvdBy1() {
        return aprvdBy1;
    }

    public void setAprvdBy1(String aprvdBy1) {
        this.aprvdBy1 = aprvdBy1;
    }

    public String getAprvdBy2() {
        return aprvdBy2;
    }

    public void setAprvdBy2(String aprvdBy2) {
        this.aprvdBy2 = aprvdBy2;
    }

    public String getCustomerSent() {
        return customerSent;
    }

    public void setCustomerSent(String customerSent) {
        this.customerSent = customerSent;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idMail;
        hash += (rcvUID != null ? rcvUID.hashCode() : 0);
        hash += (aprvdBy1 != null ? aprvdBy1.hashCode() : 0);
        hash += (aprvdBy2 != null ? aprvdBy2.hashCode() : 0);
        hash += (customerSent != null ? customerSent.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MailtablePK)) {
            return false;
        }
        MailtablePK other = (MailtablePK) object;
        if (this.idMail != other.idMail) {
            return false;
        }
        if ((this.rcvUID == null && other.rcvUID != null) || (this.rcvUID != null && !this.rcvUID.equals(other.rcvUID))) {
            return false;
        }
        if ((this.aprvdBy1 == null && other.aprvdBy1 != null) || (this.aprvdBy1 != null && !this.aprvdBy1.equals(other.aprvdBy1))) {
            return false;
        }
        if ((this.aprvdBy2 == null && other.aprvdBy2 != null) || (this.aprvdBy2 != null && !this.aprvdBy2.equals(other.aprvdBy2))) {
            return false;
        }
        if ((this.customerSent == null && other.customerSent != null) || (this.customerSent != null && !this.customerSent.equals(other.customerSent))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.exproject.mvnbootstrapjsf.MailtablePK[ idMail=" + idMail + ", rcvUID=" + rcvUID + ", aprvdBy1=" + aprvdBy1 + ", aprvdBy2=" + aprvdBy2 + ", customerSent=" + customerSent + " ]";
    }
    
}
