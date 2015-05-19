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
    private final String action;

    /**
     * Initializes a new instance of HistoryActions.
     *
     * @param action The history action.
     */
    HistoryActions(final String action) {
        this.action = action;
    }

    /**
     * Gets history action.
     *
     * @param action The action.
     * @return A history action.
     */
    public static Pair<HistoryActions, Class> getAction(final String action) {
        final Pair<HistoryActions, Class> pair;
        if ("share_receive".equals(action)) {
            pair = new Pair<HistoryActions, Class>(HistoryActions.SHARE_RECEIVE, ActionShareReceive.class);
        } else if ("share_create".equals(action)) {
            pair = new Pair<HistoryActions, Class>(HistoryActions.SHARE_CREATE, ActionShareCreate.class);
        } else if ("device_update".equals(action)) {
            pair = new Pair<HistoryActions, Class>(HistoryActions.DEVICE_UPDATE, ActionDeviceUpdate.class);
        } else if ("device_create".equals(action)) {
            pair = new Pair<HistoryActions, Class>(HistoryActions.DEVICE_CREATE, ActionDeviceCreate.class);
        } else if ("device_delete".equals(action)) {
            pair = new Pair<HistoryActions, Class>(HistoryActions.DEVICE_DELETE, ActionDeviceDelete.class);
        } else if ("alter_meta".equals(action)) {
            pair = new Pair<HistoryActions, Class>(HistoryActions.ALTER_META, ActionAlterMeta.class);
        } else if ("copy".equals(action)) {
            pair = new Pair<HistoryActions, Class>(HistoryActions.COPY, ActionCopy.class);
        } else if ("move".equals(action)) {
            pair = new Pair<HistoryActions, Class>(HistoryActions.MOVE, ActionMove.class);
        } else if ("create".equals(action)) {
            pair = new Pair<HistoryActions, Class>(HistoryActions.CREATE, ActionCreate.class);
        } else if ("delete".equals(action)) {
            pair = new Pair<HistoryActions, Class>(HistoryActions.DELETE, ActionDelete.class);
        } else if ("trash".equals(action)) {
            pair = new Pair<HistoryActions, Class>(HistoryActions.TRASH, ActionTrash.class);
        } else {
            pair = null;
        }

        return pair;
    }
}
