# CloudFS SDK for Android
  
The **CloudFS SDK for Android** enables Android developers to easily work with [CloudFS Cloud Storage Platform](https://www.bitcasa.com/cloudfs/) and build scalable solutions.

* [REST API Documentation](https://developer.bitcasa.com/cloudfs-api-documentation/)
* [Blog](http://blog.bitcasa.com/) 

## Getting Started

If you have already [signed up](http://access.bitcasa.com/Sign-Up/Info/Prototype/) and obtained your credentials you can get started in minutes.


Cloning the git repository.

  ```bash
  $ git clone https://github.com/bitcasa/CloudFS-Android.git
  ```

## Using the SDK

Use the credentials you obtained from Bitcasa admin console to create a client session. This session can be used for all future requests to Bitcasa.

```java
Session session = new Session(endpoint, clientId, clientSecret);
session.authenticate(username, password);
```

Getting the root folder

```java
root = fileSystem.root();
```

Getting the contents of root folder

```java
items = root.list();
```

Creating a sub folder under root folder

```java
folder = root.createFolder(String name, Exists exist);
```
Uploading a file to a folder

```java
uploadedFile = folder.upload(String filesystemPath, BitcasaProgressListener listener, BitcasaRESTConstants.Exists exists);
```

Download a file to a local destination.

```java
uploadedFile.download(String localDestinationPath, BitcasaProgressListener listener);
```

Deleting a file or folder

```java
item.delete(boolean commit, boolean force);
```

Create user (for paid accounts only)

```java
session.setAdminCredentials(String adminClientId, String adminClientSecret);
user = session.createAccount(String username, String password, String email, String firstName, String lastName, Boolean logInToCreatedUser);
```

Create account plan (for paid accounts only)

```java
session.setAdminCredentials(String adminClientId, String adminClientSecret);
user = session.createPlan(String planName, String planLimit)
```


## Test Suite

The tests that exist are functional tests designed to be used with a CloudFS test user. They use API credentials on your free CloudFS account. You should add the credentials to the file app\src\test\java\com\bitcasa\cloudfs\BaseTest.java.


## Support

If you have any questions, comments or encounter any bugs please contact us at sdks@bitcasa.com.