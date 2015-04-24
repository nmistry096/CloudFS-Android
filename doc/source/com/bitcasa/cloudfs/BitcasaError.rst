BitcasaError
============

.. java:package:: com.bitcasa.cloudfs
   :noindex:

.. java:type:: public class BitcasaError

   The BitcasaError class provides custom errors.

Constructors
------------
BitcasaError
^^^^^^^^^^^^

.. java:constructor:: public BitcasaError()
   :outertype: BitcasaError

   BitcasaError default constructor.

BitcasaError
^^^^^^^^^^^^

.. java:constructor:: public BitcasaError(int code, String message, String data)
   :outertype: BitcasaError

   Initializes an instance of BitcasaError with error data.

   :param code: The error code.
   :param message: The error message.
   :param data: The data.

BitcasaError
^^^^^^^^^^^^

.. java:constructor:: public BitcasaError(int code, String message)
   :outertype: BitcasaError

   Initializes an instance BitcasaError.

   :param code: The error code.
   :param message: The error message.

Methods
-------
getCode
^^^^^^^

.. java:method:: public int getCode()
   :outertype: BitcasaError

   Gets the error code.

   :return: The error code.

getData
^^^^^^^

.. java:method:: public String getData()
   :outertype: BitcasaError

   Gets the data value.

   :return: The data value.

getMessage
^^^^^^^^^^

.. java:method:: public String getMessage()
   :outertype: BitcasaError

   Gets the error message.

   :return: The error message.

setCode
^^^^^^^

.. java:method:: public void setCode(int code)
   :outertype: BitcasaError

   Sets the error code.

   :param code: The error code.

setData
^^^^^^^

.. java:method:: public void setData(String data)
   :outertype: BitcasaError

   Sets the data value.

   :param data: The data parameter.

setMessage
^^^^^^^^^^

.. java:method:: public void setMessage(String Message)
   :outertype: BitcasaError

   Sets the error message.

   :param Message: The error message.

