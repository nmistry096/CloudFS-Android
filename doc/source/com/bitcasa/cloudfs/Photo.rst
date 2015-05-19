.. java:import:: com.bitcasa.cloudfs.api RESTAdapter

.. java:import:: com.bitcasa.cloudfs.model ItemMeta

Photo
=====

.. java:package:: com.bitcasa.cloudfs
   :noindex:

.. java:type:: public class Photo extends File

   The Photo class provides accessibility to CloudFS Photo.

Constructors
------------
Photo
^^^^^

.. java:constructor:: public Photo(RESTAdapter restAdapter, ItemMeta itemMeta, String absoluteParentPath, String parentState, String shareKey)
   :outertype: Photo

   Initializes an instance of Photo.

   :param restAdapter: The REST Adapter instance.
   :param itemMeta: The photo meta data returned from REST Adapter.
   :param absoluteParentPath: The absolute parent path of this photo.
   :param parentState: The parent state of the item.
   :param shareKey: The share key of the item if the item is of type share.

