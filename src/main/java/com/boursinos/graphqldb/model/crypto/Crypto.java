package com.boursinos.graphqldb.model.crypto;

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
public class Crypto {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "crypto_id")
    private String cryptoId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "name")
    private String name;

    @Column(name = "value")
    private double value;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedAt;

    public Crypto(String name, double value) {
        this.name = name;
        this.value = value;
    }
}
