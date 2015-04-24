.. java:import:: com.google.gson JsonElement

.. java:import:: com.google.gson.annotations SerializedName

ActionDataDefault
=================

.. java:package:: com.bitcasa.cloudfs.model
   :noindex:

.. java:type:: public class ActionDataDefault

   Represents the details of a history action.

Fields
------
applicationData
^^^^^^^^^^^^^^^

.. java:field:: @SerializedName protected JsonElement applicationData
   :outertype: ActionDataDefault

   Application data of the item.

dateContentLastModified
^^^^^^^^^^^^^^^^^^^^^^^

.. java:field:: @SerializedName protected double dateContentLastModified
   :outertype: ActionDataDefault

   Last modified date of the item's content.

dateCreated
^^^^^^^^^^^

.. java:field:: @SerializedName protected double dateCreated
   :outertype: ActionDataDefault

   Created date if the item.

dateMetaLastModified
^^^^^^^^^^^^^^^^^^^^

.. java:field:: @SerializedName protected double dateMetaLastModified
   :outertype: ActionDataDefault

   Last modified date of the item's meta data.

exists
^^^^^^

.. java:field:: protected String exists
   :outertype: ActionDataDefault

   The exists choice that was used.

extension
^^^^^^^^^

.. java:field:: protected String extension
   :outertype: ActionDataDefault

   Extension of the item.

id
^^

.. java:field:: protected String id
   :outertype: ActionDataDefault

   Id of the item.

isMirrored
^^^^^^^^^^

.. java:field:: @SerializedName protected boolean isMirrored
   :outertype: ActionDataDefault

   Boolean stating whether the item is mirrored.

mime
^^^^

.. java:field:: protected String mime
   :outertype: ActionDataDefault

   Mime type of the item.

name
^^^^

.. java:field:: protected String name
   :outertype: ActionDataDefault

   Name of the item.

parentId
^^^^^^^^

.. java:field:: @SerializedName protected String parentId
   :outertype: ActionDataDefault

   Parent id of the item.

path
^^^^

.. java:field:: protected String path
   :outertype: ActionDataDefault

   The path that the action was performed to.

paths
^^^^^

.. java:field:: protected String[] paths
   :outertype: ActionDataDefault

   Paths that the action was performed to.

shareKey
^^^^^^^^

.. java:field:: @SerializedName protected String shareKey
   :outertype: ActionDataDefault

   The share key.

shareUrl
^^^^^^^^

.. java:field:: @SerializedName protected String shareUrl
   :outertype: ActionDataDefault

   The share url.

size
^^^^

.. java:field:: protected double size
   :outertype: ActionDataDefault

   Size of the item.

to
^^

.. java:field:: protected String to
   :outertype: ActionDataDefault

   Path that the item was copied/moved to.

