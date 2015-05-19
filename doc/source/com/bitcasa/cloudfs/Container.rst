.. java:import:: android.os Parcel

.. java:import:: android.os Parcelable

.. java:import:: com.bitcasa.cloudfs Utils.BitcasaRESTConstants

.. java:import:: com.bitcasa.cloudfs.api RESTAdapter

.. java:import:: com.bitcasa.cloudfs.exception BitcasaException

.. java:import:: com.bitcasa.cloudfs.model ItemMeta

.. java:import:: java.io IOException

.. java:import:: java.util Map

Container
=========

.. java:package:: com.bitcasa.cloudfs
   :noindex:

.. java:type:: public class Container extends Item

   The Container class provides accessibility to CloudFS Container.

Fields
------
CREATOR
^^^^^^^

.. java:field:: public static final Parcelable.Creator<Container> CREATOR
   :outertype: Container

   {@inheritDoc}

Constructors
------------
Container
^^^^^^^^^

.. java:constructor:: public Container(RESTAdapter restAdapter, ItemMeta meta, String absoluteParentPath, String parentState, String shareKey)
   :outertype: Container

   Initializes an instance of Container.

   :param restAdapter: The REST Adapter instance.
   :param meta: The container meta data returned from REST Adapter.
   :param absoluteParentPath: The absolute parent path of this container.
   :param parentState: The parent state of the item.
   :param shareKey: The share key of the item if the item is of type share.

Container
^^^^^^^^^

.. java:constructor:: public Container(Parcel source)
   :outertype: Container

   Initializes the Folder instance.

   :param source: The parcel object parameter.

Methods
-------
changeAttributes
^^^^^^^^^^^^^^^^

.. java:method:: @Override public boolean changeAttributes(Map<String, String> values, BitcasaRESTConstants.VersionExists ifConflict) throws BitcasaException
   :outertype: Container

   Changes the specified item attributes.

   :param values: The attributes to be changed.
   :param ifConflict: The action to be taken if a conflict occurs.
   :throws BitcasaException: If a CloudFS API error occurs.
   :return: boolean A value indicating whether the operation was successful or not.

list
^^^^

.. java:method:: public Item[] list() throws IOException, BitcasaException
   :outertype: Container

   Lists the files and folders in the container.

   :return: The list of files and folders in the container.

