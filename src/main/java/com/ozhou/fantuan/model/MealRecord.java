package com.ozhou.fantuan.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class MealRecord {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Basic
	private BigDecimal amount;
	@Basic
	private String restaurant;
	@Temporal(TemporalType.DATE)
	private Date date;
	@ManyToOne
	private Account payer;
	@ManyToMany
	private List<Account> participants;

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(String restaurant) {
		this.restaurant = restaurant;
	}

	public Account getPayer() {
		return payer;
	}

	public void setPayer(Account payer) {
		this.payer = payer;
	}

	public List<Account> getParticipants() {
		return participants;
	}

	public void setParticipants(List<Account> participants) {
		this.participants = participants;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
}
