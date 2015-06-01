.. java:import:: com.google.gson JsonElement

Storage
=======

.. java:package:: com.bitcasa.cloudfs.model
   :noindex:

.. java:type:: public class Storage

   Model class for storage.

Constructors
------------
Storage
^^^^^^^

.. java:constructor:: public Storage(Long usage, Long limit, Boolean otl)
   :outertype: Storage

   Initializes a new instance of storage details.

   :param usage: Used storage amount.
   :param limit: Storage limit.
   :param otl: Over the limit.

Methods
-------
getLimit
^^^^^^^^

.. java:method:: public final Long getLimit()
   :outertype: Storage

   Gets the storage limit.

   :return: Storage limit.

getOtl
^^^^^^

.. java:method:: public final Boolean getOtl()
   :outertype: Storage

   Gets whether the storage limit is over.

   :return: Over the limit.

getUsage
^^^^^^^^

.. java:method:: public final Long getUsage()
   :outertype: Storage

   Gets the used storage amount.

   :return: Used storage amount.

