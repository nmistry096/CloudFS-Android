package com.bitcasa.cloudfs;

import android.util.Log;

import com.bitcasa.cloudfs.Utils.BitcasaProgressListener;
import com.bitcasa.cloudfs.Utils.BitcasaRESTConstants;
import com.bitcasa.cloudfs.exception.BitcasaException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.robolectric.RobolectricTestRunner;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestShare extends BaseTest {

    private final static String SHARE_PW = "hello";
    private final static String NEW_SHARE_PW = "world";
    private static Folder sdkTestFolder;
    private static Folder testFolder;
    private static Share sharedFolder;
    private java.io.File textFile;
    private String uploadFileName = "Test.txt";
    private String text = "Test file CloudFS Android ADK.";

    @Before
    public void setUp() throws IOException, BitcasaException {
        BitcasaProgressListener listener = new BitcasaProgressListener() {

            @Override
            public void onProgressUpdate(String file, int percentage,
                                         ProgressAction action) {
                Log.d("test Upload", file + " percentage: " + percentage);

            }

            @Override
            public void canceled(String file, ProgressAction action) {

            }

        };

        try {

            // Create folder to be shared.
            String folderName = "SDKShareFolderTest";
            sdkTestFolder = this.getSDKTestFolder();
            testFolder = sdkTestFolder.createFolder(folderName,
                    BitcasaRESTConstants.Exists.OVERWRITE);

            Assert.assertEquals(folderName, testFolder.getName());

            // Create file to be uploaded and upload it to the test folder.
            textFile = temporaryFolder.newFile(uploadFileName);
            BufferedWriter output = new BufferedWriter(new FileWriter(textFile));
            output.write(text);
            output.close();
            testFolder.upload(textFile.getAbsolutePath(), listener, BitcasaRESTConstants.Exists.OVERWRITE);


        } catch (IOException e) {
            Assert.assertTrue(false);
            e.printStackTrace();
        } catch (BitcasaException e) {
            Assert.assertTrue(false);
            e.printStackTrace();
        }
    }

    /**
     * Test the create share method of the share class.
     */
    @Test
    public void test1CreateShare() {
        try {
            sharedFolder = session.getRestAdapter().createShare(testFolder.getPath(), SHARE_PW);
            assertNotNull(sharedFolder);
        } catch (IOException e) {
            e.printStackTrace();
            Assert.assertTrue(false);
        } catch (BitcasaException e) {
            e.printStackTrace();
            Assert.assertTrue(false);
        }
    }

    /**
     * Test the list share method of the share class.
     */
    @Test
    public void test2ListShare() {
        try {
            Item[] sItems = sharedFolder.list();
            assertNotNull(sItems);
        } catch (IOException e) {
            assertTrue(false);
            e.printStackTrace();
        } catch (BitcasaException e) {
            assertTrue(false);
            e.printStackTrace();
        }
    }

    /**
     * Test the receive share method of the share class.
     */
    @Test
    public void test3ReceiveShare() {
        try {
            Item[] items = sharedFolder.receive(sdkTestFolder.getPath(), BitcasaRESTConstants.Exists.OVERWRITE);
            assertNotNull(items);
        } catch (IOException e) {
            assertTrue(false);
            e.printStackTrace();
        } catch (BitcasaException e) {
            assertTrue(false);
            e.printStackTrace();
        }
    }

    /**
     * Test the set password method of the share class.
     */
    @Test
    public void test4SetName() {
        boolean success;
        String name = "ShareFolder";
        try {
            success = sharedFolder.setName(name, SHARE_PW);
            assertTrue(success);
        } catch (IOException e) {
            assertTrue(false);
            e.printStackTrace();
        } catch (BitcasaException e) {
            assertTrue(false);
            e.printStackTrace();
        }
    }

    /**
     * Test the set password method of the share class.
     */
    @Test
    public void test5SetPassword() {
        boolean success;
        try {
            success = sharedFolder.setPassword(NEW_SHARE_PW, SHARE_PW);
            assertTrue(success);
        } catch (IOException e) {
            assertTrue(false);
            e.printStackTrace();
        } catch (BitcasaException e) {
            assertTrue(false);
            e.printStackTrace();
        }
    }

    /**
     * Test the delete share method of the share class.
     */
    @Test
    public void test6DeleteShare() {
        boolean status = false;
        try {
            status = sharedFolder.delete();
        } catch (BitcasaException e) {
            assertTrue(false);
            e.printStackTrace();
        }
        assertTrue(status);
    }
}
