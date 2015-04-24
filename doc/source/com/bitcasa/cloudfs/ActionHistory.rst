.. java:import:: com.bitcasa.cloudfs.model BaseAction

.. java:import:: java.util HashMap

.. java:import:: java.util List

ActionHistory
=============

.. java:package:: com.bitcasa.cloudfs
   :noindex:

.. java:type:: public class ActionHistory

   The ActionHistory class provides accessibility to CloudFS ActionHistory.

Constructors
------------
ActionHistory
^^^^^^^^^^^^^

.. java:constructor:: public ActionHistory(List<BaseAction> actionList)
   :outertype: ActionHistory

   Initializes a new instance of ActionHistory.

   :param actionList: List of actions to be added to this instance.

ActionHistory
^^^^^^^^^^^^^

.. java:constructor:: public ActionHistory()
   :outertype: ActionHistory

   Initializes an empty instance of the ActionHistory class.

Methods
-------
addAction
^^^^^^^^^

.. java:method:: public final void addAction(BaseAction action)
   :outertype: ActionHistory

   Adds an action to the action history.

   :param action: The action to be added.

getKey
^^^^^^

.. java:method:: public final String getKey(HistoryActions action, int version)
   :outertype: ActionHistory

   Gets the action history key.

   :param action: The history action.
   :param version: The version.
   :return: The key of the action history.

getSize
^^^^^^^

.. java:method:: public final int getSize()
   :outertype: ActionHistory

   Gets the action history size.

   :return: The action history size.

removeAction
^^^^^^^^^^^^

.. java:method:: public final void removeAction(String key)
   :outertype: ActionHistory

   Removes an action from the action history.

   :param key: The action key to be removed.

removeAll
^^^^^^^^^

.. java:method:: public final void removeAll()
   :outertype: ActionHistory

   Removes all the actions from action history.

