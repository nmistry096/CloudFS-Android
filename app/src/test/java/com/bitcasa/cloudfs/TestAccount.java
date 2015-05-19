package com.bitcasa.cloudfs;

import com.bitcasa.cloudfs.exception.BitcasaException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class TestAccount extends BaseTest {

    /**
     * Tests the Account class methods.
     */
    @Test
    public void testAccount() {
        Account account = null;
        try {
            account = BaseTest.session.account();
        } catch (final BitcasaException e) {
            Assert.fail();
        } finally {
            Assert.assertNotNull(account);
        }

        Assert.assertNotNull(account.getId());
        Assert.assertNotNull(account.getAccountLocale());
        Assert.assertNotNull(account.getPlanDisplayName());
        Assert.assertNotNull(account.getPlanId());
        Assert.assertNotNull(account.getSessionLocale());
        Assert.assertNotNull(account.getStateDisplayName());
        Assert.assertNotNull(account.getStateId());
        Assert.assertNotNull(account.getStorageLimit());
        Assert.assertNotNull(account.getStorageUsage());
        Assert.assertNotNull(account.getOverStorageLimit());
    }

    /**
     * Tests getSessionLocation method.
     */
    @Test
    public void testGetSessionLocale() throws BitcasaException {
        Account account = null;
        try {
            account = BaseTest.session.account();
        } catch (final BitcasaException e) {
            Assert.fail();
        } finally {
            Assert.assertNotNull(account);
        }
        Assert.assertNotNull(account.getSessionLocale());
    }

    /**
     * Tests getAccountLocation method.
     */
    @Test
    public void testGetAccountLocale() throws BitcasaException {
        Account account = null;
        try {
            account = BaseTest.session.account();
        } catch (final BitcasaException e) {
            e.printStackTrace();
            Assert.fail();
        } finally {
            Assert.assertNotNull(account);
        }
        Assert.assertNotNull(account.getAccountLocale());
    }
}
