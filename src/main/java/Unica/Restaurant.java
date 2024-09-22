package Unica;
import java.util.List;
import javax.annotation.Generated;
import javax.validation.Valid;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Restaurant {

    @SerializedName("RestaurantName")
    @Expose
    private String restaurantName;
    @SerializedName("RestaurantUrl")
    @Expose
    private String restaurantUrl;
    @SerializedName("PriceHeader")
    @Expose
    private Object priceHeader;
    @SerializedName("Footer")
    @Expose
    private String footer;
    @SerializedName("MenusForDays")
    @Expose
    @Valid
    private List<MenusForDay> menusForDays;
    @SerializedName("ErrorText")
    @Expose
    private Object errorText;


    /**
     *
     * @param priceHeader
     * @param errorText
     * @param menusForDays
     * @param restaurantName
     * @param footer
     * @param restaurantUrl
     */
    public Restaurant(String restaurantName, String restaurantUrl, Object priceHeader, String footer, List<MenusForDay> menusForDays, Object errorText) {
        this.restaurantName = restaurantName;
        this.restaurantUrl = restaurantUrl;
        this.priceHeader = priceHeader;
        this.footer = footer;
        this.menusForDays = menusForDays;
        this.errorText = errorText;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getRestaurantUrl() {
        return restaurantUrl;
    }

    public void setRestaurantUrl(String restaurantUrl) {
        this.restaurantUrl = restaurantUrl;
    }

    public Object getPriceHeader() {
        return priceHeader;
    }

    public void setPriceHeader(Object priceHeader) {
        this.priceHeader = priceHeader;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public List<MenusForDay> getMenusForDays() {


        return menusForDays;
    }

    public void setMenusForDays(List<MenusForDay> menusForDays) {
        this.menusForDays = menusForDays;
    }

    public Object getErrorText() {
        return errorText;
    }

    public void setErrorText(Object errorText) {
        this.errorText = errorText;
    }

    @Override
    public String toString() {
        return "unicaMain{" +
                "restaurantName='" + restaurantName + "\n" +
                ", restaurantUrl='" + restaurantUrl + "\n" +
                ", priceHeader=" + priceHeader + "\n" +
                ", footer='" + footer + "\n" +
                ", menusForDays=" + menusForDays + "\n" +
                ", errorText=" + errorText +
                '}';
    }
}