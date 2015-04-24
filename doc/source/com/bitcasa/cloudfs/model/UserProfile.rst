.. java:import:: com.google.gson.annotations SerializedName

UserProfile
===========

.. java:package:: com.bitcasa.cloudfs.model
   :noindex:

.. java:type:: public class UserProfile

   Model class for user profile.

Constructors
------------
UserProfile
^^^^^^^^^^^

.. java:constructor:: public UserProfile(String username, Long createdAt, String firstName, String lastName, String accountId, String locale, AccountState accountState, Storage storage, Holds holds, AccountPlan accountPlan, String email, Session session, Long lastLogin, String id)
   :outertype: UserProfile

   Initializes a new instance of an user profile.

   :param username: Username of the user.
   :param createdAt: Created date of the profile.
   :param firstName: First name of the user.
   :param lastName: Last name of the user.
   :param accountId: Account ID of the user.
   :param locale: Locale of the user.
   :param accountState: Account state of the user.
   :param storage: Storage details of the user.
   :param holds: Holds for the account.
   :param accountPlan: Account plan of the user.
   :param email: Email of the user.
   :param session: Current session details.
   :param lastLogin: Last login date of the user.
   :param id: ID of the user.

Methods
-------
getAccountId
^^^^^^^^^^^^

.. java:method:: public final String getAccountId()
   :outertype: UserProfile

   Gets the account ID of the user.

   :return: Account ID of the user.

getAccountPlan
^^^^^^^^^^^^^^

.. java:method:: public final AccountPlan getAccountPlan()
   :outertype: UserProfile

   Gets the account plan of the user.

   :return: Account plan of the user.

getAccountState
^^^^^^^^^^^^^^^

.. java:method:: public final AccountState getAccountState()
   :outertype: UserProfile

   Gets the account state of the user.

   :return: Account state of the user.

getCreatedAt
^^^^^^^^^^^^

.. java:method:: public final Long getCreatedAt()
   :outertype: UserProfile

   Gets the created date of the profile.

   :return: Created date of the profile.

getEmail
^^^^^^^^

.. java:method:: public final String getEmail()
   :outertype: UserProfile

   Gets the email of the user.

   :return: Email of the user.

getFirstName
^^^^^^^^^^^^

.. java:method:: public final String getFirstName()
   :outertype: UserProfile

   Gets the first name of the user.

   :return: First name of the user.

getHolds
^^^^^^^^

.. java:method:: public final Holds getHolds()
   :outertype: UserProfile

   Gets the holds of the account.

   :return: Holds of the account.

getId
^^^^^

.. java:method:: public final String getId()
   :outertype: UserProfile

   Gets the ID of the user.

   :return: ID of the user.

getLastLogin
^^^^^^^^^^^^

.. java:method:: public final Long getLastLogin()
   :outertype: UserProfile

   Gets the last login date of the user.

   :return: Last login date of the user.

getLastName
^^^^^^^^^^^

.. java:method:: public final String getLastName()
   :outertype: UserProfile

   Gets the last name of the user.

   :return: Last name of the user.

getLocale
^^^^^^^^^

.. java:method:: public final String getLocale()
   :outertype: UserProfile

   Gets the locale of the user.

   :return: Locale of the user.

getSession
^^^^^^^^^^

.. java:method:: public final Session getSession()
   :outertype: UserProfile

   Gets the session of the user.

   :return: Session of the user.

getStorage
^^^^^^^^^^

.. java:method:: public final Storage getStorage()
   :outertype: UserProfile

   Gets the storage details of the user.

   :return: Storage details of the user.

getUsername
^^^^^^^^^^^

.. java:method:: public final String getUsername()
   :outertype: UserProfile

   Gets the username of the user.

   :return: Username of the user.

setHolds
^^^^^^^^

.. java:method:: public final void setHolds(Holds holds)
   :outertype: UserProfile

   Sets the holds of the account.

   :param holds: Holds of the account.

setStorage
^^^^^^^^^^

.. java:method:: public final void setStorage(Storage storage)
   :outertype: UserProfile

   Sets the storage details of the user.

   :param storage: Storage details of the user.

