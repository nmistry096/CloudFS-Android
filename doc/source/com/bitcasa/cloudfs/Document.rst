.. java:import:: com.bitcasa.cloudfs.api RESTAdapter

.. java:import:: com.bitcasa.cloudfs.model ItemMeta

Document
========

.. java:package:: com.bitcasa.cloudfs
   :noindex:

.. java:type:: public class Document extends File

   The Document class provides accessibility to CloudFS Document.

Constructors
------------
Document
^^^^^^^^

.. java:constructor::  Document(RESTAdapter restAdapter, ItemMeta meta, String absoluteParentPath, String parentState, String shareKey)
   :outertype: Document

   Initializes an instance of Document.

   :param restAdapter: The REST Adapter instance.
   :param meta: The document meta data returned from REST Adapter.
   :param absoluteParentPath: The absolute parent path of this document.
   :param parentState: The parent state of the item.
   :param shareKey: The share key of the item if the item is of type share.

