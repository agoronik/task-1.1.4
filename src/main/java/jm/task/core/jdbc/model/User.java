package jm.task.core.jdbc.model;

import javax.persistence.*;

@Entity
@Table(name = "Users1")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable=false, unique=true)
    private Long id;

    @Column(name = "name", length=20, nullable=true)
    private String name;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "age")
    private int age;

    @Override
    public String toString() {
        return String.format("User [id = %d, name = '%s', lastName = '%s', age = '%d']", id, name, lastName, age);
    }

    public User () {

    }

    public User(String name, String lastName, Byte age) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
