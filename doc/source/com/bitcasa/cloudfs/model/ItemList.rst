ItemList
========

.. java:package:: com.bitcasa.cloudfs.model
   :noindex:

.. java:type:: public class ItemList

   Model class for item meta list.

Constructors
------------
ItemList
^^^^^^^^

.. java:constructor:: public ItemList(ItemMeta meta, ItemMeta[] items)
   :outertype: ItemList

   Initializes a new instance of an item list.

   :param meta: The item meta.
   :param items: List of sub items.

Methods
-------
getItems
^^^^^^^^

.. java:method:: public final ItemMeta[] getItems()
   :outertype: ItemList

   Gets list of sub items.

   :return: List of sub items.

getMeta
^^^^^^^

.. java:method:: public final ItemMeta getMeta()
   :outertype: ItemList

   Gets item meta.

   :return: The item meta.

