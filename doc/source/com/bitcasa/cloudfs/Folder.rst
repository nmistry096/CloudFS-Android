.. java:import:: com.bitcasa.cloudfs Utils.BitcasaProgressListener

.. java:import:: com.bitcasa.cloudfs Utils.BitcasaRESTConstants

.. java:import:: com.bitcasa.cloudfs Utils.BitcasaRESTConstants.Exists

.. java:import:: com.bitcasa.cloudfs.api RESTAdapter

.. java:import:: com.bitcasa.cloudfs.exception BitcasaException

.. java:import:: com.bitcasa.cloudfs.model ItemMeta

.. java:import:: java.io IOException

Folder
======

.. java:package:: com.bitcasa.cloudfs
   :noindex:

.. java:type:: public class Folder extends Container

   The Folder class provides accessibility to CloudFS Folder.

Constructors
------------
Folder
^^^^^^

.. java:constructor:: public Folder(RESTAdapter restAdapter, ItemMeta meta, String absoluteParentPath)
   :outertype: Folder

   Initializes an instance of CloudFS Folder.

   :param restAdapter: The REST Adapter instance.
   :param meta: The folder meta data returned from REST Adapter.
   :param absoluteParentPath: The absolute parent path of this folder.

Methods
-------
createFolder
^^^^^^^^^^^^

.. java:method:: public Folder createFolder(String name, Exists exist) throws IOException, BitcasaException
   :outertype: Folder

   Creates a new folder by name provided.

   :param name: The name of the folder to be created.
   :param exist: The action to take if the folder to be created already exists.
   :throws IOException: If a network error occurs.
   :return: The folder that was created.

upload
^^^^^^

.. java:method:: public void upload(String filesystemPath, BitcasaProgressListener listener, BitcasaRESTConstants.Exists exists) throws IOException, BitcasaException
   :outertype: Folder

   Uploads a file into the specified file system path.

   :param filesystemPath: The destination file system path of the upload.
   :param exists: Action to take if the item already exists.
   :param listener: The progress listener to track the upload progress.
   :throws BitcasaException: If a CloudFS API error occurs.
   :throws IOException: If a network error occurs.

