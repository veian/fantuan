package com.ozhou.fantuan.resource.converter;

import com.google.common.base.Objects;
import com.ozhou.fantuan.model.Account;
import com.ozhou.fantuan.model.MealRecord;
import com.ozhou.fantuan.model.dao.AccountDao;
import com.ozhou.fantuan.resource.Dto.AccountDto;
import com.ozhou.fantuan.resource.Dto.MealRecordDto;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings("all")
public class DtoBoConverter {
  @Autowired
  private AccountDao accountDao;
  
  public MealRecordDto toDto(final MealRecord mealRecord) {
    MealRecordDto _mealRecordDto = new MealRecordDto();
    MealRecordDto record = _mealRecordDto;
    BigDecimal _amount = mealRecord.getAmount();
    record.setAmount(_amount);
    Account _payer = mealRecord.getPayer();
    String _name = _payer.getName();
    record.setPayer(_name);
    List<Account> _participants = mealRecord.getParticipants();
    final Function1<Account,String> _function = new Function1<Account,String>() {
        public String apply(final Account m) {
          String _name = m.getName();
          return _name;
        }
      };
    List<String> participants = ListExtensions.<Account, String>map(_participants, _function);
    record.setParticipants(participants);
    String _restaurant = mealRecord.getRestaurant();
    record.setRestaurant(_restaurant);
    Date _date = mealRecord.getDate();
    record.setDate(_date);
    return record;
  }
  
  public MealRecord toBo(final MealRecordDto record) {
    MealRecord _mealRecord = new MealRecord();
    MealRecord mealRecord = _mealRecord;
    String _payer = record.getPayer();
    Account payer = this.accountDao.get(_payer);
    boolean _equals = Objects.equal(payer, null);
    if (_equals) {
      IllegalArgumentException _illegalArgumentException = new IllegalArgumentException("{\"error\" : \"Payer Account not exist!\"}");
      throw _illegalArgumentException;
    }
    mealRecord.setPayer(payer);
    List<String> _participants = record.getParticipants();
    final Function1<String,Account> _function = new Function1<String,Account>() {
        public Account apply(final String p) {
          Account account = DtoBoConverter.this.accountDao.get(p);
          boolean _equals = Objects.equal(account, null);
          if (_equals) {
            IllegalArgumentException _illegalArgumentException = new IllegalArgumentException("{\"error\" : \"Account not exist!\"}");
            throw _illegalArgumentException;
          }
          return account;
        }
      };
    List<Account> participants = ListExtensions.<String, Account>map(_participants, _function);
    mealRecord.setParticipants(participants);
    BigDecimal _amount = record.getAmount();
    mealRecord.setAmount(_amount);
    String _restaurant = record.getRestaurant();
    mealRecord.setRestaurant(_restaurant);
    Date _date = record.getDate();
    mealRecord.setDate(_date);
    return mealRecord;
  }
  
  public AccountDto toDto(final Account account) {
    AccountDto _accountDto = new AccountDto();
    AccountDto dto = _accountDto;
    String _name = account.getName();
    dto.setName(_name);
    BigDecimal _balance = account.getBalance();
    dto.setBalance(_balance);
    return dto;
  }
}
