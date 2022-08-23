package com.roms.api.model;

import com.roms.api.config.ModelHashMapConverter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

@Entity
@Table(name ="user_preferences_map")
public class UserPreferencesMap extends CommonFields implements Serializable {

    @Serial
    private static final long serialVersionUID = -5565919178605132949L;

    @OneToOne()
    @JoinColumn(name = "employee_idx",referencedColumnName = "id")
    @Fetch(FetchMode.SELECT)
    private Employe employe;

    @Column(name = "preference_map", columnDefinition = "json")
    @Convert(converter = ModelHashMapConverter.class)
    private Map<String, Object> preferencesMap;

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public Map<String, Object> getPreferencesMap() {
        return preferencesMap;
    }

    public void setPreferencesMap(Map<String, Object> preferencesMap) {
        this.preferencesMap = preferencesMap;
    }
}
