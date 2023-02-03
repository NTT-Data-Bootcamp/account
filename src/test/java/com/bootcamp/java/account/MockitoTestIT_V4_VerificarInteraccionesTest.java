package com.bootcamp.java.account;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bootcamp.java.account.repository.AccountRepository;
import com.bootcamp.java.account.service.AccountService;
import com.bootcamp.java.account.web.mapper.AccountMapper;

@ExtendWith(MockitoExtension.class)
public class MockitoTestIT_V4_VerificarInteraccionesTest {


	@Mock
	AccountRepository accountRepository;
	
	@Mock
	AccountMapper accountMapper;
	
	@InjectMocks
	AccountService accountService;
	
	
    @Test
    void findAll() throws Exception {
    	accountService.findAll();
    	accountService.findAll();
    	verify(accountRepository, times(2)).findAll();
    }

}
