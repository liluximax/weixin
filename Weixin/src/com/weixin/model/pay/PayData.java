package com.weixin.model.pay;

public class PayData {
	private BaseContent first;
	private BaseContent product;
	private BaseContent price;
	private BaseContent time;
	private BaseContent remark;
	
	public BaseContent getRemark() {
		return remark;
	}
	public void setRemark(BaseContent remark) {
		this.remark = remark;
	}
	public BaseContent getFirst() {
		return first;
	}
	public void setFirst(BaseContent first) {
		this.first = first;
	}
	public BaseContent getProduct() {
		return product;
	}
	public void setProduct(BaseContent product) {
		this.product = product;
	}
	public BaseContent getPrice() {
		return price;
	}
	public void setPrice(BaseContent price) {
		this.price = price;
	}
	public BaseContent getTime() {
		return time;
	}
	public void setTime(BaseContent time) {
		this.time = time;
	}

	
}
