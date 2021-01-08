package com.qianyou.wangxin;

public class CodeBean {
	private String h5Url;
	  
	  private String mark;
	  
	  private String money;
	  
	  private String orderNo;
	  
	  private String payUrl;
	  
	  private String qunId;
	  
	  public String getH5Url() {
	    return (this.h5Url == null) ? "" : this.h5Url;
	  }
	  
	  public String getMark() {
	    return (this.mark == null) ? "" : this.mark;
	  }
	  
	  public String getMoney() {
	    return (this.money == null) ? "" : this.money;
	  }
	  
	  public String getOrderNo() {
	    return (this.orderNo == null) ? "" : this.orderNo;
	  }
	  
	  public String getPayUrl() {
	    return (this.payUrl == null) ? "" : this.payUrl;
	  }
	  
	  public String getQunId() {
	    return (this.qunId == null) ? "" : this.qunId;
	  }
	  
	  public void setH5Url(String paramString) {
	    this.h5Url = paramString;
	  }
	  
	  public void setMark(String paramString) {
	    this.mark = paramString;
	  }
	  
	  public void setMoney(String paramString) {
	    this.money = paramString;
	  }
	  
	  public void setOrderNo(String paramString) {
	    this.orderNo = paramString;
	  }
	  
	  public void setPayUrl(String paramString) {
	    this.payUrl = paramString;
	  }
	  
	  public void setQunId(String paramString) {
	    this.qunId = paramString;
	  }
}
