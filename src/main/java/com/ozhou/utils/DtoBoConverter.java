package com.ozhou.utils;

import ma.glasnost.orika.CustomConverter;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.Type;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import com.ozhou.fantuan.model.Account;
import com.ozhou.fantuan.model.AccountEntry;
import com.ozhou.fantuan.model.MealRecord;
import com.ozhou.fantuan.model.dao.AccountDao;
import com.ozhou.fantuan.resource.dto.MealRecordDto;
import com.ozhou.utils.SpringContextUtils;

@Component
public class DtoBoConverter implements InitializingBean {

	private MapperFactory mapperFactory;
	
	public MapperFactory getMapperFactory() {
		return mapperFactory;
	}
	
	public MapperFacade getMappingFacade() {
		return mapperFactory.getMapperFacade();
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		mapperFactory = new DefaultMapperFactory.Builder().build();
		mapperFactory.getConverterFactory().registerConverter(new BidirectionalConverter<Account, String>() {
			
			public String convertTo(Account source, Type<String> destinationType) {
				return source.getName();
			}

			public Account convertFrom(String source,
					Type<Account> destinationType) {
				AccountDao dao = SpringContextUtils.getBean(AccountDao.class);
				return dao.get(source);
			}
		});
		
		mapperFactory.classMap(MealRecord.class, MealRecordDto.class)
			//.field("payer.name", "payer")
			//.field("participants", "participants")
			.byDefault()
			.register();
		
		mapperFactory.getConverterFactory().registerConverter(new CustomConverter<AccountEntry.Type, String>() {
			@Override
			public String convert(
					com.ozhou.fantuan.model.AccountEntry.Type source,
					Type<? extends String> destinationType) {
				return source == AccountEntry.Type.DEBIT ? "充值" : "消费";
			}		
		});
	}

}
