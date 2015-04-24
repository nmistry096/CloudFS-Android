package com.bitcasa.cloudfs;

import com.bitcasa.cloudfs.Utils.BitcasaRESTConstants;
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

    protected static final String cloudfsEndpoint = "<add cloudfs endpoint here>";
    protected static final String clientId = "<add client id here>";
    protected static final String clientSecret = "<add cloudfs endpoint here>";
    protected static final String username = "<add cloudfs username here>";
    protected static final String password = "<add cloudfs password here>";
    protected final static String adminId = "<add cloudfs admin id here - only available for paid users>";
    protected final static String adminSecret = "<add cloudfs admin secret here - only available for paid users>";

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
            session = new Session(cloudfsEndpoint, clientId, clientSecret);
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
