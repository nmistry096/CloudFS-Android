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

import com.bitcasa.cloudfs.model.BaseAction;

import java.util.HashMap;
import java.util.List;

/**
 * The ActionHistory class provides accessibility to CloudFS ActionHistory.
 */
public class ActionHistory {

    /**
     * The actions list.
     */
    private HashMap<String, BaseAction> actions;

    /**
     * Initializes a new instance of ActionHistory.
     *
     * @param actionList List of actions to be added to this instance.
     */
    public ActionHistory(final List<BaseAction> actionList) {
        actions = new HashMap<String, BaseAction>();
        for (BaseAction action : actionList) {
            addAction(action);
        }
    }

    /**
     * Initializes an empty instance of the ActionHistory class.
     */
    public ActionHistory() {
        actions = new HashMap<String, BaseAction>();
    }

    /**
     * Gets the action history key.
     *
     * @param action  The history action.
     * @param version The version.
     * @return The key of the action history.
     */
    public final String getKey(final HistoryActions action, final int version) {
        return action.name() + "-" + version;
    }

    /**
     * Adds an action to the action history.
     *
     * @param action The action to be added.
     */
    public final void addAction(final BaseAction action) {
        actions.put(getKey(action.getAction(), action.getVersion()), action);
    }

    /**
     * Removes an action from the action history.
     *
     * @param key The action key to be removed.
     */
    public final void removeAction(final String key) {
        actions.remove(key);
    }

    /**
     * Removes all the actions from action history.
     */
    public final void removeAll() {
        actions.clear();
    }

    /**
     * Gets the action history size.
     *
     * @return The action history size.
     */
    public final int getSize() {
        return actions.size();
    }
}
