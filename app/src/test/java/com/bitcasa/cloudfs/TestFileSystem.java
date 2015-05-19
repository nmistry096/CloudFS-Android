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
import java.io.FileWriter;
import java.io.IOException;


@RunWith(RobolectricTestRunner.class)

public class TestFileSystem extends BaseTest {

    private Share shareItem;
    private FileSystem fileSystem;
    private File uploadedFile;
    private final String uploadFileName = "Test.txt";
    private Folder rootFolder;
    private Folder sourceFolder;
    private Item item;

    /**
     * Sets up the required conditions for testing the FileSystem class
     */
    @Before
    public void setUp() throws BitcasaException, IOException {
        this.fileSystem = new FileSystem(BaseTest.session.getRestAdapter());
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


    /**
     * Tests the root method
     */
    @Test
    public void testRoot() {
        try {
            Assert.assertNotNull(this.fileSystem.root());
        } catch (final BitcasaException e) {
            Assert.fail();
        }
    }

    /**
     * Test list trash method
     */
    @Test
    public void testListTrash() {
        try {
            Item [] trashItems = this.fileSystem.listTrash();
            Assert.assertNotNull(trashItems);

            Folder trashFolder = null;
            for (final Item item : trashItems) {
                if (item.getType().equals(Item.FileType.FOLDER)) {
                    trashFolder = (Folder)item;
                }
            }

            Item[] trashFolderItems = trashFolder.list();
            Assert.assertNotNull(trashFolderItems);

        } catch (final BitcasaException e) {
            e.printStackTrace();
        } catch (final IOException e) {
            e.printStackTrace();
        } catch (final RuntimeException e) {
            Assert.fail();
        }
    }

    /**
     * Test get item method
     */
    @Test
    public void testGetItem() throws IOException{

        try {
            Assert.assertNotNull(this.fileSystem.getItem(this.uploadedFile.getPath()));
        } catch (final BitcasaException e) {
            Assert.fail();
        }
    }

    /**
     * Test list share method
     */
    @Test
    public void testListShares() {
        try {
            Assert.assertNotNull(this.fileSystem.listShares());
        } catch (final BitcasaException e) {
            Assert.fail();
        }
    }

    /**
     * Test create share method
     */
    @Test
    public void testCreateRetrieveShare() {
        try {
            this.shareItem = this.fileSystem.createShare(this.uploadedFile.getPath(), BaseTest.password);
            Assert.assertNotNull(this.shareItem);
            Assert.assertNotNull(this.fileSystem.retrieveShare(this.shareItem.getShareKey(), BaseTest.password));
        } catch (final IOException e) {
            Assert.fail();
        } catch (final BitcasaException e) {
            Assert.fail();
        }
    }

    /**
     * Test create share method
     */
    @Test
    public void test2CreateRetrieveShare() {
        try {
            String [] paths = {this.uploadedFile.getPath(),this.sourceFolder.getPath()};
            this.shareItem = this.fileSystem.createShare(paths, BaseTest.password);
            Assert.assertNotNull(this.shareItem);
            Assert.assertNotNull(this.fileSystem.retrieveShare(this.shareItem.getShareKey(), BaseTest.password));
        } catch (final IOException e) {
            Assert.fail();
        } catch (final BitcasaException e) {
            Assert.fail();
        }
    }

}
