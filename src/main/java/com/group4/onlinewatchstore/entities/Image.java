package com.group4.onlinewatchstore.entities;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.beans.ConstructorProperties;
import java.sql.Timestamp;
import java.time.LocalDateTime;

//@Setter
//@Getter
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
@Entity
@Table(name = "Image")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private final long id;

    @Column(name = "name")
    private final String name;

    @Column(name = "size")
    private final long size;

    @Column(name = "type")
    private final String type;

    @Lob
    @Column(name = "pic_byte", nullable = false)
    private final byte[] picByte;

    @CreationTimestamp
    @Column(name = "created_at")
    private final LocalDateTime createdAt;

    @ConstructorProperties({"name", "size", "type", "pic_byte", "created_at"})
    Image(long id, String name, long size, String type, byte[] picByte, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.type = type;
        this.picByte = picByte;
        this.createdAt = createdAt;
    }

    public static Image.ImageBuilder builder() {
        return new Image.ImageBuilder();
    }

    public long getId(){
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public long getSize() {
        return this.size;
    }

    public String getType() {
        return this.type;
    }

    public byte[] getPicByte() {
        return this.picByte;
    }

    public LocalDateTime createdAt() {
        return this.createdAt;
    }

    public String toString() {
        return "Image(name=" + this.getName() + ", size=" + this.getSize() + ", type=" + this.getType() + ", picByte=" + this.getPicByte() + ", createdAt=" + this.createdAt() + ")";
    }

    public static class ImageBuilder {

        private long id;
        private String name;
        private long size;
        private String type;
        private byte[] picByte;
        private LocalDateTime createdAt;

        ImageBuilder() {
        }

        public Image.ImageBuilder name(String name){
            this.name = name;
            return this;
        }

        public Image.ImageBuilder size(long size){
            this.size = size;
            return this;
        }

        public Image.ImageBuilder type(String type){
            this.type = type;
            return this;
        }

        public Image.ImageBuilder picByte(byte[] picByte){
            this.picByte = picByte;
            return this;
        }

        public Image.ImageBuilder createdAt(LocalDateTime createdAt){
            this.createdAt = createdAt;
            return this;
        }

        public Image build() {
            return new Image(this.id, this.name, this.size, this.type, this.picByte, this.createdAt);
        }

        public String toString(){
            return "Image.ImageBuilder(name=" + this.name + ", size=" + this.size + ", type=" + this.type + ", picByte=" + this.picByte + ", createdAt=" + this.createdAt + ")";
        }
    }

}
