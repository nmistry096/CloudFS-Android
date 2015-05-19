.. java:import:: android.os Parcel

.. java:import:: android.os Parcelable

BitcasaError
============

.. java:package:: com.bitcasa.cloudfs
   :noindex:

.. java:type:: public class BitcasaError implements Parcelable

   The BitcasaError class provides custom errors.

Fields
------
CREATOR
^^^^^^^

.. java:field:: public static final Parcelable.Creator<BitcasaError> CREATOR
   :outertype: BitcasaError

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

BitcasaError
^^^^^^^^^^^^

.. java:constructor:: public BitcasaError(Parcel in)
   :outertype: BitcasaError

   Initializes the BitcasaError instance.

   :param in: The error parcel object.

Methods
-------
describeContents
^^^^^^^^^^^^^^^^

.. java:method:: @Override public int describeContents()
   :outertype: BitcasaError

   Describe the kinds of special objects contained in this Parcelable's marshalled representation

   :return: a bitmask indicating the set of special object types marshalled by the Parcelable

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

.. java:method:: public void setMessage(String message)
   :outertype: BitcasaError

   Sets the error message.

   :param message: The error message.

writeToParcel
^^^^^^^^^^^^^

.. java:method:: @Override public void writeToParcel(Parcel out, int flags)
   :outertype: BitcasaError

   Flatten this object in to a Parcel.

   :param out: The Parcel in which the object should be written.
   :param flags: Additional flags about how the object should be written. May be 0 or PARCELABLE_WRITE_RETURN_VALUE

