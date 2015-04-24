.. java:import:: com.bitcasa.cloudfs HistoryActions

.. java:import:: com.google.gson.annotations Expose

BaseAction
==========

.. java:package:: com.bitcasa.cloudfs.model
   :noindex:

.. java:type:: public class BaseAction

   Represents the base action details.

Fields
------
action
^^^^^^

.. java:field:: @Expose protected String action
   :outertype: BaseAction

   The action that was performed.

data
^^^^

.. java:field:: protected ActionData data
   :outertype: BaseAction

   The extra details of the action.

historyAction
^^^^^^^^^^^^^

.. java:field:: @Expose protected HistoryActions historyAction
   :outertype: BaseAction

   The history action value.

path
^^^^

.. java:field:: @Expose protected String path
   :outertype: BaseAction

   The path that the action was performed to.

type
^^^^

.. java:field:: @Expose protected String type
   :outertype: BaseAction

   The type of item that the action was performed on.

version
^^^^^^^

.. java:field:: @Expose protected int version
   :outertype: BaseAction

   The version of the action.

Constructors
------------
BaseAction
^^^^^^^^^^

.. java:constructor:: public BaseAction()
   :outertype: BaseAction

   Initializes an empty BaseAction instance.

Methods
-------
getAction
^^^^^^^^^

.. java:method:: public final HistoryActions getAction()
   :outertype: BaseAction

   Gets the history action value.

   :return: The history action value.

getActionString
^^^^^^^^^^^^^^^

.. java:method:: public final String getActionString()
   :outertype: BaseAction

   Gets the action string.

   :return: The action string.

getVersion
^^^^^^^^^^

.. java:method:: public final int getVersion()
   :outertype: BaseAction

   Gets the version of the action.

   :return: The version of the action.

setAction
^^^^^^^^^

.. java:method:: public final void setAction(HistoryActions action)
   :outertype: BaseAction

   Sets the history action value.

   :param action: The history action value.

setData
^^^^^^^

.. java:method:: public final void setData(ActionData actionData)
   :outertype: BaseAction

   Sets the extra data of the action.

   :param actionData: The extra data of the action.

setVersion
^^^^^^^^^^

.. java:method:: public final void setVersion(int version)
   :outertype: BaseAction

   Sets the version of the action.

   :param version: The version of the action.

