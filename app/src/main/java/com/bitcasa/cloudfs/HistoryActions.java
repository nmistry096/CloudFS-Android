/**
 * Bitcasa Client Android SDK
 * Copyright (C) 2015 Bitcasa, Inc.
 * 215 Castro Street, 2nd Floor
 * Mountain View, CA 94041
 *
 * This file contains an SDK in Java for accessing the Bitcasa infinite drive in Android platform.
 *
 * For support, please send email to support@bitcasa.com.
 */

package com.bitcasa.cloudfs;

import android.util.Pair;

import com.bitcasa.cloudfs.model.ActionAlterMeta;
import com.bitcasa.cloudfs.model.ActionCopy;
import com.bitcasa.cloudfs.model.ActionCreate;
import com.bitcasa.cloudfs.model.ActionDelete;
import com.bitcasa.cloudfs.model.ActionDeviceCreate;
import com.bitcasa.cloudfs.model.ActionDeviceDelete;
import com.bitcasa.cloudfs.model.ActionDeviceUpdate;
import com.bitcasa.cloudfs.model.ActionMove;
import com.bitcasa.cloudfs.model.ActionShareCreate;
import com.bitcasa.cloudfs.model.ActionShareReceive;
import com.bitcasa.cloudfs.model.ActionTrash;

/**
 * History action types.
 */
public enum HistoryActions {

    /**
     * The shared item received action.
     */
    SHARE_RECEIVE("share_receive"),

    /**
     * The shared item created action.
     */
    SHARE_CREATE("share_create"),

    /**
     * The device updated action.
     */
    DEVICE_UPDATE("device_update"),

    /**
     * The device created action.
     */
    DEVICE_CREATE("device_create"),

    /**
     * The device deleted action.
     */
    DEVICE_DELETE("device_delete"),

    /**
     * The item meta altered action.
     */
    ALTER_META("alter_meta"),

    /**
     * The item copied action.
     */
    COPY("copy"),

    /**
     * The item moved action.
     */
    MOVE("move"),

    /**
     * The item created action.
     */
    CREATE("create"),

    /**
     * The item deleted action.
     */
    DELETE("delete"),

    /**
     * The item trashed action.
     */
    TRASH("trash");

    /**
     * The history action.
     */
    private String action;

    /**
     * Initializes a new instance of HistoryActions.
     *
     * @param action The history action.
     */
    private HistoryActions(final String action) {
        this.action = action;
    }

    /**
     * Gets history action.
     *
     * @param action The action.
     * @return A history action.
     */
    public static Pair<HistoryActions, Class> getAction(final String action) {
        if (action.equals("share_receive")) {
            return new Pair<HistoryActions, Class>(SHARE_RECEIVE, ActionShareReceive.class);
        } else if (action.equals("share_create")) {
            return new Pair<HistoryActions, Class>(SHARE_CREATE, ActionShareCreate.class);
        } else if (action.equals("device_update")) {
            return new Pair<HistoryActions, Class>(DEVICE_UPDATE, ActionDeviceUpdate.class);
        } else if (action.equals("device_create")) {
            return new Pair<HistoryActions, Class>(DEVICE_CREATE, ActionDeviceCreate.class);
        } else if (action.equals("device_delete")) {
            return new Pair<HistoryActions, Class>(DEVICE_DELETE, ActionDeviceDelete.class);
        } else if (action.equals("alter_meta")) {
            return new Pair<HistoryActions, Class>(ALTER_META, ActionAlterMeta.class);
        } else if (action.equals("copy")) {
            return new Pair<HistoryActions, Class>(COPY, ActionCopy.class);
        } else if (action.equals("move")) {
            return new Pair<HistoryActions, Class>(MOVE, ActionMove.class);
        } else if (action.equals("create")) {
            return new Pair<HistoryActions, Class>(CREATE, ActionCreate.class);
        } else if (action.equals("delete")) {
            return new Pair<HistoryActions, Class>(DELETE, ActionDelete.class);
        } else if (action.equals("trash")) {
            return new Pair<HistoryActions, Class>(TRASH, ActionTrash.class);
        } else {
            return null;
        }
    }
}
