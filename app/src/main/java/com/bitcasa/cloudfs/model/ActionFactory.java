package com.bitcasa.cloudfs.model;

import android.util.Pair;

import com.bitcasa.cloudfs.HistoryActions;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Factory that generates Action objects.
 */
public final class ActionFactory {

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
        try {
            Pair<HistoryActions, Class> pair = HistoryActions.getAction(baseAction.getActionString());
            Class<?> actionClass = pair.second;
            Constructor<?> exceptionConstructor = actionClass.getDeclaredConstructor(BaseAction.class);
            BaseAction action = (BaseAction) exceptionConstructor.newInstance(baseAction);
            action.setAction(pair.first);
            return action;
        } catch (NoSuchMethodException exception) {
            return baseAction;
        } catch (InstantiationException exception) {
            return baseAction;
        } catch (IllegalAccessException exception) {
            return baseAction;
        } catch (InvocationTargetException exception) {
            return baseAction;
        }
    }
}
