.. java:import:: com.bitcasa.cloudfs.api RESTAdapter

.. java:import:: com.bitcasa.cloudfs.exception BitcasaException

.. java:import:: com.bitcasa.cloudfs.model ActionFactory

.. java:import:: com.bitcasa.cloudfs.model BaseAction

.. java:import:: java.io IOException

.. java:import:: java.util ArrayList

.. java:import:: java.util List

Session
=======

.. java:package:: com.bitcasa.cloudfs
   :noindex:

.. java:type:: public class Session

   The Session class provides accessibility to CloudFS.

Constructors
------------
Session
^^^^^^^

.. java:constructor:: public Session(String endPoint, String clientId, String clientSecret)
   :outertype: Session

   Initializes an instance of CloudFS Session.

   :param endPoint: The REST Adapter instance.
   :param clientId: The file meta data returned from REST Adapter.
   :param clientSecret: The absolute parent path of this file.

Methods
-------
account
^^^^^^^

.. java:method:: public Account account() throws BitcasaException
   :outertype: Session

   Gets the current account information.

   :throws BitcasaException: If a CloudFS API error occurs.
   :return: The account information.

actionHistory
^^^^^^^^^^^^^

.. java:method:: public ActionHistory actionHistory(int startVersion, int stopVersion) throws IOException, BitcasaException
   :outertype: Session

   Gets the action history.

   :param startVersion: Integer representing which version number to start listing historical actions from.
   :param stopVersion: Integer representing which version number from which to stop listing historical actions.
   :throws BitcasaException: If a CloudFS API error occurs.
   :throws IOException: If a network error occurs
   :return: The action history.

authenticate
^^^^^^^^^^^^

.. java:method:: public void authenticate(String username, String password) throws IOException, BitcasaException
   :outertype: Session

   Links a user to the session by authenticating using a username and password.

   :param username: The specified username.
   :param password: The specified password.
   :throws BitcasaException: If a CloudFS API error occurs.
   :throws IOException: If a network error occurs.

createAccount
^^^^^^^^^^^^^

.. java:method:: public User createAccount(String username, String password, String email, String firstName, String lastName, Boolean logInToCreatedUser) throws IOException, BitcasaException
   :outertype: Session

   Create a new user account and logs in to the account created, if the logInToCreatedUser flag is set.

   :param username: The username for the new user account.
   :param password: The password for the new user account.
   :param email: The email for the new user account.
   :param firstName: The first name for the new user.
   :param lastName: The last name for the new user.
   :param logInToCreatedUser: The login to created user flag which sets the method to authenticate the user and logs the user created.
   :return: The newly created user instance.

createPlan
^^^^^^^^^^

.. java:method:: public Plan createPlan(String name, String limit) throws BitcasaException, IOException
   :outertype: Session

   Creates a new account plan with the supplied data.

   :param name: The name of the account plan.
   :param limit: The limit for the account plan.
   :throws BitcasaException: If a CloudFS API error occurs.
   :throws IllegalArgumentException: If the parameters are invalid or misused.
   :throws IOException: If a network error occurs.
   :return: The newly created account plan instance.

filesystem
^^^^^^^^^^

.. java:method:: public FileSystem filesystem()
   :outertype: Session

   Gets an instance of filesystem.

   :return: The filesystem instance.

getAdminClientId
^^^^^^^^^^^^^^^^

.. java:method:: public String getAdminClientId()
   :outertype: Session

   Gets the admin client id.

   :return: The admin client id.

getAdminClientSecret
^^^^^^^^^^^^^^^^^^^^

.. java:method:: public String getAdminClientSecret()
   :outertype: Session

   Gets the admin client secret.

   :return: The admin client secret.

getClientId
^^^^^^^^^^^

.. java:method:: public String getClientId()
   :outertype: Session

   Gets the session client id.

   :return: The client id.

getClientSecret
^^^^^^^^^^^^^^^

.. java:method:: public String getClientSecret()
   :outertype: Session

   Gets the sessions client secret.

   :return: The client secret.

getRestAdapter
^^^^^^^^^^^^^^

.. java:method:: public RESTAdapter getRestAdapter()
   :outertype: Session

   Gets an instance of the RESTAdapter.

   :return: An instance of the RESTAdapter.

isLinked
^^^^^^^^

.. java:method:: public boolean isLinked()
   :outertype: Session

   Checks whether a specific user is linked to the session or not.

   :return: The value indicating whether the operation was successful or not.

listPlans
^^^^^^^^^

.. java:method:: public Plan[] listPlans() throws BitcasaException
   :outertype: Session

   Lists the custom end user account plans.

   :throws BitcasaException: If a CloudFS API error occurs.
   :return: List of custom end user plans.

setAccessToken
^^^^^^^^^^^^^^

.. java:method:: public void setAccessToken(String accessToken)
   :outertype: Session

   Set the access token of this Session credentials instance.

   :param accessToken: The access token to be set.

setAdminCredentials
^^^^^^^^^^^^^^^^^^^

.. java:method:: public void setAdminCredentials(String adminClientId, String adminClientSecret)
   :outertype: Session

   Set the sessions admin credentials.

   :param adminClientId: The admin client id.
   :param adminClientSecret: The admin client secret.

unlink
^^^^^^

.. java:method:: public void unlink()
   :outertype: Session

   Unlinks a specific user from the session.

updateUser
^^^^^^^^^^

.. java:method:: public User updateUser(String id, String username, String firstName, String lastName, String planCode) throws BitcasaException, IOException
   :outertype: Session

   Update the user details and account plan for the given the user account code.

   :param id: The account id of the user account.
   :param username: The username of the account to be updated.
   :param firstName: The first name of the account to be updated.
   :param lastName: The last name of the account to be updated.
   :param planCode: The plan code of the account to be updated.
   :throws IOException: If response data can not be read due to network errors.
   :throws BitcasaException: If a CloudFS API error occurs.
   :return: The updated user.

user
^^^^

.. java:method:: public User user() throws BitcasaException
   :outertype: Session

   Gets the current user information.

   :throws BitcasaException: If a CloudFS API error occurs.
   :return: The user information.

