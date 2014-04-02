/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package app.project;

import java.io.Serializable;

/**
 *
 * @author Niteshwar
 */
public class QualityReport implements Serializable{
        private Integer qualityno;
    private String quality;
    private String qualityscore;
    private String qualitydesc;
    private String qualityyear;

    public QualityReport() {
    }

    public QualityReport(Integer qualityno, String quality, String qualityscore, String qualitydesc, String qualityyear) {
        this.qualityno = qualityno;
        this.quality = quality;
        this.qualityscore = qualityscore;
        this.qualitydesc = qualitydesc;
        this.qualityyear = qualityyear;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getQualitydesc() {
        return qualitydesc;
    }

    public void setQualitydesc(String qualitydesc) {
        this.qualitydesc = qualitydesc;
    }

    public Integer getQualityno() {
        return qualityno;
    }

    public void setQualityno(Integer qualityno) {
        this.qualityno = qualityno;
    }

    public String getQualityscore() {
        return qualityscore;
    }

    public void setQualityscore(String qualityscore) {
        this.qualityscore = qualityscore;
    }

    public String getQualityyear() {
        return qualityyear;
    }

    public void setQualityyear(String qualityyear) {
        this.qualityyear = qualityyear;
    }



}
