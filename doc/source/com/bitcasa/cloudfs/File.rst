.. java:import:: android.os Parcel

.. java:import:: android.os Parcelable

.. java:import:: com.bitcasa.cloudfs Utils.BitcasaProgressListener

.. java:import:: com.bitcasa.cloudfs Utils.BitcasaRESTConstants

.. java:import:: com.bitcasa.cloudfs.api RESTAdapter

.. java:import:: com.bitcasa.cloudfs.exception BitcasaException

.. java:import:: com.bitcasa.cloudfs.model ItemMeta

.. java:import:: java.io IOException

.. java:import:: java.io InputStream

.. java:import:: java.util AbstractMap

.. java:import:: java.util HashMap

.. java:import:: java.util Map

File
====

.. java:package:: com.bitcasa.cloudfs
   :noindex:

.. java:type:: public class File extends Item

   The File class provides accessibility to CloudFS File.

Fields
------
CREATOR
^^^^^^^

.. java:field:: public static final Parcelable.Creator<File> CREATOR
   :outertype: File

   {@inheritDoc}

Constructors
------------
File
^^^^

.. java:constructor:: public File(RESTAdapter restAdapter, ItemMeta itemMeta, String absoluteParentPath, String parentState, String shareKey)
   :outertype: File

   Initializes an instance of CloudFS File.

   :param restAdapter: The REST Adapter instance.
   :param itemMeta: The file meta data returned from REST Adapter.
   :param absoluteParentPath: The absolute parent path of this file.
   :param parentState: The parent state of the item.
   :param shareKey: The share key of the item if the item is of type share.

File
^^^^

.. java:constructor:: public File(Parcel source)
   :outertype: File

   Initializes the Item instance.

   :param source: The parcel object parameter.

Methods
-------
changeAttributes
^^^^^^^^^^^^^^^^

.. java:method:: @Override public boolean changeAttributes(Map<String, String> values, BitcasaRESTConstants.VersionExists ifConflict) throws BitcasaException
   :outertype: File

   Changes the specified item attributes.

   :param values: The attributes to be changed.
   :param ifConflict: The action to be taken if a conflict occurs.
   :throws BitcasaException: If a CloudFS API error occurs.
   :return: boolean A value indicating whether the operation was successful or not.

download
^^^^^^^^

.. java:method:: public void download(String localDestinationPath, BitcasaProgressListener listener) throws BitcasaException, IOException
   :outertype: File

   Downloads this file into the given local destination path.

   :param localDestinationPath: Local destination path to download the file.
   :param listener: The BitcasaProgressListener to track the file download progress.
   :throws IOException: If a network error occurs.
   :throws BitcasaException: If a CloudFS API error occurs.

downloadUrl
^^^^^^^^^^^

.. java:method:: public String downloadUrl() throws BitcasaException
   :outertype: File

   Gets the file download url. Please note that this download url will expire within 24 hours.

   :throws BitcasaException: If a CloudFS API error occurs.
   :return: The file download url.

getExtension
^^^^^^^^^^^^

.. java:method:: public String getExtension()
   :outertype: File

   Gets the file extension.

   :return: The file extension.

getMime
^^^^^^^

.. java:method:: public String getMime()
   :outertype: File

   Gets the file mime type.

   :return: The mime type.

getSize
^^^^^^^

.. java:method:: public long getSize()
   :outertype: File

   Gets the file size.

   :return: The file size.

read
^^^^

.. java:method:: public InputStream read() throws BitcasaException
   :outertype: File

   Returns the InputStream of this file.

   :throws BitcasaException: If a CloudFS API error occurs.
   :return: The file InputStream.

setMime
^^^^^^^

.. java:method:: public boolean setMime(String mime) throws BitcasaException
   :outertype: File

   Sets the file mime type.

   :throws BitcasaException: If a CloudsFS API error occurs.
   :return: A value indicating whether the operation was successful or not.

versions
^^^^^^^^

.. java:method:: public File[] versions(int startVersion, int endVersion, int limit) throws BitcasaException, IOException
   :outertype: File

   Gets the file versions.

   :param startVersion: The starting file version.
   :param endVersion: The ending file version.
   :param limit: The file version list limit.
   :throws IOException: If a network error occurs.
   :return: The file version list.

writeToParcel
^^^^^^^^^^^^^

.. java:method:: @Override public void writeToParcel(Parcel out, int flags)
   :outertype: File

   Flatten this object in to a Parcel.

   :param out: The Parcel in which the object should be written.
   :param flags: Additional flags about how the object should be written. May be 0 or PARCELABLE_WRITE_RETURN_VALUE

