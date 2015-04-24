package com.bitcasa.cloudfs;

import android.util.Log;

import com.bitcasa.cloudfs.Utils.BitcasaProgressListener;
import com.bitcasa.cloudfs.Utils.BitcasaRESTConstants;
import com.bitcasa.cloudfs.Utils.BitcasaRESTConstants.Exists;
import com.bitcasa.cloudfs.Utils.BitcasaRESTConstants.VersionExists;
import com.bitcasa.cloudfs.exception.BitcasaException;
import com.bitcasa.cloudfs.model.ApplicationData;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;


import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class TestItem extends BaseTest{
    private String uploadFileName = "Test.txt";
    private Folder rootFolder;
	private Folder sourceFolder;
	private Folder destinationFolder;
    private Item uploadedFile;
	private Item item;

    @Before
	public void setUp() throws Exception {
        item = this.getSDKTestFolder();
        if(item.getType().equals(Item.FileType.FOLDER)){
            rootFolder = this.getSDKTestFolder();
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
            sourceFolder = rootFolder.createFolder("CFSAndroidSDKItemTest", Exists.OVERWRITE);
            destinationFolder = rootFolder.createFolder("destinationFolder", Exists.OVERWRITE);
            java.io.File textFile = temporaryFolder.newFile(uploadFileName);
            BufferedWriter output = new BufferedWriter(new FileWriter(textFile));
            String text = "Test file CloudFS Android ADK.";
            output.write(text);
            output.close();
            sourceFolder.upload(textFile.getAbsolutePath(), listener, Exists.OVERWRITE);

            //Gets the uploaded file.
            Item[] sourceFolderItems = sourceFolder.list();
            for (Item item : sourceFolderItems) {
                if (item.getName().equalsIgnoreCase(uploadFileName)) {
                    uploadedFile = item;
                    break;
                }
            }
        }
	}

    /**
     * Tests item class functions.
     */
    @Test
	public void test1Item() {
        try {
            assertNotNull(item);
            assertNotNull(item.getPath());	//AbsolutePath only apply to non-trash item
            assertNotNull(item.getId());
            assertNotNull(item.getName());
            assertNotNull(item.getDateContentLastModified());
            assertNotNull(item.getDateCreated());
            assertNotNull(item.getDateMetaLastModified());
            assertNotNull(item.getIsMirrored());
            assertNotNull(item.getAbsoluteParentPath());

        }catch (Exception ex){
            assertTrue(false);
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
			copyResult = uploadedFile.copy(destinationFolder, Exists.OVERWRITE);
		} catch (IOException e) {
			assertTrue(false);
			e.printStackTrace();
		} catch (BitcasaException e) {
            assertTrue(false);
            e.printStackTrace();
        }
        assertNotNull(copyResult);
        assertEquals(copyResult.getName(), uploadFileName);
	}

    /**
     * Tests move function. Takes a container class.
     */
    @Test
    public void test3Move() {
        Item moveResult = null;
        try {
            moveResult = uploadedFile.move(destinationFolder, Exists.OVERWRITE);
        } catch (BitcasaException e) {
            e.printStackTrace();
        } catch (IOException e) {
            assertTrue(false);
            e.printStackTrace();
        }
        assertNotNull(moveResult);
        assertEquals(moveResult.getName(), uploadFileName);
    }

    /**
     * Tests delete function.
     */
    @Test
	public void test4DeleteAFile() {
        Item fileForDelete = null;
		try {
            Item[] itemList = sourceFolder.list();
            for (Item item : itemList) {
                if (item.getName().equalsIgnoreCase(uploadFileName)) {
                    fileForDelete = item;
                    break;
                }
            }
            assert fileForDelete != null;
            boolean testStatus = fileForDelete.delete(false, false);
            assertTrue(testStatus);

            //File should not exist in destinationFolder.
            Item[] destinationFileList = sourceFolder.list();
            for (Item item : destinationFileList) {
                if (item.getName().equalsIgnoreCase(uploadFileName)) {
                    assertFalse(true);
                    break;
                }
                assertTrue(true);
            }
		} catch (IOException e) {
			assertTrue(false);
			e.printStackTrace();
		} catch (BitcasaException e) {
			assertTrue(false);
			e.printStackTrace();
		}
	}

    /**
     * Tests restore function. Takes a container class.
     */
    @Test
	public void test5Restore() {
        Item trashItem = null;
		try {
            Folder trashRecoverFolder = rootFolder.createFolder("destinationFolder",
                    Exists.OVERWRITE);
            FileSystem fileSystem = session.filesystem();
            Item[] trashItems = fileSystem.listTrash();
            for (Item item : trashItems) {
                if (item.getName().equalsIgnoreCase(uploadFileName)) {
                    trashItem = item;
                }
            }
            assertNotNull(trashItem);
            boolean restoreState = trashItem.restore(trashRecoverFolder,
                    BitcasaRESTConstants.RestoreMethod.RESCUE, null);
			assertTrue(restoreState);
		} catch (UnsupportedEncodingException e) {
			assertTrue(false);
			e.printStackTrace();
		} catch (IOException e) {
            e.printStackTrace();
        } catch (BitcasaException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test the changeAttributes function.
     */
    @Test
	public void test6ChangeAttributes() {
        String date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String newName = date + ".txt";
        HashMap<String, String> values = new HashMap<String, String>();
        values.put("name", newName);

        try {
            assertTrue(sourceFolder.changeAttributes(values, VersionExists.FAIL));
            assertEquals(newName, sourceFolder.getName());
        } catch (BitcasaException e) {
            assertTrue(false);
            e.printStackTrace();
        } catch (IOException e) {
            assertTrue(false);
            e.printStackTrace();
        }
	}

    /**
     * Test the set application data function.
     */
    @Test
    public void test7SetApplicationData() {
        String payload = "payload";
        ApplicationData applicationData = new ApplicationData(null, null, null, payload, null, null);
        try {
            boolean resultState = uploadedFile.setApplicationData(applicationData);
            assertNotNull(resultState);
            ApplicationData resultApplicationData = uploadedFile.getApplicationData();
            assertNotNull(resultApplicationData);
            assertEquals(resultApplicationData.getPayload(), payload);
        } catch (BitcasaException e) {
            e.printStackTrace();
            assertTrue(false);
        } catch (IOException e) {
            assertTrue(false);
            e.printStackTrace();
        }
    }
}
