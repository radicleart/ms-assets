package com.radicle.assets.api.model;

import java.io.Serializable;

public class UserInvoiceModel implements Serializable {

	private static final long serialVersionUID = -6076158540399552737L;

	private Long amount;
	private String memo;

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
}
