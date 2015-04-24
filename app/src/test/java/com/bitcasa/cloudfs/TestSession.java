package com.bitcasa.cloudfs;

import com.bitcasa.cloudfs.exception.BitcasaException;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.robolectric.RobolectricTestRunner;

import java.io.IOException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestSession extends BaseTest {

    private Session mBitcasaSession;

    /**
     * Tests the authenticate method of the session class.
     */
    @Test
    public void test1Authenticate() {
        mBitcasaSession = new Session(cloudfsEndpoint, clientId, clientSecret);
        try {
            mBitcasaSession.authenticate(username, password);
        } catch (IOException e) {
            assertTrue(false);
            e.printStackTrace();
        } catch (BitcasaException e) {
            assertTrue(false);
            e.printStackTrace();
        }
    }

    /**
     * Tests the is linked method of the session class.
     */
    @Test
    public void test2IsLinked() {
        mBitcasaSession = new Session(cloudfsEndpoint, clientId, clientSecret);
        try {
            mBitcasaSession.authenticate(username, password);
        } catch (IOException e) {
            assertTrue(false);
            e.printStackTrace();
        } catch (BitcasaException e) {
            assertTrue(false);
            e.printStackTrace();
        }
        assertTrue(mBitcasaSession.isLinked());
    }

    /**
     * Tests the unlink method of the session class.
     */
    @Test
    public void test3Unlink() {
        mBitcasaSession = new Session(cloudfsEndpoint, clientId, clientSecret);
        try {
            mBitcasaSession.authenticate(username, password);
        } catch (IOException e) {
            assertTrue(false);
            e.printStackTrace();
        } catch (BitcasaException e) {
            assertTrue(false);
            e.printStackTrace();
        }
        mBitcasaSession.unlink();
        assertTrue(!mBitcasaSession.isLinked());
    }

    /**
     * Tests the user method of the session class.
     */
    @Test
    public void test4User() {
        mBitcasaSession = new Session(cloudfsEndpoint, clientId, clientSecret);
        try {
            mBitcasaSession.authenticate(username, password);
        } catch (IOException e) {
            assertTrue(false);
            e.printStackTrace();
        } catch (BitcasaException e) {
            assertTrue(false);
            e.printStackTrace();
        }

        try {
            assertNotNull(mBitcasaSession.user());
        } catch (IOException e) {
            assertTrue(false);
            e.printStackTrace();
        } catch (BitcasaException e) {
            assertTrue(false);
            e.printStackTrace();
        }
    }

    /**
     * Tests the account method of the session class.
     */
    @Test
    public void test5Account() {
        mBitcasaSession = new Session(cloudfsEndpoint, clientId, clientSecret);
        try {
            mBitcasaSession.authenticate(username, password);
        } catch (IOException e) {
            assertTrue(false);
            e.printStackTrace();
        } catch (BitcasaException e) {
            assertTrue(false);
            e.printStackTrace();
        }
        try {
            assertNotNull(mBitcasaSession.account());
        } catch (IOException e) {
            assertTrue(false);
            e.printStackTrace();
        } catch (BitcasaException e) {
            assertTrue(false);
            e.printStackTrace();
        }
    }

    /**
     * Tests the Filesystem method of the session class.
     */
    @Test
    public void test6FileSystem() {
        mBitcasaSession = new Session(cloudfsEndpoint, clientId, clientSecret);
        try {
            mBitcasaSession.authenticate(username, password);
        } catch (IOException e) {
            assertTrue(false);
            e.printStackTrace();
        } catch (BitcasaException e) {
            assertTrue(false);
            e.printStackTrace();
        }
        assertNotNull(mBitcasaSession.filesystem());
    }

    /**
     * Tests the action history method of the session class.
     */
    @Test
    public void test7ActionHistory() {
        mBitcasaSession = new Session(cloudfsEndpoint, clientId, clientSecret);
        try {
            mBitcasaSession.authenticate(username, password);
        } catch (IOException e) {
            assertTrue(false);
            e.printStackTrace();
        } catch (BitcasaException e) {
            assertTrue(false);
            e.printStackTrace();
        }
        try {
            ActionHistory actionHistory = mBitcasaSession.actionHistory(-10, 10);
            assertNotNull(actionHistory);
        } catch (IOException e) {
            assertTrue(false);
            e.printStackTrace();
        } catch (BitcasaException e) {
            assertTrue(false);
            e.printStackTrace();
        }
    }

    /**
     * Tests the create user method of the session class.
     */
    @Test
    public void test8UserCreate() {
        mBitcasaSession = new Session(cloudfsEndpoint, clientId, clientSecret);
        try {
            mBitcasaSession.authenticate(username, password);
        } catch (IOException e) {
            assertTrue(false);
            e.printStackTrace();
        } catch (BitcasaException e) {
            assertTrue(false);
            e.printStackTrace();
        }
        mBitcasaSession.setAdminCredentials(adminId, adminSecret);
        String email = String.valueOf(System.currentTimeMillis()) + "@unique.com";

        User user = null;
        try {
            user = mBitcasaSession.createAccount(email, "123456", email, "First", "Last", false);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BitcasaException e) {
            e.printStackTrace();
        }
        assertNotNull(user);
        assertEquals(user.getUsername(), email);
    }
}
