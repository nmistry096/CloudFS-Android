package com.bitcasa.cloudfs.model;

import com.google.gson.annotations.SerializedName;

/**
 * Model class for user profile.
 */
public class UserProfile {

    /**
     * Username of the user.
     */
    private final String username;

    /**
     * Created date of the profile.
     */
    @SerializedName("created_at")
    private final Long createdAt;

    /**
     * First name of the user.
     */
    @SerializedName("first_name")
    private final String firstName;

    /**
     * Last name of the user.
     */
    @SerializedName("last_name")
    private final String lastName;

    /**
     * Account ID of the user.
     */
    @SerializedName("account_id")
    private final String accountId;

    /**
     * Locale of the user.
     */
    private final String locale;

    /**
     * Account state of the user.
     */
    @SerializedName("account_state")
    private final AccountState accountState;

    /**
     * Storage details of the user.
     */
    private Storage storage;

    /**
     * Holds for the account.
     */
    private Holds holds;

    /**
     * Account plan of the user.
     */
    @SerializedName("account_plan")
    private final AccountPlan accountPlan;

    /**
     * Email of the user.
     */
    private final String email;

    /**
     * Current session details.
     */
    private final Session session;

    /**
     * Last login date of the user.
     */
    @SerializedName("last_login")
    private final Long lastLogin;

    /**
     * ID of the user.
     */
    private final String id;

    /**
     * Initializes a new instance of an user profile.
     *
     * @param username     Username of the user.
     * @param createdAt    Created date of the profile.
     * @param firstName    First name of the user.
     * @param lastName     Last name of the user.
     * @param accountId    Account ID of the user.
     * @param locale       Locale of the user.
     * @param accountState Account state of the user.
     * @param storage      Storage details of the user.
     * @param holds        Holds for the account.
     * @param accountPlan  Account plan of the user.
     * @param email        Email of the user.
     * @param session      Current session details.
     * @param lastLogin    Last login date of the user.
     * @param id           ID of the user.
     */
    public UserProfile(final String username, final Long createdAt, final String firstName, final String lastName,
                       final String accountId, final String locale, final AccountState accountState, final Storage storage,
                       final Holds holds, final AccountPlan accountPlan, final String email, final Session session,
                       final Long lastLogin, final String id) {
        this.username = username;
        this.createdAt = createdAt;
        this.firstName = firstName;
        this.lastName = lastName;
        this.accountId = accountId;
        this.locale = locale;
        this.accountState = accountState;
        this.storage = storage;
        this.holds = holds;
        this.accountPlan = accountPlan;
        this.email = email;
        this.session = session;
        this.lastLogin = lastLogin;
        this.id = id;
    }

    /**
     * Gets the username of the user.
     *
     * @return Username of the user.
     */
    public final String getUsername() {
        return this.username;
    }

    /**
     * Gets the created date of the profile.
     *
     * @return Created date of the profile.
     */
    public final Long getCreatedAt() {
        return this.createdAt;
    }

    /**
     * Gets the first name of the user.
     *
     * @return First name of the user.
     */
    public final String getFirstName() {
        return this.firstName;
    }

    /**
     * Gets the last name of the user.
     *
     * @return Last name of the user.
     */
    public final String getLastName() {
        return this.lastName;
    }

    /**
     * Gets the account ID of the user.
     *
     * @return Account ID of the user.
     */
    public final String getAccountId() {
        return this.accountId;
    }

    /**
     * Gets the locale of the user.
     *
     * @return Locale of the user.
     */
    public final String getLocale() {
        return this.locale;
    }

    /**
     * Gets the account state of the user.
     *
     * @return Account state of the user.
     */
    public final AccountState getAccountState() {
        return this.accountState;
    }

    /**
     * Gets the storage details of the user.
     *
     * @return Storage details of the user.
     */
    public final Storage getStorage() {
        return this.storage;
    }

    /**
     * Sets the storage details of the user.
     *
     * @param storage Storage details of the user.
     */
    public final void setStorage(final Storage storage) {
        this.storage = storage;
    }

    /**
     * Gets the holds of the account.
     *
     * @return Holds of the account.
     */
    public final Holds getHolds() {
        return this.holds;
    }

    /**
     * Sets the holds of the account.
     *
     * @param holds Holds of the account.
     */
    public final void setHolds(final Holds holds) {
        this.holds = holds;
    }

    /**
     * Gets the account plan of the user.
     *
     * @return Account plan of the user.
     */
    public final AccountPlan getAccountPlan() {
        return this.accountPlan;
    }

    /**
     * Gets the email of the user.
     *
     * @return Email of the user.
     */
    public final String getEmail() {
        return this.email;
    }

    /**
     * Gets the session of the user.
     *
     * @return Session of the user.
     */
    public final Session getSession() {
        return this.session;
    }

    /**
     * Gets the last login date of the user.
     *
     * @return Last login date of the user.
     */
    public final Long getLastLogin() {
        return this.lastLogin;
    }

    /**
     * Gets the ID of the user.
     *
     * @return ID of the user.
     */
    public final String getId() {
        return this.id;
    }
}
