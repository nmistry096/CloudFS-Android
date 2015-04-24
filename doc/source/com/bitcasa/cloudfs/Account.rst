.. java:import:: com.bitcasa.cloudfs.api RESTAdapter

.. java:import:: com.bitcasa.cloudfs.model UserProfile

Account
=======

.. java:package:: com.bitcasa.cloudfs
   :noindex:

.. java:type:: public class Account

   The Account class provides accessibility to CloudFS Account.

Constructors
------------
Account
^^^^^^^

.. java:constructor:: public Account(RESTAdapter restAdapter, UserProfile profile)
   :outertype: Account

   Initializes an instance of the Account.

   :param restAdapter: The REST Adapter instance.
   :param profile: The user profile.

Methods
-------
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

toString
^^^^^^^^

.. java:method:: @Override public String toString()
   :outertype: Account

   Creates a string containing a concise, human-readable description of Account object.

   :return: The printable representation of Account object.

