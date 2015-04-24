.. java:import:: com.google.gson.annotations SerializedName

ItemMeta
========

.. java:package:: com.bitcasa.cloudfs.model
   :noindex:

.. java:type:: public class ItemMeta

   Model class for item meta data.

Constructors
------------
ItemMeta
^^^^^^^^

.. java:constructor:: public ItemMeta(String id, String parentId, String type, String name, String extension, long size, String mime, Integer dateCreated, Integer dateMetaLastModified, Integer dateContentLastModified, Integer version, ApplicationData applicationData, Boolean isMirrored)
   :outertype: ItemMeta

   Initializes a new instance of an item meta.

   :param id: The item id.
   :param parentId: The parent id.
   :param type: The type of item.
   :param name: The name of item.
   :param extension: The item extension.
   :param size: The item size.
   :param mime: The mime type of item.
   :param dateCreated: The created timestamp.
   :param dateMetaLastModified: The last modified timestamp of metadata.
   :param dateContentLastModified: The last modified timestamp of contents.
   :param version: The item version.
   :param applicationData: Additional information about item.
   :param isMirrored: A value indicating whether the item is mirrored.

Methods
-------
getApplicationData
^^^^^^^^^^^^^^^^^^

.. java:method:: public final ApplicationData getApplicationData()
   :outertype: ItemMeta

   Gets the application data.

   :return: The application data.

getDateContentLastModified
^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public final Integer getDateContentLastModified()
   :outertype: ItemMeta

   Gets the last modified date of contents.

   :return: The last modified date of meta.

getDateCreated
^^^^^^^^^^^^^^

.. java:method:: public final Integer getDateCreated()
   :outertype: ItemMeta

   Gets the created date.

   :return: The created date.

getDateMetaLastModified
^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public final Integer getDateMetaLastModified()
   :outertype: ItemMeta

   Gets the last modified date of meta.

   :return: The last modified date of meta.

getExtension
^^^^^^^^^^^^

.. java:method:: public final String getExtension()
   :outertype: ItemMeta

   Gets the extension.

   :return: The item extension.

getId
^^^^^

.. java:method:: public final String getId()
   :outertype: ItemMeta

   Gets the item id.

   :return: The item id.

getMime
^^^^^^^

.. java:method:: public final String getMime()
   :outertype: ItemMeta

   Gets the mime type.

   :return: The mime type.

getName
^^^^^^^

.. java:method:: public final String getName()
   :outertype: ItemMeta

   Gets the item name.

   :return: The item name.

getParentId
^^^^^^^^^^^

.. java:method:: public final String getParentId()
   :outertype: ItemMeta

   Gets the parent id.

   :return: The parent id.

getSize
^^^^^^^

.. java:method:: public final long getSize()
   :outertype: ItemMeta

   Gets the item size.

   :return: The item size.

getType
^^^^^^^

.. java:method:: public final String getType()
   :outertype: ItemMeta

   Gets the item type.

   :return: The item type.

getVersion
^^^^^^^^^^

.. java:method:: public final Integer getVersion()
   :outertype: ItemMeta

   Gets the version.

   :return: The version.

isFolder
^^^^^^^^

.. java:method:: public final boolean isFolder()
   :outertype: ItemMeta

   Gets a value indicating whether the item is a folder.

   :return: True if the item is a folder, otherwise false.

isMirrored
^^^^^^^^^^

.. java:method:: public final boolean isMirrored()
   :outertype: ItemMeta

   Gets a value indicating whether the item is mirrored.

   :return: True if the item is mirrored, otherwise false.

setName
^^^^^^^

.. java:method:: public final void setName(String name)
   :outertype: ItemMeta

   Sets the item name.

   :param name: Item name.

