package com.bitcasa.cloudfs;

import com.bitcasa.cloudfs.exception.BitcasaException;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.robolectric.RobolectricTestRunner;

import java.io.IOException;

@RunWith(RobolectricTestRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestSession extends BaseTest {

    private Session mBitcasaSession;
    public static User testUser;
    public static Plan createdPlan;

    /**
     * Tests the authenticate method of the session class.
     */
    @Test
    public void test1Authenticate() {
        this.mBitcasaSession = new Session(BaseTest.cloudfsEndpoint, BaseTest.clientId, BaseTest.clientSecret);
        try {
            this.mBitcasaSession.authenticate(BaseTest.username, BaseTest.password);
        } catch (final IOException e) {
            Assert.fail();
            e.printStackTrace();
        } catch (final BitcasaException e) {
            Assert.fail();
            e.printStackTrace();
        }
    }

    /**
     * Tests the is linked method of the session class.
     */
    @Test
    public void test2IsLinked() {
        this.mBitcasaSession = new Session(BaseTest.cloudfsEndpoint, BaseTest.clientId, BaseTest.clientSecret);
        try {
            this.mBitcasaSession.authenticate(BaseTest.username, BaseTest.password);
        } catch (final IOException e) {
            Assert.fail();
            e.printStackTrace();
        } catch (final BitcasaException e) {
            Assert.fail();
            e.printStackTrace();
        }
        try {
            junit.framework.Assert.assertTrue(this.mBitcasaSession.isLinked());
        } catch (BitcasaException e) {
            Assert.fail();
            e.printStackTrace();
        }
    }

    /**
     * Tests the unlink method of the session class.
     */
    @Test
    public void test3Unlink() {
        this.mBitcasaSession = new Session(BaseTest.cloudfsEndpoint, BaseTest.clientId, BaseTest.clientSecret);
        try {
            this.mBitcasaSession.authenticate(BaseTest.username, BaseTest.password);
        } catch (final IOException e) {
            Assert.fail();
            e.printStackTrace();
        } catch (final BitcasaException e) {
            Assert.fail();
            e.printStackTrace();
        }
        this.mBitcasaSession.unlink();
        try {
            Assert.assertTrue(!this.mBitcasaSession.isLinked());
        } catch (BitcasaException e) {
            Assert.fail();
            e.printStackTrace();
        }
    }

    /**
     * Tests the user method of the session class.
     */
    @Test
    public void test4User() {
        this.mBitcasaSession = new Session(BaseTest.cloudfsEndpoint, BaseTest.clientId, BaseTest.clientSecret);
        try {
            this.mBitcasaSession.authenticate(BaseTest.username, BaseTest.password);
        } catch (final IOException e) {
            Assert.fail();
            e.printStackTrace();
        } catch (final BitcasaException e) {
            Assert.fail();
            e.printStackTrace();
        }

        try {
            junit.framework.Assert.assertNotNull(this.mBitcasaSession.user());
        } catch (final BitcasaException e) {
            Assert.fail();
            e.printStackTrace();
        }
    }

    /**
     * Tests the account method of the session class.
     */
    @Test
    public void test5Account() {
        this.mBitcasaSession = new Session(BaseTest.cloudfsEndpoint, BaseTest.clientId, BaseTest.clientSecret);
        try {
            this.mBitcasaSession.authenticate(BaseTest.username, BaseTest.password);
        } catch (final IOException e) {
            Assert.fail();
            e.printStackTrace();
        } catch (final BitcasaException e) {
            Assert.fail();
            e.printStackTrace();
        }
        try {
            Assert.assertNotNull(this.mBitcasaSession.account());
        } catch (final BitcasaException e) {
            Assert.fail();
            e.printStackTrace();
        }
    }

    /**
     * Tests the Filesystem method of the session class.
     */
    @Test
    public void test6FileSystem() {
        this.mBitcasaSession = new Session(BaseTest.cloudfsEndpoint, BaseTest.clientId, BaseTest.clientSecret);
        try {
            this.mBitcasaSession.authenticate(BaseTest.username, BaseTest.password);
        } catch (final IOException e) {
            Assert.fail();
            e.printStackTrace();
        } catch (final BitcasaException e) {
            Assert.fail();
            e.printStackTrace();
        }
        Assert.assertNotNull(this.mBitcasaSession.filesystem());
    }

    /**
     * Tests the action history method of the session class.
     */
    @Test
    public void test7ActionHistory() {
        this.mBitcasaSession = new Session(BaseTest.cloudfsEndpoint, BaseTest.clientId, BaseTest.clientSecret);
        try {
            this.mBitcasaSession.authenticate(BaseTest.username, BaseTest.password);
        } catch (final IOException e) {
            Assert.fail();
            e.printStackTrace();
        } catch (final BitcasaException e) {
            Assert.fail();
            e.printStackTrace();
        }
        try {
            final ActionHistory actionHistory = this.mBitcasaSession.actionHistory(9, 10);
            Assert.assertNotNull(actionHistory);
        } catch (final IOException e) {
            Assert.fail();
            e.printStackTrace();
        } catch (final BitcasaException e) {
            Assert.fail();
            e.printStackTrace();
        }
    }

    /**
     * Tests the create user method of the session class.
     */
    @Test
    public void test8UserCreate() {
        this.mBitcasaSession = new Session(BaseTest.cloudfsEndpoint, BaseTest.clientId, BaseTest.clientSecret);
        try {
            this.mBitcasaSession.authenticate(BaseTest.username, BaseTest.password);
        } catch (final IOException e) {
            Assert.fail();
            e.printStackTrace();
        } catch (final BitcasaException e) {
            Assert.fail();
            e.printStackTrace();
        }
        this.mBitcasaSession.setAdminCredentials(BaseTest.adminId, BaseTest.adminSecret);
        final String email = System.currentTimeMillis() + "@unique.com";

        try {
            testUser = this.mBitcasaSession.createAccount(email, "123456", email, "First", "Last", false);
        } catch (final IOException e) {
            e.printStackTrace();
        } catch (final BitcasaException e) {
            e.printStackTrace();
        }
        Assert.assertNotNull(testUser);
        junit.framework.Assert.assertEquals(testUser.getUsername(), email);
    }

    @Test
    public void test9CreatePlan(){
        this.mBitcasaSession = new Session(BaseTest.cloudfsEndpoint, BaseTest.clientId, BaseTest.clientSecret);
        try {
            this.mBitcasaSession.authenticate(BaseTest.username, BaseTest.password);
        } catch (final IOException e) {
            Assert.fail();
            e.printStackTrace();
        } catch (final BitcasaException e) {
            Assert.fail();
            e.printStackTrace();
        }
        this.mBitcasaSession.setAdminCredentials(BaseTest.adminId, BaseTest.adminSecret);

        try {

            /*
            * WARNING!!! Only run the following set of commented code on a test environment.
            * It will delete the the account plans from your account and if there are users assigned to
            * the deleted plans it will cause complications.
            * DO NOT run it on a production environment.
            *
            */
            /*
            Plan[] plans = mBitcasaSession.listPlans();
            if(plans.length > 0) {
                for(Plan plan : plans){
                    boolean deleteStatus = mBitcasaSession.getRestAdapter().deletePlan(mBitcasaSession, plan.getId());
                }
            }
            */

            createdPlan = mBitcasaSession.createPlan("TestPlan", "1024");
            Assert.assertNotNull(createdPlan);
            mBitcasaSession.getRestAdapter().deletePlan(mBitcasaSession, createdPlan.getId());
        } catch (BitcasaException e) {
            Assert.fail();
        } catch (IOException e) {
            Assert.fail();
        }
    }

    @Test
    public void testListPlan(){
        this.mBitcasaSession = new Session(BaseTest.cloudfsEndpoint, BaseTest.clientId, BaseTest.clientSecret);
        try {
            this.mBitcasaSession.authenticate(BaseTest.username, BaseTest.password);
        } catch (final IOException e) {
            Assert.fail();
            e.printStackTrace();
        } catch (final BitcasaException e) {
            Assert.fail();
            e.printStackTrace();
        }
        this.mBitcasaSession.setAdminCredentials(BaseTest.adminId, BaseTest.adminSecret);

        try {
            Plan[] plans = mBitcasaSession.listPlans();
            Assert.assertNotNull(plans);
        } catch (BitcasaException e) {
            Assert.fail();
        }
    }

    @Test
    public void testUpdate(){
        this.mBitcasaSession = new Session(BaseTest.cloudfsEndpoint, BaseTest.clientId, BaseTest.clientSecret);
        try {
            this.mBitcasaSession.authenticate(BaseTest.username, BaseTest.password);
        } catch (final IOException e) {
            Assert.fail();
            e.printStackTrace();
        } catch (final BitcasaException e) {
            Assert.fail();
            e.printStackTrace();
        }
        this.mBitcasaSession.setAdminCredentials(BaseTest.adminId, BaseTest.adminSecret);

        try {
            User updatedUser = mBitcasaSession.updateUser(mBitcasaSession.account().getId(),
                    mBitcasaSession.user().getUsername(),"Test FirstName","Test Last Name",null);
            Assert.assertNotNull(updatedUser);
            Session testSession = new Session(BaseTest.cloudfsEndpoint, BaseTest.clientId, BaseTest.clientSecret);
            testSession.authenticate(updatedUser.getUsername(), BaseTest.password);
            User testUser = testSession.user();
            Assert.assertNotNull(testUser);

        } catch (BitcasaException e) {
            Assert.fail();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
