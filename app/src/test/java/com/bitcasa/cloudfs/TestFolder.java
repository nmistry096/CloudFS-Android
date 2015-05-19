package com.bitcasa.cloudfs;

import android.util.Log;

import com.bitcasa.cloudfs.Utils.BitcasaProgressListener;
import com.bitcasa.cloudfs.Utils.BitcasaRESTConstants;
import com.bitcasa.cloudfs.exception.BitcasaException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@RunWith(RobolectricTestRunner.class)

public class TestFolder extends BaseTest {

    private static Folder testFolder;
    private File textFile;
    private final String uploadFileName = "Test.txt";
    private final String text = "Test file CloudFS Android ADK.";
    private Item[] testFolderItems;
    private static final String folderName = "SDKCreateFolderTest";

    @Before
    public void setUp() {
        try {
            final Folder sdkTestFolder = BaseTest.getSDKTestFolder();
            TestFolder.testFolder = sdkTestFolder.createFolder(TestFolder.folderName,
                    BitcasaRESTConstants.Exists.OVERWRITE);
        } catch (final IOException e) {
            e.printStackTrace();
        } catch (final BitcasaException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void test1CreateFolder() {
        Assert.assertEquals(TestFolder.folderName, TestFolder.testFolder.getName());
    }

    /**
     * Test the upload method of the session class.
     * Upload to root.
     */
    @Test
    public void test2Upload() {
        //upload to root
        final BitcasaProgressListener listener = new BitcasaProgressListener() {

            @Override
            public void onProgressUpdate(final String file, final int percentage,
                                         final ProgressAction action) {
                Log.d("test Upload", file + " percentage: " + percentage);

            }

            @Override
            public void canceled(final String file, final ProgressAction action) {

            }

        };

        Item meta = null;
        try {
            final String folderName = "SDKCreateFolderTest";
            final Folder sdkTestFolder = BaseTest.getSDKTestFolder();
            TestFolder.testFolder = sdkTestFolder.createFolder(folderName,
                    BitcasaRESTConstants.Exists.OVERWRITE);

            this.textFile = this.temporaryFolder.newFile(this.uploadFileName);
            final BufferedWriter output = new BufferedWriter(new FileWriter(this.textFile));
            output.write(this.text);
            output.close();
            com.bitcasa.cloudfs.File uploadedFile1 = TestFolder.testFolder.upload(this.textFile.getAbsolutePath(), listener, BitcasaRESTConstants.Exists.OVERWRITE);
            Assert.assertNotNull(uploadedFile1);


            //Gets the uploaded file by listing.
            this.testFolderItems = TestFolder.testFolder.list();
            for (final Item item : this.testFolderItems) {
                if (item.getName().equalsIgnoreCase(this.uploadFileName)) {
                    meta = item;
                    break;
                }
            }
            Assert.assertNotNull(meta);

            Folder testFolderLevel2 = TestFolder.testFolder.createFolder("testFolderLevel2", BitcasaRESTConstants.Exists.OVERWRITE);
            com.bitcasa.cloudfs.File uploadedFile2 = testFolderLevel2.upload(this.textFile.getAbsolutePath(),listener, BitcasaRESTConstants.Exists.OVERWRITE);
            Assert.assertNotNull(uploadedFile2);
        } catch (final IOException e) {
            Assert.fail();
            e.printStackTrace();
        } catch (final BitcasaException e) {
            Assert.fail();
            e.printStackTrace();
        }



    }
}