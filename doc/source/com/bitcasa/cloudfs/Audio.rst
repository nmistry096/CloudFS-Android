.. java:import:: com.bitcasa.cloudfs.api RESTAdapter

.. java:import:: com.bitcasa.cloudfs.model ItemMeta

Audio
=====

.. java:package:: com.bitcasa.cloudfs
   :noindex:

.. java:type:: public class Audio extends File

   The Audio class provides accessibility to CloudFS Audio.

Constructors
------------
Audio
^^^^^

.. java:constructor:: public Audio(RESTAdapter restAdapter, ItemMeta itemMeta, String absoluteParentPath, String parentState, String shareKey)
   :outertype: Audio

   Initializes an instance of Audio.

   :param restAdapter: The REST Adapter instance.
   :param itemMeta: The audio meta data returned from REST Adapter.
   :param absoluteParentPath: The absolute parent path of this audio.
   :param parentState: The parent state of the item.
   :param shareKey: The share key of the item if the item is of type share.

