.. java:import:: com.bitcasa.cloudfs Utils.BitcasaRESTConstants

.. java:import:: com.bitcasa.cloudfs.api RESTAdapter

.. java:import:: com.bitcasa.cloudfs.exception BitcasaException

.. java:import:: com.bitcasa.cloudfs.model ApplicationData

.. java:import:: com.bitcasa.cloudfs.model ItemMeta

.. java:import:: com.bitcasa.cloudfs.model ShareItem

.. java:import:: java.io IOException

.. java:import:: java.util Date

.. java:import:: java.util HashMap

Share
=====

.. java:package:: com.bitcasa.cloudfs
   :noindex:

.. java:type:: public class Share

   The Share class provides accessibility to CloudFS Share.

Constructors
------------
Share
^^^^^

.. java:constructor:: public Share(RESTAdapter restAdapter, ShareItem shareItem, ItemMeta meta)
   :outertype: Share

   Initializes a new instance of Share.

   :param restAdapter: The REST Adapter instance.
   :param shareItem: Information of the shared item.
   :param meta: The share meta data returned from REST Adapter.

Methods
-------
changeAttributes
^^^^^^^^^^^^^^^^

.. java:method:: public boolean changeAttributes(HashMap<String, String> values, String sharePassword) throws IOException, BitcasaException
   :outertype: Share

   Changes the share attributes according to the values provided.

   :param values: The values to be changed.
   :param sharePassword: The current share password.
   :throws BitcasaException: If a CloudFS API error occurs.
   :throws IOException: If a network error occurs.
   :return: A value indicating whether the operation was successful or not.

delete
^^^^^^

.. java:method:: public boolean delete() throws BitcasaException
   :outertype: Share

   Deletes the share.

   :throws BitcasaException: If a CloudFS API error occurs.
   :return: The value indicating whether the operation was successful or not.

getApplicationData
^^^^^^^^^^^^^^^^^^

.. java:method:: public ApplicationData getApplicationData()
   :outertype: Share

   Gets the share's application data.

   :return: The share's application data.

getDateContentLastModified
^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public Date getDateContentLastModified()
   :outertype: Share

   Gets the share's content last modified date.

   :return: The content last modified date.

getDateMetaLastModified
^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public Date getDateMetaLastModified()
   :outertype: Share

   Gets the share's meta last modified date.

   :return: The share's meta last modified date.

getName
^^^^^^^

.. java:method:: public String getName()
   :outertype: Share

   Gets the share name.

   :return: The share name.

getShareKey
^^^^^^^^^^^

.. java:method:: public String getShareKey()
   :outertype: Share

   Gets the share key.

   :return: The share key.

getSize
^^^^^^^

.. java:method:: public long getSize()
   :outertype: Share

   Gets the share size.

   :return: The share size.

getUrl
^^^^^^

.. java:method:: public String getUrl()
   :outertype: Share

   Gets the share url.

   :return: The share url.

list
^^^^

.. java:method:: public Item[] list() throws IOException, BitcasaException
   :outertype: Share

   List the shared items created by the current user.

   :throws BitcasaException: If a CloudFS API error occurs.
   :throws IOException: If a network error occurs.
   :return: The share item array.

receive
^^^^^^^

.. java:method:: public Item[] receive(String path, BitcasaRESTConstants.Exists exists) throws IOException, BitcasaException
   :outertype: Share

   Receives the share items to the specified path.

   :param path: The path where the shares should be received.
   :param exists: The action to take if the files already exists.
   :throws BitcasaException: If a CloudFS API error occurs.
   :throws IOException: If a network error occurs.
   :return: The received item array.

setName
^^^^^^^

.. java:method:: public void setName(String name)
   :outertype: Share

   Sets the share name.

   :param name: The share name.

setName
^^^^^^^

.. java:method:: public boolean setName(String newName, String password) throws IOException, BitcasaException
   :outertype: Share

   Sets a new name for the current share.

   :param newName: The new share name.
   :param password: The share password.
   :throws BitcasaException: If a CloudFS API error occurs.
   :throws IOException: If a network error occurs.
   :return: A value indicating whether the operation was successful or not.

setPassword
^^^^^^^^^^^

.. java:method:: public boolean setPassword(String newPassword, String oldPassword) throws IOException, BitcasaException
   :outertype: Share

   Sets a new password for the given share.

   :param newPassword: The new share password.
   :param oldPassword: The current share password.
   :throws BitcasaException: If a CloudFS API error occurs.
   :throws IOException: If a network error occurs.
   :return: A value indicating whether the operation was successful or not.

toString
^^^^^^^^

.. java:method:: @Override public String toString()
   :outertype: Share

   Creates a string containing a concise, human-readable description of Share object.

   :return: The printable representation of Share object.

