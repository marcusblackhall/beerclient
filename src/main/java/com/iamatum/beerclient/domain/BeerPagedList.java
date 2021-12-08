package com.iamatum.beerclient.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * BeerPagedList
 */
public class BeerPagedList {
    @JsonProperty("content")
    private List<Beer> content = null;

    public BeerPagedList content(List<Beer> content) {
        this.content = content;
        return this;
    }

    public BeerPagedList addContentItem(Beer contentItem) {
        if (this.content == null) {
            this.content = new ArrayList<Beer>();
        }
        this.content.add(contentItem);
        return this;
    }

    /**
     * Get content
     *
     * @return content
     **/
    public List<Beer> getContent() {
        return content;
    }

    public void setContent(List<Beer> content) {
        this.content = content;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BeerPagedList beerPagedList = (BeerPagedList) o;
        return Objects.equals(this.content, beerPagedList.content) &&
                super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content, super.hashCode());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class BeerPagedList {\n");
        sb.append("    ").append(toIndentedString(super.toString())).append("\n");
        sb.append("    content: ").append(toIndentedString(content)).append("\n");
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
