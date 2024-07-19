package jpql.dto;

public class MemberDto {
    private String username;
    private int age;

    public String getUsername() {
        return username;
    }

    public int getAge() {
        return age;
    }

    public MemberDto(String username, int age) {
        this.username = username;
        this.age = age;
    }
}
