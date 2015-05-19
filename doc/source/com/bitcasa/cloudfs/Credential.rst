.. java:import:: android.os Parcel

.. java:import:: android.os Parcelable

Credential
==========

.. java:package:: com.bitcasa.cloudfs
   :noindex:

.. java:type:: public class Credential implements Parcelable, Cloneable

   The Credential class provides accessibility to CloudFS Credential.

Fields
------
CREATOR
^^^^^^^

.. java:field:: public static final Parcelable.Creator<Credential> CREATOR
   :outertype: Credential

Constructors
------------
Credential
^^^^^^^^^^

.. java:constructor:: public Credential(String endpoint)
   :outertype: Credential

   Initializes an instance of the Credential.

   :param endpoint: The CloudFS API endpoint.

Credential
^^^^^^^^^^

.. java:constructor:: public Credential(Parcel in)
   :outertype: Credential

   Initializes the credential instance using a Parcel.

   :param in: The parcel object.

Methods
-------
clone
^^^^^

.. java:method:: public Credential clone()
   :outertype: Credential

   Returns a clone of the Credential.

   :return: A clone of Credential.

describeContents
^^^^^^^^^^^^^^^^

.. java:method:: @Override public int describeContents()
   :outertype: Credential

   Describe the kinds of special objects contained in this Parcelable's marshalled representation

   :return: a bitmask indicating the set of special object types marshalled by the Parcelable

getAccessToken
^^^^^^^^^^^^^^

.. java:method:: public String getAccessToken()
   :outertype: Credential

   Gets the CloudFS API access token.

   :return: The CloudFS API access token.

getEndPoint
^^^^^^^^^^^

.. java:method:: public String getEndPoint()
   :outertype: Credential

   Gets the CloudFS API endpoint.

   :return: The CloudFS API endpoint.

getTokenType
^^^^^^^^^^^^

.. java:method:: public String getTokenType()
   :outertype: Credential

   Gets the CloudFS API token type.

   :return: The CloudFS API token type.

setAccessToken
^^^^^^^^^^^^^^

.. java:method:: public void setAccessToken(String accessToken)
   :outertype: Credential

   Sets the CloudFS API access token.

   :param accessToken: The CloudFS API access token.

setEndPoint
^^^^^^^^^^^

.. java:method:: public void setEndPoint(String endpoint)
   :outertype: Credential

   Sets the CloudFS API endpoint.

   :param endpoint: The CloudFS API token type.

setTokenType
^^^^^^^^^^^^

.. java:method:: public void setTokenType(String tokenType)
   :outertype: Credential

   Sets the CloudFS API token type.

   :param tokenType: The CloudFS API token type.

writeToParcel
^^^^^^^^^^^^^

.. java:method:: @Override public void writeToParcel(Parcel out, int flags)
   :outertype: Credential

   Flatten this object in to a Parcel.

   :param out: The Parcel in which the object should be written.
   :param flags: Additional flags about how the object should be written. May be 0 or PARCELABLE_WRITE_RETURN_VALUE

