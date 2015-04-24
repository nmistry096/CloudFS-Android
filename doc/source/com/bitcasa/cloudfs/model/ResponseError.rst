.. java:import:: com.google.gson JsonElement

ResponseError
=============

.. java:package:: com.bitcasa.cloudfs.model
   :noindex:

.. java:type:: public class ResponseError

   Model class for errors in responses.

Constructors
------------
ResponseError
^^^^^^^^^^^^^

.. java:constructor:: public ResponseError(Integer code, String message, JsonElement data)
   :outertype: ResponseError

   Initializes a new instance of a response error.

   :param code: Error code.
   :param message: Error message.
   :param data: Extra error data.

Methods
-------
getCode
^^^^^^^

.. java:method:: public final Integer getCode()
   :outertype: ResponseError

   Gets the error code.

   :return: Error code.

getData
^^^^^^^

.. java:method:: public final JsonElement getData()
   :outertype: ResponseError

   Gets extra error data.

   :return: Extra error data.

getMessage
^^^^^^^^^^

.. java:method:: public final String getMessage()
   :outertype: ResponseError

   Gets the error message.

   :return: Error message.

