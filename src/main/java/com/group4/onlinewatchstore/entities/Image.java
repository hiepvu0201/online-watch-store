package com.group4.onlinewatchstore.entities;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Image")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "size")
    private long size;

    @Column(name = "type")
    private String type;

    @Lob
    @Column(name = "pic_byte", nullable = false)
    private byte[] picByte;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Image(String name, long size, String type, byte[] image) {
        this.name = name;
        this.size = size;
        this.picByte = image;
        this.type = type;
    }
}
