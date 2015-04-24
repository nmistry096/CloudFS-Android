package com.bitcasa.cloudfs;

import com.bitcasa.cloudfs.Utils.BitcasaRESTConstants;
import com.bitcasa.cloudfs.exception.BitcasaException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;


@RunWith(RobolectricTestRunner.class)

public class TestFile extends BaseTest {

    public File fileToBeDownloaded;

    @Before
    public void setUp() {
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

    @Test
    public void testDownloadUrl(){
        try {
           assertNotNull(fileToBeDownloaded.downloadUrl());
        } catch (IOException e) {
            assertTrue(false);
        } catch (BitcasaException e) {
            assertTrue(false);
        }
    }


    //@Test
    public void testGetExtension(){
        fileToBeDownloaded.getExtension();
    }

    //@Test
    public void testGetMime(){
        fileToBeDownloaded.getMime();
    }

    //@Test
    public void testGetSize(){
        fileToBeDownloaded.getSize();
    }

   // @Test
    public void testRead() throws InterruptedException, BitcasaException, IOException {
        fileToBeDownloaded.read();
    }

    @Test
    public void testDownload() throws BitcasaException, IOException {
        String uploadFileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".txt";
        Folder testFolder = this.getSDKTestFolder();
        File file = null;

        java.io.File textFile = temporaryFolder.newFile(uploadFileName);
        BufferedWriter output = new BufferedWriter(new FileWriter(textFile));
        output.write(uploadFileName);
        output.close();
        testFolder.upload(textFile.getAbsolutePath(), null, BitcasaRESTConstants.Exists.OVERWRITE);

        //Gets the uploaded file.
        Item[] testFolderItems = testFolder.list();
        for (Item item : testFolderItems) {
            if (item.getName().equalsIgnoreCase(uploadFileName)) {
                file = (File)item;
                break;
            }
        }

        textFile.delete();
        String downloadFilePath = temporaryFolder.newFile(uploadFileName).getAbsolutePath();
        if (textFile.delete()) {
            file.download(downloadFilePath, null);
        }

        java.io.File downloadedFile = new java.io.File(downloadFilePath);
        StringBuilder text = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(downloadedFile));
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
