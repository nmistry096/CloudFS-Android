.. java:import:: com.bitcasa.cloudfs.api RESTAdapter

.. java:import:: com.bitcasa.cloudfs.model ItemMeta

Video
=====

.. java:package:: com.bitcasa.cloudfs
   :noindex:

.. java:type:: public class Video extends File

   The Video class provides accessibility to CloudFS Video.

Constructors
------------
Video
^^^^^

.. java:constructor:: public Video(RESTAdapter restAdapter, ItemMeta itemMeta, String absoluteParentPath)
   :outertype: Video

   Initializes an instance of Video.

   :param restAdapter: The REST Adapter instance.
   :param itemMeta: The video meta data returned from REST Adapter.
   :param absoluteParentPath: The absolute parent path of this video.

