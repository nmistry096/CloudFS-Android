package com.bitcasa.cloudfs;

import android.util.Log;

import com.bitcasa.cloudfs.Utils.BitcasaProgressListener;
import com.bitcasa.cloudfs.Utils.BitcasaRESTConstants;
import com.bitcasa.cloudfs.Utils.BitcasaRESTConstants.Exists;
import com.bitcasa.cloudfs.Utils.BitcasaRESTConstants.VersionExists;
import com.bitcasa.cloudfs.exception.BitcasaException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

@RunWith(RobolectricTestRunner.class)
public class TestItem extends BaseTest{
    private final String uploadFileName = "Test.txt";
    private Folder rootFolder;
	private Folder sourceFolder;
	private Folder destinationFolder;

    private Item uploadedFile;
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
            this.sourceFolder = this.rootFolder.createFolder("test", Exists.OVERWRITE);
            this.destinationFolder = this.rootFolder.createFolder("destinationFolder", Exists.OVERWRITE);
            final File textFile = this.temporaryFolder.newFile(this.uploadFileName);
            final BufferedWriter output = new BufferedWriter(new FileWriter(textFile));
            final String text = "Test file CloudFS Android ADK.";
            output.write(text);
            output.close();
            this.uploadedFile = this.sourceFolder.upload(textFile.getAbsolutePath(), listener, Exists.OVERWRITE);


        }

        final Item[] trashFolder = BaseTest.session.filesystem().listTrash();

        for (final Item item : trashFolder) {
            item.delete(true,true);
        }
	}

    /**
     * Tests item class functions.
     */
    @Test
	public void test1Item() {
        try {
            junit.framework.Assert.assertNotNull(this.item);
            Assert.assertNotNull(this.item.getPath());	//AbsolutePath only apply to non-trash item
            Assert.assertNotNull(this.item.getId());
            Assert.assertNotNull(this.item.getName());
            Assert.assertNotNull(this.item.getDateContentLastModified());
            Assert.assertNotNull(this.item.getDateCreated());
            Assert.assertNotNull(this.item.getDateMetaLastModified());
            Assert.assertNotNull(this.item.getIsMirrored());
            Assert.assertNotNull(this.item.getAbsoluteParentPath());

        }catch (final RuntimeException ex){
            Assert.fail();
            ex.printStackTrace();
        }
	}

    /**
     * Tests copy function. Takes a container class.
     */
    @Test
	public void test2Copy() {
		Item copyResult = null;
		try {
			copyResult = this.uploadedFile.copy(this.destinationFolder, null, Exists.OVERWRITE);
		} catch (final IOException e) {
            Assert.fail();
			e.printStackTrace();
		} catch (final BitcasaException e) {
            Assert.fail();
            e.printStackTrace();
        }
        Assert.assertNotNull(copyResult);
        junit.framework.Assert.assertEquals(copyResult.getName(), this.uploadFileName);
	}

    /**
     * Tests move function. Takes a container class.
     */
    @Test
    public void test3Move() {
        Item moveResult = null;
        try {
            moveResult = this.uploadedFile.move(this.destinationFolder, Exists.OVERWRITE);
        } catch (final BitcasaException e) {
            e.printStackTrace();
        } catch (final IOException e) {
            Assert.fail();
            e.printStackTrace();
        }
        Assert.assertNotNull(moveResult);
        Assert.assertEquals(moveResult.getName(), this.uploadFileName);
    }

    /**
     * Tests delete to trash function.
     */
    @Test
	public void test4DeleteFileToTrash() {
        Item fileForDelete = null;
		try {
            final Item[] itemList = this.sourceFolder.list();
            for (final Item item : itemList) {
                if (item.getName().equalsIgnoreCase(this.uploadFileName)) {
                    fileForDelete = item;
                    break;
                }
            }
            assert fileForDelete != null;
            String  filePath = fileForDelete.getPath();
            final boolean testStatus = fileForDelete.delete(false, true);
            Assert.assertTrue(testStatus);
            JsonObject jsonObject = fileForDelete.getApplicationData();
            String originalPath = jsonObject.get("_bitcasa_original_path").getAsString();
            junit.framework.Assert.assertTrue(originalPath.equalsIgnoreCase(filePath));

            //File should not exist in destinationFolder.

            final Item[] destinationFileList = this.sourceFolder.list();
            for (final Item item : destinationFileList) {
                if (item.getName().equalsIgnoreCase(this.uploadFileName)) {
                    junit.framework.Assert.fail();
                    break;
                }
                junit.framework.Assert.assertTrue(true);
            }

            //File should exist in trashFolder.
            final Item[] trashFolder = BaseTest.session.filesystem().listTrash();

            Item trashedItem = null;
            for (final Item item : trashFolder) {
                if (item.getName().equalsIgnoreCase(this.uploadFileName)) {
                    trashedItem = item;
                    junit.framework.Assert.assertTrue(true);
                    break;
                }
            }

            junit.framework.Assert.assertNotNull(trashedItem);

		} catch (final IOException e) {
            Assert.fail();
			e.printStackTrace();
		} catch (final BitcasaException e) {
            Assert.fail();
			e.printStackTrace();
		}
	}

    /**
     * Tests commit delete function.
     */
    @Test
    public void test5CommitDeleteFile() {
        Item fileForDelete = null;
        try {
            final Item[] itemList = this.sourceFolder.list();
            for (final Item item : itemList) {
                if (item.getName().equalsIgnoreCase(this.uploadFileName)) {
                    fileForDelete = item;
                    break;
                }
            }
            assert fileForDelete != null;
            String  filePath = fileForDelete.getPath();
            final boolean testStatus = fileForDelete.delete(true, true);
            Assert.assertTrue(testStatus);
            JsonObject jsonObject = fileForDelete.getApplicationData();
            String originalPath = jsonObject.get("_bitcasa_original_path").getAsString();
            junit.framework.Assert.assertTrue(originalPath.equalsIgnoreCase(filePath));

            //File should not exist in destinationFolder.

            final Item[] destinationFileList = this.sourceFolder.list();
            for (final Item item : destinationFileList) {
                if (item.getName().equalsIgnoreCase(this.uploadFileName)) {
                    junit.framework.Assert.fail();
                    break;
                }
                junit.framework.Assert.assertTrue(true);
            }

            //File should exist in trashFolder.
            final Item[] trashFolder = BaseTest.session.filesystem().listTrash();

            for (final Item item : trashFolder) {
                if (item.getName().equalsIgnoreCase(this.uploadFileName)) {
                    junit.framework.Assert.fail();
                    break;
                }
                junit.framework.Assert.assertTrue(true);
            }

        } catch (final IOException e) {
            Assert.fail();
            e.printStackTrace();
        } catch (final BitcasaException e) {
            Assert.fail();
            e.printStackTrace();
        }
    }

    /**
     * Tests delete trashed item function.
     */
    @Test
	public void test6DeleteATrashedItem() {
        Item fileForDelete = null;
		try {
            final Item[] itemList = this.sourceFolder.list();
            for (final Item item : itemList) {
                if (item.getName().equalsIgnoreCase(this.uploadFileName)) {
                    fileForDelete = item;
                    break;
                }
            }
            assert fileForDelete != null;
            final boolean testStatus = fileForDelete.delete(false, false);
            Assert.assertTrue(testStatus);

            //File should exist in trash.
            Item trashItem = null;
            final FileSystem fileSystem = BaseTest.session.filesystem();
            Item[] trashItems = fileSystem.listTrash();
            for (final Item item : trashItems) {
                if (item.getName().equalsIgnoreCase(this.uploadFileName)) {
                    trashItem = item;
                    Assert.assertTrue(true);
                    break;
                }
            }
            final boolean result = trashItem.delete(true, false);
            Assert.assertTrue(result);

            trashItem = null;
            trashItems = fileSystem.listTrash();
            for (final Item item : trashItems) {
                if (item.getName().equalsIgnoreCase(this.uploadFileName)) {
                    trashItem = item;
                    Assert.fail();
                    break;
                }
            }
            Assert.assertNull(trashItem);
            
		} catch (final IOException e) {
            Assert.fail();
			e.printStackTrace();
		} catch (final BitcasaException e) {
            Assert.fail();
			e.printStackTrace();
		}
	}

    /**
     * Tests restore function. Takes a container class.
     */
    @Test
	public void test7Restore() {
        Item trashItem = null;
		try {
            Item fileForDelete = null;
            final Item[] itemList = this.sourceFolder.list();
            for (final Item item : itemList) {
                if (item.getName().equalsIgnoreCase(this.uploadFileName)) {
                    fileForDelete = item;
                    break;
                }
            }
            fileForDelete.delete(false, false);
            final Folder trashRecoverFolder = this.rootFolder.createFolder("destinationFolder",
                    BitcasaRESTConstants.Exists.OVERWRITE);
            final FileSystem fileSystem = BaseTest.session.filesystem();
            final Item[] trashItems = fileSystem.listTrash();
            for (final Item item : trashItems) {
                if (item.getName().equalsIgnoreCase(this.uploadFileName)) {
                    trashItem = item;
                }
            }
            Assert.assertNotNull(trashItem);
            final boolean restoreState = trashItem.restore(trashRecoverFolder,
                    BitcasaRESTConstants.RestoreMethod.RESCUE, null, false);
			Assert.assertTrue(restoreState);
		} catch (final UnsupportedEncodingException e) {
            Assert.fail();
			e.printStackTrace();
		} catch (final IOException e) {
            e.printStackTrace();
        } catch (final BitcasaException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test the changeAttributes function.
     */
    @Test
	public void test8ChangeAttributes() {
        final String date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        final String newName = date + ".txt";
        final HashMap<String, String> values = new HashMap<String, String>();
        values.put("name", newName);

        try {
            Assert.assertTrue(this.sourceFolder.changeAttributes(values, VersionExists.FAIL));
            Assert.assertEquals(newName, this.sourceFolder.getName());
        } catch (final BitcasaException e) {
            Assert.fail();
            e.printStackTrace();
        }
	}

    /**
     * Test the set application data function.
     */
    @Test
    public void test9SetApplicationData() {

        JsonObject jsonObject = (JsonObject) new JsonParser().parse("{\"payload\": \"test payload\"}");

        try {
            final boolean resultState = this.uploadedFile.setApplicationData(jsonObject);
            Assert.assertNotNull(resultState);
            final JsonObject resultApplicationData = this.uploadedFile.getApplicationData();
            Assert.assertNotNull(resultApplicationData);
        } catch (final BitcasaException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }
}
