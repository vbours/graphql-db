package com.boursinos.graphqldb.model.client;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@ToString
@Entity
public class Client {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "client_id")
    private String clientId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "firstname")
    private String firstname;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "lastname")
    private String lastname;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedAt;


    public Client(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }
}
