package com.everis.atmdeposit;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.everis.atmdeposit.controller.ApiRestControllerAdvice;
import com.everis.atmdeposit.controller.AtmDepositsController;
import com.everis.bootcamp.commons.models.entity.AccountResponse;
import com.everis.bootcamp.commons.models.entity.Card;
import com.everis.bootcamp.commons.models.entity.CardResponse;
import com.everis.bootcamp.commons.models.entity.FingerprintResponse;
import com.everis.bootcamp.commons.models.entity.PersonResponse;
import com.everis.bootcamp.commons.models.entity.ReniecResponse;
import com.everis.atmdeposit.models.service.ATMDepositServiceImpl;

import io.reactivex.Single;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ControllersTest {

	@InjectMocks
	private AtmDepositsController atmController = new AtmDepositsController();

	private ApiRestControllerAdvice apiControllerAdvice = new ApiRestControllerAdvice();
	
	@Mock
	private ATMDepositServiceImpl atmService;
	
	private Map<String, Object> body = new HashMap<String, Object>();

	@Before
	public void defaultMethods() {
		MockitoAnnotations.initMocks(this);
		
		body.put("amount", 100.00);
		
		when(atmService.callPersonService("10000000")).thenReturn(Single.just(new PersonResponse(1, "10000000", true, false)));
		when(atmService.callPersonService("10000001")).thenReturn(Single.just(new PersonResponse(2, "10000001", false, false)));
		when(atmService.callPersonService("10000002")).thenReturn(Single.just(new PersonResponse(3, "10000002", true, true)));
		when(atmService.callPersonService("10000003")).thenReturn(Single.just(new PersonResponse()));
		
		when(atmService.callFingerprintService("10000000")).thenReturn(Single.just(new FingerprintResponse("Core", true)));
	
		when(atmService.callReniecService("10000001")).thenReturn(Single.just(new ReniecResponse("Reniec", true)));
		
		when(atmService.callCardService("10000000")).thenReturn(Single.just(new CardResponse(
				Arrays.asList(
					new Card("1111222233334441", true),
					new Card("1111222233334442", true), 
					new Card("1111222233334443", true))
				)));
		when(atmService.callCardService("10000001")).thenReturn(Single.just(new CardResponse(
				Arrays.asList(
						new Card("1111222233334441", true),
						new Card("1111222233334442", true), 
						new Card("1111222233334443", true)))
				));
		
		when(atmService.callAccountService("1111222233334441")).thenReturn(Single.just(new AccountResponse("1111222233334441XXX", 1000.00)));
		when(atmService.callAccountService("1111222233334442")).thenReturn(Single.just(new AccountResponse("1111222233334442XXX", 500.00)));
		when(atmService.callAccountService("1111222233334443")).thenReturn(Single.just(new AccountResponse("1111222233334443XXX", 1500.00)));
	}

	@Test
	public void testPersonNotFound() {
		body.put("documentNumber", "10000003");
		atmController.toDeposit(body).test()
			.assertComplete()
			.assertNoErrors()
			.assertValueCount(1)
			.assertValue(obj -> obj.getClass().equals(new ClassNotFoundException().getClass()));
	}

	@Test
	public void testBlackList() {
		body.put("documentNumber", "10000002");
		atmController.toDeposit(body).test()
			.assertComplete()
			.assertNoErrors()
			.assertValueCount(1)
			.assertValue(obj -> obj.getClass().equals(new IllegalAccessException().getClass()));
	}
	
	@SuppressWarnings("rawtypes")
	@Test
	public void testFingerPrint() {		
		body.put("documentNumber", "10000000");
		atmController.toDeposit(body).test()
			.assertComplete()
			.assertNoErrors()
			.assertValueCount(1)
			.assertValue(obj -> obj.getClass().equals(new ResponseEntity(HttpStatus.OK).getClass()));
	}
	
	@SuppressWarnings("rawtypes")
	@Test
	public void testReniec() {
		body.put("documentNumber", "10000001");
		atmController.toDeposit(body).test()
			.assertComplete()
			.assertNoErrors()
			.assertValueCount(1)
			.assertValue(obj -> obj.getClass().equals(new ResponseEntity(HttpStatus.OK).getClass()));
	}
	
	@Test
	public void testStatusForbidden() {
		ResponseEntity<Map<String, Object>> actual = apiControllerAdvice.statusForbidden(new IllegalAccessException("Person not found"));
		IllegalAccessException expected = new IllegalAccessException("Person not found");	
		assertNotNull(actual);
		assertEquals(expected.getMessage(), actual.getBody().get("message"));
		assertEquals(expected.getClass(), actual.getBody().get("exception"));
		assertEquals(HttpStatus.FORBIDDEN, actual.getStatusCode());
	}
	
	@Test
	public void testStatusNotfound() {
		ResponseEntity<Map<String, Object>> actual = apiControllerAdvice.statusNotFound(new ClassNotFoundException("Person not found"));
		ClassNotFoundException expected = new ClassNotFoundException("Person not found");	
		assertNotNull(actual);
		assertEquals(expected.getMessage(), actual.getBody().get("message"));
		assertEquals(expected.getClass(), actual.getBody().get("exception"));
		assertEquals(HttpStatus.NOT_FOUND, actual.getStatusCode());
	}

}
