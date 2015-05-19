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
package com.bitcasa.cloudfs.model;

import android.util.Pair;

import com.bitcasa.cloudfs.HistoryActions;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Factory that generates Action objects.
 */
public class ActionFactory {

    /**
     * Prevents initialization of this utility.
     */
    private ActionFactory() {

    }

    /**
     * Gets the correct action object for the provided base action.
     *
     * @param baseAction The base action.
     * @return Generated action.
     */
    public static BaseAction getAction(final BaseAction baseAction) {

        BaseAction newBaseAction;
        try {
            final Pair<HistoryActions, Class> pair = HistoryActions.getAction(baseAction.getActionString());
            final Class<?> actionClass = pair.second;
            final Constructor<?> exceptionConstructor = actionClass.getDeclaredConstructor(BaseAction.class);
            final BaseAction action = (BaseAction) exceptionConstructor.newInstance(baseAction);
            action.setAction(pair.first);
            newBaseAction = action;
        } catch (final NoSuchMethodException exception) {
            newBaseAction = baseAction;
        } catch (final InstantiationException exception) {
            newBaseAction = baseAction;
        } catch (final IllegalAccessException exception) {
            newBaseAction = baseAction;
        } catch (final InvocationTargetException exception) {
            newBaseAction = baseAction;
        }

        return newBaseAction;
    }
}
