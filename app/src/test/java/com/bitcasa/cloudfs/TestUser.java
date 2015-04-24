package com.bitcasa.cloudfs;

import com.bitcasa.cloudfs.exception.BitcasaException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.io.IOException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * Tests the User class methods.
 */
@RunWith(RobolectricTestRunner.class)
public class TestUser extends BaseTest {
    @Test
    public void testGetUser() {
        User user = null;
        try {
            user = session.user();
        } catch (IOException e) {
            e.printStackTrace();
            assertTrue(false);
        } catch (BitcasaException e) {
            e.printStackTrace();
            assertTrue(false);
        } finally {
            assertNotNull(user);
        }

        assertNotNull(user.getEmail());
        assertNotNull(user.getFirstName());
        assertNotNull(user.getLastName());
        assertNotNull(user.getId());
        assertNotNull(user.getUsername());
        assertEquals(username, user.getUsername());
        assertNotNull(user.getLastLogin());
        assertTrue(user.getLastLogin().getTime() > 0);
        assertNotNull(user.getCreatedAt());
        assertTrue(user.getCreatedAt().getTime() > 0);
    }
}

