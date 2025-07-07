package vs.java.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Wineries")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Winery {

    @Id
    @Column(name = "wineryID")
    private String wineryID;
    
    @Column(name = "wineryName", nullable = false)
    private String wineryName;
    
    @Column(name = "abbreviatedName")
    private String abbreviatedName;
    
    @Column(name = "Address")
    private String Address;
    
    @Column(name = "City")
    private String City;
    
    @Column(name = "State")
    private String State;
    
    @Column(name = "ZipCode")
    private String ZipCode;
} 