.. java:import:: android.util Log

.. java:import:: com.bitcasa.cloudfs BitcasaError

.. java:import:: com.bitcasa.cloudfs Credential

.. java:import:: com.bitcasa.cloudfs Utils.BitcasaProgressListener

.. java:import:: com.bitcasa.cloudfs Utils.BitcasaRESTConstants

.. java:import:: com.bitcasa.cloudfs.exception BitcasaException

.. java:import:: com.bitcasa.cloudfs.model BitcasaResponse

.. java:import:: com.bitcasa.cloudfs.model ItemMeta

.. java:import:: com.google.gson Gson

.. java:import:: java.io File

.. java:import:: java.io FileInputStream

.. java:import:: java.io IOException

.. java:import:: java.io InputStream

.. java:import:: java.io OutputStream

.. java:import:: java.io OutputStreamWriter

.. java:import:: java.io PrintWriter

.. java:import:: java.net URL

.. java:import:: java.util HashMap

.. java:import:: java.util Map

.. java:import:: javax.net.ssl HttpsURLConnection

MultipartUpload
===============

.. java:package:: com.bitcasa.cloudfs.api
   :noindex:

.. java:type:: public class MultipartUpload

   The MultipartUpload class provides utility methods for the file upload process.

Constructors
------------
MultipartUpload
^^^^^^^^^^^^^^^

.. java:constructor:: public MultipartUpload(Credential credential, String url, BitcasaRESTUtility utility) throws IOException
   :outertype: MultipartUpload

   Initializes an instance of MultipartUpload.

   :param credential: The application credentials.
   :param url: The file url.
   :param utility: The rest utility instance.
   :throws IOException: If a network error occurs.

Methods
-------
addFile
^^^^^^^

.. java:method:: public void addFile(File uploadFile, BitcasaProgressListener listener) throws IOException
   :outertype: MultipartUpload

   Add a file to the print writer to upload.

   :param uploadFile: The file to be uploaded.
   :param listener: The upload progress listener.
   :throws IOException: If a network error occurs.

addUploadFormField
^^^^^^^^^^^^^^^^^^

.. java:method:: public void addUploadFormField(CharSequence fieldName, CharSequence fieldValue)
   :outertype: MultipartUpload

   Adds an upload form field to the print writer.

   :param fieldName: The upload form field name.
   :param fieldValue: The upload form field value.

finishUpload
^^^^^^^^^^^^

.. java:method:: public com.bitcasa.cloudfs.File finishUpload(RESTAdapter restAdapter, String parentPath) throws BitcasaException
   :outertype: MultipartUpload

   Finishes the file upload process.

   :param restAdapter: The REST Adapter instance.
   :param parentPath: The parent path.
   :throws BitcasaException: If a CloudFS API error occurs.
   :return: The uploaded file.

