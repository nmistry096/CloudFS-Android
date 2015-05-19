package com.bitcasa.cloudfs;

import com.bitcasa.cloudfs.exception.BitcasaException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

/**
 * Tests the User class methods.
 */
@RunWith(RobolectricTestRunner.class)
public class TestUser extends BaseTest {
    @Test
    public void testGetUser() {
        User user = null;
        try {
            user = BaseTest.session.user();
        } catch (final BitcasaException e) {
            e.printStackTrace();
            Assert.fail();
        } finally {
            junit.framework.Assert.assertNotNull(user);
        }

        Assert.assertNotNull(user.getEmail());
        Assert.assertNotNull(user.getFirstName());
        Assert.assertNotNull(user.getLastName());
        Assert.assertNotNull(user.getId());
        Assert.assertNotNull(user.getUsername());
        junit.framework.Assert.assertEquals(BaseTest.username, user.getUsername());
        Assert.assertNotNull(user.getLastLogin());
        junit.framework.Assert.assertTrue(user.getLastLogin().getTime() > 0);
        Assert.assertNotNull(user.getCreatedAt());
        Assert.assertTrue(user.getCreatedAt().getTime() > 0);
    }
}

