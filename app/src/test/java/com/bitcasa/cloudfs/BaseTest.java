package com.bitcasa.cloudfs;

import android.util.Log;

import com.bitcasa.cloudfs.Utils.BitcasaProgressListener;
import com.bitcasa.cloudfs.Utils.BitcasaRESTConstants;
import com.bitcasa.cloudfs.exception.BitcasaException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

import java.io.BufferedWriter;
import java.io.FileWriter;
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
    protected Folder sdkTestFolder;

    private static final boolean CLEANED = false;

    protected static final String cloudfsEndpoint = "beve3sodxx.cloudfs.io";
    protected static final String clientId = "c6c_n1nkn2gS8gPlqNgLrDv90YhqXwJkYKuyzJWMp3s";
    protected static final String clientSecret = "d9TRBSvwd3zsGM6iN7ym7D6aTPjbVUuR6XCeJLRu5V7NXaHX4Bq2w6JGzuqVqfLASVd4Qnh6v1ftQ23J2K9Otw";
    protected static final String username = "manendra@calcey.com";
    protected static final String password = "user@123";
    protected static final String usernameTwo = "naja@calcey.com";
    protected static final String passwordTwo = "user@123";

    protected static final String adminId = "lO8YWMqr6SLlPztLI7JiPDM-yQuosvlvLiCA_2vzdf0";
    protected static final String adminSecret = "eIdbCSpAawBmyzCcdb5c1htZqyeCeim13cFJb1knGlQ2MjZ8AGWcBrTanTlJnNyDcPdDTPBgGK9znF0HnvjRpw";

    protected static final String testFolderName = "CFSAndroidSDKTest";

    /**
     * The temporary folder.
     */
    @Rule
    public final TemporaryFolder temporaryFolder = new TemporaryFolder();

    /**
     * Executed before executing all the tests.
     *
     * @throws BitcasaException If a CloudsFS API error occurs.
     * @throws IOException      If a network error occurs.
     */
    @BeforeClass
    public static void classSetUp() throws BitcasaException, IOException {
        BaseTest.clean();
        if (BaseTest.session == null) {
            BaseTest.session = new Session(BaseTest.cloudfsEndpoint, BaseTest.clientId, BaseTest.clientSecret);
            BaseTest.session.authenticate(BaseTest.username, BaseTest.password);
        }
    }

    /**
     * Executed after executing all the tests.
     *
     * @throws Exception If a error occurs.
     */
    @AfterClass
    public static void classTearDown() throws Exception {
//        session.unlink();
        BaseTest.session = null;
    }

    /**
     * Returns the Folder which is used for Android SDK Tests.
     *
     * @return Folder object to perform all unit test operations.
     */
    public static Folder getSDKTestFolder() throws BitcasaException, IOException {
        final Folder rootFolder = BaseTest.session.filesystem().root();
        final Item[] rootItems = rootFolder.list();

        Folder testFolder = null;
        for (final Item item : rootItems) {
            if (item.getName().equalsIgnoreCase(BaseTest.testFolderName)) {
                testFolder = (Folder)item;
                break;
            }
        }

        if (testFolder == null) {
            testFolder = rootFolder.createFolder(BaseTest.testFolderName, BitcasaRESTConstants.Exists.OVERWRITE);
        }

        return testFolder;
    }

    public java.io.File createLocalFileWithContent(String name, String content) {
        java.io.File textFile = null;
        try {
            textFile = temporaryFolder.newFile(name);
            BufferedWriter output = new BufferedWriter(new FileWriter(textFile));
            output.write(content);
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return textFile;
    }

    public BitcasaProgressListener getProgressListener() {

        BitcasaProgressListener listener = new BitcasaProgressListener() {

            @Override
            public void onProgressUpdate(String file, int percentage, ProgressAction action) {
                Log.d("test Upload", file + " percentage: " + percentage);
            }

            @Override
            public void canceled(String file, ProgressAction action) {
            }
        };
        return listener;
    }

    public static void clean() {
        if (!BaseTest.CLEANED) {
            try {
                Session session = new Session(cloudfsEndpoint, clientId, clientSecret);
                session.authenticate(username, password);

                Item[] rootItems = session.filesystem().root().list();
                for (Item item : rootItems) {
                    if (item.getName().equalsIgnoreCase(testFolderName)) {
                        item.delete(true, true);
                    }
                }
            } catch (final IOException e) {
                e.printStackTrace();
            } catch (final BitcasaException e) {
                e.printStackTrace();
            }
        }
    }
}
