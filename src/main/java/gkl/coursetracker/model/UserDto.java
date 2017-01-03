package gkl.coursetracker.model;

import java.security.MessageDigest;

/**
 * Created by shane on 21/09/2016.
 */
public class UserDto {

    private String firstName;
    private String lastName;
    private String username;
    private String usernameHashed;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsernameHashed() {
        return this.usernameHashed;
    }

    public void setUsernameHashed(String username) {
        this.usernameHashed = sha256(username);
    }

    /*
     * SHA-256 String
     * @link http://stackoverflow.com/a/11009612/1167354
     */
    public static String sha256(String base) {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch(Exception ex){
           throw new RuntimeException(ex);
        }
    }

}
