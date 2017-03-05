package ua.softserveinc.tc.dto;

import java.util.List;

/**
 * Created by daria on 02.03.2017.
 */
public class InfoDeactivateRoomDto {
    String parentName;
    String phoneNumber;
    String childName;

    public InfoDeactivateRoomDto() {
    }

    public InfoDeactivateRoomDto(String parentName, String phone, String childName) {
        this.parentName = parentName;
        this.phoneNumber = phone;
        this.childName = childName;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getPhone() {
        return phoneNumber;
    }

    public void setPhone(String phone) {
        this.phoneNumber = phone;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    @Override
    public String toString() {
        return "InfoDeactivateRoomDto{" +
                "parentName='" + parentName + '\'' +
                ", phone='" + phoneNumber + '\'' +
                ", childName='" + childName + '\'' +
                '}';
    }
}
