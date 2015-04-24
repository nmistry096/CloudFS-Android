.. java:import:: com.google.gson JsonElement

.. java:import:: com.google.gson.annotations SerializedName

ActionData
==========

.. java:package:: com.bitcasa.cloudfs.model
   :noindex:

.. java:type:: public class ActionData

   Represents the details of a history action.

Fields
------
alteredDateCreated
^^^^^^^^^^^^^^^^^^

.. java:field:: @SerializedName protected DateCreated alteredDateCreated
   :outertype: ActionData

   Altered created dates of the item.

alteredId
^^^^^^^^^

.. java:field:: @SerializedName protected AlterData alteredId
   :outertype: ActionData

   Altered ids of the item.

alteredName
^^^^^^^^^^^

.. java:field:: @SerializedName protected AlterData alteredName
   :outertype: ActionData

   Altered names of the item.

applicationData
^^^^^^^^^^^^^^^

.. java:field:: @SerializedName protected JsonElement applicationData
   :outertype: ActionData

   Application data of the item.

dateContentLastModified
^^^^^^^^^^^^^^^^^^^^^^^

.. java:field:: @SerializedName protected double dateContentLastModified
   :outertype: ActionData

   Last modified date of the item's content.

dateCreated
^^^^^^^^^^^

.. java:field:: @SerializedName protected double dateCreated
   :outertype: ActionData

   Created date if the item.

dateMetaLastModified
^^^^^^^^^^^^^^^^^^^^

.. java:field:: @SerializedName protected double dateMetaLastModified
   :outertype: ActionData

   Last modified date of the item's meta data.

exists
^^^^^^

.. java:field:: protected String exists
   :outertype: ActionData

   The exists choice that was used.

extension
^^^^^^^^^

.. java:field:: protected String extension
   :outertype: ActionData

   Extension of the item.

id
^^

.. java:field:: protected String id
   :outertype: ActionData

   Id of the item.

isMirrored
^^^^^^^^^^

.. java:field:: @SerializedName protected boolean isMirrored
   :outertype: ActionData

   Boolean stating whether the item is mirrored.

mime
^^^^

.. java:field:: protected String mime
   :outertype: ActionData

   Mime type of the item.

name
^^^^

.. java:field:: protected String name
   :outertype: ActionData

   Name of the item.

parentId
^^^^^^^^

.. java:field:: @SerializedName protected String parentId
   :outertype: ActionData

   Parent id of the item.

path
^^^^

.. java:field:: protected String path
   :outertype: ActionData

   The path that the action was performed to.

paths
^^^^^

.. java:field:: protected String[] paths
   :outertype: ActionData

   Paths that the action was performed to.

shareKey
^^^^^^^^

.. java:field:: @SerializedName protected String shareKey
   :outertype: ActionData

   The share key.

shareUrl
^^^^^^^^

.. java:field:: @SerializedName protected String shareUrl
   :outertype: ActionData

   The share url.

size
^^^^

.. java:field:: protected double size
   :outertype: ActionData

   Size of the item.

to
^^

.. java:field:: protected String to
   :outertype: ActionData

   Path that the item was copied/moved to.

Constructors
------------
ActionData
^^^^^^^^^^

.. java:constructor:: public ActionData()
   :outertype: ActionData

   Initializes an empty ActionData object.

ActionData
^^^^^^^^^^

.. java:constructor:: public ActionData(ActionDataDefault actionData)
   :outertype: ActionData

   Initializes a new instance of ActionData with data from an ActionDataDefault instance.

   :param actionData: Available action data.

ActionData
^^^^^^^^^^

.. java:constructor:: public ActionData(ActionDataAlter actionData)
   :outertype: ActionData

   Initializes a new instance of ActionData with data from an ActionDataAlter instance.

   :param actionData: Available action data.

