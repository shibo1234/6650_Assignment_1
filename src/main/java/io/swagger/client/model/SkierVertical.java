/*
 * Ski Data API for NEU Seattle distributed systems course
 * An API for an emulation of skier managment system for RFID tagged lift tickets. Basis for CS6650 Assignments for 2019
 *
 * OpenAPI spec version: 2.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package io.swagger.client.model;

import java.util.Objects;
import java.util.Arrays;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.client.model.SkierVerticalResorts;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * SkierVertical
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2025-02-08T02:37:21.458105889Z[GMT]")

public class SkierVertical {
  @SerializedName("resorts")
  private List<SkierVerticalResorts> resorts = null;

  public SkierVertical resorts(List<SkierVerticalResorts> resorts) {
    this.resorts = resorts;
    return this;
  }

  public SkierVertical addResortsItem(SkierVerticalResorts resortsItem) {
    if (this.resorts == null) {
      this.resorts = new ArrayList<SkierVerticalResorts>();
    }
    this.resorts.add(resortsItem);
    return this;
  }

   /**
   * Get resorts
   * @return resorts
  **/
  @Schema(description = "")
  public List<SkierVerticalResorts> getResorts() {
    return resorts;
  }

  public void setResorts(List<SkierVerticalResorts> resorts) {
    this.resorts = resorts;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SkierVertical skierVertical = (SkierVertical) o;
    return Objects.equals(this.resorts, skierVertical.resorts);
  }

  @Override
  public int hashCode() {
    return Objects.hash(resorts);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SkierVertical {\n");
    
    sb.append("    resorts: ").append(toIndentedString(resorts)).append("\n");
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
