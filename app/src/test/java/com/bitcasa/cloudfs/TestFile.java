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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


@RunWith(RobolectricTestRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestFile extends BaseTest {

    public File uploadedFile;
    private final String uploadFileName = "Test.txt";
    private Folder rootFolder;
    private Folder sourceFolder;
    private Item item;

    @Before
    public void setUp() throws BitcasaException, IOException {
        this.item = BaseTest.getSDKTestFolder();
        if(this.item.getType().equals(Item.FileType.FOLDER)){
            this.rootFolder = BaseTest.getSDKTestFolder();
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
            this.sourceFolder = this.rootFolder.createFolder("sourceFolder", BitcasaRESTConstants.Exists.OVERWRITE);
            final java.io.File textFile = this.temporaryFolder.newFile(this.uploadFileName);
            final BufferedWriter output = new BufferedWriter(new FileWriter(textFile));
            final String text = "Test file CloudFS Android ADK.";
            output.write(text);
            output.close();
            this.uploadedFile = this.sourceFolder.upload(textFile.getAbsolutePath(), listener, BitcasaRESTConstants.Exists.OVERWRITE);
        }
    }

    @Test
    public void testDownloadUrl(){
        try {
           Assert.assertNotNull(this.uploadedFile.downloadUrl());
        } catch (final BitcasaException e) {
            Assert.fail();
        }
    }


    @Test
    public void testGetExtension(){
        this.uploadedFile.getExtension();
    }

    @Test
    public void testGetMime(){this.uploadedFile.getMime();}

    @Test
    public void testGetSize(){
        this.uploadedFile.getSize();
    }

    @Test
    public void testRead() throws InterruptedException, BitcasaException, IOException {
        this.uploadedFile.read();
    }

    @Test
    public void testDownload() throws BitcasaException, IOException {
        final String uploadFileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".txt";
        final Folder testFolder = BaseTest.getSDKTestFolder();
        File file = null;

        final java.io.File textFile = this.temporaryFolder.newFile(uploadFileName);
        final BufferedWriter output = new BufferedWriter(new FileWriter(textFile));
        output.write(uploadFileName);
        output.close();
        testFolder.upload(textFile.getAbsolutePath(), null, BitcasaRESTConstants.Exists.OVERWRITE);

        //Gets the uploaded file.
        final Item[] testFolderItems = testFolder.list();
        for (final Item item : testFolderItems) {
            if (item.getName().equalsIgnoreCase(uploadFileName)) {
                file = (File)item;
                break;
            }
        }

        textFile.delete();
        final String downloadFilePath = this.temporaryFolder.newFile(uploadFileName).getAbsolutePath();
        if (textFile.delete()) {
            file.download(downloadFilePath, null);
        }

        final java.io.File downloadedFile = new java.io.File(downloadFilePath);
        final StringBuilder text = new StringBuilder();
        final BufferedReader reader = new BufferedReader(new FileReader(downloadedFile));
        String line;

        while ((line = reader.readLine()) != null) {
            text.append(line);
            text.append('\n');
        }
        reader.close();
        text.setLength(text.length() - 1);

        Assert.assertEquals(uploadFileName, text.toString());
        Assert.assertNotNull(textFile);
    }
}
