package com.ozhou.fantuan.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.google.common.collect.Lists;

@Entity
public class Account {

	@Id
	private String name;
	@Column(precision=10, scale=2)
	private BigDecimal balance;
	@OneToMany(mappedBy="account", cascade = {CascadeType.ALL})
	private List<AccountEntry> entries = Lists.newArrayList();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public List<AccountEntry> getEntries() {
		return entries;
	}
	public void setEntries(List<AccountEntry> entries) {
		this.entries = entries;
	}
	
	public void debit(Date date, BigDecimal amount, String description) {
		createAccountEntry(AccountEntry.Type.DEBIT, date, amount, description);
		balance = balance.add(amount);
	}
	
	public void credit(Date date, BigDecimal amount, String description) {
		createAccountEntry(AccountEntry.Type.CREDIT, date, amount, description);
		balance = balance.subtract(amount);
	}
	
	private void createAccountEntry(AccountEntry.Type type, Date date, BigDecimal amount, String description) {
		AccountEntry entry = new AccountEntry();
		entry.setType(type);
		entry.setAmount(amount);
		entry.setDescription(description);
		entry.setDate(date);
		
		getEntries().add(entry);
		entry.setAccount(this);
	}
	
}
