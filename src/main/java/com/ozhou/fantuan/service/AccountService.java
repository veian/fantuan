package com.ozhou.fantuan.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Service;

import com.ozhou.fantuan.model.Account;
import com.ozhou.fantuan.model.MealRecord;

@Service
public class AccountService {
	
	public void splitMealRecord(MealRecord mealRecord) {
		mealRecord.getPayer().debit(mealRecord.getDate(), mealRecord.getAmount(), null);
		BigDecimal avgCost = mealRecord.getAmount().divide(
				new BigDecimal(mealRecord.getParticipants().size()), 2, RoundingMode.HALF_UP);
		for (Account account : mealRecord.getParticipants()) {
			account.credit(mealRecord.getDate(), avgCost, null);
		}
	}
}
