# CloudFS SDK for Android
  
This SDK enables Android developers to easily work with the [CloudFS Storage Platform](https://www.bitcasa.com/cloudfs/) and build scalable solutions. CloudFS provides developers with a seamless and secure way to integrate cloud storage, access, and sharing functionality into applications.

* [CloudFS FAQs](https://developer.bitcasa.com/faqs/)
* [REST API Documentation](https://developer.bitcasa.com/cloudfs-api-documentation/)
* [Blog](http://blog.bitcasa.com/) 
* [Free Prototype Account](https://access.bitcasa.com/Sign-Up/Info/Prototype/) 

## Getting Started

If you have already [signed up](http://access.bitcasa.com/Sign-Up/Info/Prototype/) and obtained your credentials you can get started in minutes.

Cloning the git repository.

  ```bash
  $ git clone https://github.com/bitcasa/CloudFS-Android.git
  ```

## Using the SDK

Use the credentials you obtained from the CloudFS admin console to create a client session. This session can be used for all future requests to Bitcasa.

```java
Session session = new Session(endpoint, clientId, clientSecret);
session.authenticate(username, password);
```

Getting the root folder

```java
Folder root = fileSystem.root();
```

Getting the contents of root folder

```java
Item [] items = root.list();
```

Creating a sub folder under root folder

```java
Folder folder = root.createFolder(String name, Exists exist);
```
Uploading a file to a folder

```java
File uploadedFile = folder.upload(String filesystemPath, BitcasaProgressListener listener, BitcasaRESTConstants.Exists exists);
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
User user = session.createAccount(String username, String password, String email, String firstName, String lastName, Boolean logInToCreatedUser);
```

Create account plan (for paid accounts only)

```java
session.setAdminCredentials(String adminClientId, String adminClientSecret);
Plan plan = session.createPlan(String planName, String planLimit)
```


## Test Suite

The tests that exist are junit tests designed to be used with a CloudFS test user. They use API credentials on your free CloudFS account. To test administrative operations you will have to acquire a paid account which would provide you with separate admin credentials.  

You should add these credentials to the file:  
  
```bash
app\src\test\java\com\bitcasa\cloudfs\BaseTest.java.
```  

After adding the credentials, select the, 'Unit Tests,' test artifact under Build Variants in Android Studio.  

## Support

If you have any questions, comments or encounter any bugs please contact us at sdks@bitcasa.com.
