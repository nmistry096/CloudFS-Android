.. java:import:: com.bitcasa.cloudfs Utils.BitcasaRESTConstants

ActionCopy
==========

.. java:package:: com.bitcasa.cloudfs.model
   :noindex:

.. java:type:: public class ActionCopy extends BaseAction

   Represents Copy Action details.

Constructors
------------
ActionCopy
^^^^^^^^^^

.. java:constructor:: public ActionCopy(BaseAction action)
   :outertype: ActionCopy

   Initializes a new instance of the action class.

   :param action: Data from base action.

Methods
-------
getDataExists
^^^^^^^^^^^^^

.. java:method:: public final String getDataExists()
   :outertype: ActionCopy

   Gets the \ :java:ref:`Exists <BitcasaRESTConstants.Exists>`\  option that was used.

   :return: The \ :java:ref:`Exists <BitcasaRESTConstants.Exists>`\  option that was used.

getDataName
^^^^^^^^^^^

.. java:method:: public final String getDataName()
   :outertype: ActionCopy

   Gets the name of the item.

   :return: The name of the item.

getDataTo
^^^^^^^^^

.. java:method:: public final String getDataTo()
   :outertype: ActionCopy

   Gets the path that the item was copied to.

   :return: The path that the item was copied to.

getPath
^^^^^^^

.. java:method:: public final String getPath()
   :outertype: ActionCopy

   Gets the path that the action was performed on.

   :return: The path that the action was performed on.

getType
^^^^^^^

.. java:method:: public final String getType()
   :outertype: ActionCopy

   Gets the type of item.

   :return: The type of item.

setDataExists
^^^^^^^^^^^^^

.. java:method:: public final void setDataExists(String dataExists)
   :outertype: ActionCopy

   Sets the \ :java:ref:`Exists <BitcasaRESTConstants.Exists>`\  option that was used.

   :param dataExists: The \ :java:ref:`Exists <BitcasaRESTConstants.Exists>`\  option that was used.

setDataName
^^^^^^^^^^^

.. java:method:: public final void setDataName(String dataName)
   :outertype: ActionCopy

   Sets the name of the item.

   :param dataName: The name of the item.

setDataTo
^^^^^^^^^

.. java:method:: public final void setDataTo(String dataTo)
   :outertype: ActionCopy

   Sets the path that the item was copied to.

   :param dataTo: The path that the item was copied to.

setPath
^^^^^^^

.. java:method:: public final void setPath(String path)
   :outertype: ActionCopy

   Sets the path that the action was performed on.

   :param path: The path that the action was performed on.

setType
^^^^^^^

.. java:method:: public final void setType(String type)
   :outertype: ActionCopy

   Sets the type of item.

   :param type: The type of item.

