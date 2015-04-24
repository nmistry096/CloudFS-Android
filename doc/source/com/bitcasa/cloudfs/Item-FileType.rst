.. java:import:: com.bitcasa.cloudfs Utils.BitcasaRESTConstants

.. java:import:: com.bitcasa.cloudfs Utils.BitcasaRESTConstants.Exists

.. java:import:: com.bitcasa.cloudfs Utils.BitcasaRESTConstants.RestoreMethod

.. java:import:: com.bitcasa.cloudfs Utils.BitcasaRESTConstants.VersionExists

.. java:import:: com.bitcasa.cloudfs.api RESTAdapter

.. java:import:: com.bitcasa.cloudfs.exception BitcasaException

.. java:import:: com.bitcasa.cloudfs.model ApplicationData

.. java:import:: com.bitcasa.cloudfs.model ItemMeta

.. java:import:: com.bitcasa.cloudfs.model Storage

.. java:import:: org.json JSONObject

.. java:import:: java.io IOException

.. java:import:: java.io UnsupportedEncodingException

.. java:import:: java.util Date

.. java:import:: java.util HashMap

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

.. java:field::  String FILE
   :outertype: Item.FileType

FOLDER
^^^^^^

.. java:field::  String FOLDER
   :outertype: Item.FileType

