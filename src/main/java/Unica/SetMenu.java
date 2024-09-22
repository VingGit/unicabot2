package Unica;

import java.util.List;
import javax.annotation.Generated;
import javax.validation.Valid;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class SetMenu {

    @SerializedName("SortOrder")
    @Expose
    private Integer sortOrder;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Price")
    @Expose
    private String price;
    @SerializedName("Components")
    @Expose
    @Valid
    private List<String> components;



    /**
     *
     * @param components
     * @param price
     * @param sortOrder
     * @param name
     */
    public SetMenu(Integer sortOrder, String name, String price, List<String> components) {

        this.sortOrder = sortOrder;
        this.name = name;
        this.price = price;
        this.components = components;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public List<String> getComponents() {
        return components;
    }

    public void setComponents(List<String> components) {
        this.components = components;
    }

    @Override
    public String toString() {
        return "SetMenu{" +
                "sortOrder=" + sortOrder + "\n" +
                ", name='" + name + "\n" +
                ", price='" + price + "\n" +
                ", components=" + components +
                '}';
    }
}