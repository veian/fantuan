package com.ozhou.fantuan.resource.Dto;

import java.math.BigDecimal;
import java.util.Date;

public class AccountEntryDto {
	private String type;
	private BigDecimal amount;
	private Date date;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
}
