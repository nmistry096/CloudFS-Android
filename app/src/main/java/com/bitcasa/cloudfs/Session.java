/**
 * Bitcasa Client Android SDK
 * Copyright (C) 2015 Bitcasa, Inc.
 * 215 Castro Street, 2nd Floor
 * Mountain View, CA 94041
 *
 * This file contains an SDK in Java for accessing the Bitcasa infinite drive in Android platform.
 *
 * For support, please send email to support@bitcasa.com.
 */

package com.bitcasa.cloudfs;

import com.bitcasa.cloudfs.api.RESTAdapter;
import com.bitcasa.cloudfs.exception.BitcasaException;
import com.bitcasa.cloudfs.model.ActionFactory;
import com.bitcasa.cloudfs.model.BaseAction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The Session class provides accessibility to CloudFS.
 */
public class Session {

    /**
     * The client id.
     */
    private String clientId;

    /**
     * The client secret.
     */
    private String clientSecret;

    /**
     * The admin client id.
     */
    private String adminClientId;

    /**
     * The admin client secret.
     */
    private String adminClientSecret;

    /**
     * The application credentials.
     */
    private final Credential credential;

    /**
     * The api service to access REST end point.
     */
    private RESTAdapter restAdapter;

    /**
     * Initializes an instance of CloudFS Session.
     *
     * @param endPoint     The REST Adapter instance.
     * @param clientId     The file meta data returned from REST Adapter.
     * @param clientSecret The absolute parent path of this file.
     */
    public Session(String endPoint, String clientId, String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;

        this.credential = new Credential(endPoint);
        this.credential.setEndPoint(endPoint);

        this.restAdapter = new RESTAdapter(credential);
    }

    /**
     * Links a user to the session by authenticating using a username and password.
     *
     * @param username The specified username.
     * @param password The specified password.
     * @throws IOException      If a network error occurs.
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    public void authenticate(String username, String password)
            throws IOException, BitcasaException {
        restAdapter.authenticate(this, username, password);
    }

    /**
     * Checks whether a specific user is linked to the session or not.
     *
     * @return The value indicating whether the operation was successful or not.
     */
    public boolean isLinked() {
        return credential.getAccessToken() != null;
    }

    /**
     * Unlinks a specific user from the session.
     */
    public void unlink() {
        credential.setAccessToken(null);
        credential.setTokenType(null);
    }

    /**
     * Gets the current user information.
     *
     * @return The user information.
     * @throws IOException      If a network error occurs
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    public User user() throws IOException, BitcasaException {
        return restAdapter.requestUserInfo();
    }

    /**
     * Gets the current account information.
     *
     * @return The account information.
     * @throws IOException If a network error occurs
     */
    public Account account() throws IOException, BitcasaException {
        return restAdapter.requestAccountInfo();
    }

    /**
     * Gets an instance of filesystem.
     *
     * @return The filesystem instance.
     */
    public FileSystem filesystem() {
        return new FileSystem(restAdapter);
    }

    /**
     * Gets the session client id.
     *
     * @return The client id.
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * Gets the sessions client secret.
     *
     * @return The client secret.
     */
    public String getClientSecret() {
        return clientSecret;
    }

    /**
     * Gets an instance of the RESTAdapter.
     *
     * @return An instance of the RESTAdapter.
     */
    public RESTAdapter getRestAdapter() {
        return restAdapter;
    }

    /**
     * Set the sessions admin credentials.
     *
     * @param adminClientId     The admin client id.
     * @param adminClientSecret The admin client secret.
     */
    public void setAdminCredentials(String adminClientId, String adminClientSecret) {
        this.adminClientId = adminClientId;
        this.adminClientSecret = adminClientSecret;
    }

    /**
     * Gets the admin client id.
     *
     * @return The admin client id.
     */
    public String getAdminClientId() {
        return this.adminClientId;
    }

    /**
     * Gets the admin client secret.
     *
     * @return The admin client secret.
     */
    public String getAdminClientSecret() {
        return this.adminClientSecret;
    }

    /**
     * Gets the action history.
     *
     * @param startVersion Integer representing which version number to start listing historical
     *                     actions from.
     * @param stopVersion  Integer representing which version number from which to stop listing
     *                     historical actions.
     * @return The action history.
     * @throws IOException      If a network error occurs
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    public ActionHistory actionHistory(int startVersion, int stopVersion)
            throws IOException, BitcasaException {
        List<BaseAction> baseActions = restAdapter.listHistory(startVersion, stopVersion);
        ArrayList<BaseAction> actions = new ArrayList<BaseAction>();
        for (BaseAction action : baseActions) {
            actions.add(ActionFactory.getAction(action));
        }

        return new ActionHistory(actions);
    }

    /**
     * Create a new user account and logs in to the account created,
     * if the logInToCreatedUser flag is set.
     *
     * @param username           The username for the new user account.
     * @param password           The password for the new user account.
     * @param email              The email for the new user account.
     * @param firstName          The first name for the new user.
     * @param lastName           The last name for the new user.
     * @param logInToCreatedUser The login to created user flag which sets the method to
     *                           authenticate the user and logs the user created.
     * @return The newly created user instance.
     */
    public User createAccount(String username, String password, String email, String firstName,
                              String lastName, Boolean logInToCreatedUser)
            throws IOException,
            BitcasaException {
        User user = restAdapter.createAccount(this, username, password, email, firstName, lastName);
        if (user != null && logInToCreatedUser) {
            authenticate(username, password);
        }
        return user;
    }

    /**
     * Set the access token of this Session credentials instance.
     *
     * @param accessToken The access token to be set.
     */
    public void setAccessToken(String accessToken) {
        this.credential.setAccessToken(accessToken);
    }
}
