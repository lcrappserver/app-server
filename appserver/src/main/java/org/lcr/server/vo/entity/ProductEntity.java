package org.lcr.server.vo.entity;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;

@SuppressWarnings("unused")
@Alias(value = "ProductEntity")
public class ProductEntity implements Serializable {

    private static final long serialVersionUID = 4017133346163783765L;

    private String prodType;

    private String prodBrand;

    private String prodName;

    private String prodId;

    public String getProdType() {
        return prodType;
    }

    public void setProdType(String prodType) {
        this.prodType = prodType;
    }

    public String getProdBrand() {
        return prodBrand;
    }

    public void setProdBrand(String prodBrand) {
        this.prodBrand = prodBrand;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }
}
