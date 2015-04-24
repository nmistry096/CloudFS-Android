.. java:import:: com.google.gson.annotations SerializedName

ShareItem
=========

.. java:package:: com.bitcasa.cloudfs.model
   :noindex:

.. java:type:: public class ShareItem

   Model class for a share.

Constructors
------------
ShareItem
^^^^^^^^^

.. java:constructor:: public ShareItem(String shareKey, String name, String url, String shortUrl, Integer size, long dateCreated)
   :outertype: ShareItem

   Initializes a new instance of a shared item.

   :param shareKey: ID of the share.
   :param name: Name of the share.
   :param url: Url of the share.
   :param shortUrl: Short url of the share.
   :param size: Size of the shared content.
   :param dateCreated: Created date of the share.

Methods
-------
getDateCreated
^^^^^^^^^^^^^^

.. java:method:: public final long getDateCreated()
   :outertype: ShareItem

   Gets the created date of the share.

   :return: Created date of the share.

getName
^^^^^^^

.. java:method:: public final String getName()
   :outertype: ShareItem

   Gets the name of the share.

   :return: Name of the share.

getShareKey
^^^^^^^^^^^

.. java:method:: public final String getShareKey()
   :outertype: ShareItem

   Gets the key of the share.

   :return: Key of the share.

getShortUrl
^^^^^^^^^^^

.. java:method:: public final String getShortUrl()
   :outertype: ShareItem

   Gets the short url of the share.

   :return: Short url of the share.

getSize
^^^^^^^

.. java:method:: public final Integer getSize()
   :outertype: ShareItem

   Gets the size of the shared content.

   :return: Size of the shared content.

getUrl
^^^^^^

.. java:method:: public final String getUrl()
   :outertype: ShareItem

   Gets the url of the share.

   :return: Url of the share.

