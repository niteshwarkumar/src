/*
 * Product.java
 *
 * Created on May 15, 2008, 11:15 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package app.extjs.vo;

import java.io.Serializable;

/**
 *
 * @author pp41387
 */
public class Product  implements Serializable{
    
    /** Creates a new instance of Product */
    public Product() {
    }
    
    private Integer ID_Client;
    private Integer ID_Product;
    private String product;
    private String category;
    private String description;
    private String medical;
    
   
    
    public String getProduct() {
        return product;
    }
    
    public void setProduct(String product) {
        this.product = product;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getID_Client() {
        return ID_Client;
    }

    public void setID_Client(Integer ID_Client) {
        this.ID_Client = ID_Client;
    }

    public Integer getID_Product() {
        return ID_Product;
    }

    public void setID_Product(Integer ID_Product) {
        this.ID_Product = ID_Product;
    }
    public String getMedical(){
        return medical;
    }
    public void setMedical(String medical){
        this.medical = medical;
    }
    
    
    
    
}
