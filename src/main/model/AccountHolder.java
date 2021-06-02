package main.model;

/*
 * Class:		AccountHolder
 * Description:	A singleton class that is used to pass user information between scenes
 * Author:		Anson Go Guang Ping
 */
public class AccountHolder {

    private User user;
    private final static AccountHolder INSTANCE = new AccountHolder();

    private AccountHolder() {}

    public static AccountHolder getInstance() {
        return INSTANCE;
    }

    public void setAccount(User user) {
        this.user = user;
    }

    public User getAccount() {
        return this.user;
    }


}
