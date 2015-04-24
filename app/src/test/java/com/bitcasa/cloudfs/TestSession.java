package com.bitcasa.cloudfs;

import com.bitcasa.cloudfs.exception.BitcasaException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.io.IOException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)

public class TestSession {
    private final static String CLIENT_ID = "6VaRUN0AJDftZaaFyQy98oHvVmuUjI8fJz6UHIkQct0";
    private final static String CLIENT_SECRET = "fSXNM3HhRJaNM-N8gJsADjYwxvQnCMyZEh95BQpjuNRpt2j5EGVInd8UtTbmjg8dtd1qK0sb1NDmN7ClxxdanA";
    private final static String USER = "dhanushka@calcey.com";
    private final static String PW = "dhanushka";
    private final static String ENDPOINT = "evyg9ym7w1.cloudfs.io";
    private final static String ADMIN_ID = "lO8YWMqr6SLlPztLI7JiPDM-yQuosvlvLiCA_2vzdf0";
    private final static String ADMIN_SECRET = "eIdbCSpAawBmyzCcdb5c1htZqyeCeim13cFJb1knGlQ2MjZ8AGWcBrTanTlJnNyDcPdDTPBgGK9znF0HnvjRpw";

    private Session mBitcasaSession;

    @Before
    public void setUp() throws Exception {
        mBitcasaSession = new Session(ENDPOINT, CLIENT_ID, CLIENT_SECRET);
        if (!mBitcasaSession.isLinked())
            mBitcasaSession.authenticate(USER, PW);
    }

    @After
    public void tearDown() throws Exception {
        mBitcasaSession.unlink();
        mBitcasaSession = null;
    }

    /**
     * Tests the authenticate method of the session class.
     */
    @Test
    public void test1Authenticate() {
        mBitcasaSession = new Session(ENDPOINT, CLIENT_ID, CLIENT_SECRET);
        try {
            mBitcasaSession.authenticate(USER, PW);
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
        mBitcasaSession = new Session(ENDPOINT, CLIENT_ID, CLIENT_SECRET);
        try {
            mBitcasaSession.authenticate(USER, PW);
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
        mBitcasaSession = new Session(ENDPOINT, CLIENT_ID, CLIENT_SECRET);
        try {
            mBitcasaSession.authenticate(USER, PW);
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
        mBitcasaSession = new Session(ENDPOINT, CLIENT_ID, CLIENT_SECRET);
        try {
            mBitcasaSession.authenticate(USER, PW);
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
        mBitcasaSession = new Session(ENDPOINT, CLIENT_ID, CLIENT_SECRET);
        try {
            mBitcasaSession.authenticate(USER, PW);
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
        mBitcasaSession = new Session(ENDPOINT, CLIENT_ID, CLIENT_SECRET);
        try {
            mBitcasaSession.authenticate(USER, PW);
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
        mBitcasaSession = new Session(ENDPOINT, CLIENT_ID, CLIENT_SECRET);
        try {
            mBitcasaSession.authenticate(USER, PW);
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
        mBitcasaSession = new Session(ENDPOINT, CLIENT_ID, CLIENT_SECRET);
        try {
            mBitcasaSession.authenticate(USER, PW);
        } catch (IOException e) {
            assertTrue(false);
            e.printStackTrace();
        } catch (BitcasaException e) {
            assertTrue(false);
            e.printStackTrace();
        }
        mBitcasaSession.setAdminCredentials(ADMIN_ID, ADMIN_SECRET);
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
