package com.bootcamp.java.account;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bootcamp.java.account.repository.AccountRepository;
import com.bootcamp.java.account.service.AccountService;
import com.bootcamp.java.account.web.mapper.AccountMapper;

@ExtendWith(MockitoExtension.class)
public class MockitoTestIT_V3_InyectarMocksTest {

	@Mock
	AccountRepository accountRepository;
	
	@Mock
	AccountMapper accountMapper;
	
	@InjectMocks
	AccountService accountService;
	
	
    @Test
    void testMock() throws Exception {
    	accountService.findAll();
    }

}
