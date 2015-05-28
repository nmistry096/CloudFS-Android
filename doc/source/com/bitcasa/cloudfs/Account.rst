.. java:import:: android.os Parcel

.. java:import:: android.os Parcelable

.. java:import:: com.bitcasa.cloudfs.api RESTAdapter

.. java:import:: com.bitcasa.cloudfs.model Plan

.. java:import:: com.bitcasa.cloudfs.model UserProfile

Account
=======

.. java:package:: com.bitcasa.cloudfs
   :noindex:

.. java:type:: public class Account implements Parcelable

   The Account class provides accessibility to CloudFS Account.

Fields
------
CREATOR
^^^^^^^

.. java:field:: public static final Parcelable.Creator<Account> CREATOR
   :outertype: Account

Constructors
------------
Account
^^^^^^^

.. java:constructor:: public Account(RESTAdapter restAdapter, UserProfile profile, Plan plan)
   :outertype: Account

   Initializes an instance of the Account.

   :param restAdapter: The REST Adapter instance.
   :param profile: The user profile.

Account
^^^^^^^

.. java:constructor:: public Account(Parcel in)
   :outertype: Account

   Initializes the Account instance using a Parcel.

   :param in: The parcel object.

Methods
-------
describeContents
^^^^^^^^^^^^^^^^

.. java:method:: @Override public int describeContents()
   :outertype: Account

   Describe the kinds of special objects contained in this Parcelable's marshalled representation

   :return: a bitmask indicating the set of special object types marshalled by the Parcelable

getAccountLocale
^^^^^^^^^^^^^^^^

.. java:method:: public String getAccountLocale()
   :outertype: Account

   Gets the account locale value.

   :return: The account locale value.

getId
^^^^^

.. java:method:: public String getId()
   :outertype: Account

   Gets the user id.

   :return: The user id.

getOverStorageLimit
^^^^^^^^^^^^^^^^^^^

.. java:method:: public boolean getOverStorageLimit()
   :outertype: Account

   Gets a value indicating whether the storage limit is exceeded.

   :return: True if the limit is exceeded, otherwise false.

getPlan
^^^^^^^

.. java:method:: public Plan getPlan()
   :outertype: Account

   Gets the account plan.

   :return: The account plan.

getPlanDisplayName
^^^^^^^^^^^^^^^^^^

.. java:method:: public String getPlanDisplayName()
   :outertype: Account

   Gets the account plan display name.

   :return: The account plan display name.

getPlanId
^^^^^^^^^

.. java:method:: public String getPlanId()
   :outertype: Account

   Gets the account plan id.

   :return: The account plan id

getSessionLocale
^^^^^^^^^^^^^^^^

.. java:method:: public String getSessionLocale()
   :outertype: Account

   Gets the account session locale.

   :return: The account session locale.

getStateDisplayName
^^^^^^^^^^^^^^^^^^^

.. java:method:: public String getStateDisplayName()
   :outertype: Account

   Gets the account state display name.

   :return: The account state display name.

getStateId
^^^^^^^^^^

.. java:method:: public String getStateId()
   :outertype: Account

   Gets the account state id.

   :return: The account state id.

getStorageLimit
^^^^^^^^^^^^^^^

.. java:method:: public long getStorageLimit()
   :outertype: Account

   Gets the account's storage limit.

   :return: The account's storage limit.

getStorageUsage
^^^^^^^^^^^^^^^

.. java:method:: public long getStorageUsage()
   :outertype: Account

   Gets the account's storage usage.

   :return: The storage used by the account.

setPlan
^^^^^^^

.. java:method:: public void setPlan(Plan plan)
   :outertype: Account

   Sets the account plan.

   :param plan: The account plan to be set.

setStorageLimit
^^^^^^^^^^^^^^^

.. java:method:: public void setStorageLimit(long storageLimit)
   :outertype: Account

   Sets the account's storage limit.

   :param storageLimit: The storage limit to be set.

setStorageUsage
^^^^^^^^^^^^^^^

.. java:method:: public void setStorageUsage(long storageUsage)
   :outertype: Account

   Sets the account's storage usage.

   :param storageUsage:

toString
^^^^^^^^

.. java:method:: @Override public String toString()
   :outertype: Account

   Creates a string containing a concise, human-readable description of Account object.

   :return: The printable representation of Account object.

writeToParcel
^^^^^^^^^^^^^

.. java:method:: @Override public void writeToParcel(Parcel out, int flags)
   :outertype: Account

   Flatten this object in to a Parcel.

   :param out: The Parcel in which the object should be written.
   :param flags: Additional flags about how the object should be written. May be 0 or PARCELABLE_WRITE_RETURN_VALUE

