package api.model;

import java.util.Objects;

public class Student {

    private Integer id;
    private String name;
    private String email;
    private String registration;
    private String course;

    public Student() {
    }

    public Student(Integer id, String name, String email, String registration, String course) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.registration = registration;
        this.course = course;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }
    
    @Override
    public String toString() {
        return "Student{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", email='" + email + '\'' +
            ", registration='" + registration + '\'' +
            ", course='" + course + '\'' +
            '}';
   }
   
   @Override
   public boolean equals(Object object) {

	if (this == object) {
	        return true;
	}

    	if (object == null || getClass() != object.getClass()) {
        	return false;
    	}

    	Student student = (Student) object;

    	return Objects.equals(id, student.id);
    }
    	
    
   @Override
   public int hashCode() {
    	return Objects.hash(id);
    }

}
