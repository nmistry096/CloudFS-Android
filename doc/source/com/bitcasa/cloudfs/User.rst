.. java:import:: android.os Parcel

.. java:import:: android.os Parcelable

.. java:import:: com.bitcasa.cloudfs.api RESTAdapter

.. java:import:: com.bitcasa.cloudfs.model UserProfile

.. java:import:: java.util Date

User
====

.. java:package:: com.bitcasa.cloudfs
   :noindex:

.. java:type:: public class User implements Parcelable

   The User class provides accessibility to CloudFS User.

Fields
------
CREATOR
^^^^^^^

.. java:field:: public static final Parcelable.Creator<User> CREATOR
   :outertype: User

   {@inheritDoc}

Constructors
------------
User
^^^^

.. java:constructor:: public User(RESTAdapter restAdapter, UserProfile profile)
   :outertype: User

   Initializes an instance of CloudFS User.

   :param restAdapter: The REST Adapter instance.
   :param profile: The user profile.

User
^^^^

.. java:constructor:: public User(Parcel in)
   :outertype: User

   Initializes the User instance.

   :param in: The parcel object containing the user details.

Methods
-------
describeContents
^^^^^^^^^^^^^^^^

.. java:method:: @Override public int describeContents()
   :outertype: User

   :return: @inheritDoc

getCreatedAt
^^^^^^^^^^^^

.. java:method:: public Date getCreatedAt()
   :outertype: User

   Gets the user created date and time.

   :return: The user created date and time.

getEmail
^^^^^^^^

.. java:method:: public String getEmail()
   :outertype: User

   Gets the user's email address.

   :return: The user's email address.

getFirstName
^^^^^^^^^^^^

.. java:method:: public String getFirstName()
   :outertype: User

   Gets the user's first name.

   :return: The user's first name.

getId
^^^^^

.. java:method:: public String getId()
   :outertype: User

   Gets the user id.

   :return: The user id.

getLastLogin
^^^^^^^^^^^^

.. java:method:: public Date getLastLogin()
   :outertype: User

   Gets the user's last login date and time.

   :return: The user's last login date and time.

getLastName
^^^^^^^^^^^

.. java:method:: public String getLastName()
   :outertype: User

   Gets the user's last name.

   :return: The user's last name.

getUsername
^^^^^^^^^^^

.. java:method:: public String getUsername()
   :outertype: User

   Gets the username.

   :return: The username.

toString
^^^^^^^^

.. java:method:: @Override public String toString()
   :outertype: User

   Creates a string containing a concise, human-readable description of User object.

   :return: The printable representation of User object.

writeToParcel
^^^^^^^^^^^^^

.. java:method:: @Override public void writeToParcel(Parcel out, int flags)
   :outertype: User

   :param out: @inheritDoc
   :param flags: @inheritDoc

