package tracker;

public class Course {
    int java;
    int dsa;
    int databases;
    int spring;

    public Course() {
        this.java = 0;
        this.dsa = 0;
        this.databases = 0;
        this.spring = 0;
    }

    public int getJava() {
        return java;
    }

    public void setJava(int java) {
        this.java = java;
    }

    public int getDsa() {
        return dsa;
    }

    public void setDsa(int dsa) {
        this.dsa = dsa;
    }

    public int getDatabases() {
        return databases;
    }

    public void setDatabases(int databases) {
        this.databases = databases;
    }

    public int getSpring() {
        return spring;
    }

    public void setSpring(int spring) {
        this.spring = spring;
    }

    @Override
    public String toString() {
        return "Course{" +
                "java=" + java +
                ", dsa=" + dsa +
                ", databases=" + databases +
                ", spring=" + spring +
                '}';
    }
}
