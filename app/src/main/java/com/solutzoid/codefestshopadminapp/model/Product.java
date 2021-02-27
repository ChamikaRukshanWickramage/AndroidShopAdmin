package com.solutzoid.codefestshopadminapp.model;

public class Product {
    String productDocId;
    String productId;
    String productName;
    String productPrice;
    String productImageUrl;
    String productState;
    String adminDocId;

    public Product(){}

    public Product(String productDocId, String productId, String productName, String productPrice, String productImageUrl, String productState, String adminDocId) {
        this.productDocId = productDocId;
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productImageUrl = productImageUrl;
        this.productState = productState;
        this.adminDocId = adminDocId;
    }

    public String getAdminDocId() {
        return adminDocId;
    }

    public void setAdminDocId(String adminDocId) {
        this.adminDocId = adminDocId;
    }

    public String getProductState() {
        return productState;
    }

    public void setProductState(String productState) {
        this.productState = productState;
    }

    public String getProductDocId() {
        return productDocId;
    }

    public void setProductDocId(String productDocId) {
        this.productDocId = productDocId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }
}
