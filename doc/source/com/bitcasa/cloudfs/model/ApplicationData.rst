ApplicationData
===============

.. java:package:: com.bitcasa.cloudfs.model
   :noindex:

.. java:type:: public class ApplicationData

   A Wrapper class of application data.

Constructors
------------
ApplicationData
^^^^^^^^^^^^^^^

.. java:constructor:: public ApplicationData()
   :outertype: ApplicationData

   Initializes an empty ApplicationData object.

ApplicationData
^^^^^^^^^^^^^^^

.. java:constructor:: public ApplicationData(String originalPath, String relativePath, String nonce, String payload, String digest, String albumArt)
   :outertype: ApplicationData

   Initializes an instance of ApplicationData.

   :param originalPath: The original path.
   :param relativePath: The relative id path.
   :param nonce: The nonce data.
   :param payload: The payload data.
   :param digest: The digest value.
   :param albumArt: The album art.

Methods
-------
getAlbumArt
^^^^^^^^^^^

.. java:method:: public String getAlbumArt()
   :outertype: ApplicationData

   Gets the album art.

   :return: The album art.

getDigest
^^^^^^^^^

.. java:method:: public String getDigest()
   :outertype: ApplicationData

   Gets the digest data.

   :return: The digest data.

getNonce
^^^^^^^^

.. java:method:: public String getNonce()
   :outertype: ApplicationData

   Gets the nonce data.

   :return: The nonce data.

getOriginalPath
^^^^^^^^^^^^^^^

.. java:method:: public String getOriginalPath()
   :outertype: ApplicationData

   Gets the original path.

   :return: The original path.

getPayload
^^^^^^^^^^

.. java:method:: public String getPayload()
   :outertype: ApplicationData

   Gets the payload data.

   :return: The payload data.

getRelativeIdPath
^^^^^^^^^^^^^^^^^

.. java:method:: public String getRelativeIdPath()
   :outertype: ApplicationData

   Gets the relative id path.

   :return: The application relative id path.

setAlbumArt
^^^^^^^^^^^

.. java:method:: public void setAlbumArt(String albumArt)
   :outertype: ApplicationData

   Sets the album art.

   :param albumArt: The application album art.

setDigest
^^^^^^^^^

.. java:method:: public void setDigest(String digest)
   :outertype: ApplicationData

   Sets the digest data.

   :param digest: The digest data.

setNonce
^^^^^^^^

.. java:method:: public void setNonce(String nonce)
   :outertype: ApplicationData

   Sets the nonce data.

   :param nonce: The nonce data.

setOriginalPath
^^^^^^^^^^^^^^^

.. java:method:: public void setOriginalPath(String originalPath)
   :outertype: ApplicationData

   Sets the original path.

   :param originalPath: The original path.

setPayload
^^^^^^^^^^

.. java:method:: public void setPayload(String payload)
   :outertype: ApplicationData

   Sets the payload data.

   :param payload: The payload data.

setRelativeIdPath
^^^^^^^^^^^^^^^^^

.. java:method:: public void setRelativeIdPath(String relativeIdPath)
   :outertype: ApplicationData

   Sets the application relative id path.

   :param relativeIdPath: The relative id path.

