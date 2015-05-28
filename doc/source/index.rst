.. highlight:: java

=========================================
CloudFS Android SDK User Guide
=========================================
.. contents:: Contents
   :depth: 2
   
|

API Reference
~~~~~~~~~~~~~~~

Refer the `Javadoc <packages>`_ documentation to view class and method details.

|

Getting the SDK
~~~~~~~~~~~~~~~~

You can download the SDK from github.

.. code-block:: bash

	git clone https://github.com/bitcasa/CloudFS-Android
	
|

Using the SDK
~~~~~~~~~~~~~~~~~~~~~~~~~
If you don't have a CloudFS API account, |register_link| to gain access.

.. |register_link| raw:: html

   <a href="https://www.bitcasa.com/" target="_blank">register</a>


Creating and linking the CloudFS Sessions
-----------------------------------------
Sessions represent connections to CloudFS. They use a set of credentials that consists of an end point URL,
a client ID and a client secret. These credentials can be obtained via the Bitcasa admin console.

:java:type:`Session`  - Performs regular file system operations.

      ::

        session = new Session(String endPoint, String clientId, String clientSecret);

A user can be linked to the session by authenticating using a username and a password.

      ::

        session.authenticate(String username, String password);

You can assert whether a user is linked to the session.

      ::

        session.isLinked();

The currently linked user can be unlinked from the session.

      ::

        session.unlink();

Admin Operations
----------------
.. note:: You need to set the admin credentials in order to perform admin operations.
  You can create end users for an admin/paid account. If 'logInToCreatedUser' is true, logs in to the user after creating it.

- `Create Account <com/bitcasa/cloudfs/Session.html#createaccount>`_

      ::

        session.setAdminCredentials(String adminClientId, String adminClientSecret);
        user = session.createAccount(String username, String password, String email, String firstName, String lastName, Boolean logInToCreatedUser);

- `Create Account Plan <com/bitcasa/cloudfs/Session.html#createplan>`_

      ::

        session.setAdminCredentials(String adminClientId, String adminClientSecret);
        Plan plan = session.createPlan(String planName, String planLimit);

- `List Account Plans <com/bitcasa/cloudfs/Session.html#listplans>`_

      ::

        session.setAdminCredentials(String adminClientId, String adminClientSecret);
        Plan[] plan = session.listPlans();

- `Update User <com/bitcasa/cloudfs/Session.html#updateuser>`_

      ::

        session.setAdminCredentials(String adminClientId, String adminClientSecret);
        user = session.updateUser(String accountId, String username, String firstname, String lastname, String planCode);


File System Operations
----------------------
.. note:: You need to create a session in order to perform file system operations.

- `Get Root Folder <com/bitcasa/cloudfs/FileSystem.html#root>`_

      ::

        fileSystem = new Filesystem(session.getRestAdapter());
        root = fileSystem.root();

	  
- `Get Specific Item <com/bitcasa/cloudfs/FileSystem.html#getitem>`_

      ::

        file = fileSystem.getItem(String path);


- `List Trash Items <com/bitcasa/cloudfs/FileSystem.html#listtrash>`_

  You can list down the contents of Trash folder. Below example shows how to retrieve contents of the trash folder.
 
      ::

        trash = fileSystem.listTrash();


- `Get Shares <com/bitcasa/cloudfs/FileSystem.html#listshares>`_

  You can list down available Shares. Below example shows how to retrieve the list of shares.
 
      ::

        items = fileSystem.listShares();


- `Create Share <com/bitcasa/cloudfs/FileSystem.html#createshare>`_

  You can create a share by providing the path as shown in below example. A passworded share cannot be used for anything if the password is not provided. It doesn't make sense to create a share unless the developer has the password.
 
      ::

        share = fileSystem.createShare(String itemToSharePath, String password);


- `Get Specific Share <com/bitcasa/cloudfs/FileSystem.html#retrieveshare>`_

  You can get a share by providing the share key and the password (If available). A passworded share cannot be used for anything if the password is not provided.
 
      ::

        share = fileSystem.retrieveShare(String shareKey, String password);


Folder Operations
-----------------
.. note:: You need to create a session in order to perform folder operations.

- `List Folder Contents <com/bitcasa/cloudfs/Container.html#list>`_

  You can list the contents of a folder. This will return a list of top level folders and items in the specified folder.

      ::

        items = folder.list();


- `Change Folder Attributes <com/bitcasa/cloudfs/Item.html#changeattributes>`_

  You can change the attributes of a Folder by providing a hash map of field names and values. An example is given below.

      ::

        folder.changeAttributes(HashMap<String, String> values, VersionConflict conflictAction);

   	 
- `Copy Folder <com/bitcasa/cloudfs/Item.html#copy>`_

  You can copy a folder to a new location in the file system. If the destination conflicts with the copying folder you can either RENAME, OVERWRITE or FAIL the operation.

      ::

        newFolder = folder.copy(Container destination, Exists exists);


- `Move Folder <com/bitcasa/cloudfs/Item.html#move>`_

  You can move a folder to a new location in the file system. If the destination conflicts with the moving folder you can either RENAME, OVERWRITE or FAIL the operation.

      ::

        newFolder = folder.move(Container destination, Exists exists);


- `Delete Folder <com/bitcasa/cloudfs/Item.html#delete>`_

  You can perform the delete operation on a folder. This will return the Success/fail status of the operation.

      ::

        status = folder.delete(boolean commit, boolean force);


- `Restore Folder <com/bitcasa/cloudfs/Item.html#restore>`_

  You can restore a Folder from the trash. The restore method can be set to either FAIL, RESCUE or RECREATE. This will return the Success/failure status of the operation.

      ::

        status = folder.restore(Container destination, RestoreMethod method, String restoreArgument);


- `Create Sub Folder <com/bitcasa/cloudfs/Folder.html#createfolder>`_

  You can create a sub folder in a specific folder. If the folder already has a sub folder with the given name, the operation will fail.

      ::

        subFolder = folder.createFolder(Container item, Exists exist);


- `Upload File <com/bitcasa/cloudfs/Folder.html#upload>`_

  You can upload a file from your local file system into a specific folder. If the destination conflicts, you can either RENAME, OVERWRITE or FAIL the operation.

      ::

        file = folder.upload(String filesystemPath, BitcasaProgressListener listener, BitcasaRESTConstants.Exists exists);


File Operations
---------------
.. note:: You need to create a session in order to perform file operations.

- `Change File Attributes <com/bitcasa/cloudfs/Item.html#changeattributes>`_

  You can change the attributes of a File by providing a hash map of field names and values. An example is given below.

      ::

        file.changeAttributes(HashMap<String, String> values, VersionConflict conflictAction);

   	 
- `Copy File <com/bitcasa/cloudfs/Item.html#copy>`_

  You can copy a file to a new location in the file system. If the destination conflicts with the copying file you can either RENAME, OVERWRITE or FAIL the operation.

      ::

        newFile = file.copy(Container destination, Exists exists);


- `Move File <com/bitcasa/cloudfs/Item.html#move>`_

  You can move a file to a new location in the file system. If the destination conflicts with the moving file you can either RENAME, OVERWRITE or FAIL the operation.

      ::

        newFile = file.move(Container destination, Exists exists);


- `Delete File <com/bitcasa/cloudfs/Item.html#delete>`_

  You can perform the delete operation on a file. This will return the Success/fail status of the operation.

      ::

        status = file.delete(boolean commit, boolean force);


- `Restore File <com/bitcasa/cloudfs/Item.html#restore>`_

  You can restore files from the trash. The restore method can be set to either FAIL, RESCUE or RECREATE. This will return the Success/failure status of the operation.

      ::

        status = file.restore(Container destination, RestoreMethod method, String restoreArgument);


- `Download File <com/bitcasa/cloudfs/File.html#download>`_

  You can download a file to your local file system.

      ::

        content = file.download(String localDestinationPath, BitcasaProgressListener listener);


Share Operations
-----------------
.. note:: You need to create a session in order to perform share operations.

- `Change Share Attributes <com/bitcasa/cloudfs/Share.html#changeattributes>`_

  You can change the attributes of a Share by providing a hash map of field names and values. An example is given below.

      ::

        share.changeAttributes(HashMap<String, String> values, String sharePassword);


- `Receive Share <com/bitcasa/cloudfs/Share.html#receive>`_

  Receives all share files to the given path.

      ::

        share.receive(String path, BitcasaRESTConstants.Exists exists);

 
- `Delete Share <com/bitcasa/cloudfs/Share.html#delete>`_

      ::

        share.delete();

- `Set Share Password <com/bitcasa/cloudfs/Share.html#setpassword>`_

  Sets the share password. Old password is only needed if one exists.

      ::

        share.setPassword(String newPassword, String currentPassword);

 
