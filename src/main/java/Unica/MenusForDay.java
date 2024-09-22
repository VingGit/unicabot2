package Unica;

import java.util.List;
import javax.annotation.Generated;
import javax.validation.Valid;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class MenusForDay {

    @SerializedName("Date")
    @Expose
    private String date;
    @SerializedName("LunchTime")
    @Expose
    private String lunchTime;
    @SerializedName("SetMenus")
    @Expose
    @Valid
    private List<SetMenu> setMenus;


    /**
     *
     * @param date
     * @param lunchTime
     * @param setMenus
     */
    public MenusForDay(String date, String lunchTime, List<SetMenu> setMenus) {

        this.date = date;
        this.lunchTime = lunchTime;
        this.setMenus = setMenus;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLunchTime() {
        return lunchTime;
    }

    public void setLunchTime(String lunchTime) {
        this.lunchTime = lunchTime;
    }

    public List<SetMenu> getSetMenus() {
        return setMenus;
    }

    public void setSetMenus(List<SetMenu> setMenus) {

        this.setMenus = setMenus;
    }

    @Override
    public String toString() {
        return "MenusForDay{" +
                "date='" + date + "\n" +
                ", lunchTime='" + lunchTime + "\n" +
                ", setMenus=" + setMenus +
                '}';
    }
}