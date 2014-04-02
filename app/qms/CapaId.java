/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.qms;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Niteshwar
 */
public class CapaId implements Serializable {

    private Integer capaid_id;
    private String capa_number;
    private String rca;
    private Date rca_t_date;
    private Date rca_a_date;
    private String nc;
    private String ncyesno;
    private String actionplan;
    private String actionplan_approve;
    private String actionimp;
    private Date actionimp_t_date;
    private Date actionimp_a_date;
    private String effectiveplan;
    private String verify;
    private Date verify_t_date;
    private Date verify_a_date;
    private String verify_approve;
    private String comments;
    private Date admin_rec_date;
    private Date admin_own_date;
    private String admin_rec_person;
    private String admin_own_person;
    private String admin_disposition;
    private String owner;
    private String owner2;
    private String owner3;
    private String ncminormajor;
    private String actionplan_approve2;
    private String actionimp_status;
    private String verify_approve2;
    private Date admin_disp_date;
    private String admin_disp_person;
    private String capaid_description;

    public CapaId() {
    }

    public CapaId(Integer capaid_id, String capa_number, String rca, Date rca_t_date, Date rca_a_date, String nc, String ncyesno, String actionplan, String actionplan_approve, String actionimp, Date actionimp_t_date, Date actionimp_a_date, String effectiveplan, String verify, Date verify_t_date, Date verify_a_date, String verify_approve, String comments, Date admin_rec_date, Date admin_own_date, String admin_rec_person, String admin_own_person, String admin_disposition, String owner,String owner2, String owner3, String ncminormajor, String actionplan_approve2, String actionimp_status, String verify_approve2, Date admin_disp_date, String admin_disp_person,String capaid_description) {
        this.capaid_id = capaid_id;
        this.capa_number = capa_number;
        this.rca = rca;
        this.rca_t_date = rca_t_date;
        this.rca_a_date = rca_a_date;
        this.nc = nc;
        this.ncyesno = ncyesno;
        this.actionplan = actionplan;
        this.actionplan_approve = actionplan_approve;
        this.actionimp = actionimp;
        this.actionimp_t_date = actionimp_t_date;
        this.actionimp_a_date = actionimp_a_date;
        this.effectiveplan = effectiveplan;
        this.verify = verify;
        this.verify_t_date = verify_t_date;
        this.verify_a_date = verify_a_date;
        this.verify_approve = verify_approve;
        this.comments = comments;
        this.admin_rec_date = admin_rec_date;
        this.admin_own_date = admin_own_date;
        this.admin_rec_person = admin_rec_person;
        this.admin_own_person = admin_own_person;
        this.admin_disposition = admin_disposition;
        this.owner = owner;
         this.owner2 = owner2;
        this.owner3 = owner3;
        this.ncminormajor = ncminormajor;
        this.actionplan_approve2 = actionplan_approve2;
        this.actionimp_status = actionimp_status;
        this.verify_approve2 = verify_approve2;
        this.admin_disp_date = admin_disp_date;
        this.admin_disp_person = admin_disp_person;
         this.capaid_description = capaid_description;
    }



    public Integer getCapaid_id() {
        return capaid_id;
    }

    public void setCapaid_id(Integer capaid_id) {
        this.capaid_id = capaid_id;
    }

    public String getActionimp() {
        return actionimp;
    }

    public void setActionimp(String actionimp) {
        this.actionimp = actionimp;
    }

    public Date getActionimp_a_date() {
        return actionimp_a_date;
    }

    public void setActionimp_a_date(Date actionimp_a_date) {
        this.actionimp_a_date = actionimp_a_date;
    }

    public Date getActionimp_t_date() {
        return actionimp_t_date;
    }

    public void setActionimp_t_date(Date actionimp_t_date) {
        this.actionimp_t_date = actionimp_t_date;
    }

    public String getActionplan() {
        return actionplan;
    }

    public void setActionplan(String actionplan) {
        this.actionplan = actionplan;
    }

    public String getActionplan_approve() {
        return actionplan_approve;
    }

    public void setActionplan_approve(String actionplan_approve) {
        this.actionplan_approve = actionplan_approve;
    }

    public String getAdmin_disposition() {
        return admin_disposition;
    }

    public void setAdmin_disposition(String admin_disposition) {
        this.admin_disposition = admin_disposition;
    }

    public Date getAdmin_own_date() {
        return admin_own_date;
    }

    public void setAdmin_own_date(Date admin_own_date) {
        this.admin_own_date = admin_own_date;
    }

    public String getAdmin_own_person() {
        return admin_own_person;
    }

    public void setAdmin_own_person(String admin_own_person) {
        this.admin_own_person = admin_own_person;
    }

    public Date getAdmin_rec_date() {
        return admin_rec_date;
    }

    public void setAdmin_rec_date(Date admin_rec_date) {
        this.admin_rec_date = admin_rec_date;
    }

    public String getAdmin_rec_person() {
        return admin_rec_person;
    }

    public void setAdmin_rec_person(String admin_rec_person) {
        this.admin_rec_person = admin_rec_person;
    }

    public String getCapa_number() {
        return capa_number;
    }

    public void setCapa_number(String capa_number) {
        this.capa_number = capa_number;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getEffectiveplan() {
        return effectiveplan;
    }

    public void setEffectiveplan(String effectiveplan) {
        this.effectiveplan = effectiveplan;
    }

    public String getNc() {
        return nc;
    }

    public void setNc(String nc) {
        this.nc = nc;
    }

    public String getNcyesno() {
        return ncyesno;
    }

    public void setNcyesno(String ncyesno) {
        this.ncyesno = ncyesno;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getRca() {
        return rca;
    }

    public void setRca(String rca) {
        this.rca = rca;
    }

    public Date getRca_a_date() {
        return rca_a_date;
    }

    public void setRca_a_date(Date rca_a_date) {
        this.rca_a_date = rca_a_date;
    }

    public Date getRca_t_date() {
        return rca_t_date;
    }

    public void setRca_t_date(Date rca_t_date) {
        this.rca_t_date = rca_t_date;
    }

    public String getVerify() {
        return verify;
    }

    public void setVerify(String verify) {
        this.verify = verify;
    }

    public Date getVerify_a_date() {
        return verify_a_date;
    }

    public void setVerify_a_date(Date verify_a_date) {
        this.verify_a_date = verify_a_date;
    }

    public String getVerify_approve() {
        return verify_approve;
    }

    public void setVerify_approve(String verify_approve) {
        this.verify_approve = verify_approve;
    }

    public Date getVerify_t_date() {
        return verify_t_date;
    }

    public void setVerify_t_date(Date verify_t_date) {
        this.verify_t_date = verify_t_date;
    }

    public String getActionimp_status() {
        return actionimp_status;
    }

    public void setActionimp_status(String actionimp_status) {
        this.actionimp_status = actionimp_status;
    }

    public String getActionplan_approve2() {
        return actionplan_approve2;
    }

    public void setActionplan_approve2(String actionplan_approve2) {
        this.actionplan_approve2 = actionplan_approve2;
    }

    public Date getAdmin_disp_date() {
        return admin_disp_date;
    }

    public void setAdmin_disp_date(Date admin_disp_date) {
        this.admin_disp_date = admin_disp_date;
    }

    public String getAdmin_disp_person() {
        return admin_disp_person;
    }

    public void setAdmin_disp_person(String admin_disp_person) {
        this.admin_disp_person = admin_disp_person;
    }

    public String getNcminormajor() {
        return ncminormajor;
    }

    public void setNcminormajor(String ncminormajor) {
        this.ncminormajor = ncminormajor;
    }

    public String getVerify_approve2() {
        return verify_approve2;
    }

    public void setVerify_approve2(String verify_approve2) {
        this.verify_approve2 = verify_approve2;
    }

    public String getOwner2() {
        return owner2;
    }

    public void setOwner2(String owner2) {
        this.owner2 = owner2;
    }

    public String getOwner3() {
        return owner3;
    }

    public void setOwner3(String owner3) {
        this.owner3 = owner3;
    }

    public String getCapaid_description() {
        return capaid_description;
    }

    public void setCapaid_description(String capaid_description) {
        this.capaid_description = capaid_description;
    }



}
