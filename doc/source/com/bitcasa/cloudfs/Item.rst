.. java:import:: com.bitcasa.cloudfs Utils.BitcasaRESTConstants

.. java:import:: com.bitcasa.cloudfs Utils.BitcasaRESTConstants.Exists

.. java:import:: com.bitcasa.cloudfs Utils.BitcasaRESTConstants.RestoreMethod

.. java:import:: com.bitcasa.cloudfs Utils.BitcasaRESTConstants.VersionExists

.. java:import:: com.bitcasa.cloudfs.api RESTAdapter

.. java:import:: com.bitcasa.cloudfs.exception BitcasaException

.. java:import:: com.bitcasa.cloudfs.model ApplicationData

.. java:import:: com.bitcasa.cloudfs.model ItemMeta

.. java:import:: com.bitcasa.cloudfs.model Storage

.. java:import:: org.json JSONObject

.. java:import:: java.io IOException

.. java:import:: java.io UnsupportedEncodingException

.. java:import:: java.util Date

.. java:import:: java.util HashMap

Item
====

.. java:package:: com.bitcasa.cloudfs
   :noindex:

.. java:type:: public abstract class Item

   The Item class provides accessibility to CloudFS Item.

Fields
------
restAdapter
^^^^^^^^^^^

.. java:field:: protected RESTAdapter restAdapter
   :outertype: Item

   The REST Adapter instance.

Constructors
------------
Item
^^^^

.. java:constructor::  Item(RESTAdapter restAdapter, ItemMeta meta, String absoluteParentPath)
   :outertype: Item

   Initializes an instance of the Item.

   :param restAdapter: The REST Adapter instance.
   :param meta: The item meta data returned from REST Adapter.
   :param absoluteParentPath: The absolute parent path of this item.

Methods
-------
changeAttributes
^^^^^^^^^^^^^^^^

.. java:method:: public boolean changeAttributes(HashMap<String, String> values, VersionExists ifConflict) throws BitcasaException, IOException
   :outertype: Item

   Changes the specified item attributes.

   :param values: The attributes to be changed.
   :param ifConflict: The action to be taken if a conflict occurs.
   :throws IOException: If a network error occurs.
   :throws BitcasaException: If a CloudFS API error occurs.
   :return: boolean A value indicating whether the operation was successful or not.

copy
^^^^

.. java:method:: public Item copy(Container destination, Exists exists) throws IOException, BitcasaException
   :outertype: Item

   Copies the item to the given destination.

   :param destination: The destination container which the item needs to be copied.
   :param exists: The action to perform if the item already exists at the destination.
   :throws BitcasaException: If the server can not copy the item due to an error.
   :throws IOException: If response data can not be read.
   :return: A reference to the item at the destination path.

delete
^^^^^^

.. java:method:: public boolean delete(boolean commit, boolean force) throws IOException, BitcasaException
   :outertype: Item

   Deletes the item from CloudFS.

   :param commit: If true, item is deleted immediately. Otherwise, it is moved to the Trash. The default is false.
   :param force: If true, item is deleted even if it contains sub-items. The default is false.
   :throws BitcasaException: If a CloudFS API error occurs.
   :throws IOException: If a network error occurs.
   :return: Returns true if the item is deleted successfully, otherwise false.

getAbsoluteParentPath
^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public String getAbsoluteParentPath()
   :outertype: Item

   Gets the absolute parent path of the item.

   :return: The absolute parent path of the item.

getApplicationData
^^^^^^^^^^^^^^^^^^

.. java:method:: public ApplicationData getApplicationData()
   :outertype: Item

   Gets the item's application data. Updates the CloudFS account instantly.

   :return: The item's application data.

getDateContentLastModified
^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public Date getDateContentLastModified()
   :outertype: Item

   Gets the item's content last modified date.

   :return: The item's content last modified date.

getDateCreated
^^^^^^^^^^^^^^

.. java:method:: public Date getDateCreated()
   :outertype: Item

   Gets the item's created date.

   :return: The item's created date.

getDateMetaLastModified
^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public Date getDateMetaLastModified()
   :outertype: Item

   Gets the item's meta last modified date.

   :return: The item's meta last modified date.

getId
^^^^^

.. java:method:: public String getId()
   :outertype: Item

   Gets the item id.

   :return: The item id.

getIsMirrored
^^^^^^^^^^^^^

.. java:method:: public boolean getIsMirrored()
   :outertype: Item

   Gets a value indicating whether the item is mirrored.

   :return: A value indicating whether the item is mirrored.

getName
^^^^^^^

.. java:method:: public String getName()
   :outertype: Item

   Gets the item name.

   :return: The item name.

getPath
^^^^^^^

.. java:method:: public String getPath()
   :outertype: Item

   Gets the item's path.

   :return: The item's path.

getType
^^^^^^^

.. java:method:: public String getType()
   :outertype: Item

   Gets the item type.

   :return: The item type.

move
^^^^

.. java:method:: public Item move(Container destination, Exists exists) throws IOException, BitcasaException
   :outertype: Item

   Moves the item to the given destination.

   :param destination: The destination container which the item needs to be moved.
   :param exists: The action to perform if the item already exists at the destination.
   :throws BitcasaException: If the server can not move the item due to an error.
   :throws IOException: If response data can not be read.
   :return: An reference to the item at the destination path.

restore
^^^^^^^

.. java:method:: public boolean restore(Container destination, RestoreMethod method, String restoreArgument) throws UnsupportedEncodingException, BitcasaException
   :outertype: Item

   Restore the item to given destination.

   :param destination: The restore destination.
   :param method: The restore method.
   :param restoreArgument: The restore argument.
   :throws UnsupportedEncodingException: If encoding is not supported.
   :throws BitcasaException: BitcasaException If a CloudFS API error occurs.
   :return: boolean A value indicating whether the operation was successful or not.

setApplicationData
^^^^^^^^^^^^^^^^^^

.. java:method:: public boolean setApplicationData(ApplicationData applicationData) throws BitcasaException, IOException
   :outertype: Item

   Sets the application data and sends update to CloudFS instantly.

   :param applicationData: The application data of the item.
   :throws IOException: If a network error occurs.
   :throws BitcasaException: If a CloudFS API error occurs.
   :return: A value indicating whether the operation was successful or not.

setName
^^^^^^^

.. java:method:: public boolean setName(String name) throws BitcasaException, IOException
   :outertype: Item

   Sets the item name. Updates the CloudFS account instantly.

   :param name: The item name.
   :throws IOException: If a network error occurs.
   :throws BitcasaException: If a CloudFS API error occurs.
   :return: A value indicating whether the operation was successful or not.

toString
^^^^^^^^

.. java:method:: @Override public String toString()
   :outertype: Item

   Returns a string containing a concise, human-readable description of this object.

   :return: A printable representation of this object.

