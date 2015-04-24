ActionShareReceive
==================

.. java:package:: com.bitcasa.cloudfs.model
   :noindex:

.. java:type:: public class ActionShareReceive extends BaseAction

   Represents Share Receive Action details.

Constructors
------------
ActionShareReceive
^^^^^^^^^^^^^^^^^^

.. java:constructor:: public ActionShareReceive(BaseAction action)
   :outertype: ActionShareReceive

   Initializes a new instance of the action class.

   :param action: Data from base action.

Methods
-------
getDataExists
^^^^^^^^^^^^^

.. java:method:: public final String getDataExists()
   :outertype: ActionShareReceive

   Gets the exists choice that was used.

   :return: The exists choice that was used.

getDataPath
^^^^^^^^^^^

.. java:method:: public final String getDataPath()
   :outertype: ActionShareReceive

   Gets the path that the share was received to.

   :return: The path that the share was received to.

setDataExists
^^^^^^^^^^^^^

.. java:method:: public final void setDataExists(String dataExists)
   :outertype: ActionShareReceive

   Sets the exists choice that was used.

   :param dataExists: The exists choice that was used.

setDataPath
^^^^^^^^^^^

.. java:method:: public final void setDataPath(String dataPath)
   :outertype: ActionShareReceive

   Sets the path that the share was received to.

   :param dataPath: The path that the share was received to.

