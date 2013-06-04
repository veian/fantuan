import java.math.BigDecimal;
import java.util.Date;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.Type;

import com.ozhou.fantuan.model.Account;
import com.ozhou.fantuan.model.AccountEntry;
import com.ozhou.fantuan.model.MealRecord;
import com.ozhou.fantuan.model.dao.AccountDao;
import com.ozhou.fantuan.resource.dto.AccountDto;
import com.ozhou.fantuan.resource.dto.MealRecordDto;


public class OrikaTest {
	
	public static void main(String[] args) {
		//System.setProperty(OrikaSystemProperties.WRITE_SOURCE_FILES,"true");
		//System.setProperty(OrikaSystemProperties.WRITE_SOURCE_FILES_TO_PATH,"/Users/zhouyujie/Temp");
		MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
		mapperFactory.getConverterFactory().registerConverter(new BidirectionalConverter<Account, String>() {
			AccountDao dao;
			{
				//dao = SpringContextUtils.getBean(AccountDao.class);
			}
			public String convertTo(Account source, Type<String> destinationType) {
				return source.getName();
			}

			public Account convertFrom(String source,
					Type<Account> destinationType) {
				return dao.get(source);
			}
		});
		mapperFactory.classMap(MealRecord.class, MealRecordDto.class)
			.field("payer.name", "payer")
			.field("participants", "participants")
			.byDefault()
			.register();		
		
		Account account = new Account();
		account.setName("Oliver Zhou");
		account.setBalance(new BigDecimal(100));
		AccountEntry entry = new AccountEntry();
		entry.setAccount(account);
		entry.setAmount(new BigDecimal(200));
		AccountDto dto = mapperFactory.getMapperFacade().map(account, AccountDto.class);
		System.out.println(dto);
		
		MealRecord mealRecord = new MealRecord();
		mealRecord.setAmount(new BigDecimal(100));
		mealRecord.setDate(new Date());
		mealRecord.setPayer(account);
		mealRecord.setRestaurant("lalal");
		mealRecord.getParticipants().add(account);
		
		MealRecordDto dto2 = mapperFactory.getMapperFacade().map(mealRecord, MealRecordDto.class);
		System.out.println(dto2);
		
		// Use it as clone
		MealRecordDto dto3 = mapperFactory.getMapperFacade().map(dto2, MealRecordDto.class);
		System.out.println(dto3);
	}
}
