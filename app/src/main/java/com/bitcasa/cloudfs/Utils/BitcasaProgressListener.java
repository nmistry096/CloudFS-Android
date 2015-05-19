/**
 * Bitcasa Client Android SDK
 * Copyright (C) 2015 Bitcasa, Inc.
 * 1200 Park Place,
 * Suite 350 San Mateo, CA 94403.
 *
 * This file contains an SDK in Java for accessing the Bitcasa infinite drive in Android platform.
 *
 * For support, please send email to sdks@bitcasa.com.
 */

package com.bitcasa.cloudfs.Utils;

public interface BitcasaProgressListener {

    public enum ProgressAction {
        BITCASA_ACTION_DOWNLOAD,
        BITCASA_ACTION_UPLOAD
    }

    public void onProgressUpdate(String file, int percentage, BitcasaProgressListener.ProgressAction action);

    public void canceled(String file, BitcasaProgressListener.ProgressAction action);
}
