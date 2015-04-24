package com.bitcasa.cloudfs;

import com.bitcasa.cloudfs.exception.BitcasaException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.io.IOException;

@RunWith(RobolectricTestRunner.class)

public class TestContainer extends BaseTest {

    @Test
    public void testList() {
        try {

            Folder rootFolder = session.filesystem().root();
            Item[] rootItems = rootFolder.list();
            Assert.assertNotNull(rootItems);

        } catch (IOException e) {
            e.printStackTrace();
            Assert.assertTrue(false);
        } catch (BitcasaException e) {
            e.printStackTrace();
            Assert.assertTrue(false);
        }
    }
}