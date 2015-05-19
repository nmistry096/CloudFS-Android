.. java:import:: com.bitcasa.cloudfs BitcasaError

BitcasaException
================

.. java:package:: com.bitcasa.cloudfs.exception
   :noindex:

.. java:type:: public class BitcasaException extends Exception

   The BitcasaException class.

Constructors
------------
BitcasaException
^^^^^^^^^^^^^^^^

.. java:constructor::  BitcasaException()
   :outertype: BitcasaException

   Initializes the BitcasaException instance.

BitcasaException
^^^^^^^^^^^^^^^^

.. java:constructor:: public BitcasaException(String message)
   :outertype: BitcasaException

   Initializes the BitcasaException instance.

   :param message: The error message.

BitcasaException
^^^^^^^^^^^^^^^^

.. java:constructor:: public BitcasaException(int code, String message)
   :outertype: BitcasaException

   Initializes the BitcasaException instance.

   :param code: The error code.
   :param message: The error message.

BitcasaException
^^^^^^^^^^^^^^^^

.. java:constructor:: public BitcasaException(BitcasaError error)
   :outertype: BitcasaException

   Initializes the BitcasaException instance.

   :param error: The error object.

BitcasaException
^^^^^^^^^^^^^^^^

.. java:constructor:: public BitcasaException(Throwable throwable)
   :outertype: BitcasaException

   Initializes the BitcasaException instance.

   :param throwable: The throwable object.

