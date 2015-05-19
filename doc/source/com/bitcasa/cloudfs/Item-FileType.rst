.. java:import:: android.os Parcel

.. java:import:: android.os Parcelable

.. java:import:: com.bitcasa.cloudfs Utils.BitcasaRESTConstants

.. java:import:: com.bitcasa.cloudfs.api RESTAdapter

.. java:import:: com.bitcasa.cloudfs.exception BitcasaException

.. java:import:: com.bitcasa.cloudfs.model ItemMeta

.. java:import:: com.google.gson JsonObject

.. java:import:: com.google.gson JsonParser

.. java:import:: java.io IOException

.. java:import:: java.io UnsupportedEncodingException

.. java:import:: java.util AbstractMap

.. java:import:: java.util Date

.. java:import:: java.util HashMap

.. java:import:: java.util Map

Item.FileType
=============

.. java:package:: com.bitcasa.cloudfs
   :noindex:

.. java:type:: public interface FileType
   :outertype: Item

   File type interface containing the item types.

Fields
------
FILE
^^^^

.. java:field:: final String FILE
   :outertype: Item.FileType

FOLDER
^^^^^^

.. java:field:: final String FOLDER
   :outertype: Item.FileType

