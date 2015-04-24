package com.bitcasa.cloudfs;

import com.bitcasa.cloudfs.exception.BitcasaException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.io.IOException;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class TestAccount extends BaseTest {

    /**
     * Tests the Account class methods.
     */
    @Test
    public void testAccount() {
        Account account = null;
        try {
            account = session.account();
        } catch (IOException e) {
            e.printStackTrace();
            assertTrue(false);
        } catch (BitcasaException e) {
            assertNotNull(account);
        } finally {
            assertNotNull(account);
        }

        assertNotNull(account.getId());
        assertNotNull(account.getAccountLocale());
        assertNotNull(account.getPlanDisplayName());
        assertNotNull(account.getPlanId());
        assertNotNull(account.getSessionLocale());
        assertNotNull(account.getStateDisplayName());
        assertNotNull(account.getStateId());
        assertNotNull(account.getStorageLimit());
        assertNotNull(account.getStorageUsage());
        assertNotNull(account.getOverStorageLimit());
    }

    /**
     * Tests getSessionLocation method.
     */
    @Test
    public void testGetSessionLocale() throws Throwable {
        Account account = null;
        try {
            account = session.account();
        } catch (IOException e) {
            e.printStackTrace();
            assertTrue(false);
        } finally {
            assertNotNull(account);
        }
        assertNotNull(account.getSessionLocale());
    }

    /**
     * Tests getAccountLocation method.
     */
    @Test
    public void testGetAccountLocale() throws Throwable {
        Account account = null;
        try {
            account = session.account();
        } catch (IOException e) {
            e.printStackTrace();
            assertTrue(false);
        } finally {
            assertNotNull(account);
        }
        assertNotNull(account.getAccountLocale());
    }
}
