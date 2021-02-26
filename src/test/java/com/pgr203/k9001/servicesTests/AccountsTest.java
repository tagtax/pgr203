package com.pgr203.k9001.servicesTests;

import com.pgr203.k9001.model.Account;
import com.pgr203.k9001.services.Accounts;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import static com.pgr203.k9001.util.DataSource.dataSourceSetup;
import static com.pgr203.k9001.util.Helpers.PickOne;
import static org.assertj.core.api.Assertions.assertThat;

public class AccountsTest {
    private Accounts accounts;

    @Rule
    public PostgreSQLContainer postgresContainer = new PostgreSQLContainer("postgres:latest");

    @Before
    public void setUp() throws SQLException {
        Connection connection = dataSourceSetup(postgresContainer.getJdbcUrl(), postgresContainer.getUsername(), postgresContainer.getPassword());
        accounts = new Accounts(connection);
    }

    static Account generateAccountData() {
        Account account = new Account();
        account.setAccountName(PickOne(new String[]{"test1 test_1", "test2 test_2", "test3 test_3", "test4 test_4", "test5 test_5", "test6 test_6"}));
        account.setEmail(PickOne(new String[]{"test1@mail.com", "test2@mail.com", "test3@mail.com", "test4@mail.com", "test5@mail.com", "test6@mail.com"}));
        return account;
    }

    @Test
    public void shouldAccountCreate() throws SQLException {
        Account account = generateAccountData();
        accounts.create(account);
        assertThat(account.getId()).isNotZero();
    }

    @Test
    public void shouldReadCreatedAccount() throws SQLException {
        Account account = generateAccountData();
        accounts.create(account);
        assertThat(account.getId()).isNotZero();
        Optional<Account> createdAccount = accounts.read(account.getId());
        assertThat(createdAccount.orElse(new Account()).getId()).isEqualTo(account.getId());
        assertThat(createdAccount.orElse(new Account()).getAccountName()).isEqualTo(account.getAccountName());
        assertThat(createdAccount.orElse(new Account()).getEmail()).isEqualTo(account.getEmail());
    }
}
