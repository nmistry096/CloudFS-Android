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

import android.os.Parcel;
import android.os.Parcelable;

import com.bitcasa.cloudfs.api.RESTAdapter;
import com.bitcasa.cloudfs.model.UserProfile;

import java.util.Date;

/**
 * The User class provides accessibility to CloudFS User.
 */
public class User implements Parcelable {

    /**
     * The api service to access REST endpoint.
     */
    private final RESTAdapter restAdapter;

    /**
     * The user id.
     */
    private final String userId;

    /**
     * The user name.
     */
    private final String username;

    /**
     * The user created date and time.
     */
    private final Date createdTime;

    /**
     * The user's last login date and time.
     */
    private final Date lastLogin;

    /**
     * The user's first name.
     */
    private final String firstName;

    /**
     * The user's last name.
     */
    private final String lastName;

    /**
     * The user's email address.
     */
    private final String email;

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
     * {@inheritDoc}
     */
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {

        @Override
        /**
         * {@inheritDoc}
         */
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        /**
         * {@inheritDoc}
         */
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    /**
     * Initializes the User instance.
     *
     * @param in The parcel object containing the user details.
     */
    public User(Parcel in) {
        userId = in.readString();
        username = in.readString();
        createdTime = new Date(in.readLong());
        firstName = in.readString();
        lastName = in.readString();
        email = in.readString();
        lastLogin = new Date(in.readLong());
        restAdapter = (RESTAdapter) in.readValue(RESTAdapter.class.getClassLoader());
    }

    /**
     * @return @inheritDoc
     * @inheritDoc
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * @param out   @inheritDoc
     * @param flags @inheritDoc
     * @inheritDoc
     */
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(userId);
        out.writeString(username);
        out.writeLong(createdTime.getTime());
        out.writeString(firstName);
        out.writeString(lastName);
        out.writeString(email);
        out.writeLong(lastLogin.getTime());
        out.writeValue(restAdapter);
    }

    /**
     * Gets the user's email address.
     *
     * @return The user's email address.
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Gets the user's first name.
     *
     * @return The user's first name.
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * Gets the user's last name.
     *
     * @return The user's last name.
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * Gets the user id.
     *
     * @return The user id.
     */
    public String getId() {
        return this.userId;
    }

    /**
     * Gets the username.
     *
     * @return The username.
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Gets the user's last login date and time.
     *
     * @return The user's last login date and time.
     */
    public Date getLastLogin() {
        return this.lastLogin;
    }

    /**
     * Gets the user created date and time.
     *
     * @return The user created date and time.
     */
    public Date getCreatedAt() {
        return this.createdTime;
    }

    /**
     * Creates a string containing a concise, human-readable description of User object.
     *
     * @return The printable representation of User object.
     */
    @Override
    public String toString() {
        return "\nusername[" + this.username + "] \ncreated_time[" + Long.toString(this.createdTime.getTime())
                + "] \nfirst_name[" + this.firstName + "] \nlast_name[" + this.lastName + "] \nemail[" + this.email
                + "]*****";
    }
}
