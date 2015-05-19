.. java:import:: android.os Parcel

.. java:import:: android.os Parcelable

.. java:import:: com.bitcasa.cloudfs Utils.BitcasaRESTConstants

.. java:import:: com.bitcasa.cloudfs.api RESTAdapter

.. java:import:: com.bitcasa.cloudfs.exception BitcasaException

.. java:import:: com.bitcasa.cloudfs.model ItemMeta

.. java:import:: com.google.gson JsonObject

.. java:import:: com.google.gson JsonParser

.. java:import:: java.io IOException

.. java:import:: java.io UnsupportedEncodingException

.. java:import:: java.util AbstractMap

.. java:import:: java.util Date

.. java:import:: java.util HashMap

.. java:import:: java.util Map

Item
====

.. java:package:: com.bitcasa.cloudfs
   :noindex:

.. java:type:: public abstract class Item implements Parcelable

   The Item class provides accessibility to CloudFS Item.

Fields
------
absoluteParentPath
^^^^^^^^^^^^^^^^^^

.. java:field:: protected String absoluteParentPath
   :outertype: Item

   The item's absolute parent path.

absolutePath
^^^^^^^^^^^^

.. java:field:: protected String absolutePath
   :outertype: Item

   The item's absolute path.

applicationData
^^^^^^^^^^^^^^^

.. java:field:: protected JsonObject applicationData
   :outertype: Item

   The item application data.

dateContentLastModified
^^^^^^^^^^^^^^^^^^^^^^^

.. java:field:: protected Date dateContentLastModified
   :outertype: Item

   The item content last modified date.

dateCreated
^^^^^^^^^^^

.. java:field:: protected Date dateCreated
   :outertype: Item

   The item created date.

dateMetaLastModified
^^^^^^^^^^^^^^^^^^^^

.. java:field:: protected Date dateMetaLastModified
   :outertype: Item

   The item meta last modified date.

id
^^

.. java:field:: protected String id
   :outertype: Item

   The item id.

isMirrored
^^^^^^^^^^

.. java:field:: protected boolean isMirrored
   :outertype: Item

   A value that indicates whether the item is mirrored.

name
^^^^

.. java:field:: protected String name
   :outertype: Item

   The item name.

restAdapter
^^^^^^^^^^^

.. java:field:: protected final RESTAdapter restAdapter
   :outertype: Item

   The REST Adapter instance.

type
^^^^

.. java:field:: protected String type
   :outertype: Item

   The item type.

version
^^^^^^^

.. java:field:: protected int version
   :outertype: Item

   The item version.

Constructors
------------
Item
^^^^

.. java:constructor::  Item(RESTAdapter restAdapter, ItemMeta meta, String absoluteParentPath, String state, String shareKey)
   :outertype: Item

   Initializes an instance of the Item.

   :param restAdapter: The REST Adapter instance.
   :param meta: The item meta data returned from REST Adapter.
   :param absoluteParentPath: The absolute parent path of this item.
   :param state: The parent state of the item.
   :param shareKey: The share key of the item if the item is of type share.

Item
^^^^

.. java:constructor:: public Item(Parcel source)
   :outertype: Item

   Initializes the Item instance.

   :param source: The parcel object parameter.

Methods
-------
changeAttributes
^^^^^^^^^^^^^^^^

.. java:method:: public abstract boolean changeAttributes(Map<String, String> values, BitcasaRESTConstants.VersionExists ifConflict) throws BitcasaException
   :outertype: Item

   Changes the specified item attributes.

   :param values: The attributes to be changed.
   :param ifConflict: The action to be taken if a conflict occurs.
   :throws BitcasaException: If a CloudFS API error occurs.
   :return: boolean A value indicating whether the operation was successful or not.

copy
^^^^

.. java:method:: public Item copy(Container destination, String newName, BitcasaRESTConstants.Exists exists) throws IOException, BitcasaException
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

describeContents
^^^^^^^^^^^^^^^^

.. java:method:: @Override public int describeContents()
   :outertype: Item

   Describe the kinds of special objects contained in this Parcelable's marshalled representation

   :return: a bitmask indicating the set of special object types marshalled by the Parcelable

getAbsoluteParentPath
^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public String getAbsoluteParentPath()
   :outertype: Item

   Gets the absolute parent path of the item.

   :return: The absolute parent path of the item.

getApplicationData
^^^^^^^^^^^^^^^^^^

.. java:method:: public JsonObject getApplicationData()
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

getParentId
^^^^^^^^^^^

.. java:method:: public String getParentId()
   :outertype: Item

   Gets the item's parent id.

   :return: The item's parent id.

getPath
^^^^^^^

.. java:method:: public String getPath()
   :outertype: Item

   Gets the item's path.

   :return: The item's path.

getShareKey
^^^^^^^^^^^

.. java:method:: public String getShareKey()
   :outertype: Item

   Gets the item's share key if the item is of type share.

   :return: The share key of the item.

getState
^^^^^^^^

.. java:method:: public String getState()
   :outertype: Item

   Gets the parent state of the item.

   :return: The parent state.

getType
^^^^^^^

.. java:method:: public String getType()
   :outertype: Item

   Gets the item type.

   :return: The item type.

getVersion
^^^^^^^^^^

.. java:method:: public int getVersion()
   :outertype: Item

   Gets the item version number.

   :return: The item version number;

move
^^^^

.. java:method:: public Item move(Container destination, BitcasaRESTConstants.Exists exists) throws IOException, BitcasaException
   :outertype: Item

   Moves the item to the given destination.

   :param destination: The destination container which the item needs to be moved.
   :param exists: The action to perform if the item already exists at the destination.
   :throws BitcasaException: If the server can not move the item due to an error.
   :throws IOException: If response data can not be read.
   :return: An reference to the item at the destination path.

restore
^^^^^^^

.. java:method:: public boolean restore(Container destination, BitcasaRESTConstants.RestoreMethod method, String restoreArgument, boolean maintainValidity) throws UnsupportedEncodingException, BitcasaException
   :outertype: Item

   Restore the item to given destination.

   :param destination: The restore destination.
   :param method: The restore method.
   :param restoreArgument: The restore argument.
   :param maintainValidity: If true, item maintains it's validity. The default is false.
   :throws UnsupportedEncodingException: If encoding is not supported.
   :throws BitcasaException: If a CloudFS API error occurs.
   :return: boolean A value indicating whether the operation was successful or not.

setApplicationData
^^^^^^^^^^^^^^^^^^

.. java:method:: public boolean setApplicationData(JsonObject applicationData) throws BitcasaException
   :outertype: Item

   Sets the application data and sends update to CloudFS instantly.

   :param applicationData: The application data to be set.
   :throws BitcasaException: If a CloudFS API error occurs.
   :return: A value indicating whether the operation was successful or not.

setName
^^^^^^^

.. java:method:: public boolean setName(String name) throws BitcasaException
   :outertype: Item

   Sets the item name. Updates the CloudFS account instantly.

   :param name: The item name.
   :throws BitcasaException: If a CloudFS API error occurs.
   :return: A value indicating whether the operation was successful or not.

setParentId
^^^^^^^^^^^

.. java:method:: public void setParentId(String parentId)
   :outertype: Item

setShareKey
^^^^^^^^^^^

.. java:method:: public void setShareKey(String shareKey)
   :outertype: Item

   Sets the item's share key.

   :param shareKey: The share key to be set.

setState
^^^^^^^^

.. java:method:: public void setState(String state)
   :outertype: Item

   Set the parent state of the item.

   :param state: The parent state to be set.

toString
^^^^^^^^

.. java:method:: @Override public String toString()
   :outertype: Item

   Returns a string containing a concise, human-readable description of this object.

   :return: A printable representation of this object.

writeToParcel
^^^^^^^^^^^^^

.. java:method:: @Override public void writeToParcel(Parcel out, int flags)
   :outertype: Item

   Flatten this object in to a Parcel.

   :param out: The Parcel in which the object should be written.
   :param flags: Additional flags about how the object should be written. May be 0 or PARCELABLE_WRITE_RETURN_VALUE

