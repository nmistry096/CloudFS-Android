.. java:import:: com.bitcasa.cloudfs Utils.BitcasaProgressListener

.. java:import:: com.bitcasa.cloudfs Utils.BitcasaRESTConstants

.. java:import:: com.bitcasa.cloudfs.api RESTAdapter

.. java:import:: com.bitcasa.cloudfs.exception BitcasaException

.. java:import:: com.bitcasa.cloudfs.model ItemMeta

.. java:import:: java.io IOException

.. java:import:: java.io InputStream

.. java:import:: java.util HashMap

File
====

.. java:package:: com.bitcasa.cloudfs
   :noindex:

.. java:type:: public class File extends Item

   The File class provides accessibility to CloudFS File.

Constructors
------------
File
^^^^

.. java:constructor:: public File(RESTAdapter restAdapter, ItemMeta itemMeta, String absoluteParentPath)
   :outertype: File

   Initializes an instance of CloudFS File.

   :param restAdapter: The REST Adapter instance.
   :param itemMeta: The file meta data returned from REST Adapter.
   :param absoluteParentPath: The absolute parent path of this file.

Methods
-------
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

.. java:method:: public String downloadUrl() throws IOException, BitcasaException
   :outertype: File

   Gets the file download url. Please note that this download url will expire within 24 hours.

   :throws BitcasaException: If a CloudFS API error occurs.
   :throws IOException: If a network error occurs.
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

.. java:method:: public boolean setMime(String mime) throws BitcasaException, IOException
   :outertype: File

   Sets the file mime type.

   :throws IOException: If a network error occurs.
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

