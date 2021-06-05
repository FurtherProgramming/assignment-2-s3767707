package main.model;

/*
 * Class:		AccountHolder
 * Description:	A singleton class that is used to pass user information between scenes
 * Author:		Anson Go Guang Ping
 */
public class AccountHolder {

    private final static AccountHolder INSTANCE = new AccountHolder();
    private User user;

    private AccountHolder() {
    }

    public static AccountHolder getInstance() {
        return INSTANCE;
    }

    public User getAccount() {
        return this.user;
    }

    public void setAccount(User user) {
        this.user = user;
    }


}
