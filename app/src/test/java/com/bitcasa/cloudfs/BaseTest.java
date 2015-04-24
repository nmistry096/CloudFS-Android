package com.bitcasa.cloudfs;

import com.bitcasa.cloudfs.Utils.BitcasaRESTConstants;
import com.bitcasa.cloudfs.Utils.BitcasaRESTConstants.VersionExists;
import com.bitcasa.cloudfs.exception.BitcasaException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;

/**
 * The base unit test.
 */
public abstract class BaseTest {

    /**
     * The Bitcasa client session.
     */
    protected static Session session;

    /**
     * Reference to the folder which is used for Android SDK Tests.
     */
    protected static Folder sdkTestFolder;

    protected static final String endpoint = "evyg9ym7w1.cloudfs.io";
    protected static final String clientId = "6VaRUN0AJDftZaaFyQy98oHvVmuUjI8fJz6UHIkQct0";
    protected static final String clientSecret = "fSXNM3HhRJaNM-N8gJsADjYwxvQnCMyZEh95BQpjuNRpt2j5EGVInd8UtTbmjg8dtd1qK0sb1NDmN7ClxxdanA";
    protected static final String username = "dhanushka@calcey.com";
    protected static final String password = "dhanushka";
    protected static final String testFolderName = "CFSAndroidSDKTest";

    /**
     * The temporary folder.
     */
    @Rule
    public final TemporaryFolder temporaryFolder = new TemporaryFolder();

    /**
     * Executed before executing all the tests.
     *
     * @throws Exception If a error occurs.
     */
    @BeforeClass
    public static void classSetUp() throws Exception {
        if (session == null) {
            session = new Session(endpoint, clientId, clientSecret);
            session.authenticate(username, password);
        }
    }

    /**
     * Executed after executing all the tests.
     *
     * @throws Exception If a error occurs.
     */
    @AfterClass
    public static void classTearDown() throws Exception {
        session.unlink();
        session = null;
    }

    /**
     * Returns the Folder which is used for Android SDK Tests.
     *
     * @return Folder object to perform all unit test operations.
     */
    public Folder getSDKTestFolder() throws BitcasaException, IOException {
        Folder rootFolder = session.filesystem().root();
        Item[] rootItems = rootFolder.list();

        Folder testFolder = null;
        for (Item item : rootItems) {
            if (item.getName().equalsIgnoreCase(testFolderName)) {
                testFolder = (Folder)item;
                break;
            }
        }

        if (testFolder == null) {
            testFolder = rootFolder.createFolder(testFolderName, BitcasaRESTConstants.Exists.OVERWRITE);
        }

        return testFolder;
    }
}
