.. java:import:: com.bitcasa.cloudfs BitcasaError

BitcasaRequestErrorException
============================

.. java:package:: com.bitcasa.cloudfs.exception
   :noindex:

.. java:type:: public class BitcasaRequestErrorException extends BitcasaException

   The BitcasaRequestErrorException class.

Constructors
------------
BitcasaRequestErrorException
^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:constructor::  BitcasaRequestErrorException()
   :outertype: BitcasaRequestErrorException

   Initializes the BitcasaRequestErrorException instance.

BitcasaRequestErrorException
^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:constructor:: public BitcasaRequestErrorException(String message)
   :outertype: BitcasaRequestErrorException

   Initializes the BitcasaRequestErrorException instance.

   :param message: The error message.

BitcasaRequestErrorException
^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:constructor:: public BitcasaRequestErrorException(int code, String message)
   :outertype: BitcasaRequestErrorException

   Initializes the BitcasaRequestErrorException instance.

   :param code: The error code.
   :param message: The error message.

BitcasaRequestErrorException
^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:constructor:: public BitcasaRequestErrorException(BitcasaError error)
   :outertype: BitcasaRequestErrorException

   Initializes the BitcasaRequestErrorException instance.

   :param error: The error object.

