.. java:import:: com.bitcasa.cloudfs.api RESTAdapter

.. java:import:: com.bitcasa.cloudfs.exception BitcasaException

.. java:import:: com.bitcasa.cloudfs.model ShareItem

.. java:import:: java.io IOException

FileSystem
==========

.. java:package:: com.bitcasa.cloudfs
   :noindex:

.. java:type:: public class FileSystem

   The FileSystem class provides accessibility to CloudFS FileSystem.

Constructors
------------
FileSystem
^^^^^^^^^^

.. java:constructor:: public FileSystem(RESTAdapter restAdapter)
   :outertype: FileSystem

   Initializes an instance of FileSystem.

   :param restAdapter: The REST Adapter instance.

Methods
-------
createShare
^^^^^^^^^^^

.. java:method:: public Share createShare(String path, String password) throws IOException, BitcasaException
   :outertype: FileSystem

   Creates a new share.

   :param path: The full path of an item to share.
   :param password: The password of the share to be created.
   :throws BitcasaException: If a CloudFS API error occurs.
   :throws IOException: If a network error occurs.
   :return: The new share instance.

createShare
^^^^^^^^^^^

.. java:method:: public Share createShare(String[] paths, String password) throws IOException, BitcasaException
   :outertype: FileSystem

   Creates a new share.

   :param paths: The full array paths of items to share.
   :param password: The password of the share to be created.
   :throws BitcasaException: If a CloudFS API error occurs.
   :throws IOException: If a network error occurs.
   :return: The new share instance.

getItem
^^^^^^^

.. java:method:: public Item getItem(String path) throws BitcasaException
   :outertype: FileSystem

   Gets the item at the given path.

   :param path: The file system item path.
   :throws BitcasaException: If a CloudFS API error occurs.
   :return: The item at the given path.

listShares
^^^^^^^^^^

.. java:method:: public Share[] listShares() throws BitcasaException
   :outertype: FileSystem

   Lists the shares in the file system.

   :throws BitcasaException: If a CloudFS API error occurs.
   :return: The list of shares in the file system.

listTrash
^^^^^^^^^

.. java:method:: public Item[] listTrash() throws BitcasaException
   :outertype: FileSystem

   Lists the items in the trash.

   :throws BitcasaException: If a CloudFS API error occurs.
   :return: The list of trash items.

retrieveShare
^^^^^^^^^^^^^

.. java:method:: public Share retrieveShare(String shareKey, String password) throws IOException, BitcasaException
   :outertype: FileSystem

   Retrieves an existing share.

   :param shareKey: The share key.
   :param password: The password of the share.
   :throws BitcasaException: If a CloudFS API error occurs.
   :throws IOException: If a network error occurs
   :return: The share specified by the key.

root
^^^^

.. java:method:: public Folder root() throws BitcasaException
   :outertype: FileSystem

   Gets the file system root folder.

   :throws BitcasaException: If a CloudFS API error occurs.
   :return: The file system root folder.

