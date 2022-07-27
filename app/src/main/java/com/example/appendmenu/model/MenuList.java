package com.example.appendmenu.model;

public class MenuList {
    private String menuType;
    private Long menuNum;
    private String menuName;
    private String menuPrice;
    private String menuDetail;
    private String menuCG;
    private Boolean stockState;
    private String OptSize01;
    private String OptPrice01;
    private String OptSize02;
    private String OptPrice02;
    private String OptSize03;
    private String OptPrice03;
    private String OptKind01;
    private String OptPrice04;
    private String OptKind02;
    private String OptPrice05;
    private String imgPath;

    public MenuList(Long menuNum, String menuName, String menuPrice, String menuDetail, String menuCG, Boolean stockState, String optSize01, String optPrice01, String optSize02, String optPrice02, String optSize03, String optPrice03, String optKind01, String optPrice04, String optKind02, String optPrice05, String imgPath) {
        this.menuNum = menuNum;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.menuDetail = menuDetail;
        this.menuCG = menuCG;
        this.stockState = stockState;
        this.OptSize01 = optSize01;
        this.OptPrice01 = optPrice01;
        this.OptSize02 = optSize02;
        this.OptPrice02 = optPrice02;
        this.OptSize03 = optSize03;
        this.OptPrice03 = optPrice03;
        this.OptKind01 = optKind01;
        this.OptPrice04 = optPrice04;
        this.OptKind02 = optKind02;
        this.OptPrice05 = optPrice05;
        this.imgPath = imgPath;
    }

    public MenuList() {
    }

    public MenuList(String menuType) {
        this.menuType = menuType;
    }


    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Long getMenuNum() {
        return menuNum;
    }

    public void setMenuNum(Long menuNum) {
        this.menuNum = menuNum;
    }

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuPrice() {
        return menuPrice;
    }

    public void setMenuPrice(String menuPrice) {
        this.menuPrice = menuPrice;
    }

    public String getMenuDetail() {
        return menuDetail;
    }

    public void setMenuDetail(String menuDetail) {
        this.menuDetail = menuDetail;
    }

    public String getMenuCG() {
        return menuCG;
    }

    public void setMenuCG(String menuCG) {
        this.menuCG = menuCG;
    }

    public Boolean getStockState() {
        return stockState;
    }

    public void setStockState(Boolean stockState) {
        this.stockState = stockState;
    }

    public String getOptSize01() {
        return OptSize01;
    }

    public void setOptSize01(String optSize01) {
        OptSize01 = optSize01;
    }

    public String getOptPrice01() {
        return OptPrice01;
    }

    public void setOptPrice01(String optPrice01) {
        OptPrice01 = optPrice01;
    }

    public String getOptSize02() {
        return OptSize02;
    }

    public void setOptSize02(String optSize02) {
        OptSize02 = optSize02;
    }

    public String getOptPrice02() {
        return OptPrice02;
    }

    public void setOptPrice02(String optPrice02) {
        OptPrice02 = optPrice02;
    }

    public String getOptSize03() {
        return OptSize03;
    }

    public void setOptSize03(String optSize03) {
        OptSize03 = optSize03;
    }

    public String getOptPrice03() {
        return OptPrice03;
    }

    public void setOptPrice03(String optPrice03) {
        OptPrice03 = optPrice03;
    }

    public String getOptKind01() {
        return OptKind01;
    }

    public void setOptKind01(String optKind01) {
        OptKind01 = optKind01;
    }

    public String getOptPrice04() {
        return OptPrice04;
    }

    public void setOptPrice04(String optPrice04) {
        OptPrice04 = optPrice04;
    }

    public String getOptKind02() {
        return OptKind02;
    }

    public void setOptKind02(String optKind02) {
        OptKind02 = optKind02;
    }

    public String getOptPrice05() {
        return OptPrice05;
    }

    public void setOptPrice05(String optPrice05) {
        OptPrice05 = optPrice05;
    }

    @Override
    public String toString() {
        return "MenuList{" +
                "menuType='" + menuType + '\'' +
                ", menuNum=" + menuNum +
                ", menuName='" + menuName + '\'' +
                ", menuPrice='" + menuPrice + '\'' +
                ", menuDetail='" + menuDetail + '\'' +
                ", menuCG='" + menuCG + '\'' +
                ", stockState=" + stockState +
                ", OptSize01='" + OptSize01 + '\'' +
                ", OptPrice01='" + OptPrice01 + '\'' +
                ", OptSize02='" + OptSize02 + '\'' +
                ", OptPrice02='" + OptPrice02 + '\'' +
                ", OptSize03='" + OptSize03 + '\'' +
                ", OptPrice03='" + OptPrice03 + '\'' +
                ", OptKind01='" + OptKind01 + '\'' +
                ", OptPrice04='" + OptPrice04 + '\'' +
                ", OptKind02='" + OptKind02 + '\'' +
                ", OptPrice05='" + OptPrice05 + '\'' +
                '}';
    }
}
