.. java:import:: android.net Uri

.. java:import:: android.util Log

.. java:import:: com.bitcasa.cloudfs Account

.. java:import:: com.bitcasa.cloudfs BitcasaError

.. java:import:: com.bitcasa.cloudfs Credential

.. java:import:: com.bitcasa.cloudfs Folder

.. java:import:: com.bitcasa.cloudfs Item

.. java:import:: com.bitcasa.cloudfs Session

.. java:import:: com.bitcasa.cloudfs Share

.. java:import:: com.bitcasa.cloudfs User

.. java:import:: com.bitcasa.cloudfs Utils.BitcasaProgressListener

.. java:import:: com.bitcasa.cloudfs Utils.BitcasaRESTConstants

.. java:import:: com.bitcasa.cloudfs Utils.BitcasaRESTConstants.Exists

.. java:import:: com.bitcasa.cloudfs Utils.BitcasaRESTConstants.VersionExists

.. java:import:: com.bitcasa.cloudfs.exception BitcasaAuthenticationException

.. java:import:: com.bitcasa.cloudfs.exception BitcasaClientException

.. java:import:: com.bitcasa.cloudfs.exception BitcasaException

.. java:import:: com.bitcasa.cloudfs.exception BitcasaFileException

.. java:import:: com.bitcasa.cloudfs.model AccessToken

.. java:import:: com.bitcasa.cloudfs.model ActionData

.. java:import:: com.bitcasa.cloudfs.model ActionDataAlter

.. java:import:: com.bitcasa.cloudfs.model ActionDataDefault

.. java:import:: com.bitcasa.cloudfs.model BaseAction

.. java:import:: com.bitcasa.cloudfs.model BitcasaResponse

.. java:import:: com.bitcasa.cloudfs.model ItemList

.. java:import:: com.bitcasa.cloudfs.model ItemMeta

.. java:import:: com.bitcasa.cloudfs.model ShareItem

.. java:import:: com.bitcasa.cloudfs.model SharedFolder

.. java:import:: com.bitcasa.cloudfs.model UserProfile

.. java:import:: com.google.gson Gson

.. java:import:: com.google.gson GsonBuilder

.. java:import:: com.google.gson JsonObject

.. java:import:: java.io BufferedInputStream

.. java:import:: java.io BufferedOutputStream

.. java:import:: java.io File

.. java:import:: java.io FileOutputStream

.. java:import:: java.io IOException

.. java:import:: java.io InputStream

.. java:import:: java.io OutputStream

.. java:import:: java.io UnsupportedEncodingException

.. java:import:: java.math BigInteger

.. java:import:: java.net HttpURLConnection

.. java:import:: java.net MalformedURLException

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

.. java:type:: public class RESTAdapter

   Entry point to all CloudFS API requests.

Constructors
------------
RESTAdapter
^^^^^^^^^^^

.. java:constructor:: public RESTAdapter(Credential credential)
   :outertype: RESTAdapter

   Constructor, takes in a credential instance and initialises the RESTAdapter instance.

   :param credential: Application Credentials.

Methods
-------
alterMeta
^^^^^^^^^

.. java:method:: public Item alterMeta(Item meta, Map<String, String> changes, int version, VersionExists versionExists) throws BitcasaException, IOException
   :outertype: RESTAdapter

   Alter File Meta

   :param meta: Item object of file meta
   :param changes: String map of meta changes
   :param version: Integer version
   :param versionExists: VersionExists enum
   :throws BitcasaException: If a CloudFS API error occurs.
   :throws IOException: If a network error occurs
   :throws BitcasaAuthenticationException: If user not authenticated
   :return: Item object with altered meta

alterShare
^^^^^^^^^^

.. java:method:: public Share alterShare(String shareKey, Map<String, String> changes, String currentPassword) throws IOException, BitcasaException
   :outertype: RESTAdapter

   Alters the Shares attributes.

   :param shareKey: The share key of the specified share.
   :param changes: The changes to be updated.
   :param currentPassword: The current password of the share.
   :throws BitcasaException: If a CloudFS API error occurs.
   :throws IOException: If a network error occurs
   :return: Share object of the altered share

alterShareInfo
^^^^^^^^^^^^^^

.. java:method:: public Share alterShareInfo(String shareKey, String currentPassword, String newPassword) throws IOException, BitcasaException
   :outertype: RESTAdapter

   Alter Share Info

   :param shareKey: String share key of the share
   :param currentPassword: The current password of the share
   :param newPassword: The new password of the share
   :throws BitcasaException: If a CloudFS API error occurs.
   :throws IOException: If a network error occurs
   :return: Share object of the altered share info

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

   Given the sharekey and the path to any folder/file under share, browseShare method will return the item list for that share. Make sure unlockShare is called before browseShare

   :param shareKey: String share key of the share
   :param path: String file path
   :throws BitcasaException: If a CloudFS API error occurs.
   :throws IOException: If a network error occurs
   :return: Share object

browseTrash
^^^^^^^^^^^

.. java:method:: public Item[] browseTrash() throws IOException, BitcasaException
   :outertype: RESTAdapter

   Browse Trash

   :throws BitcasaException: If a CloudFS API error occurs.
   :throws IOException: If a network error occurs
   :return: An array of item objects

copy
^^^^

.. java:method:: public Item copy(Item item, String destinationPath, String newName, Exists exists) throws IOException, BitcasaException
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
   :param username: String username.
   :param password: String password.
   :param email: String email.
   :param firstName: String first name.
   :param lastName: String last name.
   :throws BitcasaException: If a CloudFS API error occurs.
   :throws IllegalArgumentException: If the parameters are invalid or misused.
   :throws IOException: If a network error occurs.
   :return: User object.

createFolder
^^^^^^^^^^^^

.. java:method:: public Folder createFolder(String folderName, Folder parentFolder, Exists exists) throws IOException, BitcasaException
   :outertype: RESTAdapter

   Creates a folder in the CloudFS file system.

   :param folderName: The name of the folder to be created.
   :param parentFolder: The parent folder under which the new folder is to be created.
   :param exists: Action to take if the folder to be created already exists.
   :throws BitcasaException: If a CloudFS API error occurs.
   :throws IOException: If a network error occurs.
   :return: An instance of the newly created folder.

createShare
^^^^^^^^^^^

.. java:method:: public Share createShare(String path, String password) throws IOException, BitcasaException
   :outertype: RESTAdapter

   Creates a share including the item in the path specified.

   :param path: String file path
   :param password: String password
   :throws BitcasaException: If a CloudFS API error occurs.
   :throws IOException: If a network error occurs
   :return: Share object created

deleteFile
^^^^^^^^^^

.. java:method:: public boolean deleteFile(String path, boolean commit, boolean force) throws IOException, BitcasaException
   :outertype: RESTAdapter

   Deletes an existing file from the CloudFS file system.

   :param path: String file path.
   :param commit: if boolean false, transfer file to trash.
   :param force: if boolean true, deletes file without trashing.
   :throws BitcasaException: If a CloudFS API error occurs.
   :throws IOException: If response data can not be read due to network errors.
   :return: boolean flag whether the file was successfully deleted or not.

deleteFolder
^^^^^^^^^^^^

.. java:method:: public boolean deleteFolder(String path, boolean commit, boolean force) throws IOException, BitcasaException
   :outertype: RESTAdapter

   Deletes the folder at given path.

   :param commit: If true, folder is deleted immediately. Otherwise, it is moved to the Trash. The default is false.
   :param force: If true, folder is deleted even if it contains sub-items. The default is false.
   :throws BitcasaException: If a CloudFS API error occurs.
   :throws IOException: If response data can not be read due to network errors.
   :return: Returns true if the folder is deleted successfully, otherwise false.

deleteShare
^^^^^^^^^^^

.. java:method:: public boolean deleteShare(String shareKey) throws BitcasaException
   :outertype: RESTAdapter

   Delete Share

   :param shareKey: String share key of the share
   :throws BitcasaException: If a CloudFS API error occurs.
   :return: Boolean if the share was deleted or not

deleteTrashItem
^^^^^^^^^^^^^^^

.. java:method:: public boolean deleteTrashItem(String path) throws BitcasaException
   :outertype: RESTAdapter

   Delete Trash Item

   :param path: String file path
   :throws BitcasaException: If a CloudFS API error occurs.
   :return: Boolean whether the item in trash was deleted or not

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
   :param range: Any valid content range. No less than 0, no greater than the filesize.
   :param localDestination: Device file location with file path and name.
   :param listener: The progress listener to listen to the file download progress.
   :throws IOException: If a network error occurs.
   :throws BitcasaException: If a CloudFS API error occurs.

downloadUrl
^^^^^^^^^^^

.. java:method:: public String downloadUrl(String path, long size) throws IOException, BitcasaException
   :outertype: RESTAdapter

   Creates a redirect url from a given path.

   :param path: The actual file path.
   :param size: The actual size of the file
   :throws IOException: If a network error occurs.
   :return: The redirect url.

getFolderMeta
^^^^^^^^^^^^^

.. java:method:: public Folder getFolderMeta(String absolutePath) throws BitcasaException, IOException
   :outertype: RESTAdapter

   Get Folder Meta.

   :param absolutePath: String file location
   :throws BitcasaException: If a CloudFS API error occurs.
   :throws IOException: If a network error occurs
   :throws BitcasaAuthenticationException: If user not authenticated
   :return: Folder object at the given path.

getItemMeta
^^^^^^^^^^^

.. java:method:: public Item getItemMeta(String absolutePath) throws BitcasaException, IOException
   :outertype: RESTAdapter

   Get Item Meta.

   :param absolutePath: String file location.
   :throws BitcasaException: If a CloudFS API error occurs.
   :throws IOException: If a network error occurs.
   :throws BitcasaAuthenticationException: If user not authenticated.
   :return: Item object at the given path.

getList
^^^^^^^

.. java:method:: public Item[] getList(String folderPath, int version, int depth, String filter) throws IOException, BitcasaException
   :outertype: RESTAdapter

   Lists all the files and folders under the given folder path.

   :param folderPath: String folder path.
   :param version: String version.
   :param depth: Integer depth.
   :param filter: String filter.
   :throws BitcasaException: If a CloudFS API error occurs.
   :throws IOException: If a network error occurs.
   :return: Item array of files and folders.

listFileVersions
^^^^^^^^^^^^^^^^

.. java:method:: public com.bitcasa.cloudfs.File[] listFileVersions(String path, int startVersion, int stopVersion, int limit) throws BitcasaException, IOException
   :outertype: RESTAdapter

   List File Versions

   :param path: String file path
   :param startVersion: Integer starting file version
   :param stopVersion: Integer Ending version
   :param limit: Integer file Limit
   :throws BitcasaException: If a CloudFS API error occurs.
   :throws IOException: If a network error occurs.
   :return: A list of file meta data results, as they have been recorded in the file version history after successful meta data changes.

listHistory
^^^^^^^^^^^

.. java:method:: public List<BaseAction> listHistory(int startVersion, int stopVersion) throws IOException, BitcasaException
   :outertype: RESTAdapter

   List History

   :param startVersion: Integer starting file version
   :param stopVersion: Integer Ending version
   :throws BitcasaException: If a CloudFS API error occurs.
   :throws IOException: If a network error occurs
   :return: ActionHistory object

listShare
^^^^^^^^^

.. java:method:: public Share[] listShare() throws IOException, BitcasaException
   :outertype: RESTAdapter

   Lists the shares the user has created.

   :throws BitcasaException: If a CloudFS API error occurs.
   :throws IOException: If a network error occurs
   :return: list of ShareItem

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

   Given a valid location in a user's fileSystem, all items found in this share specified by the sharekey will be inserted into the given location. File collisions will be handled with the exist action specified.

   :param shareKey: String share key of the share
   :param pathToInsertShare: String path to insert share
   :param exists: Exists enum
   :throws BitcasaException: If a CloudFS API error occurs.
   :throws IOException: If a network error occurs
   :return: An array of container object

recoverTrashItem
^^^^^^^^^^^^^^^^

.. java:method:: public boolean recoverTrashItem(String path, BitcasaRESTConstants.RestoreMethod restoreMethod, String rescueOrRecreatePath) throws UnsupportedEncodingException, BitcasaException
   :outertype: RESTAdapter

   Recover Trash Item

   :param path: String file path
   :param restoreMethod: RestoreMethod enum
   :param rescueOrRecreatePath: String path to rescue or recreate file
   :throws UnsupportedEncodingException: If encoding not supported
   :throws BitcasaException: If a CloudFS API error occurs.
   :return: Boolean whether the item was recovered from trash or not

requestAccountInfo
^^^^^^^^^^^^^^^^^^

.. java:method:: public Account requestAccountInfo() throws IOException, BitcasaException
   :outertype: RESTAdapter

   Requests to retrieve account information from the CloudFS server.

   :throws BitcasaException: If a CloudFS API error occurs.
   :throws IOException: If a network error occurs.
   :return: Bitcasa Account object.

requestUserInfo
^^^^^^^^^^^^^^^

.. java:method:: public User requestUserInfo() throws IOException, BitcasaException
   :outertype: RESTAdapter

   Requests to retrieve user information from the CloudFS server.

   :throws BitcasaException: If a CloudFS API error occurs.
   :throws IOException: If a network error occurs.
   :return: Bitcasa User object.

unlockShare
^^^^^^^^^^^

.. java:method:: public boolean unlockShare(String shareKey, String password) throws IOException, BitcasaException
   :outertype: RESTAdapter

   Given a valid share and its password, this entrypoint will unlock the share for the login session.

   :param shareKey: String share key of the share
   :param password: String password
   :throws BitcasaException: If a CloudFS API error occurs.
   :throws IOException: If a network error occurs
   :return: boolean of whether the share was unlocked or not

uploadFile
^^^^^^^^^^

.. java:method:: public com.bitcasa.cloudfs.File uploadFile(Item folder, String sourceFilePath, Exists exists, BitcasaProgressListener listener) throws IOException, BitcasaException
   :outertype: RESTAdapter

   Upload a local file to the CloudFS server.

   :param folder: Item object of the folder.
   :param sourceFilePath: String path of the source file.
   :param exists: Exists enum which specifies the action to take if the file already exists.
   :param listener: The progress listener to listen to the file upload progress.
   :throws BitcasaException: If a CloudFS API error occurs.
   :throws IOException: If a network error occurs.
   :return: CloudFS file meta data of this successful upload, null if upload failed.

