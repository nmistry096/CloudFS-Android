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

import com.bitcasa.cloudfs.model.BaseAction;

import java.util.AbstractMap;
import java.util.HashMap;

/**
 * The ActionHistory class provides accessibility to CloudFS ActionHistory.
 */
public class ActionHistory {

    /**
     * The actions list.
     */
    private final AbstractMap<String, BaseAction> actions;

    /**
     * Initializes a new instance of ActionHistory.
     *
     * @param actionList List of actions to be added to this instance.
     */
    public ActionHistory(final Iterable<BaseAction> actionList) {
        this.actions = new HashMap<String, BaseAction>();
        for (final BaseAction action : actionList) {
            this.addAction(action);
        }
    }

    /**
     * Initializes an empty instance of the ActionHistory class.
     */
    public ActionHistory() {
        this.actions = new HashMap<String, BaseAction>();
    }

    /**
     * Gets the action history key.
     *
     * @param action  The history action.
     * @param version The version.
     * @return The key of the action history.
     */
    public final String getKey(final HistoryActions action, final int version) {
        return action.name() + '-' + version;
    }

    /**
     * Adds an action to the action history.
     *
     * @param action The action to be added.
     */
    public final void addAction(final BaseAction action) {
        this.actions.put(this.getKey(action.getAction(), action.getVersion()), action);
    }

    /**
     * Removes an action from the action history.
     *
     * @param key The action key to be removed.
     */
    public final void removeAction(final String key) {
        this.actions.remove(key);
    }

    /**
     * Removes all the actions from action history.
     */
    public final void removeAll() {
        this.actions.clear();
    }

    /**
     * Gets the action history size.
     *
     * @return The action history size.
     */
    public final int getSize() {
        return this.actions.size();
    }
}
