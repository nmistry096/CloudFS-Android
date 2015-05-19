.. java:import:: android.os Parcel

.. java:import:: android.os Parcelable

.. java:import:: com.bitcasa.cloudfs Utils.BitcasaProgressListener

.. java:import:: com.bitcasa.cloudfs Utils.BitcasaRESTConstants

.. java:import:: com.bitcasa.cloudfs.api RESTAdapter

.. java:import:: com.bitcasa.cloudfs.exception BitcasaException

.. java:import:: com.bitcasa.cloudfs.model ItemMeta

.. java:import:: java.io IOException

.. java:import:: java.util Map

Folder
======

.. java:package:: com.bitcasa.cloudfs
   :noindex:

.. java:type:: public class Folder extends Container

   The Folder class provides accessibility to CloudFS Folder.

Fields
------
CREATOR
^^^^^^^

.. java:field:: public static final Parcelable.Creator<Folder> CREATOR
   :outertype: Folder

   {@inheritDoc}

Constructors
------------
Folder
^^^^^^

.. java:constructor:: public Folder(RESTAdapter restAdapter, ItemMeta meta, String absoluteParentPath, String parentState, String shareKey)
   :outertype: Folder

   Initializes an instance of CloudFS Folder.

   :param restAdapter: The REST Adapter instance.
   :param meta: The folder meta data returned from REST Adapter.
   :param absoluteParentPath: The absolute parent path of this folder.
   :param parentState: The parent state of the item.
   :param shareKey: The share key of the item if the item is of type share.

Folder
^^^^^^

.. java:constructor:: public Folder(Parcel source)
   :outertype: Folder

   Initializes the Folder instance.

   :param source: The parcel object parameter.

Methods
-------
changeAttributes
^^^^^^^^^^^^^^^^

.. java:method:: @Override public boolean changeAttributes(Map<String, String> values, BitcasaRESTConstants.VersionExists ifConflict) throws BitcasaException
   :outertype: Folder

   Changes the specified item attributes.

   :param values: The attributes to be changed.
   :param ifConflict: The action to be taken if a conflict occurs.
   :throws BitcasaException: If a CloudFS API error occurs.
   :return: boolean A value indicating whether the operation was successful or not.

createFolder
^^^^^^^^^^^^

.. java:method:: public Folder createFolder(String name, BitcasaRESTConstants.Exists exist) throws IOException, BitcasaException
   :outertype: Folder

   Creates a new folder by name provided.

   :param name: The name of the folder to be created.
   :param exist: The action to take if the folder to be created already exists.
   :throws IOException: If a network error occurs.
   :return: The folder that was created.

upload
^^^^^^

.. java:method:: public File upload(String filesystemPath, BitcasaProgressListener listener, BitcasaRESTConstants.Exists exists) throws IOException, BitcasaException
   :outertype: Folder

   Uploads a file into the specified file system path.

   :param filesystemPath: The destination file system path of the upload.
   :param exists: Action to take if the item already exists.
   :param listener: The progress listener to track the upload progress.
   :throws BitcasaException: If a CloudFS API error occurs.
   :throws IOException: If a network error occurs.

