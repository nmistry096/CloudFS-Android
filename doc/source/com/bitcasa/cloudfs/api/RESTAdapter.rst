.. java:import:: android.net Uri

.. java:import:: android.os Parcel

.. java:import:: android.os Parcelable

.. java:import:: android.util Log

.. java:import:: com.bitcasa.cloudfs Account

.. java:import:: com.bitcasa.cloudfs BitcasaError

.. java:import:: com.bitcasa.cloudfs Credential

.. java:import:: com.bitcasa.cloudfs Folder

.. java:import:: com.bitcasa.cloudfs Item

.. java:import:: com.bitcasa.cloudfs Plan

.. java:import:: com.bitcasa.cloudfs Session

.. java:import:: com.bitcasa.cloudfs Share

.. java:import:: com.bitcasa.cloudfs User

.. java:import:: com.bitcasa.cloudfs Utils.BitcasaProgressListener

.. java:import:: com.bitcasa.cloudfs Utils.BitcasaRESTConstants

.. java:import:: com.bitcasa.cloudfs.exception BitcasaAuthenticationException

.. java:import:: com.bitcasa.cloudfs.exception BitcasaClientException

.. java:import:: com.bitcasa.cloudfs.exception BitcasaException

.. java:import:: com.bitcasa.cloudfs.model AccessToken

.. java:import:: com.bitcasa.cloudfs.model ActionData

.. java:import:: com.bitcasa.cloudfs.model ActionDataAlter

.. java:import:: com.bitcasa.cloudfs.model ActionDataDefault

.. java:import:: com.bitcasa.cloudfs.model BaseAction

.. java:import:: com.bitcasa.cloudfs.model BitcasaResponse

.. java:import:: com.bitcasa.cloudfs.model ItemList

.. java:import:: com.bitcasa.cloudfs.model ItemMeta

.. java:import:: com.bitcasa.cloudfs.model PlanMeta

.. java:import:: com.bitcasa.cloudfs.model ShareItem

.. java:import:: com.bitcasa.cloudfs.model SharedFolder

.. java:import:: com.bitcasa.cloudfs.model Storage

.. java:import:: com.bitcasa.cloudfs.model UserProfile

.. java:import:: com.google.gson Gson

.. java:import:: com.google.gson GsonBuilder

.. java:import:: com.google.gson JsonObject

.. java:import:: com.google.gson JsonSyntaxException

.. java:import:: java.io BufferedInputStream

.. java:import:: java.io BufferedOutputStream

.. java:import:: java.io File

.. java:import:: java.io FileNotFoundException

.. java:import:: java.io FileOutputStream

.. java:import:: java.io IOException

.. java:import:: java.io InputStream

.. java:import:: java.io OutputStream

.. java:import:: java.io UnsupportedEncodingException

.. java:import:: java.math BigInteger

.. java:import:: java.net HttpURLConnection

.. java:import:: java.net MalformedURLException

.. java:import:: java.net ProtocolException

.. java:import:: java.net URL

.. java:import:: java.net URLEncoder

.. java:import:: java.security InvalidKeyException

.. java:import:: java.security NoSuchAlgorithmException

.. java:import:: java.text SimpleDateFormat

.. java:import:: java.util ArrayList

.. java:import:: java.util Arrays

.. java:import:: java.util Calendar

.. java:import:: java.util HashMap

.. java:import:: java.util List

.. java:import:: java.util Locale

.. java:import:: java.util Map

.. java:import:: java.util TimeZone

.. java:import:: java.util TreeMap

.. java:import:: javax.net.ssl HttpsURLConnection

RESTAdapter
===========

.. java:package:: com.bitcasa.cloudfs.api
   :noindex:

.. java:type:: public class RESTAdapter implements Parcelable, Cloneable

   Entry point to all CloudFS API requests.

Fields
------
CREATOR
^^^^^^^

.. java:field:: public static final Parcelable.Creator<RESTAdapter> CREATOR
   :outertype: RESTAdapter

Constructors
------------
RESTAdapter
^^^^^^^^^^^

.. java:constructor:: public RESTAdapter(Credential credential)
   :outertype: RESTAdapter

   Constructor, takes in a credential instance and initialises the RESTAdapter instance.

   :param credential: Application Credentials.

RESTAdapter
^^^^^^^^^^^

.. java:constructor:: public RESTAdapter(Parcel in)
   :outertype: RESTAdapter

   Initializes the credential instance using a Parcel.

   :param in: The parcel object.

Methods
-------
alterFileMeta
^^^^^^^^^^^^^

.. java:method:: public com.bitcasa.cloudfs.File alterFileMeta(Item meta, Map<String, String> changes, int version, BitcasaRESTConstants.VersionExists versionExists) throws BitcasaException
   :outertype: RESTAdapter

   Changes the specified file's meta data.

   :param meta: Item object to be changed.
   :param changes: Meta data to be changed.
   :param version: Version of the item to be changed.
   :param versionExists: The action to perform if the version exists at the destination.
   :throws BitcasaException: If a CloudFS API error occurs.
   :throws BitcasaAuthenticationException: If user not authenticated.
   :return: File object with altered meta data.

alterFolderMeta
^^^^^^^^^^^^^^^

.. java:method:: public Folder alterFolderMeta(Item meta, Map<String, String> changes, int version, BitcasaRESTConstants.VersionExists versionExists) throws BitcasaException
   :outertype: RESTAdapter

   Changes the specified folder's meta data.

   :param meta: Item object to be changed.
   :param changes: Meta data to be changed.
   :param version: Version of the item to be changed.
   :param versionExists: The action to perform if the version exists at the destination.
   :throws BitcasaException: If a CloudFS API error occurs.
   :throws BitcasaAuthenticationException: If user not authenticated.
   :return: Folder object with altered meta

alterMeta
^^^^^^^^^

.. java:method:: public Item alterMeta(Item meta, Map<String, String> changes, int version, BitcasaRESTConstants.VersionExists versionExists) throws BitcasaException
   :outertype: RESTAdapter

   Changes the specified item's meta data.

   :param meta: Item object to be changed.
   :param changes: Meta data to be changed.
   :param version: Version of the item to be changed.
   :param versionExists: The action to perform if the version exists at the destination.
   :throws BitcasaException: If a CloudFS API error occurs.
   :throws BitcasaAuthenticationException: If user not authenticated.
   :return: Item object with altered meta data.

alterShare
^^^^^^^^^^

.. java:method:: public Share alterShare(String shareKey, Map<String, String> changes, String currentPassword) throws IOException, BitcasaException
   :outertype: RESTAdapter

   Alter the share attributes associated with the given shareKey.

   :param shareKey: The shareKey of the share to be altered.
   :param changes: The changes to be updated to the share associated with given shareKey.
   :param currentPassword: Password associated with the share which needs to be altered.
   :throws BitcasaException: If a CloudFS API error occurs.
   :throws IOException: If a network error occurs.
   :return: Share object with altered share attributes.

alterShareInfo
^^^^^^^^^^^^^^

.. java:method:: public Share alterShareInfo(String shareKey, String currentPassword, String newPassword) throws IOException, BitcasaException
   :outertype: RESTAdapter

   Alter the share information associated with the given shareKey.

   :param shareKey: The shareKey of the share which needs to be altered.
   :param currentPassword: Password associated with the share which needs to be altered.
   :param newPassword: New password to be set to the current share.
   :throws BitcasaException: If a CloudFS API error occurs.
   :throws IOException: If a network error occurs.
   :return: Share object with altered share info.

authenticate
^^^^^^^^^^^^

.. java:method:: public void authenticate(Session session, String username, String password) throws IOException, BitcasaException, IllegalArgumentException
   :outertype: RESTAdapter

   Authenticates with CloudFS and retrieves the access token.

   :param session: Session object.
   :param username: String username.
   :param password: String password.
   :throws BitcasaException: If a CloudFS API error occurs.
   :throws IllegalArgumentException: If the parameters are invalid or misused.
   :throws IOException: If a network error occurs.

browseShare
^^^^^^^^^^^

.. java:method:: public Item[] browseShare(String shareKey, String path) throws IOException, BitcasaException
   :outertype: RESTAdapter

   Given the shareKey and the path to any folder/file under share, browseShare method will return the item list for that share. Make sure unlockShare is called before browseShare

   :param shareKey: The shareKey of the share to be browsed.
   :param path: Path to be browsed.
   :throws BitcasaException: If a CloudFS API error occurs.
   :throws IOException: If a network error occurs
   :return: Items list found at given share path.

browseTrash
^^^^^^^^^^^

.. java:method:: public Item[] browseTrash(String path) throws BitcasaException
   :outertype: RESTAdapter

   Browse the trash associated with current account.

   :throws BitcasaException: If a CloudFS API error occurs.
   :return: An array of trash item objects found.

clone
^^^^^

.. java:method:: public RESTAdapter clone()
   :outertype: RESTAdapter

   Returns a clone of the RESTAdapter.

   :return: A clone of RESTAdapter.

copy
^^^^

.. java:method:: public Item copy(Item item, String destinationPath, String newName, BitcasaRESTConstants.Exists exists) throws IOException, BitcasaException
   :outertype: RESTAdapter

   Copies an item to given destination path.

   :param item: The item object to be copied.
   :param destinationPath: The destination path which the item should be copied.
   :param newName: The new name of the item.
   :param exists: The action to perform if the item already exists at the destination.
   :throws BitcasaException: If the server can not copy the item due to an error.
   :throws IOException: If response data can not be read due to network errors.
   :return: An item which refers to the item at the destination path.

createAccount
^^^^^^^^^^^^^

.. java:method:: public User createAccount(Session session, String username, String password, String email, String firstName, String lastName) throws IOException, IllegalArgumentException, BitcasaException
   :outertype: RESTAdapter

   Creates a new CloudFS user with the supplied data.

   :param session: Session object.
   :param username: The username for the new user.
   :param password: The password for the new user.
   :param email: The email for the new user.
   :param firstName: The first name of the new user.
   :param lastName: The last name of the new user.
   :throws BitcasaException: If a CloudFS API error occurs.
   :throws IllegalArgumentException: If the parameters are invalid or misused.
   :throws IOException: If a network error occurs.
   :return: The newly created user.

createFolder
^^^^^^^^^^^^

.. java:method:: public Folder createFolder(String folderName, Folder parentFolder, BitcasaRESTConstants.Exists exists) throws IOException, BitcasaException
   :outertype: RESTAdapter

   Creates a folder in the CloudFS file system.

   :param folderName: The name of the folder to be created.
   :param parentFolder: The parent folder under which the new folder is to be created.
   :param exists: Action to take if the folder to be created already exists.
   :throws BitcasaException: If a CloudFS API error occurs.
   :throws IOException: If a network error occurs.
   :return: An instance of the newly created folder.

createPlan
^^^^^^^^^^

.. java:method:: public Plan createPlan(Session session, String name, String limit) throws BitcasaException, IOException, IllegalArgumentException
   :outertype: RESTAdapter

   Creates a new account plan with the supplied data.

   :param session: The session object.
   :param name: The name of the account plan.
   :param limit: The limit for the account plan.
   :throws BitcasaException: If a CloudFS API error occurs.
   :throws IllegalArgumentException: If the parameters are invalid or misused.
   :throws IOException: If a network error occurs.
   :return: The newly created account plan instance.

createShare
^^^^^^^^^^^

.. java:method:: public Share createShare(String path, String password) throws IOException, BitcasaException
   :outertype: RESTAdapter

   Creates a share including the item in the path specified.

   :param path: Path to the item to be shared.
   :param password: Password to access the share to be created.
   :throws BitcasaException: If a CloudFS API error occurs.
   :throws IOException: If a network error occurs
   :return: The created share object.

createShare
^^^^^^^^^^^

.. java:method:: public Share createShare(String[] paths, String password) throws IOException, BitcasaException
   :outertype: RESTAdapter

   Creates a share including the item in the path specified.

   :param paths: Paths to the items to be shared.
   :param password: Password to access the share to be created.
   :throws BitcasaException: If a CloudFS API error occurs.
   :throws IOException: If a network error occurs
   :return: The created share object.

deleteFile
^^^^^^^^^^

.. java:method:: public boolean deleteFile(String path, boolean commit) throws IOException, BitcasaException
   :outertype: RESTAdapter

   Deletes an existing file from the CloudFS file system.

   :param path: Path to the file to be deleted.
   :param commit: If true, folder is deleted immediately. Otherwise, it is moved to the Trash. The default is false.
   :throws BitcasaException: If a CloudFS API error occurs.
   :throws IOException: If response data can not be read due to network errors.
   :return: Returns true if the file is deleted successfully, otherwise false.

deleteFolder
^^^^^^^^^^^^

.. java:method:: public boolean deleteFolder(String path, boolean commit, boolean force) throws IOException, BitcasaException
   :outertype: RESTAdapter

   Deletes the folder from the CloudFS file system.

   :param path: Path to the folder to be deleted.
   :param commit: If true, folder is deleted immediately. Otherwise, it is moved to the Trash. The default is false.
   :param force: If true, folder is deleted even if it contains sub-items. The default is false.
   :throws BitcasaException: If a CloudFS API error occurs.
   :throws IOException: If response data can not be read due to network errors.
   :return: Returns true if the folder is deleted successfully, otherwise false.

deletePlan
^^^^^^^^^^

.. java:method:: public boolean deletePlan(Session session, String planId) throws IOException, BitcasaException
   :outertype: RESTAdapter

   Deletes the account plan from CloudFS for the given plan id.

   :param session: The session object.
   :param planId: The path of the item which needs to be deleted.
   :throws BitcasaException: If a CloudFS API error occurs.
   :throws IOException: If response data can not be read due to network errors.
   :return: Returns true if the account plan is deleted successfully, otherwise false.

deleteShare
^^^^^^^^^^^

.. java:method:: public boolean deleteShare(String shareKey) throws BitcasaException
   :outertype: RESTAdapter

   Delete the share associated with given shareKey.

   :param shareKey: The shareKey of the share which needs to be deleted.
   :throws BitcasaException: If a CloudFS API error occurs.
   :return: Returns true if the share is deleted successfully, otherwise false.

deleteTrashItem
^^^^^^^^^^^^^^^

.. java:method:: public boolean deleteTrashItem(String trashItemId) throws BitcasaException
   :outertype: RESTAdapter

   Delete the given item from trash.

   :param trashItemId: Item id to be deleted from trash.
   :throws BitcasaException: If a CloudFS API error occurs.
   :return: Returns true if the item is deleted successfully, otherwise false.

describeContents
^^^^^^^^^^^^^^^^

.. java:method:: @Override public int describeContents()
   :outertype: RESTAdapter

   Describe the kinds of special objects contained in this Parcelable's marshalled representation

   :return: a bitmask indicating the set of special object types marshalled by the Parcelable

download
^^^^^^^^

.. java:method:: public InputStream download(Item file, long range) throws BitcasaException
   :outertype: RESTAdapter

   Download a file from CloudFS file system.

   :param file: Item object of a file.
   :param range: long range of the file to be downloaded.
   :throws BitcasaException: If a CloudFS API error occurs.
   :return: InputStream of the downloaded file.

downloadFile
^^^^^^^^^^^^

.. java:method:: public void downloadFile(com.bitcasa.cloudfs.File file, long range, String localDestination, BitcasaProgressListener listener) throws BitcasaException, IOException
   :outertype: RESTAdapter

   Download a file from CloudFS file system.

   :param file: Bitcasa Item with valid bitcasa file path and file name.
   :param range: Any valid content range. No less than 0, no greater than the file size.
   :param localDestination: Device file location with file path and name.
   :param listener: The progress listener to listen to the file download progress.
   :throws IOException: If a network error occurs.
   :throws BitcasaException: If a CloudFS API error occurs.

downloadUrl
^^^^^^^^^^^

.. java:method:: public String downloadUrl(String path, long size) throws IOException
   :outertype: RESTAdapter

   Creates a redirect url from a given path.

   :param path: The actual file path.
   :param size: The actual size of the file
   :throws IOException: If a network error occurs.
   :return: The redirect url.

getFolderMeta
^^^^^^^^^^^^^

.. java:method:: public Folder getFolderMeta(String absolutePath) throws BitcasaException
   :outertype: RESTAdapter

   Gets the Meta data of the folder.

   :param absolutePath: Location of the folder whose meta data is to be obtained.
   :throws BitcasaException: If a CloudFS API error occurs.
   :throws BitcasaAuthenticationException: If user not authenticated
   :return: Folder object at the given path.

getItemMeta
^^^^^^^^^^^

.. java:method:: public Item getItemMeta(String absolutePath) throws BitcasaException
   :outertype: RESTAdapter

   Gets the meta data of an item.

   :param absolutePath: Location of the item whose meta data is to be obtained.
   :throws BitcasaException: If a CloudFS API error occurs.
   :throws BitcasaAuthenticationException: If user not authenticated.
   :return: Item object at the given path.

getList
^^^^^^^

.. java:method:: public Item[] getList(String folderPath, int version, int depth, String filter) throws IOException, BitcasaException
   :outertype: RESTAdapter

   Lists all the files and folders under the given folder path.

   :param folderPath: String folder path to get the list.
   :param version: String version of the folder.
   :param depth: Integer folder depth to read.
   :param filter: String filter to be applied when reading the list.
   :throws BitcasaException: If a CloudFS API error occurs.
   :throws IOException: If a network error occurs.
   :return: Item array of files and folders.

listFileVersions
^^^^^^^^^^^^^^^^

.. java:method:: public com.bitcasa.cloudfs.File[] listFileVersions(String path, int startVersion, int stopVersion, int limit) throws BitcasaException, IOException
   :outertype: RESTAdapter

   List the versions of specified file.

   :param path: File path of the file whose versions are to be listed.
   :param startVersion: Start version of the version list.
   :param stopVersion: End version of the version list.
   :param limit: Limits the number of versions to be listed down in results.
   :throws BitcasaException: If a CloudFS API error occurs.
   :throws IOException: If a network error occurs.
   :return: A list of file meta data results, as they have been recorded in the file version history after successful meta data changes.

listHistory
^^^^^^^^^^^

.. java:method:: public List<BaseAction> listHistory(int startVersion, int stopVersion) throws IOException, BitcasaException
   :outertype: RESTAdapter

   List the action history associated with current account.

   :param startVersion: Version to start the list from.
   :param stopVersion: Version to end the list.
   :throws BitcasaException: If a CloudFS API error occurs.
   :throws IOException: If a network error occurs.
   :return: ActionHistory object containing actions associated with current account.

listPlans
^^^^^^^^^

.. java:method:: public Plan[] listPlans(Session session) throws BitcasaException
   :outertype: RESTAdapter

   Lists the custom end user account plans.

   :param session: The session object.
   :throws BitcasaException: If a CloudFS API error occurs.
   :return: List of custom end user plans.

listShare
^^^^^^^^^

.. java:method:: public Share[] listShare() throws BitcasaException
   :outertype: RESTAdapter

   Lists the shares the user has created.

   :throws BitcasaException: If a CloudFS API error occurs.
   :return: List of shares associated with current account.

move
^^^^

.. java:method:: public Item move(Item item, String destinationPath, String newName, BitcasaRESTConstants.Exists exists) throws IOException, BitcasaException
   :outertype: RESTAdapter

   Moves an item to given destination path.

   :param item: The item object to be moved.
   :param destinationPath: The destination path which the item should be moved.
   :param newName: The new name of the item.
   :param exists: The action to perform if the item already exists at the destination.
   :throws BitcasaException: If the server can not move the item due to an error.
   :throws IOException: If response data can not be read.
   :return: An item which refers to the item at the destination path.

receiveShare
^^^^^^^^^^^^

.. java:method:: public Item[] receiveShare(String shareKey, String pathToInsertShare, BitcasaRESTConstants.Exists exists) throws IOException, BitcasaException
   :outertype: RESTAdapter

   Given a valid location in a user's fileSystem, all items found in this share specified by the shareKey will be inserted into the given location. File collisions will be handled with the exist action specified.

   :param shareKey: The shareKey of the share to be received.
   :param pathToInsertShare: Path to save the received files and folders.
   :param exists: The action to perform if the version exists at the destination.
   :throws BitcasaException: If a CloudFS API error occurs.
   :throws IOException: If a network error occurs
   :return: An array of item objects received from share.

recoverTrashItem
^^^^^^^^^^^^^^^^

.. java:method:: public boolean recoverTrashItem(String trashItemId, BitcasaRESTConstants.RestoreMethod restoreMethod, String rescueOrRecreatePath) throws UnsupportedEncodingException, BitcasaException
   :outertype: RESTAdapter

   Recover Trash Item

   :param trashItemId: Item id to be recovered from trash.
   :param restoreMethod: RestoreMethod to be used on the recover process.
   :param rescueOrRecreatePath: Path to rescue or recreate the item to.
   :throws UnsupportedEncodingException: If encoding not supported.
   :throws BitcasaException: If a CloudFS API error occurs.
   :return: Returns true if the item is recovered successfully, otherwise false.

requestAccountInfo
^^^^^^^^^^^^^^^^^^

.. java:method:: public Account requestAccountInfo() throws BitcasaException
   :outertype: RESTAdapter

   Requests to retrieve account information from the CloudFS server.

   :throws BitcasaException: If a CloudFS API error occurs.
   :return: Bitcasa Account object.

requestUserInfo
^^^^^^^^^^^^^^^

.. java:method:: public User requestUserInfo() throws BitcasaException
   :outertype: RESTAdapter

   Requests to retrieve user information from the CloudFS server.

   :throws BitcasaException: If a CloudFS API error occurs.
   :return: Bitcasa User object.

unlockShare
^^^^^^^^^^^

.. java:method:: public boolean unlockShare(String shareKey, String password) throws IOException, BitcasaException
   :outertype: RESTAdapter

   Given a valid share and its password, this entry point will unlock the share for the login session.

   :param shareKey: The shareKey of the share need to be unlocked.
   :param password: Password associated with the share need to be unlocked.
   :throws BitcasaException: If a CloudFS API error occurs.
   :throws IOException: If a network error occurs.
   :return: Returns true if the share is unlocked successfully, otherwise false.

updateUser
^^^^^^^^^^

.. java:method:: public User updateUser(Session session, String id, String username, String firstName, String lastName, String planCode) throws BitcasaException, IOException
   :outertype: RESTAdapter

   Update the user details and account plan for the given the user account code.

   :param session: The session object.
   :param id: The account id of the user account.
   :param username: The username of the account to be updated.
   :param firstName: The firstname of the account to be updated.
   :param lastName: The lastname of the account to be updated.
   :param planCode: The plan code of the account to be updated.
   :throws IOException: If response data can not be read due to network errors.
   :throws BitcasaException: If a CloudFS API error occurs.
   :return: The updated user.

uploadFile
^^^^^^^^^^

.. java:method:: public com.bitcasa.cloudfs.File uploadFile(Item folder, String sourceFilePath, BitcasaRESTConstants.Exists exists, BitcasaProgressListener listener) throws IOException, BitcasaException
   :outertype: RESTAdapter

   Upload a local file to the CloudFS server.

   :param folder: Item object of the folder.
   :param sourceFilePath: String path of the source file.
   :param exists: Exists enum which specifies the action to take if the file already exists.
   :param listener: The progress listener to listen to the file upload progress.
   :throws BitcasaException: If a CloudFS API error occurs.
   :throws IOException: If a network error occurs.
   :return: CloudFS file meta data of this successful upload, null if upload failed.

writeToParcel
^^^^^^^^^^^^^

.. java:method:: @Override public void writeToParcel(Parcel out, int flags)
   :outertype: RESTAdapter

   Flatten this object in to a Parcel.

   :param out: The Parcel in which the object should be written.
   :param flags: Additional flags about how the object should be written. May be 0 or PARCELABLE_WRITE_RETURN_VALUE

