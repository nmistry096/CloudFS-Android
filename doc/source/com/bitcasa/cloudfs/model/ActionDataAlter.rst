.. java:import:: com.google.gson JsonElement

.. java:import:: com.google.gson.annotations SerializedName

ActionDataAlter
===============

.. java:package:: com.bitcasa.cloudfs.model
   :noindex:

.. java:type:: public class ActionDataAlter

   Represents the details of a history action regarding item alterations.

Fields
------
alteredDateCreated
^^^^^^^^^^^^^^^^^^

.. java:field:: @SerializedName protected DateCreated alteredDateCreated
   :outertype: ActionDataAlter

   Altered created dates of the item.

alteredId
^^^^^^^^^

.. java:field:: @SerializedName protected AlterData alteredId
   :outertype: ActionDataAlter

   Altered ids of the item.

alteredName
^^^^^^^^^^^

.. java:field:: @SerializedName protected AlterData alteredName
   :outertype: ActionDataAlter

   Altered names of the item.

applicationData
^^^^^^^^^^^^^^^

.. java:field:: @SerializedName protected JsonElement applicationData
   :outertype: ActionDataAlter

   Application data of the item.

dateContentLastModified
^^^^^^^^^^^^^^^^^^^^^^^

.. java:field:: @SerializedName protected double dateContentLastModified
   :outertype: ActionDataAlter

   Last modified date of the item's content.

dateMetaLastModified
^^^^^^^^^^^^^^^^^^^^

.. java:field:: @SerializedName protected double dateMetaLastModified
   :outertype: ActionDataAlter

   Last modified date of the item's meta data.

exists
^^^^^^

.. java:field:: protected String exists
   :outertype: ActionDataAlter

   The exists choice that was used.

extension
^^^^^^^^^

.. java:field:: protected String extension
   :outertype: ActionDataAlter

   Extension of the item.

isMirrored
^^^^^^^^^^

.. java:field:: @SerializedName protected boolean isMirrored
   :outertype: ActionDataAlter

   Boolean stating whether the item is mirrored.

mime
^^^^

.. java:field:: protected String mime
   :outertype: ActionDataAlter

   Mime type of the item.

parentId
^^^^^^^^

.. java:field:: @SerializedName protected String parentId
   :outertype: ActionDataAlter

   Parent id of the item.

path
^^^^

.. java:field:: protected String path
   :outertype: ActionDataAlter

   The path that the action was performed to.

paths
^^^^^

.. java:field:: protected String[] paths
   :outertype: ActionDataAlter

   Paths that the action was performed to.

shareKey
^^^^^^^^

.. java:field:: @SerializedName protected String shareKey
   :outertype: ActionDataAlter

   The share key.

shareUrl
^^^^^^^^

.. java:field:: @SerializedName protected String shareUrl
   :outertype: ActionDataAlter

   The share url.

size
^^^^

.. java:field:: protected double size
   :outertype: ActionDataAlter

   Size of the item.

to
^^

.. java:field:: protected String to
   :outertype: ActionDataAlter

   Path that the item was copied/moved to.

