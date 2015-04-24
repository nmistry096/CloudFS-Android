.. java:import:: com.bitcasa.cloudfs BitcasaError

BitcasaFileSystemException
==========================

.. java:package:: com.bitcasa.cloudfs.exception
   :noindex:

.. java:type:: public class BitcasaFileSystemException extends BitcasaException

   The BitcasaFileSystemException class.

Constructors
------------
BitcasaFileSystemException
^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:constructor::  BitcasaFileSystemException()
   :outertype: BitcasaFileSystemException

   Initializes the BitcasaFileSystemException instance.

BitcasaFileSystemException
^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:constructor:: public BitcasaFileSystemException(String message)
   :outertype: BitcasaFileSystemException

   Initializes the BitcasaFileSystemException instance.

   :param message: The error message.

BitcasaFileSystemException
^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:constructor:: public BitcasaFileSystemException(int code, String message)
   :outertype: BitcasaFileSystemException

   Initializes the BitcasaFileSystemException instance.

   :param code: The error code.
   :param message: The error message.

BitcasaFileSystemException
^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:constructor:: public BitcasaFileSystemException(BitcasaError error)
   :outertype: BitcasaFileSystemException

   Initializes the BitcasaFileSystemException instance.

   :param error: The error object.

