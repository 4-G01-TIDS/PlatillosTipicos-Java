/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package platillostipicos.appdesktop.utils;

/**
 *
 * @author edefl
 */
public class ItemsCombo {

    private int id;
    private String label;
    private String value;
    // getter y setter

    public ItemsCombo() {
    }

    public ItemsCombo(int id, String label, String value) {
        this.id = id;
        this.label = label;
        this.value = value;
    }

    public String toString() {
        return label;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ItemsCombo other = (ItemsCombo) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
}
