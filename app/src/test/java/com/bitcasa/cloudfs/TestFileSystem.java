package com.bitcasa.cloudfs;

import com.bitcasa.cloudfs.exception.BitcasaException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.io.IOException;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;


@RunWith(RobolectricTestRunner.class)

public class TestFileSystem extends BaseTest {

    //private Item mFile;
    private Container mContainer;
    private Share shareItem;
    private FileSystem fileSystem;
    private File fileToBeDownloaded;

    /**
     * Sets up the required conditions for testing the FileSystem class
     */
    @Before
    public void setUp() {
        fileSystem = new FileSystem(session.getRestAdapter());
        Item[] result = null;
        try {
            result = session.getRestAdapter().getList(null, 0, 1, null);
            assertNotNull(result);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BitcasaException e) {
            e.printStackTrace();
        }

        for (int i = 1; i < result.length; i++) {
            if (result[i].getType().equals(Item.FileType.FILE)) {
                fileToBeDownloaded = (File) result[i];
                break;
            }
        }
    }


    /**
     * Tests the root method
     */
    @Test
    public void testRoot() {
        try {
            assertNotNull(fileSystem.root());
        } catch (IOException e) {
            assertTrue(false);
        } catch (BitcasaException e) {
            assertTrue(false);
        }
    }

    /**
     * Test list trash method
     */
    @Test
    public void testListTrash() {
        try {
            assertNotNull(fileSystem.listTrash());
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    /**
     * Test get item method
     */
    @Test
    public void testGetItem() throws IOException{

        try {
            assertNotNull(fileSystem.getItem(fileToBeDownloaded.getPath()));
        } catch (BitcasaException e) {
            assertTrue(false);
        }
    }

    /**
     * Test list share method
     */
    @Test
    public void testListShares() {
        try {
            assertNotNull(fileSystem.listShares());
        } catch (IOException e) {
            assertTrue(false);
        } catch (BitcasaException e) {
            assertTrue(false);
        }
    }

    /**
     * Test create share method
     */
    @Test
    public void testcreateRetrieveShare() {
        try {
            shareItem = fileSystem.createShare(fileToBeDownloaded.getPath(), password);
            assertNotNull(shareItem);
            assertNotNull(fileSystem.retrieveShare(shareItem.getShareKey(), password));
        } catch (IOException e) {
            assertTrue(false);
        } catch (BitcasaException e) {
            assertTrue(false);
        }
    }

}
