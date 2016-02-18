package group12.whosplayin;


/**
 * Created by twohyjr on 2/16/16.
 */


public class User {

    //[{"username":"tom","id":1,"name":"Tom Collin","age":20,"gender":"male","location":"Chicago","rating":null,"verified":null,"dateCreated":"2016-02-12T17:07:11.000Z",
    // "lastLogin":null,"picture":null,"gamesPlayed":null,"gamesCreated":null}]

    private int id = 0;
    private String username = "";
    private String name = "";
    private int age = 0;
    private String gender = "";
    private String location = "";
    private int rating = 0;
    private String verified = "";
    private String dateCreated = "";
    private String lastLogin = "";
    private String profilePicture = "";
    private int gamesPlayed = 0;
    private int gamesCreated = 0;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String isVerified() {
        return verified;
    }

    public void setVerified(String verified) {
        this.verified = verified;
    }

    public String getDateCreated() {return dateCreated; }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public int getGamesCreated() {
        return gamesCreated;
    }

    public void setGamesCreated(int gamesCreated) {
        this.gamesCreated = gamesCreated;
    }





}
