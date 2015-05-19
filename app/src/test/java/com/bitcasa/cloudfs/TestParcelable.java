package com.bitcasa.cloudfs;

import android.os.Parcel;
import android.util.Log;

import com.bitcasa.cloudfs.Utils.BitcasaProgressListener;
import com.bitcasa.cloudfs.Utils.BitcasaRESTConstants;
import com.bitcasa.cloudfs.exception.BitcasaException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.io.IOException;

import static junit.framework.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
public class TestParcelable extends BaseTest {

    @Test
    public void testAccountParcelable() throws BitcasaException, IOException {
        Account account = session.account();

        Parcel parcel = Parcel.obtain();
        account.writeToParcel(parcel, 0);

        parcel.setDataPosition(0);

        Account accountFromParcel = Account.CREATOR.createFromParcel(parcel);
        assertNotNull(accountFromParcel);
    }

    @Test
    public void testUserParcelable() throws BitcasaException, IOException {
        User user = session.user();

        Parcel parcel = Parcel.obtain();
        user.writeToParcel(parcel, 0);

        parcel.setDataPosition(0);

        User userFromParcel = User.CREATOR.createFromParcel(parcel);
        assertNotNull(userFromParcel);
    }

    @Test
    public void testFolderParcelable() throws BitcasaException, IOException {
        FileSystem fileSystem = session.filesystem();

        Folder rootFolder = fileSystem.root();

        Parcel parcel = Parcel.obtain();
        rootFolder.writeToParcel(parcel,0);

        parcel.setDataPosition(0);

        Folder rootFolderFromParcel = Folder.CREATOR.createFromParcel(parcel);
        assertNotNull(rootFolderFromParcel);
    }

    @Test
    public void testFileParcelable() throws BitcasaException, IOException {

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
        Folder folder = getSDKTestFolder();

        File fileToTest = folder.upload(createLocalFileWithContent("Parcelable.txt","Parcelable Test Data").getAbsolutePath(),
                listener,
                BitcasaRESTConstants.Exists.OVERWRITE);

        Parcel parcel = Parcel.obtain();
        fileToTest.writeToParcel(parcel,0);

        parcel.setDataPosition(0);

        File fileFromParcel = File.CREATOR.createFromParcel(parcel);
        assertNotNull(fileFromParcel);
    }

}