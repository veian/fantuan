package com.ozhou.fantuan.model;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Account {

	@Id
	private String name;
	@Basic
	private BigDecimal balance;
	@OneToMany(mappedBy="account", cascade = {CascadeType.ALL})
	private List<AccountEntry> entries;
	
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
	
	public void debit(BigDecimal amount, String description) {
		createAccountEntry(AccountEntry.Type.DEBIT, amount, description);
		balance = balance.add(amount);
	}
	
	public void credit(BigDecimal amount, String description) {
		createAccountEntry(AccountEntry.Type.CREDIT, amount, description);
		balance = balance.subtract(amount);
	}
	
	private void createAccountEntry(AccountEntry.Type type, BigDecimal amount, String description) {
		AccountEntry entry = new AccountEntry();
		entry.setType(type);
		entry.setAmount(amount);
		entry.setDescription(description);
		
		getEntries().add(entry);
		entry.setAccount(this);
	}
	
}
