SharedFolder
============

.. java:package:: com.bitcasa.cloudfs.model
   :noindex:

.. java:type:: public class SharedFolder

   Model class for item meta list.

Constructors
------------
SharedFolder
^^^^^^^^^^^^

.. java:constructor:: public SharedFolder(ShareItem shareItem, ItemMeta meta, ItemMeta[] items)
   :outertype: SharedFolder

   Initializes a new instance of a shared folder.

   :param shareItem: The share details.
   :param meta: The share item meta.
   :param items: List of sub items.

Methods
-------
getItems
^^^^^^^^

.. java:method:: public final ItemMeta[] getItems()
   :outertype: SharedFolder

   Gets list of sub items.

   :return: List of sub items.

getMeta
^^^^^^^

.. java:method:: public final ItemMeta getMeta()
   :outertype: SharedFolder

   Gets item meta.

   :return: The item meta.

getShareItem
^^^^^^^^^^^^

.. java:method:: public final ShareItem getShareItem()
   :outertype: SharedFolder

   Gets the share details.

   :return: Share details

