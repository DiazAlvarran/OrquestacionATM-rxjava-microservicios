package com.everis.atmdeposit;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.boot.test.context.SpringBootTest;

import com.everis.atmdeposit.client.ClientFeignAccounts;
import com.everis.atmdeposit.client.ClientFeignCards;
import com.everis.atmdeposit.client.ClientFeignFingerprints;
import com.everis.atmdeposit.client.ClientFeignPersons;
import com.everis.atmdeposit.client.ClientFeignReniec;
import com.everis.atmdeposit.models.service.ATMDepositServiceImpl;
import com.everis.bootcamp.commons.models.entity.AccountResponse;
import com.everis.bootcamp.commons.models.entity.Card;
import com.everis.bootcamp.commons.models.entity.CardResponse;
import com.everis.bootcamp.commons.models.entity.FingerprintResponse;
import com.everis.bootcamp.commons.models.entity.PersonResponse;
import com.everis.bootcamp.commons.models.entity.ReniecResponse;

@RunWith(value = Parameterized.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ServicesTest {
	
	@Rule
    public MockitoRule mockito = MockitoJUnit.rule();  
	
	@InjectMocks
	private ATMDepositServiceImpl atmService;
	
	@Mock
	private ClientFeignPersons personFeign;
	
	@Mock
	private ClientFeignFingerprints fingerprintsFeign;
	
	@Mock
	private ClientFeignReniec reniecFeign;
	
	@Mock
	private ClientFeignCards cardsFeign;
	
	@Mock
	private ClientFeignAccounts accountsFeign;
	
	String document;
	String cardNumber;
	String accountNumber;
	
	@Parameters
	public static Iterable<Object[]> getData(){
		List<Object[]> obj = new ArrayList<Object[]>();
		obj.add(new Object[] {"10000000", "1111222233334441", "1111222233334441XXX"});
		obj.add(new Object[] {"10000001", "1111222233334442", "1111222233334442XXX"});
		obj.add(new Object[] {"10000002", "1111222233334443", "1111222233334443XXX"});
		return obj;
	}
	
	public ServicesTest(String document, String cardNumber, String accountNumber) {
		this.document = document;
		this.cardNumber = cardNumber;
		this.accountNumber = accountNumber;
	}
	
	@Test
	public void testPersonService() {
		when(personFeign.showPersonByDocument(document)).thenReturn(new PersonResponse(1, document, true, false));
		atmService.callPersonService(document).test()
			.assertComplete()
			.assertNoErrors()
			.assertValueCount(1)
			.assertValue(obj -> obj.getClass().equals(new PersonResponse().getClass()))
			.assertResult(new PersonResponse(1, document, true, false));
	}
	
	@Test
	public void testFingerprintService() {
		when(fingerprintsFeign.getValidateFingerprint(document)).thenReturn(new FingerprintResponse("Core", true));
		atmService.callFingerprintService(document).test()
			.assertComplete()
			.assertNoErrors()
			.assertValueCount(1)
			.assertValue(obj -> obj.getClass().equals(new FingerprintResponse().getClass()))
			.assertResult(new FingerprintResponse("Core", true));
	}
	
	@Test
	public void testReniecService() {
		when(reniecFeign.getValidateReniec(document)).thenReturn(new ReniecResponse("Reniec", true));
		atmService.callReniecService(document).test()
			.assertComplete()
			.assertNoErrors()
			.assertValueCount(1)
			.assertValue(obj -> obj.getClass().equals(new ReniecResponse().getClass()))
			.assertResult(new ReniecResponse("Reniec", true));
	}
	
	@Test
	public void testCardsService() {
		when(cardsFeign.getActiveCard(document)).thenReturn(new CardResponse(Arrays.asList(
				new Card("1111222233334441", true),
				new Card("1111222233334442", true),
				new Card("1111222233334443", true),
				new Card("1111222233334444", false),
				new Card("1111222233334445", false),
				new Card("1111222233334446", false)
			)));
		atmService.callCardService(document).test()
			.assertComplete()
			.assertNoErrors()
			.assertValueCount(1)
			.assertValue(obj -> obj.getClass().equals(new CardResponse().getClass()))
			.assertResult(new CardResponse(Arrays.asList(
					new Card("1111222233334441", true),
					new Card("1111222233334442", true),
					new Card("1111222233334443", true)
				)));
	}
	
	@Test
	public void testAccount() {
		when(accountsFeign.getAccount(cardNumber)).thenReturn(new AccountResponse(accountNumber, 1000.00));
		atmService.callAccountService(cardNumber).test()
			.assertComplete()
			.assertNoErrors()
			.assertValueCount(1)
			.assertValue(obj -> obj.getClass().equals(new AccountResponse().getClass()))
			.assertResult(new AccountResponse(accountNumber, 1000.00));
	}

}
