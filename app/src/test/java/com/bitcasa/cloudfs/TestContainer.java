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

            final Folder rootFolder = BaseTest.session.filesystem().root();
            final Item[] rootItems = rootFolder.list();
            Assert.assertNotNull(rootItems);

        } catch (final IOException e) {
            e.printStackTrace();
            Assert.fail();
        } catch (final BitcasaException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }
}