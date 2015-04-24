package com.bitcasa.cloudfs;

import android.util.Log;

import com.bitcasa.cloudfs.Utils.BitcasaProgressListener;
import com.bitcasa.cloudfs.Utils.BitcasaRESTConstants;
import com.bitcasa.cloudfs.exception.BitcasaException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

@RunWith(RobolectricTestRunner.class)

public class TestFolder extends BaseTest {

    private static Folder testFolder;
    private java.io.File textFile;
    private String uploadFileName = "Test.txt";
    private String text = "Test file CloudFS Android ADK.";
    private Item[] testFolderItems;

    @Test
    public void test1CreateFolder() {
        try {
            String folderName = "SDKCreateFolderTest";
            Folder sdkTestFolder = this.getSDKTestFolder();
            testFolder = sdkTestFolder.createFolder(folderName,
                    BitcasaRESTConstants.Exists.OVERWRITE);

            Assert.assertEquals(folderName, testFolder.getName());
        } catch (IOException e) {
            e.printStackTrace();
            Assert.assertTrue(false);
        } catch (BitcasaException e) {
            e.printStackTrace();
            Assert.assertTrue(false);
        }
    }

    /**
     * Test the upload method of the session class.
     * Upload to root.
     */
    @Test
    public void test2Upload() {
        //upload to root
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

        Item meta = null;
        try {

            textFile = temporaryFolder.newFile(uploadFileName);
            BufferedWriter output = new BufferedWriter(new FileWriter(textFile));
            output.write(text);
            output.close();
            testFolder.upload(textFile.getAbsolutePath(), listener, BitcasaRESTConstants.Exists.OVERWRITE);

            //Gets the uploaded file.
            testFolderItems = testFolder.list();
            for (Item item : testFolderItems) {
                if (item.getName().equalsIgnoreCase(uploadFileName)) {
                    meta = item;
                    break;
                }
            }

        } catch (IOException e) {
            Assert.assertTrue(false);
            e.printStackTrace();
        } catch (BitcasaException e) {
            Assert.assertTrue(false);
            e.printStackTrace();
        }

        Assert.assertNotNull(meta);

    }
}
