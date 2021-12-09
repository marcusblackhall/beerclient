package com.iamatum.beerclient.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

/**
 * Beer
 */
@Data
@Builder
public class Beer {


    private UUID id;

    @JsonProperty("beerName")
    private String beerName = null;

    @JsonProperty("beerStyle")
    private BeerStyleEnum beerStyle = null;

    @JsonProperty("upc")
    private String upc = null;

    @JsonProperty("quantityOnHand")
    private Integer quantityOnHand = null;

    @JsonProperty("price")
    private BigDecimal price = null;

    public Beer beerName(String beerName) {
        this.beerName = beerName;
        return this;
    }

    /**
     * Get beerName
     *
     * @return beerName
     **/

    public String getBeerName() {
        return beerName;
    }

    public void setBeerName(String beerName) {
        this.beerName = beerName;
    }

    public Beer beerStyle(BeerStyleEnum beerStyle) {
        this.beerStyle = beerStyle;
        return this;
    }

    /**
     * Get beerStyle
     *
     * @return beerStyle
     **/


    public BeerStyleEnum getBeerStyle() {
        return beerStyle;
    }

    public void setBeerStyle(BeerStyleEnum beerStyle) {
        this.beerStyle = beerStyle;
    }

    public Beer upc(String upc) {
        this.upc = upc;
        return this;
    }

    /**
     * Get upc
     *
     * @return upc
     **/


    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public Beer quantityOnHand(Integer quantityOnHand) {
        this.quantityOnHand = quantityOnHand;
        return this;
    }

    /**
     * Get quantityOnHand
     *
     * @return quantityOnHand
     **/
    public Integer getQuantityOnHand() {
        return quantityOnHand;
    }

    public void setQuantityOnHand(Integer quantityOnHand) {
        this.quantityOnHand = quantityOnHand;
    }



    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Beer beer = (Beer) o;
        return Objects.equals(this.beerName, beer.beerName) &&
                Objects.equals(this.beerStyle, beer.beerStyle) &&
                Objects.equals(this.upc, beer.upc) &&
                Objects.equals(this.quantityOnHand, beer.quantityOnHand) &&
                Objects.equals(this.price, beer.price) &&
                super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(beerName, beerStyle, upc, quantityOnHand, price, super.hashCode());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Beer {\n");
        sb.append("    ").append(toIndentedString(super.toString())).append("\n");
        sb.append("    beerName: ").append(toIndentedString(beerName)).append("\n");
        sb.append("    beerStyle: ").append(toIndentedString(beerStyle)).append("\n");
        sb.append("    upc: ").append(toIndentedString(upc)).append("\n");
        sb.append("    quantityOnHand: ").append(toIndentedString(quantityOnHand)).append("\n");
        sb.append("    price: ").append(toIndentedString(price)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
