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
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@RunWith(RobolectricTestRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestShare extends BaseTest {

    private static final String SHARE_PW = "hello";
    private static final String NEW_SHARE_PW = "world";
    private Folder testFolder;
    private static Share sharedFolder;
    private File textFile;
    private final String uploadFileName = "Test.txt";
    private final String text = "Test file CloudFS Android ADK.";

    @Before
    public void setUp() throws IOException, BitcasaException {
        final BitcasaProgressListener listener = new BitcasaProgressListener() {

            @Override
            public void onProgressUpdate(final String file, final int percentage,
                                         final BitcasaProgressListener.ProgressAction action) {
                Log.d("test Upload", file + " percentage: " + percentage);

            }

            @Override
            public void canceled(final String file, final BitcasaProgressListener.ProgressAction action) {

            }

        };

        try {

            // Create folder to be shared.
            final String folderName = "SDKShareFolderTest";
            this.sdkTestFolder = BaseTest.getSDKTestFolder();
            this.testFolder = this.sdkTestFolder.createFolder(folderName,
                    BitcasaRESTConstants.Exists.OVERWRITE);

            Assert.assertEquals(folderName, this.testFolder.getName());

            // Create file to be uploaded and upload it to the test folder.
            this.textFile = this.temporaryFolder.newFile(this.uploadFileName);
            final BufferedWriter output = new BufferedWriter(new FileWriter(this.textFile));
            output.write(this.text);
            output.close();
            this.testFolder.upload(this.textFile.getAbsolutePath(), listener, BitcasaRESTConstants.Exists.OVERWRITE);
            Folder level2Folder = this.testFolder.createFolder("sharedFolder2",BitcasaRESTConstants.Exists.OVERWRITE);
            level2Folder.upload(this.textFile.getAbsolutePath(), listener, BitcasaRESTConstants.Exists.OVERWRITE);


        } catch (final IOException e) {
            Assert.fail();
            e.printStackTrace();
        } catch (final BitcasaException e) {
            Assert.fail();
            e.printStackTrace();
        }

        sharedFolder = BaseTest.session.getRestAdapter().createShare(this.testFolder.getPath(), TestShare.SHARE_PW);
    }

    /**
     * Test the create share method of the share class.
     */
    @Test
    public void test1CreateShare() {
        try {
            sharedFolder = BaseTest.session.getRestAdapter().createShare(this.testFolder.getPath(), TestShare.SHARE_PW);
            junit.framework.Assert.assertNotNull(sharedFolder);
        } catch (final IOException e) {
            e.printStackTrace();
            Assert.fail();
        } catch (final BitcasaException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * Test the list share method of the share class.
     */
    @Test
    public void test2ListShare() {
        try {
            final Item[] sItems = sharedFolder.list();
            Assert.assertNotNull(sItems);

            Folder shareFolder = null;
            for (final Item item : sItems) {
                if (item.getType().equals(Item.FileType.FOLDER)) {
                    shareFolder = (Folder)item;
                }
            }

            Item[] shareFolderItems = shareFolder.list();
            Assert.assertNotNull(shareFolderItems);

        } catch (final IOException e) {
            Assert.fail();
            e.printStackTrace();
        } catch (final BitcasaException e) {
            Assert.fail();
            e.printStackTrace();
        }
    }

    /**
     * Test the receive share method of the share class.
     */
    @Test
    public void test3ReceiveShare() {
        try {
            final Item[] items = sharedFolder.receive(this.sdkTestFolder.getPath(), BitcasaRESTConstants.Exists.OVERWRITE);
            Assert.assertNotNull(items);
        } catch (final IOException e) {
            Assert.fail();
            e.printStackTrace();
        } catch (final BitcasaException e) {
            Assert.fail();
            e.printStackTrace();
        }
    }

    /**
     * Test the set password method of the share class.
     */
    @Test
    public void test4SetName() {
        final boolean success;
        final String name = "ShareFolder";
        try {
            success = sharedFolder.setName(name, TestShare.SHARE_PW);
            junit.framework.Assert.assertTrue(success);
        } catch (final IOException e) {
            Assert.fail();
            e.printStackTrace();
        } catch (final BitcasaException e) {
            Assert.fail();
            e.printStackTrace();
        }
    }

    /**
     * Test the set password method of the share class.
     */
    @Test
    public void test5SetPassword() {
        final boolean success;
        try {
            success = sharedFolder.setPassword(TestShare.NEW_SHARE_PW, TestShare.SHARE_PW);
            Assert.assertTrue(success);
        } catch (final IOException e) {
            Assert.fail();
            e.printStackTrace();
        } catch (final BitcasaException e) {
            Assert.fail();
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
        } catch (final BitcasaException e) {
            Assert.fail();
            e.printStackTrace();
        }
        Assert.assertTrue(status);
    }
}
