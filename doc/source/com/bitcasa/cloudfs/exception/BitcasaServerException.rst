.. java:import:: com.bitcasa.cloudfs BitcasaError

BitcasaServerException
======================

.. java:package:: com.bitcasa.cloudfs.exception
   :noindex:

.. java:type:: public class BitcasaServerException extends BitcasaException

   The BitcasaServerException class.

Constructors
------------
BitcasaServerException
^^^^^^^^^^^^^^^^^^^^^^

.. java:constructor::  BitcasaServerException()
   :outertype: BitcasaServerException

   Initializes the BitcasaServerException instance.

BitcasaServerException
^^^^^^^^^^^^^^^^^^^^^^

.. java:constructor:: public BitcasaServerException(String message)
   :outertype: BitcasaServerException

   Initializes the BitcasaServerException instance.

   :param message: The error message.

BitcasaServerException
^^^^^^^^^^^^^^^^^^^^^^

.. java:constructor:: public BitcasaServerException(int code, String message)
   :outertype: BitcasaServerException

   Initializes the BitcasaServerException instance.

   :param code: error code.
   :param message: The error message.

BitcasaServerException
^^^^^^^^^^^^^^^^^^^^^^

.. java:constructor:: public BitcasaServerException(BitcasaError error)
   :outertype: BitcasaServerException

   Initializes the BitcasaServerException instance.

   :param error: The error object.

