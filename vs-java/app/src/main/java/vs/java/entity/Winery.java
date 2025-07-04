package vs.java.entity;

public class Winery {

    private String wineryID;
    private String wineryName;
    private String abbreviatedName;
    private String Address;
    private String City;
    private String State;
    private String ZipCode;

    // Getters and setters
    public String getWineryID() {
        return wineryID;
    }

    public void setWineryID(String wineryID) {
        this.wineryID = wineryID;
    }

    public String getWineryName() {
        return wineryName;
    }

    public void setWineryName(String wineryName) {
        this.wineryName = wineryName;
    }

    public String getAbbreviationName() {
        return abbreviatedName;
    }

    public void setAbbreviatedName(String abbreviatedName) {
        this.abbreviatedName = abbreviatedName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String City) {
        this.City = City;
    }

    public String getState() {
        return State;
    }

    public void setState(String State) {
        this.State = State;
    }

    public String getZipCode() {
        return ZipCode;
    }

    public void setZipCode(String ZipCode) {
        this.ZipCode = ZipCode;
    }

    @Override
    public String toString() {
        return "Winery{" +
                "wineryID=" + wineryID +
                ", wineryName='" + wineryName + '\'' +
                ", abbreviatedName='" + abbreviatedName + '\'' +
                ", Address='" + Address + '\'' +
                ", City='" + City + '\'' +
                ", State='" + State + '\'' +
                ", ZipCode='" + ZipCode + '\'' +
                '}';
    }
} 