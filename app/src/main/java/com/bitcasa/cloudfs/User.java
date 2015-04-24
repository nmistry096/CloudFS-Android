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
import com.bitcasa.cloudfs.model.UserProfile;

import java.util.Date;

/**
 * The User class provides accessibility to CloudFS User.
 */
public class User {

    /**
     * The api service to access REST endpoint.
     */
    private RESTAdapter restAdapter;

    /**
     * The user id.
     */
    private String userId;

    /**
     * The user name.
     */
    private String username;

    /**
     * The user created date and time.
     */
    private Date createdTime;

    /**
     * The user's last login date and time.
     */
    private Date lastLogin;

    /**
     * The user's first name.
     */
    private String firstName;

    /**
     * The user's last name.
     */
    private String lastName;

    /**
     * The user's email address.
     */
    private String email;

    /**
     * Initializes an instance of CloudFS User.
     *
     * @param restAdapter The REST Adapter instance.
     * @param profile     The user profile.
     */
    public User(final RESTAdapter restAdapter, final UserProfile profile) {
        this.userId = profile.getId();
        this.username = profile.getUsername();
        this.createdTime = new Date(profile.getCreatedAt());
        this.firstName = profile.getFirstName();
        this.lastName = profile.getLastName();
        this.email = profile.getEmail();
        this.lastLogin = new Date(profile.getLastLogin());
        this.restAdapter = restAdapter;
    }

    /**
     * Gets the user's email address.
     *
     * @return The user's email address.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets the user's first name.
     *
     * @return The user's first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Gets the user's last name.
     *
     * @return The user's last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Gets the user id.
     *
     * @return The user id.
     */
    public String getId() {
        return userId;
    }

    /**
     * Gets the username.
     *
     * @return The username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the user's last login date and time.
     *
     * @return The user's last login date and time.
     */
    public Date getLastLogin() {
        return lastLogin;
    }

    /**
     * Gets the user created date and time.
     *
     * @return The user created date and time.
     */
    public Date getCreatedAt() {
        return createdTime;
    }

    /**
     * Creates a string containing a concise, human-readable description of User object.
     *
     * @return The printable representation of User object.
     */
    @Override
    public String toString() {
        return "\nusername[" + username + "] \ncreated_time[" + Long.toString(createdTime.getTime())
                + "] \nfirst_name[" + firstName + "] \nlast_name[" + lastName + "] \nemail[" + email
                + "]*****";
    }
}
