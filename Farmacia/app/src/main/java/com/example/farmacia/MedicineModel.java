package com.example.farmacia;

public class MedicineModel {
    String id, name, datecad, compoundactive, content, description, laboratory, price;

    public MedicineModel() {

    }

    public MedicineModel(String id, String name, String datecad, String compoundactive, String content, String description, String laboratory, String price) {
        this.id = id;
        this.name = name;
        this.datecad = datecad;
        this.compoundactive = compoundactive;
        this.content = content;
        this.description = description;
        this.laboratory = laboratory;
        this.price = price;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getDatecad() {
        return datecad;
    }

    public void setDatecad(String datecad) {
        this.datecad = datecad;
    }

    public String getCompoundactive() {
        return compoundactive;
    }

    public void setCompoundactive(String compoundactive) {
        this.compoundactive = compoundactive;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLaboratory() {
        return laboratory;
    }

    public void setLaboratory(String laboratory) {
        this.laboratory = laboratory;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
