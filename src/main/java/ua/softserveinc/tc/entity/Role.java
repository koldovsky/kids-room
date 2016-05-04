package ua.softserveinc.tc.entity;

import javax.persistence.*;



public enum Role {

    ANONYMOUS,
    USER,
    MANAGER,
    ADMINISTRATOR;

    Role(){
    }

}
