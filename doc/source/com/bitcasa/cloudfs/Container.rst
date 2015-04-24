.. java:import:: com.bitcasa.cloudfs.api RESTAdapter

.. java:import:: com.bitcasa.cloudfs.exception BitcasaException

.. java:import:: com.bitcasa.cloudfs.model ItemMeta

.. java:import:: java.io IOException

Container
=========

.. java:package:: com.bitcasa.cloudfs
   :noindex:

.. java:type:: public class Container extends Item

   The Container class provides accessibility to CloudFS Container.

Constructors
------------
Container
^^^^^^^^^

.. java:constructor:: public Container(RESTAdapter restAdapter, ItemMeta meta, String absoluteParentPath)
   :outertype: Container

   Initializes an instance of Container.

   :param restAdapter: The REST Adapter instance.
   :param meta: The container meta data returned from REST Adapter.
   :param absoluteParentPath: The absolute parent path of this container.

Methods
-------
list
^^^^

.. java:method:: public Item[] list() throws IOException, BitcasaException
   :outertype: Container

   Lists the files and folders in the container.

   :return: The list of files and folders in the container.

