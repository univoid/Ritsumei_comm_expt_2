package exp2web.auth;

import java.security.MessageDigest;

public class User {
    //id
    private int id;

    // name
    private String name;

    // password
    private String password;

    //authority
    private int authority;


    public User() {
    }

    public User(String name, String password) {
        this.name = name;
        // password done MD5
        this.password = this.getMD5(password);
    }

    //id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    private String getMD5(String password) {

        StringBuilder sb = new StringBuilder();

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] pass = md.digest(password.getBytes());

            for (byte b : pass) {
                sb.append(String.format("%02x", b));
            }

            ;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    /**
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @param pass
     * @return
     */
    public boolean checkPassword(String pass) {
        String md5 = this.getMD5(pass);

        return this.password.equals(md5);
    }

    /**
     * @return the authority
     */
    public int getAuthority() {
        return authority;
    }

    /**
     *
     * @param Authority
     */
    public void setAuthority(int authority) {
        this.authority = authority;
    }

    @Override
    public String toString() {
        return String.format("[User: %s, %s]", name, password);
    }
}
