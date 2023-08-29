//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.khteam2.connectgym.dietlist.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(
    name = "foods"
)

@XmlRootElement(name = "food")
public class Food {
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    private String foodCd; //식품 코드

    private String foodNm; //식품명
    private double fatce; //지방
    private double chocdf; //탄수화물
    private double prot; // 단백질
    private double enerc; // 칼로리

    private double sugar;  // 당류
    private double foodSize;  // 식품중량
    private String cooCd; // 원산지국코드
    private String cooNm; // 원산지국명
    private String companyNm; // 업체명

    private double nutConSrtrQua; //영양성분 함량


    public Food() {
    }

    public String getFoodCd() {
        return foodCd;
    }

    public void setFoodCd(String foodCd) {
        this.foodCd = foodCd;
    }

    public String getFoodNm() {
        return foodNm;
    }

    public void setFoodNm(String foodNm) {
        this.foodNm = foodNm;
    }

    public double getFatce() {
        return fatce;
    }

    public void setFatce(double fatce) {
        this.fatce = fatce;
    }

    public double getChocdf() {
        return chocdf;
    }

    public void setChocdf(double chocdf) {
        this.chocdf = chocdf;
    }

    public double getProt() {
        return prot;
    }

    public void setProt(double prot) {
        this.prot = prot;
    }

    public double getEnerc() {
        return enerc;
    }

    public void setEnerc(double enerc) {
        this.enerc = enerc;
    }

    public double getSugar() {
        return sugar;
    }

    public void setSugar(double sugar) {
        this.sugar = sugar;
    }

    public double getFoodSize() {
        return foodSize;
    }

    public void setFoodSize(double foodSize) {
        this.foodSize = foodSize;
    }

    public String getCooCd() {
        return cooCd;
    }

    public void setCooCd(String cooCd) {
        this.cooCd = cooCd;
    }

    public String getCooNm() {
        return cooNm;
    }

    public void setCooNm(String cooNm) {
        this.cooNm = cooNm;
    }

    public String getCompanyNm() {
        return companyNm;
    }

    public void setCompanyNm(String companyNm) {
        this.companyNm = companyNm;
    }

    public double getNutConSrtrQua() {
        return nutConSrtrQua;
    }

    public void setNutConSrtrQua(double nutConSrtrQua) {
        this.nutConSrtrQua = nutConSrtrQua;
    }
}
