/**
 * Bitcasa Client Android SDK
 * Copyright (C) 2015 Bitcasa, Inc.
 * 1200 Park Place,
 * Suite 350 San Mateo, CA 94403.
 *
 * This file contains an SDK in Java for accessing the Bitcasa infinite drive in Android platform.
 *
 * For support, please send email to sdks@bitcasa.com.
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
    private final String clientId;

    /**
     * The client secret.
     */
    private final String clientSecret;

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
    private final RESTAdapter restAdapter;

    /**
     * Initializes an instance of CloudFS Session.
     *
     * @param endPoint     The REST Adapter instance.
     * @param clientId     The file meta data returned from REST Adapter.
     * @param clientSecret The absolute parent path of this file.
     */
    public Session(final String endPoint, final String clientId, final String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;

        this.credential = new Credential(endPoint);
        this.credential.setEndPoint(endPoint);

        this.restAdapter = new RESTAdapter(this.credential);
    }

    /**
     * Links a user to the session by authenticating using a username and password.
     *
     * @param username The specified username.
     * @param password The specified password.
     * @throws IOException      If a network error occurs.
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    public void authenticate(final String username, final String password)
            throws IOException, BitcasaException {
        this.restAdapter.authenticate(this, username, password);
    }

    /**
     * Checks whether a specific user is linked to the session or not.
     *
     * @return The value indicating whether the operation was successful or not.
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    public boolean isLinked() throws BitcasaException {
        if (this.credential.getAccessToken() != null) {
            return this.restAdapter.ping();
        } else {
            return false;
        }

    }

    /**
     * Unlinks a specific user from the session.
     */
    public void unlink() {
        this.credential.setAccessToken(null);
        this.credential.setTokenType(null);
    }

    /**
     * Gets the current user information.
     *
     * @return The user information.
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    public User user() throws BitcasaException {
        return this.restAdapter.requestUserInfo();
    }

    /**
     * Gets the current account information.
     *
     * @return The account information.
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    public Account account() throws BitcasaException {
        return this.restAdapter.requestAccountInfo();
    }

    /**
     * Gets an instance of filesystem.
     *
     * @return The filesystem instance.
     */
    public FileSystem filesystem() {
        return new FileSystem(this.restAdapter);
    }

    /**
     * Gets the session client id.
     *
     * @return The client id.
     */
    public String getClientId() {
        return this.clientId;
    }

    /**
     * Gets the sessions client secret.
     *
     * @return The client secret.
     */
    public String getClientSecret() {
        return this.clientSecret;
    }

    /**
     * Gets an instance of the RESTAdapter.
     *
     * @return An instance of the RESTAdapter.
     */
    public RESTAdapter getRestAdapter() {
        return this.restAdapter;
    }

    /**
     * Set the sessions admin credentials.
     *
     * @param adminClientId     The admin client id.
     * @param adminClientSecret The admin client secret.
     */
    public void setAdminCredentials(final String adminClientId, final String adminClientSecret) {
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
     * Set the access token of this Session credentials instance.
     *
     * @param accessToken The access token to be set.
     */
    public void setAccessToken(final String accessToken) {
        this.credential.setAccessToken(accessToken);
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
    public ActionHistory actionHistory(final int startVersion, final int stopVersion)
            throws IOException, BitcasaException {
        final List<BaseAction> baseActions = this.restAdapter.listHistory(startVersion, stopVersion);
        final ArrayList<BaseAction> actions = new ArrayList<BaseAction>();
        for (final BaseAction action : baseActions) {
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
    public User createAccount(final String username, final String password, final String email, final String firstName,
                              final String lastName, final Boolean logInToCreatedUser)
            throws IOException,
            BitcasaException {
        final User user = this.restAdapter.createAccount(this, username, password, email, firstName, lastName);
        if ((user != null) && logInToCreatedUser) {
            this.authenticate(username, password);
        }
        return user;
    }

    /**
     * Creates a new account plan with the supplied data.
     *
     * @param name    The name of the account plan.
     * @param limit   The limit for the account plan.
     * @return The newly created account plan instance.
     * @throws IOException              If a network error occurs.
     * @throws IllegalArgumentException If the parameters are invalid or misused.
     * @throws BitcasaException         If a CloudFS API error occurs.
     */
    public Plan createPlan(final String name, String limit) throws BitcasaException, IOException {
        return this.restAdapter.createPlan(this, name, limit);
    }

    /**
     * Update the user details and account plan for the given the user account code.
     *
     * @param id        The account id of the user account.
     * @param username  The username of the account to be updated.
     * @param firstName The first name of the account to be updated.
     * @param lastName  The last name of the account to be updated.
     * @param planCode  The plan code of the account to be updated.
     * @return The updated user.
     * @throws BitcasaException If a CloudFS API error occurs.
     * @throws IOException      If response data can not be read due to network errors.
     */
    public User updateUser(final String id, final String username, final String firstName,
                           final String lastName, final String planCode)
            throws BitcasaException, IOException {
        return this.restAdapter.updateUser(this, id, username, firstName, lastName, planCode);
    }

    /**
     * Lists the custom end user account plans.
     *
     * @return List of custom end user plans.
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    public Plan[] listPlans() throws BitcasaException {
        return this.restAdapter.listPlans(this);
    }
}
