.. java:import:: com.google.gson JsonElement

BitcasaResponse
===============

.. java:package:: com.bitcasa.cloudfs.model
   :noindex:

.. java:type:: public class BitcasaResponse

   Model class for API responses.

Constructors
------------
BitcasaResponse
^^^^^^^^^^^^^^^

.. java:constructor:: public BitcasaResponse(ResponseError error, JsonElement result)
   :outertype: BitcasaResponse

   Initializes a new instance of a Bitcasa response.

   :param error: Response error.
   :param result: Response result.

Methods
-------
getError
^^^^^^^^

.. java:method:: public final ResponseError getError()
   :outertype: BitcasaResponse

   Gets the response error.

   :return: Response error.

getResult
^^^^^^^^^

.. java:method:: public final JsonElement getResult()
   :outertype: BitcasaResponse

   Gets the response result.

   :return: Response result.

