/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.inteqa;

import java.io.Serializable;

/**
 *
 * @author Niteshwar
 */
public class INQualityFeedback implements Serializable {

    private Integer id;
    private String tab;
    private String comScore;
    private String comExplain;
    private String comEnteredBy;
    private String timScore;
    private String timExplain;
    private String timEnteredBy;
    private String quaScore;
    private String quaExplain;
    private String quaEnteredBy;
    private String othScore;
    private String othExplain;
    private String othEnteredBy;
    private String porq;
    private String number;
    private Integer inteqaid;
    private Integer porqid;

    public INQualityFeedback() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTab() {
        return tab;
    }

    public void setTab(String tab) {
        this.tab = tab;
    }

    public String getComScore() {
        return comScore;
    }

    public void setComScore(String comScore) {
        this.comScore = comScore;
    }

    public String getComExplain() {
        return comExplain;
    }

    public void setComExplain(String comExplain) {
        this.comExplain = comExplain;
    }

    public String getComEnteredBy() {
        return comEnteredBy;
    }

    public void setComEnteredBy(String comEnteredBy) {
        this.comEnteredBy = comEnteredBy;
    }

    public String getTimScore() {
        return timScore;
    }

    public void setTimScore(String timScore) {
        this.timScore = timScore;
    }

    public String getTimExplain() {
        return timExplain;
    }

    public void setTimExplain(String timExplain) {
        this.timExplain = timExplain;
    }

    public String getTimEnteredBy() {
        return timEnteredBy;
    }

    public void setTimEnteredBy(String timEnteredBy) {
        this.timEnteredBy = timEnteredBy;
    }

    public String getQuaScore() {
        return quaScore;
    }

    public void setQuaScore(String quaScore) {
        this.quaScore = quaScore;
    }

    public String getQuaExplain() {
        return quaExplain;
    }

    public void setQuaExplain(String quaExplain) {
        this.quaExplain = quaExplain;
    }

    public String getQuaEnteredBy() {
        return quaEnteredBy;
    }

    public void setQuaEnteredBy(String quaEnteredBy) {
        this.quaEnteredBy = quaEnteredBy;
    }

    public String getOthScore() {
        return othScore;
    }

    public void setOthScore(String othScore) {
        this.othScore = othScore;
    }

    public String getOthExplain() {
        return othExplain;
    }

    public void setOthExplain(String othExplain) {
        this.othExplain = othExplain;
    }

    public String getOthEnteredBy() {
        return othEnteredBy;
    }

    public void setOthEnteredBy(String othEnteredBy) {
        this.othEnteredBy = othEnteredBy;
    }

    public String getPorq() {
        return porq;
    }

    public void setPorq(String porq) {
        this.porq = porq;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getInteqaid() {
        return inteqaid;
    }

    public void setInteqaid(Integer inteqaid) {
        this.inteqaid = inteqaid;
    }

    public Integer getPorqid() {
        return porqid;
    }

    public void setPorqid(Integer porqid) {
        this.porqid = porqid;
    }
    
    
}
